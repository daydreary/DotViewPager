package com.cx.dvpdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cx.dotviewpager.view.DotViewPager;
import com.cx.dvpdemo.R;

public class MyFragment extends Fragment implements OnClickListener{
	
	private List<View> list;
	private DotViewPager dot;
	private MyPagerAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment, container, false);
		dot = (DotViewPager) view.findViewById(R.id.dot);
		init();
		dot.setAdapter(adapter);
		dot.setGravity(DotViewPager.BOTTOM_CENTER);
		dot.setMarginDip(0, 0, 0, 20);
		dot.setAutoScroll(true, 500, null);
//		dot.setScrollDuration(300);
//		dot.setDelay(2000);
		return view;
	}
	
	private void init() {
		list = new ArrayList<View>();
		for (int i = 0; i < 4; i++) {
			ImageView img = new ImageView(getActivity());
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
		int index = (Integer) v.getTag();
		Toast.makeText(getActivity(), "position="+index, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		dot.setAutoScroll(false, 0, null);
		super.onDestroy();
	}
}
