package com.jdenner.minhasfinancas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Juliano on 04/08/2016.
 */
public class AberturaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);

        Timer timer = new Timer();
        timer.schedule(new IniciarPrincipal(), 5000);
    }

    private class IniciarPrincipal extends TimerTask {

        @Override
        public void run() {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int ano = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);

            Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
            intent.putExtra("animar", 0);
            intent.putExtra("ano", ano);
            intent.putExtra("mes",mes);

            startActivity(intent);
            finish();
        }
    }
}
