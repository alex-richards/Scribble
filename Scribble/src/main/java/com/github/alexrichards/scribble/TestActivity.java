package com.github.alexrichards.scribble;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.github.alexrichards.scribble.widget.Brush;
import com.github.alexrichards.scribble.widget.BufferBuilder;
import com.github.alexrichards.scribble.widget.PaintBrush;
import com.github.alexrichards.scribble.widget.ScribbleCanvas;

public class TestActivity extends Activity {

    private static final Brush BLACK = new PaintBrush.Builder().color(0x000000).alpha(0xFF).antialias(true).size(3).build();
    private static final Brush WHITE = new PaintBrush.Builder().color(0xFFFFFF).alpha(0xFF).antialias(true).size(3).build();
    private static final Brush RED = new PaintBrush.Builder().color(0xFF0000).alpha(0xFF).antialias(true).size(3).build();
    private static final Brush GREEN = new PaintBrush.Builder().color(0x00FF00).alpha(0xFF).antialias(true).size(3).build();
    private static final Brush BLUE = new PaintBrush.Builder().color(0x0000FF).alpha(0xFF).antialias(true).size(3).build();

    private static final int[] IDS = {R.id.button_black, R.id.button_white, R.id.button_red, R.id.button_green, R.id.button_blue};
    private static final Brush[] BRUSHES = {BLACK, WHITE, RED, GREEN, BLUE};

    private ScribbleCanvas scribbleCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        scribbleCanvas = (ScribbleCanvas) findViewById(R.id.view_canvas);

        scribbleCanvas.setBuffer(new BufferBuilder(1000, 1000).background(0xFFFFFFFF).build());
        scribbleCanvas.setBrush(BLACK);

        for (int i = 0; i < IDS.length; ++i) {
            final Brush brush = BRUSHES[i];
            findViewById(IDS[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scribbleCanvas.setBrush(brush);
                }
            });
        }
    }
}
