package bricout.maxence.tpone.contacts;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Contact implements Serializable {
    private String firstName;
    private String lastName;
    private boolean favorite;
    private String email;
    private String mobile;
    private String city;

    public Contact(String firstName, String lastName, boolean favorite, String email, String mobile, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.favorite = favorite;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCity() {
        return city;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
