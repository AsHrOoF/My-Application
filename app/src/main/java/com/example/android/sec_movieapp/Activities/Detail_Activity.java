package com.example.android.sec_movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.sec_movieapp.Fragments.detail_Fragment;
import com.example.android.sec_movieapp.R;

public class Detail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent sentIntent=getIntent();
        Bundle sentBundle=sentIntent.getExtras();
        detail_Fragment fragment=new detail_Fragment();
        fragment.setArguments(sentBundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fram_Detail,fragment,"").commit();
    }
}