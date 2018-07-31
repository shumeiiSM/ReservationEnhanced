package com.example.a17010233.reservationenhanced;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tvName;
    EditText etName;
    TextView tvTele;
    EditText etTele;
    TextView tvSize;
    EditText etSize;
    TextView tvDay;
    EditText etDay;
    TextView tvTime;
    EditText etTime;
    CheckBox smoke;
    Button btnReserve;
    Button btnReset;


    @Override
    protected void onPause() {
        super.onPause();

        String strName = etName.getText().toString();
        String strTele = etTele.getText().toString();
        String strSize = etSize.getText().toString();
        String strDay = etDay.getText().toString();
        String strTime = etTime.getText().toString();
        final CheckBox checkBox = findViewById(R.id.smoke_id);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        final SharedPreferences.Editor prefEdit = pref.edit();

        if(pref.contains("checked") && pref.getBoolean("checked",false) == true) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);

        }



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()) {
                    prefEdit.putBoolean("checked", true);
                    prefEdit.commit();
                }else{
                    prefEdit.putBoolean("unchecked", false);
                    prefEdit.commit();
                }
            }
        });

        prefEdit.putString("myName", strName);
        prefEdit.putString("myTele", strTele);
        prefEdit.putString("mySize", strSize);
        prefEdit.putString("myDay", strDay);
        prefEdit.putString("myTime", strTime);

        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String msg1 = pref.getString("myName", "");
        String msg2 = pref.getString("myTele", "");
        String msg3 = pref.getString("mySize", "");
        String msg4 = pref.getString("myDay", "");
        String msg5 = pref.getString("myTime", "");

        etName.setText(msg1);
        etTele.setText(msg2);
        etSize.setText(msg3);
        etDay.setText(msg4);
        etTime.setText(msg5);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.textViewName);
        etName = findViewById(R.id.editTextName);
        tvTele = findViewById(R.id.textViewTele);
        etTele = findViewById(R.id.editTextTele);
        tvSize = findViewById(R.id.textViewSize);
        etSize = findViewById(R.id.editTextSize);
        tvDay = findViewById(R.id.textViewDay);
        etDay =  findViewById(R.id.editTextDay);
        tvTime = findViewById(R.id.textViewTime);
        etTime = findViewById(R.id.editTextTime);
        smoke = findViewById(R.id.smoke_id);
        btnReserve = findViewById(R.id.buttonReserve);
        btnReset = findViewById(R.id.buttonReset);


        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        etDay.setText("Date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                };

                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                        myDateListener, mYear, mMonth, mDay);

                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        etTime.setText("Time: " + hourOfDay + ":" + minute);
                    }
                };

                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMin = c.get(Calendar.MINUTE);

                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,
                        myTimeListener, mHour, mMin,true);
                myTimeDialog.show();
            }
        });


        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyActivity", "Inside onClick()");
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.input, null);

                final TextView dpName = viewDialog.findViewById(R.id.textViewName);
                final TextView dpTele = viewDialog.findViewById(R.id.textViewTele);
                final TextView dpSmoke = viewDialog.findViewById(R.id.textViewSmoke);
                final TextView dpSize = viewDialog.findViewById(R.id.textViewSize);
                final TextView dpDate = viewDialog.findViewById(R.id.textViewDate);
                final TextView dpTime = viewDialog.findViewById(R.id.textViewTime);

                String nameS = etName.getText().toString();
                String teleS = etTele.getText().toString();
                String sizeS = etSize.getText().toString();
                String dateS = etDay.getText().toString();
                String timeS = etTime.getText().toString();

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");

                if (smoke.isChecked()) {
                    myBuilder.setMessage("New Reservation \nName: " + nameS +
                            "\nTelephone: " + teleS +
                            "\nSmoking: Yes"  +
                            "\nSize: " + sizeS +
                            "\n" + dateS +
                            "\n" + timeS);
                } else {
                    myBuilder.setMessage("New Reservation \nName: " + nameS +
                            "\nTelephone: " + teleS +
                            "\nSmoking: No" +
                            "\nSize: " + sizeS +
                            "\n" + dateS +
                            "\n" + timeS);
                }


                myBuilder.setCancelable(false);


                myBuilder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etName.setText("");
                etTele.setText("");
                etSize.setText("");

                smoke.setChecked(false);

                etDay.setText("");
                etTime.setText("");


            }

        });







    }
}



