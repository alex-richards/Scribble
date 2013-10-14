package com.github.alexrichards.scribble.widget;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Parcelable;

public abstract class Brush implements Parcelable {

    protected abstract RectF down(Canvas canvas, Path path);

    protected abstract RectF along(Canvas canvas, Path path);

    protected abstract RectF up(Canvas canvas, Path path);

    @Override
    public int describeContents() {
        return 0;
    }
}