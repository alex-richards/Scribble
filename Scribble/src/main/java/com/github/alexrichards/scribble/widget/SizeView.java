package com.github.alexrichards.scribble.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SizeView extends AbstractColoredCircleView {

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
    public int getColor() {
        return 0x000000;
    }

    @Override
    public float getScale() {
        if (maxSize > 0) {
            return size / maxSize;
        } else {
            return 0;
        }
    }
}
