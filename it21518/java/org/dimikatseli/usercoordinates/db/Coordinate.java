package org.dimikatseli.usercoordinates.db;

/**
 * Created by dimitra on 15/12/2017.
 */

public class Coordinate {
    private int id;
    private String userid;
    private double longitude;
    private double latitude;
    private String timestamp;

    public Coordinate(int id, String userid, double longitude, double latitude, String timestamp) {
        this.id = id;
        this.userid = userid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
    }

    public Coordinate(String userid, double longitude, double latitude, String timestamp) {
        this.id=-1;
        this.userid = userid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
    }

    public Coordinate(double longitude, double latitude, String timestamp) {
        this(DbHelper.ANONYMOUS_USER,longitude,latitude,timestamp);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Results\n" +
                "id=" + id +"\n"+
                "userid=" + userid  +"\n" +
                "longitude=" + longitude  +"\n" +
                "latitude=" + latitude + "\n"+
                "timestamp=" + timestamp  ;
    }
}