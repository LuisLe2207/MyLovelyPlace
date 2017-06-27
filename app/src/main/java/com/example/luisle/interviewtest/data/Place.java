package com.example.luisle.interviewtest.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by LuisLe on 6/27/2017.
 */

public class Place {

    @NonNull
    private String placeID;

    @NonNull
    private String placeName;

    @NonNull
    private String placeAddress;

    @NonNull
    private String placeDescription;

    @Nullable
    private byte[] placeImage;

    @NonNull
    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(@NonNull String placeID) {
        this.placeID = placeID;
    }

    @NonNull
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(@NonNull String placeName) {
        this.placeName = placeName;
    }

    @NonNull
    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(@NonNull String placeAddress) {
        this.placeAddress = placeAddress;
    }

    @NonNull
    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(@NonNull String placeDescription) {
        this.placeDescription = placeDescription;
    }

    @Nullable
    public byte[] getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(@Nullable byte[] placeImage) {
        this.placeImage = placeImage;
    }


    public Place(@NonNull String placeID,
                 @NonNull String placeName,
                 @NonNull String placeAddress,
                 @NonNull String placeDescription,
                 @Nullable byte[] placeImage) {
        this.placeID = placeID;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeDescription = placeDescription;
        this.placeImage = placeImage;
    }

    public Place(@NonNull String placeName, @NonNull String placeAddress, @NonNull String placeDescription, @Nullable byte[] placeImage) {
        this.placeID = UUID.randomUUID().toString();
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeDescription = placeDescription;
        this.placeImage = placeImage;
    }

    public Place(@NonNull String placeName, @NonNull String placeAddress, @NonNull String placeDescription) {
        this.placeID = UUID.randomUUID().toString();
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeDescription = placeDescription;
        this.placeImage = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Place place = (Place) obj;
        return (this.placeID.equals(place.placeID)
                && this.placeName.equals(place.placeName)
                && this.placeAddress.equals(place.placeAddress)
                && this.placeImage == place.placeImage
        );
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        if (placeImage != null) {
            hashcode = placeID.hashCode() * placeName.hashCode() * placeAddress.hashCode() * placeImage.hashCode();
        } else {
            hashcode = placeID.hashCode() * placeName.hashCode() * placeAddress.hashCode();
        }
        return hashcode;
    }
}
