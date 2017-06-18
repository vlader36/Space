package com.hkv.melon.space.Parsers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;

import com.caverock.androidsvg.SVG;
import com.hkv.melon.space.interfaces.IParser;
import com.hkv.melon.space.structures.County;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ParceCountry implements IParser <List<County>, String>{
    public List<County> parse(String jsonString) {
        List<County> countries = new ArrayList<>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryInfo = jsonArray.getJSONObject(i);
                County country = new County();

                final String link = countryInfo.getString("flag");
                country.setCountry(countryInfo.getString("name"));
                country.setCapital(countryInfo.getString("capital"));
                country.setRegion(countryInfo.getString("region"));
                country.setFlag(getImage(link));

                countries.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return countries;
    }

    public Drawable getImage(String link) {
        Drawable result = null;

        try {
            final URL url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            SVG svg = SVG.getFromInputStream(inputStream);
            if (svg.getDocumentWidth() != -1){
                PictureDrawable picture = new PictureDrawable(svg.renderToPicture());
                result = picture;

            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
