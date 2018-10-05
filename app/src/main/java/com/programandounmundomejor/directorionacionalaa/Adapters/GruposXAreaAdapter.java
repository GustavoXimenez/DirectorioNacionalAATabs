package com.programandounmundomejor.directorionacionalaa.Adapters;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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

import com.programandounmundomejor.directorionacionalaa.Clases.Callback;
import com.programandounmundomejor.directorionacionalaa.Clases.PostRequest;
import com.programandounmundomejor.directorionacionalaa.GrupoActivity;
import com.programandounmundomejor.directorionacionalaa.GruposXAreaActivity;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXArea;
import com.programandounmundomejor.directorionacionalaa.Models.GruposXCP;
import com.programandounmundomejor.directorionacionalaa.R;

import java.util.List;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGrupo;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGruposXArea;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.signature;

public class GruposXAreaAdapter extends RecyclerView.Adapter<GruposXAreaAdapter.ViewHolder> {

    private Context context;
    private List<GruposXArea> groupList;
    private Activity activityGrupo;

    private PostRequest postRequest = new PostRequest();
    private Callback callback = new Callback();

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

        TextView txtNombreGrupo = viewHolder.txtNombreGrupo;
        TextView txtValueColonia = viewHolder.txtValueColonia;
        LinearLayout linear = viewHolder.linear;

        txtNombreGrupo.setText(nombreGrupo);
        txtValueColonia.setText(colonia);

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
        private TextView txtTitleNombreGrupo;
        private LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreGrupo = (TextView) itemView.findViewById(R.id.txtNombreGrupo);
            txtValueColonia = (TextView) itemView.findViewById(R.id.txtValueColonia);
            txtTitleNombreGrupo = (TextView) itemView.findViewById(R.id.txtTitleNombreGrupo);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int requestCode = getAdapterPosition();
                    getValuesGrupo(v, requestCode);
                }
            });
        }
    }

    private void getValuesGrupo(final View v, final int requestCode){
        Thread thread = new Thread(){
            @Override
            public void run() {
                final int idGrupo = lstGruposXArea.get(requestCode).getIdGrupo();
                final String nombreGrupo = lstGruposXArea.get(requestCode).getNombreGrupo();
                final int color = getValueColor(requestCode);
                final String response = postRequest.enviarPost("signature="+signature+"&idGrupo="+idGrupo, "searchGrupoXId.php");
                activityGrupo.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("grupo", response);
                        if(lstGrupo.size() > 0){
                            Activity activity = (AppCompatActivity) context;
                            Intent intent = new Intent(context, GrupoActivity.class);
                            intent.putExtra("IdGrupo", idGrupo);
                            intent.putExtra("NombreGrupo", nombreGrupo);
                            intent.putExtra("Color", color);
                            // Check if we're running on Android 5.0 or higher
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        activity,
                                        new Pair<View, String>(v.findViewById(R.id.linear), GrupoActivity.VIEW_NAME_HEADER_IMAGE),
                                        new Pair<View, String>(v.findViewById(R.id.txtTitleNombreGrupo), GrupoActivity.VIEW_NAME_HEADER_TITLE),
                                        new Pair<View, String>(v.findViewById(R.id.txtNombreGrupo), GrupoActivity.VIEW_NAME_HEADER_NAME));

                                //((AppCompatActivity) context).startActivityForResult(intent, requestCode, options.toBundle());
                                ActivityCompat.startActivity(context, intent, options.toBundle());
                            } else {
                                // Swap without transition
                                ((AppCompatActivity) context).startActivity(intent);
                            }
                        } else {
                            messageSnackbar("error en el servicio", "Intentar más tarde", v);
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

    private void messageSnackbar(String messageError, String messageButton, View v){
        Snackbar snackbar = Snackbar
                .make(v, messageError, Snackbar.LENGTH_LONG)
                .setAction(messageButton, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        snackbar.show();
    }
}
