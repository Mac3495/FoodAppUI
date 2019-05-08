package com.project.rafa.yourfood.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.adapter.TabDetailAdapter;
import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.fragment.RecommendedFragment;
import com.project.rafa.yourfood.fragment.TextFragment;

public class DetailActivity extends AppCompatActivity {

    ImageView img;
    TextView textView;

    private TabDetailAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img = findViewById(R.id.img_detail);
        textView = findViewById(R.id.text_detail);

        Bundle bundle = getIntent().getExtras();
        Food food = (Food) bundle.getSerializable("food");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(food.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(food.getName());
        String url = food.getImg();
        Glide.with(getApplicationContext()).load(url).into(img);
//        textView.setText(food.getIngredients());



        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabDetailAdapter(getSupportFragmentManager());
        adapter.addFragment(new TextFragment(), "Preparar", "nombre", "ingrediente", "preparacion");
        adapter.addFragment(new TextFragment(), "Ingredientes", "nombre", "ingrediente", "preparacion");
        adapter.addFragment(new RecommendedFragment(), "Similares",  "nombre", "ingrediente", "preparacion");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1, false);

    }
}
