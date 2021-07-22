package com.example.collegehourtracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

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

public class ThisWeek extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_this_week);
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

        List<String> prn1 = new ArrayList();
        List<String> act2 = new ArrayList();
        List<String> time3 = new ArrayList();
        List<String> date4 = new ArrayList();
        try {

            Class.forName(classes);
            connection[0] = DriverManager.getConnection(url, username, password);

            if (connection[0] != null) {

                try {

                    statement[0] = connection[0].createStatement();
                    ResultSet resultSet = statement[0].executeQuery("select * from Student_log where DATE=(select dateadd(dd,0,CAST(SYSDATETIME() as date))) or  DATE=(select dateadd(dd,-6,CAST(SYSDATETIME() as date))) or  DATE=(select dateadd(dd,-5,CAST(SYSDATETIME() as date))) or  DATE=(select dateadd(dd,-4,CAST(SYSDATETIME() as date))) or  DATE=(select dateadd(dd,-3,CAST(SYSDATETIME() as date))) or DATE=(select dateadd(dd,-2,CAST(SYSDATETIME() as date))) or  DATE=(select dateadd(dd,-1,CAST(SYSDATETIME() as date)))"
                            );
                    while(resultSet.next())
                    {
                        prn1.add(resultSet.getString(2));
                        date4.add(resultSet.getString(1));
                        act2.add(resultSet.getString(4));
                        time3.add(resultSet.getString(3).substring(0,8));

                    }

                    String[] listrow1=(String[]) prn1.toArray(new String[prn1.size()]);
                    String[] listrow2=(String[]) act2.toArray(new String[prn1.size()]);
                    String[] listrow3=(String[])time3.toArray(new String[prn1.size()]);
                    String[] listrow4=(String[]) date4.toArray(new String[prn1.size()]);
                    ListView list1=(ListView)findViewById(R.id.List1);


                    Item1 i1=new Item1(this,listrow1,listrow2,listrow3,listrow4);

                    list1.setAdapter(i1);


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
}