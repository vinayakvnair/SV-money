package com.tkmdashboard.admins.currenc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Tony Davis on 02-03-2016.
 */
public class signupActivity extends Activity {

    EditText mUsername;
    EditText mPassword;
    EditText mpasswordcnf;
    EditText memail;
    EditText mcontact;

    EditText mdob;

    RadioButton mgenderm;
    RadioButton mgenderf;
    RadioButton mgendert;
    Button mRegisterbutton;
    String gender;
    String username, password, passwordcnf, email, contact, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mdob = (EditText) findViewById(R.id.dobid);

        mdob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(signupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }


    public void saveInfo(View view) {
        mUsername = (EditText) findViewById(R.id.usernameid);
        mPassword = (EditText) findViewById(R.id.passwordid);
        mpasswordcnf = (EditText) findViewById(R.id.passwordcnfid);
        memail = (EditText) findViewById(R.id.emailid);

        mcontact = (EditText) findViewById(R.id.contactid);

        mdob = (EditText) findViewById(R.id.dobid);

        mRegisterbutton = (Button) findViewById(R.id.registerid);
        mgenderm = (RadioButton) findViewById(R.id.maleid);
        mgenderf = (RadioButton) findViewById(R.id.femaleid);
        mgendert = (RadioButton) findViewById(R.id.transid);

        username = mUsername.getText().toString();
        password = mPassword.getText().toString();
        passwordcnf = mpasswordcnf.getText().toString();
        email = memail.getText().toString();
        contact = mcontact.getText().toString();
        dob = mdob.getText().toString();



        if (mgenderm.isChecked()) {
            gender = "Male";
        } else if (mgenderf.isChecked()) {
        gender = "Female";
    }
        else
        {
            gender="Transgender";
        }
        if (email.isEmpty() || password.isEmpty() || dob.isEmpty() || username.isEmpty() || contact.isEmpty() || passwordcnf.isEmpty() ||gender.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(signupActivity.this);
            builder.setMessage(R.string.signuperror)
                    .setTitle(R.string.serrortext)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
else {
            if (password.equals(passwordcnf))

            {
                BackgroundTask backgroundTask = new BackgroundTask();

                backgroundTask.execute(username,password, email, contact,dob, gender);
                finish();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(signupActivity.this);
                builder.setMessage("Passwords dont Match")
                        .setTitle("Sorry")
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mdob.setText(sdf.format(myCalendar.getTime()));
    }



        class BackgroundTask extends AsyncTask<String, Void, String> {
            String add_info_url;



            @Override
            protected void onPreExecute() {
                add_info_url = "http://tkmdashboard.in/add_info.php";

            }

            @Override

            protected String doInBackground(String... args) {


                String username, password, email, dob, gender, contact;
                username = args[0];
                password = args[1];
                email = args[2];
                contact = args[3];
                dob = args[4];

                gender =args[5];

                try {
                    URL url = new URL(add_info_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                            URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&" +
                            URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(dob, "UTF-8") + "&" +
                            URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
                    bufferedWriter.write(data_string);
                    bufferedWriter.flush();

                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return "Thank You! You Have Successfully Registered";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
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
    }


