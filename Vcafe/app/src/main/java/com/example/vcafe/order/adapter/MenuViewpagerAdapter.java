package com.example.vcafe.order.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.vcafe.order.fragment.MenuItemCategoryFragment;
import com.example.vcafe.order.model.IUpdateView;
import com.example.vcafe.order.model.ItemCategory;

import java.util.ArrayList;
import java.util.List;

public class MenuViewpagerAdapter extends FragmentStatePagerAdapter   {
    private List<ItemCategory> menu;
    private final List<MenuItemCategoryFragment> fragments=new ArrayList<>();


    public MenuViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MenuViewpagerAdapter(FragmentManager fm, List<ItemCategory> menu) {
        super(fm);
        this.menu=menu;

        for(int i=0;i<menu.size();i++){
            fragments.add(new MenuItemCategoryFragment(menu.get(i).getItems()));
        }
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
       return menu.get(position).getCategory();
    }


}
