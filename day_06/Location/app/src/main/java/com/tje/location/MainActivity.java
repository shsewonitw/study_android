package com.tje.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_USED_PERMISSION = 200;

    private static final String [] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionToLocationAccepted = true;

        switch (requestCode){
            case REQUEST_USED_PERMISSION:
                for(int result : grantResults){
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionToLocationAccepted = false;
                        break;
                    }
                }
                break;
        }

        if(permissionToLocationAccepted == false){
            finish();
        } else{
            getMyLocation();
        }

    }




    private static final String MAP_BUNDLE_KEY = "MapBundelKey";
    private static final LatLng DEFAULT_LOCATION = new LatLng(37.56641923090,126.9778741551);
    private static final int DEFAULT_ZOOM = 15;

    private static final long INTERVAL_TIME = 5000;
    private static final long FASTEST_INTERVAL_TIME = 2000;

    private GoogleMap map;
    private MapView mapView;
    private Location lastKnownLocation;
    private int pinNumber =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for(String permission : needPermissions){
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, needPermissions, REQUEST_USED_PERMISSION);
                break;
            }
        }

        setContentView(R.layout.activity_main);

        Bundle mapViewBundel = null;
        if(savedInstanceState != null){
            mapViewBundel = savedInstanceState.getBundle(MAP_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map);
        mapView.onCreate(mapViewBundel);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                LatLng seoul = new LatLng(37.5641923090, 126.9778741551);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(seoul);
                markerOptions.title("서울");

                map.addMarker(markerOptions);
                map.moveCamera(CameraUpdateFactory.newLatLng(seoul));

                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);

                getMyLocation();
            }
        });

    }

    private void getMyLocation(){

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                lastKnownLocation = location;

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude()),DEFAULT_ZOOM));

                setMapLongClickEvent();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION,DEFAULT_ZOOM));
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
            }
        });
    }

    private void updateMyLocation(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL_TIME);
        locationRequest.setFastestInterval(FASTEST_INTERVAL_TIME);

        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
            }
        },null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapBundle = outState.getBundle(MAP_BUNDLE_KEY);
        if(mapBundle == null){
            mapBundle = new Bundle();
            outState.putBundle(MAP_BUNDLE_KEY,mapBundle);
        }
        mapView.onSaveInstanceState(mapBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void setMapLongClickEvent(){
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(MainActivity.this, "위도 "+ latLng.latitude+" 경도 "+latLng.longitude , Toast.LENGTH_SHORT).show();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("핀 "+pinNumber);
                markerOptions.icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.ryan2)
                );
                map.addMarker(markerOptions);
                pinNumber = pinNumber + 1;
                getRoute(latLng);
            }
        });
    }

    private void getRoute(final LatLng endPoint){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!=
        PackageManager.PERMISSION_GRANTED){
            return;
        }
        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Location lastLocation= location;
                LatLng startPoint = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                GoogleRouteApi googleRouteApi = new GoogleRouteApi(MainActivity.this, startPoint, endPoint,new GoogleRouteApi.EventListener(){
                    @Override
                    public void onApiResult(String result) {
                        Toast.makeText(MainActivity.this, "요청 성공 : "+result, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onApiFailed() {
                        Toast.makeText(MainActivity.this, "요청 실패", Toast.LENGTH_LONG).show();
                    }
                });
                googleRouteApi.start();
            }
        });
    }
}
