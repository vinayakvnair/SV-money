package com.tkmdashboard.admins.currenc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class credit extends AppCompatActivity {
    EditText msource;
    EditText mamount;
    EditText cdob;

    public static final String CREDIT = "0";


    RadioButton my;
    RadioButton mm;
    String source,cob,amountcc,y,m;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        cdob = (EditText) findViewById(R.id.cobid);

        cdob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(credit.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    public void creditInfo(View view) {
        msource = (EditText) findViewById(R.id.sourceid);
        mamount = (EditText) findViewById(R.id.amountid);
        cdob = (EditText) findViewById(R.id.cobid);

        my = (RadioButton) findViewById(R.id.yyid);
        mm = (RadioButton) findViewById(R.id.mmid);

        source = msource.getText().toString();
        int converted2=Integer.parseInt(mamount.getText().toString());


        SharedPreferences sp = getSharedPreferences("credits", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("credit", converted2);
        editor.commit();

        if (my.isChecked()) {
            y = "Yearly";
        } else if (mm.isChecked()) {
            m = "Monthly";
        }
        Intent intent = new Intent(credit.this,MenuActivity.class);
        intent.putExtra(CREDIT, converted2);
        startActivity(intent);
    }

        Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        cdob.setText(sdf.format(myCalendar.getTime()));
    }

}
