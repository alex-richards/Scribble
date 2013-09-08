package com.github.alexrichards.scribble;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.SeekBar;

import com.github.alexrichards.scribble.widget.BufferBuilder;
import com.github.alexrichards.scribble.widget.PaintBrush;
import com.github.alexrichards.scribble.widget.ScribbleCanvas;

public class TestActivity extends Activity {

    private static final int[] IDS = {R.id.button_black, R.id.button_white, R.id.button_red, R.id.button_green, R.id.button_blue};
    private static final SparseIntArray COLORS = new SparseIntArray();

    static {
        COLORS.put(R.id.button_black, 0xFF000000);
        COLORS.put(R.id.button_white, 0xFFFFFFFF);
        COLORS.put(R.id.button_red, 0xFFFF0000);
        COLORS.put(R.id.button_green, 0xFF00FF00);
        COLORS.put(R.id.button_blue, 0xFF0000FF);
    }

    private final Paint paint = new Paint();
    private final PaintBrush paintBrush = new PaintBrush(paint);

    {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private ScribbleCanvas scribbleCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        scribbleCanvas = (ScribbleCanvas) findViewById(R.id.view_canvas);
        scribbleCanvas.setBuffer(new BufferBuilder(500, 500).background(0xFFFFFFFF).build());
        scribbleCanvas.setBrush(paintBrush);

        for (final int id : IDS) {
            final int color = COLORS.get(id);
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    paint.setColor(color);
                }
            });
        }

        ((SeekBar) findViewById(R.id.view_size)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                paint.setStrokeWidth(i / 10f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
