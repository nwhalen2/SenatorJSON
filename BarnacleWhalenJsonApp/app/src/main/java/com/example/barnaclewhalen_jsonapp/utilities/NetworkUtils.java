package com.example.barnaclewhalen_jsonapp.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {
    public static URL buildSenatorUrl(){
        String senatorUrlString = "https://www.govtrack.us/api/v2/role?current=true&role_type=senator";
        URL senatorUrl = null;
        try{
            senatorUrl = new URL(senatorUrlString);
        } catch(MalformedURLException e){
            e.printStackTrace();
        }
        return senatorUrl;
    } // return URL object for countries data

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // opens connection to url
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in); // connection to the input stream
            scanner.useDelimiter("\\A"); // delimiter for end of message
            // now actually read it
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
        } catch(Exception e){
            e.printStackTrace();
            Log.d("debug", "reading from url source failed");
        }

        return null;
    } // end of getResponseFromUrl

    public static ArrayList<String> parseSenatorJson(String senatorResponseString, String searchParam, String specificParam){
        ArrayList<String> senatorList = new ArrayList<String>();
        try {
            JSONObject allSenatorsObject = new JSONObject(senatorResponseString);
            JSONArray allSenatorsArray = allSenatorsObject.getJSONArray("objects");

            for(int i = 0; i < allSenatorsArray.length(); i++){
                JSONObject childJson = allSenatorsArray.getJSONObject(i);
                JSONObject grandChildJson = childJson.getJSONObject("person");
                String senatorName = "\n";
                senatorName = senatorName + grandChildJson.getString("firstname") + " " + grandChildJson.getString("lastname") + "\n";
                searchParam = searchParam.toLowerCase();
                if(childJson.has(searchParam)){
                    String param = childJson.getString(searchParam); // gets the value for key "name"
                    Log.d("debug", "param is " + param);
                    if (param != null) {
                        if (param.toLowerCase().equals(specificParam.toLowerCase())) {
                            senatorList.add(senatorName);
                        }
                    }

                }
                else if(grandChildJson.has(searchParam.toLowerCase())){
                    String param = grandChildJson.getString(searchParam);
                    if(searchParam.toLowerCase().equals("gender")){
                        if (param != null) {
                            if(param.toLowerCase().equals(specificParam.toLowerCase())){
                                senatorList.add(senatorName);
                            }
                        }
                    }
                }

            } // end of for
            if(senatorList.size() == 0){
                senatorList.add("\nIncorrect or no parameters entered.");
            }

        } catch(JSONException e){
            e.printStackTrace();
            Log.d("debug", "JSON parsing failed");
        }
        return senatorList;
    } // end of parseSenatorJson

} // end of class
