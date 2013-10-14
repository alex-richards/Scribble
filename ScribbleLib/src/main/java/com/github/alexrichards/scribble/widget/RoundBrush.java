package com.github.alexrichards.scribble.widget;

import android.graphics.Paint;
import android.os.Parcel;

public class RoundBrush extends PaintBrush {

    private static final Paint BASE_PAINT = new Paint();

    static {
        BASE_PAINT.setAntiAlias(true);
        BASE_PAINT.setStyle(Paint.Style.STROKE);
        BASE_PAINT.setStrokeCap(Paint.Cap.ROUND);
    }

    private final Paint paint;

    public RoundBrush(final int color, final float size) {
        super(getPaint(color, size));
        paint = getPaint();
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setColor(final int color) {
        paint.setColor(color);
    }

    public float getSize() {
        return paint.getStrokeWidth();
    }

    public void setSize(final float size) {
        paint.setStrokeWidth(size);
    }

    private static Paint getPaint(final int color, final float size) {
        final Paint paint = new Paint(BASE_PAINT);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        return paint;
    }

    public RoundBrush(final Parcel parcel) {
        this(parcel.readInt(), parcel.readFloat());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        final Paint paint = getPaint();
        dest.writeInt(paint.getColor());
        dest.writeFloat(paint.getStrokeWidth());
    }

    public static final Creator<RoundBrush> CREATOR = new Creator<RoundBrush>() {
        @Override
        public RoundBrush createFromParcel(Parcel source) {
            return new RoundBrush(source);
        }

        @Override
        public RoundBrush[] newArray(int size) {
            return new RoundBrush[size];
        }
    };
}
