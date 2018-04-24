package qapp.mangosoft.com.qappproject;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import qapp.mangosoft.com.qappproject.R;

public class Purpose extends AppCompatActivity {

    private SharedPreferences preferences;
    private int langIndex=0;
    private Typeface face;
    private TextView titleText,purDesc;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);
        purDesc=findViewById(R.id.pur_desc);
        face=Typeface.createFromAsset(getAssets(), "kalpurush.ttf");
        preferences=getSharedPreferences("DetectLanguage",MODE_PRIVATE);
        langIndex=preferences.getInt("Index",0);

        setUpToolbar();

        if (langIndex == 2){
            purDesc.setTypeface(face);
            purDesc.setText(R.string.purpose_desc);
        }else{
            purDesc.setText("Every ayat of the Al-Qur'an is guidance for mankind from Allah.The purpose of this app is to deliver this guidance directly from the Al-Qur'an.");
        }


    }


    public void setUpToolbar(){
        toolbar = findViewById(R.id.toolbar);
        titleText=toolbar.findViewById(R.id.toolbar_title);

        if (langIndex == 2){
            toolbar.setTitle("");
            titleText.setTypeface(face);
            titleText.setText(R.string.bd_purpose);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else{

            toolbar.setTitle("");
            titleText.setText("Purpose Of This Application");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            finish();
        }


        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        langIndex=preferences.getInt("Index",1);
        setUpToolbar();

        if (langIndex == 2){
            purDesc.setTypeface(face);
            purDesc.setText(R.string.purpose_desc);
        }else{
            purDesc.setText("Every ayat of the Al-Qur'an is guidance for mankind from Allah.The purpose of this app is to deliver this guidance directly from the Al-Qur'an.");
        }
    }
}
