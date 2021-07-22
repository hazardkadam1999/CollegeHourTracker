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
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import ClickSend.Api.SmsApi;
import ClickSend.ApiClient;
import ClickSend.ApiException;
import ClickSend.Model.SmsMessage;
import ClickSend.Model.SmsMessageCollection;

public class Visitor extends AppCompatActivity {
String message1,message2="";
String visitor_phone,office="8668348313";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String ip = "192.168.56.1";
        String port = "1433";
        String classes = "net.sourceforge.jtds.jdbc.Driver";
        String database = "Demo1";
        String username = "test";
        String password = "1234";
        String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;
        final Connection[] connection = {null};
        final Statement[] statement = {null};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        EditText name=(EditText)findViewById(R.id.id);
        final EditText[] phone = {(EditText) findViewById(R.id.password)};
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Button enter=(Button)findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class.forName(classes);
                    connection[0] = DriverManager.getConnection(url, username, password);
                    String visitor_name=name.getText().toString();
                    visitor_phone = phone[0].getText().toString();
                    if (connection[0] != null) {

                        try {
                            statement[0] = connection[0].createStatement();
                                statement[0].executeUpdate("INSERT INTO Visitor values('"+visitor_phone+"'" + ","+"'"+visitor_name+"'"+","+"SYSDATETIME(),SYSDATETIME())");
                                message1="Hello, "+visitor_name+"! Welcome to PCCOER. Your Visitor Id : "+visitor_phone+".";
                                message2="A Visitor has entered. Name : "+visitor_name+", Mobile No. : "+visitor_phone+".";
                          new MyTask().execute();
                            Intent intent =new Intent(getApplicationContext(),SendAnimation.class);
                            startActivity(intent);

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
    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ApiClient defaultClient = new ApiClient();
            defaultClient.setUsername("yogeshwarram.godara_comp2018@pccoer.in");
            defaultClient.setPassword("EC8AE951-FCCD-590E-CD61-60AF131CCD81");
            SmsApi apiInstance = new SmsApi(defaultClient);

            SmsMessage smsMessage = new SmsMessage();
            smsMessage.body(message1);
            smsMessage.to("+91" + visitor_phone);

            smsMessage.source("sdk");
            SmsMessage smsMessage1= new SmsMessage();
            smsMessage1.body(message2);
            smsMessage1.to("+91"+office);

            smsMessage1.source("sdk");
            List<SmsMessage> smsMessageList1 = Arrays.asList(smsMessage1);
            // SmsMessageCollection | SmsMessageCollection model
            SmsMessageCollection smsMessages1 = new SmsMessageCollection();
            smsMessages1.messages(smsMessageList1);
            try {
                String result = apiInstance.smsSendPost(smsMessages1);
                Log.v("Message", "Success");

                System.out.println(result);
            } catch (ApiException e) {
                System.err.println("Exception when calling SmsApi#smsSendPost");
                Log.v("Message", "Exception when calling SmsApi#smsSendPost");
                e.printStackTrace();
            }



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