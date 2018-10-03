package com.programandounmundomejor.directorionacionalaa.Adapters;

import android.animation.Animator;
import android.app.Activity;
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

import com.programandounmundomejor.directorionacionalaa.Clases.Callback;
import com.programandounmundomejor.directorionacionalaa.Clases.PostRequest;
import com.programandounmundomejor.directorionacionalaa.GrupoActivity;
import com.programandounmundomejor.directorionacionalaa.GruposXAreaActivity;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXEstado;
import com.programandounmundomejor.directorionacionalaa.R;

import java.util.List;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGrupo;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXEstado;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.signature;

public class GruposXEstadoAdapter extends RecyclerView.Adapter<GruposXEstadoAdapter.ViewHolder> {

    private static final String DEBUG_TAG = "GruposXCPAdapter";

    private Context context;
    private List<GruposXEstado> groupList;

    private PostRequest postRequest = new PostRequest();
    private Callback callback = new Callback();
    private Activity activityGrupo;

    public GruposXEstadoAdapter(Activity activity, Context context, List<GruposXEstado> groupList) {
        this.context = context;
        this.groupList = groupList;
        this.activityGrupo = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.holder_groupxestado, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
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
                    int requestCode = getAdapterPosition();

                    //Obtener los valores por default
                    int color = getValueColor(requestCode);
                    int idGrupo = lstGruposXEstado.get(requestCode).getIdGrupo();
                    String nombreGrupo = lstGruposXEstado.get(requestCode).getNombreGrupo();

                    Intent intent = new Intent(context, GrupoActivity.class);
                    intent.putExtra("NombreGrupo", nombreGrupo);
                    intent.putExtra("IdGrupo", idGrupo);
                    intent.putExtra("Color", color);

                    //((AppCompatActivity) context).startActivityForResult(intent, requestCode, options.toBundle());
                    getValuesGrupo(intent, requestCode, options, idGrupo);
                }
            });
        }
    }

    private void getValuesGrupo(final Intent intent, final int requestCode, final ActivityOptionsCompat options, final int idGrupo){
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost("signature="+signature+"&idGrupo="+idGrupo, "searchGrupoXId.php");
                activityGrupo.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("grupo", response);
                        if(lstGrupo.size() > 0){
                            //Creamos array de estados
                            ((AppCompatActivity) context).startActivityForResult(intent, requestCode, options.toBundle());
                        } else {
                            Toast.makeText(context, "error en el servicio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
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
