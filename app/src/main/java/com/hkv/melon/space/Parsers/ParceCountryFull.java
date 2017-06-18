package com.hkv.melon.space.Parsers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;

import com.caverock.androidsvg.SVG;
import com.hkv.melon.space.interfaces.IParser;
import com.hkv.melon.space.structures.CountryFull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParceCountryFull implements IParser <CountryFull, String> {
    public CountryFull parse(String jsonString) {
        CountryFull countryFull = new CountryFull();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                String language = "", currencies = "";
                JSONArray jsboof = null;
                JSONObject countryInfo = jsonArray.getJSONObject(i);
                countryFull.setRegion(countryInfo.getString("region"));
                countryFull.setCountry(countryInfo.getString("name"));
                countryFull.setCapital(countryInfo.getString("capital"));
                countryFull.setFlag(countryInfo.getString("flag"));

                jsboof = countryInfo.getJSONArray("languages");
                for (int j = 0; j < jsboof.length(); j++) {
                    JSONObject lang = jsboof.getJSONObject(j);
                    language += lang.getString("name");
                    language += ",";
                }
                language = language.substring(0, language.length() - 1);
                countryFull.setLanguage(language);

                countryFull.setTimezone(countryInfo.getString("timezones"));
                countryFull.setArea(countryInfo.getInt("area"));

                jsboof = countryInfo.getJSONArray("currencies");
                for (int j = 0; j < jsboof.length(); j++) {
                    JSONObject lang = jsboof.getJSONObject(j);
                    currencies += lang.getString("name");
                    currencies += ",";
                }
                currencies = currencies.substring(0, currencies.length() - 1);
                countryFull.setCurrencies(currencies);

                countryFull.setPopulation(countryInfo.getInt("population"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return countryFull;
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
