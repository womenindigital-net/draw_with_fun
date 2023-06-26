package com.newname.drawwithfun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DrawingView extends View implements View.OnTouchListener {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFFFF0000, paintAlpha = 255,color= Color.BLACK;
    private static int latestColor;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    //brush sizes
    private float brushSize, lastBrushSize;
    //erase flag
    private boolean erase = false;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private Map<Path, Integer> colorsMap = new HashMap<Path, Integer>();

    private ArrayList<Float> widths = new ArrayList<>();

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }


    //setup drawing
    private void setupDrawing() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);

        //prepare for drawing and setup paint stroke properties
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;
        latestColor = color;
        drawPaint = new Paint();
        drawPaint.setColor(color);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

        drawPath = new Path();
        paths.add(drawPath);
        colorsMap.put(drawPath,latestColor);
        widths.add(lastBrushSize);
        Log.d("Drawing","setupDrawing");
    }
    //size assigned to view
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("onSizeChanged","onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    //draw the view - will be called after touch event

    @Override
    protected void onDraw(Canvas canvas) {


       // Log.i("latestColor", "REACH ON DRAW"+"color "+latestColor);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        Log.d("Tag", String.valueOf(erase));
        int i = 0;
        for (Path p : paths)
        {
            drawPaint.setColor(colorsMap.get(p));
            drawPaint.setStrokeWidth(widths.get(i));
            canvas.drawPath(p, drawPaint);
            i++;
        }
        drawPaint.setColor(latestColor);
        drawPaint.setStrokeWidth(lastBrushSize);
        canvas.drawPath(drawPath, drawPaint);
        //erase = isErase;
        if(erase== true){
            drawPaint.setColor(0xFFFFFFFF);
            canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        }
    }


    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
       // Log.d("Touch","touch_start");
//        drawPaint.setColor(latestColor);
//        drawPaint.setStrokeWidth(lastBrushSize);
        undonePaths.clear();
        drawPath.reset();
        drawPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touch_move(float x, float y) {
       // Log.d("Touch","touch_move");
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            drawPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    private void touch_up() {
        paths.add(drawPath);
        widths.add(lastBrushSize);
        colorsMap.put(drawPath,latestColor);
        drawPath.lineTo(mX, mY);
        // commit the path to our offscreen
        //drawCanvas.drawPath(drawPath, drawPaint);
        // kill this so we don't double draw
        drawPath = new Path();


    }

    //register user touches as drawing action
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

//    public boolean onTouchEvent(MotionEvent event) {
//        float touchX = event.getX();
//        float touchY = event.getY();
//        //respond to down, move and up events
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //drawPath.moveTo(touchX, touchY);
//                touch_start(touchX, touchY);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                // drawPath.lineTo(touchX, touchY);
//                touch_move(touchX, touchY);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
////                drawPath.lineTo(touchX, touchY);
////                drawCanvas.drawPath(drawPath, drawPaint);
//                touch_up();
//                invalidate();
//                //drawPath.reset();
//                break;
//            default:
//                return false;
//        }
//        //redraw
//        invalidate();
//        return true;
//
//    }

    public void onClickUndo () {
        if (paths.size()>0)
        {
            undonePaths.add(paths.remove(paths.size()-1));
            invalidate();
        }
        else
        {   Toast.makeText(getContext(), "nothing more to undo", Toast.LENGTH_SHORT).show();


        }
        //toast the user
    }

    public void onClickRedo (){
        if (undonePaths.size()>0)
        {
            paths.add(undonePaths.remove(undonePaths.size()-1));
            invalidate();
        }
        else
        {

        }
        //toast the user
    }

    //delete drawing
    public void deleteDrawing() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        colorsMap.clear();
        widths.clear();
        paths.clear();
        invalidate();
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    //update color
    public void setColor(String newColor) {
        invalidate();
        //check whether color value or pattern name
        if (newColor.startsWith("#")) {
            Log.d("Color","setColor If");
            paintColor = Color.parseColor(newColor);
            latestColor= paintColor;
            drawPaint.setColor(paintColor);
            drawPaint.setShader(null);
        } else {
            Log.d("Color","setColor else");
            //pattern
            int patternID = getResources().getIdentifier(
                    newColor, "drawable", "com.example.drawingfun");
            //decode
            Bitmap patternBMP = BitmapFactory.decodeResource(getResources(), patternID);
            //create shader
            BitmapShader patternBMPshader = new BitmapShader(patternBMP,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            //color and shader
            drawPaint.setColor(0xFFFFFFFF);
            drawPaint.setShader(patternBMPshader);


        }
    }

    public static int getLastColor() {
        return latestColor;
    }

    //set brush size
    public void setBrushSize(float newSize) {
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize = pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    //get and set last brush size
    public void setLastBrushSize(float lastSize) {
        lastBrushSize = lastSize;
    }

    public float getLastBrushSize() {
        return lastBrushSize;
    }

    //set erase true or false
    public void setErase(boolean isErase) {
        erase = isErase;
        if (erase) {

            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

            Log.d("Erase", "if");
        }
        else {
            drawPaint.setXfermode(null);
            Log.d("Erase", "else");
        }
    }

    //start new drawing
    public void startNew() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        colorsMap.clear();
        widths.clear();
        paths.clear();
        invalidate();
    }

    //return current alpha
    public int getPaintAlpha() {
        return Math.round((float) paintAlpha / 255 * 100);
    }

    //set alpha
    public void setPaintAlpha(int newAlpha) {
        paintAlpha = Math.round((float) newAlpha / 100 * 255);
        drawPaint.setColor(paintColor);
        drawPaint.setAlpha(paintAlpha);
    }
}