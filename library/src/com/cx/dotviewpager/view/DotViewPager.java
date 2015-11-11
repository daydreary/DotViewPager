package com.cx.dotviewpager.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cx.dotviewpager.R;

public class DotViewPager extends FrameLayout {
	
	private RadioGroup radioGroup;
	private ViewPager pager;
	
	private int pagerId;
	private Context context;
	
	private PagerAdapter adapter;
	private OnPageChangeListener pageListener;
	private OnTouchListener touchListener;
	private ViewPagerScroller scroller;
	
	public static final int BOTTOM_CENTER = 1;
	public static final int BOTTOM_LEFT = 2;
	public static final int BOTTOM_RIGHT = 3;
	public static final int CENTER_ABSOLUTE = 4;
	public static final int TOP_CENTER = 5;
	public static final int TOP_LEFT = 6;
	public static final int TOP_RIGHT = 7;
	
	private Handler handler;
	private boolean autoScroll;
	private int delay = 3000;
	
	public DotViewPager(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		doInit(attrs);
	}

	public DotViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		doInit(attrs);
	}
	
	private void doInit(AttributeSet attrs) {
		this.context = getContext();
		if (attrs != null) {
			TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.DotViewPager);
			if (ta != null) {
				pagerId = ta.getResourceId(R.styleable.DotViewPager_dvpViewPager, -1);
				ta.recycle();
			}
		}
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (pagerId != -1) {
			pager = (ViewPager) findViewById(pagerId);
			if (pager != null) {
				LayoutParams params = (LayoutParams) pager.getLayoutParams();
				params.gravity = Gravity.CENTER;
				pager.setLayoutParams(params);
				pager.setOnPageChangeListener(new OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						setPositionChecked(position);
						if (pageListener != null) {
							pageListener.onPageScrollStateChanged(position);
						}
					}
					@Override
					public void onPageScrolled(int position, float offset, int offsetPixels) {
						if (pageListener != null) {
							pageListener.onPageScrolled(position, offset, offsetPixels);
						}
					}
					@Override
					public void onPageScrollStateChanged(int arg0) {
						if (pageListener != null) {
							pageListener.onPageScrollStateChanged(arg0);
						}
					}
				});
				pager.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (handler != null) {
							handler.removeMessages(1);
						}
						if (event.getAction() == MotionEvent.ACTION_UP
								&& autoScroll) {
							sendScrollMsg();
						}
						if (touchListener != null) {
							return touchListener.onTouch(v, event);
						} else {
							return false;
						}
					}
				});
			}
		}
	}
	
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.pageListener = listener;
	}
	
	public void setOnTouchListener(OnTouchListener listener) {
		this.touchListener = listener;
	}
	
	public void setAdapter(PagerAdapter adapter) {
		if (adapter != null) {
			this.adapter = adapter;
			pager.setAdapter(adapter);
			setRadioGroup(adapter.getCount());
		}
	}
	
	public void notifyDataSetChanged() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
			setRadioGroup(adapter.getCount());
		}
	}
	
	public void setMarginPixel(int left, int top, int right, int bottom) {
		if (radioGroup != null) {
			LayoutParams params = (LayoutParams) radioGroup.getLayoutParams();
			params.bottomMargin = bottom;
			params.topMargin = top;
			params.leftMargin = left;
			params.rightMargin = right;
			radioGroup.setLayoutParams(params);
		}
	}
	
	public void setMarginDip(int left, int top, int right, int bottom) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int leftDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, metrics);
		int rightDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, metrics);
		int topDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, metrics);
		int bottomDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom, metrics);
		if (radioGroup != null) {
			LayoutParams params = (LayoutParams) radioGroup.getLayoutParams();
			params.bottomMargin = bottomDip;
			params.topMargin = topDip;
			params.leftMargin = leftDip;
			params.rightMargin = rightDip;
			radioGroup.setLayoutParams(params);
		}
	}
	
	public void setGravity(int gravity) {
		if (radioGroup != null) {
			LayoutParams params = (LayoutParams) radioGroup.getLayoutParams();
			switch (gravity) {
			case BOTTOM_LEFT:
				params.gravity = Gravity.BOTTOM | Gravity.LEFT;
				break;
			case BOTTOM_RIGHT:
				params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
				break;
			case CENTER_ABSOLUTE:
				params.gravity = Gravity.CENTER;
				break;
			case TOP_CENTER:
				params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
				break;
			case TOP_LEFT:
				params.gravity = Gravity.TOP | Gravity.LEFT;
				break;
			case TOP_RIGHT:
				params.gravity = Gravity.TOP | Gravity.RIGHT;
				break;
			default:
				params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
				break;
			}
			radioGroup.setLayoutParams(params);
		}
	}
	
	private void setRadioGroup(int count) {
		if (count <= 0) {
			return;
		}
		if (radioGroup == null) {
			radioGroup = new RadioGroup(context);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
			params.bottomMargin = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
							.getDisplayMetrics());
			radioGroup.setLayoutParams(params);
			radioGroup.setOrientation(LinearLayout.HORIZONTAL);
			addView(radioGroup);
			
			for (int i = 0; i < count; i++) {
				radioGroup.addView(initRadioButton(i));
			}
		} else {
			radioGroup.removeAllViews();
			for (int i = 0; i < count; i++) {
				radioGroup.addView(initRadioButton(i));
			}
		}
		setPositionChecked(pager.getCurrentItem());
	}
	
	@SuppressLint("InflateParams") 
	private View initRadioButton(int index) {
        RadioButton radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.view_pager_dot, null);
        return radioButton;
    }
	
	private void setPositionChecked(int position) {
		if (position >= radioGroup.getChildCount()) {
			return;
		}
		RadioButton button = (RadioButton) radioGroup.getChildAt(position);
		button.setChecked(true);
	}

	public boolean isAutoScroll() {
		return autoScroll;
	}
	
	public void setScrollDuration(int duration) {
		if (scroller != null) {
			scroller.setScrollDuration(duration);
		}
	}

	public void setAutoScroll(boolean autoScroll, int duration, Interpolator i) {
		this.autoScroll = autoScroll;
		if (autoScroll) {
			initHandler();
			if (duration > 0) {
				initScroller(duration, i);
			}
		} else {
			removeAutoScroll();
		}
	}
	
	private void initScroller(int duration, Interpolator i) {
		Interpolator interpolator = null;
		if (i != null) {
			interpolator = i;
		} else {
			interpolator = new AccelerateDecelerateInterpolator();
		}
		scroller = new ViewPagerScroller(context, interpolator);
		scroller.setScrollDuration(duration);
		scroller.resetScrollDuration(pager);
	}
	
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void setAutoScrollDuration(int duration) {
		scroller.setScrollDuration(duration);
	}

	private void removeAutoScroll() {
		if (handler != null) {
			handler.removeMessages(1);
			handler = null;
		}
	}
	
	private void initHandler() {
		if (handler == null) {
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (autoScroll && pager != null) {
						int curr = pager.getCurrentItem();
						int next = curr + 1;
						if (next > pager.getChildCount()) {
							next = 0;
						}
						pager.setCurrentItem(next, true);
						sendScrollMsg();
					}
					super.handleMessage(msg);
				}
			};
			sendScrollMsg();
		}
	}
	
	private void sendScrollMsg() {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(1, delay);
		}
	}
}
