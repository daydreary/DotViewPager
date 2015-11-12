# DotViewPager

  This purpose of this project is adding some circle indicator to a ViewPager.
  <br>Example:<br>
  ![](http://c.picphotos.baidu.com/album/s%3D740%3Bq%3D90/sign=07ba47c74f540923ae69617aa263a034/29381f30e924b899230ac0dc68061d950a7bf624.jpg)
  
  
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
  If you want to change the circle's style, you can simply modify the `ic_dot_normal.png` and the `ic_dot_selected` in /res/drawable-hdpi<br>
  <br>
  The DotViewPager is a subclass of FrameLayout actually instead of a ViewPager.<br>
  It provides some methods to manage the ViewPager<br>
  You can add more views to it if you need.<br>
