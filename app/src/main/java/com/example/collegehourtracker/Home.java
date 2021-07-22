package com.example.collegehourtracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Home extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button scanbtn=(Button)findViewById(R.id.scan);
        Button visitor=(Button) findViewById(R.id.visitor);
        Button admin=(Button)findViewById(R.id.login);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent =new Intent(getApplicationContext(),Login.class);

                startActivity(login_intent);


            }
        });
        visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visitor_intent =new Intent(getApplicationContext(),Visitor.class);

                startActivity(visitor_intent);

            }
        });
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA,
                        CAMERA_PERMISSION_CODE);


            }

        });
    }
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(Home.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Home.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Intent btn_intent =new Intent(getApplicationContext(),Scanner.class);

            startActivity(btn_intent);
        }

    }
}