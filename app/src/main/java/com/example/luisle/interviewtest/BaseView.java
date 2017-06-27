package com.example.luisle.interviewtest;

/**
 * Created by LuisLe on 6/27/2017.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);


    // show or hide Progress Dialog
    interface ViewProgress {
        void showProgressDlg();
        void hideProgressDlg();
    }
}
