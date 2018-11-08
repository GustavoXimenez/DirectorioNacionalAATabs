package com.programandounmundomejor.directorionacionalaa;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.programandounmundomejor.directorionacionalaa.Clases.Callback;
import com.programandounmundomejor.directorionacionalaa.Clases.PostRequest;

import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstGrupo;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.lstHorarios;
import static com.programandounmundomejor.directorionacionalaa.Clases.Global.signature;

public class GrupoActivity extends AppCompatActivity {

    private Intent intent;
    private LinearLayout linear;
    private TextView txtTitleNombreGrupo, txtNombreGrupo, txtNumero, txtCalle, txtColonia, txtMunicipio, txtEstado, txtReferencias, txtArea,
            txtDistrito, txtFechaInicio, txtLunes, txtMartes, txtMiercoles, txtJueves, txtViernes, txtSabado, txtDomingo;
    private Context context;

    private String nombreGrupo;
    private int colorGrupo;
    private int idGrupo;

    private PostRequest postRequest = new PostRequest();
    private Callback callback = new Callback();

    // Extra name for the ID parameter
    public static final String EXTRA_PARAM_ID = "detail:_id";
    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    // View name of the header title. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";
    // View name of the header title. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_NAME = "detail:header:name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);

        context = this;
        intent = getIntent();

        initView();

    }

    private void initView(){
        nombreGrupo = intent.getStringExtra("NombreGrupo");
        colorGrupo = intent.getIntExtra("Color", R.color.colorBlueOne);
        idGrupo = intent.getIntExtra("IdGrupo", 0);

        initElements();

        ViewCompat.setTransitionName(linear, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(txtTitleNombreGrupo, VIEW_NAME_HEADER_TITLE);
        ViewCompat.setTransitionName(txtNombreGrupo, VIEW_NAME_HEADER_NAME);

        assignamentValues();

        getHorarios(idGrupo);
    }

    private void initElements(){
        linear = (LinearLayout) findViewById(R.id.initial);
        txtTitleNombreGrupo = (TextView) findViewById(R.id.txtTitleNombreGrupo);
        txtNombreGrupo = (TextView) findViewById(R.id.txtNombreGrupo);
        txtCalle = (TextView) findViewById(R.id.txtCalle);
        txtNumero = (TextView) findViewById(R.id.txtNumero);
        txtColonia = (TextView) findViewById(R.id.txtColonia);
        txtMunicipio = (TextView) findViewById(R.id.txtMunicipio);
        txtEstado = (TextView) findViewById(R.id.txtEstado);
        txtReferencias = (TextView) findViewById(R.id.txtReferencias);
        txtArea = (TextView) findViewById(R.id.txtArea);
        txtDistrito = (TextView) findViewById(R.id.txtDistrito);
        txtFechaInicio = (TextView) findViewById(R.id.txtFechaInicio);
        txtLunes = (TextView) findViewById(R.id.txtLunes);
        txtMartes = (TextView) findViewById(R.id.txtMartes);
        txtMiercoles = (TextView) findViewById(R.id.txtMiercoles);
        txtJueves = (TextView) findViewById(R.id.txtJueves);
        txtViernes = (TextView) findViewById(R.id.txtViernes);
        txtSabado = (TextView) findViewById(R.id.txtSabado);
        txtDomingo = (TextView) findViewById(R.id.txtDomingo);
    }

    private void assignamentValues(){
        linear.setBackgroundColor(context.getResources().getColor(colorGrupo));
        txtNombreGrupo.setText(nombreGrupo);
        txtCalle.setText(lstGrupo.get(0).getCalle());
        txtNumero.setText(lstGrupo.get(0).getNumExt());
        txtColonia.setText(lstGrupo.get(0).getColonia());
        txtMunicipio.setText(lstGrupo.get(0).getMnpioDel());
        txtEstado.setText(lstGrupo.get(0).getEstado());
        txtReferencias.setText(lstGrupo.get(0).getReferencia());
        txtArea.setText(lstGrupo.get(0).getDescArea());
        txtDistrito.setText(lstGrupo.get(0).getIdAreaDistrito());
        txtFechaInicio.setText(lstGrupo.get(0).getFechaInicio().split(" ")[0]);
    }

    private void getHorarios(final int idGrupo){
        Thread thread = new Thread(){
            @Override
            public void run() {
                final String response = postRequest.enviarPost("signature="+signature+"&idGrupo="+idGrupo, "searchHorariosXIdGrupo.php");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.processingResult("horarios", response);
                        if(lstHorarios.size() > 0){
                            txtLunes.setText(lstHorarios.get(0).get(4));
                            txtMartes.setText(lstHorarios.get(1).get(4));
                            txtMiercoles.setText(lstHorarios.get(2).get(4));
                            txtJueves.setText(lstHorarios.get(3).get(4));
                            txtViernes.setText(lstHorarios.get(4).get(4));
                            txtSabado.setText(lstHorarios.get(5).get(4));
                            txtDomingo.setText(lstHorarios.get(6).get(4));
                        } else {
                            Toast.makeText(context, R.string.grupo_txterrorService, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
