package dicoding.adrian.submission4.features.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import dicoding.adrian.submission4.R;
import dicoding.adrian.submission4.basic.SplashRightActivity;

public class AlarmReceiverRelease extends BroadcastReceiver {

    // Tags
    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";

    private final int ID_REPEATING = 201;

    // Constructor
    public AlarmReceiverRelease() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        // Get today's date
        Date rawDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(rawDate);

        // API Key
        final String API_KEY = "f733887094fe514518e8087c86f26c59";

        // URL
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + date + "&primary_release_date.lte=" + date;

        // Async HTTP
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    String movieReleaseTitle = responseObject.getJSONArray("results").getJSONObject(1).getString("title");
                    // Get message
                    String hasReleased = context.getString(R.string.hasReleased);
                    String message = movieReleaseTitle + hasReleased;
                    // Put title and id as notifId
                    String title = context.getString(R.string.release_reminder);
                    // Show notiffcation
                    showAlarmNotification(context, title, message);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    private void showAlarmNotification(Context context, String title, String message) {

        // Notification Channel
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "ReleaseAlarmManager channel";

        // NotificationManager Instance
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Intent
        Intent notificationIntent = new Intent(context, SplashRightActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        // Notification Properties Configuration
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movie_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);

        // Upper Version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(201, notification);
        }
    }

    public void setRepeatingAlarm(Context context, String time) {

        String TIME_FORMAT = "HH:mm";
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiverRelease.class);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "Release Reminder is enabled", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiverRelease.class);

        // Ids
        int ID_ONETIME = 200;

        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, "Release Reminder is disabled", Toast.LENGTH_SHORT).show();
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
