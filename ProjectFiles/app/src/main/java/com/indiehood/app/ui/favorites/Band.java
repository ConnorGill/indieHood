package com.indiehood.app.ui.favorites;

import android.media.Image;

import java.util.ArrayList;

public class Band {
    private String bandName;
    private boolean favorited;
    private String bandBio;
    private String city;
    private String state;
    private String social_media_1;
    private String social_media_2;
    private String streaming_link_1;
    private String streaming_link_2;
    private Image bandPicture;
    private Image headerPhoto;
    // TODO add more data fields

    public Band(String name) {
        bandName = name;
        favorited = false;
        bandBio = "";
    }

    public String getBandName() {
        return this.bandName;
    }

    public Boolean isFavorited() {
        return this.favorited;
    }

    public void setFavorited(boolean status) {
        this.favorited = status;
    }

    public void setBandName(String name) {
        this.bandName = name;
    }

    public String getBandBio() {
        return bandBio;
    }

    public void setBandBio(String bandBio) {
        this.bandBio = bandBio;
    }
    // TODO remove this code and create list class
    public static ArrayList<Band> createBandList(int numBands) {
        ArrayList<Band> bands = new ArrayList<>();
        for (int i = 0; i < numBands; i++) {
            bands.add(new Band("This is an example band"));
            // this is test code
            // TODO REMOVE THIS IF STMT
            if (i % 3 == 0) bands.get(i).setFavorited(true);
        }

        return bands;
    }
    // TODO create favorites class; remove this code
    public static ArrayList<Band> createFavoritesList(ArrayList<Band> bands) {
        ArrayList<Band> favorites = new ArrayList<>();
        for (int i = 0; i < bands.size(); i++) {
            if (bands.get(i).isFavorited()) {
                favorites.add(bands.get(i));
            }
        }

        return favorites;
    }
}
