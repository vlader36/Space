package com.hkv.melon.space.structures;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by Melon on 09.06.2017.
 */

public class County {
    String country;
    String capital;
    String region;
    Drawable flag;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Drawable getFlag() {
        return flag;
    }

    public void setFlag(Drawable flag) {
        this.flag = flag;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
