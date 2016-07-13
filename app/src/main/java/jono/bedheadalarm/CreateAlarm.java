package jono.bedheadalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
    }

    public void navigate(View view) {
        switch (view.getId()) {

            case (R.id.cGPSAlarm):
                Intent GPSAlarm = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(GPSAlarm);
                break;
            case (R.id.cTimeAlarm):
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent TimeAlarm = new Intent(getApplicationContext(), AddTimeAlarm.class);

                TimeAlarm.putExtras(dataBundle);
                startActivity(TimeAlarm);
                break;
        }
    }
}
