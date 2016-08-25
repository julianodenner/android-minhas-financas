package com.jdenner.minhasfinancas.model;

/**
 * Created by Juliano on 04/08/2016.
 */
public enum Tipo {

    ENTRADA(1, "Entrada"),
    SAIDA(2, "Saída");

    private int codigo;
    private String descricao;

    Tipo(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Tipo get(int codigo) {

        for (Tipo tipo : Tipo.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }

        return null;
    }
}
