package com.indiehood.app.ui.favorites;

import android.media.Image;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Artist {
    private FirebaseDatabase myDatabase;
    private DatabaseReference dbRef;
    private String name;
    private boolean favorited;
    private String bio;
    private int rating;
    private String social1;
    private String social2;
    private String media1; // link to streaming service
    private String media2;
    private Image profilePicture;
    private Image coverPhoto;
    // TODO add more data fields

    public Artist() {
        // default constructor to pass Band object to Firebase
    }

    // TODO find out how to pass in proPic and coverPhoto to constructor
    public Artist (String name, String bio, int rating, String social1, String social2,
                   String media1, String media2) {
        this.name = name;
        this.bio = bio;
        this.rating = rating;
        this.social1 = social1;
        this.social2 = social2;
        this.media1 = media1;
        this.media2 = media2;
    }

    public void writeNewArtist(Artist newArtist) {
        myDatabase = FirebaseDatabase.getInstance();
        dbRef = myDatabase.getReference().child("/ArtistCollection");
        dbRef.child(newArtist.getName()).setValue(newArtist);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setFavorited(boolean status) {
        this.favorited = status;
    }

    public Boolean getFavorited() {
        return this.favorited;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setSocial1(String social1) {
        this.social1 = social1;
    }

    public String getSocial1() {
        return social1;
    }

    public void setSocial2(String social2) {
        this.social2 = social2;
    }

    public String getSocial2() {
        return social2;
    }

    public void setMedia1(String media1) {
        this.media1 = media1;
    }

    public String getMedia1() {
        return media1;
    }

    public void setMedia2(String media2) {
        this.media2 = media2;
    }

    public String getMedia2() {
        return media2;
    }

    // TODO remove this code and use list class?
    public static ArrayList<Artist> createArtistList(int numArtists) {
        ArrayList<Artist> artists = new ArrayList<>();
        for (int i = 0; i < numArtists; i++) {
            artists.add(new Artist());
            // artists.get(i).setName("Phish");
            // TODO REMOVE THIS IF STMT
            if (i % 3 == 0) artists.get(i).setFavorited(true);
        }

        return artists;
    }
    // TODO create favorites class; remove this code
    public static ArrayList<Artist> createFavoritesList(ArrayList<Artist> artists) {
        ArrayList<Artist> favorites = new ArrayList<>();
        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getFavorited()) {
                favorites.add(artists.get(i));
            }
        }

        return favorites;
    }
}
