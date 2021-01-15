package com.cameraApplication;

import android.annotation.SuppressLint;
import android.content.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CurvedTextView extends TextView {

    private Paint paintRoll,paintAzimuth,paintPitchLine,paintPitchText;
    private String mText = null;

    public int[][] coordsTextPitch=new int[9][4];
    public int[] coordsTextRoll=new int[4];
    public int[] coordsTextCompas=new int[4];

    public float[] coordsCenter=new float[2];
    public int angle=0;
    public int roll=0;
    public int[] coordsRollLine;
    public int[] coordsAzimuthLine;
    public int[] coordsPitchLine;
    public float rollValue=0;
    public float azimuthValue=0;
    public float pitchValue=0;

    public CurvedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CurvedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurvedTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){

    }

    public void setText(String text){
        mText = text;
    }

    Paint p;
    Path path;
    Matrix matrix;

    public void changeRollBar(float valueBar,Canvas canvas,float dp,int centerLine){
        float valueBarDrob=Float.parseFloat(String.format("%1$.1f", valueBar-(int)valueBar).replace(",", "."));
        if((int)valueBar%5==0) {
            if(valueBarDrob>-0.6)
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(60+15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(60+15*-valueBarDrob)),paintRoll);
            else
                canvas.drawText((int)(valueBar-5)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]+((int)dp*(int)(80-15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(45+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(45+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(30+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(30+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15+15*-valueBarDrob)),paintRoll);

            canvas.drawText((int)valueBar+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]+((int)dp*(int)(5-15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(15-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(15-15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(30-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(30-15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(45-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(45-15*-valueBarDrob)),paintRoll);

            if(valueBarDrob<0.8)
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(60-15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(60-15*-valueBarDrob)),paintRoll);
            else
                canvas.drawText((int)(valueBar+5)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]-((int)dp*(int)(70+15*-valueBarDrob)),paintRoll);
        }
        else if((int)valueBar%5==4 || (int)valueBar%5==-1) {
            if(valueBarDrob>-0.6)
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(60+15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(60+15*-valueBarDrob)),paintRoll);
            else
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(75-15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(75-15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(45+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(45+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(30+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(30+15*-valueBarDrob)),paintRoll);

            canvas.drawText((int)(valueBar+1)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]-((int)dp*(int)(10+15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(15-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(15-15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(30-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(30-15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(45-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(45-15*-valueBarDrob)),paintRoll);

            if(valueBarDrob<0.5)
                canvas.drawText((int)(valueBar-4)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]+((int)dp*(int)(65-15*-valueBarDrob)),paintRoll);
            else
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(75+15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(75+15*-valueBarDrob)),paintRoll);
        }
        else if((int)valueBar%5==3 || (int)valueBar%5==-2) {
            if(valueBarDrob>-0.6)
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(60+15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(60+15*-valueBarDrob)),paintRoll);
            else
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(75-15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(75-15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(45+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(45+15*-valueBarDrob)),paintRoll);

            canvas.drawText((int)(valueBar+2)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]-((int)dp*(int)(25+15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(15-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(15-15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(30-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(30-15*-valueBarDrob)),paintRoll);

            canvas.drawText((int)(valueBar-3)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]+((int)dp*(int)(50-15*-valueBarDrob)),paintRoll);

            if(valueBarDrob<0.8)
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(60-15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(60-15*-valueBarDrob)),paintRoll);
            else
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(75+15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(75+15*-valueBarDrob)),paintRoll);
        }
        else if((int)valueBar%5==2 || (int)valueBar%5==-3) {
            if(valueBarDrob>-0.6)
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(60+15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(60+15*-valueBarDrob)),paintRoll);
            else
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(75-15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(75-15*-valueBarDrob)),paintRoll);

            canvas.drawText((int)(valueBar+3)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]-((int)dp*(int)(40+15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(30+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(30+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(15-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(15-15*-valueBarDrob)),paintRoll);

            canvas.drawText((int)(valueBar-2)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]+((int)dp*(int)(35-15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(45-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(45-15*-valueBarDrob)),paintRoll);

            if(valueBarDrob<0.8)
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(60-15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(60-15*-valueBarDrob)),paintRoll);
            else
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(75+15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(75+15*-valueBarDrob)),paintRoll);
        }
        else if((int)valueBar%5==1 || (int)valueBar%5==-4) {
            if(valueBarDrob>-0.4)
                canvas.drawText((int)(valueBar+4)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]-((int)dp*(int)(55+15*-valueBarDrob)),paintRoll);
            else
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(75-15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(75-15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(45+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(45+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(30+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(30+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15+15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15+15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(15*-valueBarDrob)),paintRoll);

            canvas.drawText((int)(valueBar-1)+"\u00B0",coordsRollLine[0]+centerLine,coordsRollLine[2]+((int)dp*(int)(20-15*-valueBarDrob)),paintRoll);

            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(30-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(30-15*-valueBarDrob)),paintRoll);
            canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(45-15*-valueBarDrob)),
                            coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(45-15*-valueBarDrob)),paintRoll);

            if(valueBarDrob<0.8)
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]+((int)dp*(int)(60-15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]+((int)dp*(int)(60-15*-valueBarDrob)),paintRoll);
            else
                canvas.drawLine(coordsRollLine[0]+((int)dp*5),coordsRollLine[2]-((int)dp*(int)(75+15*-valueBarDrob)),
                                coordsRollLine[1]-((int)dp*5),coordsRollLine[2]-((int)dp*(int)(75+15*-valueBarDrob)),paintRoll);
        }
    }

    public void drawPitch(Path pathPitchLine,Canvas canvas,float dp,int x1,int y1,int x2,int y2,boolean isUp,float valueBarDrob,int numPos,boolean isText,float valueBar){
        if(isUp) {
            pathPitchLine.moveTo(coordsPitchLine[0] + ((int) dp * (int)(x1+numPos*-valueBarDrob)), coordsPitchLine[2] - ((int) dp * (int)(y1+15*-valueBarDrob)));
            pathPitchLine.lineTo(coordsPitchLine[1] - ((int) dp * (int)(x2+numPos*valueBarDrob)), coordsPitchLine[2] - ((int) dp * (int)(y2+15*-valueBarDrob)));
            pathPitchLine.lineTo(coordsPitchLine[1] - ((int) dp * (int)(x2+numPos*valueBarDrob)), coordsPitchLine[2] - ((int) dp * (int)(y2+15*-valueBarDrob)));
        }
        else{
            pathPitchLine.moveTo(coordsPitchLine[0] + ((int) dp * (int)(x1+numPos*valueBarDrob)), coordsPitchLine[2] + ((int) dp * (int)(y1-15*-valueBarDrob)));
            pathPitchLine.lineTo(coordsPitchLine[1] - ((int) dp * (int)(x2+numPos*-valueBarDrob)), coordsPitchLine[2] + ((int) dp * (int)(y2-15*-valueBarDrob)));
            pathPitchLine.lineTo(coordsPitchLine[1] - ((int) dp * (int)(x2+numPos*-valueBarDrob)), coordsPitchLine[2] + ((int) dp * (int)(y2-15*-valueBarDrob)));
        }
        pathPitchLine.close();
        if(!isText)canvas.drawPath(pathPitchLine,paintPitchLine);
        else canvas.drawTextOnPath((int)valueBar+"\u00B0",pathPitchLine,dp*-12,dp*5,paintPitchText);
        pathPitchLine.reset();
    }

    public void changePitchBar(float valueBar,Canvas canvas,float dp,int centerLine){
        Path pathPitchLine=new Path();
        float valueBarDrob=Float.parseFloat(String.format("%1$.1f", valueBar-(int)valueBar).replace(",", "."));
        if((int)valueBar%5==0) {
            if(valueBarDrob>=0.3f)
                drawPitch(pathPitchLine, canvas, dp,20, 85, -10, 75, true,valueBarDrob,5,true,valueBar+5);

            drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, true,valueBarDrob,5,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, true,valueBarDrob,4,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, true,valueBarDrob,3,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, true,valueBarDrob,2,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp,5, 0, 5, 0, false,valueBarDrob,1,true,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, false,valueBarDrob,2,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, false,valueBarDrob,3,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, false,valueBarDrob,4,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, false,valueBarDrob,5,false,valueBar);
        }
        else if((int)valueBar%5==4 || (int)valueBar%5==-1){
            if(valueBarDrob>=0.3f)
                drawPitch(pathPitchLine, canvas, dp,20, 85, -10, 75, true,valueBarDrob,5,true,valueBar+6);

            drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, true,valueBarDrob,5,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, true,valueBarDrob,4,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, true,valueBarDrob,3,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, true,valueBarDrob,2,true,valueBar+1);

            drawPitch(pathPitchLine, canvas, dp,5, 0, 5, 0, false,valueBarDrob,1,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, false,valueBarDrob,2,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, false,valueBarDrob,3,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, false,valueBarDrob,4,false,valueBar);

            if(valueBarDrob<=0.7f)
                drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, false,valueBarDrob,5,true,valueBar-4);

            if(valueBarDrob<=-0.1f)
                drawPitch(pathPitchLine, canvas, dp, 20, 85, -10, 75, false,valueBarDrob,5,false,valueBar);
        }
        else if((int)valueBar%5==3 || (int)valueBar%5==-2){
            if(valueBarDrob>=0.1f)
                drawPitch(pathPitchLine, canvas, dp,20, 85, -10, 75, true,valueBarDrob,5,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, true,valueBarDrob,5,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, true,valueBarDrob,4,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, true,valueBarDrob,3,true,valueBar+2);

            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, true,valueBarDrob,2,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp,5, 0, 5, 0, false,valueBarDrob,1,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, false,valueBarDrob,2,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, false,valueBarDrob,3,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, false,valueBarDrob,4,true,valueBar-3);

            drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, false,valueBarDrob,5,false,valueBar);

            if(valueBarDrob<=-0.1f)
                drawPitch(pathPitchLine, canvas, dp, 20, 85, -10, 75, false,valueBarDrob,5,false,valueBar);
        }
        else if((int)valueBar%5==2 || (int)valueBar%5==-3){
            if(valueBarDrob>=0.1f)
                drawPitch(pathPitchLine, canvas, dp,20, 85, -10, 75, true,valueBarDrob,5,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, true,valueBarDrob,5,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, true,valueBarDrob,4,true,valueBar+3);

            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, true,valueBarDrob,3,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, true,valueBarDrob,2,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp,5, 0, 5, 0, false,valueBarDrob,1,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, false,valueBarDrob,2,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, false,valueBarDrob,3,true,valueBar-2);

            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, false,valueBarDrob,4,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, false,valueBarDrob,5,false,valueBar);

            if(valueBarDrob<=-0.1f)
                drawPitch(pathPitchLine, canvas, dp, 20, 85, -10, 75, false,valueBarDrob,5,false,valueBar);
        }
        else if((int)valueBar%5==1 || (int)valueBar%5==-4){//here
            if(valueBarDrob>=0.1f)
                drawPitch(pathPitchLine, canvas, dp,20, 85, -10, 75, true,valueBarDrob,5,false,valueBar);

            if(valueBarDrob>=-0.7f)
                drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, true,valueBarDrob,5,true,valueBar+4);

            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, true,valueBarDrob,4,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, true,valueBarDrob,3,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, true,valueBarDrob,2,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp,5, 0, 5, 0, false,valueBarDrob,1,false,valueBar);

            drawPitch(pathPitchLine, canvas, dp, 6, 17, 4, 15, false,valueBarDrob,2,true,valueBar-1);

            drawPitch(pathPitchLine, canvas, dp, 8, 34, 2, 30, false,valueBarDrob,3,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 11, 51, -1, 45, false,valueBarDrob,4,false,valueBar);
            drawPitch(pathPitchLine, canvas, dp, 15, 68, -5, 60, false,valueBarDrob,5,false,valueBar);

            if(valueBarDrob<=-0.1f)
                drawPitch(pathPitchLine, canvas, dp, 20, 85, -10, 75, false,valueBarDrob,5,false,valueBar);
        }
    }

    public void changeAzimuthBar(float valueBar,Canvas canvas,float dp,int centerLine){
        float valueBarDrob=Float.parseFloat(String.format("%1$.1f", valueBar-(int)valueBar).replace(",", "."));
        if((int)valueBar%5==0){
            if(valueBarDrob<0.9f)
                canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                                coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            if(valueBarDrob>=0.7f)
                canvas.drawText((int)(valueBar+5)+"\u00B0",coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawText((int)valueBar+"\u00B0",coordsAzimuthLine[0]+((int)dp*(int)(3+25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

        }
        else if((int)valueBar%5==4){
            if(valueBarDrob<0.4f)
                canvas.drawText((int)(valueBar-4)+"\u00B0",coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);
            if(valueBarDrob>=0.2f)
                canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                                coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+(int)dp*(int)(25*-valueBarDrob),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+(int)dp*(int)(25*-valueBarDrob),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawText((int)(valueBar+1)+"\u00B0",coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
        }
        else if((int)valueBar%5==3){
            if(valueBarDrob<0.9f)
                canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                                coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            if(valueBarDrob>=0.2f)
                canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                                coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawText((int)(valueBar-3)+"\u00B0",coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+(int)dp*(int)(25*-valueBarDrob),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+(int)dp*(int)(25*-valueBarDrob),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawText((int)(valueBar+2)+"\u00B0",coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
        }
        else if((int)valueBar%5==2){
            if(valueBarDrob<0.9f)
                canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                                coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            if(valueBarDrob>=0.2f)
                canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                                coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawText((int)(valueBar-2)+"\u00B0",coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+(int)dp*(int)(25*-valueBarDrob),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+(int)dp*(int)(25*-valueBarDrob),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawText((int)(valueBar+3)+"\u00B0",coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
        }
        else if((int)valueBar%5==1){
            if(valueBarDrob<0.9f)
                canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                                coordsAzimuthLine[0]-((int)dp*(int)(96-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            if(valueBarDrob>=0.2f)
                canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                                coordsAzimuthLine[0]+((int)dp*(int)(120+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(72-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]-((int)dp*(int)(48-25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawText((int)(valueBar-1)+"\u00B0",coordsAzimuthLine[0]-((int)dp*(int)(24-25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);

            canvas.drawLine(coordsAzimuthLine[0]+(int)dp*(int)(25*-valueBarDrob),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+(int)dp*(int)(25*-valueBarDrob),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(24+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(48+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);
            canvas.drawLine(coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[1]+((int)dp*3),
                            coordsAzimuthLine[0]+((int)dp*(int)(72+25*-valueBarDrob)),coordsAzimuthLine[2]-((int)dp*3),paintAzimuth);

            canvas.drawText((int)(valueBar+4)+"\u00B0",coordsAzimuthLine[0]+((int)dp*(int)(96+25*-valueBarDrob)),coordsAzimuthLine[2]-centerLine+((int)dp*5),paintAzimuth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        matrix=new Matrix();

        //Перевод dp в пиксели
        float dp = this.getResources().getDisplayMetrics().density;
        float radiusCircle=(int)dp*120;
        //

        paintRoll=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRoll.setColor(Color.WHITE);
        paintRoll.setStrokeWidth(3);
        paintRoll.setTextSize(dp*12);
        paintRoll.setTextAlign(Paint.Align.CENTER);

        //круг
        Paint circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        float centerX=coordsCenter[0];
        float centerY=coordsCenter[1];
        circlePaint.setARGB(100,255,255,255);
        canvas.drawCircle(centerX,centerY,radiusCircle,circlePaint);
        //

        //Проекция на север
        int length=(int)(Math.cos(Math.toRadians(-roll))*100);
        Matrix matrixNorth=new Matrix();
        matrixNorth.setRotate(-angle,centerX,centerY);

        Paint projectionNorthPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        projectionNorthPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        projectionNorthPaint.setARGB(100,0,0,255);
        projectionNorthPaint.setStrokeWidth(5);

        Path projectionNorth=new Path();
        projectionNorth.moveTo(centerX,centerY);
        projectionNorth.lineTo(centerX,centerY - ((int) dp * length));
        projectionNorth.lineTo(centerX,centerY - ((int) dp * length));
        projectionNorth.addCircle(centerX,centerY - ((int) dp * length), 10, Path.Direction.CW);

        projectionNorth.transform(matrixNorth);
        projectionNorth.close();
        canvas.drawPath(projectionNorth,projectionNorthPaint);
        //

        //Проекция вверх
        length=(int)(Math.sin(Math.toRadians(-roll))*100);

        Paint projectionUpPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        projectionUpPaint.setStyle(Paint.Style.FILL);
        projectionUpPaint.setARGB(100,255,0,0);
        projectionUpPaint.setStrokeWidth(5);
        if(roll>10 || roll<-10) {
            canvas.drawLine(centerX, centerY, centerX, centerY - ((int) dp * length), projectionUpPaint);
            Path triangleArrow = new Path();
            triangleArrow.moveTo(centerX, centerY - ((int) dp * length));
            if(roll<0){
                triangleArrow.lineTo(centerX - ((int) dp * 20), centerY - ((int) dp * (length - 20)));
                triangleArrow.lineTo(centerX + ((int) dp * 20), centerY - ((int) dp * (length - 20)));
            }
            else{
                triangleArrow.lineTo(centerX - ((int) dp * 20), centerY - ((int) dp * (length + 20)));
                triangleArrow.lineTo(centerX + ((int) dp * 20), centerY - ((int) dp * (length + 20)));
            }
            triangleArrow.close();
            canvas.drawPath(triangleArrow, projectionUpPaint);
        }
        //

        //pitch
        p.setColor(Color.GRAY);
        p.setAlpha(100);
        Path path1=new Path();
        path1.addRect(coordsTextPitch[0][0],coordsTextPitch[0][1],coordsTextPitch[0][2],coordsTextPitch[0][3],Path.Direction.CW);

        Path path2=new Path();
        path2.addRect(coordsTextPitch[1][0], coordsTextPitch[1][1], coordsTextPitch[1][2], coordsTextPitch[1][3],Path.Direction.CW);
        int x=coordsTextPitch[1][0]+(coordsTextPitch[1][2]-coordsTextPitch[1][0])/2;
        int y=coordsTextPitch[1][1]+(coordsTextPitch[1][3]-coordsTextPitch[1][1])/2;
        matrix.setRotate(5,x,y);
        path2.transform(matrix);
        path1.addPath(path2);

        path2.reset();
        path2.addRect(coordsTextPitch[2][0], coordsTextPitch[2][1], coordsTextPitch[2][2], coordsTextPitch[2][3],Path.Direction.CW);
        x=coordsTextPitch[2][0]+(coordsTextPitch[2][2]-coordsTextPitch[2][0])/2;
        y=coordsTextPitch[2][1]+(coordsTextPitch[2][3]-coordsTextPitch[2][1])/2;
        matrix.setRotate(10,x,y);
        path2.transform(matrix);
        path1.addPath(path2);

        path2.reset();
        path2.addRect(coordsTextPitch[3][0], coordsTextPitch[3][1], coordsTextPitch[3][2], coordsTextPitch[3][3],Path.Direction.CW);
        x=coordsTextPitch[3][0]+(coordsTextPitch[3][2]-coordsTextPitch[3][0])/2;
        y=coordsTextPitch[3][1]+(coordsTextPitch[3][3]-coordsTextPitch[3][1])/2;
        matrix.setRotate(15,x,y);
        path2.transform(matrix);
        path1.addPath(path2);

        path2.reset();
        path2.addRect(coordsTextPitch[4][0], coordsTextPitch[4][1], coordsTextPitch[4][2], coordsTextPitch[4][3],Path.Direction.CW);
        x=coordsTextPitch[4][0]+(coordsTextPitch[4][2]-coordsTextPitch[4][0])/2;
        y=coordsTextPitch[4][1]+(coordsTextPitch[4][3]-coordsTextPitch[4][1])/2;
        matrix.setRotate(20,x,y);
        path2.transform(matrix);
        path1.addPath(path2);

        path2.reset();
        path2.addRect(coordsTextPitch[5][0], coordsTextPitch[5][1], coordsTextPitch[5][2], coordsTextPitch[5][3],Path.Direction.CW);
        x=coordsTextPitch[5][0]+(coordsTextPitch[5][2]-coordsTextPitch[5][0])/2;
        y=coordsTextPitch[5][1]+(coordsTextPitch[5][3]-coordsTextPitch[5][1])/2;
        matrix.setRotate(-5,x,y);
        path2.transform(matrix);
        path1.addPath(path2);

        path2.reset();
        path2.addRect(coordsTextPitch[6][0], coordsTextPitch[6][1], coordsTextPitch[6][2], coordsTextPitch[6][3],Path.Direction.CW);
        x=coordsTextPitch[6][0]+(coordsTextPitch[6][2]-coordsTextPitch[6][0])/2;
        y=coordsTextPitch[6][1]+(coordsTextPitch[6][3]-coordsTextPitch[6][1])/2;
        matrix.setRotate(-10,x,y);
        path2.transform(matrix);
        path1.addPath(path2);

        path2.reset();
        path2.addRect(coordsTextPitch[7][0], coordsTextPitch[7][1], coordsTextPitch[7][2], coordsTextPitch[7][3],Path.Direction.CW);
        x=coordsTextPitch[7][0]+(coordsTextPitch[7][2]-coordsTextPitch[7][0])/2;
        y=coordsTextPitch[7][1]+(coordsTextPitch[7][3]-coordsTextPitch[7][1])/2;
        matrix.setRotate(-15,x,y);
        path2.transform(matrix);
        path1.addPath(path2);

        path2.reset();
        path2.addRect(coordsTextPitch[8][0], coordsTextPitch[8][1], coordsTextPitch[8][2], coordsTextPitch[8][3],Path.Direction.CW);
        x=coordsTextPitch[8][0]+(coordsTextPitch[8][2]-coordsTextPitch[8][0])/2;
        y=coordsTextPitch[8][1]+(coordsTextPitch[8][3]-coordsTextPitch[8][1])/2;
        matrix.setRotate(-20,x,y);
        path2.transform(matrix);
        path1.addPath(path2);
        canvas.drawPath(path1,p);

        int centerLine=(coordsPitchLine[1]-coordsPitchLine[0])/2;

        paintPitchLine=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPitchLine.setStyle(Paint.Style.STROKE);
        paintPitchLine.setColor(Color.WHITE);
        paintPitchLine.setStrokeWidth(3);
        paintPitchLine.setTextSize(dp*14);
        paintPitchLine.setTextAlign(Paint.Align.CENTER);

        paintPitchText=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPitchText.setStyle(Paint.Style.FILL);
        paintPitchText.setColor(Color.WHITE);
        //paintPitchText.setStrokeWidth(3);
        paintPitchText.setTextSize(dp*12);
        paintPitchText.setTextAlign(Paint.Align.CENTER);

        changePitchBar(pitchValue,canvas,dp,centerLine);

        //compas
        path2.reset();
        path2.addRect(coordsTextCompas[0], coordsTextCompas[1], coordsTextCompas[2], coordsTextCompas[3],Path.Direction.CW);
        canvas.drawPath(path2,p);

        paintAzimuth=new Paint(Paint.ANTI_ALIAS_FLAG);
        paintAzimuth.setColor(Color.WHITE);
        paintAzimuth.setStrokeWidth(3);
        paintAzimuth.setTextSize(dp*12);
        paintAzimuth.setTextAlign(Paint.Align.CENTER);

        centerLine=(coordsAzimuthLine[2]-coordsAzimuthLine[1])/2;
        changeAzimuthBar(azimuthValue,canvas,dp,centerLine);

        //roll
        path2.reset();
        path2.addRect(coordsTextRoll[0], coordsTextRoll[1], coordsTextRoll[2], coordsTextRoll[3],Path.Direction.CW);
        canvas.drawPath(path2,p);

        centerLine=(coordsRollLine[1]-coordsRollLine[0])/2;
        changeRollBar(rollValue,canvas,dp,centerLine);
        //
    }

}
