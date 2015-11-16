package com.cx.dvpdemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cx.dotviewpager.view.DotViewPager;
import com.cx.dvpdemo.R;

public class MainActivity extends Activity implements OnClickListener {
	
	private List<View> list;
	private DotViewPager dot;
	private MyPagerAdapter adapter;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		
		dot.setAdapter(adapter);
		
		dot.setGravity(DotViewPager.BOTTOM_RIGHT);
		dot.setMarginDip(30, 30, 30, 30);
	}
	
	private void init() {
		dot = (DotViewPager) findViewById(R.id.dot);
		button = (Button) findViewById(R.id.btn);
		button.setOnClickListener(this);
		button.setText("use it in fragment (automatic cycling)");
		
		list = new ArrayList<View>();
		for (int i = 0; i < 4; i++) {
			ImageView img = new ImageView(this);
			img.setTag(i);
			img.setOnClickListener(this);
			switch (i) {
			case 0:
				img.setImageResource(R.drawable.image1);break;
			case 1:
				img.setImageResource(R.drawable.image2);break;
			case 2:
				img.setImageResource(R.drawable.image3);break;
			case 3:
				img.setImageResource(R.drawable.image4);break;
			}
			list.add(img);
		}
		adapter = new MyPagerAdapter(list);
	}
	
	
	private class MyPagerAdapter extends PagerAdapter {
		
		List<View> list;
		
		public MyPagerAdapter(List<View> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(list.get(position), 0);
			return list.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
	}


	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn) {
			startActivity(new Intent(this, HalfImageActivity.class));
		} else {
			int index = (Integer) v.getTag();
			Toast.makeText(this, "position="+index, Toast.LENGTH_SHORT).show();
		}
	}
}
