package com.github.alexrichards.scribble.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class PaintBrush extends Brush {

    private final Paint paint;

    private final RectF invalidate = new RectF();

    protected PaintBrush(Paint paint) {
        this.paint = paint;
    }

    @Override
    protected RectF draw(Canvas canvas, Path path) {
        if (!path.isEmpty()) {
            canvas.drawPath(path, paint);
        }

        path.computeBounds(invalidate, true);

        float stroke = paint.getStrokeWidth();
        invalidate.inset(-stroke, -stroke);

        return invalidate;
    }

    public static class Builder {

        private final Paint paint = new Paint();

        public Builder() {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
        }

        public Builder color(int color) {
            paint.setColor(color);
            return this;
        }

        public Builder alpha(int alpha) {
            paint.setAlpha(alpha);
            return this;
        }

        public Builder antialias(boolean antialias) {
            paint.setAntiAlias(antialias);
            return this;
        }

        public Builder size(float size) {
            paint.setStrokeWidth(size);
            return this;
        }

        public PaintBrush build() {
            return new PaintBrush(paint);
        }
    }
}
