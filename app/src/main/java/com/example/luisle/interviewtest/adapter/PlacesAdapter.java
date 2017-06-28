package com.example.luisle.interviewtest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisle.interviewtest.R;
import com.example.luisle.interviewtest.data.Place;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LuisLe on 6/28/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {


    private List<Place> places;
    private LayoutInflater layoutInflater;
    private OnClickCallback callback;

    public PlacesAdapter(Context context, List<Place> places, OnClickCallback callback) {
        setPlaces(places);
        this.layoutInflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void replaceData(List<Place> places) {
        setPlaces(places);
        notifyDataSetChanged();
    }

    @Override
    public PlacesAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = layoutInflater.inflate(R.layout.row_place, parent, false);
        return new PlaceViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(PlacesAdapter.PlaceViewHolder holder, int position) {
        Place place = places.get(position);

        byte[] imgByte = place.getPlaceImage();
        if (imgByte != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            holder.placeImage.setImageBitmap(bitmap);
        } else {
            holder.placeImage.setImageResource(R.mipmap.ic_no_image);
        }

        holder.txtPlaceName.setText(place.getPlaceName());
        holder.txtPlaceAddress.setText(place.getPlaceAddress());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }


    class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.imgPlaceRow)
        ImageView placeImage;

        @BindView(R.id.txtPlaceRow_PlaceName)
        TextView txtPlaceName;

        @BindView(R.id.txtPlaceRow_PlaceAddress)
        TextView txtPlaceAddress;

        @BindView(R.id.ibtnPlaceRow_Direction)
        ImageButton iBtnGetDirection;

        public PlaceViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

            iBtnGetDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    Place place = places.get(position);
                    callback.startDirectionActivity(view, place);
                }
            });
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Place place = places.get(position);
            callback.showPlaceDetailUI(place.getPlaceID());
        }
    }

    public interface OnClickCallback {
        void showPlaceDetailUI(@NonNull String placeID);
        void startDirectionActivity(View v, Place place);
    }
}
