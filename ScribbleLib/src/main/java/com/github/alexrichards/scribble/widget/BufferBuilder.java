package com.github.alexrichards.scribble.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BufferBuilder {

    private final Bitmap buffer;

    BufferBuilder(final ScribbleCanvas canvas) {
        this(canvas.getInternalWidth(), canvas.getInternalHeight(), Bitmap.Config.ARGB_8888);
    }

    public BufferBuilder(final int width, final int height) {
        this(width, height, Bitmap.Config.ARGB_8888);
    }

    public BufferBuilder(final int width, final int height, final Bitmap.Config config) {
        buffer = Bitmap.createBitmap(width, height, config);
    }

    public BufferBuilder background(int color) {
        new Canvas(buffer).drawColor(color);
        return this;
    }

    public Bitmap build() {
        return buffer;
    }
}
