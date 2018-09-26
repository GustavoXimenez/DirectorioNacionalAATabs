package com.programandounmundomejor.directorionacionalaa.Adapters;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.programandounmundomejor.directorionacionalaa.Models.GruposXCP;
import com.programandounmundomejor.directorionacionalaa.R;

import java.util.List;

public class GruposXCPAdapter extends RecyclerView.Adapter<GruposXCPAdapter.ViewHolder> {

    private static final String DEBUG_TAG = "GruposXCPAdapter";

    private Context context;
    private List<GruposXCP> groupList;

    public GruposXCPAdapter(Context context, List<GruposXCP> groupList){
        this.context = context;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.holder_groupxcp, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String nombreGrupo = groupList.get(position).getNombreGrupo();
        String colonia = groupList.get(position).getColonia();

        TextView txtNombreGrupo = viewHolder.txtNombreGrupo;
        TextView txtValueColonia = viewHolder.txtValueColonia;

        txtNombreGrupo.setText(nombreGrupo);
        txtValueColonia.setText(colonia);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.itemView.clearAnimation();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        animateCircularReveal(viewHolder.itemView);
    }

    public void animateCircularReveal(View view) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    @Override
    public long getItemId(int position) {
        return groupList.get(position).getIdGrupo();
    }

    @Override
    public int getItemCount() {
        if(groupList.isEmpty()){
            return 0;
        } else {
            return groupList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNombreGrupo;
        private TextView txtValueColonia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreGrupo = (TextView) itemView.findViewById(R.id.txtNombreGrupo);
            txtValueColonia = (TextView) itemView.findViewById(R.id.txtValueColonia);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "hola", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
