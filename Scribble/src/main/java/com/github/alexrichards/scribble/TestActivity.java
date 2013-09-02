package com.github.alexrichards.scribble;

import android.app.Activity;
import android.os.Bundle;

import com.github.alexrichards.scribble.widget.BufferBuilder;
import com.github.alexrichards.scribble.widget.PaintBrush;
import com.github.alexrichards.scribble.widget.ScribbleCanvas;

public class TestActivity extends Activity {

    private ScribbleCanvas scribbleCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        scribbleCanvas = (ScribbleCanvas) findViewById(R.id.view_canvas);

        scribbleCanvas.setBuffer(new BufferBuilder(700, 700).background(0xFFFFFFFF).build());
        scribbleCanvas.setBrush(new PaintBrush.Builder().color(0xFF0000).alpha(0xFF).antialias(true).size(30.0f).build());
    }
}
