package g1736229.elderlyui;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by jaspreet on 18/10/17.
 */

public class ContactInfo implements Serializable {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private Bitmap picture;

    public ContactInfo(String id, String name, String phoneNumber, String email, Bitmap picture) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.picture = picture;
    }

    public ContactInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactInfo createSerialisableCopy() {
        return new ContactInfo(id, name, phoneNumber, email, null);
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
