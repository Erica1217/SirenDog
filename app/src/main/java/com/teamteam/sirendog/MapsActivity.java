package com.teamteam.sirendog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private SafetyMapResponse safetyMapResponse;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=1;
    private final String TAG="MapsActivity";
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation = null;
    private LatLng defaultLatLng = new LatLng( 37.60291699,127.01969699);
    private static final int UPDATE_INTERVAL_MS = 1000;

    private GpsTracker gpsTracker;
    private static final int DEFAULT_ZOOM=15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar) ;
        setSupportActionBar(tb);
        FloatingActionButton sirenBtn = findViewById(R.id.sirenBtn);

        sirenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, SirenActivity.class));
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        gpsTracker = new GpsTracker(this);

        getLocationPermission();
        if(!mLocationPermissionGranted)
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    defaultLatLng, 15));

            return;
        }
        updateLocationUI();
        getDeviceLocation();
        showSafetyHouseMarkers();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude()), 15));
    }

    private void showSafetyHouseMarkers()
    {
        SafetyMapRequest mapRequest = new SafetyMapRequest(this);
        mLastKnownLocation = gpsTracker.getLocation();
        mMap.setOnMarkerClickListener(this);

        if(mLastKnownLocation == null)
        {
            mLastKnownLocation = new Location("dummy");
            mLastKnownLocation.setLatitude(defaultLatLng.latitude);
            mLastKnownLocation.setLongitude(defaultLatLng.longitude);
        }

        double diffLatitude = latitudeInDifference(800);
        double diffLongitude = longitudeInDifference(gpsTracker.getLatitude(), 800);

        mapRequest.minY = mLastKnownLocation.getLatitude() - diffLatitude;
        mapRequest.maxY = mLastKnownLocation.getLatitude() + diffLatitude;
        mapRequest.minX = mLastKnownLocation.getLongitude() - diffLongitude;
        mapRequest.maxX = mLastKnownLocation.getLongitude() + diffLongitude;

        Log.d("position X", ""+mapRequest.minX +" "+mapRequest.maxX);
        Log.d("position Y", ""+mapRequest.minY +" "+mapRequest.maxY);

        int pageIndex=1;
        RetrofitCreator.getService().getSafetyMap(
                mapRequest.esntlId,
                mapRequest.authKey,pageIndex,
                mapRequest.pageUnit,
                mapRequest.minX,
                mapRequest.maxX,
                mapRequest.minY,
                mapRequest.maxY,
                mapRequest.detailDate1,
                mapRequest.xmlUseYN
        ).enqueue(new Callback<SafetyMapResponse>() {
            @Override
            public void onResponse(Call<SafetyMapResponse> call, Response<SafetyMapResponse> response) {
                if(response.isSuccessful() && response.body().getResult().equals("00")) {
                    safetyMapResponse = response.body();
                    if(safetyMapResponse.getList().size() == 0)
                    {
                        Toast.makeText(MapsActivity.this,"근처에 아동안전지킴이 집이 없습니다..",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for(int i=0 ; i<safetyMapResponse.getList().size() ; i++) {
//                        Log.d("position",safetyMapResponse.getList().get(i).getLcinfoLa()+" "+safetyMapResponse.getList().get(i).getLcinfoLo());
                        SafetyMapData item = safetyMapResponse.getList().get(i);
                        LatLng l = new LatLng(item.getLcinfoLa(), item.getLcinfoLo());
                        Marker maker =mMap.addMarker(new MarkerOptions()
                                .position(l)
                                .title(item.getBsshNm()));
                        maker.setTag(safetyMapResponse.getList().get(i));
                    }
                }
                else {
                    Toast.makeText(MapsActivity.this,"정보를 가져오지 못했습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SafetyMapResponse> call, Throwable t) {
                Toast.makeText(MapsActivity.this,"정보를 가져오지 못했습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    showSafetyHouseMarkers();
                    getDeviceLocation();
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
//                mMap.setMyLocationEnabled(false);

                if(gpsTracker!=null)
                {
                    mLastKnownLocation = gpsTracker.getLocation();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                }
            }
            else
            {
                getLocationPermission();
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    //반경 m이내의 위도차(degree)
    public double latitudeInDifference(int diff){
        //지구반지름mCallPermissionGranted
        final int earth = 6371000;    //단위m

        return (diff*360.0) / (2*Math.PI*earth);
    }

    //반경 m이내의 경도차(degree)
    public double longitudeInDifference(double _latitude, int diff){
        //지구반지름
        final int earth = 6371000;    //단위m

        double ddd = Math.cos(0);
        double ddf = Math.cos(Math.toRadians(_latitude));

        return (diff*360.0) / (2*Math.PI*earth*Math.cos(Math.toRadians(_latitude)));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog();

        SafetyMapData data = (SafetyMapData) marker.getTag();
        Bundle args = new Bundle();
        if(data!=null)
        {
            args.putString("name",data.getBsshNm());
            args.putString("phone",data.getTelno());
        }
        bottomSheet.setArguments(args);
        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu) ;
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu :
                startActivity(new Intent(MapsActivity.this, MenuActivity.class));
                return true ;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }

}
