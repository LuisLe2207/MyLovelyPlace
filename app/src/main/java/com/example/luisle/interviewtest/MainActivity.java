package com.example.luisle.interviewtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.luisle.interviewtest.places.DaggerPlacesPresenterComponent;
import com.example.luisle.interviewtest.places.PlacesFragment;
import com.example.luisle.interviewtest.places.PlacesPresenter;
import com.example.luisle.interviewtest.places.PlacesPresenterModule;
import com.example.luisle.interviewtest.utils.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.luisle.interviewtest.utils.AppUtils.PLACE_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity implements AppUtils.Communicator{

    @Inject
    PlacesPresenter presenter;


    @BindView(R.id.mainAct_Toolbar)
    Toolbar toolbar;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        PlacesFragment placeFragment = PlacesFragment.newInstance();
        if (!AppUtils.deviceIsTabletAndInLandscape(MainActivity.this)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainAct_FrameLayout, placeFragment, PLACE_FRAGMENT_TAG).commit();

        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainAct_ListFragContent, placeFragment, PLACE_FRAGMENT_TAG).commit();
        }

        DaggerPlacesPresenterComponent.builder()
                .placesRepositoryComponent(((MyApp)getApplication()).getRepositoryComponent())
                .placesPresenterModule(new PlacesPresenterModule(placeFragment)).build()
                .inject(this);
    }

    @Override
    public void setActionBarTitle(@NonNull String title) {
        actionBar.setTitle(title);
    }
}
