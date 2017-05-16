package com.test.xzh.mytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.test.xzh.mytest.fragment.CacheFragment;
import com.test.xzh.mytest.fragment.ElementaryFragment;
import com.test.xzh.mytest.fragment.MapFragment;
import com.test.xzh.mytest.fragment.TokenAdvancedFragment;
import com.test.xzh.mytest.fragment.TokenFragment;
import com.test.xzh.mytest.fragment.ZipFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzh on 2017/3/8
 */

public class Test7Activity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test7);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ElementaryFragment();
                    case 1:
                        return new MapFragment();
                    case 2:
                        return new ZipFragment();
                    case 3:
                        return new TokenFragment();
                    case 4:
                        return new TokenAdvancedFragment();
                    case 5:
                        return new CacheFragment();
                    default:
                        return new ElementaryFragment();
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.title_elementary);
                    case 1:
                        return getString(R.string.title_map);
                    case 2:
                        return getString(R.string.title_zip);
                    case 3:
                        return getString(R.string.title_token);
                    case 4:
                        return getString(R.string.title_token_advanced);
                    case 5:
                        return getString(R.string.title_cache);
                    default:
                        return getString(R.string.title_elementary);
                }
            }
        });
        tabs.setupWithViewPager(vp);


    }


































}
