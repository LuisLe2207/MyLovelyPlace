package com.example.luisle.interviewtest.addeditplace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.luisle.interviewtest.MyApp;
import com.example.luisle.interviewtest.R;
import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.utils.AppUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.example.luisle.interviewtest.utils.AppUtils.DETAIL_FRAGMENT_TAG;
import static com.example.luisle.interviewtest.utils.AppUtils.PLACE_FRAGMENT_TAG;

/**
 * Created by LuisLe on 6/28/2017.
 */

public class AddEditPlaceFragment extends Fragment implements AddEditPlaceContract.View {

    @Inject
    AddEditPlacePresenter addEditPlacePresenter;

    private AddEditPlaceContract.Presenter presenter;
    private AppUtils.Communicator communicator;

    @BindView(R.id.imgAddEditFrag)
    RoundedImageView placeImage;

    @BindView(R.id.edtAddEditFrag_PlaceName)
    EditText edtPlaceName;

    @BindView(R.id.edtAddEditFrag_PlaceAddress)
    EditText edtPlaceAddress;

    @BindView(R.id.edtAddEditFrag_PlaceDescription)
    EditText edtPlaceDescription;

    @BindString(R.string.error_field_can_not_be_empty)
    String error;

    private ProgressDialog progressDialog;

    public static final int REQUEST_IMAGE_CAPTURE = 1;


    public static AddEditPlaceFragment newInstance() {
        return new AddEditPlaceFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (AppUtils.Communicator) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getResources().getString(R.string.txt_saving_dialog));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);

        DaggerAddEditPlacePresenterComponent.builder()
                .addEditPlacePresenterModule(new AddEditPlacePresenterModule(this, null))
                .placesRepositoryComponent(((MyApp) getActivity().getApplication()).getRepositoryComponent()).build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_place_add_edit, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data !=null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            placeImage.setImageBitmap(imageBitmap);
        }
    }

    @OnClick(R.id.imgAddEditFrag)
    public void takeImage(View view) {
        presenter.openCamera();
    }

    @OnClick(R.id.btnAddEditFrag_Save)
    public void save(View view) {
        String placeName = edtPlaceName.getText().toString().trim();
        String placeAddress = edtPlaceAddress.getText().toString().trim();
        String placeDescription = edtPlaceDescription.getText().toString().trim();

        switch (Place.validate(placeName, placeAddress, placeDescription)) {
            case 1:
                edtPlaceName.setError(error);
                break;
            case 2:
                edtPlaceAddress.setError(error);
                break;
            case 3:
                edtPlaceDescription.setError(error);
                break;
            case 0:
                byte[] placeImageByte = AppUtils.imageViewToByte(placeImage);
                presenter.save(placeName, placeAddress, placeDescription, placeImageByte);
                break;
        }
    }

    @Override
    public void setPresenter(AddEditPlaceContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgressDlg() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDlg() {
        progressDialog.hide();
    }

    @Override
    public void redirectUI(boolean result) {
        Fragment resultFragment;
        String tag;
        if (result) {
            resultFragment =  getActivity().getSupportFragmentManager().findFragmentByTag(PLACE_FRAGMENT_TAG);
            tag = PLACE_FRAGMENT_TAG;
        } else {
            resultFragment =  getActivity().getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
            tag = DETAIL_FRAGMENT_TAG;
        }
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        transaction.replace(R.id.mainAct_FrameLayout, resultFragment, tag);
        if (!result) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        resultFragment.onResume();
        transaction.commit();
    }

    @Override
    public void setData(@NonNull Place place) {
        byte[] placeImageByte = place.getPlaceImage();
        if (placeImageByte != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(placeImageByte, 0, placeImageByte.length);
            placeImage.setImageBitmap(bitmap);
        } else {
            placeImage.setImageResource(R.mipmap.ic_no_image);
        }

        edtPlaceName.setText(place.getPlaceName());
        edtPlaceAddress.setText(place.getPlaceAddress());
        edtPlaceDescription.setText(place.getPlaceDescription());
    }

    @Override
    public void showCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void updateActionBarTitle(boolean isAdd) {
        if (isAdd) {
            communicator.setActionBarTitle(getContext().getResources().getString(R.string.action_bar_title_add));
        } else {
            communicator.setActionBarTitle(getContext().getResources().getString(R.string.action_bar_title_modify));
        }
    }

    @Override
    public void showNoPlaceAvailableAlert() {

    }
}
