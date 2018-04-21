package com.se.hilagangluzon.apc_proto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txfUser, txfPass;
    TextView txvStat;
    Button btnSignIn, btnFacebook, btnGmail, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        addListeners();
    }

    private void setupViews()
    {
        txfUser = findViewById(R.id.txfUser);
        txfPass = findViewById(R.id.txfPass);
        txvStat = findViewById(R.id.txvStat);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnGmail = findViewById(R.id.btnGmail);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void addListeners()
    {
        btnSignIn.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnGmail.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btnSignIn:
                signIn();
                break;
            case R.id.btnFacebook:
                break;
            case R.id.btnGmail:
                break;
            case R.id.btnSignUp:
                break;
            default:
                break;
        }
    }

    private void signIn() {
        Intent toDashboard;
        //please change this next time
        String typedUser, typedPass;
        typedUser = txfUser.getText().toString();
        typedPass = txfPass.getText().toString();
        //SELECT Password FROM User WHERE Username=typedUser;
        try {
            if (/*typedUser.equals("rc@apc.com") && typedPass.equals("12345")*/true) {
                toDashboard = new Intent(this, Dashboard.class);
                startActivity(toDashboard);
            } else {
                txvStat.setText("Invalid username/password.");
            }
        } catch (NullPointerException npe) {
            txvStat.setText("Please enter your username/password.");
        }
    }

    private void signInWithFacebook()
    {

    }

    private void signInWithGmail()
    {

    }

    private void signUp()
    {
        /*
        Intent toSignUpScreen = new Intent(this, SignUpScreen.class);
        startActivity(toSignUpScreen);
         */
    }
}
