package com.is.jishelpdesk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments=new ArrayList<>();
    private List<String> fragmentTitle=new ArrayList<>();

    public ItemViewPagerAdapter(FragmentManager childFragmentManager, int behaviour) {
        super(childFragmentManager,behaviour);
    }

    public void addFragment(Fragment fragment,String title){
        fragments.add(fragment);
        fragmentTitle.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}
