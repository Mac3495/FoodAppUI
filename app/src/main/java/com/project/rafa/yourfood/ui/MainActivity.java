package com.project.rafa.yourfood.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.fragment.ExploreFragment;
import com.project.rafa.yourfood.fragment.FavoriteFragment;
import com.project.rafa.yourfood.fragment.FeedFragment;

public class MainActivity extends AppCompatActivity {

    final Fragment fragmentFav = new FavoriteFragment();
    final Fragment fragmentFeed = new FeedFragment();
    final Fragment fragmentExplore = new ExploreFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment active = fragmentFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_container, fragmentFeed, "1").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    openFragment(fragmentFeed);
                    return true;
                case R.id.navigation_fav:
                    openFragment(fragmentFav);
                    return true;
                case R.id.navigation_explore:
                    openFragment(fragmentExplore);
                    return true;
                case R.id.navigation_add:
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                    return true;
            }
            return false;
        }
    };

    void openFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
    }

}
