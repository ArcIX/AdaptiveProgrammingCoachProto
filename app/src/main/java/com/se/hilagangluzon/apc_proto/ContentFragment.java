package com.se.hilagangluzon.apc_proto;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * Created by Rc Zeravla on 09/04/2018.
 */

public class ContentFragment extends Fragment {

    String content;
    String code;

    ScrollView scrContent;
    TextView txvContent;
    EditText txfCode;
    Button btnRun;
    PopupWindow puwRun;

    public static ContentFragment newInstance(String content, String code)
    {
        ContentFragment cf = new ContentFragment();
        cf.content = content;
        cf.code = code;
        return cf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_content, container, false);

        scrContent = rootView.findViewById(R.id.scrContent);

        txvContent = rootView.findViewById(R.id.txvContent);
        txvContent.setText(content);

        txfCode = rootView.findViewById(R.id.txfCode);
        txfCode.setText(code);

        btnRun = rootView.findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflaterForRun = (LayoutInflater) rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    }
                });

                puwRun.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        return rootView;
    }
}
