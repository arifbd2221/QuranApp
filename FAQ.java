package qapp.mangosoft.com.qappproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.lang.annotation.Target;
import java.util.ArrayList;

import qapp.mangosoft.com.qappproject.R;

public class FAQ extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Database database;
    private RecyclerView recyclerView;
    private ArrayList<String> quesList;
    private Toolbar toolbar;
    private ListAdapter adapter;

    private SharedPreferences preferences,status;
    private SharedPreferences.Editor editor;
    private int langIndex=0;
    private Typeface face;
    private TextView titleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        face=Typeface.createFromAsset(getAssets(), "kalpurush.ttf");
        preferences=getSharedPreferences("DetectLanguage",MODE_PRIVATE);
        status=getSharedPreferences("TSTATUS",MODE_PRIVATE);
        editor=status.edit();
        langIndex=preferences.getInt("Index",0);


        Log.e("activity_faq","working");
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recyclerView);
        //toolbar.setTitle("Frequently Asked Question");

        setupToolbar();


        quesList=new ArrayList<>();

        database=new Database(this);

        quesList=database.getAllQues();

        adapter=new ListAdapter(this,quesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        if (status.getBoolean("status2",true)){
            TapTargetView.showFor(this,                 // `this` is an Activity
                    TapTarget.forToolbarMenuItem(toolbar,R.id.search,"Frequently Asked Question","Search here what you added to the FAQ section by the Question")
                            // All options below are optional
                            .outerCircleColor(R.color.colorAccent)      // Specify a color for the outer circle
                            .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                            .targetCircleColor(R.color.white)   // Specify a color for the target circle
                            .titleTextSize(20)                  // Specify the size (in sp) of the title text
                            .titleTextColor(R.color.white)      // Specify the color of the title text
                            .descriptionTextSize(17)            // Specify the size (in sp) of the description text
                            .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                            .textColor(R.color.white)            // Specify a color for both the title and description text
                            .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                            .dimColor(R.color.white)            // If set, will dim behind the view with 30% opacity of the given color
                            .drawShadow(true)                   // Whether to draw a drop shadow or not
                            .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                            .tintTarget(true)                   // Whether to tint the target view's color
                            .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                            // Specify a custom drawable to draw as the target
                            .targetRadius(60),                  // Specify the target radius (in dp)
                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional
                            Log.e("FAQ","onTargetClicked");
                            editor.putBoolean("status2",false);
                            editor.commit();
                        }
                    });
        }

    }

    private void setupToolbar() {

        titleText=toolbar.findViewById(R.id.toolbar_title);

        if (langIndex == 2){
            titleText.setTypeface(face);
            toolbar.setTitle("");
            titleText.setText(R.string.toolbar_bd_faq);
        }
        else{
            toolbar.setTitle("");
            titleText.setText("Frequently Asked Question");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.faq_menu);

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
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.faq_menu,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);


        return true;
    }



    @Override
    public boolean onQueryTextChange(String newText) {

        newText=newText.toLowerCase();

        ArrayList<String> newCountryList=new ArrayList<>();

        for (String c : quesList){
            if (c.toLowerCase().contains(newText)){
                newCountryList.add(c);

            }
        }
        adapter.filterAdapter(newCountryList);


        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        langIndex=preferences.getInt("Index",1);
        setupToolbar();


        quesList=new ArrayList<>();

        database=new Database(this);

        quesList=database.getAllQues();



        adapter=new ListAdapter(this,quesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


    }


}
