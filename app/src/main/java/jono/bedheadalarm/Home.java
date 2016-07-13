package jono.bedheadalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void navigate(View view) {
        switch (view.getId()) {
            case (R.id.cAlarm):
                 Intent cAlarm = new Intent(getApplicationContext(), CreateAlarm.class);
                 startActivity(cAlarm);
                break;
            case (R.id.vAlarm):
                 Intent vAlarm = new Intent(getApplicationContext(), AlarmView.class);
                 startActivity(vAlarm);
                break;
            case (R.id.vtimetable):
                Toast.makeText(getApplicationContext(), "Currently does nothing!", Toast.LENGTH_SHORT).show();
                // Intent vTimetable = new Intent(getApplicationContext(), .class);
                // startActivity(vTimetable);
                break;
            case (R.id.options):
                Toast.makeText(getApplicationContext(), "Also does nothing!", Toast.LENGTH_SHORT).show();
                // Intent options = new Intent(getApplicationContext(), .class);
                // startActivity(options);
                break;
        }
    }
}
