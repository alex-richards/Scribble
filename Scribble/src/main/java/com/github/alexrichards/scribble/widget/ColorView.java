package com.github.alexrichards.scribble.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class ColorView extends SquareView {

    private int color;

    public ColorView(Context context) {
        super(context);
    }

    public ColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int pl = getPaddingLeft();
        int w = getWidth() - (pl + getPaddingRight());
        int pt = getPaddingTop();
        int h = getHeight() - (pt + getPaddingBottom());
        int cc = canvas.save();
        canvas.translate(pl, pt);
        canvas.clipRect(0, 0, w, h);
        canvas.drawColor(0xFF000000 | color);
        canvas.restoreToCount(cc);
    }
}
