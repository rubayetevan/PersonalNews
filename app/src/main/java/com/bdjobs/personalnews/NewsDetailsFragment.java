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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tabriz on 23-Apr-16.
 */
public class NewsDetailsFragment extends Fragment {
    View view;
    ImageView imageView;
    TextView detailsTV,dtitleTV;
    String url = "http://www.prothom-alo.com/";
    ProgressDialog mProgressDialog;
    String detailsLink1,title_1,details_1,imageLink="";
    String a[];
    String information;
    String[]id={"div[id=widget_50158]","div[id=div_49675]","div[id=widget_50227]","div[id=widget_49684]",
            "div[id=div_49678]","div[id=div_1299]","div[id=div_1778]","div[id=widget_33045]","div[id=widget_33057]",
            "div[id=div_43609]"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.news_detail_fragment,container,false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        detailsTV = (TextView) view.findViewById(R.id.detailsTV);
        dtitleTV = (TextView) view.findViewById(R.id.dtitleTV);
        information = getArguments().getString("information");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Title().execute();
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
                Elements element = document.select(id[Integer.parseInt(information)]);
                //Elements element = document.select("div[id=div_49675]");
                //Elements element = document.select("div[id=widget_50227]");
                //Elements element = document.select("div[id=widget_49684]");
                //Elements element = document.select("div[id=div_49678]");
                //Elements element = document.select("div[id=div_1299]");
                //Elements element = document.select("div[id=div_1778]");
                //Elements element = document.select("div[id=widget_33045]");
                //Elements element = document.select("div[id=widget_33057]");
                //Elements element = document.select("div[id=div_43609]");
                //Elements element = document.select("div[id=ticker_widget_47647]");
                //Elements element = document.select("div[id=widget_48289]");
                System.out.println("Evan"+element.toString());

                Elements title1 = element.select(".title");

                title_1 = Html.fromHtml(title1.toString()).toString();
                Elements details1_link = title1.select("a[href]");
                detailsLink1 = url + link(details1_link.toString());
                System.out.println(detailsLink1);

                Document document1 = Jsoup.connect(detailsLink1).get();
                Elements elements1 = document1.select("div[itemprop=articleBody]");
                details_1 = Html.fromHtml(elements1.toString()).toString();

                Elements elements = elements1.select("img[itemprop=image]");
                String sa= elements.toString();
                a = sa.split("\"");
                for(int i =0;i<a.length;i++) {
                    //Arrays.asList(a).contains("http:");
                    //System.out.println(a[i] + "\nPosition:" + String.valueOf(i));
                    int s = a[i].compareTo("http:");
                    if(s>100)
                    {
                        imageLink=a[i];
                    }

                    //System.out.println(s +"***"+imageLink+ "\nPosition:" + String.valueOf(i));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView

            detailsTV.setText(details_1);
            dtitleTV.setText(title_1);
            if(imageLink.matches("")||imageLink==null)
            {

            }
            else
            {
                Glide.with(getActivity()).load(imageLink).into(imageView);
                System.out.println("***"+imageLink);
            }

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
