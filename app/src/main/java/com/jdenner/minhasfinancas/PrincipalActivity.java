package com.jdenner.minhasfinancas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdenner.minhasfinancas.dao.MovimentoDAO;
import com.jdenner.minhasfinancas.model.Movimento;
import com.jdenner.minhasfinancas.model.Tipo;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Juliano on 04/08/2016.
 */
public class PrincipalActivity extends AppCompatActivity {

    private int ano;
    private int mes;
    private TextView tvSaldo;
    private TextView tvPeriodo;
    private TextView tvEntradas;
    private TextView tvSaidas;
    private ListView lvMovimentos;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mes = getIntent().getExtras().getInt("mes");
        ano = getIntent().getExtras().getInt("ano");
        if (getIntent().getExtras().getInt("animar") == 1) {
            overridePendingTransition(R.animator.slide_in_from_right, R.animator.slide_out_to_left);
        } else if (getIntent().getExtras().getInt("animar") == -1) {
            overridePendingTransition(R.animator.slide_in_from_left, R.animator.slide_out_to_right);
        }

        tvSaldo = (TextView) findViewById(R.id.tvSaldo);
        tvPeriodo = (TextView) findViewById(R.id.tvPeriodo);
        tvEntradas = (TextView) findViewById(R.id.tvEntradas);
        tvSaidas = (TextView) findViewById(R.id.tvSaidas);
        lvMovimentos = (ListView) findViewById(R.id.lvMovimentos);

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(2);
        nf.setGroupingUsed(false);

        tvPeriodo.setText(nf.format(mes) + "/" + nf.format(ano));

        CarregarDados carregarDados = new CarregarDados();
        carregarDados.execute();

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                if (e1.getX() > e2.getX()) {
                    mes++;
                    if (mes > 12) {
                        mes = 1;
                        ano++;
                    }
                    intent.putExtra("animar", 1);
                }
                if (e1.getX() < e2.getX()) {
                    mes--;
                    if (mes < 1) {
                        mes = 12;
                        ano--;
                    }
                    intent.putExtra("animar", -1);
                }

                intent.putExtra("ano", ano);
                intent.putExtra("mes", mes);

                startActivity(intent);
                finish();

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    public void handleBtnNovo(View view) {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("movimento", new Movimento());
        intent.putExtra("ano", ano);
        intent.putExtra("mes", mes);
        startActivity(intent);
        this.finish();
    }

    public void detalhe(Movimento movimento) {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("movimento", movimento);
        intent.putExtra("ano", ano);
        intent.putExtra("mes", mes);
        startActivity(intent);
        this.finish();
    }

    private class CarregarDados extends AsyncTask<Void, Void, List<Movimento>> {
        @Override
        protected List<Movimento> doInBackground(Void... voids) {
            try {
                return MovimentoDAO.listar(getApplicationContext(), ano, mes);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movimento> lista) {
            if (lista == null) {
                Toast.makeText(getApplicationContext(), "Erro ao consultar dados", Toast.LENGTH_SHORT).show();
                return;
            }
            MovimentoAdapter adapter = new MovimentoAdapter(getApplicationContext(), lista, PrincipalActivity.this);
            lvMovimentos.setAdapter(adapter);

            float entradas = 0;
            float saidas = 0;
            for (Movimento mov : lista) {
                if (mov.getTipo() == Tipo.ENTRADA) {
                    entradas += mov.getValor();
                } else {
                    saidas += mov.getValor();
                }
            }

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            tvEntradas.setText(nf.format(entradas));
            tvSaidas.setText(nf.format(saidas));
            tvSaldo.setText(nf.format(entradas - saidas));
        }
    }
}
