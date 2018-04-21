package com.se.hilagangluzon.apc_proto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.text.Layout;
//import android.text.method.KeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LearnScreen extends AppCompatActivity {

    Bundle extras;
    Resources res;
    int pages;
    String title;
    ArrayList<String> contents, codes;
    int currentPage = 0;

    SeekBar skbLesson;
    ViewPager pgrContent;
    TextView txvTitle;
    ScrollView scrContent;
    Button btnPrevious, btnNext;

    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_screen);

        prepareExtras();
        prepareResources();
        setupViews();
        addListeners();
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Paused")
                .setMessage("Are you sure you want to exit the lesson?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //resume timer
                    }
                })
                .show();
    }

    private void prepareExtras()
    {
        extras = getIntent().getExtras();

        if (extras != null)
        {
            title = (String) extras.getString("title");
        }
    }

    private void prepareResources() {
        res = getResources();
        contents = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.content_read_and_print)));
        codes = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.codes_read_and_print)));
        pages = contents.size();
    }

    private void setupViews()
    {
        skbLesson = findViewById(R.id.skbLesson);
        skbLesson.setMax(pages - 1);
        skbLesson.setProgress(currentPage);
        skbLesson.setEnabled(false);

        scrContent = findViewById(R.id.scrContent);

        pgrContent = findViewById(R.id.pgrContent);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), pages, contents, codes);
        pgrContent.setAdapter(pagerAdapter);
        pgrContent.setCurrentItem(currentPage);

        txvTitle = findViewById(R.id.txvTitle);
        txvTitle.setText(title);

        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    private void addListeners()
    {
        btnPrevious.setOnClickListener(new ButtonActor());
        btnNext.setOnClickListener(new ButtonActor());
        pgrContent.addOnPageChangeListener(new ViewPagerActor());
    }

    private class ButtonActor implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btnPrevious:
                    goToPrevious();
                    break;
                case R.id.btnNext:
                    goToNext();
                    break;
                default:
                    break;
            }
        }

        private void goToPrevious()
        {
            if (currentPage > 0) {
                currentPage--;
                skbLesson.setProgress(currentPage);
                pgrContent.setCurrentItem(currentPage);
            }
        }

        private void goToNext()
        {
            if (currentPage < pages - 1) {
                currentPage++;
                skbLesson.setProgress(currentPage);
                pgrContent.setCurrentItem(currentPage);
            }
        }
    }

    private class ViewPagerActor implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (currentPage > position)
            {
                progressBackward();
            } else if (currentPage < position)
            {
                progressForward();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        private void progressForward()
        {
            if (currentPage < pages - 1)
            {
                skbLesson.setProgress(++currentPage);
            }
        }

        private void progressBackward()
        {
            if (currentPage > 0)
            {
                skbLesson.setProgress(--currentPage);
            }
        }
    }
}
