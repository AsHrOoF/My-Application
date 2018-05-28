package com.example.android.sec_movieapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.sec_movieapp.Adapter.TrailerAdapter;
import com.example.android.sec_movieapp.DataBase.DataSource;
import com.example.android.sec_movieapp.Details.Model;
import com.example.android.sec_movieapp.Details.Movie;
import com.example.android.sec_movieapp.R;
import com.example.android.sec_movieapp.Utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class detail_Fragment extends Fragment {

    TextView textView4;
    TextView textView5;
    String author =null;
    String content =null;
    ListView ls;
    Button fav_bt;
    ImageButton image_bt;
    TrailerAdapter mForecastAdapter;
    String[] key;
    Model model=new Model();
    List<Model> fav_list=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        String Base_photo = "http://image.tmdb.org/t/p/w185/";
        String title_Details, poster_Details, overview_Details, voteaverage_Details, release_Details, id_Details ,type_Details ;

        ls = (ListView) rootView.findViewById(R.id.listview);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + key[i]));
                                          startActivity(intent);
                                      }
                                  }
        );
        image_bt=(ImageButton)rootView.findViewById(R.id.imagebutton_fav);

        fav_bt=(Button)rootView.findViewById(R.id.button_fav);

        fav_bt.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!fav_list.isEmpty()) {
                    if (fav_list.get(0).isFav()) {
                        image_bt.setImageResource(R.drawable.unfav_icon);
                        fav_list.get(0).setFav(false);
                        DataSource.getInstance().markAsFav(fav_list.get(0));
                    } else if (!model.isFav()) {
                        image_bt.setImageResource(R.drawable.fav_icon);
                        fav_list.get(0).setFav(true);
                        DataSource.getInstance().markAsFav(fav_list.get(0));
                    }
                }
                else
                {
                    image_bt.setImageResource(R.drawable.fav_icon);
                    fav_list.get(0).setFav(true);
                    DataSource.getInstance().markAsFav(fav_list.get(0));
                }
            }
        });


        Bundle  sentBundle =getArguments();

        if (sentBundle != null) {

            title_Details = sentBundle.getString("title");
            TextView textView = (TextView) rootView.findViewById(R.id.detail);
            textView.setText(title_Details);

            poster_Details = sentBundle.getString("url");
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageview);
            Picasso.with(getContext()).load(Base_photo + poster_Details).into(imageView);

            overview_Details = sentBundle.getString("overview");
            TextView textView1 = (TextView) rootView.findViewById(R.id.detail1);
            textView1.setText("Over View  : " + overview_Details);

            voteaverage_Details = sentBundle.getString("vote");
            TextView textView2 = (TextView) rootView.findViewById(R.id.detail2);
            textView2.setText("Rate  : " + voteaverage_Details);

            release_Details = sentBundle.getString("release");
            TextView textView3 = (TextView) rootView.findViewById(R.id.detail3);
            textView3.setText("Release Date : " + release_Details);

            id_Details = sentBundle.getString("id");
            GetTrailer getTrailer = new GetTrailer();

            Utils a=new Utils();
            if(a.isNetworkConnected(getContext())) {
                getTrailer.execute(id_Details);
            }

            type_Details = sentBundle.getString("type");

            GetReviews getReview = new GetReviews();
            if(a.isNetworkConnected(getContext()))
            getReview.execute(id_Details);
            textView4 = (TextView) rootView.findViewById(R.id.textview);
            textView5 = (TextView) rootView.findViewById(R.id.textview1);
            if(author==null&&author==null) {
                textView4.setText("Author is : no one ");
                textView5.setText("Content is : no one ");
            }
            model.setUrl(poster_Details);
            model.setID(id_Details);
            model.setTitle(title_Details);
            model.setTag(type_Details);
            model.setOverview(overview_Details);
            model.setVote(voteaverage_Details);
            model.setRelease(release_Details);


            fav_list = DataSource.getInstance().getImagesByname(title_Details);
            if (!fav_list.isEmpty()) {
                if (fav_list.get(0).isFav())
                    image_bt.setImageResource(R.drawable.fav_icon);
                else
                    image_bt.setImageResource(R.drawable.unfav_icon);
            } else {
                image_bt.setImageResource(R.drawable.unfav_icon);
                fav_list.add(model);
                DataSource.getInstance().insertList(fav_list);
            }
        }

        return rootView;
    }

    private class GetTrailer extends AsyncTask<String, Void, String[]> {

        String[] Trailar;
        Movie MD = new Movie();

        private ArrayList<String> Movies;

        public GetTrailer() {
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String movieJsonStr = null;


        public String[] getmovieDataFromJson(String data)
                throws JSONException {

            JSONObject jsonObject = new JSONObject(data);
            JSONArray moviesArray = jsonObject.getJSONArray("results");

            key = new String[moviesArray.length()];

            MD.key_of_movie = new ArrayList<>();

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject object = moviesArray.getJSONObject(i);
                key[i] = object.getString("key");

            }
            return key;
        }

        @Override
        public String[] doInBackground(String... voids) {


            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/" + voids[0] + "/videos?api_key=43fbd7fe5199870f63961e91e72423a6");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
            try {
                Trailar = getmovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return Trailar;
        }

        @Override
        protected void onPostExecute(String[] result) {


            Movies = new ArrayList<>(Arrays.asList(result));
            if (result != null) {
                Movies.clear();
                for (String dayForecastStr : result) {
                    Movies.add(dayForecastStr);
                }
                mForecastAdapter = new TrailerAdapter(getContext(), Movies);
                ls.setAdapter(mForecastAdapter);
            }
        }
    }

    private class GetReviews  extends AsyncTask<String, Void, String> {

        String content_result;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String reviewsJsonStr = null;


        public String getmovieDataFromJson(String data)
                throws JSONException {

            JSONObject object = new JSONObject(data);
            JSONArray moviesArray = object.getJSONArray("results");

                JSONObject details = moviesArray.getJSONObject(0);
                author = details.getString("author");
                content = details.getString("content");

            return content;
        }

        @Override
        public String doInBackground(String... voids) {


            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/" + voids[0] + "/reviews?api_key=43fbd7fe5199870f63961e91e72423a6");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                reviewsJsonStr = buffer.toString();
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                    }
                }
            }

            try {
                content_result = getmovieDataFromJson(reviewsJsonStr);
            } catch (JSONException e) {

            }
            return content_result;
        }

        @Override
        protected void onPostExecute(String s) {
                if(s==null)
                {
                    textView4.setText("Author is : no one " );
                    textView5.setText("Content is : no one " );
                }
                else
                {
                    textView4.setText("Author is : "+author );
                    textView5.setText("Content is : "+content );
                }
        }
    }
}