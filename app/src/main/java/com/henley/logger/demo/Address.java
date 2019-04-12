package com.henley.logger.demo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 地址信息
 *
 * @author Henley
 * @date 2017/7/24 17:49
 */
public class Address implements Parcelable {

    private String province;
    private String city;
    private String area;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.area);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.province = in.readString();
        this.city = in.readString();
        this.area = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
