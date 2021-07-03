package com.example.barnaclewhalen_jsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class FactActivity extends AppCompatActivity {

    private Button mFactButton;
    private TextView mFactText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact);

        mFactButton = (Button)findViewById(R.id.fact_button);
        mFactText = (TextView)findViewById(R.id.fact_text);

        final String[] randomFacts = {"The word 'senator' comes from the Latin word for 'old man,' 'senex.'",
        " The first Senate met in 1789 in New York City. The Senate soon after moved to Philadelphia in 1790 and then to Washington D.C.  ten years later.",
        " Out of 100 Senate seats, there are just 17 female Senators.  The first female Senator was Rebecca Felton, a Democrat from Georgia in 1922.",
        "U.S. Senators serve six year terms with no term limits.",
        "The first Senators elected were Robert Morris and William Maclay from Pennsylvania in 1788.",
        "The longest-serving Senator was Robert C. Byrd, a Democrat from West Virginia who, in 2009, served for 56 years.",
        "The first black Senator was Hiram Rhodes Revels in 1870,  representing Mississippi after the Reconstruction.",
        "The longest speech was Strom Thurmond’s 1957 filibuster against the Civil Rights Act. He spoke for 24 hours and 18 minutes.",
        "Thurmond was also the oldest Senator, retiring at 100 in 2002.",
        "The first son and father team to serve in the Senate was Henry Dodge and Augustus Dodge in 1857 to 1866.",
        "The first radio broadcast from the Senate chambers occurred on March 4, 1929.",
        "C-Span began Senate coverage in 1986.",
        "Tammy Baldwin is the first openly lesbian Senator. She was elected in 2012 and represents Wisconsin.",
        "The first former president to be elected Senator was Andrew Johnson in 1875.",
        "Senator James Shields represented Illinois, Minnesota and Missouri in the late 1800’s.  He is the only Senator to represent three states in his career.",
        "The first woman elected to chair a Senate committee was Hattie Caraway of the Committee on Enrolled Bills in 1933.",
        "There have only been nine Hispanic and Latino American Senators.",
        "There have only been nine African-American Senators, with just three currently in office.",
        "Senators receive a yearly salary of around $165,000.",
        "The youngest senator to serve was John H. Easton of Tennessee, who was 28."};

        final String defaultText = "";
        mFactText.setText(defaultText);

        mFactButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFactText.setText(defaultText);
                        displayRandomFact(randomFacts);
                    }
                }

        );
    }

    public void displayRandomFact(String[] randomFacts) {
        Random rand = new Random();
        int randNumber = rand.nextInt(randomFacts.length);
        mFactText.setText(randomFacts[randNumber]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflates menu - to attach it to MainActivity page
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // respond to menu item click - call intent to go to AboutActivity
        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.menu_home) {
            // telling class about other class - weird!
            Class destinationActivity = MainActivity.class;

            // create intent to go to destination
            Intent startAboutActivityIntent = new Intent(FactActivity.this, destinationActivity);
            // could transfer data between
            // startAboutActivityIntent.putExtra(Intent.EXTRA_TEXT, [message] );

            startActivity(startAboutActivityIntent);
        }
        return true;
    }
}