package org.perogers.android.wearablenotification;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    // Key for the string that's delivered in the action's intent
    private static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    private static int sNotificationNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void notifyButton(View view) {
        Log.i(TAG, "Got click");
        fireNotification();
    }


    private void fireNotification() {
        int notificationId = 001;

        // Build intent for notification content
        Intent resultIntent = new Intent(this, ResultActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this, 0, resultIntent, 0);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_notifications_white_18dp,
                        getString(R.string.notification_label),
                        resultPendingIntent)
                        .build();



        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_white_18dp)
                        .setContentTitle( getString(R.string.notification_title) + " - " + sNotificationNumber++ )
                        .setContentText( getString(R.string.notification_content))
                        .extend(new NotificationCompat.WearableExtender().addAction( action ));

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());

        // Voice response
    /*
        String replyLabel = getResources().getString(R.string.reply_label);

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .build();
*/
        Log.i(TAG, "Notification sent");


    }



    /**
     * Obtain the intent that started this activity by calling
     * Activity.getIntent() and pass it into this method to
     * get the associated voice input string.
     */
    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(EXTRA_VOICE_REPLY);
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
