package com.se.hilagangluzon.apc_proto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TestScreen extends AppCompatActivity implements View.OnClickListener {

    Bundle extras;
    Resources res;
    String title;
    String instruct;

    ScrollView scrContent;
    TextView txvTitle, txvTimer, txvContent;
    EditText txfCode;
    Button btnSubmit, btnRun;
    PopupWindow puwRun;

    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);
        prepareExtras();
        setupViews();
        addListeners();
        cdt = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                txvTimer.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_LONG).show();
                //to Results
            }
        }.start();
    }

    @Override
    public void onBackPressed()
    {
        //pause timer
        new AlertDialog.Builder(this)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Paused")
                .setMessage("Are you sure you want to exit testing?")
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

    private void prepareExtras()
    {
        extras = getIntent().getExtras();

        if (extras != null) {
            title = extras.getString("title").toString();
        }
    }

    private void prepareResources() {
        res = getResources();

        instruct = res.getString(R.string.instruct_read_and_print);
    }

    private void setupViews()
    {
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
    }

    private void addListeners()
    {
        btnRun.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnRun:
                runCode(v);
                btnRun.setEnabled(false);
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

    private void submitCode()
    {
        cdt.cancel();
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
