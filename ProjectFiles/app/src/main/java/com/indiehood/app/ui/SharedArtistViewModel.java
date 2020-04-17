package com.indiehood.app.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedArtistViewModel extends ViewModel {
    /*public static class FavoritesToArtist {
        private static MutableLiveData<String> artistPath = new MutableLiveData<>();
        public static void setArtistPath(String input) {
            artistPath.setValue(input);
        }
        public LiveData<String> getArtistPath() {
            return artistPath;
        }
    }*/
    private MutableLiveData<String> artistPath = new MutableLiveData<>();
    public void setArtistPath(String input) {
        artistPath.setValue(input);
    }
    public LiveData<String> getArtistPath() {
        return artistPath;
    }
}
