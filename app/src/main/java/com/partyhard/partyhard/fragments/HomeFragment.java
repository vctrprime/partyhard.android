package com.partyhard.partyhard.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.partyhard.partyhard.MainActivity;
import com.partyhard.partyhard.R;
import com.partyhard.partyhard.api.DjangoApi;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(
          Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoadData().execute();

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new LoadData().execute();
            }
        }, 500);
    }

    public class LoadData extends AsyncTask<Void, Void, String> {

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
//            Random random = new Random();
//            textView.setText("Котика пора кормить. Его не кормили уже "
//                    + (1 + random.nextInt(10)) + " мин.");
            mSwipeRefreshLayout.setRefreshing(false);

        }


    }
}
