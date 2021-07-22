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

public class Forget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
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
        EditText email=(EditText)findViewById(R.id.email);
        EditText phone=(EditText)findViewById(R.id.phone);
        Button verify=(Button)findViewById(R.id.verify);
        Button login=(Button)findViewById(R.id.login);
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(getApplicationContext(),Login.class);
                startActivity(intent1);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone1 = phone.getText().toString();
                String email1 = email.getText().toString();
                String email2="",phone2="";

                try {
                    Class.forName(classes);
                    connection[0] = DriverManager.getConnection(url, username, password);

                    if (connection[0] != null) {

                        try {
                            statement[0] = connection[0].createStatement();
                            ResultSet resultSet = statement[0].executeQuery("SELECT * From Admin");
                            while(resultSet.next())
                            {
                                phone2=resultSet.getString(2);
                                email2=resultSet.getString(3);


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
                if((phone1.equals(phone2)) && (email1.equals(email2)))
                {
                    Intent intent1= new Intent(getApplicationContext(),Newpass.class);

                    startActivity(intent1);
                }
                else
                {
                    Intent intent1= new Intent(getApplicationContext(),Forget.class);
                    intent1.putExtra("Error","Details Don't Match");
                    startActivity(intent1);

                }
            }
        });


    }
}