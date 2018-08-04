package com.partyhard.partyhard.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.partyhard.partyhard.MainActivity;
import com.partyhard.partyhard.R;
import com.partyhard.partyhard.api.DjangoApi;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new BackgroundTask().execute();
    }

    public class BackgroundTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {

            SharedPreferences credentials = getActivity().getSharedPreferences("user_credentials", MODE_PRIVATE);
            String token = credentials.getString("token", "");
            String api_method = "parties/";
            return DjangoApi.getJSON(api_method, token);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) getActivity().findViewById(R.id.test_text);
            textView.setText(result);
        }


    }
}
