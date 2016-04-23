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
    String url = "http://www.prothom-alo.com/";
    ProgressDialog mProgressDialog;
    String detailsLink1,title_1,details_1,imageLink="";
    String[]a;
    String[]id={"div[id=widget_50158]","div[id=div_49675]","div[id=widget_50227]","div[id=widget_49684]",
            "div[id=div_49678]","div[id=div_1299]","div[id=div_1778]","div[id=widget_33045]","div[id=widget_33057]",
            "div[id=div_43609]"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.news_list_fragment,container,false);
        listView = (ListView) view.findViewById(R.id.newsTitleLV);

        return view;
    }

    public void setCommunicator(Communicator communicator){
        this.communicator=communicator;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Title().execute();


    }

    @Override
    public void onPause() {
        super.onPause();
        newsTitles.clear();
    }

    private class Title extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("News is Loading");
            mProgressDialog.setMessage("Please Wait...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document document = Jsoup.connect(url).get();

                for(int i=0;i<id.length;i++)
                {
                    Elements element = document.select(id[i]);
                    System.out.println("Evan" + element.toString());
                    Elements title1 = element.select(".title");
                    title_1 = Html.fromHtml(title1.toString()).toString();
                    newsTitles.add(title_1);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TitleListAdapter titleListAdapter = new TitleListAdapter(getActivity(),newsTitles);
            listView.setAdapter(titleListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    communicator.information(String.valueOf(position));

                }
            });

            mProgressDialog.dismiss();
        }
    }

}
