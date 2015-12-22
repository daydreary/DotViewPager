# DotViewPager

  DotViewPager可以为ViewPager提供一组圆点indicator。同时支持ViewPager的自动、循环滚动。
  <br>示意图:<br>
  ![](https://github.com/daydreary/DotViewPager/raw/master/img2.png)
  
  
## 如何使用

  1. 把library引用到你的工程里.
  2. 在xml中使用:
  
  ```
  <com.cx.dotviewpager.view.DotViewPager 
        android:id="@+id/dot"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:dvpViewPager="@+id/vPager">
        <android.support.v4.view.ViewPager
            android:id="@+id/vPager"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
  </com.cx.dotviewpager.view.DotViewPager >
  ```
  `dvpViewPager`属性很重要，如果不设置将无法引入ViewPager
  
  3. 在代码中进行设置。使用`DotViewPager.setAdapter()`替代ViewPager的setAdapter方法。
  <br>示例代码:
  ```
  dot = (DotViewPager) findViewById(R.id.dot);
  dot.setAdapter(yourPagerAdapter);
  ```
  
  4. 设置循环及自动滚动
  <br>如下设置:<br>
  ```
  private void init() {
		//初始化list
		list = new ArrayList<View>();
		
		//给list添加一个header，相当于是额外添加最后一个view到list最前面
		ImageView img0 = new ImageView(getActivity());
		img0.setTag(3);
		img0.setOnClickListener(this);
		img0.setImageResource(R.drawable.image4);
		list.add(img0);
		
		//添加需要的数据
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
		
		//给list添加一个footer，相当于是额外添加第一个view到list的末尾
		ImageView imgl = new ImageView(getActivity());
		imgl.setTag(0);
		imgl.setOnClickListener(this);
		imgl.setImageResource(R.drawable.image1);
		list.add(imgl);
		adapter = new MyPagerAdapter(list);
		
		//设置adapter同时开启循环
		dot.setAdapterWithLoop(adapter);
		//调用这个方法时候，记得在onDestory里调用setAutoScroll(false, 0, null)，以确保不会出现内存泄漏
		dot.setAutoScroll(true, 500, null);
	}
	```
  <br>
  循环原理如下：假如有 0-1-2-3 这几个view
  <br>
  额外添加header和footer，变为  3-0-1-2-3-0
  <br>
  当3切换到0之后，自动跳转到第一个0的位置，实现循环。
  <br>
  手指触摸ViewPager后，自动循环会停止计时，直到手指抬起后重新计时。
  
## 更多功能
  如果要给ViewPager加监听，调用 `dotViewPager.setOnTouchListener()` 和 `dotViewPager.setOnPageChangeListener()`替代ViewPager本身的方法<br>

  1.`dotViewPager.setGravity(DotViewPager.BOTTOM_CENTER)` 可以改变圆点的位置。目前支持7个位置。<br>
  2.`dotViewPager.setMarginDip(30, 30, 30, 30)` 或者 `dotViewPager.setMarginPixel(30, 30, 30, 30)` 可以改变圆点四周的边距。<br>
  3.`dotViewPager.setAutoScroll(true, 500, null)`. 这方法会使ViewPager循环滚动<br>
     `注意:` 如果调用过这个方法, 你需要在onDestory里调用 `setAutoScroll(false, 0, null)` 以确保不会出现内存泄漏<br>
     第二个参数表示页面切换的动画的时间。<br>
     第三个参数你可以给动画设置一个interpolator，默认的是AccelerateDecelerateInterpolator<br>
  4.`dotViewPager.setScrollDuration(300)` 同setAutoScroll中的第二个参数，页面切换的动画时间<br>
  5.`dotViewPager.setDelay(2000)` 可以改变切换频率(即等多久切换到下一页)<br>
  
  <br>
  如果你想改变圆点的样式，你可以用自己的图片素材覆盖/res/drawable-hdpi下的 `ic_dot_normal.png` 和 `ic_dot_selected.png`<br>
  <br>
  DotViewPager本质是一个FrameLayout，提供了一些ViewPager的管理方法<br>
  如果你需要添加一些其他的view，可以直接在布局文件中添加。<br>



# DotViewPager English Instruction

  This purpose of this project is adding some circle indicators to a ViewPager. Support automatic scrolling and cycling.
  <br>Example:<br>
  ![](https://github.com/daydreary/DotViewPager/raw/master/img2.png)
  
  
## How to use

  1. Add the DotViewPager library to your project.
  2. Use it in your xml file:
  
  ```
  <com.cx.dotviewpager.view.DotViewPager 
        android:id="@+id/dot"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:dvpViewPager="@+id/vPager">
        <android.support.v4.view.ViewPager
            android:id="@+id/vPager"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
  </com.cx.dotviewpager.view.DotViewPager >
  ```
  The `dvpViewPager` attribute is neccessary. Without it, the ViewPager can't be found.
  
  3. Set it in the codes. Use `DotViewPager.setAdapter()` instead of the ViewPager's method. 
  <br>Example:
  ```
  dot = (DotViewPager) findViewById(R.id.dot);
  dot.setAdapter(yourPagerAdapter);
  ```
  
  4. Make it automatic cycling
  <br>Setting like this:<br>
  ```
  private void init() {
		//start to initialize the list
		list = new ArrayList<View>();
		
		//add header to list. it is a clone of last view
		ImageView img0 = new ImageView(getActivity());
		img0.setTag(3);
		img0.setOnClickListener(this);
		img0.setImageResource(R.drawable.image4);
		list.add(img0);
		
		//add the images
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
		
		//add footer to list. it is a clone of first view
		ImageView imgl = new ImageView(getActivity());
		imgl.setTag(0);
		imgl.setOnClickListener(this);
		imgl.setImageResource(R.drawable.image1);
		list.add(imgl);
		adapter = new MyPagerAdapter(list);
		
		dot.setAdapterWithLoop(adapter);
		//remember to call setAutoScroll(false, 0, null) in onDestroy()
		dot.setAutoScroll(true, 500, null);
	}
	```
  <br>
  If there are '0-1-2-3' four views 
  <br>
  add header and footer. These will be '3-0-1-2-3-0'
  <br>
  When sliding from 3 to 0, auto jump to first view0.
  <br>
  When touching the ViewPager, the automatic cycling will stop until your fingers up.

  
## More functions
  Use the `dotViewPager.setOnTouchListener()` and `dotViewPager.setOnPageChangeListener()` if you want to add listener to the ViewPager.<br>

  1.`dotViewPager.setGravity(DotViewPager.BOTTOM_CENTER)` can change the position of the circle indicator. Seven positions are available.<br>
  2.`dotViewPager.setMarginDip(30, 30, 30, 30)` or `dotViewPager.setMarginPixel(30, 30, 30, 30)` can change the margin of the circle indicator.<br>
  3.`dotViewPager.setAutoScroll(true, 500, null)`. This method will make the ViewPager auto scrolling.<br>
     `warning:` If you use setAutoScroll, you have to call `setAutoScroll(false, 0, null)` in onDestroy()<br>
     The second parameter means the animation will last 500ms.<br>
     The third parameter is the animation Interpolator. Default is AccelerateDecelerateInterpolator.<br>
  4.`dotViewPager.setScrollDuration(300)` can change the duration of scrolling.<br>
  5.`dotViewPager.setDelay(2000)` can change the frequency of scrolling.<br>
  
  <br>
  If you want to change the circle's style, you can simply modify the `ic_dot_normal.png` and the `ic_dot_selected.png` in /res/drawable-hdpi<br>
  <br>
  The DotViewPager is a subclass of FrameLayout actually instead of a ViewPager.<br>
  It provides some methods to manage the ViewPager<br>
  You can add more views to it if you need.<br>
