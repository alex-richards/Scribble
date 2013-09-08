package com.github.alexrichards.scribble.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
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

    private final Rect invalidateOut = new Rect();

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);

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
        final int w = MeasureSpec.getSize(widthMeasureSpec);
        final int wm = MeasureSpec.getMode(widthMeasureSpec);

        final int h = MeasureSpec.getSize(heightMeasureSpec);
        final int hm = MeasureSpec.getMode(heightMeasureSpec);

        if (buffer != null) {
            final int bw = buffer.getWidth();
            final int bh = buffer.getHeight();

            final int pl = getPaddingLeft();
            final int pt = getPaddingTop();

            final int hp = pl + getPaddingRight();
            final int vp = pt + getPaddingBottom();

            final int iw = w - hp;
            final int ih = h - vp;

            final float sx;
            if (wm != MeasureSpec.UNSPECIFIED && bw > iw) {
                sx = iw / (float) bw;
            } else {
                sx = 1;
            }

            final float sy;
            if (hm != MeasureSpec.UNSPECIFIED && bh > ih) {
                sy = ih / (float) bh;
            } else {
                sy = 1;
            }

            final float s = Math.min(sx, sy);
            final int sbw = (int) (bw * s);
            final int sbh = (int) (bh * s);

            final int mw;
            if (wm == MeasureSpec.EXACTLY) {
                mw = w;
            } else {
                mw = sbw + hp;
            }

            final int mh;
            if (hm == MeasureSpec.EXACTLY) {
                mh = h;
            } else {
                mh = sbh + vp;
            }

            setMeasuredDimension(mw, mh);

            matrix.reset();

            matrix.preScale(s, s);
            matrix.postTranslate(pl + ((iw - sbw) / 2), pt + ((ih - sbh) / 2));

            matrix.invert(inverse);
        } else {
            final int mw;
            if (wm != MeasureSpec.EXACTLY) {
                mw = w;
            } else {
                mw = 0;
            }

            final int mh;
            if (hm != MeasureSpec.EXACTLY) {
                mh = h;
            } else {
                mh = 0;
            }

            setMeasuredDimension(mw, mh);
        }
    }

    public Bitmap getBuffer() {
        return buffer;
    }

    public void setBuffer(final Bitmap buffer) {
        this.buffer = buffer;
        canvas.setBitmap(buffer);

        requestLayout();
    }

    public Brush getBrush() {
        return brush;
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

    @Override
    protected void onDraw(final Canvas canvas) {
        if (buffer != null) {
            canvas.drawBitmap(buffer, matrix, paint);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (buffer != null) {
            final int action = event.getAction();

            final float rx = event.getX();
            final float ry = event.getY();

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

                if (brush != null) {
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

                    RectF invalidate = brush.draw(canvas, path);
                    if(invalidate != null){
                        matrix.mapRect(invalidate);
                        invalidate.roundOut(invalidateOut);
                        invalidate(invalidateOut);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
