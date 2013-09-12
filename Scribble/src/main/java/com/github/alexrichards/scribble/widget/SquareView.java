package com.github.alexrichards.scribble.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SquareView extends View {

    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int ws = MeasureSpec.getSize(widthMeasureSpec);
        final int hs = MeasureSpec.getSize(heightMeasureSpec);
        final int s = Math.max(ws, hs);
        setMeasuredDimension(s, s);
    }
}
