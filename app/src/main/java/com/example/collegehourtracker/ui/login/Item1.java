package com.example.collegehourtracker.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collegehourtracker.R;

public class Item1 extends BaseAdapter {

    String[] listrow1,listrow2,listrow3,listrow4;
    LayoutInflater n;
    public Item1(Context c, String[] i, String[] j, String[] k,String[] l)
    {
        listrow1=i;
        listrow2=j;
        listrow3=k;
        listrow4=l;
        n=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return listrow1.length;
    }

    @Override
    public Object getItem(int i) {
        return listrow1[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=n.inflate(R.layout.listrow,null);
        TextView name=(TextView)v.findViewById(R.id.prn2);
        TextView description=(TextView)v.findViewById(R.id.activity2);
        TextView price=(TextView)v.findViewById(R.id.time2);
TextView date=(TextView)v.findViewById(R.id.date2);
        String name1=listrow1[i];
        String desc1=listrow2[i];
        String price1=listrow3[i];
String date1=listrow4[i];
        name.setText(name1);
        description.setText(desc1);
        price.setText(price1);
date.setText(date1);
        return v;
    }
}