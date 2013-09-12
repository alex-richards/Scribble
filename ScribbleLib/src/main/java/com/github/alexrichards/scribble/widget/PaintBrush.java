package com.github.alexrichards.scribble.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class PaintBrush extends Brush {

    private final Paint paint;

    private final RectF invalidate = new RectF();

    public PaintBrush(Paint paint) {
        this.paint = new Paint(paint);
    }

    public Paint getPaint() {
        return new Paint(paint);
    }

    @Override
    protected RectF draw(Canvas canvas, Path path) {
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
