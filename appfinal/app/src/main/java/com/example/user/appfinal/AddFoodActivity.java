package com.example.user.appfinal;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.appfinal.data.FoodContract;
import com.example.user.appfinal.sync.ReminderUtilities;

import java.util.Calendar;


/**
 * Created by user on 2017/6/17.
 */

public class AddFoodActivity extends AppCompatActivity {
    private DatePicker mDatePicker;
    private Spinner spinner_alertTime;

    private String D;
    private String M;
    private String ch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_add);

        mDatePicker = (DatePicker) findViewById(R.id.datePicker_buy);


        spinner_alertTime = (Spinner)findViewById(R.id.spinner_alert);
        final String[] time_choice = {"3秒", "1小時", "4小時", "8小時"};
        ArrayAdapter<String> timeList = new ArrayAdapter<>(AddFoodActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                time_choice);

        spinner_alertTime.setAdapter(timeList);

        spinner_alertTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddFoodActivity.this, "你選的是" +  time_choice[position], Toast.LENGTH_SHORT).show();
                ch = time_choice[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void onClickAddFood(View view) {
        String input = ((EditText) findViewById(R.id.et_foodinput)).getText().toString();
        if (input.length() == 0) {
            return;
        }
        String remarkinput = ((EditText) findViewById(R.id.et_remarkinput)).getText().toString();

        String D = String.valueOf(mDatePicker.getDayOfMonth());
        String M = String.valueOf(mDatePicker.getMonth());
        String fullDay = M+"/"+D;

        Calendar c = Calendar.getInstance();
        int second =  c.get(Calendar.SECOND);
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int id = 8;

        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodContract.FoodEntry.COLUMN_FOOD_NAME,input);
        contentValues.put(FoodContract.FoodEntry.COLUMN_BUY_TIME, fullDay);
        contentValues.put(FoodContract.FoodEntry.COLUMN_ALERT_TIME, ch);
        contentValues.put(FoodContract.FoodEntry.COLUMN_REMARK,remarkinput);

        Uri uri = getContentResolver().insert(FoodContract.FoodEntry.CONTENT_URI, contentValues);

        ReminderUtilities.scheduleChargingReminder(this,id, hour, minute,second);
        finish();

    }
}
