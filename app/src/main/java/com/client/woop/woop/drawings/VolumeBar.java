package com.client.woop.woop.drawings;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class VolumeBar extends View {
    int _textSize = 30;
    int _height = 0;
    String _text = "";

    public VolumeBar(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        Rect rect = new Rect(0, canvasHeight - _height, canvasWidth, canvasHeight);
        canvas.drawRect(rect, paint );

        Paint tempTextPaint = new Paint();
        tempTextPaint.setAntiAlias(true);
        tempTextPaint.setStyle(Paint.Style.FILL);

        tempTextPaint.setColor(Color.BLACK);
        tempTextPaint.setTextSize(_textSize);

        float textWidth = tempTextPaint.measureText(_text);
        canvas.drawText(_text, (canvasWidth / 2) - (textWidth/2), (canvasHeight / 2) + (_textSize/2), tempTextPaint);
    }

    public void setVolume(double volume){
        _text = volume + "";
        _height = (int) (volume * 10);
        this.invalidate();
    }
}