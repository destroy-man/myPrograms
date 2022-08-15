import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import org.json.simple.JSONObject;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 1;
    private static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 2;
    private static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 3;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 4;
    private static final int REQUEST_CODE_PERMISSION_FINE_LOCATION = 5;
    private static final int REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 6;

    private int countCameraPermission = 0;
    private int countLocationPermission = 0;
    private int countFineLocationCount = 0;

    File directory;
    File photoFile;
    File jsonFile;

    public MainActivity active;
    public int result;

    TextView text1;
    SurfaceView sv;
    SurfaceHolder holder;
    HolderCallback holderCallback;
    static Camera camera;

    final int CAMERA_ID = 0;

    boolean[] hasSensors;
    SensorManager sensorManager = null;
    SensorEventListener sensorListener;
    float[] valuesAccelerometer = {0, 0, 0};
    String[] valuesGps = {"0", "0", "0", "0", "0"};
    float[] valuesMagneticField = {0, 0, 0};
    float[] valueTemperature = {0};
    float[] valuePressure = {0};
    float[] valueLight = {0};
    float cep=0;
    float bearing=0;
    private LocationManager locationManager;

    String nameSession = "";
    String namePoint = "";
    String nameDevice = "";
    int numberPoint = 0;
    int numberPicture = 0;
    int widthPicture=0;
    int heightPicture=0;
    long guid=0;
    ArrayList<String> listFiles=new ArrayList<String>();
    Map dataSensors=new LinkedHashMap();

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_GUID = "GUID";
    public static final String APP_PREFERENCES_NAME_DEVICE = "NAME_DEVICE";
    public static final String APP_PREFERENCES_MSK = "MSK";
    public static final String APP_PREFERENCES_DALNOMER = "DALNOMER";
    SharedPreferences mSettings;

    int numberSession=0;
    int numberObject=0;
    int numberTrack=0;
    boolean isDalnomer=false;
    String distanceToObject="";
    String nameLocalSystem="";
    String nameTrack="";
    double dxLocalSystem;
    double dyLocalSystem;
    double dzLocalSystem;
    double angleLocalSystem;
    Map<String,String> dataMap;
    int stepTrack=5;
    Timer timerTrack;
    Timer infoTimer;
    int numberPointTrack;
    int timeTrack;
    double distanceTrack;
    String mskSetting;

    double oldXSession=0;
    double oldYSession=0;
    double newXSession=0;
    double newYSession=0;

    boolean hasTrackFile;
    int numberFileSession=0;
    String fileSession="";
    ArrayList<String> nameTracks=new ArrayList<String>();
    ArrayList<String> nameSessions=new ArrayList<String>();
    int countDataSensors=10;

    float[][] arrAcc;
    float[][] arrMag;
    float[] arrTemperature;
    float[] arrPres;
    float[] arrLight;
    double[][] arrGps;
    double[][] arrUtm;
    double[][] arrMsk;
    float[][] arrOrient;
    int currentAcc=0, currentMag=0, currentTemperature=0, currentPres=0, currentLight=0, currentGps=0,
    currentUtm=0, currentMsk=0, currentOrient=0;

    CurvedTextView curveTextView;
    int[][] coordsTextPitch=new int[9][4];
    int[] coordsTextRoll=new int[4];
    int[] coordsTextCompas=new int[4];
    boolean needGps=false;

    int zeroPitch=0;

    float[] avgGps={0,0,0};
    float[] sumOtklonenieGps={0,0,0};
    float[] avgOtklonenieGps={0,0,0};
    float[] avgUtm={0,0};
    float[] sumOtklonenieUtm={0,0};
    float[] avgOtklonenieUtm={0,0};
    float[] avgMsk={0,0};

    double[] oldGps={0,0};
    double[] newGps={0,0};
    //

    //
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            if (ActivityCompat.checkSelfPermission(active, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(active, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };


    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            String strLocation = formatLocation(location);
            String[] strLocationArr = strLocation.split(", ");
            valuesGps[0] = strLocationArr[0];
            valuesGps[1] = strLocationArr[1];
            valuesGps[2] = strLocationArr[2];
            valuesGps[3] = strLocationArr[3];
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        cep=location.getAccuracy();
        return String.format("%1$.4f, %2$.4f, %3$.4f, %4$.4f",
                             location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy() / 5);
    }

    private void checkEnabled() {

    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
    //

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    System.exit(0);
                }
                break;
            }
            case REQUEST_CODE_PERMISSION_WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    System.exit(0);
                }
                break;
            case REQUEST_CODE_PERMISSION_READ_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    System.exit(0);
                }
                break;
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    System.exit(0);
                }
                break;
            case REQUEST_CODE_PERMISSION_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    System.exit(0);
                }
                break;
        }
        return;
    }

    private void askCameraPermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = this.checkSelfPermission(Manifest.permission.CAMERA);
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
            }
        }
    }

    private void askWriteStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int storagePermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_WRITE_STORAGE);
            }
        }
    }

    private void askReadStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int storagePermission = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_STORAGE);
            }
        }
    }

    private void askLocatePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int storagePermission = this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSION_LOCATION);
            }
        }
    }

    private void askFineLocatePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int storagePermission = this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION_FINE_LOCATION);
            }
        }
    }

    private void askReadPhoneStatePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_PERMISSION_READ_PHONE_STATE);
            }
        }
    }

    //
    public Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        mtx.setRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
    //

    //Получение данных для записи в файл
    public void getTextToFile(StringBuilder text, String day, String month, String year, String hour
            , String minute, String second, boolean isPoint) {
        //
        StringBuilder avgDataSensors=new StringBuilder();
        //

        JSONObject root=new JSONObject();
        Map jsonObject=new LinkedHashMap<>();

        if(!nameSession.isEmpty())jsonObject.put("S",nameSession);

        jsonObject.put("N",""+numberPicture);

        jsonObject.put("Date",day+"."+month+"."+year);
        jsonObject.put("Time",hour+":"+minute+":"+second);

        Map dev=new LinkedHashMap();
        dev.put("Name",nameDevice);
        dev.put("GUID",""+guid);
        dev.put("Brand",Build.BRAND);
        dev.put("Device",Build.DEVICE);
        dev.put("ID",Build.ID);
        jsonObject.put("Dev",dev);

        String nameCamera = "";
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(CAMERA_ID, info);
        if (info.facing == CameraInfo.CAMERA_FACING_BACK) nameCamera = "Back_camera";
        else nameCamera = "Front_camera";
        Map cameraJson=new LinkedHashMap();
        cameraJson.put("Name",nameCamera);
        cameraJson.put("GUID",""+guid);
        cameraJson.put("Version",BuildConfig.VERSION_NAME);
        jsonObject.put("Cam",cameraJson);

        int width=widthPicture;
        int height=heightPicture;
        if(width==0 && height==0){
            String[] sizes=getSizesPhoto();
            height=Integer.parseInt(sizes[0].split("x")[0]);
            width=Integer.parseInt(sizes[0].split("x")[1]);
        }
        Map picture=new LinkedHashMap();
        picture.put("W",""+width);
        picture.put("H",""+height);
        if(isDalnomer)picture.put("distance_to_object",distanceToObject);
        jsonObject.put("Img",picture);

        //
        if(isPoint) {
            Map point=new LinkedHashMap();
            point.put("Name_point",namePoint);
            point.put("Number_point",""+numberPoint);
            jsonObject.put("Point",point);
        }
        //

        if(camera==null)camera=Camera.open(CAMERA_ID);
        Map angleView=new LinkedHashMap();
        angleView.put("H",""+camera.getParameters().getHorizontalViewAngle());
        angleView.put("V",""+camera.getParameters().getVerticalViewAngle());
        jsonObject.put("VA",angleView);

        if(hasSensors[0]){
            avgDataSensors.append("Acc:\n");
            StringBuilder x=new StringBuilder();
            StringBuilder y=new StringBuilder();
            StringBuilder z=new StringBuilder();
            float[] sumAcc={0,0,0};
            for(int i=0;i<countDataSensors;i++){
                sumAcc[0]+=arrAcc[i][0];
                sumAcc[1]+=arrAcc[i][1];
                sumAcc[2]+=arrAcc[i][2];
                //
                if(i==0){
                    x.append(arrAcc[i][0]);
                    y.append(arrAcc[i][1]);
                    z.append(arrAcc[i][2]);
                }
                else{
                    x.append(", "+arrAcc[i][0]);
                    y.append(", "+arrAcc[i][1]);
                    z.append(", "+arrAcc[i][2]);
                }
                //
            }
            //
            avgDataSensors.append("\tX: "+x+"\n\tY: "+y+"\n\tZ: "+z+"\n");
            //
            Map accsel=new LinkedHashMap();
            accsel.put("X",String.format("%1$.4f",sumAcc[0]/countDataSensors).replace(",","."));
            accsel.put("Y",String.format("%1$.4f",sumAcc[1]/countDataSensors).replace(",","."));
            accsel.put("Z",String.format("%1$.4f",sumAcc[2]/countDataSensors).replace(",","."));
            jsonObject.put("Acc",accsel);
        }

        if(dataSensors.get("GPS")!=null){
            avgDataSensors.append("GPS:\n");
            StringBuilder lat=new StringBuilder();
            StringBuilder lon=new StringBuilder();
            StringBuilder elev=new StringBuilder();
            StringBuilder hdop=new StringBuilder();
            StringBuilder vdop=new StringBuilder();
            double[] sumGps={0,0,0,0,0};
            for(int i=0;i<countDataSensors;i++){
                sumGps[0]+=arrGps[i][0];
                sumGps[1]+=arrGps[i][1];
                sumGps[2]+=arrGps[i][2];
                sumGps[3]+=arrGps[i][3];
                sumGps[4]+=arrGps[i][4];
                //
                if(i==0){
                    lat.append(arrGps[i][0]);
                    lon.append(arrGps[i][1]);
                    elev.append(arrGps[i][2]);
                    hdop.append(arrGps[i][3]);
                    vdop.append(arrGps[i][4]);
                }
                else{
                    lat.append(", "+arrGps[i][0]);
                    lon.append(", "+arrGps[i][1]);
                    elev.append(", "+arrGps[i][2]);
                    hdop.append(", "+arrGps[i][3]);
                    vdop.append(", "+arrGps[i][4]);
                }
                //
            }
            avgDataSensors.append("\tlat: "+lat+"\n\tlon: "+lon+"\n\telev: "+elev+"\n\thdop: "+hdop+"\n\tvdop: "+vdop+"\n");

            Map gps=new LinkedHashMap();
            gps.put("Lat",String.format("%1$.4f",sumGps[0]/countDataSensors).replace(",","."));
            gps.put("Lon",String.format("%1$.4f",sumGps[1]/countDataSensors).replace(",","."));
            gps.put("Elev",String.format("%1$.4f",sumGps[2]/countDataSensors).replace(",","."));
            gps.put("HDOP",String.format("%1$.4f",sumGps[3]/countDataSensors).replace(",","."));
            gps.put("VDOP",String.format("%1$.4f",sumGps[4]/countDataSensors).replace(",","."));
            jsonObject.put("GPS",gps);

            avgDataSensors.append("UTM:\n");
            StringBuilder x=new StringBuilder();
            StringBuilder y=new StringBuilder();
            StringBuilder z=new StringBuilder();
            double[] sumUtm={0,0,0};
            for(int i=0;i<countDataSensors;i++){
                sumUtm[0]+=arrUtm[i][0];
                sumUtm[1]+=arrUtm[i][1];
                sumUtm[2]+=arrUtm[i][2];
                //
                if(i==0){
                    x.append(arrUtm[i][0]);
                    y.append(arrUtm[i][1]);
                    z.append(arrUtm[i][2]);
                }
                else{
                    x.append(", "+arrUtm[i][0]);
                    y.append(", "+arrUtm[i][1]);
                    z.append(", "+arrUtm[i][2]);
                }
                //
            }
            avgDataSensors.append("\tX: "+x+"\n\tY: "+y+"\n\tZ: "+z+"\n");

            Map utm=new LinkedHashMap();
            utm.put("X",String.format("%1$.4f",sumUtm[0]/countDataSensors).replace(",","."));
            utm.put("Y",String.format("%1$.4f",sumUtm[1]/countDataSensors).replace(",","."));
            utm.put("Z",String.format("%1$.4f",sumUtm[2]/countDataSensors).replace(",","."));
            jsonObject.put("UTM",utm);

            avgDataSensors.append("MSK:\n");
            x=new StringBuilder();
            y=new StringBuilder();
            z=new StringBuilder();
            double[] sumMsk={0,0,0};
            for(int i=0;i<countDataSensors;i++){
                sumMsk[0]+=arrMsk[i][0];
                sumMsk[1]+=arrMsk[i][1];
                sumMsk[2]+=arrMsk[i][2];
                //
                if(i==0){
                    x.append(arrMsk[i][0]);
                    y.append(arrMsk[i][1]);
                    z.append(arrMsk[i][2]);
                }
                else{
                    x.append(", "+arrMsk[i][0]);
                    y.append(", "+arrMsk[i][1]);
                    z.append(", "+arrMsk[i][2]);
                }
                //
            }
            avgDataSensors.append("\tX: "+x+"\n\tY: "+y+"\n\tZ: "+z+"\n");

            Map msk=new LinkedHashMap();
            msk.put("X",String.format("%1$.4f",sumMsk[0]/countDataSensors).replace(",","."));
            msk.put("Y",String.format("%1$.4f",sumMsk[1]/countDataSensors).replace(",","."));
            msk.put("Z",String.format("%1$.4f",sumMsk[2]/countDataSensors).replace(",","."));
            jsonObject.put("MSK",msk);
        }

        if(hasSensors[1]) {
            avgDataSensors.append("Mag:\n");
            StringBuilder x=new StringBuilder();
            StringBuilder y=new StringBuilder();
            StringBuilder z=new StringBuilder();
            float[] sumMag={0,0,0};
            for(int i=0;i<countDataSensors;i++){
                sumMag[0]+=arrMag[i][0];
                sumMag[1]+=arrMag[i][1];
                sumMag[2]+=arrMag[i][2];
                //
                if(i==0){
                    x.append(arrMag[i][0]);
                    y.append(arrMag[i][1]);
                    z.append(arrMag[i][2]);
                }
                else{
                    x.append(", "+arrMag[i][0]);
                    y.append(", "+arrMag[i][1]);
                    z.append(", "+arrMag[i][2]);
                }
                //
            }
            avgDataSensors.append("\tX: "+x+"\n\tY: "+y+"\n\tZ: "+z+"\n");

            Map magnet=new LinkedHashMap();
            magnet.put("X",String.format("%1$.4f",sumMag[0]/countDataSensors).replace(",","."));
            magnet.put("Y",String.format("%1$.4f",sumMag[1]/countDataSensors).replace(",","."));
            magnet.put("Z",String.format("%1$.4f",sumMag[2]/countDataSensors).replace(",","."));
            jsonObject.put("Mag",magnet);
        }

        if(hasSensors[2]) {
            avgDataSensors.append("Temperature: ");
            float sumTemp=0;
            for(int i=0;i<countDataSensors;i++) {
                sumTemp += arrTemperature[i];
                //
                if(i==0)avgDataSensors.append(arrTemperature[i]);
                else avgDataSensors.append(", "+arrTemperature[i]);
                //
            }
            avgDataSensors.append("\n");

            jsonObject.put("Temperature",String.format("%1$.4f",sumTemp/countDataSensors).replace(",","."));
        }

        if(hasSensors[3]) {
            avgDataSensors.append("Pres: ");
            float sumPres=0;
            for(int i=0;i<countDataSensors;i++) {
                sumPres += arrPres[i];
                //
                if(i==0)avgDataSensors.append(arrPres[i]);
                else avgDataSensors.append(", "+arrPres[i]);
                //
            }
            avgDataSensors.append("\n");

            jsonObject.put("Pres",String.format("%1$.4f",sumPres/countDataSensors).replace(",","."));
        }

        if(hasSensors[4]){
            avgDataSensors.append("Light: ");
            float sumLight=0;
            for(int i=0;i<countDataSensors;i++) {
                sumLight += arrLight[i];
                //
                if(i==0)avgDataSensors.append(arrLight[i]);
                else avgDataSensors.append(", "+arrLight[i]);
                //
            }
            avgDataSensors.append("\n");

            jsonObject.put("Light",String.format("%1$.4f",sumLight/countDataSensors).replace(",","."));
        }

        avgDataSensors.append("Orient:\n");
        StringBuilder a=new StringBuilder();
        StringBuilder r=new StringBuilder();
        StringBuilder p=new StringBuilder();
        float[] sumOrient={0,0,0};
        for(int i=0;i<countDataSensors;i++){
            sumOrient[0]+=arrOrient[i][0];
            sumOrient[1]+=arrOrient[i][1];
            sumOrient[2]+=arrOrient[i][2];
            //
            if(i==0){
                a.append(arrOrient[i][0]);
                r.append(arrOrient[i][1]);
                p.append(arrOrient[i][2]);
            }
            else{
                a.append(", "+arrOrient[i][0]);
                r.append(", "+arrOrient[i][1]);
                p.append(", "+arrOrient[i][2]);
            }
            //
        }
        avgDataSensors.append("\tA: "+a+"\n\tR: "+r+"\n\tP: "+p);

        Map orientation=new LinkedHashMap();
        orientation.put("A",String.format("%1$.4f",sumOrient[0]/countDataSensors).replace(",","."));

        orientation.put("R",String.format("%1$.4f",sumOrient[1]/countDataSensors).replace(",","."));
        orientation.put("P",String.format("%1$.4f",sumOrient[2]/countDataSensors).replace(",","."));
        jsonObject.put("Orient",orientation);

        root.put("",jsonObject);

        //
        String rootText=""+root.toJSONString();
        rootText=rootText.substring(4);
        rootText=rootText.substring(0,rootText.length()-1);

        //Разбиение содержимого json файла на строки и форматирование
        rootText=rootText.replaceAll("\\{","{\n");
        rootText=rootText.replaceAll(",",",\n");
        rootText=rootText.replaceAll("\\}","\n}");

        int numT=0;
        String[] arrRoot=rootText.split("\n");
        for(int i=0;i<arrRoot.length;i++){
            String s=arrRoot[i];
            if(s.contains("}"))
                numT--;
            int t=0;
            while(t<numT){
                s="\t"+s;
                t++;
            }
            if(s.contains("{"))
                numT++;
            arrRoot[i]=s;
        }
        rootText="";
        for(String s:arrRoot)
            rootText+=s+"\n";
        //

        //
        if(!isPoint) {
            try {
                ExifInterface exif = new ExifInterface(photoFile.toString());
                exif.setAttribute("UserComment", rootText);
                exif.saveAttributes();
            }
            catch (IOException ex) {}
        }
        //

        text.append(rootText);
        //
    }
    //

    public void updateGPS(StringBuilder info){
        //
        if(needGps) {
            valuesGps[0] = "56.9023";
            valuesGps[1] = "60.6092";
            valuesGps[2] = "238";
            valuesGps[3] = "9.9";
        }
        //
        if(!valuesGps[0].equals("0") || !valuesGps[1].equals("0") || !valuesGps[2].equals("0")
                || !valuesGps[3].equals("0") || !valuesGps[4].equals("0")) {
            valuesGps[0] = valuesGps[0].replace(",", ".");
            valuesGps[1] = valuesGps[1].replace(",", ".");
            valuesGps[2] = valuesGps[2].replace(",", ".");
            valuesGps[3] = valuesGps[3].replace(",", ".");
            valuesGps[4] = valuesGps[4].replace(",", ".");
            info.append("GPS (Lat, Lon, Elev, HDOP, VDOP): "+valuesGps[0]+", "+valuesGps[1]+", "+valuesGps[2]+", "+valuesGps[3]+", "+valuesGps[4]+"\n");

            //
            arrGps[currentGps][0]=Double.parseDouble(valuesGps[0]);
            arrGps[currentGps][1]=Double.parseDouble(valuesGps[1]);
            arrGps[currentGps][2]=Double.parseDouble(valuesGps[2]);
            arrGps[currentGps][3]=Double.parseDouble(valuesGps[3]);
            arrGps[currentGps][4]=Double.parseDouble(valuesGps[4]);
            currentGps++;
            if(currentGps==countDataSensors)currentGps=0;
            //

            Map gps=new LinkedHashMap();
            gps.put("Lat",""+valuesGps[0]);
            gps.put("Lon",""+valuesGps[1]);
            gps.put("Elev",""+valuesGps[2]);
            gps.put("HDOP",""+valuesGps[3]);
            gps.put("VDOP",""+valuesGps[4]);
            dataSensors.put("GPS",gps);

            double lat=Double.parseDouble(valuesGps[0]);
            double lon=Double.parseDouble(valuesGps[1]);
            CoordinateConversion utmCoords=new CoordinateConversion();
            //
            Deg2UTM deg=new Deg2UTM(lat,lon);
            double northing=Double.parseDouble(String.format("%1.4f",deg.northing).replace(",","."));
            double easting=Double.parseDouble(String.format("%1.4f",deg.easting).replace(",","."));
            //
            info.append("UTM (N, E): "+northing+", "+easting+"\n");

            Map utm=new LinkedHashMap();
            utm.put("X",""+easting);
            utm.put("Y",""+northing);
            utm.put("Z",""+valuesGps[2]);
            dataSensors.put("UTM",utm);

            //
            arrUtm[currentUtm][0]=easting;
            arrUtm[currentUtm][1]=northing;
            arrUtm[currentUtm][2]=Double.parseDouble(valuesGps[2]);
            currentUtm++;
            if(currentUtm==countDataSensors)currentUtm=0;
            //

            double mskX=easting+dxLocalSystem;
            double mskY=northing+dyLocalSystem;
            double mskZ=Double.parseDouble(valuesGps[2])+dzLocalSystem;
            double angleMsk=Math.toRadians(angleLocalSystem);
            mskX=0+(mskX-0)*Math.cos(angleMsk)-(0-mskY)*Math.sin(angleMsk);
            mskY=0+(mskY-0)*Math.cos(angleMsk)+(mskX-0)*Math.sin(angleMsk);

            mskX=Double.parseDouble(String.format("%1$.4f",mskX).replace(",","."));
            mskY=Double.parseDouble(String.format("%1$.4f",mskY).replace(",","."));

            //
            arrMsk[currentMsk][0]=mskX;
            arrMsk[currentMsk][1]=mskY;
            arrMsk[currentMsk][2]=mskZ;
            currentMsk++;
            if(currentMsk==countDataSensors)currentMsk=0;
            //

            Map msk=new LinkedHashMap();
            msk.put("Name",nameLocalSystem);
            msk.put("X",mskX);
            msk.put("Y",mskY);
            msk.put("Z",mskZ);
            dataSensors.put("MSK",msk);
        }
    }

    public void showInformation(StringBuilder info){
        if(heightPicture==0 && widthPicture==0){
            String[] sizes=getSizesPhoto();
            heightPicture=Integer.parseInt(sizes[0].split("x")[0]);
            widthPicture=Integer.parseInt(sizes[0].split("x")[1]);
        }

        if(hasSensors[0]){
            valuesAccelerometer[0]=Float.parseFloat(String.format("%1$.4f",valuesAccelerometer[0]).replace(",","."));
            valuesAccelerometer[1]=Float.parseFloat(String.format("%1$.4f",valuesAccelerometer[1]).replace(",","."));
            valuesAccelerometer[2]=Float.parseFloat(String.format("%1$.4f",valuesAccelerometer[2]).replace(",","."));
            info.append("Acc (X, Y, Z): "+valuesAccelerometer[0]+", "+valuesAccelerometer[1]+", "+valuesAccelerometer[2]+"\n");

            Map accsel=new LinkedHashMap();
            accsel.put("X",""+valuesAccelerometer[0]);
            accsel.put("Y",""+valuesAccelerometer[1]);
            accsel.put("Z",""+valuesAccelerometer[2]);
            dataSensors.put("Acc",accsel);
        }

        if(hasSensors[1]) {
            valuesMagneticField[0]=Float.parseFloat(String.format("%1$.4f",valuesMagneticField[0]).replace(",","."));
            valuesMagneticField[1]=Float.parseFloat(String.format("%1$.4f",valuesMagneticField[1]).replace(",","."));
            valuesMagneticField[2]=Float.parseFloat(String.format("%1$.4f",valuesMagneticField[2]).replace(",","."));
            info.append("Mag (X, Y, Z): "+valuesMagneticField[0]+", "+valuesMagneticField[1]+", "+valuesMagneticField[2]+"\n");

            Map magnet=new LinkedHashMap();
            magnet.put("X",""+valuesMagneticField[0]);
            magnet.put("Y",""+valuesMagneticField[1]);
            magnet.put("Z",""+valuesMagneticField[2]);
            dataSensors.put("Mag",magnet);
        }

        if(hasSensors[2]) {
            valueTemperature[0]=Float.parseFloat(String.format("%1$.4f",valueTemperature[0]).replace(",","."));
            info.append("Temperature: "+valueTemperature[0]+"\n");

            dataSensors.put("Temperature",""+valueTemperature[0]);
        }

        if(hasSensors[3]) {
            valuePressure[0]=Float.parseFloat(String.format("%1$.4f",valuePressure[0]).replace(",","."));
            info.append("Pres: "+valuePressure[0]+"\n");

            dataSensors.put("Pres",""+valuePressure[0]);
        }

        if(hasSensors[4]){
            valueLight[0]=Float.parseFloat(String.format("%1$.4f",valueLight[0]).replace(",","."));
            info.append("Light: "+valueLight[0]+"\n");

            dataSensors.put("Light",""+valueLight[0]);
        }

        //
        if(widthPicture!=0 && heightPicture!=0)
            info.append("Size photo: "+heightPicture+", "+widthPicture+"\n");
        //

        //orientation
        float[] r = new float[9];
        float[] valueResults1 = new float[3];
        SensorManager.getRotationMatrix(r, null, valuesAccelerometer, valuesMagneticField);
        SensorManager.getOrientation(r, valueResults1);
        valueResults1[0] = (float) Math.toDegrees(valueResults1[0]);
        valueResults1[1] = (float) Math.toDegrees(valueResults1[1]);
        valueResults1[2] = (float) Math.toDegrees(valueResults1[2]);
        //
        if(valueResults1[0]<0)valueResults1[0]+=360;
        arrOrient[currentOrient][0]=valueResults1[0];
        arrOrient[currentOrient][1]=valueResults1[2];
        arrOrient[currentOrient][2]=valueResults1[1];
        //
        result=(int)valueResults1[2];
        //

        currentOrient++;
        if(currentOrient==countDataSensors)currentOrient=0;
        //

        Map orientation=new LinkedHashMap();
        orientation.put("A",""+valueResults1[0]);
        orientation.put("P",""+valueResults1[1]);
        orientation.put("R",""+valueResults1[2]);
        dataSensors.put("Orient",orientation);
        //
    }

    public void onClickPicture(View view) {
        Runnable runnable = new Runnable() {
            public void run() {
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        try {
                            numberPicture++;
                            //
                            GregorianCalendar cal = new GregorianCalendar();
                            String year = "" + cal.get(Calendar.YEAR);
                            String month = "" + (cal.get(Calendar.MONTH) + 1);
                            if (month.length() == 1) month = "0" + month;
                            String day = "" + cal.get(Calendar.DAY_OF_MONTH);
                            if (day.length() == 1) day = "0" + day;
                            String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
                            if (hour.length() == 1) hour = "0" + hour;
                            String minute = "" + cal.get(Calendar.MINUTE);
                            if (minute.length() == 1) minute = "0" + minute;
                            String second = "" + cal.get(Calendar.SECOND);
                            if (second.length() == 1) second = "0" + second;
                            String nameFiles = "LC-" + year + month + day + "-" + hour + minute + second;
                            photoFile = new File(directory.getPath() + "/" + nameFiles + ".jpg");
                            jsonFile = new File(directory.getPath() + "/" + nameFiles + ".txt");
                            //
                            FileOutputStream fos = new FileOutputStream(photoFile);
                            Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);

                            //
                            if(heightPicture!=0 && widthPicture!=0)
                                realImage=Bitmap.createScaledBitmap(realImage,widthPicture,heightPicture,true);
                            //

                            int angleToRotate = result;
                            if(angleToRotate>-45 && angleToRotate<45)
                                realImage = rotate(realImage, 90);
                            else if(angleToRotate>=45)
                                realImage = rotate(realImage, 180);
                            realImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();

                            //Запись в файл сессии
                            if(!fileSession.isEmpty()) {
                                File sessionFile = new File(fileSession);
                                try {
                                    String[] date = getDate();
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(sessionFile, true));
                                    numberFileSession++;
                                    writer.write(numberFileSession + ", фото, " + photoFile.getName() + ", " + date[2] + "." + date[1] + "." + date[0] + ", " + date[3] + ":" + date[4] + ":" + date[5]);
                                    writer.newLine();
                                    writer.close();
                                } catch (IOException ex) {}
                            }
                            //

                            if(isDalnomer) {
                                //Дальномер
                                LayoutInflater li = LayoutInflater.from(active);
                                final View dalnomerView = li.inflate(R.layout.dalnomer, null);
                                AlertDialog.Builder dalnomerDialogBuilder = new AlertDialog.Builder(active);
                                dalnomerDialogBuilder.setView(dalnomerView);
                                final EditText dalnomerInput = dalnomerView.findViewById(R.id.input_text);
                                Button num1=dalnomerView.findViewById(R.id.num1);
                                num1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"1");
                                    }
                                });
                                Button num2=dalnomerView.findViewById(R.id.num2);
                                num2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"2");
                                    }
                                });
                                Button num3=dalnomerView.findViewById(R.id.num3);
                                num3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"3");
                                    }
                                });
                                Button num4=dalnomerView.findViewById(R.id.num4);
                                num4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"4");
                                    }
                                });
                                Button num5=dalnomerView.findViewById(R.id.num5);
                                num5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"5");
                                    }
                                });
                                Button num6=dalnomerView.findViewById(R.id.num6);
                                num6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"6");
                                    }
                                });
                                Button num7=dalnomerView.findViewById(R.id.num7);
                                num7.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"7");
                                    }
                                });
                                Button num8=dalnomerView.findViewById(R.id.num8);
                                num8.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"8");
                                    }
                                });
                                Button num9=dalnomerView.findViewById(R.id.num9);
                                num9.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"9");
                                    }
                                });
                                Button num0=dalnomerView.findViewById(R.id.num0);
                                num0.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dalnomerInput.setText(dalnomerInput.getText()+"0");
                                    }
                                });
                                dalnomerDialogBuilder.setCancelable(false).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        distanceToObject = "" + dalnomerInput.getText();
                                        try {
                                            String[] date=getDate();
                                            BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));
                                            StringBuilder text = new StringBuilder();
                                            getTextToFile(text, date[2], date[1], date[0], date[3], date[4], date[5], false);
                                            writer.write(text.toString());
                                            writer.close();
                                        }
                                        catch(Exception e){}
                                        //
                                    }
                                }).setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alertDialog = dalnomerDialogBuilder.create();
                                alertDialog.show();
                                Button numPoint=dalnomerView.findViewById(R.id.numPoint);
                                numPoint.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(!dalnomerInput.getText().toString().contains(".") && !dalnomerInput.getText().toString().isEmpty())
                                            dalnomerInput.setText(dalnomerInput.getText()+".");
                                    }
                                });
                            }
                            else {
                                BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));
                                StringBuilder text = new StringBuilder();
                                getTextToFile(text, day, month, year, hour, minute, second, false);

                                writer.write(text.toString());
                                writer.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        camera.startPreview();
                    }
                });
            }
        };
        camera.startPreview();
        Thread thread = new Thread(runnable);
        thread.start();
    }
    //

    //
    public String[] getSizesPhoto(){
        if(camera==null)
            camera=Camera.open(CAMERA_ID);
        Size s=camera.getParameters().getPictureSize();
        int height=s.height;
        int width=s.width;
        String size1=height+"x"+width;
        String size2=(int)(height*0.75)+"x"+(int)(width*0.75);
        String size3=(int)(height*0.5)+"x"+(int)(width*0.5);
        String[] sizes={size1,size2,size3};
        return sizes;
    }
    //

    public static String getUTCdatetimeAsString() {
        String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(new Date());
        String[] utcArr=utcTime.split(" ");
        return utcArr[0]+", "+utcArr[1]+", ";
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //вертикальное положение

        askCameraPermission();
        askWriteStoragePermission();
        askReadStoragePermission();
        askLocatePermission();
        askFineLocatePermission();
        active = this;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        //Инициализация массивов датчиков
        arrAcc=new float[countDataSensors][3];
        arrMag=new float[countDataSensors][3];
        arrTemperature=new float[countDataSensors];
        arrPres=new float[countDataSensors];
        arrLight=new float[countDataSensors];
        arrGps=new double[countDataSensors][5];
        arrUtm=new double[countDataSensors][3];
        arrMsk=new double[countDataSensors][3];
        arrOrient=new float[countDataSensors][3];
        //

        //
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locationManager.addNmeaListener(new OnNmeaMessageListener() {
                    @Override
                    public void onNmeaMessage(String message, long timestamp) {
                        String[] nmeaParts = message.split(",");
                        if (nmeaParts[0].equalsIgnoreCase("$GPGSA")) {
                            if (nmeaParts.length > 17 && nmeaParts[17] != null && !nmeaParts[17].isEmpty() && !nmeaParts[17].startsWith("*")) {
                                double d = Double.parseDouble(nmeaParts[17].split("\\*")[0]);
                                valuesGps[4] = String.format("%1$.4f", d);
                            }
                        }
                    }
                });
            }
        }
        //
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensorListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                int type=event.sensor.getType();
                if(type==Sensor.TYPE_ACCELEROMETER) {
                    valuesAccelerometer = event.values.clone();
                    arrAcc[currentAcc][0]=valuesAccelerometer[0];
                    arrAcc[currentAcc][1]=valuesAccelerometer[1];
                    arrAcc[currentAcc][2]=valuesAccelerometer[2];
                    currentAcc++;
                    if(currentAcc==countDataSensors)currentAcc=0;
                }
                else if(type==Sensor.TYPE_MAGNETIC_FIELD) {
                    valuesMagneticField = event.values.clone();
                    arrMag[currentMag][0]=valuesMagneticField[0];
                    arrMag[currentMag][1]=valuesMagneticField[1];
                    arrMag[currentMag][2]=valuesMagneticField[2];
                    currentMag++;
                    if(currentMag==countDataSensors)currentMag=0;
                }
                else if(type==Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    valueTemperature = event.values.clone();
                    arrTemperature[currentTemperature]=valueTemperature[0];
                    currentTemperature++;
                    if(currentTemperature==countDataSensors)currentTemperature=0;
                }
                else if(type==Sensor.TYPE_PRESSURE) {
                    valuePressure = event.values.clone();
                    arrPres[currentPres]=valuePressure[0];
                    currentPres++;
                    if(currentPres==countDataSensors)currentPres=0;
                }
                else if(type==Sensor.TYPE_LIGHT) {
                    valueLight = event.values.clone();
                    arrLight[currentLight]=valueLight[0];
                    currentLight++;
                    if(currentLight==countDataSensors)currentLight=0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //
        startCamera();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("listFiles",listFiles);
        outState.putStringArray("gps",valuesGps);
        outState.putFloat("cep",cep);
        outState.putSerializable("dataSensors",(Serializable)dataSensors);
        outState.putFloat("bearing",bearing);
        outState.putInt("zeroPitch",zeroPitch);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listFiles = savedInstanceState.getStringArrayList("listFiles");
        valuesGps=savedInstanceState.getStringArray("gps");
        cep=savedInstanceState.getFloat("cep");
        dataSensors=(Map)savedInstanceState.getSerializable("dataSensors");
        bearing=savedInstanceState.getFloat("bearing");
        zeroPitch=savedInstanceState.getInt("zeroPitch");
    }

    public String[] getDate(){
        GregorianCalendar cal = new GregorianCalendar();
        String year = "" + cal.get(Calendar.YEAR);
        String month = "" + (cal.get(Calendar.MONTH) + 1);
        if (month.length() == 1) month = "0" + month;
        String day = "" + cal.get(Calendar.DAY_OF_MONTH);
        if (day.length() == 1) day = "0" + day;
        String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
        if (hour.length() == 1) hour = "0" + hour;
        String minute = "" + cal.get(Calendar.MINUTE);
        if (minute.length() == 1) minute = "0" + minute;
        String second = "" + cal.get(Calendar.SECOND);
        if (second.length() == 1) second = "0" + second;
        return new String[]{year,month,day,hour,minute,second};
    }

    //Расчет расстояния трека
    public double getDistanceTrack(double lat1, double lon1, double lat2, double lon2){
        if(lat1==lat2 && lon1==lon2)
            return 0;
        else{
            double radLat1=Math.PI*lat1/180;
            double radLat2=Math.PI*lat2/180;
            double theta=lon1-lon2;
            double radtheta=Math.PI*theta/180;
            double dist=Math.sin(radLat1)*Math.sin(radLat2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.cos(radtheta);
            if(dist>1)
                dist=1;
            dist=Math.acos(dist);
            dist*=180/Math.PI;
            dist*=60*1.1515;
            dist*=1.609344;
            return dist;
        }
    }

    //Трек
    public void createTrack(String nameCreateTrack){
        numberPointTrack=0;
        timeTrack=0;
        distanceTrack=0;
        nameTrack=nameCreateTrack;
        //Поиск файлов треков и определение номеров для трека
        File[] filesDirectory=directory.listFiles();
        for(File fileDirectory:filesDirectory){
            if(fileDirectory.getName().contains("Трек"))
                nameTracks.add(fileDirectory.getName());
        }
        if(!nameTracks.isEmpty())
            for(String name:nameTracks){
                int newNumberTrack=Integer.parseInt(name.split("Трек")[1].split("-")[1].substring(0,2));
                if(newNumberTrack>numberTrack)numberTrack=newNumberTrack;
            }
        if(nameTrack.contains("Трек-")){
            int newNumberTrack=Integer.parseInt(nameTrack.split("Трек")[1].split("-")[1].substring(0,2));
            if(newNumberTrack>numberTrack)numberTrack=newNumberTrack;
        }
        //
        hasTrackFile=false;
        final String[] nameFiles = new String[1];

        if(timerTrack!=null){
            timerTrack.cancel();
            timerTrack=null;
        }
        //Вывод дополнительной информации
        final TextView trackText=findViewById(R.id.trackText);
        trackText.setText(nameTrack+", "+numberPointTrack+", "+timeTrack+"с, "+distanceTrack+"м");
        infoTimer=new Timer();
        infoTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeTrack++;
                        trackText.setText(nameTrack+", "+numberPointTrack+", "+timeTrack+"с, "+distanceTrack+"м");
                    }
                });
            }
        },0,1000);
        //Запись файла трека
        timerTrack=new Timer();
        timerTrack.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        numberPointTrack++;
                        String[] date=getDate();

                        StringBuilder trackText=new StringBuilder();
                        trackText.append(""+numberPointTrack);
                        if(dataSensors!=null && dataSensors.containsKey("MSK")) {
                            float[] sumMsk={0,0,0};
                            for(int i=0;i<countDataSensors;i++){
                                sumMsk[0]+=arrMsk[i][0];
                                sumMsk[1]+=arrMsk[i][1];
                                sumMsk[2]+=arrMsk[i][2];
                            }
                            if(sumMsk[0]/countDataSensors!=0 || sumMsk[1]/countDataSensors!=0 || sumMsk[2]/countDataSensors!=0) {
                                trackText.append(", " + String.format("%1$.4f", sumMsk[0] / countDataSensors).replace(",", "."));
                                trackText.append(", " + String.format("%1$.4f", sumMsk[1] / countDataSensors).replace(",", "."));
                                trackText.append(", " + String.format("%1$.4f", sumMsk[2] / countDataSensors).replace(",", "."));
                            }
                        }
                        if(dataSensors!=null && dataSensors.containsKey("Orient")){
                            float[] sumOrient={0,0,0};
                            for(int i=0;i<countDataSensors;i++){
                                sumOrient[0]+=arrOrient[i][0];
                                sumOrient[1]+=arrOrient[i][1];
                                sumOrient[2]+=arrOrient[i][2];
                            }
                            trackText.append(", "+String.format("%1$.4f",sumOrient[0]/countDataSensors).replace(",","."));
                            trackText.append(", "+String.format("%1$.4f",sumOrient[1]/countDataSensors).replace(",","."));
                            trackText.append(", "+String.format("%1$.4f",sumOrient[2]/countDataSensors).replace(",","."));
                        }
                        if(dataSensors!=null && dataSensors.containsKey("GPS")){
                            double[] sumGps={0,0,0,0,0};
                            for(int i=0;i<countDataSensors;i++){
                                sumGps[0]+=arrGps[i][0];
                                sumGps[1]+=arrGps[i][1];
                                sumGps[2]+=arrGps[i][2];
                                sumGps[3]+=arrGps[i][3];
                                sumGps[4]+=arrGps[i][4];
                            }
                            trackText.append(", "+String.format("%1$.4f",sumGps[0]/countDataSensors).replace(",","."));
                            trackText.append(", "+String.format("%1$.4f",sumGps[1]/countDataSensors).replace(",","."));
                            trackText.append(", "+String.format("%1$.4f",sumGps[2]/countDataSensors).replace(",","."));
                            trackText.append(", "+String.format("%1$.4f",sumGps[3]/countDataSensors).replace(",","."));
                            trackText.append(", "+String.format("%1$.4f",sumGps[4]/countDataSensors).replace(",","."));

                            //расчет расстояния трека
                            if(numberPointTrack==1){
                                oldGps[0]=Double.parseDouble(String.format("%1$.4f",sumGps[0]/countDataSensors).replace(",","."));
                                oldGps[1]=Double.parseDouble(String.format("%1$.4f",sumGps[1]/countDataSensors).replace(",","."));
                            }
                            else if(numberPointTrack>1){
                                newGps[0]=Double.parseDouble(String.format("%1$.4f",sumGps[0]/countDataSensors).replace(",","."));
                                newGps[1]=Double.parseDouble(String.format("%1$.4f",sumGps[1]/countDataSensors).replace(",","."));
                                distanceTrack+=getDistanceTrack(oldGps[0],oldGps[1],newGps[0],newGps[1]);
                                distanceTrack=Double.parseDouble(String.format("%1$.4f",distanceTrack).replace(",","."));
                                oldGps[0]=newGps[0];
                                oldGps[1]=newGps[1];
                            }
                            //
                        }
                        trackText.append(", "+date[2]+"."+date[1]+"."+date[0]);
                        trackText.append(", "+date[3]+":"+date[4]+":"+date[5]);

                        if(nameFiles[0]==null){
                            nameFiles[0] = "track_" + date[0] + date[1] + date[2] + "-" + date[3] + date[4] + date[5]+"-"+nameTrack;
                            //Запись в файл сессии
                            if(!fileSession.isEmpty()) {
                                File sessionFile = new File(fileSession);
                                try {
                                    date = getDate();
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(sessionFile, true));
                                    numberFileSession++;
                                    writer.write(numberFileSession + ", трек, " + (nameFiles[0] + ".txt") + ", " + date[2] + "." + date[1] + "." + date[0] + ", " + date[3] + ":" + date[4] + ":" + date[5]);
                                    writer.newLine();
                                    writer.close();
                                } catch (IOException ex) { }
                            }
                            //
                        }
                        File trackFile = new File(directory.getPath() + "/" + nameFiles[0] + ".txt");
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(trackFile,true));
                            writer.write(trackText.toString());
                            writer.newLine();
                            writer.close();
                        }
                        catch(IOException e){}
                    }
                });
            }
        },0,stepTrack*1000);
        //
    }

    //Точка
    public void createPoint(String nameCreatePoint){
        namePoint=nameCreatePoint;
        numberPoint++;
        String[] date = getDate();
        String nameFiles="point_"+date[0]+date[1]+date[2]+"-"+date[3]+date[4]+date[5];
        File pointFile = new File(directory.getPath() + "/" + nameFiles + ".txt");
        //Запись в файл сессии
        if(!fileSession.isEmpty()) {
            File sessionFile = new File(fileSession);
            try {
                String[] date1 = getDate();
                BufferedWriter writer = new BufferedWriter(new FileWriter(sessionFile, true));
                numberFileSession++;
                writer.write(numberFileSession + ", точка, " + pointFile.getName() + ", " + date1[2] + "." + date1[1] + "." + date1[0] + ", " + date1[3] + ":" + date1[4] + ":" + date1[5]);
                writer.newLine();
                writer.close();
            } catch (IOException ex) {}
        }
        //
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pointFile));
            StringBuilder text = new StringBuilder();
            getTextToFile(text, date[2],date[1],date[0],date[3],date[4],date[5], true);
            writer.write(text.toString());
            writer.close();
        }
        catch(IOException ex){}

        Map utm=(Map)dataSensors.get("UTM");
        if(utm!=null) {
            newXSession = Double.parseDouble("" + utm.get("X"));
            newYSession = Double.parseDouble("" + utm.get("Y"));
            if(numberPoint==1)oldXSession=newXSession;
            if(numberPoint==1)oldYSession=newYSession;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null) {
            return;
        }
        //
        dataMap=(Map<String, String>)data.getSerializableExtra("data");
        if(dataMap.containsKey("dalnomer")){
            if(dataMap.get("dalnomer").equals("true"))
                isDalnomer=true;
            else
                isDalnomer=false;
            SharedPreferences.Editor editor=mSettings.edit();
            editor.putString(APP_PREFERENCES_DALNOMER,""+isDalnomer);
            editor.apply();
        }
        if(dataMap.containsKey("sizePhoto")){
            heightPicture=Integer.parseInt(dataMap.get("sizePhoto").split(" ")[0]);
            widthPicture=Integer.parseInt(dataMap.get("sizePhoto").split(" ")[1]);
        }
        if(dataMap.containsKey("nameDevice")){
            nameDevice=dataMap.get("nameDevice");
            SharedPreferences.Editor editor=mSettings.edit();
            editor.putString(APP_PREFERENCES_NAME_DEVICE,nameDevice);
            editor.apply();
        }
        if(dataMap.containsKey("localSystem")){
            nameLocalSystem=dataMap.get("localSystem").split(" ")[0];
            dxLocalSystem=Double.parseDouble(dataMap.get("localSystem").split(" ")[1]);
            dyLocalSystem=Double.parseDouble(dataMap.get("localSystem").split(" ")[2]);
            dzLocalSystem=Double.parseDouble(dataMap.get("localSystem").split(" ")[3]);
            angleLocalSystem=Double.parseDouble(dataMap.get("localSystem").split(" ")[4]);

            SharedPreferences.Editor editor=mSettings.edit();
            editor.putString(APP_PREFERENCES_MSK,nameLocalSystem+", "+dxLocalSystem+", "+dyLocalSystem+", "+dzLocalSystem+", "+angleLocalSystem);
            editor.apply();
        }
        if(dataMap.containsKey("stepTrack")){
            stepTrack=Integer.parseInt(dataMap.get("stepTrack"));
        }
        if(dataMap.containsKey("point")){
            createPoint(dataMap.get("point"));
        }
        if(dataMap.containsKey("session")){
            nameSession=dataMap.get("session");
            numberPoint=0;
            numberPicture=0;
            numberFileSession=0;
            oldXSession=0;
            oldYSession=0;
            newXSession=0;
            newYSession=0;
            //Поиск файлов треков и определение номеров для трека
            File[] filesDirectory=directory.listFiles();
            for(File fileDirectory:filesDirectory){
                if(fileDirectory.getName().contains("Сессия"))
                    nameSessions.add(fileDirectory.getName());
            }
            if(!nameSessions.isEmpty())
                for(String name:nameSessions){
                    int newNumberSession=Integer.parseInt(name.split("Сессия")[1].split("-")[1].substring(0,2));
                    if(newNumberSession>numberSession)numberSession=newNumberSession;
                }
            if(nameSession.contains("Сессия-")){
                int newNumberSession=Integer.parseInt(nameSession.split("Сессия")[1].split("-")[1].substring(0,2));
                if(newNumberSession>numberSession)numberSession=newNumberSession;
            }
            //Создание файла сессии
            String[] date=getDate();
            String nameFiles="session_"+date[0]+date[1]+date[2]+"-"+date[3]+date[4]+date[5]+"-"+nameSession;
            File sessionFile=new File(directory.getPath() + "/" + nameFiles + ".txt");
            fileSession=sessionFile.getPath();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(sessionFile));
                writer.write("");
                writer.close();
            }
            catch(IOException ex){}
            //
        }
        if(dataMap.containsKey("object")){
            numberObject++;
            String nameObject=dataMap.get("object");
        }
        if(dataMap.containsKey("track")){
            nameTrack=dataMap.get("track");
            createTrack(nameTrack);
        }
        if(dataMap.containsKey("countDataSensors")){
            countDataSensors=Integer.parseInt(dataMap.get("countDataSensors"));
            arrAcc=new float[countDataSensors][3];
            arrMag=new float[countDataSensors][3];
            arrTemperature=new float[countDataSensors];
            arrPres=new float[countDataSensors];
            arrLight=new float[countDataSensors];
            arrGps=new double[countDataSensors][5];
            arrUtm=new double[countDataSensors][3];
            arrMsk=new double[countDataSensors][3];
            arrOrient=new float[countDataSensors][3];
            currentAcc=currentMag=currentTemperature=currentPres=currentLight=currentGps=currentUtm=currentMsk=currentOrient=0;
        }
        //
    }

    //
    public void getCoordsTextPitch(final int numberPitch){
        final TextView textPitch;
        if(numberPitch==0)textPitch=findViewById(R.id.textPitch1);
        else if(numberPitch==1)textPitch=findViewById(R.id.textPitch2);
        else if(numberPitch==2)textPitch=findViewById(R.id.textPitch3);
        else if(numberPitch==3)textPitch=findViewById(R.id.textPitch4);
        else if(numberPitch==4)textPitch=findViewById(R.id.textPitch5);
        else if(numberPitch==5)textPitch=findViewById(R.id.textPitch_2);
        else if(numberPitch==6)textPitch=findViewById(R.id.textPitch_3);
        else if(numberPitch==7)textPitch=findViewById(R.id.textPitch_4);
        else textPitch=findViewById(R.id.textPitch_5);
        ViewTreeObserver vto = textPitch.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    textPitch.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    textPitch.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                coordsTextPitch[numberPitch][0]=textPitch.getLeft();
                coordsTextPitch[numberPitch][1]=textPitch.getTop();
                coordsTextPitch[numberPitch][2]=textPitch.getRight();
                coordsTextPitch[numberPitch][3]=textPitch.getBottom();
            }
        });

        if(numberPitch==8){
            curveTextView.coordsTextPitch=coordsTextPitch;
        }
    }
    //

    //Получение имен файлов снимка в папке locator camera
    public void fillingListFiles(){
        File[] files=directory.listFiles();
        if(files!=null){
            for(File file:files){
                if(file.getName().endsWith(".jpg")){
                    String nameFile=file.getName().substring(0,file.getName().length()-4);
                    if(!listFiles.contains(nameFile))
                        listFiles.add(nameFile);
                }
            }
        }
    }
    //

    public void startCamera(){
        setContentView(R.layout.activity_main);

        //
        curveTextView=findViewById(R.id.textView);
        for(int i=0;i<9;i++)
            getCoordsTextPitch(i);

        final ImageView projectionNorth=findViewById(R.id.projectionNorth);
        projectionNorth.setVisibility(View.INVISIBLE);

        final ImageView centerPoint=findViewById(R.id.centerPoint);
        ViewTreeObserver vto = centerPoint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    centerPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    centerPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float[] coordsCenter=new float[2];
                coordsCenter[0]=centerPoint.getX();
                coordsCenter[1]=centerPoint.getY();
                curveTextView.coordsCenter=coordsCenter;

                ImageView rollLine=findViewById(R.id.rollLine);
                curveTextView.coordsRollLine=new int[]{rollLine.getLeft(),rollLine.getRight(),rollLine.getTop()};
                ImageView azimuthLine=findViewById(R.id.compasLine);
                curveTextView.coordsAzimuthLine=new int[]{azimuthLine.getRight(),azimuthLine.getTop(),azimuthLine.getBottom()};
                ImageView pitchLine=findViewById(R.id.pitchLine);
                curveTextView.coordsPitchLine=new int[]{pitchLine.getLeft(),pitchLine.getRight(),pitchLine.getTop()};

                projectionNorth.setPivotX(coordsCenter[0]);
                projectionNorth.setPivotY(coordsCenter[1]);
            }
        });

        final TextView textRoll=findViewById(R.id.textRoll);
        ViewTreeObserver vto1 = textRoll.getViewTreeObserver();
        vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                textRoll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            else
                textRoll.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            coordsTextRoll[0]=textRoll.getLeft();
            coordsTextRoll[1]=textRoll.getTop();
            coordsTextRoll[2]=textRoll.getRight();
            coordsTextRoll[3]=textRoll.getBottom();
            curveTextView.coordsTextRoll=coordsTextRoll;
            }
        });

        final TextView textCompas=findViewById(R.id.textCompas);
        vto = textCompas.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    textCompas.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    textCompas.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                coordsTextCompas[0]=textCompas.getLeft();
                coordsTextCompas[1]=textCompas.getTop();
                coordsTextCompas[2]=textCompas.getRight();
                coordsTextCompas[3]=textCompas.getBottom();
                curveTextView.coordsTextCompas=coordsTextCompas;
            }
        });
        //

        text1=(TextView)findViewById(R.id.dataText);
        directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"Data sensors");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //Поиск файлов треков и сессий и определение  начальных номеров для трека и сессии
        File[] filesDirectory=directory.listFiles();
        for(File fileDirectory:filesDirectory){
            if(fileDirectory.getName().contains("Трек"))
                nameTracks.add(fileDirectory.getName());
            else if(fileDirectory.getName().contains("Сессия"))
                nameSessions.add(fileDirectory.getName());
        }
        for(String name:nameTracks){
            int newNumberTrack=Integer.parseInt(name.split("Трек")[1].split("-")[1].substring(0,2));
            if(newNumberTrack>numberTrack)numberTrack=newNumberTrack;
        }
        for(String name:nameSessions){
            int newNumberSession=Integer.parseInt(name.split("Сессия")[1].split("-")[1].substring(0,2));
            if(newNumberSession>numberSession)numberSession=newNumberSession;
        }
        //

        sv = (SurfaceView) findViewById(R.id.surfaceView);
        holder = sv.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holderCallback = new HolderCallback();
        holder.addCallback(holderCallback);

        //Создание файла с местными системами
        File mskFile = new File(directory.getPath() + "/MSK.txt");
        if (!mskFile.exists()){
            try {
                String text="Пусто, 0, 0, 0, 0\n"
                        +"МСК-1, 1500000, -5911057.63, 0, 0\n"
                        +"МСК-2, 2500000, -5911057.63, 0, 0\n"
                        +"МСК-3, 1500000, -5911057.63, 0, 0\n"
                        +"МСК-4, 2500000, -5911057.63, 0, 0\n"
                        +"МСК-5, 3500000, -5911057.63, 0, 0";
                BufferedWriter writer = new BufferedWriter(new FileWriter(mskFile));
                writer.write(text);
                writer.close();
            } catch (IOException e) { }
        }
        //

        //Генерация GUID
        if(mSettings.contains(APP_PREFERENCES_GUID))
            guid=mSettings.getLong(APP_PREFERENCES_GUID,0);
        if(mSettings.contains(APP_PREFERENCES_NAME_DEVICE))
            nameDevice=mSettings.getString(APP_PREFERENCES_NAME_DEVICE,"");

        try {
            if (guid == 0) {
                long min = 100000000000000L;
                long max = 999999999999999L;
                max -= min;
                guid = (long) (Math.random() * ++max) + min;

                SharedPreferences.Editor editor=mSettings.edit();
                editor.putLong(APP_PREFERENCES_GUID,guid);
                editor.apply();
            }
        }
        catch(Exception e){}

        //
        Timer timerUpdate=new Timer();
        timerUpdate.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateGPS(new StringBuilder());
                    }
                });
            }
        },0,1000);

        Timer timerScroll=new Timer();
        timerScroll.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        showInformation(new StringBuilder());

                        double[] sumOrient={0,0,0};
                        for(int i=0;i<countDataSensors;i++){
                            sumOrient[0]+=arrOrient[i][0];
                            sumOrient[1]+=arrOrient[i][1];
                            sumOrient[2]+=arrOrient[i][2];
                        }
                        String azimuthStr=""+(sumOrient[0]/countDataSensors);
                        String pitchStr=""+(sumOrient[1]/countDataSensors);
                        String rollStr=""+(sumOrient[2]/countDataSensors);
                        bearing=Float.parseFloat(azimuthStr);
                        if(bearing<0)
                            bearing=180+(Math.abs(bearing));
                        float pitch=Float.parseFloat(pitchStr);
                        float roll=Float.parseFloat(rollStr);

                        //Отклонения
                        double otkloneniePitch=pitch-arrOrient[currentOrient][1];
                        double otklonenieRoll=roll-arrOrient[currentOrient][2];
                        //

                        TextView pitchValue=findViewById(R.id.pitchValue);
                        pitchValue.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));
                        TextView rollValue=findViewById(R.id.rollValue);
                        rollValue.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));

                        //

                        //
                        rollStr=String.format("%1$.1f",0f);
                        String otklonenieRollStr="("+String.format("%1$.2f",0f)+")";
                        pitchStr=String.format("%1$.1f",0f);
                        String otkloneniePitchStr="("+String.format("%1$.2f",0f)+")";
                        rollValue.setText("   "+rollStr+"\u00B0 "+otklonenieRollStr);

                        //Расчет длины проекции вверх
                        curveTextView.roll = (int)roll;
                        //

                        rollStr=String.format("%1$.1f",roll);

                        if(rollStr.length()==5)rollStr=" "+rollStr;
                        else if(rollStr.length()==4)rollStr="  "+rollStr;
                        else if(rollStr.length()==3)rollStr="   "+rollStr;

                        otklonenieRollStr="("+String.format("%1$.2f",Math.abs(otklonenieRoll))+")";
                        if(otklonenieRollStr.length()==6)otklonenieRollStr=" "+otklonenieRollStr;

                        rollValue.setText("" + rollStr + "\u00B0"+otklonenieRollStr);
                        curveTextView.rollValue=roll;

                        pitchStr=String.format("%1$.1f",pitch);

                        if(pitchStr.length()==5)pitchStr=" "+pitchStr;
                        else if(pitchStr.length()==4)pitchStr="  "+pitchStr;
                        else if(pitchStr.length()==3)pitchStr="   "+pitchStr;

                        otkloneniePitchStr="("+String.format("%1$.2f",Math.abs(otkloneniePitch))+")";
                        if(otkloneniePitchStr.length()==6)otkloneniePitchStr=" "+otkloneniePitchStr;

                        pitchValue.setText("" + pitchStr + "\u00B0"+otkloneniePitchStr);
                        curveTextView.pitchValue=pitch;
                    }
                });
            }
        },0,50);
        //

        //Обновление датчиков
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        StringBuilder info=new StringBuilder();
                        showInformation(info);

                        String[] localDate=getDate();
                        String utc=getUTCdatetimeAsString();
                        Map gps=(Map)dataSensors.get("GPS");
                        StringBuilder textUpLeft=new StringBuilder();
                        textUpLeft.append("Время: "+utc+localDate[3]+":"+localDate[4]+":"+localDate[5]);
                        textUpLeft.append("\nУсреднение: "+countDataSensors+", "+countDataSensors);
                        if(gps!=null && !gps.get("Lat").equals("0") && !gps.get("Lat").equals("0.0")){
                            //
                            double[] sumGps={0,0,0,0};
                            double[] sumUtm={0,0};
                            double[] sumMsk={0,0};
                            for(int i=0;i<countDataSensors;i++){
                                sumGps[0]+=arrGps[i][0];
                                sumGps[1]+=arrGps[i][1];
                                sumGps[2]+=arrGps[i][2];
                                sumGps[3]+=arrGps[i][3];

                                sumUtm[0]+=arrUtm[i][0];
                                sumUtm[1]+=arrUtm[i][1];

                                sumMsk[0]+=arrMsk[i][0];
                                sumMsk[1]+=arrMsk[i][1];
                            }
                            String lat=String.format("%1$.4f", sumGps[0]/countDataSensors).replace(",", ".");
                            String lon=String.format("%1$.4f", sumGps[1]/countDataSensors).replace(",", ".");
                            String elev=String.format("%1$.4f", sumGps[2]/countDataSensors).replace(",", ".");
                            String hdov=String.format("%1$.4f", sumGps[3]/countDataSensors).replace(",", ".");
                            String utmX=String.format("%1$.1f", sumUtm[0]/countDataSensors).replace(",", ".");
                            String utmY=String.format("%1$.1f", sumUtm[1]/countDataSensors).replace(",", ".");
                            String mskX=String.format("%1$.1f", sumMsk[0]/countDataSensors).replace(",", ".");
                            String mskY=String.format("%1$.1f", sumMsk[1]/countDataSensors).replace(",", ".");

                            //Вычисление gps
                            if(currentGps+1==countDataSensors){
                                avgGps[0]=Float.parseFloat(lat);
                                avgGps[1]=Float.parseFloat(lon);
                                avgGps[2]=Float.parseFloat(elev);
                                avgOtklonenieGps[0]=sumOtklonenieGps[0]/countDataSensors;
                                avgOtklonenieGps[1]=sumOtklonenieGps[1]/countDataSensors;
                                avgOtklonenieGps[2]=sumOtklonenieGps[2]/countDataSensors;
                                sumOtklonenieGps[0]=0;
                                sumOtklonenieGps[1]=0;
                                sumOtklonenieGps[2]=0;
                            }
                            if(avgGps[0]!=0)
                                sumOtklonenieGps[0]+=Float.parseFloat(""+gps.get("Lat"))-avgGps[0];
                            if(avgGps[1]!=0)
                                sumOtklonenieGps[1]+=Float.parseFloat(""+gps.get("Lon"))-avgGps[1];
                            if(avgGps[2]!=0)
                                sumOtklonenieGps[2]+=Float.parseFloat(""+gps.get("Elev"))-avgGps[2];

                            String avgGps0Str=String.format("%1$.4f",avgGps[0]).replace(",", ".");
                            String avgGps1Str=String.format("%1$.4f",avgGps[1]).replace(",", ".");
                            String avgGps2Str=String.format("%1$.4f",avgGps[2]).replace(",", ".");
                            String avgGpsOtklonenie0Str=String.format("%1$.4f",avgOtklonenieGps[0]).replace(",", ".");
                            String avgGpsOtklonenie1Str=String.format("%1$.4f",avgOtklonenieGps[1]).replace(",", ".");
                            String avgGpsOtklonenie2Str=String.format("%1$.4f",avgOtklonenieGps[2]).replace(",", ".");
                            textUpLeft.append("\nWGS84: "+gps.get("Lat")+", "+gps.get("Lon")+", "+gps.get("Elev")+", "+gps.get("HDOP")+", "+gps.get("VDOP"));
                            if(avgGps[0]!=0 || avgGps[1]!=0 || avgGps[2]!=0) {
                                textUpLeft.append("\n[WGS84]: "+avgGps0Str+", "+avgGps1Str+", "+avgGps2Str);
                                textUpLeft.append("\n(WGS84): "+avgGpsOtklonenie0Str+", "+avgGpsOtklonenie1Str+", "+avgGpsOtklonenie2Str);
                            }
                            else
                                textUpLeft.append("\n[WGS84]:\n(WGS84)");
                            //

                            //Вычисление utm
                            Map utm=(Map)dataSensors.get("UTM");
                            if(currentUtm+1==countDataSensors){
                                avgUtm[0]=Float.parseFloat(utmX);
                                avgUtm[1]=Float.parseFloat(utmY);
                                avgOtklonenieUtm[0]=sumOtklonenieUtm[0]/countDataSensors;
                                avgOtklonenieUtm[1]=sumOtklonenieUtm[1]/countDataSensors;
                                sumOtklonenieUtm[0]=0;
                                sumOtklonenieUtm[1]=0;
                            }
                            if(avgUtm[0]!=0)
                                sumOtklonenieUtm[0]+=Float.parseFloat(""+utm.get("X"))-avgUtm[0];
                            if(avgUtm[1]!=0)
                                sumOtklonenieUtm[1]+=Float.parseFloat(""+utm.get("Y"))-avgUtm[1];

                            String avgUtm0Str=String.format("%1$.4f",avgUtm[0]).replace(",", ".");
                            String avgUtm1Str=String.format("%1$.4f",avgUtm[1]).replace(",", ".");
                            String avgUtmOtklonenie0Str=String.format("%1$.4f",avgOtklonenieUtm[0]).replace(",", ".");
                            String avgUtmOtklonenie1Str=String.format("%1$.4f",avgOtklonenieUtm[1]).replace(",", ".");
                            if(avgUtm[0]!=0 || avgUtm[1]!=0) {
                                textUpLeft.append("\n[UTM]: " + avgUtm0Str + ", " + avgUtm1Str);
                                textUpLeft.append("\n(UTM): " + avgUtmOtklonenie0Str + ", " + avgUtmOtklonenie1Str);
                            }
                            else
                                textUpLeft.append("\n[UTM]:\n(UTM):");
                            //

                            //Вычисление msk
                            Map msk=(Map)dataSensors.get("MSK");
                            if(currentMsk+1==countDataSensors){
                                avgMsk[0]=Float.parseFloat(mskX);
                                avgMsk[1]=Float.parseFloat(mskY);
                            }
                            String avgMsk0Str=String.format("%1$.4f",avgMsk[0]).replace(",", ".");
                            String avgMsk1Str=String.format("%1$.4f",avgMsk[1]).replace(",", ".");
                            if(!nameLocalSystem.isEmpty() && (avgMsk[0]!=0 || avgMsk[1]!=0))
                                textUpLeft.append("\nМСК: "+nameLocalSystem+", "+avgMsk0Str+", "+avgMsk1Str);
                            else
                                textUpLeft.append("\nМСК:");
                            //
                        }
                        else
                            textUpLeft.append("\nWGS84:\n[WGS84]:\n(WGS84):\n[UTM]:\n(UTM):\nМСК:");

                        if(!nameSession.isEmpty()) {
                            textUpLeft.append("\nСессия: " + numberPicture + ", " + numberPoint + ", " + nameSession);
                            if(numberPoint>0){
                                textUpLeft.append("\nБаза: "+oldXSession+", "+oldYSession);
                                double raznostX = Double.parseDouble(String.format("%1$.4f", newXSession-oldXSession).replace(",", "."));
                                double raznostY = Double.parseDouble(String.format("%1$.4f", newYSession-oldYSession).replace(",", "."));
                                textUpLeft.append("\nСмещение: "+raznostX+", "+raznostY);
                            }
                            else{
                                textUpLeft.append("\nБаза:\nСмещение");
                            }
                        }
                        else
                            textUpLeft.append("\nСессия:\nБаза:\nСмещение:");
                        text1.setText(textUpLeft.toString());

                        //orientation
                        double sumOrient=0;
                        for(int i=0;i<countDataSensors;i++)
                            sumOrient+=arrOrient[i][0];
                        String azimuthStr=""+(sumOrient/countDataSensors);

                        bearing=Float.parseFloat(azimuthStr);
                        bearing=Float.parseFloat(String.format("%1$.1f",bearing).replace(",", "."));

                        curveTextView.azimuthValue=bearing;
                        curveTextView.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        final int bearingI=(int)bearing;

                        //Проекция на север
                        curveTextView.angle=bearingI;
                        curveTextView.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        //
                        TextView azimuthText=findViewById(R.id.azimuthText);
                        azimuthText.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL));

                        double otklonenieAzimuth=bearing-arrOrient[currentOrient][0];
                        String otklonenieAzimuthStr="("+String.format("%1$.2f", Math.abs(otklonenieAzimuth))+")";
                        if(otklonenieAzimuthStr.length()==6)otklonenieAzimuthStr=" "+otklonenieAzimuthStr;

                        azimuthStr=String.format("%1$.1f",bearing);
                        if(azimuthStr.length()==4)azimuthStr=" "+azimuthStr;
                        else if(azimuthStr.length()==3)azimuthStr="  "+azimuthStr;

                        azimuthText.setText(azimuthStr+"\u00B0"+otklonenieAzimuthStr);
                        //
                    }
                });
            }
        },0,50);
        //

        //Восстановление последней выбранной мск
        if(mSettings.contains(APP_PREFERENCES_MSK)){
            String[] mskArr=mSettings.getString(APP_PREFERENCES_MSK,"").split(", ");
            Map msk=new LinkedHashMap();
            msk.put("Name",mskArr[0]);
            msk.put("X",mskArr[1]);
            msk.put("Y",mskArr[2]);
            msk.put("Z",mskArr[3]);
            dataSensors.put("MSK",msk);
            nameLocalSystem=mskArr[0];
            dxLocalSystem=Double.parseDouble(mskArr[1]);
            dyLocalSystem=Double.parseDouble(mskArr[2]);
            dzLocalSystem=Double.parseDouble(mskArr[3]);
            angleLocalSystem=Double.parseDouble(mskArr[4]);
            mskSetting=mskArr[0]+", "+mskArr[1]+", "+mskArr[2]+", "+mskArr[3]+", "+mskArr[4];
        }
        //Восстановление состояния дальномера
        if(mSettings.contains(APP_PREFERENCES_DALNOMER))
            isDalnomer=Boolean.parseBoolean(mSettings.getString(APP_PREFERENCES_DALNOMER,""));

        //Обработка кнопки для просмотра фото
        ImageButton viewPhotosButton=findViewById(R.id.viewPhotosButton);
        viewPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                //
                fillingListFiles();
                //
                intent=new Intent(active,ViewPhotos.class);
                intent.putExtra("listFiles",listFiles);
                intent.putExtra("nameLocalSystem",nameLocalSystem);
                startActivity(intent);
            }
        });
        //Обработка кнопки с настройками
        ImageButton settingsButton=findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //
                fillingListFiles();
                //

                Intent intent=new Intent(MainActivity.this,SettingForm.class);
                intent.putExtra("listFiles",listFiles);
                intent.putExtra("numberPoint",numberPoint);
                intent.putExtra("numberSession",numberSession);
                intent.putExtra("numberObject",numberObject);
                intent.putExtra("stepTrack",stepTrack);
                intent.putExtra("numberTrack",numberTrack);
                intent.putExtra("mskSetting",mskSetting);
                intent.putExtra("isDalnomer",isDalnomer);
                intent.putExtra("heightPicture",heightPicture);
                intent.putExtra("widthPicture",widthPicture);
                intent.putExtra("countDataSensors",countDataSensors);
                startActivityForResult(intent,1);
            }
        });
        //Обработка кнопки с запуском трекера
        final ImageButton startTrackButton=findViewById(R.id.startTrackButton);
        final ImageButton stopTrackButton=findViewById(R.id.stopTrackButton);
        startTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTrackButton.setVisibility(View.INVISIBLE);
                stopTrackButton.setVisibility(View.VISIBLE);
                LayoutInflater li=LayoutInflater.from(active);
                View trackView=li.inflate(R.layout.session,null);
                AlertDialog.Builder trackDialogBuilder=new AlertDialog.Builder(active);
                trackDialogBuilder.setView(trackView);
                TextView tv=trackView.findViewById(R.id.tv);
                tv.setText("Введите название трека: ");
                final EditText trackInput=trackView.findViewById(R.id.input_text);
                //
                if(numberTrack+1<10)trackInput.setText("Трек-0"+(numberTrack+1));
                else trackInput.setText("Трек-"+(numberTrack+1));
                //
                trackDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String nameTrack=""+trackInput.getText();
                        createTrack(nameTrack);
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startTrackButton.setVisibility(View.VISIBLE);
                        stopTrackButton.setVisibility(View.INVISIBLE);
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=trackDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Обработка нажатия кнопки с остановкой трекера
        stopTrackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startTrackButton.setVisibility(View.VISIBLE);
                stopTrackButton.setVisibility(View.INVISIBLE);
                timerTrack.cancel();
                timerTrack = null;
                infoTimer.cancel();
                infoTimer=null;
                TextView trackText=findViewById(R.id.trackText);
                trackText.setText("");
                Toast.makeText(active, "Запись трека " + nameTrack + " остановлена", Toast.LENGTH_LONG).show();
            }
        });
        //Обработка нажатия на активную область
        ImageButton activeAreaButton=findViewById(R.id.activeAreaButton);
        activeAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(active);
                View objectView=li.inflate(R.layout.object,null);
                AlertDialog.Builder objectDialogBuilder=new AlertDialog.Builder(active);
                objectDialogBuilder.setView(objectView);
                final EditText objectInput=objectView.findViewById(R.id.input_text);
                objectDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String nameObject=""+objectInput.getText();
                        boolean objectExist=false;
                        File objectFile=new File(directory.getPath() + "/listObjects.txt");
                        try {
                            FileInputStream fStream=new FileInputStream(objectFile);
                            InputStreamReader oWriter=new InputStreamReader(fStream, "UTF-8");
                            BufferedReader reader=new BufferedReader(oWriter);
                            String s="";
                            while((s=reader.readLine())!=null){
                                if(s.equals(nameObject)){
                                    objectExist=true;
                                    break;
                                }
                            }
                        }
                        catch(Exception e){}

                        if(!objectExist) {
                            try {
                                FileOutputStream fStream = new FileOutputStream(objectFile,true);
                                OutputStreamWriter oWriter = new OutputStreamWriter(fStream, "UTF-8");
                                BufferedWriter writer = new BufferedWriter(oWriter);
                                writer.write(nameObject);
                                writer.newLine();
                                writer.close();
                            } catch (Exception e) {}
                        }
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                //Выбрать объект
                Button selectObjectButton=objectView.findViewById(R.id.selectObjectButton);
                selectObjectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater liObject=LayoutInflater.from(active);
                        View selectObjectView=liObject.inflate(R.layout.sizephoto,null);
                        AlertDialog.Builder selectObjectDialogBuilder=new AlertDialog.Builder(active);
                        selectObjectDialogBuilder.setView(selectObjectView);

                        Spinner listObjects=selectObjectView.findViewById(R.id.input_text);
                        ArrayList<String> objectsArr=getObjects();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(active, android.R.layout.simple_list_item_1, objectsArr);
                        listObjects.setAdapter(adapter);

                        TextView textView=selectObjectView.findViewById(R.id.tv);
                        textView.setText("Выберите объект: ");

                        listObjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                String textObject=((TextView)view).getText().toString();
                                objectInput.setText(textObject);
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });

                        selectObjectDialogBuilder.setCancelable(false).setNegativeButton("Выбрать", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog=selectObjectDialogBuilder.create();
                        alertDialog.show();
                    }
                });
                //

                AlertDialog alertDialog=objectDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Создание точки
        ImageButton createPointButton=findViewById(R.id.createPointButton);
        createPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(active);
                View sessionView=li.inflate(R.layout.session,null);
                AlertDialog.Builder pointDialogBuilder=new AlertDialog.Builder(active);
                pointDialogBuilder.setView(sessionView);
                TextView tv=sessionView.findViewById(R.id.tv);
                tv.setText("Введите название точки: ");
                final EditText pointInput=sessionView.findViewById(R.id.input_text);
                //
                if(numberPoint+1<10)pointInput.setText("Точка-0"+(numberPoint+1));
                else pointInput.setText("Точка-"+(numberPoint+1));
                //
                pointDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String namePoint=""+pointInput.getText();
                        createPoint(namePoint);
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=pointDialogBuilder.create();
                alertDialog.show();
            }
        });
        //
    }

    public ArrayList<String> getObjects(){
        ArrayList<String> objectsArr=new ArrayList<String>();
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Data sensors");
        File listObjectsFile = new File(directory.getPath() + "/listObjects.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(listObjectsFile));
            String s="";
            while((s=reader.readLine())!=null)
                objectsArr.add(s);
        }
        catch (IOException e) { }
        return objectsArr;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(this.checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED) {
                if(countCameraPermission==0){
                    askCameraPermission();
                    countCameraPermission++;
                }
            }
            else
                camera = Camera.open(CAMERA_ID);

            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if(countLocationPermission==0){
                    askLocatePermission();
                    countLocationPermission++;
                }
            }
            if(this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if(countFineLocationCount==0){
                    askFineLocatePermission();
                    countFineLocationCount++;
                }
            }
        }
        else camera = Camera.open(CAMERA_ID);

        List<Sensor> listSensor=sensorManager.getSensorList(Sensor.TYPE_ALL);
        hasSensors=new boolean[]{false,false,false,false,false};
        for(int i=0;i<listSensor.size();i++)
            if(listSensor.get(i).getType()==Sensor.TYPE_ACCELEROMETER)
                hasSensors[0]=true;
            else if(listSensor.get(i).getType()==Sensor.TYPE_MAGNETIC_FIELD)
                hasSensors[1]=true;
            else if(listSensor.get(i).getType()==Sensor.TYPE_AMBIENT_TEMPERATURE)
                hasSensors[2]=true;
            else if(listSensor.get(i).getType()==Sensor.TYPE_PRESSURE)
                hasSensors[3]=true;
            else if(listSensor.get(i).getType()==Sensor.TYPE_LIGHT)
                hasSensors[4]=true;
        if(hasSensors[0])sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_UI);
        if(hasSensors[1])sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_UI);
        if(hasSensors[2])sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),SensorManager.SENSOR_DELAY_UI);
        if(hasSensors[3])sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_UI);
        if(hasSensors[4])sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_UI);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 0, 10, locationListener);
            checkEnabled();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)
            camera.release();
        camera = null;
        locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onStop() {
        // unregister listener
        sensorManager.unregisterListener(sensorListener);
        super.onStop();
    }

    class HolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                if(camera!=null) {
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            if(camera!=null) {
                camera.stopPreview();
                setCameraDisplayOrientation(CAMERA_ID);
                try {
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

    }

    void setCameraDisplayOrientation(int cameraId) {
        // определяем насколько повернут экран от нормального положения
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        result=0;

        // получаем инфо по камере cameraId
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        // задняя камера
        if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
            result = ((360 - degrees) + info.orientation);
        } else
            // передняя камера
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                result = ((360 - degrees) - info.orientation);
                result += 360;
            }
        result = result % 360;

        camera.setDisplayOrientation(result);
    }
}
