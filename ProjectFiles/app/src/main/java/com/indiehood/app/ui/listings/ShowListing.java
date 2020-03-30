package com.indiehood.app.ui.listings;

public class ShowListing {
    private String mBandName;
    private String mVenue;
    private String mTime;
    private Boolean mUserInterested;
    private Integer mNumberInterested;
    private Boolean mUserFavoritedBand;
    private Boolean mUserFavoritedVenue;
    //private String mDate;     //this will be converted into an image displayed rather than just an image of a calendar
    public ShowListing(String band, String venue, String time) {
        this.mBandName = band;
        this.mVenue = venue;
        this.mTime = time;
        this.mNumberInterested = 0; //determine number interested based on user's who have liked a certain listing
        this.mUserInterested = false;
        this.mUserFavoritedVenue = false;
        this.mUserFavoritedBand = false;
        if (band == "Banana Rays"){             //for dummy data
            this.mUserFavoritedBand = true;
        }
        if (venue == "Red Shed"){
            this.mUserFavoritedVenue = true;
        }
        //grab interested and favorite true/false information from database based on the user's device ID
    }

    public String getBandName() {
        return mBandName;
    }
    public String getVenue() {
        return mVenue;
    }

    public String getTime() {
        return mTime;
    }

    public Boolean getUserInterested() {
        return mUserInterested;
    }

    public String getNumberInterested () {
        if (getUserInterested()){
            return ("you and x other people are interested");
        }
        else {
            return "x other people are interested";
        }
    }

    public Boolean getBandFavorite() {
        return mUserFavoritedBand;
    }

    public Boolean getVenueFavorite() {
        return mUserFavoritedVenue;
    }
}
