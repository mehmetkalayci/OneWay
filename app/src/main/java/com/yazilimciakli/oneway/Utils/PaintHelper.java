package com.yazilimciakli.oneway.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class PaintHelper {

    Typeface plain;
    float screenRatio;

    public PaintHelper(Context context, float getBallance) {
        plain = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Atma.ttf");
        screenRatio = getBallance;
        if (screenRatio > 2.25) {
            screenRatio = (float) 2.25;
        }
    }

    public Paint circlePaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(screenRatio * 5);
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    public Paint pathLine() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(screenRatio * 5);
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    public Paint pathLittleAlpa() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(screenRatio * 5);
        paint.setColor(Color.argb(10, 255, 255, 255));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    public Paint guideLine() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(screenRatio * 2);
        paint.setColor(Color.argb(10, 255, 255, 255));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    public Paint borderText() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setFakeBoldText(true);
        paint.setTypeface(plain);
        paint.setTextSize(screenRatio * 20);
        return paint;
    }

    public Paint titleText() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setFakeBoldText(true);
        paint.setTypeface(plain);
        paint.setTextSize(screenRatio * 30);
        return paint;
    }

    public Paint borderMiddleText() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setFakeBoldText(true);
        paint.setTypeface(plain);
        paint.setTextSize(screenRatio * 20);
        paint.getTextAlign();
        return paint;
    }
}
