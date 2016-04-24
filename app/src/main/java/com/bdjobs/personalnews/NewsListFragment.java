package com.bdjobs.personalnews;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Tabriz on 23-Apr-16.
 */
public class NewsListFragment extends Fragment {

    View view;
    ListView listView;
    ArrayList<String> newsTitles = new ArrayList<>();
    Communicator communicator;

    public NewsListFragment(ArrayList<String> newsTitles) {
        this.newsTitles = newsTitles;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_list_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.newsTitleLV);

        return view;

    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TitleListAdapter titleListAdapter = new TitleListAdapter(getActivity(), newsTitles);
        listView.setAdapter(titleListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                communicator.information(String.valueOf(position));
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

    }


}
