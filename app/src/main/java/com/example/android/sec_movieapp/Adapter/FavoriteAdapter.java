package com.example.android.sec_movieapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.sec_movieapp.Details.Model;
import com.example.android.sec_movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ashraf96 on 29/11/2016.
 */

public class FavoriteAdapter extends BaseAdapter {
    Context c;
    List<Model> images;

    public FavoriteAdapter(Context c, List<Model> images) {
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
            view1 = inflater.inflate(R.layout.favorite, viewGroup, false);
        }
        ImageView imageView = (ImageView) view1.findViewById(R.id.image_fav);
        Picasso.with(c).load("http://image.tmdb.org/t/p/w185/" + images.get(i).getUrl()).placeholder(R.drawable.loading).into(imageView);
        return imageView;
    }
}
