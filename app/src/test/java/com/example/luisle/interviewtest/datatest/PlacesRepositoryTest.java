package com.example.luisle.interviewtest.datatest;

import android.content.Context;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by LuisLe on 6/27/2017.
 */

public class PlacesRepositoryTest {

    private PlacesRepository placesRepository;

    private static List<Place> places = Lists.newArrayList(
            new Place("1", "Du Mien Cofee", "Go Vap District", "Lots of trees", null),
            new Place("2", "Starbuck coffe", "District 1", "Luxury ", null)
    );

    private static Place testPlace = new Place("3", "Phuc Long", "Phu Nhuan District", "Bad service", null);

    @Mock
    private IPlacesDataSource iPlacesDataSource;

    @Mock
    private IPlacesDataSource.LoadPlacesCallback loadPlacesCallback;

    @Mock
    private IPlacesDataSource.GetPlaceCallback getPlaceCallback;

    @Captor
    private ArgumentCaptor<IPlacesDataSource.LoadPlacesCallback> loadPlacesCallbackCaptor;

    @Captor
    private ArgumentCaptor<IPlacesDataSource.GetPlaceCallback> getPlaceCallbackCaptor;

    private Context context;

    @Before
    public void setUpRepository() {
        MockitoAnnotations.initMocks(this);
        placesRepository = new PlacesRepository(iPlacesDataSource);
        addTestData();
    }

    @Test
    public void savePlaceTest() throws Exception{
        Place place = new Place("3", "Phuc Long", "Phu Nhuan District", "Good service", null);
        placesRepository.addPlace(place);

        verify(iPlacesDataSource).addPlace(place);

    }

    @Test
    public void getPlacesTest() {
        placesRepository.getPlaces(loadPlacesCallback);
        verify(iPlacesDataSource).getPlaces(any(IPlacesDataSource.LoadPlacesCallback.class));
    }

    @Test
    public void getPlaceTest() {
        placesRepository.getPlace("1", getPlaceCallback);

        verify(iPlacesDataSource).getPlace(eq("1"), any(IPlacesDataSource.GetPlaceCallback.class));
    }

    @Test
    public void updatePlaceTest() {
        placesRepository.updatePlace(testPlace);

        verify(iPlacesDataSource).updatePlace(testPlace);
    }

    @Test
    public void deletePlace() {
        placesRepository.deletePlace(testPlace.getPlaceID());

        verify(iPlacesDataSource).deletePlace(testPlace.getPlaceID());
    }

    @Test
    public void getPlacesWithDataSourceAvailable() {
        placesRepository.getPlaces(loadPlacesCallback);

        setPlacesAvailable(iPlacesDataSource, places);

        verify(loadPlacesCallback).onPlacesLoaded(places);
    }

    @Test
    public void getPlacesWithDataSourceNotAvailable() {
        placesRepository.getPlaces(loadPlacesCallback);

        setPlacesNotAvailable(iPlacesDataSource);

        verify(loadPlacesCallback).onDataNotAvailable();
    }

    @Test
    public void getPlaceWithDataSourceAvailable() {

        placesRepository.getPlace(testPlace.getPlaceID(), getPlaceCallback);

        setPlaceAvailable(iPlacesDataSource, testPlace);

        verify(getPlaceCallback).onPlaceLoaded(testPlace);
    }

    @Test
    public void getPlaceWithDataSourceNotAvailable() {

        placesRepository.getPlace(testPlace.getPlaceID(), getPlaceCallback);

        setPlaceNotAvailable(iPlacesDataSource, testPlace.getPlaceID());

        verify(getPlaceCallback).onDataNotAvailable();
    }

    private void addTestData() {
        for (Place place : places) {
            placesRepository.addPlace(place);
        }
    }

    private void setPlacesAvailable(IPlacesDataSource iPlacesDataSource, List<Place> places) {
        verify(iPlacesDataSource).getPlaces(loadPlacesCallbackCaptor.capture());
        loadPlacesCallbackCaptor.getValue().onPlacesLoaded(places);
    }

    private void setPlacesNotAvailable(IPlacesDataSource iPlacesDataSource) {
        verify(iPlacesDataSource).getPlaces(loadPlacesCallbackCaptor.capture());
        loadPlacesCallbackCaptor.getValue().onDataNotAvailable();
    }

    private void setPlaceNotAvailable(IPlacesDataSource iPlacesDataSource, String placeID) {
        verify(iPlacesDataSource).getPlace(eq(placeID), getPlaceCallbackCaptor.capture());
        getPlaceCallbackCaptor.getValue().onDataNotAvailable();
    }

    private void setPlaceAvailable(IPlacesDataSource iPlacesDataSource, Place place) {
        verify(iPlacesDataSource).getPlace(eq(place.getPlaceID()), getPlaceCallbackCaptor.capture());
        getPlaceCallbackCaptor.getValue().onPlaceLoaded(place);
    }

}
