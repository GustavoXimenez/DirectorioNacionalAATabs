package com.programandounmundomejor.directorionacionalaa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class GrupoActivity extends AppCompatActivity {

    private Intent intent;
    private TextView initialTextView;
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);

        intent = getIntent();
        String nameExtra = "Test";
        String initialExtra = "T";
        int colorExtra = R.color.colorBlueFive;

        nameEditText = (EditText) findViewById(R.id.name);
        initialTextView = (TextView) findViewById(R.id.initial);

        nameEditText.setText(nameExtra);
        nameEditText.setSelection(nameEditText.getText().length());
        initialTextView.setText(initialExtra);
        initialTextView.setBackgroundColor(getResources().getColor(colorExtra));
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
