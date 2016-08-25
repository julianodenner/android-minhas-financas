package com.jdenner.minhasfinancas.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juliano on 04/08/2016.
 */
public enum Grupo {


    SALARIO(1001, "Salário", Tipo.ENTRADA),
    VENDA(1002, "Venda", Tipo.ENTRADA),
    ALUGUEL(1003, "Aluguel", Tipo.ENTRADA),
    OUTRAS_ENTRADA(1999, "Outro", Tipo.ENTRADA),
    ALIMENTACAO(2001, "Alimentação", Tipo.SAIDA),
    MORADIA(2002, "Moradia", Tipo.SAIDA),
    TRANSPORTE(2003, "Transporte", Tipo.SAIDA),
    EDUCACAO(2004, "Educação", Tipo.SAIDA),
    SAUDE(2005, "Saúde", Tipo.SAIDA),
    LAZER(2006, "Lazer", Tipo.SAIDA),
    OUTRAS_SAIDA(2999, "Outro", Tipo.SAIDA);

    private int codigo;
    private String descricao;
    private Tipo tipo;

    Grupo(int codigo, String descricao, Tipo tipo) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public static Grupo[] getListaEntradas() {
        List<Grupo> lista = new ArrayList<>();
        for (Grupo grupo : Grupo.values()) {
            if (grupo.getTipo() == Tipo.ENTRADA) {
                lista.add(grupo);
            }
        }
        Grupo arr[] = new Grupo[lista.size()];
        return lista.toArray(arr);
    }

    public static Grupo[] getListaSaidas() {
        List<Grupo> lista = new ArrayList<>();
        for (Grupo grupo : values()) {
            if (grupo.getTipo() == Tipo.SAIDA) {
                lista.add(grupo);
            }
        }
        Grupo arr[] = new Grupo[lista.size()];
        return lista.toArray(arr);
    }

    public static Grupo get(int codigo) {

        for (Grupo grupo : values()) {
            if (grupo.getCodigo() == codigo) {
                return grupo;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
