package com.example.android.sec_movieapp.Fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.sec_movieapp.Adapter.MovieAdapter;
import com.example.android.sec_movieapp.Details.Movie;
import com.example.android.sec_movieapp.MovieListiner;
import com.example.android.sec_movieapp.R;
import com.example.android.sec_movieapp.Utils.Utils;

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


public class Movie_Fragment extends Fragment {

    public MovieAdapter adapter;
    public List<String> MovieForecast;
    public GridView gv;
    Movie MD = new Movie();
    private MovieListiner nListiner;

    public void setMovieListiner(MovieListiner listiner)
    {
        this.nListiner=listiner;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.movie_fragment, container, false);;
        gv = (GridView) rootView.findViewById(R.id.gridview);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MD.title_info = MD.title_of_movie.get(i);
                MD.poster_info = MD.poster_of_movie.get(i);
                MD.overview_info = MD.overview_of_movie.get(i);
                MD.voteaverage_info = MD.voteaverage_of_movie.get(i);
                MD.release_info = MD.release_of_movie.get(i);
                MD.id_info = MD.id_of_movie.get(i);

                nListiner.setSelectedMovie(MD.title_info,MD.poster_info,MD.overview_info,MD.voteaverage_info, MD.release_info,MD.id_info, MD.type_of_movie);
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        update();
    }

    public void update() {
        FetchMovieTask movieTask = new FetchMovieTask();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_kind = pref.getString(getString(R.string.key), getString(R.string.default_value));
        Utils a=new Utils();
        if(a.isNetworkConnected(getContext())) {
            movieTask.execute(sort_kind);
        }
        else {
            Toast.makeText(getContext()," No Internet Connection ,Try Again !!",Toast.LENGTH_LONG).show();
        }
    }

    class FetchMovieTask extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
        String[] posters;
        String[] names;
        String[] overview;
        String[] voteaverage;
        String[] release;
        String[] id;
        String[] movies;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String posterJsonStr = null;

        public String[] getmovieDataFromJson(String data, String tag)
                throws JSONException {

            JSONObject object = new JSONObject(data);
            JSONArray moviesArray = object.getJSONArray("results");

            posters = new String[moviesArray.length()];
            names = new String[moviesArray.length()];
            overview = new String[moviesArray.length()];
            voteaverage = new String[moviesArray.length()];
            release = new String[moviesArray.length()];
            id = new String[moviesArray.length()];

            MD.title_of_movie = new ArrayList<>();
            MD.poster_of_movie = new ArrayList<>();
            MD.overview_of_movie = new ArrayList<>();
            MD.voteaverage_of_movie = new ArrayList<>();
            MD.release_of_movie = new ArrayList<>();
            MD.id_of_movie = new ArrayList<>();


            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject jsonObject = moviesArray.getJSONObject(i);
                posters[i] = jsonObject.getString("poster_path");
                names[i] = jsonObject.getString("original_title");
                overview[i] = jsonObject.getString("overview");
                voteaverage[i] = jsonObject.getString("vote_average");
                release[i] = jsonObject.getString("release_date");
                id[i] = jsonObject.getString("id");

                MD.title_of_movie.add(names[i]);
                MD.poster_of_movie.add(posters[i]);
                MD.overview_of_movie.add(overview[i]);
                MD.voteaverage_of_movie.add(voteaverage[i]);
                MD.release_of_movie.add(release[i]);
                MD.id_of_movie.add(id[i]);
                MD.type_of_movie = tag;
            }

            return posters;
        }

        @Override
        public String[] doInBackground(String... voids) {

            try {
                    URL url = new URL("https://api.themoviedb.org/3/movie/" + voids[0] + "?api_key=43fbd7fe5199870f63961e91e72423a6");

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
                    posterJsonStr = buffer.toString();
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
                    movies = getmovieDataFromJson(posterJsonStr, voids[0]);


                } catch (JSONException e) {

                }
            return movies;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            {
                    MovieForecast = new ArrayList<>(Arrays.asList(strings));
                    if (strings != null) {
                        MovieForecast.clear();
                        for (String dayForecastStr : strings) {
                            MovieForecast.add(dayForecastStr);
                        }
                        adapter = new MovieAdapter(getContext(), MovieForecast);
                        gv.setAdapter(adapter);
                    }
            }
        }
    }
}