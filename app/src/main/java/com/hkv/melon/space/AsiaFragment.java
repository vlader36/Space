package com.hkv.melon.space;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkv.melon.space.Parsers.ParceCountry;
import com.hkv.melon.space.Parsers.ParceCountryFull;
import com.hkv.melon.space.net.WebWork;
import com.hkv.melon.space.structures.CountryFull;
import com.hkv.melon.space.structures.County;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AsiaFragment extends Fragment implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView flag;
    private FloatingActionButton fab;
    private AsiaFragment.DownladCountries downloadMaterials;

    public AsiaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.asia_fragment,
                container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerAsia);
        flag = (ImageView) view.findViewById(R.id.ivFlagAsia);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                List<County> searchResult = new ArrayList<>();
                downloadMaterials = new AsiaFragment.DownladCountries(searchResult);
                downloadMaterials.execute("asia");

                mLayoutManager = new LinearLayoutManager(this.getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                break;
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<AsiaFragment.RecyclerAdapter.ViewHolder>{
        private List<County> countries;

        // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
        // отдельного пункта списка
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // наш пункт состоит только из одного TextView
            private TextView country;
            private TextView capital;
            private TextView region;
            private ImageView image;
            private CardView card;


            public ViewHolder(View v) {
                super(v);
                country = (TextView) v.findViewById(R.id.country);
                image = (ImageView) v.findViewById(R.id.flag);
                capital = (TextView) v.findViewById(R.id.capital);
                region = (TextView) v.findViewById(R.id.region);
                card = (CardView) v.findViewById(R.id.card);

                card.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (v.getId()){
                        case R.id.card:
                            CountryFull countryFull = null;
                            AsiaFragment.DownladCountry downloadCountry = new AsiaFragment.DownladCountry(countryFull);
                            downloadCountry.execute(capital.getText().toString());
                            try {
                                countryFull = downloadCountry.get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                            if (countryFull != null) {
                                Intent intent = new Intent(getContext(), ConcreteCountry.class);
                                intent.putExtra(countryFull.getClass().getCanonicalName(), countryFull);
                                startActivity(intent);
                            }
                            else
                                showSnackBar(getActivity(), "Network error. Please check your connection.");
                    }
                }
            }
        }

        // Конструктор
        public RecyclerAdapter(List<County> countries) {
            this.countries = countries;
        }

        // Создает новые views (вызывается layout manager-ом)
        @Override
        public AsiaFragment.RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content, parent, false);
            // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

            v.setBackgroundResource(R.drawable.cardview);
            AsiaFragment.RecyclerAdapter.ViewHolder vh = new AsiaFragment.RecyclerAdapter.ViewHolder(v);
            return vh;
        }

        // Заменяет контент отдельного view (вызывается layout manager-ом)
        @Override
        public void onBindViewHolder(AsiaFragment.RecyclerAdapter.ViewHolder holder, int position) {
            holder.country.setText(countries.get(position).getCountry());
            holder.capital.setText(countries.get(position).getCapital());
            holder.region.setText(countries.get(position).getRegion());
            holder.image.setImageDrawable(countries.get(position).getFlag());
            holder.image.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // Возвращает размер данных (вызывается layout manager-ом)
        @Override
        public int getItemCount() { return countries.size(); }
    }

    public class DownladCountry extends AsyncTask<String, Void, CountryFull> {
        CountryFull countryFull;
        ParceCountryFull parceCountry = new ParceCountryFull();
        String responce = null;

        public DownladCountry(CountryFull toUpdate){
            this.countryFull = toUpdate;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected CountryFull doInBackground(String... params) {
            responce = WebWork.connect(params[0], 1);
            if (responce == null) {
                showSnackBar(getActivity(), "Network error. Please check your connection.");
                return null;
            }
            countryFull = parceCountry.parse(responce);
            return countryFull;
        }

        @Override
        protected void onProgressUpdate(Void... param){
        }

        @Override
        protected void onPostExecute(CountryFull result) {
            super.onPostExecute(result);
        }
    }

    public class DownladCountries extends AsyncTask<String, Void, List<County>> {
        List<County> allCountries;
        ParceCountry parceCountry = new ParceCountry();
        String responce = null;

        public DownladCountries(List<County> toUpdate){
            this.allCountries = toUpdate;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            fab.setImageResource(R.drawable.process);
        }

        @Override
        protected List<County> doInBackground(String... params) {
            responce = WebWork.connect(params[0], 0);
            if (responce == null)
                responce = WebWork.connect(params[0], 1);
            if (responce == null)
                responce = WebWork.connect(params[0], 2);
            if (responce == null)
                return null;


            allCountries = parceCountry.parse(responce);
            return allCountries;
        }

        @Override
        protected void onProgressUpdate(Void... param){
        }

        @Override
        protected void onPostExecute(List<County> result) {
            super.onPostExecute(result);
            if (result != null) {
                super.onPostExecute(result);
                mAdapter = new AsiaFragment.RecyclerAdapter(result);
                mRecyclerView.setAdapter(mAdapter);
                fab.setImageResource(R.drawable.done);
            }
            else{
                showSnackBar(getActivity(), "Network error. Please check your connection.");
                fab.setImageResource(R.drawable.download);
            }
        }
    }
    public void showSnackBar(Activity activity, String message){
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}