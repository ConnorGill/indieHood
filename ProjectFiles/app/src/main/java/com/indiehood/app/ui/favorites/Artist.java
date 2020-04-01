package com.indiehood.app.ui.favorites;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;

public class Artist {
    // get instance of current database TODO maybe move from Artist?
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // create reference directly to ArtistCollection
    private CollectionReference ArtistCollection = this.db.collection("ArtistCollection");
    private String artistName;
    private boolean favorited;
    private String bio;
    private int rating;
    private String social1;
    private String social2;
    private String media1; // link to streaming service
    private String media2;
    private Image profilePicture;
    private Image coverPhoto;

    public Artist() {
        // default constructor to pass Artist object to Firebase
    }

    // TODO find out how to pass in proPic and coverPhoto to constructor
    public Artist (String name, String bio, boolean fav, int rating, String social1, String social2,
                   String media1, String media2) {
        this.artistName = name;
        this.bio = bio;
        this.favorited = fav;
        this.rating = rating;
        this.social1 = social1;
        this.social2 = social2;
        this.media1 = media1;
        this.media2 = media2;
    }

    public void writeNewArtist(final Artist newArtist) {
        // for logging purposes
        final String TAG = "writeNewArtist";

        ArtistCollection.document(newArtist.getArtistName()).set(newArtist)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO change to toasts in production
                        Log.d(TAG, e.toString());
                    }
                });
    }

    // TODO may or may not need this... all reading capabilities are in FavoritesFragment
    /*public ArrayList<Artist> readArtists() {
        // for logging purposes
        final String TAG = "readArtists";
        final ArrayList<Artist> artists = new ArrayList<>();

        return artists;
    }*/

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return this.artistName;
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
}
