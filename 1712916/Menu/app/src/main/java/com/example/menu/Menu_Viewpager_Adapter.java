package com.example.menu;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class Menu_Viewpager_Adapter extends FragmentStatePagerAdapter {
    private ArrayList<Category_Menu> dsMenu;


    public Menu_Viewpager_Adapter(FragmentManager fm) {
        super(fm);
    }

    public Menu_Viewpager_Adapter(FragmentManager fm, ArrayList<Category_Menu> dsMenu) {
        super(fm);
        this.dsMenu=dsMenu;
    }
    @Override
    public Fragment getItem(int position) {
        return new Menu_Fragment(dsMenu.get(position).getList());
    }

    @Override
    public int getCount() {
        return dsMenu.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
       return dsMenu.get(position).getLoai();
    }
}
