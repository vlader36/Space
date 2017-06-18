package com.hkv.melon.space.structures;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class CountryFull implements Parcelable {
    private String region, country, capital, timezone, currencies;
    private int population, area;
    private String flag;
    public String language;

    public CountryFull(){}

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }


    public CountryFull (String flag, String country, String region, String capital,
                        int population, String language, int area, String timezone,
                        String currencies){
        this.language = language;
        this.timezone = timezone;
        this.currencies = currencies;
        this.population = population;
        this.area = area;
        this.region = region;
        this.capital = capital;
        this.country = country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(region);
        dest.writeString(country);
        dest.writeString(capital);
        dest.writeInt(population);
        dest.writeInt(area);
        dest.writeString(language);
        dest.writeString(timezone);
        dest.writeString(currencies);
        dest.writeString(flag);
    }

    public static final Creator<CountryFull> CREATOR = new Creator<CountryFull>() {
        // распаковываем объект из Parcel
        public CountryFull createFromParcel(Parcel in) {
            return new CountryFull(in);
        }

        @Override
        public CountryFull[] newArray(int size) {
            return new CountryFull[0];
        }
    };

    private CountryFull(Parcel parcel) {
        region = parcel.readString();
        country = parcel.readString();
        capital = parcel.readString();
        population = parcel.readInt();
        area = parcel.readInt();
        language = parcel.readString();
        timezone = parcel.readString();
        currencies = parcel.readString();
        flag = parcel.readString();
    }
}
