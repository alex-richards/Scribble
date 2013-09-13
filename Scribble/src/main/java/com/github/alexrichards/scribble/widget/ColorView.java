package com.github.alexrichards.scribble.widget;

import android.content.Context;
import android.util.AttributeSet;

public class ColorView extends AbstractColoredCircleView {

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
    public int getColor() {
        return color;
    }

    @Override
    public float getScale() {
        return 0.8f;
    }
}
