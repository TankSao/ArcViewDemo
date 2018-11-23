package com.example.administrator.arcviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.arcviewdemo.R;

/**
 * Created by Administrator on 2018/11/23.
 */

public class SexAngleHeadView extends View {
    private Paint sidePaint,srcPaint;
    private float sideWidth = 10;//边框的宽度
    private int sideColor = Color.WHITE;
    private int radius = 60;//六边形的半径
    private int width;
    private int height;
    private int src_resource;
    private Context context;
    private PorterDuffXfermode mPorterDuffXfermode;
    private int scaleType = 0;

    public SexAngleHeadView(Context context) {
        this(context,null);
        this.context = context;
    }

    public SexAngleHeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        this.context = context;
    }

    public SexAngleHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SexAngleHeadView);
        radius = typedArray.getDimensionPixelSize(R.styleable.SexAngleHeadView_radius, 0);
        //src_resource = typedArray.getDimensionPixelSize(R.styleable.SexAngleHeadView_resourceid, 0);
        String resourceid = typedArray.getString(R.styleable.SexAngleHeadView_resourceid);
        scaleType= typedArray.getInt(R.styleable.SexAngleHeadView_scaleType,0);
        sideWidth= typedArray.getInt(R.styleable.SexAngleHeadView_sideWidth,10);
        src_resource = getResId(context,resourceid);
        Log.e("src_resource",src_resource+"ss");
        initPaint();
    }
    private int getResId(Context context,String res){
        String s[] = res.split("/");
        String type = s[0];
        String name = s[1];
        return  context.getResources().getIdentifier(name,type,context.getPackageName());
    }
    private void initPaint() {
        sidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sidePaint.setStyle(Paint.Style.STROKE);
        sidePaint.setStrokeWidth(sideWidth);
        sidePaint.setColor(sideColor);
        srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();//控件宽度
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas bgCanvas = new Canvas(bgBitmap);

        //Bitmap bgSrc = BitmapFactory.decodeResource(getResources(),src_resource);//本地图片
        Bitmap bgSrc = getBitmap(context,src_resource);
        drawPicture(bgCanvas,srcPaint,scaleType);

        if(bgSrc == null){
            Log.e("sssss","ssssss"+src_resource);
        }
        srcPaint.setXfermode(mPorterDuffXfermode);
        bgCanvas.drawBitmap(bgSrc, null, new RectF(0,0,width,height), srcPaint);
        canvas.drawBitmap(bgBitmap,0,0,null);
        srcPaint.setXfermode(null);

        drawPicture(canvas,sidePaint,scaleType);
    }
    private static Bitmap getBitmap(Context context,int vectorDrawableId) {
        Bitmap bitmap=null;
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }
    //画六边形
    private void drawPicture(Canvas bgCanvas,Paint paint,int scaleType) {
        float left = (float) ((width - Math.sqrt(3) * radius) / 2.0);
        float top = (float) ((height - 2 * radius) / 2.0);
        Path localPath = new Path();
        if(scaleType==1) {//六边形
            localPath.moveTo((float) (left + Math.sqrt(3) * radius / 2.0), top);
            localPath.lineTo(left, top + radius / 2);
            localPath.lineTo(left, top + 1.5f * radius);
            localPath.lineTo((float) (left + Math.sqrt(3) * radius / 2.0f), top + 2 * radius);
            localPath.lineTo((float) (left + Math.sqrt(3) * radius), top + 1.5f * radius);
            localPath.lineTo((float) (left + Math.sqrt(3) * radius), top + radius / 2.0f);
            localPath.lineTo((float) (left + Math.sqrt(3) * radius / 2.0), top);
        }else if(scaleType==2){//圆形
            localPath.addCircle(width/2, height/2, radius, Path.Direction.CW);
        }else{
            localPath.moveTo(0,0);
            localPath.lineTo(0,height);
            localPath.lineTo(width,height);
            localPath.lineTo(height,0);
            localPath.lineTo(0,0);
        }
        localPath.close();
        bgCanvas.drawPath(localPath, paint);
    }
}
