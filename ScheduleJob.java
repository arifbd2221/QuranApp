package qapp.mangosoft.com.qappproject;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by User on 2/26/2018.
 */

public class ScheduleJob extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {

        //getApplicationContext().startService(new Intent(getApplicationContext(),MainActivity.class));
        //Toast.makeText(getApplicationContext(),"Im JobScheduler",Toast.LENGTH_SHORT).show();




        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent = new Intent(this, GuidanceOfTheDay.class);
        repeating_intent.putExtra("data","ScheduleJob");
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Verse Of The Day")
                .setContentText("See what it says...")
                .setAutoCancel(true);

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.setSound(sound);

        notificationManager.notify(100, notification.build());


        jobFinished(params,false);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
