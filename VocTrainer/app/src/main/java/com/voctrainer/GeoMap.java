package com.voctrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeoMap extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener{


    /*
    PrimaryColorBlue(RGB):    0,  81, 159
    PrimaryColorGreen(RGB): 145, 167, 3
     */
    private GoogleMap mMap;
    public Button btn_DEBUG_Inside_Radius;

    private final String SELECTED_AREA = "selectedArea";

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 500; // millisec.
    private final float MIN_DIST = 0.2f; // Meters

    private LatLngBounds UNIVERSITY_AREA_BOUNDS = new LatLngBounds(
            new LatLng(52.162012256087124, 9.932759429103145), // SW bounds
            new LatLng(52.17854514551059, 9.954006410545121)  // NE bounds
    );

    private float MIN_ZOOM_SIZE_MAP = 14.0f; // Max size for zooming out
    private float MAX_ZOOM_SIZE_MAP = 18.0f; // Max size for zooming in

    private LatLng current_latLng = new LatLng(52.3825973407738, 9.717844806973376);

    private LatLng COORDS_AREA_NETTO = new LatLng(52.37715709991843, 9.807249143046873);
    private LatLng COORDS_AREA_PHYSIK = new LatLng(52.38820423962352, 9.710702083217582);
    private LatLng COORDS_AREA_WIRTSCHAFT = new LatLng(52.378316453594074, 9.724494898171324);
    private LatLng COORDS_AREA_SE = new LatLng(52.382673590028475, 9.716884204104959);
    private LatLng COORDS_AREA_ETECHNIK = new LatLng(52.38944092518481, 9.71510862426616);
    private LatLng COORDS_AREA_SOZIOLOGIE = new LatLng(52.38586900257287, 9.713364899880526);

    private int areaID = -1; // Physik=0, Wirtschaft=1, SE=2, ETechnik=3, Soziologie=4

    private final int MARKER_USER = -1;
    private final int MARKER_AREA_BLUE = 0;
    private final int MARKER_AREA_BLUE_1_STAR = 1;
    private final int MARKER_AREA_BLUE_2_STARS = 2;
    private final int MARKER_AREA_BLUE_3_STARS = 3;
    private final int MARKER_AREA_GREY = 4;

    private final String USER_DATA_KEY_LAT = "userDataKeyLat";
    private final String USER_DATA_KEY_LON = "userDataKeyLon";

    private final double AREA_RADIUS = 30.0;

    private Marker marker_user;
    private boolean userPosSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMapsBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_gui4_geo_map);
        this.setTitle("Fachgebiet finden");
        btn_DEBUG_Inside_Radius = (Button) findViewById(R.id.button_DEBUG_in_Radius);
        btn_DEBUG_Inside_Radius.setText("Skip");
        btn_DEBUG_Inside_Radius.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
    }

    // scales the marker icons
    private Bitmap createSmallIcon(int markerID, int height, int width) {
        BitmapDrawable bitmapDraw;
        if (markerID == -1)
            bitmapDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_user);
        else if (markerID == 0)
            bitmapDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_area_blue);
        else if (markerID == 1)
            bitmapDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_area_blue1star);
        else if (markerID == 2)
            bitmapDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_area_blue2stars);
        else if (markerID == 3)
            bitmapDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_area_blue3stars);
        else bitmapDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_area_grey);
        return Bitmap.createScaledBitmap(bitmapDraw.getBitmap(), width, height, false);
    }

    private float distanceBetweenLocations(LatLng ll1, LatLng ll2){
        float[] results = new float[1];
        Location.distanceBetween(ll1.latitude, ll1.longitude, ll2.latitude, ll2.longitude, results);
        return results[0];
    }

    private boolean isUserInArea(LatLng userPos, LatLng areaPos){
        double dist = distanceBetweenLocations(userPos, areaPos);
        if(dist < AREA_RADIUS) return true;
        else return false;
    }

    private void saveUserPos(LatLng ll){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_DATA_KEY_LAT, String.valueOf(ll.latitude));
        editor.putString(USER_DATA_KEY_LON, String.valueOf(ll.longitude));
    }

    private LatLng getUserPos(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String latStr = sharedPref.getString(USER_DATA_KEY_LAT, null);
        String lonStr = sharedPref.getString(USER_DATA_KEY_LON, null);
        double latDou = 0.0, lonDou = 0.0;
        if(latStr != null) latDou = Double.valueOf(latStr);
        if(lonStr != null) lonDou = Double.valueOf(lonStr);
        return new LatLng(latDou, lonDou);
    }
    /*
    Is called if the user entered an area.
    Physik=0, Wirtschaft=1, SE=2, ETechnik=3, Soziologie=4
    */
    public void OnUserEnteredArea(int areaID){
        this.areaID = areaID;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
         Properties of the Map
        // Constrain the camera target to the university-area bounds.

        mMap.setLatLngBoundsForCameraTarget(UNIVERSITY_AREA_BOUNDS);
        mMap.setMinZoomPreference(MIN_ZOOM_SIZE_MAP); // Max size for zooming out
        mMap.setMaxZoomPreference(MAX_ZOOM_SIZE_MAP); // Max size for zooming in

         */

        marker_user = mMap.addMarker(new MarkerOptions().position(getUserPos()).title("My Position").icon(BitmapDescriptorFactory.fromBitmap(createSmallIcon(MARKER_USER, 100, 100))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_latLng, 16.0f));

        // Areas
        mMap.addMarker(new MarkerOptions().position(COORDS_AREA_NETTO).title("Fachbereich: A").icon(BitmapDescriptorFactory.fromBitmap(createSmallIcon(MARKER_AREA_BLUE, 150, 100))));
        mMap.addCircle(new CircleOptions().center(COORDS_AREA_NETTO).radius(AREA_RADIUS).strokeColor(Color.argb(0, 0, 81, 159)).fillColor(Color.argb(50, 0, 81, 159)));

        mMap.addMarker(new MarkerOptions().position(COORDS_AREA_PHYSIK).title("Fachbereich: Gravitationsphysik").icon(BitmapDescriptorFactory.fromBitmap(createSmallIcon(MARKER_AREA_BLUE, 150, 100))));
        mMap.addCircle(new CircleOptions().center(COORDS_AREA_PHYSIK).radius(AREA_RADIUS).strokeColor(Color.argb(0, 0, 81, 159)).fillColor(Color.argb(50, 0, 81, 159)));

        mMap.addMarker(new MarkerOptions().position(COORDS_AREA_WIRTSCHAFT).title("Fachbereich: Wirtschaftswissenschaft").icon(BitmapDescriptorFactory.fromBitmap(createSmallIcon(MARKER_AREA_BLUE, 150, 100))));
        mMap.addCircle(new CircleOptions().center(COORDS_AREA_WIRTSCHAFT).radius(AREA_RADIUS).strokeColor(Color.argb(0, 0, 81, 159)).fillColor(Color.argb(50, 0, 81, 159)));

        mMap.addMarker(new MarkerOptions().position(COORDS_AREA_SE).title("Fachbereich: Software Engineering").icon(BitmapDescriptorFactory.fromBitmap(createSmallIcon(MARKER_AREA_BLUE, 150, 100))));
        mMap.addCircle(new CircleOptions().center(COORDS_AREA_SE).radius(AREA_RADIUS).strokeColor(Color.argb(0, 0, 81, 159)).fillColor(Color.argb(50, 0, 81, 159)));

        mMap.addMarker(new MarkerOptions().position(COORDS_AREA_ETECHNIK).title("Fachbereich: Elektrotechnik").icon(BitmapDescriptorFactory.fromBitmap(createSmallIcon(MARKER_AREA_BLUE, 150, 100))));
        mMap.addCircle(new CircleOptions().center(COORDS_AREA_ETECHNIK).radius(AREA_RADIUS).strokeColor(Color.argb(0, 0, 81, 159)).fillColor(Color.argb(50, 0, 81, 159)));

        mMap.addMarker(new MarkerOptions().position(COORDS_AREA_SOZIOLOGIE).title("Fachbereich: Soziologie").icon(BitmapDescriptorFactory.fromBitmap(createSmallIcon(MARKER_AREA_BLUE, 150, 100))));
        mMap.addCircle(new CircleOptions().center(COORDS_AREA_SOZIOLOGIE).radius(AREA_RADIUS).strokeColor(Color.argb(0, 0, 81, 159)).fillColor(Color.argb(50, 0, 81, 159)));

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                current_latLng = new LatLng(location.getLatitude(), location.getLongitude());
                if(!userPosSaved){
                    saveUserPos(current_latLng);
                    userPosSaved = true;
                }
                if(marker_user != null) marker_user.remove();

                marker_user = mMap.addMarker(new MarkerOptions().position(current_latLng).title("My Position").icon(BitmapDescriptorFactory.fromBitmap(createSmallIcon(MARKER_USER, 100, 100))));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_latLng, 16.0f));
                //Toast.makeText(getApplicationContext(), location.getLatitude() + "  " + location.getLongitude(), Toast.LENGTH_LONG).show();
                if(isUserInArea(current_latLng, COORDS_AREA_NETTO)) Toast.makeText(getApplicationContext(), "Du bist bei Netto:" + distanceBetweenLocations(current_latLng, COORDS_AREA_NETTO), Toast.LENGTH_LONG).show();
                else if(isUserInArea(current_latLng, COORDS_AREA_PHYSIK)) OnUserEnteredArea(0);  // Physik=0, Wirtschaft=1, SE=2, ETechnik=3, Soziologie=4
                else if(isUserInArea(current_latLng, COORDS_AREA_WIRTSCHAFT)) OnUserEnteredArea(1);
                else if(isUserInArea(current_latLng, COORDS_AREA_SE)) OnUserEnteredArea(2);
                else if(isUserInArea(current_latLng, COORDS_AREA_ETECHNIK)) OnUserEnteredArea(3);
                else if(isUserInArea(current_latLng, COORDS_AREA_SOZIOLOGIE)) OnUserEnteredArea(4);
            }
            @Override
            public void onProviderDisabled (String provider){
            }
            @Override
            public void onProviderEnabled (String provider){
            }
            @Override
            public void onStatusChanged (String provider,int status, Bundle ext){
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_DEBUG_in_Radius) {
            Intent intent = new Intent(GeoMap.this, LevelSelection.class);
            //intent.putExtra(SELECTED_AREA, this.areaID);
            intent.putExtra(SELECTED_AREA, 3);
            startActivity(intent);
            this.finish();
        }
    }
    public void onBackPressed(){
        Intent intent = new Intent(GeoMap.this, MovingCounter.class);
        startActivity(intent);
        this.finish();
    }
}