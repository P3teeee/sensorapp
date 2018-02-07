package com.example.android.sensorapp;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    float x = 0;
    float y = 0;
    float dX, dY;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;
    private ImageView cubeView;
    private RelativeLayout screenLayout;
    private TextView scoreTextView;
    int wText;
    int wLayout;
    int hLayout;
    int hText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        cubeView = (ImageView) findViewById(R.id.image_cube);
        screenLayout = (RelativeLayout) findViewById(R.id.screen_layout);
        scoreTextView = (TextView) findViewById(R.id.score_text_view);


        if (gyroscopeSensor == null) {
            Toast.makeText(this, "The device has no Gyroscope !", Toast.LENGTH_SHORT).show();
            finish();
        }

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[1] > 1.5f) {
                    score = score + 1;
                    x = 7;
                    displayScore(score);
                    movePlayerRight(x);
                } else if (sensorEvent.values[1] < -1.5f) {
                    score = score + 1;
                    x = -7;
                    displayScore(score);
                    movePlayerLeft(x);
                }
                if (sensorEvent.values[0] > 1.5f) {
                    score = score + 1;
                    y = 10;
                    displayScore(score);
                    movePlayerDown(y);
                } else if (sensorEvent.values[0] < -1.5f) {
                    score = score + 1;
                    y = -10;
                    displayScore(score);
                    movePlayerUp(y);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }

    private void displayScore(int number) {
        scoreTextView.setText("" + number);

    }

    public void movePlayerRight(float moveX) {
        wLayout = screenLayout.getWidth();
        wText = scoreTextView.getWidth();
        float p = dX + wText;
        dX = scoreTextView.getX();
        dY = scoreTextView.getY();
        if(p >= wLayout) {
            scoreTextView.setX(dX);
        }else {
            scoreTextView.setX(dX + moveX);
        }
    }
    public void movePlayerLeft(float moveX) {
        wText = scoreTextView.getWidth();
        float p = dX + wText;
        dX = scoreTextView.getX();
        dY = scoreTextView.getY();
        if(dX < 0) {
            scoreTextView.setX(0);
        }else {
            scoreTextView.setX(dX + moveX);
        }
    }
    public void movePlayerDown(float moveY) {
        hLayout = screenLayout.getHeight();
        hText = scoreTextView.getHeight();
        float p = dY + hText;
        dX = scoreTextView.getX();
        dY = scoreTextView.getY();
        if(p >= hLayout) {
            scoreTextView.setY(dY);
        }else {
            scoreTextView.setY(dY + moveY);
        }
    }
    public void movePlayerUp(float moveY) {
        dX = scoreTextView.getX();
        dY = scoreTextView.getY();
        if(dY < 0) {
            scoreTextView.setY(0);
        }else {
            scoreTextView.setY(dY + moveY);
        }
    }
}
