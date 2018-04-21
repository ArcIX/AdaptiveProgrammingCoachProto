package com.se.hilagangluzon.apc_proto;

import android.content.Intent;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Created by Rc Zeravla on 07/04/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (childPosition+1 == getChildrenCount(groupPosition))
            {
                convertView = infalInflater.inflate(R.layout.list_item_simple, null);
            }
            else
            {
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }
        }

        if (childPosition+1 == this.getChildrenCount(groupPosition))
        {
            TextView txtListChild = (TextView) convertView.findViewById(R.id.txvTest);
            txtListChild.setText("TEST");
        }
        else
        {
            TextView txtListChild = (TextView) convertView.findViewById(R.id.lblModule);
            txtListChild.setText(childText);

            Button btnLearn = (Button) convertView.findViewById(R.id.btnLearn);
            btnLearn.setText("Learn");
            btnLearn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //transfer of values to Learn Screen and go to Learn screen
                    Intent toLearnScreen = new Intent(view.getContext(), LearnScreen.class);

                    toLearnScreen.putExtra("title", childText.toString());

                    view.getContext().startActivity(toLearnScreen);
                }
            });

            Button btnTrain = (Button) convertView.findViewById(R.id.btnTrain);
            btnTrain.setText("Train");
            btnTrain.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //transfer of values to Train Screen and go to Train screen
                    Intent toTrainScreen = new Intent(view.getContext(), TrainScreen.class);

                    toTrainScreen.putExtra("title", childText.toString());

                    view.getContext().startActivity(toTrainScreen);
                }
            });
        }
        
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblChapter = (TextView) convertView.findViewById(R.id.lblChapter);
        lblChapter.setTypeface(null, Typeface.BOLD);
        lblChapter.setText(headerTitle);
        
        /*
        Button btnTest = (Button) convertView.findViewById(R.id.btnTest);
        btnTest.setText("Test");
        btnTest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Test button for " + headerTitle + " is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        */

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
