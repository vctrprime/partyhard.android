package com.partyhard.partyhard.api;

import android.content.Intent;
import android.content.SharedPreferences;

import com.partyhard.partyhard.LoginActivity;
import com.partyhard.partyhard.MainActivity;
import com.partyhard.partyhard.domain.Party;
import com.partyhard.partyhard.domain.ToJoinRequest;
import com.partyhard.partyhard.domain.User;

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Integer.parseInt;

public final class DjangoApi {

    public static final String SERVER = "http://192.168.1.5:8000/";

    private static String server = "http://192.168.1.5:8000/api/home/";

    private static JSONObject jsonObject;
    private static JSONArray jsonArray;
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

    public static ArrayList<Party> parseJSONParties(String json_string){
        ArrayList<Party> result = new ArrayList<>();
        try {
            jsonArray = new JSONArray(json_string);
            int count = 0;
            String title, description, image;
            Float longitude, latitude, distance;
            Date dateStart, dateFinish;
            User user;
            int partyId;
            while(count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                partyId = jo.getInt("party_id");
                title = jo.getString("title");
                description = jo.getString("description");
                image = SERVER + "static/" + jo.getString("image").split("/static/")[1];
                longitude = Float.parseFloat(jo.getString("longitude"));
                latitude = Float.parseFloat(jo.getString("latitude"));
                distance = 0F;
                dateStart = new Date();
                dateFinish = new Date();
                String userJSON = jo.getString("user");
                user = parseUserJSON(userJSON);
                Party party = new Party(partyId, title, description, image,
                        longitude, latitude, distance, dateStart, dateFinish,
                        user, new ArrayList<ToJoinRequest>());
                result.add(party);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static User parseUserJSON(String userJSON){
        String external_avantar = parseJSON(parseJSON(userJSON, "userprofile"), "external_avatar");
        String avatar = external_avantar == "null" ?
                SERVER + "static/" + parseJSON(parseJSON(userJSON, "userprofile"), "avatar").split("/static/")[1]:
                external_avantar;
        String display_name = parseJSON(parseJSON(userJSON, "userprofile"), "display_name");
        String username = display_name == "null" ?
                parseJSON(userJSON, "username"):
                display_name;
        return new User(username,
                avatar,
                parseInt(parseJSON(userJSON, "id")));
    }

}