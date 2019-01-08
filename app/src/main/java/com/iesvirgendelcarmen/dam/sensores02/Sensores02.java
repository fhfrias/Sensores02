package com.iesvirgendelcarmen.dam.sensores02;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sensores02 extends AppCompatActivity implements SensorEventListener {

    int contador;
    double x;
    double y;
    double z;
    double a ;
    double amax;
    double gravedad;

    TextView tvax, tvay, tvaz, tva, tvaMax, tvG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores02);

        x = 0;
        y = 0;
        z = 0;
        a = 0;
        amax = 0;
        gravedad = SensorManager.STANDARD_GRAVITY;

        tvax = findViewById(R.id.aceleracion_x);
        tvay = findViewById(R.id.aceleracion_y);
        tvaz = findViewById(R.id.aceleracion_z);
        tva = findViewById(R.id.aceleracion);
        tvaMax = findViewById(R.id.aceleracion_max);
        tvG = findViewById(R.id.gravedad);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_FASTEST);

        new MiAsyncTask().execute();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        a = Math.sqrt(x * x + y * y + z * z);
        if(a > amax){
            amax = a;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class MiAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... arg0) {
            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                contador ++;
                publishProgress();
            }
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
            tvax.setText("" + x);
            tvay.setText("" + y);
            tvaz.setText("" + z);
            tva.setText("" + a);
            tvaMax.setText("" + amax);
            tvG.setText("" + gravedad);
            tvG.append("\n" + "CONTADOR: " + contador);
        }
    }
}
