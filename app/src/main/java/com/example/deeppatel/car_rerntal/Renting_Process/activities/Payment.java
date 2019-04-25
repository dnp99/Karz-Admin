package com.example.deeppatel.car_rerntal.Renting_Process.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.deeppatel.car_rerntal.R;

public class Payment extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment2);
        webView = findViewById(R.id.wv_payment);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.paypal.com/ca/signin");
    }

    public void btn_payment(View view) {

            /***************************Receipt Activity************/

        Intent recieptIntent = new Intent(getBaseContext(), Receipt.class);
        recieptIntent.putExtra("selected_car", String.valueOf("Receipt"));
        startActivity(recieptIntent);
    }
}
