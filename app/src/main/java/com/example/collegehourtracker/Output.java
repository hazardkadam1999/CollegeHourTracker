package com.example.collegehourtracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ClickSend.Api.SmsApi;
import ClickSend.ApiClient;
import ClickSend.ApiException;
import ClickSend.Model.SmsMessage;
import ClickSend.Model.SmsMessageCollection;

public class Output extends AppCompatActivity {
    final String[] message = {""};
    final String[] phone = new String[1];
    final String[] name1 = new String[1];
    String ip = "192.168.56.1";
    String port = "1433";
    String classes = "net.sourceforge.jtds.jdbc.Driver";
    String database = "Demo1";
    String username = "test";
    String password = "1234";
    String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
    Connection connection = null;

    Statement statement = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Button in = (Button) findViewById(R.id.in);
        Button out = (Button) findViewById(R.id.out);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Map<String, Integer> StudentNames = new HashMap<String, Integer>();
        StudentNames.put("71923779D", R.drawable.adi);
        StudentNames.put("71923755G", R.drawable.chinu);
        StudentNames.put("71923780D", R.drawable.sam);
        String PRN = getIntent().getExtras().getString("PRN");


        Integer searchPrn = StudentNames.get(PRN);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(searchPrn);
        TextView name = (TextView) findViewById(R.id.name);
        TextView prn = (TextView) findViewById(R.id.prn);


        final String[] last_activity_time = new String[1];
        final String[] last_activity = new String[1];
        try {
            Class.forName(classes);
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {

                try {
                    statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery("SELECT * from Student WHERE PRN='" + PRN + "'");
                    if (resultSet.next()) {

                        prn.setText("PRN : "+resultSet.getString(1));
                        name.setText(resultSet.getString(2));
                        name1[0] = resultSet.getString(2);
                        phone[0] = resultSet.getString(3);
                    }
                    else
                    {
                        Intent intent1= new Intent(getApplicationContext(),Home.class);
                        startActivity(intent1);
                    }


                } catch (SQLException e) {
                    Intent intent2 = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent2);

                }


            }
        } catch (ClassNotFoundException e) {
            Intent intent3 = new Intent(getApplicationContext(),Home.class);
            startActivity(intent3);


        } catch (SQLException e) {
            Intent intent4 = new Intent(getApplicationContext(),Home.class);
            startActivity(intent4);



        }

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message[0] = "Your ward " + name1[0] +" has entered college premises";
                new MyTask().execute();
                Statement statement1= null;
                try {
                    statement1 = connection.createStatement();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    statement1.executeUpdate("INSERT INTO Student_log values(SYSDATETIME(),'"+PRN+"',SYSDATETIME(),'IN')");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),SendAnimation.class);
                startActivity(intent);
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message[0] = "Your ward " + name1[0] +" has left college premises";
                new MyTask().execute();
                Statement statement2= null;
                try {
                    statement2 = connection.createStatement();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    statement2.executeUpdate("INSERT INTO Student_log values(SYSDATETIME(),'"+PRN+"',SYSDATETIME(),'OUT')");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),SendAnimation.class);
                startActivity(intent);
            }

        });


    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ApiClient defaultClient = new ApiClient();
            defaultClient.setUsername("yogeshwarram.godara_comp2018@pccoer.in");
            defaultClient.setPassword("EC8AE951-FCCD-590E-CD61-60AF131CCD81");
            SmsApi apiInstance = new SmsApi(defaultClient);

            SmsMessage smsMessage = new SmsMessage();
            smsMessage.body(message[0]);
            smsMessage.from("PCCOER");
            smsMessage.to("+91" + phone[0]);
            smsMessage.source("sdk");

            List<SmsMessage> smsMessageList = Arrays.asList(smsMessage);
            // SmsMessageCollection | SmsMessageCollection model
            SmsMessageCollection smsMessages = new SmsMessageCollection();
            smsMessages.messages(smsMessageList);
            try {
                String result = apiInstance.smsSendPost(smsMessages);
                Log.v("Message", "Sucess");

                System.out.println(result);
            } catch (ApiException e) {
                System.err.println("Exception when calling SmsApi#smsSendPost");
                Log.v("Message", "Exception when calling SmsApi#smsSendPost");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}

