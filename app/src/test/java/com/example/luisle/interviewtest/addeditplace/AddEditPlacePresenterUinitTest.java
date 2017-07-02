package com.example.luisle.interviewtest.addeditplace;

import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.PlacesRepository;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by LuisLe on 7/2/2017.
 */

public class AddEditPlacePresenterUinitTest {

    private static Place place = new Place("1", "Du Mien Cofee", "Go Vap District", "Lots of trees", null);

    @Mock
    private PlacesRepository placesRepository;

    @Mock
    private AddEditPlaceContract.View view;

    @Captor
    private ArgumentCaptor<IPlacesDataSource.GetPlaceCallback> getPlaceCallbackArgumentCaptor;

    private AddEditPlacePresenter presenter;

    @Before
    public void setUpAddEditPlacePresenter() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveNewPlace() {
        presenter = new AddEditPlacePresenter(placesRepository, view, null);

        Place newPlace =  new Place("Starbuck coffee", "District 1", "Luxury ", null);

        presenter.save(newPlace.getPlaceName(), newPlace.getPlaceAddress(), newPlace.getPlaceDescription(), newPlace.getPlaceImage());

        verify(placesRepository).addPlace(any(Place.class));
        verify(view).redirectUI(true, null);

    }

    @Test
    public void updatePlace() {
        presenter = new AddEditPlacePresenter(placesRepository, view, place.getPlaceID());

        presenter.save(place.getPlaceName(), place.getPlaceAddress(), place.getPlaceDescription(), place.getPlaceImage());

        verify(placesRepository).updatePlace(any(Place.class));
        verify(view).redirectUI(false, place.getPlaceID());
    }

}
