package com.example.luisle.interviewtest.direction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.luisle.interviewtest.R;
import com.example.luisle.interviewtest.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LuisLe on 6/30/2017.
 */

public class DirectionActivity extends AppCompatActivity implements AppUtils.Communicator{

    @BindView(R.id.directionAct_Toolbar)
    Toolbar toolbar;

    ActionBar actionBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        String placeID = getIntent().getStringExtra(MapFragment.PLACE_ID);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MapFragment mapFragment = MapFragment.newInstance(placeID);
        transaction.replace(R.id.directionAct_FrameLayout, mapFragment);
        transaction.commit();

    }

    @Override
    public void setActionBarTitle(@NonNull String title) {
        actionBar.setTitle(title);
    }
}
