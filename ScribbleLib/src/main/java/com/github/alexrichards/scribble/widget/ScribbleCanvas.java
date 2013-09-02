package com.github.alexrichards.scribble.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ScribbleCanvas extends View {

    private static final int POINTS = 3;
    private static final int FIRSTPOINT = 0;
    private static final int LASTPOINT = POINTS / 2;
    private static final int POINT = POINTS - 1;

    private Bitmap buffer;

    private Brush brush;

    private final Canvas canvas = new Canvas();

    private final Matrix matrix = new Matrix();
    private final Matrix inverse = new Matrix();

    private final PointF[] points = new PointF[POINTS];

    private final Path path = new Path();

    private final RectF invalidate = new RectF();
    private final Rect invalidateOut = new Rect();

    public ScribbleCanvas(final Context context) {
        super(context);
    }

    public ScribbleCanvas(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ScribbleCanvas(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int w;
        final int h;

        if (buffer != null) {
            final int bw = buffer.getWidth();
            final int bh = buffer.getHeight();

            final int pl = getPaddingLeft();
            final int pt = getPaddingTop();

            final int hp = pl + getPaddingRight();
            final int vp = pt + getPaddingBottom();

            w = getDefaultSize(bw + hp, widthMeasureSpec);
            h = getDefaultSize(bh + vp, heightMeasureSpec);

            final int iw = w - hp;
            final int ih = h - vp;

            final float ws;
            if (bw > iw) {
                ws = iw / (float) bw;
            } else {
                ws = 1;
            }

            float hs;
            if (bh > ih) {
                hs = ih / (float) bh;
            } else {
                hs = 1;
            }

            if (hs != 1 || ws != 1) {
                final float scale = Math.min(ws, hs);
                matrix.setScale(scale, scale);
            }

            final int ho = (iw - bw) / 2;
            final int vo = (ih - bh) / 2;
            matrix.setTranslate(pl + ho, pt + vo);

            matrix.invert(inverse);
        } else {
            w = MeasureSpec.getSize(widthMeasureSpec);
            h = MeasureSpec.getSize(heightMeasureSpec);
        }

        setMeasuredDimension(w, h);
    }

    public void createBuffer(final int width, final int height, final Bitmap.Config config) {
        setBuffer(new BufferBuilder(width, height, config).build());
    }

    public void setBuffer(final Bitmap buffer) {
        this.buffer = buffer;
        canvas.setBitmap(buffer);

        requestLayout();
    }

    public void setBrush(Brush brush) {
        this.brush = brush;
    }

    int getInternalWidth() {
        return getMeasuredWidth() - (getPaddingLeft() + getPaddingRight());
    }

    int getInternalHeight() {
        return getMeasuredHeight() - (getPaddingTop() + getPaddingBottom());
    }

    public Bitmap getBuffer() {
        return buffer;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (buffer != null) {
            canvas.drawBitmap(buffer, matrix, null);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (buffer != null) {
            final int action = event.getAction();

            final float rx = event.getX();
            final float ry = event.getY();

            final PointF rawPoint = new PointF(rx, ry);

            final float[] rxy = {rx, ry};
            inverse.mapPoints(rxy);

            final float x = rxy[0];
            final float y = rxy[1];

            final PointF point = new PointF(x, y);

            if (action == MotionEvent.ACTION_DOWN) {
                for (int i = 0; i < POINT; ++i) {
                    points[i] = null;
                }
                points[POINT] = point;
            } else if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_UP) {
                for (int i = 0; i < POINT; ++i) {
                    int n = i + 1;
                    points[i] = points[n];
                }
                points[POINT] = point;

                PointF first = points[FIRSTPOINT];
                PointF last = points[LASTPOINT];

                path.reset();

                if (first != null) {
                    path.moveTo((first.x + last.x) / 2, (first.y + last.y) / 2);
                    path.quadTo(last.x, last.y, (last.x + point.x) / 2, (last.y + point.y) / 2);
                } else if (last != null) {
                    path.moveTo(last.x, last.y);
                    path.lineTo((last.x + point.x) / 2, (last.y + point.y) / 2);
                }

                if (brush != null) {
                    matrix.mapRect(invalidate, brush.draw(canvas, path));
                    invalidate.roundOut(invalidateOut);
                    invalidate(invalidateOut);
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
