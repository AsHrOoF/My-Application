package com.example.android.sec_movieapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.sec_movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends BaseAdapter {

    Context c;
    List<String> images;

    public MovieAdapter(Context c, List<String> images) {
        this.c = c;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        if (view1 == null) {
            LayoutInflater inflater = (LayoutInflater)
                    c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view1 = inflater.inflate(R.layout.movie_fragment, viewGroup, false);
        }
        ImageView imageView = (ImageView) view1.findViewById(R.id.iv);
        Picasso.with(c).load("http://image.tmdb.org/t/p/w185/" + images.get(i)).into(imageView);
        return imageView;
    }
}
