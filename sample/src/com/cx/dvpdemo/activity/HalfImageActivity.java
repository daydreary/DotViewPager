package com.cx.dvpdemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.cx.dvpdemo.R;
import com.cx.dvpdemo.fragment.MyFragment;
import com.cx.dvpdemo.fragment.TestFragment;

public class HalfImageActivity extends FragmentActivity {

	private ViewPager pager;
	private List<Fragment> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.half);
		
		init();
	}
	
	private void init() {
		pager = (ViewPager) findViewById(R.id.vPager);
		list = new ArrayList<Fragment>();
		MyFragment mFragment = new MyFragment();
		list.add(mFragment);
		for (int i = 0; i < 4; i++) {
			TestFragment testFragment = new TestFragment();
			list.add(testFragment);
		}
		MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), list);
		pager.setAdapter(adapter);
	}
	
}
