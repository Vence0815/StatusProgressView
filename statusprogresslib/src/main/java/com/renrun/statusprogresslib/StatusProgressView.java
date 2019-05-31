package com.renrun.statusprogresslib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by vence on 2019-05-28 10:14
 * 邮箱 ：vence0815@foxmail.com
 * 带状态改变的进度条
 */

public class StatusProgressView extends View {

    /**
     * 绘制的文字数组
     */
    private String[] textArray = {"第一步", "第二步", "第三步", "提交"};
    /**
     * 文字大小
     */
    private int textSize;
    /**
     * 文字选中颜色
     */
    private int textSelectColor;
    /**
     * 文字未选中颜色
     */
    private int textDefaultColor;
    /**
     * 进度条默认颜色
     */
    private int prgressDefaultColor;
    /**
     * 进度条颜色
     */
    private int prgressColor;
    /**
     * 进度条高度
     */
    private int prgressHeight;

    /**
     * 进度条距离两边的距离
     */
    private int prgressMargin;

    /**
     * 文字距离图片的距离
     */
    private int textTopMargin;

    /**
     * 未选中图片
     */
    private int[] unSelectDrawable = {R.drawable.shoiji_in, R.drawable.cheliang_in, R.drawable.cheliang_in, R.drawable.tijiao_in};

    /**
     * 已选中图片
     */
    private int[] selectDrawable = {R.drawable.shouji_on, R.drawable.cheliang_on, R.drawable.cheliang_on, R.drawable.tijiao_on};

    /**
     * View 的宽高
     */
    private int realWidth, realHeight;

    /**
     * 进度条状态
     * 刚开始状态，全部为灰色
     */
    public static final int STEP_START = -1;

    /**
     * 进度条状态
     * 结束，全部为选中状态
     */
    public static final int STEP_END = -2;

    /**
     * 进度条的进度，快捷的选择，数字不够可以自己加，记住是从0开始的
     */
    public static final int STEP_1 = 0;
    public static final int STEP_2 = 1;
    public static final int STEP_3 = 2;
    public static final int STEP_4 = 3;
    public static final int STEP_5 = 4;
    public static final int STEP_6 = 5;
    public static final int STEP_7 = 6;

    /**
     * 默认状态为开始
     */
    private int progressStatus = STEP_START;

    private Paint paint = new Paint();

    public StatusProgressView(Context context) {
        super(context);
    }

    public StatusProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.StatusProgressView);
