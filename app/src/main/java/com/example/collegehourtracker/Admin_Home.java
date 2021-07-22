



package com.example.collegehourtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Admin_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        TextView textView = (TextView) findViewById(R.id.text1);
        String name = getIntent().getExtras().getString("adname");

        textView.setText("Hello " + name);
        Button insert = (Button) findViewById(R.id.insert);
        Button delete = (Button) findViewById(R.id.delete);
        Button update = (Button) findViewById(R.id.update);
        Button record = (Button) findViewById(R.id.record);
        TextView today = (TextView) findViewById(R.id.today);
        TextView yesterday = (TextView) findViewById(R.id.yesterday);
        TextView week = (TextView) findViewById(R.id.week);
        ImageView search = (ImageView) findViewById(R.id.search);
        today.setOnClickListener(new View.OnClickListener() {
            @Override

            /*
             *This Method is called when the Today button is clicked.
             */
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Today.class);
                startActivity(intent1);

            }
        });

        yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            /*
             *This Method is called when the Yesterday button is clicked.
             */
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Yesterday.class);
                startActivity(intent1);
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ThisWeek.class);
                startActivity(intent1);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Search.class);
                startActivity(intent1);
            }
        });


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Insert.class);
                startActivity(intent1);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), Edit.class);
                startActivity(intent2);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Delete.class);
                startActivity(intent1);
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), AllRecords.class);
                startActivity(intent1);
            }
        });
    }
}