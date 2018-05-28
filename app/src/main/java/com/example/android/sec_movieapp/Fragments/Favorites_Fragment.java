package com.example.android.sec_movieapp.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.android.sec_movieapp.Adapter.FavoriteAdapter;
import com.example.android.sec_movieapp.DataBase.DataSource;
import com.example.android.sec_movieapp.Details.Model;
import com.example.android.sec_movieapp.MovieListiner;
import com.example.android.sec_movieapp.R;

import java.util.List;

/**
 * Created by ashraf96 on 28/11/2016.
 */

public class Favorites_Fragment extends Fragment {

    GridView gv;
    List<Model> model;
    Button clear;
    FavoriteAdapter favoriteAdapter;
    Model model_Fav;
    boolean mIsTwoPane;
    MovieListiner nListiner;
    public void setMovieListiner(MovieListiner listiner)
    {
        this.nListiner=listiner;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.favorite,container,false);
        if(null==rootview.findViewById(R.id.fram_Detail))
        {
            mIsTwoPane=true;
        }
        model=DataSource.getInstance().getFav();

        clear=(Button)rootview.findViewById(R.id.clear_button);
        clear.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSource.getInstance().deleteImagesWithTag();

            }
        });

        favoriteAdapter=new FavoriteAdapter(getContext(),model);
        gv=(GridView)rootview.findViewById(R.id.gridview_fav);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model_Fav = model.get(i);

                    nListiner.setSelectedMovie(model_Fav.getTitle(), model_Fav.getUrl(), model_Fav.getOverview(), model_Fav.getVote(), model_Fav.getRelease(), model_Fav.getID(), model_Fav.getTag());
                }
        });
        gv.setAdapter(favoriteAdapter);
        return rootview;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static boolean ismIsTwoPane(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}