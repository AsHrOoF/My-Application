package com.example.android.sec_movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.sec_movieapp.Fragments.Favorites_Fragment;
import com.example.android.sec_movieapp.Fragments.Movie_Fragment;
import com.example.android.sec_movieapp.Fragments.detail_Fragment;
import com.example.android.sec_movieapp.MovieListiner;
import com.example.android.sec_movieapp.R;

/**
 * Created by ashraf96 on 30/11/2016.
 */

public class MovieActivity extends AppCompatActivity implements MovieListiner{
boolean mIsTwoPane=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);

        Movie_Fragment movie_fragment=new Movie_Fragment();
        movie_fragment.setMovieListiner(this);
        if(getSupportFragmentManager().findFragmentByTag("home") == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fram_main,movie_fragment,"home").commit();

        if(null!=findViewById(R.id.fram_Detail))
        {
            mIsTwoPane=true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.b1) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.b2_fav) {
                Favorites_Fragment favorites_fragment=new Favorites_Fragment();
                favorites_fragment.setMovieListiner(this);
                getSupportFragmentManager().beginTransaction().add(R.id.fram_main,favorites_fragment).commit();
                return true;
        } else if (id == R.id.b3) {
                Movie_Fragment movie_fragment=new Movie_Fragment();
                movie_fragment.setMovieListiner(this);
                getSupportFragmentManager().beginTransaction().add(R.id.fram_main   ,movie_fragment).commit();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    public void setSelectedMovie(String title, String url, String overview, String vote, String release, String id ,String type) {

        if(!mIsTwoPane)
        {
            Intent i=new Intent(this,Detail_Activity.class);
            i.putExtra("title",title );
            i.putExtra("url",url );
            i.putExtra("overview",overview );
            i.putExtra("vote",vote );
            i.putExtra("release",release );
            i.putExtra("id",id );
            i.putExtra("type",type);
            startActivity(i);
        }
        else
        {
            detail_Fragment fragment=new detail_Fragment();
            Bundle bundle=new Bundle();
            bundle.putString("title",title);
            bundle.putString("url",url);
            bundle.putString("overview",overview);
            bundle.putString("vote",vote);
            bundle.putString("release",release);
            bundle.putString("id",id);
            bundle.putString("type",type);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.fram_Detail,fragment,"").commit();

        }
    }
}
