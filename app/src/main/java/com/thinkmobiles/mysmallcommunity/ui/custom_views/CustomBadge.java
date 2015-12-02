package com.thinkmobiles.mysmallcommunity.ui.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.thinkmobiles.mysmallcommunity.R;

/**
 * Created by feltsan on 08.09.15.
 */
public class CustomBadge extends View {
    private String badgeCount;
    private int backgroundColor, countColor, radius, maxWidth;
    private Paint badgePaint;
    private float sizeText;
    private int widthBadge;
    private int heightBadge;
    private String plus="";



    public CustomBadge(Context context) {
        super(context);
    }

    public CustomBadge(Context context, AttributeSet attrs) {
        super(context, attrs);
        badgePaint = new Paint();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomBadge,0,0);

        try {
            badgeCount = typedArray.getString(R.styleable.CustomBadge_badgeCount);
            backgroundColor = typedArray.getInteger(R.styleable.CustomBadge_badgeColor, 0);
            countColor = typedArray.getInteger(R.styleable.CustomBadge_countColor, 0);
            radius = typedArray.getDimensionPixelSize(R.styleable.CustomBadge_radiusRound, 0);
            maxWidth = typedArray.getDimensionPixelSize(R.styleable.CustomBadge_maxWidth,0);
            sizeText = typedArray.getDimensionPixelSize(R.styleable.CustomBadge_countSize, 0);

        }finally {
            typedArray.recycle();
        }
    }

    public CustomBadge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        if(maxWidth > 0 && Integer.valueOf(badgeCount)>99) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, measureMode);
        }
        // Adjust height as necessary

        if(maxWidth > 0 && Integer.valueOf(badgeCount)>99) {
            int measureMode = MeasureSpec.getMode(heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, measureMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    public void setBadgeCount(String _count){
        badgeCount =_count;
    }

    public String getBadgeCount(){
        return badgeCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        widthBadge = this.getMeasuredWidth();
        heightBadge = this.getMeasuredHeight();

        if (maxWidth > 0 && Integer.valueOf(badgeCount)>99) {
            badgeCount ="99";
            widthBadge=heightBadge=maxWidth;
            plus = "+";
        }

        int viewWidthhalf = widthBadge/2;
        int viewHeighthalf = heightBadge/2;



        badgePaint.setStyle(Paint.Style.FILL);
        badgePaint.setAntiAlias(true);
        badgePaint.setColor(backgroundColor);

        Rect rect = new Rect(0,0,widthBadge,heightBadge);

        canvas.drawRoundRect(new RectF(rect), radius,radius, badgePaint);

        badgePaint.setColor(countColor);
        badgePaint.setTextAlign(Paint.Align.CENTER);
        badgePaint.setTextSize(sizeText);
        badgePaint.setAntiAlias(true);

        badgePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        canvas.drawText(badgeCount + plus, viewWidthhalf,viewHeighthalf + sizeText/3 ,badgePaint);
    }
}
