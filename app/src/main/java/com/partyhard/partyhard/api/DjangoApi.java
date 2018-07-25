package com.partyhard.partyhard.api;

import android.content.Intent;
import android.content.SharedPreferences;

import com.partyhard.partyhard.LoginActivity;
import com.partyhard.partyhard.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public final class DjangoApi {

    private static String server = "http://192.168.1.5:8000/api/home/";

    private static JSONObject jsonObject;
    JSONArray jsonArray;
    public static String getJSON(String jsonUrl, String authorization){
        String JSON_STRING;
        String result = null;
        URL url = null;
        try {
            url = new URL(server + jsonUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();
            if (authorization != null){
                httpURLConnection.setRequestProperty("Authorization", "Token " + authorization);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getInputStream();
        }
        catch (IOException e) {
            e.printStackTrace();
            if (e instanceof FileNotFoundException){
                return "Incorrect";
            }
            else {
                return "Connection refused";
            }


        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpURLConnection.disconnect();
        return stringBuilder.toString().trim();
    }

    public static String parseJSON(String stringJSON, String key) {

        String result = null;
        try {
            jsonObject = new JSONObject(stringJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*try {
            jsonArray = jsonObject.getJSONArray("objects");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jo = null;
        try {
            jo = jsonArray.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        try {
            assert jsonObject != null;
            result = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}