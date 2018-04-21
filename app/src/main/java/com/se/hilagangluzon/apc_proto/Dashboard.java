package com.se.hilagangluzon.apc_proto;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    Resources res;

    TabHost tabHost;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Lessons Tab
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Lessons");
        //spec.setContent(R.id.tab_lessons);
        host.addTab(spec);

        //Profile Tab
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Profile");
        //spec.setContent(R.id.tab_profile);
        host.addTab(spec);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
               if (childPosition+1 == listAdapter.getChildrenCount(groupPosition))
               {
                   Intent toTestScreen = new Intent(v.getContext(), TestScreen.class);
                   toTestScreen.putExtra("title", listDataHeader.get(groupPosition).toString());
                   v.getContext().startActivity(toTestScreen);
               }
               else
               {
                   Toast.makeText(
                           getApplicationContext(),
                           listDataHeader.get(groupPosition)
                                   + " : "
                                   + listDataChild.get(
                                   listDataHeader.get(groupPosition)).get(
                                   childPosition), Toast.LENGTH_SHORT)
                           .show();
               }
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        res = getResources();

        listDataHeader = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.lessons)));
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        //listDataHeader.add("Programming Fundamentals");

        // Adding child data
        List<String> lesson1 = new ArrayList<String>(Arrays.asList(res.getStringArray(R.array.modules)));
       /* lesson1.add("Read and Print");
        lesson1.add("Math Operators");
        lesson1.add("Boolean Operators");
        lesson1.add("Conditions");
        lesson1.add("Loops");*/
        lesson1.add("TEST"); //last child always TEST

        listDataChild.put(listDataHeader.get(0), lesson1); // Header, Child data
    }
}
