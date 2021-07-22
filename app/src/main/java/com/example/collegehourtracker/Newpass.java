package com.example.collegehourtracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Newpass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpass);
        if(getIntent().hasExtra("Error"))
        {
            TextView err= (TextView)findViewById(R.id.error);
            err.setText(getIntent().getExtras().getString("Error"));
        }
        else
        {
            TextView err= (TextView)findViewById(R.id.error);
            err.setText("");
        }
        String ip = "192.168.56.1";
        String port = "1433";
        String classes = "net.sourceforge.jtds.jdbc.Driver";
        String database = "Demo1";
        String username = "test";
        String password = "1234";
        String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
        final Connection[] connection = {null};
        final Statement[] statement = {null};
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        EditText pass1=(EditText)findViewById(R.id.pass1);
        final EditText[] pass2 = {(EditText) findViewById(R.id.pass2)};
        Button save=(Button)findViewById(R.id.save);
        Button login1=(Button)findViewById(R.id.login1);
        final String[] p1 = {""};
        final String[] p2 = { "" };
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),Login.class);
                startActivity(intent1);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1[0] =pass1.getText().toString();
                p2[0] = pass2[0].getText().toString();
                if(p1[0].equals(p2[0]))
                {
                    try {
                        Class.forName(classes);
                        connection[0] = DriverManager.getConnection(url, username, password);

                        if (connection[0] != null) {

                            try {
                                statement[0] = connection[0].createStatement();
                                statement[0].executeUpdate("Update Admin set PASSWORD='"+ p1[0] +"'");
                                Intent intent1= new Intent(getApplicationContext(),Login.class);
                                intent1.putExtra("message","Password Successfully changed");
                                startActivity(intent1);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();

                    } catch (SQLException e) {
                        e.printStackTrace();

                    }
                }
                else
                {
                    Intent intent1= new Intent(getApplicationContext(),Newpass.class);
                    intent1.putExtra("Error","Password doesn't match");
                    startActivity(intent1);

                }


            }
        });

    }
}