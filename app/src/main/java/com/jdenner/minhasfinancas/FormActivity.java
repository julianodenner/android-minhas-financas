package com.jdenner.minhasfinancas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.jdenner.minhasfinancas.dao.MovimentoDAO;
import com.jdenner.minhasfinancas.model.Grupo;
import com.jdenner.minhasfinancas.model.Movimento;
import com.jdenner.minhasfinancas.model.Tipo;

public class FormActivity extends AppCompatActivity {

    private int ano;
    private int mes;

    private EditText txtValor;
    private EditText txtDescricao;
    private RadioGroup rgTipo;
    private RadioButton rbEntrada;
    private RadioButton rbSaida;
    private Spinner spnGrupo;
    private ArrayAdapter<Grupo> adapterGrupo;
    private CalendarView calData;
    private Movimento movimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        txtValor = (EditText) findViewById(R.id.txtValor);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        rgTipo = (RadioGroup) findViewById(R.id.rgTipo);
        rbEntrada = (RadioButton) findViewById(R.id.rbEntrada);
        rbSaida = (RadioButton) findViewById(R.id.rbSaida);
        spnGrupo = (Spinner) findViewById(R.id.spnGrupo);
        calData = (CalendarView) findViewById(R.id.calData);

        mes = getIntent().getExtras().getInt("mes");
        ano = getIntent().getExtras().getInt("ano");
        movimento = (Movimento) getIntent().getExtras().get("movimento");

        if(movimento.getCodigo() != 0) {
            txtValor.setText(movimento.getValorFormatoNumero());
            txtDescricao.setText(movimento.getDescricao());
            rbEntrada.setSelected(movimento.getTipo() == Tipo.ENTRADA);
            rbSaida.setSelected(movimento.getTipo() == Tipo.SAIDA);
            if (movimento.getTipo() == Tipo.ENTRADA) {
                handleRadEntrada(null);
            } else {
                handleRadSaida(null);
            }
            spnGrupo.setSelection(adapterGrupo.getPosition(movimento.getGrupo()));
        }
    }

    public void handleRadEntrada(View view) {
        adapterGrupo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Grupo.getListaEntradas());
        adapterGrupo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGrupo.setAdapter(adapterGrupo);
    }

    public void handleRadSaida(View view) {
        adapterGrupo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Grupo.getListaSaidas());
        adapterGrupo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGrupo.setAdapter(adapterGrupo);
    }

    public void handleBtnSalvar(View view) {
        try {
            movimento.setDescricao(txtDescricao.getText().toString());
            movimento.setValor(txtValor.getText().toString());
            movimento.setData(calData.getDate());
            movimento.setTipo(rgTipo.getCheckedRadioButtonId() == R.id.rbEntrada ? Tipo.ENTRADA : Tipo.SAIDA);
            movimento.setGrupo((Grupo) spnGrupo.getSelectedItem());

            MovimentoDAO.salvar(getApplicationContext(), movimento);

            Intent intent = new Intent(this, PrincipalActivity.class);
            intent.putExtra("ano", ano);
            intent.putExtra("mes", mes);
            startActivity(intent);
            this.finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
