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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tabriz on 23-Apr-16.
 */
public class NewsDetailsFragment extends Fragment {
    View view;
    ImageView imageView;
    TextView detailsTV, dtitleTV;

    String title_1, details_1, imageLink = "";

    String information;

    ArrayList<String> newsDetails = new ArrayList<>();
    ArrayList<String> imageLinks = new ArrayList<>();
    ArrayList<String> newsTitles = new ArrayList<>();

    public NewsDetailsFragment(ArrayList<String> newsDetails, ArrayList<String> imageLinks, ArrayList<String> newsTitles) {
        this.newsDetails = newsDetails;
        this.imageLinks = imageLinks;
        this.newsTitles = newsTitles;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_detail_fragment, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        detailsTV = (TextView) view.findViewById(R.id.detailsTV);
        dtitleTV = (TextView) view.findViewById(R.id.dtitleTV);
        information = getArguments().getString("information");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int index = Integer.parseInt(information);
        title_1 = newsTitles.get(index).toString();
        details_1 = newsDetails.get(index).toString();
        imageLink = imageLinks.get(index);


        detailsTV.setText(details_1);
        dtitleTV.setText(title_1);
        if (imageLink.matches("") || imageLink == null) {

        } else {
            Glide.with(getActivity()).load(imageLink).into(imageView);
            System.out.println("***" + imageLink);
        }


    }

}
