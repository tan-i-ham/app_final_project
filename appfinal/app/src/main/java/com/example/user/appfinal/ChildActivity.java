package com.example.user.appfinal;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.appfinal.data.FoodContract;

/**
 * Created by user on 2017/6/17.
 */

public class ChildActivity extends AppCompatActivity implements FoodRecyclerViewAdapter.ItemClickListener {
    private FoodRecyclerViewAdapter mAdapter;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_show);

        String[] data = {"1", "2", "3", "4",};

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mAdapter = new FoodRecyclerViewAdapter(this, data);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);


        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);


    }

    public void onFoodAddedButtonClicked(View view) {
        createDialog().show();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        builder.setMessage(R.string.message_add_task);
        View contentView = inflater.inflate(R.layout.food_add, null);
        builder.setView(contentView);
        final EditText foodName = (EditText) contentView.findViewById(R.id.et_foodinput);
        final DatePicker datePicker = (DatePicker) contentView.findViewById(R.id.datePicker_buy);
        final Spinner spinner_alertTime = (Spinner) contentView.findViewById(R.id.spinner_alert);

        final String[] time_choice = {"3秒", "1小時", "4小時", "8小時"};
        ArrayAdapter<String> timeList = new ArrayAdapter<>(ChildActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                time_choice);

        spinner_alertTime.setAdapter(timeList);

        spinner_alertTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChildActivity.this, "你選的是" +  time_choice[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        builder.setPositiveButton(R.string.message_sure, new DialogInterface.OnClickListener() {
            // 如果使用者確認加入Task，就透過ContentProvider存入資料庫，然後設定提醒~
            public void onClick(DialogInterface dialog, int whichButton) {
                ContentValues values = new ContentValues();
                values.put(FoodContract.FoodEntry.COLUMN_FOOD_NAME, String.valueOf(foodName.getText()));
                values.put(FoodContract.FoodEntry.COLUMN_BUY_TIME, String.valueOf(datePicker.getMonth() + datePicker.getFirstDayOfWeek()));

                Uri id = getContentResolver().insert(FoodContract.BASE_CONTENT_URI, values);
//                getLoaderManager().restartLoader(LOADER_SEARCH_TASKS, null, ChildActivity.this);
//                startAlarm((int) ContentUris.parseId(id), timePicker.getHour(), timePicker.getMinute());
            }
        });
        builder.setNegativeButton(R.string.message_cancel, null);
        return builder.create();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_foodshow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_BacktoMain) {
            Context context =ChildActivity.this;
            Class destinationActivity = MainActivity.class;
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            startActivity(startChildActivityIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + mAdapter.getItem(position) + ", which is at cell position " + position);
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + position + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();

    }
}
