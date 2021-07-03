package com.example.barnaclewhalen_jsonapp;

import com.example.barnaclewhalen_jsonapp.utilities.NetworkUtils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchTermEditText;
    private Button mSearchButton;
    private Button mResetButton;
    private TextView mSearchResultsDisplay;
    private TextView mWelcomeDisplay;
    private TextView mSecondDisplay;
    private EditText mSpecificTermEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Step 1: Connect with visual xml elements
        mWelcomeDisplay = (TextView)findViewById(R.id.tv_welcome_text);
        mSearchTermEditText = (EditText) findViewById(R.id.et_search_box);
        mSearchButton = (Button) findViewById(R.id.button_search);
        mResetButton = (Button) findViewById(R.id.button_reset);
        mSearchResultsDisplay = (TextView)findViewById(R.id.tv_display_text);
        mSpecificTermEditText = (EditText) findViewById(R.id.et_second_box);
        mSecondDisplay = (TextView) findViewById(R.id.tv_second_text);

        final String defaultDisplayText = "Results: \n";

        mSearchResultsDisplay.setText(defaultDisplayText);

        // Step 3: Respond to search button
        mSearchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSearchResultsDisplay.setText(defaultDisplayText);
                        makeNetworkSearchQuery();

                    } // end of OnClick
                } // end of OnClick...
        ); // end of setOnClick....

        // Step 4: Respond to reset button
        mResetButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        mSearchResultsDisplay.setText(defaultDisplayText);
                    } // end of onClick
                } // end of OnClickLi...
        ); // end of setOnClick...
    } // end of onCreate

    // networking code begins

    public void makeNetworkSearchQuery(){
        // grab search term
        String searchTerm = mSearchTermEditText.getText().toString();
        // make network call
        new FetchNetworkData().execute(searchTerm);
    } // end of makeNetworkSearchQuery

    // inner class definition
    public class FetchNetworkData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) { // input parameter is a string array
            // get the search term
            //String searchTerm = null;
            //if (params.length == 0) {
            //    return null;
            //} else {
            //    searchTerm = params[0];
            //}
            // get the URL - hard coded
            URL senatorURL = NetworkUtils.buildSenatorUrl();

            // get response from the URL
            String responseData = null;
            try {
                responseData = NetworkUtils.getResponseFromUrl(senatorURL);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("debug", "getting response from url failed");
            }

            // return response to onPostExecute
            return responseData;
        } // end of doInBackground

        @Override
        protected void onPostExecute(String responseData){ // automatically triggered after doInBackground is done
            // update UI with Response
            Log.d("debug", "response received from url - " + responseData);
            // use response data from url, parse the json in it, get countries list
            ArrayList<String> senatorList = NetworkUtils.parseSenatorJson(responseData, mSearchTermEditText.getText().toString(), mSpecificTermEditText.getText().toString());
            for(String senator : senatorList){
                mSearchResultsDisplay.append(senator);
            }
        } // end of onPostExecute

    } // end of FetchNetworkData class

    // networking code ends

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
        if(menuItemSelected == R.id.menu_fact) {
            // telling class about other class - weird!
            Class destinationActivity = FactActivity.class;

            // create intent to go to destination
            Intent startAboutActivityIntent = new Intent(MainActivity.this, destinationActivity);
            // could transfer data between
            // startAboutActivityIntent.putExtra(Intent.EXTRA_TEXT, [message] );

            startActivity(startAboutActivityIntent);
        }
        return true;
    }

} // end of class