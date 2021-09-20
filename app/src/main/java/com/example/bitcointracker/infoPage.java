package com.example.bitcointracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class infoPage extends AppCompatActivity {
    private Button returnBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        //HYPERLINK TO COINBASE
        setupCoinbase();

        // RETURN BUTTON FUNCTIONS
        Button returnBTN = (Button) findViewById(R.id.returnBTN);
        returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }

        });
    }
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setupCoinbase(){
        TextView linkTV = findViewById(R.id.activity_main_link);
        linkTV.setMovementMethod(LinkMovementMethod.getInstance());
    }
}