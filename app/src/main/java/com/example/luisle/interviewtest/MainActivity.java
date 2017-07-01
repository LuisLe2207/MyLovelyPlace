package com.example.luisle.interviewtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.luisle.interviewtest.addeditplace.AddEditPlaceFragment;
import com.example.luisle.interviewtest.places.PlacesFragment;
import com.example.luisle.interviewtest.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.luisle.interviewtest.utils.AppUtils.LANDSCAPE_STACK;
import static com.example.luisle.interviewtest.utils.AppUtils.PLACE_FRAGMENT_TAG;
import static com.example.luisle.interviewtest.utils.AppUtils.PORTRAIT_STACK;

public class MainActivity extends AppCompatActivity implements AppUtils.Communicator {

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

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        PlacesFragment placeFragment = PlacesFragment.newInstance();
        if (!AppUtils.deviceIsTabletAndInLandscape(MainActivity.this)) {
            transaction.replace(R.id.mainAct_FrameLayout, placeFragment, PLACE_FRAGMENT_TAG);

        } else {
            AddEditPlaceFragment addEditPlaceFragment = AddEditPlaceFragment.newInstance(null);
            transaction.replace(R.id.mainAct_FrameLayout, placeFragment, PLACE_FRAGMENT_TAG);
            transaction.replace(R.id.mainAct_AnotherFragContent, addEditPlaceFragment);
        }

        // Remove all BackStack fragment when device orientation change
        getSupportFragmentManager().popBackStack(LANDSCAPE_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().popBackStack(PORTRAIT_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.commit();

    }

    @Override
    public void setActionBarTitle(@NonNull String title) {
        actionBar.setTitle(title);
    }
}
