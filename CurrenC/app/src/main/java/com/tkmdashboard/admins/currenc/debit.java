package com.tkmdashboard.admins.currenc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class debit extends AppCompatActivity {
    String[] items = new String[] {"Education", "Transportation", "Rent","Bills","Grocery","Subscriptions","Eating","Others"};
Spinner sp;

    EditText dmamount;
    EditText ddob;

    public static final String DEBIT = "0";


    RadioButton my;
    RadioButton mm;
    String cat,dob,amountdd,y,m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit);
        sp = (Spinner) findViewById(R.id.spinner);
        sp.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items));
        ddob = (EditText) findViewById(R.id.ddobid);

        ddob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(debit.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void debitInfo(View view) {
      dmamount = (EditText) findViewById(R.id.amountdid);

        ddob = (EditText) findViewById(R.id.ddobid);

        my = (RadioButton) findViewById(R.id.yyid);
        mm = (RadioButton) findViewById(R.id.mmid);


        int converted=Integer.parseInt(dmamount.getText().toString());


        SharedPreferences sp1 = getSharedPreferences("debits", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();
        editor.putInt("debit", converted);
        editor.commit();
        dob = ddob.getText().toString();

        Intent intent = new Intent(debit.this,MenuActivity.class);
        intent.putExtra(DEBIT, converted);
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

        ddob.setText(sdf.format(myCalendar.getTime()));
    }

}
