package dicoding.adrian.submission4.features.reminder;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import dicoding.adrian.submission4.R;

public class ReminderActivity extends AppCompatActivity {

    // Widget Variables
    Switch swRelease;
    Switch swDaily;

    // Alarm Receiver Declaration
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Alarm Receiver Instance
        alarmReceiver = new AlarmReceiver();

        // Set Toolbar as Action Bar
        Toolbar toolbar = findViewById(R.id.toolbar_reminder);
        setSupportActionBar(toolbar);
        // Add Back Arrow to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // Remove Toolbar Title
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Cast The Widgets
        swRelease = findViewById(R.id.switchReleaseReminder);
        swDaily = findViewById(R.id.switchDailyReminder);

        // Get the Switch State from Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("dicoding.submission4.adrian", MODE_PRIVATE);
        swDaily.setChecked(sharedPreferences.getBoolean("daily", false));
        swRelease.setChecked(sharedPreferences.getBoolean("release", false));

        // setOnCheckedChangeListener for swDaily
        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The Activity if True
                    String repeatTime = "07:00";
                    String repeatMessage = ReminderActivity.this.getString(R.string.check_daily);
                    alarmReceiver.setRepeatingAlarm(ReminderActivity.this, AlarmReceiver.TYPE_REPEATING,
                            repeatTime, repeatMessage);
                    // Save The Switch's State Using Shared Preferences
                    SharedPreferences.Editor editor = getSharedPreferences("dicoding.submission4.adrian", MODE_PRIVATE).edit();
                    editor.putBoolean("daily", true);
                    editor.apply();
                } else {
                    // The Activity if False
                    alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_REPEATING);
                    // Save The Switch's State Using Shared Preferences
                    SharedPreferences.Editor editor = getSharedPreferences("dicoding.submission4.adrian", MODE_PRIVATE).edit();
                    editor.putBoolean("daily", false);
                    editor.apply();
                }
            }
        });

        // setOnCheckedChangeListener for swRelease
        swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The Activity if True
                    Toast.makeText(ReminderActivity.this, "Release reminder active", Toast.LENGTH_SHORT).show();
                    // Save The Switch's State Using Shared Preferences
                    SharedPreferences.Editor editor = getSharedPreferences("dicoding.submission4.adrian", MODE_PRIVATE).edit();
                    editor.putBoolean("release", true);
                    editor.apply();
                } else {
                    // The Activity if False
                    Toast.makeText(ReminderActivity.this, "Release reminder deactive", Toast.LENGTH_SHORT).show();
                    // Save The Switch's State Using Shared Preferences
                    SharedPreferences.Editor editor = getSharedPreferences("dicoding.submission4.adrian", MODE_PRIVATE).edit();
                    editor.putBoolean("release", false);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle Back Arrow Click Here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
