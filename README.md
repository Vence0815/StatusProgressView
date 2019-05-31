# StatusProgressView
可以自定义参数的状态进度指示条

#### 1.场景
在平常开发中，有时候一个任务需要分多步操作，UI就会设计出下面这种类似于时光轴的东西。


![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531162145586.png)

由于我们用的比较多，我就把这个功能封装了一下，做成可以自己配置相关参数的自定义view。最终实现效果如下所示。

![状态进度条](https://img-blog.csdnimg.cn/2019053116275421.gif)

#### 2.使用方式
在XML 文件中直接设置
 ```javascript
	  <com.renrun.statusprogresslib.StatusProgressView
            android:layout_width="match_parent"
            android:background="#ffffff"
            android:id="@+id/statusView"
            app:spv_progress_color="#ff3d8df0"
            app:spv_progress_default_color="#999999"
            app:spv_progress_text_color="#ff3d8df0"
            app:spv_progress_text_default_color="#999999"
            app:spv_progress_text_size="11dp"
            app:spv_progress_height="1dp"
            app:spv_progress_text_margin_top="11dp"
            android:layout_height="90dp"/>
```


相关参数说明
```javascript
  	app:spv_progress_color="#ff3d8df0" //进度条颜色
            app:spv_progress_default_color="#999999"//进度条的底色
            app:spv_progress_text_color="#ff3d8df0"//文字选中颜色
            app:spv_progress_text_default_color="#999999"//文字未选中颜色
            app:spv_progress_text_size="11dp"//文字大小
            app:spv_progress_height="1dp"//进度条的高度
            app:spv_progress_text_margin_top="11dp"//文字距离图片的距离
```

代码设置文字和图片

```javascript
  /**
     * 未选中图片
     */
    private var unSelectDrawable = intArrayOf(
        R.drawable.shoiji_in,
        R.drawable.cheliang_in,
        R.drawable.cheliang_in,
        R.drawable.tijiao_in,
        R.drawable.tijiao_in
    )

    /**
     * 已选中图片
     */
    private var selectDrawable = intArrayOf(
        R.drawable.shouji_on,
        R.drawable.cheliang_on,
        R.drawable.cheliang_on,
        R.drawable.cheliang_on,
        R.drawable.tijiao_on
    )

    /**
     * 图片对应的文字
     */
    private var textArray = arrayOf("第一步", "第二步", "第三步", "第四步", "提交")

     //设置图片及文字
     statusView.setProgressDrawableAndText(unSelectDrawable, selectDrawable, textArray)
    //调用setProgress 才会去重绘界面
     statusView.setProgress(StatusProgressView.STEP_START)
```
