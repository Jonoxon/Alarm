package jono.bedheadalarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;


import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {
    private DBHelper mydb;

    TextView nam;
    TextView ringtone;
    Switch vibrate;
    Integer vib;
    TimePicker timePicker;
    Calendar calendar;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        nam = (TextView) findViewById(R.id.editTextName);
        vibrate = (Switch) findViewById(R.id.vibrateToggle);
        ringtone = (TextView) findViewById(R.id.editTextRingtone);

        vibrate.setChecked(false);

        mydb = new DBHelper(this);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);

        Button delete = (Button)findViewById(R.id.DeleteAlarm);
        TextView alarmtitle = (TextView) findViewById(R.id.AlarmTitle);
        delete.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //if you are editing an alarm
                delete.setVisibility(View.VISIBLE);
                alarmtitle.setText("Edit Alarm");

                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String name = rs.getString(rs.getColumnIndex(DBHelper.ALARMSDATABASE_COLUMN_NAME));
                String ring = rs.getString(rs.getColumnIndex(DBHelper.ALARMSDATABASE_COLUMN_RINGTONE));
                Integer oldvib = rs.getInt(rs.getColumnIndex(DBHelper.ALARMSDATABASE_COLUMN_VIBRATE));
                Integer oldhour = rs.getInt(rs.getColumnIndex(DBHelper.ALARMSDATABASE_COLUMN_HOURS));
                Integer oldminute = rs.getInt(rs.getColumnIndex(DBHelper.ALARMSDATABASE_COLUMN_MINUTES));

                if (!rs.isClosed()) {
                    rs.close();
                }

                nam.setText(name);
                ringtone.setText(ring);
                timePicker.setCurrentHour(oldhour);
                timePicker.setCurrentMinute(oldminute);


                if (oldvib == 1) {
                    vibrate.setChecked(true);
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
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(AddAlarm.this, AlarmView.class);
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
                }
                else {
                }
                break;
            case R.id.checkbox_mon: if (checked){
                }
                else {
                }
                break;
            case R.id.checkbox_tue: if (checked){
                }
                else {
                }
                break;
            case R.id.checkbox_wed: if (checked){
                }
                else {
                }
                break;
            case R.id.checkbox_thu: if (checked){
                }
                else {
                }
                break;
            case R.id.checkbox_fri: if (checked){
                }
                else {
                }
                break;
            case R.id.checkbox_sat: if (checked){
                }
                else {
                }
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
                AlarmTrigger.TimeCreate(timePicker.getCurrentHour(),timePicker.getCurrentMinute(), specifiedDay );
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
        makeAlarmIntents();
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,nam.getText().toString(),vib,ringtone.getText().toString(),timePicker.getCurrentHour(),timePicker.getCurrentMinute())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),AlarmView.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertContact(nam.getText().toString(),vib,ringtone.getText().toString(),timePicker.getCurrentHour(),timePicker.getCurrentMinute())){
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