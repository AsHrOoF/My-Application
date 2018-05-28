package com.example.android.sec_movieapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.sec_movieapp.R;

import java.util.List;


public class TrailerAdapter extends BaseAdapter {

    Context c;
    List<String> key;

    public TrailerAdapter(Context c, List<String> key) {
        this.c = c;
        this.key = key;
    }

    @Override
    public int getCount() {
        return key.size();
    }

    @Override
    public Object getItem(int i) {
        return key.get(i);
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
            view1 = inflater.inflate(R.layout.fragment_detail, viewGroup, false);
        }
        TextView textView = (TextView) view1.findViewById(R.id.tv1);
        textView.setText(" Trailer " + i + 1);
        return textView;
    }
}

