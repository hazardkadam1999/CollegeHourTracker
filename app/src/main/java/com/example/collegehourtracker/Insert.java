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

public class Insert extends AppCompatActivity {
    TextView err;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        if(getIntent().hasExtra("Error"))
        {
         err= (TextView)findViewById(R.id.error);
            err.setTextColor(getResources().getColor(R.color.red));
            err.setText(getIntent().getExtras().getString("Error"));
        }
        else
        {
         err= (TextView)findViewById(R.id.error);
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
        Button enter=(Button)findViewById(R.id.enter);
        EditText prn=(EditText)findViewById(R.id.prn);
        EditText num=(EditText)findViewById(R.id.num);
        EditText name=(EditText)findViewById(R.id.name);
        final String[] prn1 = {""};
        final String[] num1 = { "" };
        final String[] name1 = { "" };
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prn1[0] =prn.getText().toString();
                num1[0] =num.getText().toString();
                name1[0] =name.getText().toString();
                try {
                    Class.forName(classes);
                    connection[0] = DriverManager.getConnection(url, username, password);

                    if (connection[0] != null) {

                        try {
                            statement[0] = connection[0].createStatement();
                            statement[0].executeUpdate("Insert into Student VAlues('" + prn1[0] +"','"+ name1[0] +"','"+ num1[0] +"')");
                            Intent intent1= new Intent(getApplicationContext(),Success.class);
                            startActivity(intent1);
                            prn.setText("");
                            num.setText("");
                            name.setText("");
                            err.setText("");
                      



                        } catch (SQLException e) {
                            Intent intent1= new Intent(getApplicationContext(),Insert.class);
                            intent1.putExtra("Error","PRN already exists");
                            startActivity(intent1);

                        }


                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();

                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        });

    }
}