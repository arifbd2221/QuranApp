package qapp.mangosoft.com.qappproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 2/7/2018.
 */

public class SecondActivity extends AppCompatActivity {

    Spinner spinner;
    EditText verse,verse_no;
    Button add;
    Database database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        database=new Database(this);


        spinner= (Spinner) findViewById(R.id.spinner);
        verse= (EditText) findViewById(R.id.verse);
        verse_no= (EditText) findViewById(R.id.verse_no);
        add= (Button) findViewById(R.id.add_verse);


    }
}



