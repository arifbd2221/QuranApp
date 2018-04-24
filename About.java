package qapp.mangosoft.com.qappproject;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by User on 2/10/2018.
 */

public class About extends AppCompatActivity {

    private SharedPreferences preferences;
    private int langIndex=0;
    private Typeface face;
    private TextView titleText;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);
        face=Typeface.createFromAsset(getAssets(), "kalpurush.ttf");
        preferences=getSharedPreferences("DetectLanguage",MODE_PRIVATE);
        langIndex=preferences.getInt("Index",0);
        toolbar=findViewById(R.id.toolbar5);
        collapsingToolbarLayout= findViewById(R.id.collapsing_bar);

        if (langIndex == 2)
        {
            titleText=toolbar.findViewById(R.id.titleText);
            toolbar.setTitle("");
            titleText.setTypeface(face);
            collapsingToolbarLayout.setCollapsedTitleTypeface(face);
            titleText.setText(R.string.about_bd);
            collapsingToolbarLayout.setTitle(R.string.about_bd+"");
        }
        else{
            titleText=toolbar.findViewById(R.id.titleText);
            toolbar.setTitle("");
            titleText.setText("About");
            collapsingToolbarLayout.setTitle("About Developer");
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        langIndex=preferences.getInt("Index",1);

        if (langIndex == 2)
        {
            titleText=toolbar.findViewById(R.id.titleText);
            toolbar.setTitle("");
            titleText.setTypeface(face);
            collapsingToolbarLayout.setCollapsedTitleTypeface(face);
            //titleText.setText(R.string.about_bd);
            collapsingToolbarLayout.setTitle("আমাদের সম্পর্কে");
        }
        else{
            titleText=toolbar.findViewById(R.id.titleText);
            toolbar.setTitle("");
            titleText.setText("About");
            collapsingToolbarLayout.setTitle("About Developer");
        }

    }
}
