package com.example.luisle.interviewtest.direction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luisle.interviewtest.AppModule;
import com.example.luisle.interviewtest.MyApp;
import com.example.luisle.interviewtest.R;
import com.example.luisle.interviewtest.utils.AppUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
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

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback{

    @Inject
    MapPresenter mapPresenter;

    private MapContract.Presenter presenter;
    private AppUtils.Communicator communicator;

    @BindView(R.id.mvMapFrag_Map)
    MapView mapView;

    private GoogleMap googleMap;

    private ProgressDialog progressDialog;

    private Marker originMarker, destinationMarker;
    private List<LatLng> decodedPath;
    private List<Polyline> polylineList = new ArrayList<>();

    public static final String PLACE_ID = "PlaceID";

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
        progressDialog.setMessage(getContext().getResources().getString(R.string.txt_delete_dialog));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);

        String placeID = getArguments().getString(PLACE_ID);

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

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
        mapView.onResume();
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

        originMarker = googleMap.addMarker(new MarkerOptions().position(origin).title(originAddress));
        destinationMarker = googleMap.addMarker(new MarkerOptions().position(destination).title(destinationAddress));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
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
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.googleMap.setMyLocationEnabled(true);
    }
}
