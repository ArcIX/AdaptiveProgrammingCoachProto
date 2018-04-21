package com.se.hilagangluzon.apc_proto;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Rc Zeravla on 09/04/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int pages;
    private ArrayList<String> contents;
    private ArrayList<String> codes;

    public ViewPagerAdapter(FragmentManager fm, int pages, ArrayList<String> contents, ArrayList<String> codes)
    {
        super(fm);
        this.pages = pages;
        this.contents = contents;
        this.codes = codes;
    }

    @Override
    public Fragment getItem(int position)
    {
        /*ContentFragment cf = new ContentFragment();
        cf.mother = mother;
        cf.content = contents.get(position);
        cf.code = codes.get(position);
        return cf;*/
        return ContentFragment.newInstance(contents.get(position), codes.get(position));
    }

    @Override
    public int getCount() {
        return pages;
    }
}
