package com.indiehood.app.ui.favorites;

import android.media.Image;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public ArrayList<Artist> loadArtists() {
        final String TAG = "loadArtists";
        //final ArrayList<Artist> artists = new ArrayList<>();
        final ArrayList<Artist> artists = readData(new FirestoreCallback() {
            @Override
            public void onCallback(ArrayList<Artist> list) {
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "artist: " + list.get(i).getArtistName());
                }
            }
        });

        return artists;
    }

    private ArrayList<Artist> readData(final FirestoreCallback cb) {
        final ArrayList<Artist> artists = new ArrayList<>();
        // for logging purposes
        final String TAG = "readData";
        ArtistCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // iterate through each document in collection
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Artist artist = document.toObject(Artist.class);
                                artists.add(artist);
                            }
                            cb.onCallback(artists);
                        }
                        else {
                            Log.d(TAG, "Error getting documents");
                        }
                    }
                });

        return artists;
    }

    private interface FirestoreCallback {
        void onCallback(ArrayList<Artist> list);
    }

    // TODO put into Favorite class
    public ArrayList<Artist> createFavoritesList(ArrayList<Artist> artists) {
        ArrayList<Artist> favorites = new ArrayList<>();
        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getFavorited()) {
                favorites.add(artists.get(i));
            }
        }

        return favorites;
    }

    // TODO in all setters allow for update to Firestorm
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
