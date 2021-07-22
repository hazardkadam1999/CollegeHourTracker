package com.example.collegehourtracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class Scanner extends AppCompatActivity {
    CodeScanner codescan;
    CodeScannerView cdscanvw;
    String prn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        cdscanvw=findViewById(R.id.Camscan);
        codescan=new CodeScanner(this,cdscanvw);

        codescan.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                prn=result.getText();

                if(!prn.isEmpty())
                {
                    Intent decode_intent =new Intent(getApplicationContext(), Output.class);
                    decode_intent.putExtra("PRN",prn);
                    startActivity(decode_intent);
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        codescan.startPreview();

    }


}