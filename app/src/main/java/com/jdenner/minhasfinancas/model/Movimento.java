package com.jdenner.minhasfinancas.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Juliano on 04/08/2016.
 */
public class Movimento implements Serializable {

    private int codigo;
    private String descricao;
    private float valor;
    private Date data;
    private Tipo tipo;
    private Grupo grupo;

    public Movimento() {
        this.codigo = 0;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) throws Exception {
        if (descricao.trim().length() < 3) {
            throw new Exception("Descrição muito curta!");
        }
        this.descricao = descricao.trim();
    }

    public float getValor() {
        return valor;
    }

    public String getValorFormatoFinanceiro() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(getValor());
    }

    public String getValorFormatoNumero() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(getValor());
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public String getDataBanco() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(getData());
    }

    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(getData());
    }

    public void setData(long milisegundos) {
        this.data = new Date(milisegundos);
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setValor(String texto) throws Exception {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            setValor(nf.parse(texto).floatValue());
        } catch (Exception e) {
            throw new Exception("Valor inválido!");
        }
    }
}
