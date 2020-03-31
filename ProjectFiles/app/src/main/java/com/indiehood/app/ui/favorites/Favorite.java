package com.indiehood.app.ui.favorites;

import android.media.Image;

import java.util.ArrayList;

public class Favorite extends Artist {
    /* private String name;
    private boolean favorited;
    private String bio;
    private int rating;
    private String social1;
    private String social2;
    private String media1; // link to streaming service
    private String media2;
    private Image profilePicture;
    private Image coverPhoto; */

    public Favorite() {
        // default constructor in case we use firebase
    }

    // TODO: how to create a list of favorites that were once artists?
    /*public static ArrayList<Favorite> createFavoritesList(ArrayList<? extends Artist> artists) {
        ArrayList<Favorite> favorites = new ArrayList<>();
        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getFavorited()) {
                favorites.add((Favorite) artists.get(i));
            }
        }

        return favorites;
    }*/

    // TODO add method to unfavorite artists from FavoritesView

}
