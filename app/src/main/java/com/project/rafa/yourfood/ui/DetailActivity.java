package com.project.rafa.yourfood.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.Food;

public class DetailActivity extends AppCompatActivity {

    ImageView img;
    TextView textView;

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
        img.setImageResource(food.getImg());
        textView.setText(food.getIngredients());

    }
}
