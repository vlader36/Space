package com.hkv.melon.space;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.caverock.androidsvg.SVG;
import com.hkv.melon.space.Parsers.ParceCountryFull;
import com.hkv.melon.space.structures.CountryFull;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConcreteCountry extends AppCompatActivity{
    TextView region;
    TextView country;
    TextView capital;
    TextView timeZone;
    TextView currencies;
    TextView languages;
    TextView population;
    TextView area;
    ImageView flagImage;
    ParceCountryFull parser = new ParceCountryFull();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_view);
        CountryFull countryFull = (CountryFull)getIntent().getParcelableExtra(CountryFull.class.getCanonicalName());

        region = (TextView) findViewById(R.id.tvRegionCountry);
        country = (TextView) findViewById(R.id.tvNameCountry);
        capital = (TextView) findViewById(R.id.tvCapital);
        languages = (TextView) findViewById(R.id.tvLanguage);
        timeZone = (TextView) findViewById(R.id.tvTime);
        currencies = (TextView) findViewById(R.id.tvMoney);
        population = (TextView) findViewById(R.id.tvPeoples);
        flagImage = (ImageView) findViewById(R.id.ivFlagCountry);
        area = (TextView)findViewById(R.id.tvArea);

        region.setText(countryFull.getRegion());
        country.setText(countryFull.getCountry());
        capital.setText(countryFull.getCapital());
        languages.setText(countryFull.language);
        timeZone.setText(countryFull.getTimezone());
        currencies.setText(countryFull.getCurrencies());
        population.setText(Integer.toString(countryFull.getPopulation()) + " peoples");
        area.setText(Integer.toString(countryFull.getArea()) + " sq.km");
        DownladImage downladImage = new DownladImage(countryFull.getFlag());
        downladImage.execute();
        flagImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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

        }
        return null;
    }

    public class DownladImage extends AsyncTask<String, Void, Drawable> {
        String link;
        public DownladImage(String link){
            this.link = link;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... params) {
            return getImage(link);
        }

        @Override
        protected void onProgressUpdate(Void... param){
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            flagImage.setImageDrawable(result);
        }
    }
}
