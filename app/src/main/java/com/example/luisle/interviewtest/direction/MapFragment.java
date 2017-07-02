package com.example.luisle.interviewtest.direction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.luisle.interviewtest.AppModule;
import com.example.luisle.interviewtest.MyApp;
import com.example.luisle.interviewtest.R;
import com.example.luisle.interviewtest.utils.AppUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LuisLe on 6/30/2017.
 */

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.mvMapFrag_Map)
    MapView mapView;

    @Inject
    MapPresenter mapPresenter;

    private MapContract.Presenter presenter;
    private AppUtils.Communicator communicator;


    private ProgressDialog progressDialog;
    public static final String PLACE_ID = "PlaceID";
    public static final int PERMISSIONS_REQUEST_LOCATION = 1;

    // Google map
    private GoogleMap googleMap;
    private GoogleApiClient apiClient;
    private static final int DEFAULT_ZOOM = 15;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;
    private LocationRequest locationRequest;


    private Marker originMarker, destinationMarker;
    private List<LatLng> decodedPath;
    private List<Polyline> polylineList = new ArrayList<>();

    public static MapFragment newInstance(@NonNull String placeID) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PLACE_ID, placeID);
        mapFragment.setArguments(bundle);

        return mapFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (AppUtils.Communicator) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getResources().getString(R.string.txt_finding_dialog));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);

        String placeID = getArguments().getString(PLACE_ID);

        // Create the presenter
        DaggerMapPresenterComponent.builder()
                .appModule(new AppModule(getContext()))
                .mapPresenterModule(new MapPresenterModule(this, placeID))
                .placesRepositoryComponent(((MyApp) getActivity().getApplication()).getRepositoryComponent()).build()
                .inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, root);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        presenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (apiClient == null) {
                            buildGoogleMapApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void drawRoutes(LatLng origin, LatLng destination, String originAddress, String destinationAddress, String polylinePoints) {
        if (decodedPath != null) {
            decodedPath.clear();
            for (Polyline polyline : polylineList) {
                polyline.remove();
            }
        }

        if (originMarker != null && destinationMarker != null) {
            originMarker.remove();
            destinationMarker.remove();
        }

        decodedPath = PolyUtil.decode(polylinePoints);
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(decodedPath);

        polylineList.add(googleMap.addPolyline(polylineOptions));

        originMarker = googleMap.addMarker(new MarkerOptions().position(origin).title(originAddress)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        destinationMarker = googleMap.addMarker(new MarkerOptions().position(destination).title(destinationAddress)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, DEFAULT_ZOOM));
    }

    @Override
    public void setToolbarTitle(String title) {
        communicator.setActionBarTitle(title);
    }

    @Override
    public void showProgressDlg() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDlg() {
        progressDialog.hide();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleMapApiClient();
                this.googleMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleMapApiClient();
            this.googleMap.setMyLocationEnabled(true);
        }

        this.googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                getCurrentLocation();
                return false;
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void buildGoogleMapApiClient() {
        apiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        apiClient.connect();
    }

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lastKnownLocation = location;

                    //Place current location marker
                    LatLng currLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    //move map camera
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(currLatLng));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    presenter.getRoutes(lastKnownLocation);

                    //stop location updates
                    if (apiClient != null) {
                        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, this);
                    }
                }
            });
        }
    }
}
