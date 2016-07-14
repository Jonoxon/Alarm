package jono.bedheadalarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import java.util.Calendar;

public class AddGPSAlarm extends AppCompatActivity {
    private DBHelper mydb;

    TextView nam;
    Switch vibrate;
    Switch trigger;
    Integer vib;
    Integer trg;
    Integer daysofweek;
    Integer radius;
    Double lon;
    Double lat;
    Button numberpicker;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gpsalarm);
        nam = (TextView) findViewById(R.id.editTextName);
        vibrate = (Switch) findViewById(R.id.vibrateToggle);
        trigger = (Switch) findViewById(R.id.triggerToggle);
        Button numberpicker = (Button) findViewById(R.id.bpickerdisplay);

        vibrate.setChecked(false);
        trigger.setChecked(false);

        mydb = new DBHelper(this);

        Button delete = (Button)findViewById(R.id.DeleteAlarm);
        TextView alarmtitle = (TextView) findViewById(R.id.AlarmTitle);
        delete.setVisibility(View.INVISIBLE);

        numberpicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showpicker();
            }
        });


        Bundle extras = getIntent().getExtras();
        lon = extras.getDouble("lon");
        lat = extras.getDouble("lat");

        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //if you are editing an alarm
                delete.setVisibility(View.VISIBLE);
                alarmtitle.setText("Edit GPS Alarm");

                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String name = rs.getString(rs.getColumnIndex(DBHelper.ALARMDATABASE_COLUMN_NAME));
                Integer oldvib = rs.getInt(rs.getColumnIndex(DBHelper.ALARMDATABASE_COLUMN_VIBRATE));
                Integer oldtrg = rs.getInt(rs.getColumnIndex(DBHelper.ALARMDATABASE_COLUMN_TRIGGER));

                if (!rs.isClosed()) {
                    rs.close();
                }

                nam.setText(name);


                if (oldvib == 1) {
                    vibrate.setChecked(true);
                }
                if (oldtrg == 1) {
                    trigger.setChecked(true);
                }
            }
        }



        //attach a listener to check for changes in state
        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vibrate.setChecked(true);
                    vib = 1;
                    Toast.makeText(getApplicationContext(), "Activated", Toast.LENGTH_SHORT).show();
                } else {
                    vibrate.setChecked(false);
                    vib = 0;
                    Toast.makeText(getApplicationContext(), "Deactivated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        trigger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    trigger.setChecked(true);
                    trg = 1;
                    Toast.makeText(getApplicationContext(), "Activated", Toast.LENGTH_SHORT).show();
                } else {
                    vibrate.setChecked(false);
                    trg = 0;
                    Toast.makeText(getApplicationContext(), "Deactivated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showpicker() {

        final Dialog d = new Dialog(AddGPSAlarm.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.numberpickerdialog);
        Button b1 = (Button) d.findViewById(R.id.bnumberp);
        final NumberPicker picker = (NumberPicker) d.findViewById(R.id.numberPicker1);
        picker.setMaxValue(25);
        picker.setMinValue(5);
        picker.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radius = picker.getValue()*10;
                String lol = Integer.toString(radius)+" metres";
                Button numberpicker = (Button) findViewById(R.id.bpickerdisplay);
                numberpicker.setText(lol);
                d.dismiss();
            }
        });
        d.show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(AddGPSAlarm.this, AlarmView.class);
            finish();
            startActivity(intent);
        }
        return true;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_sun: if (checked){
                daysofweek = daysofweek|Days.SUNDAY; }
            else {
                daysofweek = daysofweek^Days.SUNDAY;}
                break;
            case R.id.checkbox_mon: if (checked){
                daysofweek = daysofweek|Days.MONDAY; }
            else {
                daysofweek = daysofweek^Days.MONDAY;}
                break;
            case R.id.checkbox_tue: if (checked){
                daysofweek = daysofweek|Days.TUESDAY; }
            else {
                daysofweek = daysofweek^Days.TUESDAY;}
                break;
            case R.id.checkbox_wed: if (checked){
                daysofweek = daysofweek|Days.WEDNESDAY; }
            else {
                daysofweek = daysofweek^Days.WEDNESDAY;}
                break;
            case R.id.checkbox_thu: if (checked){
                daysofweek = daysofweek|Days.THURSDAY; }
            else {
                daysofweek = daysofweek^Days.THURSDAY;}
                break;
            case R.id.checkbox_fri: if (checked){
                daysofweek = daysofweek|Days.FRIDAY; }
            else {
                daysofweek = daysofweek^Days.FRIDAY;}
                break;
            case R.id.checkbox_sat: if (checked){
                daysofweek = daysofweek|Days.SATURDAY; }
            else {
                daysofweek = daysofweek^Days.SATURDAY;}
                break;
        }
    }

    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    public void makeAlarmIntents(ArrayList<Integer> repeatdays) {
        for (Integer item : repeatdays) {
            //do something with the list
            if (item == 1) {
                // non static method?????
                Integer specifiedDay = repeatdays.indexOf(item);
                //AlarmTrigger.TimeCreate(timePicker.getCurrentHour(),timePicker.getCurrentMinute(), specifiedDay );
            }
        }
    }

    public void delete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteContact)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.deleteContact(id_To_Update);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),AlarmView.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog d = builder.create();
        d.setTitle("Delete the Alarm?");
        d.show();
    }

    public void run(View view)
    {
        // not sure if putting here is good
        // makeAlarmIntents();
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateGPSContact(id_To_Update,nam.getText().toString(),lat, lon, radius, vib, trg, daysofweek)){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),AlarmView.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertGPSContact(nam.getText().toString(),lat, lon, radius, vib, trg, daysofweek)){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),AlarmView.class);
                startActivity(intent);
            }
        }
    }
}

