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

    public void setArtistName(final String artistName) {
        // for logging
        final String TAG = "setArtistName";
        // get original artist name
        //final DocumentReference artistRef = ArtistCollection.document(this.getArtistName());
        // update artistName locally
        this.artistName = artistName;
        // update artistName on Firestore TODO IMPLEMENT
       /*db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                transaction.update(artistRef, "artistName", artistName);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });*/
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setFavorited(final boolean status) {
        // for logging
        final String TAG = "setFavorited";
        //final DocumentReference artistRef = ArtistCollection.document(this.getArtistName());
        this.favorited = status;
        /*db.runTransaction(new Transaction.Function<Void>() { TODO IMPLEMENT
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                transaction.update(artistRef, "favorited", status);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });*/
    }

    public Boolean getFavorited() {
        return this.favorited;
    }

    public void setRating(final int rating) {
        // for logging
        final String TAG = "setRating";
        //final DocumentReference artistRef = ArtistCollection.document(this.getArtistName());
        this.rating = rating;
        /*db.runTransaction(new Transaction.Function<Void>() { TODO IMPLEMENT
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                transaction.update(artistRef, "rating", rating);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });*/
    }

    public int getRating() {
        return this.rating;
    }

    public void setBio(final String bio) {
        // for logging
        final String TAG = "setBio";
        //final DocumentReference artistRef = ArtistCollection.document(this.getArtistName());
        this.bio = bio;
        /*db.runTransaction(new Transaction.Function<Void>() { TODO IMPLEMENT
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                //DocumentSnapshot snapshot = transaction.get(artistRef);
                transaction.update(artistRef, "bio", bio);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });*/
    }

    public String getBio() {
        return bio;
    }

    public void setSocial1(final String social1) {
        // for logging
        final String TAG = "setSocial1";
        //final DocumentReference artistRef = ArtistCollection.document(this.getArtistName());
        this.social1 = social1;
        /*db.runTransaction(new Transaction.Function<Void>() { TODO IMPLEMENT
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                //DocumentSnapshot snapshot = transaction.get(artistRef);
                transaction.update(artistRef, "social1", social1);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });*/
    }

    public String getSocial1() {
        return social1;
    }

    public void setSocial2(final String social2) {
        // for logging
        final String TAG = "setSocial2";
        //final DocumentReference artistRef = ArtistCollection.document(this.getArtistName());
        this.social2 = social2;
        /*db.runTransaction(new Transaction.Function<Void>() { TODO IMPLEMENT
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                //DocumentSnapshot snapshot = transaction.get(artistRef);
                transaction.update(artistRef, "social2", social2);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });*/
    }

    public String getSocial2() {
        return social2;
    }

    public void setMedia1(final String media1) {
        // for logging
        final String TAG = "setMedia1";
        //final DocumentReference artistRef = ArtistCollection.document(this.getArtistName());
        this.media1 = media1;
        /*db.runTransaction(new Transaction.Function<Void>() { TODO IMPLEMENT
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                //DocumentSnapshot snapshot = transaction.get(artistRef);
                transaction.update(artistRef, "media1", media1);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });*/
    }

    public String getMedia1() {
        return media1;
    }

    public void setMedia2(final String media2) {
        // for logging
        final String TAG = "setMedia2";
        //final DocumentReference artistRef = ArtistCollection.document(this.getArtistName());
        this.media2 = media2;
        /*db.runTransaction(new Transaction.Function<Void>() { TODO IMPLEMENT
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                //DocumentSnapshot snapshot = transaction.get(artistRef);
                transaction.update(artistRef, "media2", media2);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });*/
    }

    public String getMedia2() {
        return media2;
    }
}
