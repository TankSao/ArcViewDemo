package com.example.administrator.arcviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.arcviewdemo.R;

/**
 * Created by Administrator on 2018/11/23.
 */

public class ArcViewOut  extends View {
    private int mWidth;
    private int mHeight;
    /**
     * 弧形高度
     */
    private int mArcHeight;
    /**
     * 背景颜色
     */
    private int mBgColor;
    private Paint mPaint;
    private Context mContext;

    public ArcViewOut(Context context) {
        this(context, null);
    }

    public ArcViewOut(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcViewOut(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcViewOut);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcViewOut_arcHeight, 0);
        mBgColor=typedArray.getColor(R.styleable.ArcViewOut_bgColor, Color.parseColor("#303F9F"));

        mContext = context;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBgColor);

        Rect rect = new Rect(0, 0, mWidth, mHeight - mArcHeight);
        canvas.drawRect(rect, mPaint);


        Path path = new Path();
        path.moveTo(0, mHeight - mArcHeight);
        path.quadTo(mWidth / 2, mHeight, mWidth, mHeight - mArcHeight);
        canvas.drawPath(path, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}
