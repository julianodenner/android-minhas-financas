package com.jdenner.minhasfinancas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jdenner.minhasfinancas.model.Movimento;

import java.util.List;

/**
 * Created by JuliMovimento on 31/05/2016.
 */
public class MovimentoAdapter extends BaseAdapter {


    private List<Movimento> data;
    private LayoutInflater inflater;
    private PrincipalActivity activity;
    private Context context;

    public MovimentoAdapter(Context context, List<Movimento> data, PrincipalActivity activity) {
        this.activity = activity;
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Movimento getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Movimento movimento = getItem(position);

        View view = inflater.inflate(R.layout.list_item, null);

        TextView tbGrupo = (TextView) view.findViewById(R.id.tvGrupo);
        tbGrupo.setText(movimento.getGrupo().getDescricao());


        TextView tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
        tvDescricao.setText(movimento.getDataFormatada());

        TextView tvValor = (TextView) view.findViewById(R.id.tvValor);
        tvValor.setText(movimento.getValorFormatoFinanceiro());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.detalhe(getItem(position));
                Toast.makeText(context, getItem(position).getDescricao(), Toast.LENGTH_SHORT);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.detalhe(getItem(position));
                return true;
            }
        });
        return view;
    }
}
