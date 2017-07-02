package com.example.luisle.interviewtest.placedetail;

import android.content.Context;

import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.PlacesRepository;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;
import com.example.luisle.interviewtest.map.Service;
import com.example.luisle.interviewtest.map.ServiceContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by LuisLe on 7/2/2017.
 */

public class PlaceDetailPresenterUnitTest {

    private static Place place = new Place("1", "Du Mien Cofee", "Go Vap District", "Lots of trees", null);

    @Mock
    PlacesRepository placesRepository;

    @Mock
    private PlaceDetailContract.View view;

    @Mock
    private Service service;

    private Context context;

    @Captor
    private ArgumentCaptor<IPlacesDataSource.GetPlaceCallback> getPlaceCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<ServiceContract.OnLocationLoaded> onLocationLoadedArgumentCaptor;

    private PlaceDetailPresenter presenter;


    @Before
    public void setUpPlaceDetailPresenter() {

        MockitoAnnotations.initMocks(this);

        presenter = new PlaceDetailPresenter(placesRepository, view, place.getPlaceID(), context);
    }

    /**
     * This test case will pass in case we comment service.getLocation(param) in PlaceDetailPresenter
     * The reason i think this test case failed because service is null.
     * I still looking for the solution.
     */
    @Test
    public void getPlaceFromRepositoryAndLoadIntoView() {
        presenter.start();

        verify(placesRepository).getPlace(eq(place.getPlaceID()), getPlaceCallbackArgumentCaptor.capture());
        getPlaceCallbackArgumentCaptor.getValue().onPlaceLoaded(place);


        verify(view).setData(place);

        verify(service).getLocation(eq(place.getPlaceName()),
                eq(place.getPlaceName() + place.getPlaceAddress()),
                onLocationLoadedArgumentCaptor.capture());

    }

    /**
     * This test case will pass in case we don't use Handler in PlaceDetailPresenter
     * The reason i think this test case failed because postDelay method() is not mocked
     * I still looking for the solution.
     */
    @Test
    public void deletePlace() {
        presenter.deletePlace();
        verify(placesRepository).deletePlace(place.getPlaceID());
        verify(view).showPlaces();
    }

    @Test
    public void openUpdateUI() {
        presenter.openEditPlaceUi();
        verify(view).showPlaceEditUi(place.getPlaceID());
    }

    @Test
    public void openDirectionActivity() {
        presenter.findRoute();

        verify(view).startDirectionActivity(place.getPlaceID());
    }

}
