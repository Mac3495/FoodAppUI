package com.project.rafa.yourfood.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class TabDetailAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public TabDetailAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
    public void addFragment(Fragment fragment,String title, String nombre, String ingrediente, String preparacion, String costo, String foodId, String user) {
        Bundle bundle = new Bundle();
        bundle.putString("titulo",title);
        bundle.putString("nombre",nombre);
        bundle.putString("ingrediente",ingrediente);
        bundle.putString("preparacion",preparacion);
        bundle.putString("costo",costo);
        bundle.putString("foodId",foodId);
        bundle.putString("user",user);
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}