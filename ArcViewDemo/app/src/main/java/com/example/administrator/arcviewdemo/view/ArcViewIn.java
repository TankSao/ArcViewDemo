package com.example.administrator.arcviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.arcviewdemo.R;

/**
 * Created by Administrator on 2018/11/23.
 */

public class ArcViewIn  extends ImageView {
    /*
     *弧形高度
     */
    private int mArcHeight;
    private static final String TAG = "ArcImageView";

    public ArcViewIn(Context context) {
        this(context, null);
    }

    public ArcViewIn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcViewIn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcImageIn);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcImageIn_arcHeight, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, getHeight());
        path.quadTo(getWidth() / 2, getHeight() - 2 * mArcHeight, getWidth(), getHeight());
        path.lineTo(getWidth(), 0);
        path.close();
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

}
