package com.vpaliy.flip_concept;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.pager)
  AnimatedViewPager pager;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    pager.setAdapter(new AuthAdapter(getSupportFragmentManager(), pager));
  }
}
