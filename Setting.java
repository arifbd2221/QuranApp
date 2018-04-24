package qapp.mangosoft.com.qappproject;


import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import qapp.mangosoft.com.qappproject.R;

public class Setting extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime{

    Spinner language;
    Toolbar toolbar;
    SharedPreferences preferences,TSTATUS;
    SharedPreferences.Editor editor,statusEditor;
    TextView setNotification,ts,title;
    private JobScheduler jobScheduler;
    private int interval;
    private Switch tStat;
    private SharedPreferences preferencesLang;
    private Typeface face;
    private FragmentManager fragmentManager;
    private RangeTimePickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        preferencesLang=getSharedPreferences("DetectLanguage",MODE_PRIVATE);
        face= Typeface.createFromAsset(getAssets(), "kalpurush.ttf");
        jobScheduler= JobScheduler.getInstance(this);


        toolbar=findViewById(R.id.toolbar);
        title=toolbar.findViewById(R.id.toolbar_title);
        tStat=findViewById(R.id.train_status);
        setNotification=findViewById(R.id.set_notification);
        ts=findViewById(R.id.id2);


        TSTATUS=getSharedPreferences("TSTATUS",MODE_PRIVATE);
        preferences=getSharedPreferences("DetectLanguage",MODE_PRIVATE);
        editor=preferences.edit();
        statusEditor=TSTATUS.edit();

        language=findViewById(R.id.language);

        final String[] lang={"Choose Language","English","Bangla"};





        if (preferencesLang.getInt("Index",0) == 2){

            toolbar.setTitle("");
            title.setTypeface(face);
            title.setText(R.string.bd_setting);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setNotification.setTypeface(face);
            ts.setTypeface(face);
            setNotification.setText(R.string.bd_notific);
            ts.setText(R.string.bd_tarin);

        }else{

            toolbar.setTitle("");
            title.setText("Setting");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            setNotification.setText("Set Scheduling Notification");
            ts.setText("Training Status");

        }


        if(preferences.getBoolean("status",false)){
            tStat.setChecked(true);
        }else{
            tStat.setChecked(false);
        }


        tStat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    statusEditor.putBoolean("status",true);
                    statusEditor.commit();
                    Toast.makeText(getApplicationContext(),"Now Training Status Is Running, Please Restart The Application",Toast.LENGTH_SHORT).show();
                }
                else{
                    statusEditor.putBoolean("status",false);
                    statusEditor.commit();
                    Toast.makeText(getApplicationContext(),"Now Training Status Has Been Stopped",Toast.LENGTH_SHORT).show();
                }

            }
        });



        setNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new RangeTimePickerDialog();
                dialog.newInstance();
                dialog.setRadiusDialog(20); // Set radius of dialog (default is 50)
                dialog.setIs24HourView(true); // Indicates if the format should be 24 hours
                dialog.setColorBackgroundHeader(R.color.colorPrimary); // Set Color of Background header dialog
                dialog.setColorTextButton(R.color.colorPrimaryDark); // Set Text color of button
                fragmentManager= getFragmentManager();
                getNotificationTime();

            }
        });





        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapter);

        if (preferences.getInt("Index",1) == 0 || preferences.getInt("Index",1) == 1){
            language.setSelection(1);
        }
        else if (preferences.getInt("Index",1) == 2){
            language.setSelection(2);
        }

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    Toast.makeText(getApplicationContext(),"Select a language",Toast.LENGTH_SHORT).show();
                }
                else{
                    editor.putInt("Index",i);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),lang[i]+" Is Selected",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            finish();
        }

        return true;
    }


    private void constructJob(){
        JobInfo.Builder builder=new JobInfo.Builder(100,new ComponentName(this,ScheduleJob.class));
        builder.setPeriodic(interval)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);

        jobScheduler.schedule(builder.build());
        Toast.makeText(getApplicationContext(),"A Notification is going to fire up after "+(interval/3600000)+" hours later",Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }



    private void getNotificationTime() {
        dialog.show(fragmentManager, "Pick Up A Time");
        /*final Calendar calendar=Calendar.getInstance();
        TimePickerDialog timePickerDialog=TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                                                           @Override
                                                                           public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                                                            Calendar calendar1=Calendar.getInstance();
                                                                                int h=Math.abs(hourOfDay-calendar1.get(Calendar.HOUR_OF_DAY));
                                                                                int m=Math.abs(minute-calendar1.get(Calendar.MINUTE));
                                                                                interval=((h*3600)+(m*60))*1000;
                                                                               Log.e("Setting","OnTimeSetListenerExecuted "+interval);
                                                                               constructJob();
                                                                           }
                                                                       }, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);   // true means 24 hours format else 12 hours


        timePickerDialog.setTitle("Set Your Preffered Time");
        timePickerDialog.show(getFragmentManager(),"Time Picker");*/
    }


    @Override
    protected void onResume() {
        super.onResume();

        preferencesLang=getSharedPreferences("DetectLanguage",MODE_PRIVATE);

        if (preferencesLang.getInt("Index",0) == 2){

            toolbar.setTitle("");
            title.setTypeface(face);
            title.setText(R.string.bd_setting);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setNotification.setTypeface(face);
            ts.setTypeface(face);
            setNotification.setText(R.string.bd_notific);
            ts.setText(R.string.bd_tarin);

        }else{

            toolbar.setTitle("");
            title.setTypeface(face);
            title.setText("Setting");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            setNotification.setText("Set Scheduling Notification");
            ts.setText("Training Status");

        }

    }

    @Override
    public void onSelectedTime(int i, int i1, int i2, int i3) {

        int h=Math.abs(i2-i);
        int m=Math.abs(i3-i1);
        interval=((h*3600)+(m*60))*1000;
        Log.e("onSelectedTime",""+interval);
        constructJob();
    }
}
