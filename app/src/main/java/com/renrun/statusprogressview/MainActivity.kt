package com.renrun.statusprogressview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.renrun.statusprogresslib.StatusProgressView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 未选中图片
     */
    private var unSelectDrawable = intArrayOf(
        com.renrun.statusprogresslib.R.drawable.shoiji_in,
        com.renrun.statusprogresslib.R.drawable.cheliang_in,
        com.renrun.statusprogresslib.R.drawable.cheliang_in,
        com.renrun.statusprogresslib.R.drawable.tijiao_in,
        com.renrun.statusprogresslib.R.drawable.tijiao_in
    )

    /**
     * 已选中图片
     */
    private var selectDrawable = intArrayOf(
        com.renrun.statusprogresslib.R.drawable.shouji_on,
        com.renrun.statusprogresslib.R.drawable.cheliang_on,
        com.renrun.statusprogresslib.R.drawable.cheliang_on,
        com.renrun.statusprogresslib.R.drawable.cheliang_on,
        com.renrun.statusprogresslib.R.drawable.tijiao_on
    )

    /**
     * 图片对应的文字
     */
    private var textArray = arrayOf("第一步", "第二步", "第三步", "第四步", "提交")


    /**
     * 设置的图片，文字要对应起来哦
     */
    fun setArgument(view: View) {
        statusView.setProgressDrawableAndText(unSelectDrawable, selectDrawable, textArray)
        statusView.setProgress(StatusProgressView.STEP_START)
    }

    var step = 0
    fun onNext(view: View) {
        if (step in 0..statusView.progressSize) {
            statusView.setProgress(step)
            step++
        }
    }

    fun onRest(view: View) {
        step = 0
        statusView.setProgress(StatusProgressView.STEP_START)
    }
}
