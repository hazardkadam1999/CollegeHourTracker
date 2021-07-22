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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getIntent().hasExtra("Error"))
        {
            TextView err= (TextView)findViewById(R.id.error);
            err.setTextColor(getResources().getColor(R.color.red));
            err.setText(getIntent().getExtras().getString("Error"));
        }
        else if(getIntent().hasExtra("message"))
            {
                TextView err= (TextView)findViewById(R.id.error);
                err.setTextColor(getResources().getColor(R.color.green));
                err.setText(getIntent().getExtras().getString("message"));
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
        Button forgot=(Button)findViewById(R.id.forgot);
        Button login =(Button)findViewById(R.id.enter);
        EditText id=(EditText)findViewById(R.id.id);
        EditText pass=(EditText)findViewById(R.id.password);
       forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Forget.class);
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = id.getText().toString();
                String pass1 = pass.getText().toString();
                String id2="",pass2="";
                String adname="";
                try {
                    Class.forName(classes);
                    connection[0] = DriverManager.getConnection(url, username, password);

                    if (connection[0] != null) {

                        try {
                            statement[0] = connection[0].createStatement();
                            ResultSet resultSet = statement[0].executeQuery("SELECT * From Admin");
                            while(resultSet.next())
                            {
                                id2=resultSet.getString(1);
                                pass2=resultSet.getString(5);
                                adname=resultSet.getString(4);

                            }



                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();

                } catch (SQLException e) {
                    e.printStackTrace();

                }

                if((pass1.equals(pass2)) && (id1.equals(id2)))
                {
                    Intent intent1= new Intent(getApplicationContext(),Admin_Home.class);
                    intent1.putExtra("adname",adname);
                    startActivity(intent1);
                }
                else
                {
                    Intent intent1= new Intent(getApplicationContext(),Login.class);
                    intent1.putExtra("Error","Incorrect User ID or Password");
                    startActivity(intent1);

                }


            }
        });

    }







}
