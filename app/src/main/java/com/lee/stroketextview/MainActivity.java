package com.lee.stroketextview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private StrokeTextView tvHorGradient;
    private StrokeTextView tvVerGradient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHorGradient = (StrokeTextView) findViewById(R.id.gradient_hor);
        tvVerGradient = (StrokeTextView) findViewById(R.id.gradient_ver);
        tvHorGradient.setGradientColor(new int[]{Color.BLUE, Color.YELLOW});
        tvVerGradient.setGradientColor(new int[]{Color.BLUE, Color.YELLOW});
    }
}
