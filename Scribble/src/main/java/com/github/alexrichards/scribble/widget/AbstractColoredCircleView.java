package com.github.alexrichards.scribble.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

abstract class AbstractColoredCircleView extends SquareView {

    private final Paint paint = new Paint();

    {
        paint.setColor(0xFF000000);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    public AbstractColoredCircleView(Context context) {
        super(context);
    }

    public AbstractColoredCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractColoredCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int pl = getPaddingLeft();
        int w = getWidth() - (pl + getPaddingRight());
        int pt = getPaddingTop();
        int h = getHeight() - (pt + getPaddingBottom());
        canvas.translate(pl, pt);
        float s = Math.max(Math.min(w, h) * getScale(), 1);
        paint.setColor(0xFF000000 | getColor());
        canvas.drawCircle(w / 2, h / 2, s / 2, paint);
    }

    public abstract int getColor();

    public abstract float getScale();
}
