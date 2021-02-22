package com.example.testapp5.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testapp5.Fragments.KgFragment;
import com.example.testapp5.R;

import java.util.Calendar;

public class CartActivity extends AppCompatActivity
{
    Spinner spPaymentOption;
    LinearLayout linearLayoutFlat, linearLayoutPercent;
    ImageButton imgbtnpercent,imgbtnExtraProducts, imgbtntimepicker, imgBtnMinusDate, imgBtnPlusDate;
    Button btnflatrs;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    TimePickerDialog timepickerdialog;
    EditText edttimepicker, edtdate;
    String format;
    DatePickerDialog datePickerDialog;
    String TAG = "CartActivity";
    TextView txtViewAll;

    String ProductName = "", ProductPrice = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        txtViewAll = findViewById(R.id.txtViewAll);

        spPaymentOption = findViewById(R.id.spPaymentOption);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.payment_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPaymentOption.setAdapter(adapter);

        btnflatrs = findViewById(R.id.btnflatrs);
        imgbtnpercent = findViewById(R.id.imgbtnpercent);
        edttimepicker =findViewById(R.id.edttimepicker);
        imgbtnExtraProducts = findViewById(R.id.imgbtnExtraProducts);
        imgbtntimepicker = findViewById(R.id.imgbtntimepicker);
        imgBtnMinusDate = findViewById(R.id.imgBtnMinusDate);
        imgBtnPlusDate = findViewById(R.id.imgBtnPlusDate);
        edtdate = findViewById(R.id.edtdate);

        linearLayoutFlat = findViewById(R.id.linearLayoutFlat);
        linearLayoutPercent = findViewById(R.id.linearLayoutPercent);

        btnflatrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                linearLayoutPercent.setVisibility(View.GONE);
                linearLayoutFlat.setVisibility(View.VISIBLE);
            }
        });

        imgbtnpercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                linearLayoutPercent.setVisibility(View.VISIBLE);
                linearLayoutFlat.setVisibility(View.GONE);
            }
        });

        imgbtnExtraProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LayoutInflater li = LayoutInflater.from(CartActivity.this);
                View mView = li.inflate(R.layout.add_extra_products, null);
                final AlertDialog alertDialogAndroid;

                final EditText edtProductName = mView.findViewById(R.id.edtProductName);
                final EditText edtProductPrice = mView.findViewById(R.id.edtProductPrice);
                final ImageView imgClose = mView.findViewById(R.id.imgClose);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(CartActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                ProductName = edtProductName.getText().toString().trim();
                                ProductPrice = edtProductPrice.getText().toString().trim();

                                Bundle bundle = new Bundle();
                                /*Intent i =new Intent(getApplicationContext(),SelectGarmentsActivity.class);
                                i.putExtra("ProductName",ProductName);
                                i.putExtra("ProductPrice",ProductPrice);
                                startActivity(i);
                                finish();*/
                                Fragment fragment = new KgFragment();
                                bundle.putString("ProductName",ProductName);
                                bundle.putString("ProductPrice",ProductPrice);
                                fragment.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                            }
                        })

                        .setNegativeButton("Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                alertDialogAndroid = alertDialogBuilderUserInput.create();

                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogAndroid.dismiss();
                    }
                });


                alertDialogAndroid.show();
            }
        });

        imgbtntimepicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(CartActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
                    {
                        if (hourOfDay == 0)
                        {
                            hourOfDay += 12;
                            format = "AM";
                        }
                        else if (hourOfDay == 12)
                        {
                            format = "PM";
                        }
                        else if (hourOfDay > 12)
                        {
                            hourOfDay -= 12;
                            format = "PM";
                        }
                        else
                        {
                            format = "AM";
                        }

                        edttimepicker.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });

        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(CartActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtdate.setText(month + "/" + day + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Working..",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
