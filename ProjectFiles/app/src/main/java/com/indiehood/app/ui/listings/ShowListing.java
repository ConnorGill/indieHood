package com.indiehood.app.ui.listings;

import com.google.type.Date;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ShowListing implements Serializable {
    private String bandName;
    private String venueName;
    private String day;
    private String time;
    private Double price;
    private String address;
    private String description;
    private Boolean userInterested;
    private Integer numberInterested;
  //  private Boolean mUserFavoritedBand;
  //  private Boolean mUserFavoritedVenue;
    //private String mDate;     //this will be converted into an image displayed rather than just an image of a calendar

    public ShowListing() {
        //emptyConstructor needed
    }

    public ShowListing(String band, String venue, String day) {
        this.bandName = band;
        this.venueName = venue;
        this.day = day;
        /*
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
        //grab interested and favorite true/false information from database based on the user's device ID */
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUserInterested() {
        return userInterested;
    }

    public void setUserInterested(Boolean userInterested) {
        this.userInterested = userInterested;
    }

    public Integer getNumberInterested() {
        return numberInterested;
    }

    public void setNumberInterested(Integer numberInterested) {
        this.numberInterested = numberInterested;
    }
    /*
    End Setters/Getters
     */

    public String getStringifiedPrice() {
        Double price = getPrice();
        if (price == 0.0) {
            return "FREE";
        }
        else {
            NumberFormat formatter = new DecimalFormat("#0.00");
            String formatted = formatter.format(price);
            return "$" + formatted;
        }
    }

    public String getInterestedText() {
        if (getNumberInterested() == 1) {
            return getNumberInterested() + " person is interested";
        }
        else {
            return getNumberInterested() + " people are interested";
        }
    }

    public String getAdjustedTime() {
        String time = getTime(); //format= 24 clock HH:MM
        String[] divide = time.split(":");
        if (divide.length == 2) {
            int hours = Integer.parseInt(divide[0]);
            String designation;
            if (hours >= 12) {
                designation = "PM";
                if (hours != 12) {
                    hours = hours - 12;
                }
            } else {
                designation = "AM";
            }
            return hours + ":" + divide[1] + " " + designation;
        }
        else {
            return time;
        }
    }

    public String getAdjustedDate() {
        String date = getDay();     //format = yyyy-MM-dd
        String[] intervals = date.split("-");
        if (intervals.length == 3) {
            String month = intervals[1];
            int day = Integer.parseInt(intervals[2]);
            switch (month) {
                case "01":
                    month = "January";
                    break;
                case "02":
                    month = "February";
                    break;
                case "03":
                    month = "March";
                    break;
                case "04":
                    month = "April";
                    break;
                case "05":
                    month = "May";
                    break;
                case "06":
                    month = "June";
                    break;
                case "07":
                    month = "July";
                    break;
                case "08":
                    month = "August";
                    break;
                case "09":
                    month = "September";
                    break;
                case "10":
                    month = "October";
                    break;
                case "11":
                    month = "November";
                    break;
                case "12":
                    month = "December";
                    break;
            }
            String modifier;
            switch(day) {
                case 1:
                    modifier = "st";
                    break;
                case 2:
                    modifier = "nd";
                    break;
                case 3:
                    modifier = "rd";
                    break;
                default:
                    modifier = "th";
                    break;
            }
            return month + " " + day + modifier;
        }
        else {
            return date;
        }
    }

    public Boolean getBandFavorite() {
        //call overarching user class and pass getBandName() into checkFavorited() method
        //for now...
        return false;
    }

}
