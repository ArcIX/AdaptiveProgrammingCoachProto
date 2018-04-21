package com.se.hilagangluzon.apc_proto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class TrainScreen extends AppCompatActivity implements View.OnClickListener {

    Bundle extras;
    Resources res;
    String title;
    String instruct;

    ScrollView scrContent;
    TextView txvTitle, txvTimer, txvContent;
    EditText txfCode;
    Button btnSubmit, btnRun, btnHint;
    PopupWindow puwRun;

    Stopwatch sw = new Stopwatch();
    final int REFRESH_RATE = 100;
    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    long elapsedTime;
    Handler myHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            elapsedTime = sw.getElapsedTimeSecs();
            switch (msg.what) {
                case MSG_START_TIMER:
                    sw.start(); //start timer
                    myHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                   txvTimer.setText(""+ elapsedTime);
                    myHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                    break;                                  //though the timer is still running
                case MSG_STOP_TIMER:
                    myHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    sw.stop();//stop timer
                    txvTimer.setText(""+ elapsedTime);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_screen);
        prepareExtras();
        setupViews();
        addListeners();
        myHandler.sendEmptyMessage(MSG_START_TIMER);
    }

    @Override
    public void onBackPressed()
    {
        //pause timer
        new AlertDialog.Builder(this)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Paused")
                .setMessage("Are you sure you want to exit training?")
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
        //super.onBackPressed();
    }

    private void prepareExtras() {
        extras = getIntent().getExtras();

        title = extras.getString("title").toString();
    }

    private void prepareResources() {
        res = getResources();
        instruct = res.getString(R.string.instruct_read_and_print);
    }

    private void setupViews() {
        scrContent = findViewById(R.id.scrContent);
        scrContent.setVisibility(View.GONE);

        txvTitle = findViewById(R.id.txvTitle);
        txvTitle.setText(title);
        txvContent = findViewById(R.id.txvContent);
        txvContent.setText(instruct);
        txvContent.setVisibility(View.GONE);
        txvTimer = findViewById(R.id.txvTimer);

        txfCode = findViewById(R.id.txfCode);
        txfCode.setText("//Code here");

        btnSubmit = findViewById(R.id.btnSubmit);
        btnRun = findViewById(R.id.btnRun);
        btnHint = findViewById(R.id.btnHint);
    }

    private void addListeners() {
        btnRun.setOnClickListener(this);
        btnHint.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnRun:
                runCode(v);
                btnRun.setEnabled(false);
                break;
            case R.id.btnHint:
                showHint();
                break;
            case R.id.btnSubmit:
                submitCode();
                break;
            default:
                break;
        }
    }

    private void runCode(View v)
    {
        LayoutInflater inflaterForRun = (LayoutInflater) this.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewForRun = inflaterForRun.inflate(R.layout.popupwin_run, null);

        puwRun = new PopupWindow(viewForRun, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView txvCode = viewForRun.findViewById(R.id.txvCode);
        String codeToRun = txfCode.getText().toString();
        txvCode.setText(codeToRun);

        ImageButton btnClose = viewForRun.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                puwRun.dismiss();
                btnRun.setEnabled(true);
            }
        });

        puwRun.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    private void showHint()
    {
        Toast.makeText(this, "Use your imagination." , Toast.LENGTH_LONG).show();
    }

    private void submitCode()
    {
        myHandler.sendEmptyMessage(MSG_STOP_TIMER);
        //to Results
    }

    public void toggleContent(View v) {
        if(scrContent.isShown())
        {
            scrContent.setVisibility(View.GONE);
            txvContent.setVisibility(View.GONE);
            slideUp(this, scrContent);
        }
        else
        {
            slideDown(this, scrContent);
            scrContent.setVisibility(View.VISIBLE);
            txvContent.setVisibility(View.VISIBLE);
        }
    }

    private static void slideDown(Context ctx, View v)
    {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null)
        {
            a.reset();
            if(v != null)
            {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }

    }

    private static void slideUp(Context ctx, View v)
    {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if(a != null)
        {
            a.reset();
            if(v != null)
            {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }

    }
}
