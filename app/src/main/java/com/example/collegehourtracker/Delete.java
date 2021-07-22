package com.example.collegehourtracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete extends AppCompatActivity {
    TextView err;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

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
        EditText eprn=(EditText)findViewById(R.id.eprn);

        TextView prn1=(TextView) findViewById(R.id.prn);
        TextView name1=(TextView) findViewById(R.id.name);
        TextView num2=(TextView) findViewById(R.id.num);
      Button delete1=(Button) findViewById(R.id.delete1);
        prn1.setVisibility(View.GONE);
        name1.setVisibility(View.GONE);
        num2.setVisibility(View.GONE);
        delete1.setVisibility(View.GONE);

        final String[] eprn1 = {""};
        final String[] name = { "" };
        final String[] phone = { "" };
        ImageView search=(ImageView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class.forName(classes);
                    connection[0] = DriverManager.getConnection(url, username, password);

                    if (connection[0] != null) {

                        try {
                            eprn1[0] =eprn.getText().toString();
                            statement[0] = connection[0].createStatement();
                            ResultSet resultSet;
                            resultSet=statement[0].executeQuery("SELECT * FROM Student where PRN='"+eprn1[0]+"'");
                                    if(resultSet.next())
                                    {
                                        name[0] =resultSet.getString(2);
                                        phone[0] =resultSet.getString(3);
                                    }
                                    else {
                                        Intent intent1 = new Intent(getApplicationContext(), Delete.class);
                                        intent1.putExtra("Error", "PRN doesn't exist");
                                        startActivity(intent1);
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

                prn1.setVisibility(View.VISIBLE);
                name1.setVisibility(View.VISIBLE);
                num2.setVisibility(View.VISIBLE);
                delete1.setVisibility(View.VISIBLE);
                prn1.setText(eprn1[0]);
                name1.setText(name[0]);
                num2.setText(phone[0]);

                    }
                });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class.forName(classes);
                    connection[0] = DriverManager.getConnection(url, username, password);
                    if (connection[0] != null) {

                        try {

                            statement[0] = connection[0].createStatement();
                            statement[0].executeUpdate("DELETE FROM Student where PRN='"+eprn1[0]+"'");
                            err.setText("");
                            Intent i1=new Intent(getApplicationContext(),Success.class);

                            startActivity(i1);
                            prn1.setVisibility(View.GONE);
                            name1.setVisibility(View.GONE);
                            num2.setVisibility(View.GONE);
                            delete1.setVisibility(View.GONE);
                            eprn.setText("");



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
        });


    }
}




