package com.apk.firebase;

public class Track {

    private String trackId;
    private String trackNama;
    private int trackRating;

    public Track(){

    }
    public Track(String trackId, String trackNama, int trackRating) {
        this.trackId = trackId;
        this.trackNama = trackNama;
        this.trackRating = trackRating;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getTrackNama() {
        return trackNama;
    }

    public int getTrackRating() {
        return trackRating;
    }
}
