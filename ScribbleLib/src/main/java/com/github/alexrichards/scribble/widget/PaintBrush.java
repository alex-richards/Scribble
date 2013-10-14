package com.github.alexrichards.scribble.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

abstract class PaintBrush extends Brush {

    private final Paint paint;

    private final RectF invalidate = new RectF();

    public PaintBrush(final Paint paint) {
        this.paint = paint;
    }

    protected Paint getPaint() {
        return paint;
    }

    @Override
    protected RectF down(Canvas canvas, Path path) {
        return draw(canvas, path);
    }

    @Override
    protected RectF along(Canvas canvas, Path path) {
        return draw(canvas, path);
    }

    @Override
    protected RectF up(Canvas canvas, Path path) {
        return draw(canvas, path);
    }

    private RectF draw(Canvas canvas, Path path) {
        if (!path.isEmpty()) {
            canvas.drawPath(path, paint);

            path.computeBounds(invalidate, true);

            float stroke = paint.getStrokeWidth() / 2;
            invalidate.inset(-stroke, -stroke);

            return invalidate;
        } else {
            return null;
        }
    }
}
