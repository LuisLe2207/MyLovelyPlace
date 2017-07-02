package com.example.luisle.interviewtest.places;

import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.PlacesRepository;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Created by LuisLe on 7/2/2017.
 */

public class PlacesPresenterUnitTest {

    private static List<Place> places;

    @Mock
    private PlacesRepository placesRepository;

    @Mock
    private PlacesContract.View view;

    @Captor
    private ArgumentCaptor<IPlacesDataSource.LoadPlacesCallback> loadPlacesCallbackArgumentCaptor;

    private PlacesPresenter placesPresenter;

    @Before
    public void setupPlacesPresenter() {
        MockitoAnnotations.initMocks(this);

        placesPresenter = new PlacesPresenter(placesRepository, view);

        places = Lists.newArrayList(
                new Place("1", "Du Mien Cofee", "Go Vap District", "Lots of trees", null),
                new Place("2", "Starbuck coffe", "District 1", "Luxury ", null)
        );
    }

    @Test
    public void loadPlacesFromRepositoryAndLoadIntoView() {
        placesPresenter.loadPlaces();

        verify(placesRepository).getPlaces(loadPlacesCallbackArgumentCaptor.capture());
        loadPlacesCallbackArgumentCaptor.getValue().onPlacesLoaded(places);
        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).showPlaces(listArgumentCaptor.capture());
        assertTrue(listArgumentCaptor.getValue().size() == 2);

    }

    @Test
    public void clickOnFab_ShowAddPlaceUI() {
        placesPresenter.addNewPlace();

        verify(view).showAddPlaceUi();
    }
}
