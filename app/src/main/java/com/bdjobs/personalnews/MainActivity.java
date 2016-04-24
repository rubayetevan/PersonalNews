package com.bdjobs.personalnews;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements Communicator {
    FragmentManager fragmentManager;
    NewsListFragment newsListFragment;
    ArrayList<String> newsTitles = new ArrayList<>();
    ArrayList<String> newsDetails = new ArrayList<>();
    ArrayList<String> imageLinks = new ArrayList<>();
    String[] a;
    String url = "http://www.prothom-alo.com/";
    ProgressDialog mProgressDialog;
    String title_1, imageLink, detailsLink1, details_1;
    String[] id = {"div[id=widget_50158]", "div[id=div_49675]", "div[id=widget_50227]", "div[id=widget_49684]",
            "div[id=div_49678]", "div[id=div_1299]", "div[id=div_1778]", "div[id=widget_33045]", "div[id=widget_33057]",
            "div[id=div_43609]"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Title().execute();

    }

    @Override
    public void information(String information) {
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment(newsDetails, imageLinks, newsTitles);
        FragmentTransaction transactionR = fragmentManager.beginTransaction();
        Bundle args = new Bundle();
        args.putString("information", information);
        newsDetailsFragment.setArguments(args);
        transactionR.replace(R.id.landingPage, newsDetailsFragment, "newsDetailsFragment");
        transactionR.addToBackStack("newsDetailsFragment");
        transactionR.commit();

    }

    private class Title extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("News is Loading");
            mProgressDialog.setMessage("Please Wait...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(url).get();
                for (int i = 0; i < id.length; i++) {
                    Elements element = document.select(id[i]);
                    System.out.println("Evan" + element.toString());

                    Elements title1 = element.select(".title");

                    title_1 = Html.fromHtml(title1.toString()).toString();
                    Elements details1_link = title1.select("a[href]");
                    detailsLink1 = url + link(details1_link.toString());
                    System.out.println(detailsLink1);

                    Document document1 = Jsoup.connect(detailsLink1).get();
                    Elements elements1 = document1.select("div[itemprop=articleBody]");
                    details_1 = Html.fromHtml(elements1.toString()).toString();

                    Elements elements = elements1.select("img[itemprop=image]");
                    String sa = elements.toString();
                    a = sa.split("\"");
                    for (int l = 0; l < a.length; l++) {

                        int s = a[l].compareTo("http:");
                        if (s > 100) {
                            imageLink = a[l];
                        }

                    }
                    newsTitles.add(title_1);
                    newsDetails.add(details_1);
                    imageLinks.add(imageLink);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            fragmentManager = getSupportFragmentManager();
            newsListFragment = new NewsListFragment(newsTitles);
            newsListFragment.setCommunicator(MainActivity.this);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.landingPage, newsListFragment, "newsListFragment");
            transaction.commit();
            mProgressDialog.dismiss();
        }
    }

    public static String link(String data) {

        String regex = "<a href=(\"[^\"]*\")[^<]*</a>";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(data);
        System.out.println(m.replaceAll("$1"));
        String d = m.replaceAll("$1").toString();
        return d.replaceAll("\"", "");
    }

}
