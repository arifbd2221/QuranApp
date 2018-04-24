package qapp.mangosoft.com.qappproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.io.File;
import java.util.ArrayList;

import qapp.mangosoft.com.qappproject.R;

public class GuidanceOfTheDay extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private EditText ques,yesCommenet,qEditText;
    private Button submit;

    private int gIndex=0;
    private ImageButton left,right,shareAyat;

    private SharedPreferences preferences,TSTATUS;
    private SharedPreferences.Editor editor;
    private TestAdapter database;

    private ProgressDialog progressDialog;


    private ArrayList<String> allVerses;
    private int verseNoForVerse,suraNoForVerse;

    private int langIndex=0;

    private TextView suraNo,suraName,verseNo,verse,titleText,doesthisayat,yText,nText;

    private CheckBox yes,no;
    private EditText userDefineFAQ;
    private Button addToFAQ,userDefineaddToFAQ;

    private Typeface face;



    private int sharesuraNO,shareverseNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_of_the_day);

        Intent intent=getIntent();
        String className= intent.getStringExtra("data");





        face=Typeface.createFromAsset(getAssets(), "kalpurush.ttf");
        TSTATUS=getSharedPreferences("TSTATUS",MODE_PRIVATE);
        preferences=getSharedPreferences("DetectLanguage",MODE_PRIVATE);
        langIndex=preferences.getInt("Index",0);
        editor=TSTATUS.edit();



        database=new TestAdapter(getApplicationContext());
        database.createDatabase();
        database.open();


        allVerses=new ArrayList<>();

        shareAyat=findViewById(R.id.shareayat);
        suraNo=findViewById(R.id.sura_no);
        suraName=findViewById(R.id.sura_name);
        verseNo=findViewById(R.id.verse_no);
        verse=findViewById(R.id.verse);
        yesCommenet=findViewById(R.id.yesCommenet);
        doesthisayat=findViewById(R.id.doesthisayat);
        yText=findViewById(R.id.yesText);
        nText=findViewById(R.id.noText);

        left=findViewById(R.id.left_verse);
        right=findViewById(R.id.right_verse);



        progressDialog=new ProgressDialog(this);
        ques=findViewById(R.id.question);
        qEditText=findViewById(R.id.question);
        submit=findViewById(R.id.submit);
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        userDefineFAQ=findViewById(R.id.userFAQ);
        addToFAQ=findViewById(R.id.addTofaq);
        userDefineaddToFAQ=findViewById(R.id.userDefineaddTofaq);

        setUpToolbar();
        setUpNavigationDrawer();

        submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        if(langIndex == 2){
            submit.setTypeface(face);
            doesthisayat.setTypeface(face);
            yText.setTypeface(face);
            nText.setTypeface(face);
            addToFAQ.setTypeface(face);
            ques.setTypeface(face);
            suraName.setTypeface(face);
            suraNo.setTypeface(face);
            verseNo.setTypeface(face);
            yesCommenet.setTypeface(face);
            verse.setTypeface(face);
            submit.setText(R.string.submit_bd_button);
            doesthisayat.setText(R.string.doesthisayat);
            yText.setText(R.string.yes);
            nText.setText(R.string.no);
            addToFAQ.setText(R.string.add_bd_button);
            ques.setHint(R.string.enter_query);
            suraName.setText("সূরা নাম");
            suraNo.setText("সূরা নং: ");
            verseNo.setText("আয়াত নং: ");
            verse.setText("আয়াত");

        }else{
            submit.setText("Submit");
            doesthisayat.setText("Does This Ayat Answer Your Question?");
            yText.setText("Yes");
            nText.setText("No");
            addToFAQ.setText("Add This Question To The FAQ");
            ques.setHint("Enter your query");
        }



        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !qEditText.getText().toString().isEmpty() )
                    Log.e("leftIndex",""+(gIndex-1));
                    new SecondTask().execute(--gIndex,1);
            }
        });


        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !qEditText.getText().toString().isEmpty() )
                    Log.e("rightIndex",""+(gIndex+1));
                    new SecondTask().execute(++gIndex,1);
            }
        });


        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                no.setChecked(false);

                if (langIndex == 2){
                    yesCommenet.setHint(R.string.bd_cmnt);
                    yesCommenet.setGravity(Gravity.CENTER);
                }
                else{
                    yesCommenet.setHint("Give your comment..");
                    yesCommenet.setGravity(Gravity.CENTER);
                }


                final Database database=new Database(getApplicationContext());

                if(b) {
                    yesCommenet.setVisibility(View.VISIBLE);
                    addToFAQ.setVisibility(View.VISIBLE);
                    Log.e("cmnt",yesCommenet.getText().toString());

                    addToFAQ.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (yesCommenet.getText().toString().isEmpty() || ques.getText().toString().isEmpty()){

                            }else{
                                database.insertInoFaq(qEditText.getText().toString(),yesCommenet.getText().toString(),allVerses.get(gIndex),verseNoForVerse,suraNoForVerse);

                                Toast.makeText(getApplicationContext(),"Added To The FAQ Section",Toast.LENGTH_SHORT).show();
                                yesCommenet.setText("");
                                createDialog();
                            }

                        }
                    });


                }
                else {
                    yesCommenet.setVisibility(View.INVISIBLE);
                    addToFAQ.setVisibility(View.INVISIBLE);
                }


            }
        });



      no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                yes.setChecked(false);

              /*final Database database=new Database(getApplicationContext());
              if (b) {
                  userDefineFAQ.setVisibility(View.VISIBLE);
                  userDefineaddToFAQ.setVisibility(View.VISIBLE);

                  userDefineaddToFAQ.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          database.insertInoFaq(qEditText.getText().toString(),userDefineFAQ.getText().toString(),allVerses.get(gIndex),verseNoForVerse,suraNoForVerse-1);
                          Toast.makeText(getApplicationContext(),"Added To The FAQ Section",Toast.LENGTH_SHORT).show();

                          createDialog();

                      }
                  });

              }else {

                  userDefineFAQ.setVisibility(View.INVISIBLE);
                  userDefineaddToFAQ.setVisibility(View.INVISIBLE);
              }*/

              if (langIndex == 2){
                  yesCommenet.setHint(R.string.bd_why);
                  yesCommenet.setGravity(Gravity.CENTER);
              }
              else{
                  yesCommenet.setHint("Why not?");
                  yesCommenet.setGravity(Gravity.CENTER);
              }


              final Database database=new Database(getApplicationContext());

              if(b) {
                  yesCommenet.setVisibility(View.VISIBLE);
                  addToFAQ.setVisibility(View.VISIBLE);
                  Log.e("cmnt",yesCommenet.getText().toString());

                  addToFAQ.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          database.insertInoFaq(qEditText.getText().toString(),yesCommenet.getText().toString(),allVerses.get(gIndex),verseNoForVerse,suraNoForVerse);

                          Toast.makeText(getApplicationContext(),"Added To The FAQ Section",Toast.LENGTH_SHORT).show();
                          yesCommenet.setText("");
                          createDialog();
                      }
                  });


              }
              else {
                  yesCommenet.setVisibility(View.INVISIBLE);
                  addToFAQ.setVisibility(View.INVISIBLE);
              }



          }
      });



        shareAyat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    animate();

                if (langIndex ==2){

                    String versenosuranosuraname="সূরা নং: "+getResources().getStringArray(R.array.bd_sura_name)[sharesuraNO]
                            +" , আয়াত নং:"+shareverseNo;
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"প্রশ্ন: "+qEditText.getText().toString()+"?\n\n"+"উত্তর\n"+versenosuranosuraname+"\n\n"+allVerses.get(gIndex)+"\n\n"+"'আল কুরআনের জন্য প্রশ্ন'");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent,"সঙ্গে শেয়ার করুন"));
                }
                else{

                    String versenosuranosuraname="Sura Name: "+getResources().getStringArray(R.array.SuraName)[sharesuraNO]
                            +" , Verse NO:"+shareverseNo;
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Question: "+qEditText.getText().toString()+"?\n\n"+"Answer\n"+versenosuranosuraname+"\n\n"+allVerses.get(gIndex)+"\n\n"+"'Questions For The Al-Quran'");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent,"Share with"));
                }


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !qEditText.getText().toString().isEmpty() ){
                    hideKeyboard();
                    new MyTask().execute();
                    shareAyat.setVisibility(View.VISIBLE);
                    animate();
                }

                else {
                    Toast.makeText(getApplicationContext(),"Please,Enter The Question First",Toast.LENGTH_SHORT).show();
                }

            }
        });


        if (TSTATUS.getBoolean("status",true)){
            final Display display = getWindowManager().getDefaultDisplay();
            // Load our little droid guy
            final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_action_search2);


            final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
            // Using deprecated methods makes you look way cool
            droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

            new TapTargetSequence(this)
                    .targets(
                            TapTarget.forView(findViewById(R.id.question), "Put Your Question Here")
                                    .tintTarget(true)
                                    .outerCircleColor(R.color.colorAccent)
                                    .transparentTarget(true)
                                    .textColor(R.color.white)
                            ,
                            TapTarget.forView(findViewById(R.id.submit), "Submit Here")
                                    .tintTarget(true)
                                    .outerCircleColor(R.color.colorAccent)
                                    .transparentTarget(true)
                                    .textColor(R.color.white)
                            ,
                            TapTarget.forView(findViewById(R.id.right_verse), "See The Next Verse Onwards")
                                    .tintTarget(true)
                                    .outerCircleColor(R.color.colorAccent)
                                    .transparentTarget(true)
                                    .textColor(R.color.white)
                            ,
                            TapTarget.forView(findViewById(R.id.left_verse), "See The Previous Verse Onbackwards")
                                    .tintTarget(true)
                                    .outerCircleColor(R.color.colorAccent)
                                    .transparentTarget(true)
                                    .textColor(R.color.white)
                    )
                    .listener(new TapTargetSequence.Listener() {
                        // This listener will tell us when interesting(tm) events happen in regards
                        // to the sequence
                        @Override
                        public void onSequenceFinish() {
                            // Yay
                            Toast.makeText(GuidanceOfTheDay.this,"Great! Now You Have Been Educated About This Application",Toast.LENGTH_LONG).show();
                            editor.putBoolean("status",false);
                            editor.commit();
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                            //Toast.makeText(GuidanceOfTheDay.this,"Now This Step Has To Be Done",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                            // Boo
                            editor.putBoolean("status",false);
                            editor.commit();
                            Toast.makeText(GuidanceOfTheDay.this,"Oops! Seems Like You Cancelled The Session",Toast.LENGTH_LONG).show();
                        }
                    }).start();
        }

        //educating user


        try{

            if (className.equals("ScheduleJob")){
                Log.e("GuidanceOfTheDay",className);
                new MyTask().execute();
                Log.e("GuidanceOfTheDay","MyTask().execute()");
                Toast.makeText(GuidanceOfTheDay.this,"Verse OF The Day",Toast.LENGTH_LONG).show();
            }
        }catch (NullPointerException np){
            np.printStackTrace();
        }


    }

    private void animate() {

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = 2000;
        myAnim.setDuration((long)animationDuration);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(.05, 5);

        myAnim.setInterpolator(interpolator);
        shareAyat.startAnimation(myAnim);

        // Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {}

            @Override
            public void onAnimationRepeat(Animation arg0) {}

            @Override
            public void onAnimationEnd(Animation arg0) {
                animate();
            }
        });

    }

    public void setUpToolbar(){
        toolbar = findViewById(R.id.toolbar);

        if (langIndex == 2){
            toolbar.setTitle("");
            titleText=toolbar.findViewById(R.id.toolbar_title);
            titleText.setTypeface(face);
            titleText.setText(R.string.app_bd_name);
        }
        else{
            toolbar.setTitle("");
            titleText=toolbar.findViewById(R.id.toolbar_title);
            titleText.setText(R.string.app_name);
        }

        //toolbar.setTitleTextColor(Color.GREEN);
        setSupportActionBar(toolbar);
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException np){
            np.printStackTrace();
        }

    }

    public void setUpNavigationDrawer(){
        NavigationView navigationView=(NavigationView) findViewById(R.id.navigation);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggler=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(toggler);
        toggler.syncState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("onCreateOptionsMenu","onCreateOptionsMenu inflated");
       /* if (langIndex == 2){
            SpannableStringBuilder invite = new SpannableStringBuilder("আমন্ত্রণ");

            invite.setSpan(face, 0, invite.length(), 0);
            menu.add(Menu.NONE, R.id.share, 0, invite); // THIS
            MenuItem menuItem = menu.findItem(R.id.share); // OR THIS
            menuItem.setTitle(invite);

            invite=new SpannableStringBuilder(getResources().getString(R.string.toolbar_bd_faq));
            invite.setSpan(face, 0, invite.length(), 0);
            menu.add(Menu.NONE, R.id.FAQ, 1, invite); // THIS
            MenuItem menuItem2 = menu.findItem(R.id.FAQ); // OR THIS
            menuItem2.setTitle(invite);
        }
*/
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("onOptionsItemSelected","onOptionsItemSelected created");
        if (item.getItemId() == R.id.share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=qapp.mangosoft.com.qapp&hl=en");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,"Invite with"));
        }


        if(item.getItemId() == R.id.FAQ)
            startActivity(new Intent(this,FAQ.class));
        if (item.getItemId() == R.id.about){
            startActivity(new Intent(this,About.class));
        }

        if (item.getItemId() == R.id.purpose){
            startActivity(new Intent(this,Purpose.class));
        }
        if (item.getItemId() == R.id.setting){
            startActivity(new Intent(this,Setting.class));
        }

        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return true;
    }


    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if(item.getItemId() == R.id.FAQ)
                startActivity(new Intent(this,FAQ.class));
            if (item.getItemId() == R.id.about){
                startActivity(new Intent(this,About.class));
            }

            if (item.getItemId() == R.id.purpose){
                startActivity(new Intent(this,Purpose.class));
            }
            if (item.getItemId() == R.id.setting){
            startActivity(new Intent(this,Setting.class));
            }


        closeDrawer();

        return true;
    }



    public class MyTask extends AsyncTask<Integer,Integer,Boolean>{

        int index=0;

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            Log.e("OOPS","woking here too ,"+allVerses.size());
            Log.e("suraNoForVerse Before",""+suraNoForVerse);
            int sno=suraNoForVerse-1;
            Log.e("suraNoForVerse After",""+sno);



            if ( langIndex == 1 || langIndex == 0 ){
                try{
                    suraName.setText(getResources().getStringArray(R.array.SuraName)[sno]);
                }catch (ArrayIndexOutOfBoundsException arr){
                    arr.printStackTrace();
                }
                verse.setMovementMethod(new ScrollingMovementMethod());
                verse.setText(allVerses.get(index));
                suraNo.setText("Sura No: "+(suraNoForVerse));
                verseNo.setText("Verse No: "+verseNoForVerse);
                Log.e("verseNoForVerse",""+verseNoForVerse);
            }
            else if (langIndex == 2){
                try{
                    suraName.setTypeface(face);
                    suraName.setText(getResources().getStringArray(R.array.bd_sura_name)[sno]);
                }catch (ArrayIndexOutOfBoundsException arr){
                    arr.printStackTrace();
                }
                verse.setTypeface(face);
                suraNo.setTypeface(face);
                verseNo.setTypeface(face);
                verse.setMovementMethod(new ScrollingMovementMethod());
                verse.setText(allVerses.get(index));
                suraNo.setText("সূরা নং: "+(suraNoForVerse));
                verseNo.setText("আয়াত নং: "+verseNoForVerse);
                //Log.e("verseNoForVerse",""+verseNoForVerse);

            }
            shareverseNo=verseNoForVerse;
            sharesuraNO=suraNoForVerse-1;
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {


            if (langIndex == 0 || langIndex ==1){
                allVerses=database.getSurasAyats();
                suraNoForVerse=database.getSuraNo(allVerses.get(index+2));
                verseNoForVerse=database.getVerseNo(allVerses.get(index));
            }
            else if (langIndex == 2){
                allVerses=database.getBDSurasAyats();
                suraNoForVerse=database.getBDSuraNo(allVerses.get(index+2));
                verseNoForVerse=database.getBDVerseNo(allVerses.get(index));
            }

            Log.e("OOPS","woking here "+suraNoForVerse+", "+index+", "+verseNoForVerse);
            return true;
        }



        @Override
        protected void onPreExecute() {
            index=(int) (Math.random() * ( 6235 - 0 ));
            gIndex=index;
            progressDialog.setTitle("Getting Data");
            progressDialog.show();
        }
    }



    private void createDialog(){

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        View v=getLayoutInflater().inflate(R.layout.dialogbox,(ViewGroup) findViewById(R.id.id),false);
        Button yesTry=v.findViewById(R.id.tryagain);
        Button anotherQues=v.findViewById(R.id.anotherques);

        alert.setView(v);
        alert.setTitle("Choose An Action");
        final AlertDialog dialog=alert.create();


        dialog.show();
        yesTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Please Press Submit Button Again!",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        anotherQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                qEditText.setText("");
                Toast.makeText(getApplicationContext(),"Try Another Question!",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


    }


    public class SecondTask extends AsyncTask<Integer,Integer,Boolean>{

        int suraNO=0;
        int versNo=0;
        String s;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Integer... integers) {

            if (langIndex == 0 || langIndex == 1){
                try{
                    Log.e("suraNO",""+versNo);
                    s=allVerses.get(integers[0]);
                    suraNO=database.getSuraNo(s);
                    versNo=database.getVerseNo(s);

                    Log.e("verseNoSecond",""+versNo);
                }catch (ArrayIndexOutOfBoundsException a){
                    a.printStackTrace();
                }catch (IndexOutOfBoundsException i){
                    i.printStackTrace();
                }

            }
            else if(langIndex == 2){
                try{
                    Log.e("suraNO",""+versNo);
                    s=allVerses.get(integers[0]);
                    suraNO=database.getBDSuraNo(s);
                    versNo=database.getBDVerseNo(s);

                    //Log.e("verseNoSecond",""+versNo);
                }catch (ArrayIndexOutOfBoundsException a){
                    a.printStackTrace();
                }catch (IndexOutOfBoundsException i){
                    i.printStackTrace();
                }

            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if (langIndex == 0 || langIndex == 1){
                suraNo.setText("Sura No: "+suraNO);
                try{
                    suraName.setText(getResources().getStringArray(R.array.SuraName)[suraNO-1]);
                }catch (ArrayIndexOutOfBoundsException a){
                    a.printStackTrace();
                }

                verseNo.setText("Verse No :"+versNo);
                verse.setMovementMethod(new ScrollingMovementMethod());
                verse.setText(s);
            }
            else if (langIndex == 2){

                suraNo.setTypeface(face);
                verseNo.setTypeface(face);
                verse.setTypeface(face);


                Log.e("suraNO",""+suraNO);
                suraNo.setText("সূরা নং: "+suraNO);
                try{
                    suraName.setTypeface(face);
                    suraName.setText(getResources().getStringArray(R.array.bd_sura_name)[suraNO-1]);
                }catch (ArrayIndexOutOfBoundsException a){
                    a.printStackTrace();
                }

                verseNo.setText("আয়াত নং: "+versNo);
                verse.setMovementMethod(new ScrollingMovementMethod());
                verse.setText(s);
            }

            sharesuraNO=suraNO;
            shareverseNo=versNo;

        }
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view =getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        langIndex=preferences.getInt("Index",1);

        setUpToolbar();
        if(langIndex == 2){
            submit.setTypeface(face);
            doesthisayat.setTypeface(face);
            yText.setTypeface(face);
            nText.setTypeface(face);
            addToFAQ.setTypeface(face);
            ques.setTypeface(face);
            yesCommenet.setTypeface(face);
            submit.setText(R.string.submit_bd_button);
            doesthisayat.setText(R.string.doesthisayat);
            yText.setText(R.string.yes);
            nText.setText(R.string.no);
            addToFAQ.setText(R.string.add_bd_button);
            ques.setHint(R.string.enter_query);

        }else{
            submit.setText("Submit");
            doesthisayat.setText("Does This Ayat Answer Your Question?");
            yText.setText("Yes");
            nText.setText("No");
            addToFAQ.setText("Add This Question To The FAQ");
            ques.setHint("Enter your query");
        }

    }
}
