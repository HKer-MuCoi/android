package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class DinhduongActivity extends AppCompatActivity {
    private Button btnBack, btnTangCan, btnGiamCan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinhduong);

        Intent intent = getIntent();
        String resultTra=intent.getStringExtra(EXTRA_MESSAGE);
        TextView tvResultTra = findViewById(R.id.tvResultTra);
        tvResultTra.setText(resultTra);


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnTangCan = findViewById(R.id.btnTangCan);
        btnTangCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTang = new Intent(DinhduongActivity.this, GoiYTang.class);
                startActivity(intentTang);
            }
        });

        btnGiamCan =findViewById(R.id.btnGiamCan);
        btnGiamCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenGiam = new Intent(DinhduongActivity.this, GoiYGiam.class);
                startActivity(intenGiam);
            }
        });

    }
}