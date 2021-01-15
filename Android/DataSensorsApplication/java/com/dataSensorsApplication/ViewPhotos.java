package com.cameraApplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ViewPhotos extends AppCompatActivity {

    private TableLayout table;
    private ScrollView scrollView;
    private HorizontalScrollView hView;
    ViewPhotos active;

    public static String getUTCdatetimeAsString() {
        String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(new Date());
        String[] utcArr=utcTime.split(" ");
        return utcArr[0]+"T"+utcArr[1]+"Z";
    }

    public void createPhoto(ArrayList<String> listFiles,double zoom){
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Data sensors");
        for(String nameFile:listFiles){

            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();

            ImageView mImageView=new ImageView(this);

            File photoFile = new File(directory.getPath() + "/" + nameFile + ".jpg");
            File jsonFile = new File(directory.getPath() + "/" + nameFile + ".txt");

            //Чтение данных с json файла
            StringBuilder textJson=new StringBuilder();
            try{
                String s="";
                BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
                while((s=reader.readLine())!=null)
                    textJson.append(s+"\n");
            }
            catch(Exception ex){}

            File jsonFile1=new File(directory.getPath() + "/" + nameFile + ".json");
            try{
                BufferedWriter writer=new BufferedWriter(new FileWriter(jsonFile1));
                String[] textArr=textJson.toString().split("\n");
                for(String s:textArr){
                    writer.write(s);
                    writer.newLine();
                }
                writer.close();
            }
            catch(Exception ex){}

            textJson=new StringBuilder();
            StringBuilder textJsonAzimuth=new StringBuilder();
            String textPitchJson="",textRollJson="";

            try {
                Object obj = new JSONParser().parse(new FileReader(jsonFile1.getAbsolutePath()));
                JSONObject jo=(JSONObject)obj;

                textJson.append("UTC: "+getUTCdatetimeAsString()+"\n");

                JSONObject gps=(JSONObject)jo.get("GPS");
                if(gps!=null){
                    textJson.append("Lat,Lon: "+gps.get("Lat")+", "+gps.get("Lon")+"\n");
                    textJson.append("Alt: "+gps.get("Elev")+"\n");
                    float cep=Float.parseFloat(""+gps.get("HDOP"))*5;
                    textJson.append("CEP: "+cep+"\n");

                    JSONObject utm=(JSONObject)jo.get("UTM");
                    String utmX=String.format("%1.1f",Double.parseDouble(""+utm.get("X"))).replace(",",".");
                    String utmY=String.format("%1.1f",Double.parseDouble(""+utm.get("Y"))).replace(",",".");
                    String utmZ=String.format("%1.1f",Double.parseDouble(""+utm.get("Z"))).replace(",",".");
                    textJson.append("UTM: "+utmX+", "+utmY+", "+utmZ+"\n");

                    Intent intent=getIntent();
                    String nameLocalSystem=intent.getStringExtra("nameLocalSystem");
                    JSONObject msk=(JSONObject)jo.get("MSK");
                    String mskX=String.format("%1.1f",Double.parseDouble(""+msk.get("X"))).replace(",",".");
                    String mskY=String.format("%1.1f",Double.parseDouble(""+msk.get("Y"))).replace(",",".");
                    String mskZ=String.format("%1.1f",Double.parseDouble(""+msk.get("Z"))).replace(",",".");
                    if(!nameLocalSystem.isEmpty())textJson.append("MSK: "+nameLocalSystem+", "+mskX+", "+mskY+", "+mskZ);
                    else textJson.append("MSK: "+mskX+", "+mskY+", "+mskZ);
                }

                JSONObject orientation=(JSONObject)jo.get("Orient");
                if(orientation!=null){
                    textJsonAzimuth.append("Azimuth and Bearing:\n");
                    float bearing=Float.parseFloat(""+orientation.get("A"));
                    if(bearing<0)
                        bearing=180+(Math.abs(bearing));
                    int bearingI=(int)bearing;
                    String bearingText="";
                    if((bearingI>=0 && bearingI<90) || bearingI==360)
                        bearingText="N"+bearingI+"E";
                    else if(bearingI>=90 && bearingI<180)
                        bearingText="S"+(bearingI-90)+"E";
                    else if(bearingI>=180 && bearingI<270)
                        bearingText="S"+(bearingI-180)+"W";
                    else if(bearingI>=270 && bearingI<360)
                        bearingText="N"+(bearingI-270)+"W";
                    textJsonAzimuth.append("      "+bearingI+"\u00B0        "+bearingText);

                    textPitchJson=""+(int)Float.parseFloat(""+orientation.get("P"));
                    textRollJson=""+(int)Float.parseFloat(""+orientation.get("R"));
                }

                jsonFile1.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //

            Bitmap bitmap=BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            bitmap=Bitmap.createScaledBitmap(bitmap,(int)(width*zoom),(int)(height*zoom),true);
            mImageView.setImageBitmap(bitmap);

            //масштаб 2 пальцами
            mImageView.setOnTouchListener(listener);
            //

            TextView textSensors=new TextView(this);
            textSensors.setText(textJson);
            textSensors.setTextColor(Color.WHITE);
            textSensors.setTextSize(12);

            TextView textAzimuth=new TextView(this);
            textAzimuth.setText(textJsonAzimuth);
            textAzimuth.setTextColor(Color.WHITE);
            textAzimuth.setTextSize(12);

            TextView textPitch=new TextView(this);
            textPitch.setText(textPitchJson);
            textPitch.setTextColor(Color.WHITE);
            textPitch.setTextSize(12);

            TextView textRoll=new TextView(this);
            textRoll.setText(textRollJson);
            textRoll.setTextColor(Color.WHITE);
            textRoll.setTextSize(12);

            //
            FrameLayout layout=new FrameLayout(active);
            layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            layout.addView(mImageView);
            layout.addView(textSensors);

            textAzimuth.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER));
            FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)textAzimuth.getLayoutParams();
            params.bottomMargin=150;
            textAzimuth.setLayoutParams(params);
            layout.addView(textAzimuth);

            ImageView horizontalLine=new ImageView(active);
            horizontalLine.setBackgroundColor(Color.WHITE);
            horizontalLine.setLayoutParams(new FrameLayout.LayoutParams(200, 1,Gravity.CENTER));
            layout.addView(horizontalLine);

            ImageView verticalLine=new ImageView(active);
            verticalLine.setBackgroundColor(Color.WHITE);
            verticalLine.setLayoutParams(new FrameLayout.LayoutParams(1, 200,Gravity.CENTER));
            layout.addView(verticalLine);

            table.addView(layout);
            //
        }
    }

    //Касание два пальца
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    View.OnTouchListener listener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            float scaleReal=1;
            ImageView view = (ImageView) v;
            view.setScaleType(ImageView.ScaleType.MATRIX);
            float scale;

            //dumpEvent(event);
            // Handle touch events here...

            switch (event.getAction() & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN:   // first finger down only
                    //
                    scrollView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    hView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    //

                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    mode = DRAG;
                    break;

                case MotionEvent.ACTION_UP: // first finger lifted

                case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                    //
                    scrollView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    hView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    //

                    mode = NONE;
                    break;

                case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
                    //
                    scrollView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    hView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    //

                    oldDist = spacing(event);
                    if (oldDist > 5f) {
                        savedMatrix.set(matrix);
                        midPoint(mid, event);
                        mode = ZOOM;
                    }
                    break;

                case MotionEvent.ACTION_MOVE:

                    if (mode == DRAG)
                    {
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                    }
                    else if (mode == ZOOM)
                    {
                        //
                        scrollView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        hView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        //

                        // pinch zooming
                        float newDist = spacing(event);
                        if (newDist > 5f)
                        {
                            matrix.set(savedMatrix);
                            scale = newDist / oldDist; // setting the scaling of the
                            // matrix...if scale > 1 means
                            // zoom in...if scale < 1 means
                            // zoom out
                            if(scale<1){
                                scaleReal=0.75f;
                                matrix.postScale(scaleReal, scaleReal, mid.x, mid.y);
                            }
                            else{
                                scaleReal=1.5f;
                                matrix.postScale(scaleReal, scaleReal, mid.x, mid.y);
                            }
                        }
                    }
                    break;
            }

            //
            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            if((int)(view.getWidth()*scaleReal)>=width && (int)(view.getHeight()*scaleReal)>=height) {
                view.setImageMatrix(matrix); // display the transformation on screen

                view.setLayoutParams(new FrameLayout.LayoutParams((int) (view.getWidth() * scaleReal), (int) (view.getHeight() * scaleReal)));
                view.setScaleType(ImageView.ScaleType.FIT_START);
            }
            else {
                view.setImageMatrix(matrix); // display the transformation on screen
                view.setLayoutParams(new FrameLayout.LayoutParams(width,height));
                view.setScaleType(ImageView.ScaleType.FIT_START);
            }
            //

            return true; // indicate event was handled
        }

        /*
         * --------------------------------------------------------------------------
         * Method: spacing Parameters: MotionEvent Returns: float Description:
         * checks the spacing between the two fingers on touch
         * ----------------------------------------------------
         */

        private float spacing(MotionEvent event)
        {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float)Math.sqrt(x*x+y*y);
        }

        /*
         * --------------------------------------------------------------------------
         * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
         * Description: calculates the midpoint between the two fingers
         * ------------------------------------------------------------
         */

        private void midPoint(PointF point, MotionEvent event)
        {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
        }
    };
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_photos);

        table=findViewById(R.id.tableLayout);
        scrollView=findViewById(R.id.scroll);
        hView=findViewById(R.id.hscroll);
        active=this;

        Intent intent=getIntent();
        final ArrayList<String> listFiles=intent.getStringArrayListExtra("listFiles");
        //Сортировка
        String buf="";
        boolean isSorted=false;
        while(!isSorted) {
            isSorted=true;
            for (int i = 0; i < listFiles.size() - 1; i++) {
                String date = listFiles.get(i);
                int year1 = Integer.parseInt(listFiles.get(i).substring(3, 7));
                int year2 = Integer.parseInt(listFiles.get(i + 1).substring(3, 7));
                int month1 = Integer.parseInt(listFiles.get(i).substring(7, 9));
                int month2 = Integer.parseInt(listFiles.get(i + 1).substring(7, 9));
                int day1 = Integer.parseInt(listFiles.get(i).substring(9, 11));
                int day2 = Integer.parseInt(listFiles.get(i + 1).substring(9, 11));
                int hour1 = Integer.parseInt(listFiles.get(i).substring(12, 14));
                int hour2 = Integer.parseInt(listFiles.get(i + 1).substring(12, 14));
                int minute1 = Integer.parseInt(listFiles.get(i).substring(14, 16));
                int minute2 = Integer.parseInt(listFiles.get(i + 1).substring(14, 16));
                int second1 = Integer.parseInt(listFiles.get(i).substring(16, 18));
                int second2 = Integer.parseInt(listFiles.get(i + 1).substring(16, 18));
                GregorianCalendar cal1 = new GregorianCalendar();
                cal1.set(year1, month1, day1, hour1, minute1, second1);
                GregorianCalendar cal2 = new GregorianCalendar();
                cal2.set(year2, month2, day2, hour2, minute2, second2);
                if (cal2.before(cal1)) {
                    isSorted=false;
                    buf = listFiles.get(i);
                    listFiles.set(i, listFiles.get(i + 1));
                    listFiles.set(i + 1, buf);
                }
            }
        }
        //
        createPhoto(listFiles,1.0);

        Button exit=new Button(this);
        exit.setText("Выход");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active.finish();
            }
        });

        TableRow rowBtn=new TableRow(this);
        rowBtn.setGravity(Gravity.CENTER);
        rowBtn.addView(exit);
    }
}
