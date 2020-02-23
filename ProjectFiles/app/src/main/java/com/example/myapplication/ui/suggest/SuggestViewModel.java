package com.example.myapplication.ui.suggest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuggestViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SuggestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Have a venue you'd like to see listed on indieHood? Tell us about it below!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}