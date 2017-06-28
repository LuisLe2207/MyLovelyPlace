package com.example.luisle.interviewtest.places;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisle.interviewtest.R;
import com.example.luisle.interviewtest.adapter.PlacesAdapter;
import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LuisLe on 6/28/2017.
 */

public class PlacesFragment extends Fragment implements PlacesContract.View, PlacesAdapter.OnClickCallback{

    private PlacesContract.Presenter presenter;
    private AppUtils.Communicator communicator;

    @BindView(R.id.txtListFrag_NoData)
    TextView txtNoData;

    @BindView(R.id.rcvListFrag_Places)
    RecyclerView rcvPlaces;

    private PlacesAdapter placesAdapter;

    public static PlacesFragment newInstance() {
        return new PlacesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (AppUtils.Communicator) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placesAdapter = new PlacesAdapter(getContext(), new ArrayList<Place>(0), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rowView = inflater.inflate(R.layout.fragment_place_list, container, false);
        ButterKnife.bind(this, rowView);

        rcvPlaces.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvPlaces.setAdapter(placesAdapter);

        return rowView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.start();
        }
        communicator.setActionBarTitle(getContext().getResources().getString(R.string.action_bar_title_list));
    }

    @Override
    public void setPresenter(PlacesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPlaces(List<Place> places) {
        placesAdapter.replaceData(places);
    }

    @Override
    public void showAddPlaceUi() {

    }

    @Override
    public void showNoDataAvailable() {
        txtNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataAvailable() {
        txtNoData.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPlaceDetailUI(@NonNull String placeID) {
        Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startDirectionActivity(View v, Place place) {
        Toast.makeText(getContext(), "Yoyo", Toast.LENGTH_SHORT).show();
    }
}