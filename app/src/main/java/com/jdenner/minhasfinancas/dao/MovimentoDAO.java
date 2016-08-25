package com.jdenner.minhasfinancas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jdenner.minhasfinancas.model.Grupo;
import com.jdenner.minhasfinancas.model.Movimento;
import com.jdenner.minhasfinancas.model.Tipo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Juliano on 04/08/2016.
 */
public class MovimentoDAO {

    public static void salvar(Context context, Movimento movimento) throws Exception {
        if (movimento.getCodigo() == 0) {
            inserir(context, movimento);
        } else {
            alterar(context, movimento);
        }
    }

    public static void inserir(Context context, Movimento movimento) throws Exception {
        String table = "tb_movimento";
        ContentValues values = new ContentValues();
        values.put("descricao", movimento.getDescricao());
        values.put("valor", movimento.getValor());
        values.put("data", movimento.getDataBanco());
        values.put("tipo", movimento.getTipo().getCodigo());
        values.put("grupo", movimento.getGrupo().getCodigo());
        ConexaoSQLite con = new ConexaoSQLite(context);
        SQLiteDatabase db = con.getWritableDatabase();

        boolean sucesso = db.insert(table, null, values) > 0;
        db.close();

        if (!sucesso) {
            throw new Exception("O registro não foi inserido!");
        }
    }

    public static void alterar(Context context, Movimento movimento) throws Exception {
        String table = "tb_movimento";

        ContentValues values = new ContentValues();
        values.put("descricao", movimento.getDescricao());
        values.put("valor", movimento.getValor());
        values.put("data", movimento.getDataBanco());
        values.put("tipo", movimento.getTipo().getCodigo());
        values.put("grupo", movimento.getGrupo().getCodigo());

        String whereClause = "codigo=?";
        String whereArgs[] = {String.valueOf(movimento.getCodigo())};

        ConexaoSQLite con = new ConexaoSQLite(context);
        SQLiteDatabase db = con.getWritableDatabase();

        boolean sucesso = db.update(table, values, whereClause, whereArgs) > 0;
        db.close();

        if (!sucesso) {
            throw new Exception("O registro não foi alterado!");
        }
    }

    public static List<Movimento> listar(Context context, int ano, int mes) throws Exception {
        String table = "tb_movimento";

        String[] columns = new String[]{"codigo", "descricao", "valor", "data", "tipo", "grupo"};

        Calendar cal = Calendar.getInstance();
        cal.set(cal.YEAR, ano);
        cal.set(cal.MONTH, mes);
        cal.set(cal.DAY_OF_MONTH, 1);
        Date dataDe = cal.getTime();

        cal.add(cal.MONTH, 1);
        cal.add(cal.DAY_OF_MONTH, -1);
        Date dataAte = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selection = "data BETWEEN ? AND ?";
        String[] selectionArgs = new String[]{sdf.format(dataDe), sdf.format(dataAte)};

        String order = "data";

        ConexaoSQLite conn = new ConexaoSQLite(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, order);

        List<Movimento> lista = new ArrayList<>();
        while (cursor.moveToNext()) {
            Movimento movimento = new Movimento();
            movimento.setCodigo(cursor.getInt(0));
            movimento.setDescricao(cursor.getString(1));
            movimento.setValor(cursor.getFloat(2));
            movimento.setData(sdf.parse(cursor.getString(3)));
            movimento.setTipo(Tipo.get(cursor.getInt(4)));
            movimento.setGrupo(Grupo.get(cursor.getInt(5)));
            lista.add(movimento);
        }

        return lista;
    }
}
