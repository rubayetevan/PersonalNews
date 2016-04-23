package com.bdjobs.personalnews;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Communicator {
    FragmentManager fragmentManager;
    NewsListFragment newsListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        newsListFragment = new NewsListFragment();
        newsListFragment.setCommunicator(this);
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.add(R.id.landingPage,newsListFragment,"newsListFragment");
        transaction.commit();

    }

    @Override
    public void information(String information) {
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
        FragmentTransaction transactionR = fragmentManager.beginTransaction();

        Bundle args = new Bundle();
        args.putString("information", information);
        newsDetailsFragment.setArguments(args);
        transactionR.replace(R.id.landingPage,newsDetailsFragment,"newsDetailsFragment");
        transactionR.addToBackStack("newsDetailsFragment");
        transactionR.commit();



    }

}
