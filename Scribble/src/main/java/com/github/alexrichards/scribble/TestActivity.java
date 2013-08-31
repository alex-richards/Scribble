package com.github.alexrichards.scribble;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.github.alexrichards.scribble.widget.BufferBuilder;
import com.github.alexrichards.scribble.widget.ScribbleCanvas;

public class TestActivity extends Activity {

    private ScribbleCanvas scribbleCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        scribbleCanvas = (ScribbleCanvas) findViewById(R.id.view_canvas);

        Object nci = getLastNonConfigurationInstance();
        if (nci instanceof Bitmap) {
            scribbleCanvas.setBuffer((Bitmap) nci);
        } else {
            scribbleCanvas.setBuffer(new BufferBuilder(500, 500).background(0xFFFFFFFF).build());
        }
    }

    @Override
    public Object getLastNonConfigurationInstance() {
        return scribbleCanvas.getBuffer();
    }
}
