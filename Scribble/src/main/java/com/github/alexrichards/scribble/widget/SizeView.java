package com.github.alexrichards.scribble.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class SizeView extends SquareView {

    private final Paint paint = new Paint();

    {
        paint.setColor(0xFF000000);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    private float maxSize;
    private float size;

    public SizeView(Context context) {
        super(context);
    }

    public SizeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SizeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setSize(float maxSize, float size) {
        this.maxSize = maxSize;
        this.size = size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (size > 0 && maxSize > 0) {
            int pl = getPaddingLeft();
            int w = getWidth() - (pl + getPaddingRight());
            int pt = getPaddingTop();
            int h = getHeight() - (pt + getPaddingBottom());
            canvas.translate(pl, pt);
            float s = Math.min(w, h) * (size / maxSize);
            canvas.drawCircle(w / 2, h / 2, s / 2, paint);
        }
    }
}
