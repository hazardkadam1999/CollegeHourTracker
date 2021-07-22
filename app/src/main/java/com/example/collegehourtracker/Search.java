package com.example.collegehourtracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.collegehourtracker.ui.login.Item1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    TextView err;
    String[] listrow1,listrow2,listrow3,listrow4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EditText eprn1= (EditText)findViewById(R.id.eprn1);
        ImageView search1=(ImageView)findViewById(R.id.search1);
        ListView list1 = (ListView)findViewById(R.id.List1);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        Context context =this;
        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eprn2="";
                eprn2=eprn1.getText().toString();
                String ip = "192.168.56.1";
                String port = "1433";
                String classes = "net.sourceforge.jtds.jdbc.Driver";
                String database = "Demo1";
                String username = "test";
                String password = "1234";
                String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
                final Connection[] connection = {null};
                final Statement[] statement = {null};

                List<String> prn1 = new ArrayList();
                List<String> act2 = new ArrayList();
                List<String> time3 = new ArrayList();
                List<String> date4 = new ArrayList();

                try {
                    Class.forName(classes);
                    connection[0] = DriverManager.getConnection(url, username, password);

                    if (connection[0] != null) {

                        try {
                            eprn2 =eprn1.getText().toString();
                            statement[0] = connection[0].createStatement();
                            ResultSet resultSet;
                            resultSet=statement[0].executeQuery("SELECT * FROM Student where PRN='"+eprn2+"'");
                            if(resultSet.next())
                            {
                              resultSet = statement[0].executeQuery("SELECT * FROM Student_log where PRN='"+eprn2+"'");

                                while(resultSet.next())
                                {
                                    prn1.add(resultSet.getString(2));
                                    date4.add(resultSet.getString(1));
                                    act2.add(resultSet.getString(4));
                                    time3.add(resultSet.getString(3).substring(0,8));

                                }

                                 listrow1=(String[]) prn1.toArray(new String[prn1.size()]);
                                listrow2=(String[]) act2.toArray(new String[prn1.size()]);
                                listrow3=(String[])time3.toArray(new String[prn1.size()]);
                               listrow4=(String[]) date4.toArray(new String[prn1.size()]);
                                ListView list1=(ListView)findViewById(R.id.List1);
                                Item1 i1=new Item1(context,listrow1,listrow2,listrow3,listrow4);

                                list1.setAdapter(i1);


                            }
                            else {
                                Intent intent1 = new Intent(getApplicationContext(),Search.class);
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


            }
        });

    }
}