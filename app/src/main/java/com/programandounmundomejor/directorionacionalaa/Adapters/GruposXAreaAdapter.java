package com.programandounmundomejor.directorionacionalaa.Adapters;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.programandounmundomejor.directorionacionalaa.GrupoActivity;
import com.programandounmundomejor.directorionacionalaa.GruposXAreaActivity;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXArea;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXCP;
import com.programandounmundomejor.directorionacionalaa.R;

import java.util.List;

public class GruposXAreaAdapter extends RecyclerView.Adapter<GruposXAreaAdapter.ViewHolder> {

    private static final String DEBUG_TAG = "GruposXAreaAdapter";

    private Context context;
    private List<GruposXArea> groupList;
    private Activity activityGrupo;

    public GruposXAreaAdapter(Activity activity, Context context, List<GruposXArea> groupList){
        this.context = context;
        this.groupList = groupList;
        this.activityGrupo = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.holder_groupxarea, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String nombreGrupo = groupList.get(position).getNombreGrupo();
        String colonia = groupList.get(position).getColonia();
        String municipio = groupList.get(position).getMunicipio();

        TextView txtNombreGrupo = viewHolder.txtNombreGrupo;
        TextView txtValueColonia = viewHolder.txtValueColonia;
        TextView txtValueMunicipio = viewHolder.txtValueMunicipio;
        LinearLayout linear = viewHolder.linear;

        txtNombreGrupo.setText(nombreGrupo);
        txtValueColonia.setText(colonia);
        txtValueMunicipio.setText(municipio);

        int color = getValueColor(position);
        linear.setBackgroundColor(context.getResources().getColor(color));
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
        private TextView txtValueMunicipio;
        private LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreGrupo = (TextView) itemView.findViewById(R.id.txtNombreGrupo);
            txtValueColonia = (TextView) itemView.findViewById(R.id.txtValueColonia);
            txtValueMunicipio = (TextView) itemView.findViewById(R.id.txtValueMunicipio);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Pair<View, String> p1 = Pair.create((View) txtNombreGrupo, GruposXAreaActivity.TRANSITION_INITIAL);
                    Pair<View, String> p2 = Pair.create((View) txtValueColonia, GruposXAreaActivity.TRANSITION_NAME);
                    Pair<View, String> p3 = Pair.create((View) txtValueMunicipio, GruposXAreaActivity.TRANSITION_DELETE_BUTTON);

                    ActivityOptionsCompat options;
                    Activity activity = (AppCompatActivity) context;
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1, p2, p3);

                    Intent intent = new Intent(context, GrupoActivity.class);
                    int requestCode = getAdapterPosition();
                    ((AppCompatActivity) context).startActivityForResult(intent, requestCode, options.toBundle());
                }
            });
        }
    }

    private int getValueColor(int position){
        int ultimoDigito = position%10;
        switch (ultimoDigito){
            case 0:
                return R.color.colorBlueOne;
            case 1:
                return R.color.colorBlueTwo;
            case 2:
                return R.color.colorBlueThree;
            case 3:
                return R.color.colorBlueFour;
            case 4:
                return R.color.colorBlueFive;
            case 5:
                return R.color.colorBlueOne;
            case 6:
                return R.color.colorBlueTwo;
            case 7:
                return R.color.colorBlueThree;
            case 8:
                return R.color.colorBlueFour;
            case 9:
                return R.color.colorBlueFive;
        }
        return 0;
    }
}