//        textArray = (String[]) t.getTextArray(R.styleable.StatusProgressView_spv_progress_text);
        textSize = (int) t.getDimension(R.styleable.StatusProgressView_spv_progress_text, Utils.dp2px(getContext(), 12));
        textSelectColor = t.getColor(R.styleable.StatusProgressView_spv_progress_text_color, 0xFF4BD962);
        textDefaultColor = t.getColor(R.styleable.StatusProgressView_spv_progress_text_default_color, 0xFFD7D7D7);
        prgressDefaultColor = t.getColor(R.styleable.StatusProgressView_spv_progress_default_color, 0xFFD7D7D7);
        prgressColor = t.getColor(R.styleable.StatusProgressView_spv_progress_color, 0xFFD7D7D7);
        prgressHeight = (int) t.getDimension(R.styleable.StatusProgressView_spv_progress_height, Utils.dp2px(getContext(), 1));
        textTopMargin = (int) t.getDimension(R.styleable.StatusProgressView_spv_progress_text_margin_top, Utils.dp2px(getContext(), 11));
        t.recycle();
    }

    private void initPaint() {
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        realHeight = h;
        realWidth = w;
        Log.e("TAG", "realHeight: " + realHeight);
        Log.e("TAG", "realWidth: " + realWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (unSelectDrawable.length == 0 || selectDrawable.length == 0 || textArray.length == 0) {
            throw new IllegalArgumentException("请先设置要显示的文字，图片数组！");
        }
        if (unSelectDrawable.length != selectDrawable.length || selectDrawable.length != textArray.length) {
            throw new IllegalArgumentException("设置显示的文字，图片数组长度要保持一致");
        }
        /**
         * 先获取一下图片的宽高，图片的大小用的都是固定的，不会有变化
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), unSelectDrawable[0]);
        //获取图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //先绘制线
        for (int i = 0; i < unSelectDrawable.length; i++) {
            if (i < unSelectDrawable.length - 1) {
                paint.setStrokeWidth(prgressHeight);
                //开始状态
                if (progressStatus == STEP_START) {
                    paint.setColor(prgressDefaultColor);
                    canvas.drawLine(realWidth / (unSelectDrawable.length + 1) * (i + 1), realHeight / 2 - height / 2,
                            realWidth / (unSelectDrawable.length + 1) * (i + 2), realHeight / 2 - height / 2, paint);
                } else if (progressStatus == STEP_END) {
                    paint.setColor(prgressColor);
                    canvas.drawLine(realWidth / (unSelectDrawable.length + 1) * (i + 1), realHeight / 2 - height / 2,
                            realWidth / (unSelectDrawable.length + 1) * (i + 2), realHeight / 2 - height / 2, paint);
                } else {
                    //从 0 开始为第一步，数值应不能大于自己设置的步数长度
                    if (progressStatus > i) {
                        paint.setColor(prgressColor);
                        canvas.drawLine(realWidth / (unSelectDrawable.length + 1) * (i + 1), realHeight / 2 - height / 2,
                                realWidth / (unSelectDrawable.length + 1) * (i + 2), realHeight / 2 - height / 2, paint);
                    } else {
                        paint.setColor(prgressDefaultColor);
                        canvas.drawLine(realWidth / (unSelectDrawable.length + 1) * (i + 1), realHeight / 2 - height / 2,
                                realWidth / (unSelectDrawable.length + 1) * (i + 2), realHeight / 2 - height / 2, paint);
                    }
                }
            }
            //绘制图片
            if (progressStatus == STEP_START) {
                bitmap = BitmapFactory.decodeResource(getResources(), unSelectDrawable[i]);
                //获取图片的宽高
                //先不考虑边距的问题
                canvas.drawBitmap(bitmap, realWidth / (unSelectDrawable.length + 1) * (i + 1) - width / 2, realHeight / 2 - height, paint);
            } else if (progressStatus == STEP_END) {
                bitmap = BitmapFactory.decodeResource(getResources(), selectDrawable[i]);
                //获取图片的宽高
                //先不考虑边距的问题
                canvas.drawBitmap(bitmap, realWidth / (unSelectDrawable.length + 1) * (i + 1) - width / 2, realHeight / 2 - height, paint);
            } else {
                //从 0 开始为第一步，数值应不能大于自己设置的步数长度
                if (progressStatus >= i) {
                    bitmap = BitmapFactory.decodeResource(getResources(), selectDrawable[i]);
                    //获取图片的宽高
                    //先不考虑边距的问题
                    canvas.drawBitmap(bitmap, realWidth / (unSelectDrawable.length + 1) * (i + 1) - width / 2, realHeight / 2 - height, paint);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(), unSelectDrawable[i]);
                    //获取图片的宽高
                    //先不考虑边距的问题
                    canvas.drawBitmap(bitmap, realWidth / (unSelectDrawable.length + 1) * (i + 1) - width / 2, realHeight / 2 - height, paint);
                }
            }
            //绘制文字
            paint.setTextSize(textSize);
            if (progressStatus == STEP_START) {
                paint.setColor(textDefaultColor);
            } else if (progressStatus == STEP_END) {
                paint.setColor(textSelectColor);
            } else {
                //从 0 开始为第一步，数值应不能大于自己设置的步数长度
                if (progressStatus >= i) {
                    paint.setColor(textSelectColor);
                } else {
                    //等于0 第一步的时候，文字变色
                    paint.setColor(textDefaultColor);
                }
            }
            float textWidth = paint.measureText(textArray[i]);
            canvas.drawText(textArray[i], realWidth / (unSelectDrawable.length + 1) * (i + 1) - textWidth / 2, realHeight / 2 + height / 2 + textTopMargin, paint);
        }
    }


    /**
     * 设置图片资源和文字
     *
     * @param unSelectDrawable 未选中图片
     * @param selectDrawable   选中图片
     */
    public void setProgressDrawableAndText(int[] unSelectDrawable, int[] selectDrawable,String[] textArray) {
        this.unSelectDrawable = unSelectDrawable;
        this.selectDrawable = selectDrawable;
        this.textArray = textArray;
    }

    /**
     * 获取总的步数
     */
    public int getProgressSize() {
        return textArray.length;
    }

    /**
     * 设置进度条的进度
     *
     * @param status STEP_START 开始状态
     *               STEP_END  结束状态
     *               <p>
     *               从0 开始为第一步
     */
    public void setProgress(int status) {
        this.progressStatus = status;
        invalidate();
    }
}
