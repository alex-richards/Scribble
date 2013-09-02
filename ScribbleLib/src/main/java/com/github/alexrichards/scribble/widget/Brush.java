package com.github.alexrichards.scribble.widget;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

public abstract class Brush {

    protected abstract RectF draw(Canvas canvas, Path path);

}