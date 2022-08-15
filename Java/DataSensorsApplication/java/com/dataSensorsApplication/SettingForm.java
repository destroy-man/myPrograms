import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SettingForm extends AppCompatActivity {
    public SettingForm active;
    int widthPicture=0;
    int heightPicture=0;

    Map<String,String> dataMap=new LinkedHashMap<String,String>();

    public String[] getSizesPhoto(){
        Camera camera=Camera.open(0);
        Camera.Size s=camera.getParameters().getPictureSize();
        int height=s.height;
        int width=s.width;
        String size1=height+"x"+width;
        String size2=(int)(height*0.75)+"x"+(int)(width*0.75);
        String size3=(int)(height*0.5)+"x"+(int)(width*0.5);
        String[] sizes={size1,size2,size3};
        return sizes;
    }

    public ArrayList<String> getMsk(){
        ArrayList<String> mskArr=new ArrayList<String>();
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Data sensors");
        File mskFile = new File(directory.getPath() + "/MSK.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mskFile));
            String s="";
            while((s=reader.readLine())!=null)
                mskArr.add(s);
        }
        catch (IOException e) { }
        return mskArr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_menu);
        active=this;
        //Дальномер
        CheckBox dalnomerCheckbox=findViewById(R.id.dalnomerCheckbox);

        Intent intent=getIntent();
        boolean isDalnomer=intent.getBooleanExtra("isDalnomer",false);
        dalnomerCheckbox.setChecked(isDalnomer);

        dalnomerCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent=new Intent();
                dataMap.put("dalnomer",""+isChecked);
                intent.putExtra("data", (Serializable)dataMap);
                setResult(RESULT_OK,intent);
            }
        });
        //Размер фото
        Button sizePhotoButton=findViewById(R.id.sizePhotoButton);
        sizePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(active);
                View sizephotoView=li.inflate(R.layout.sizephoto,null);
                AlertDialog.Builder sizephotoDialogBuilder=new AlertDialog.Builder(active);
                sizephotoDialogBuilder.setView(sizephotoView);
                Spinner listSizes=sizephotoView.findViewById(R.id.input_text);
                String[] sizes=getSizesPhoto();

                Intent intent=getIntent();
                heightPicture=intent.getIntExtra("heightPicture",0);
                widthPicture=intent.getIntExtra("widthPicture",0);
                final int oldHeightPicture=heightPicture;
                final int oldWidthPicture=widthPicture;

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(active, android.R.layout.simple_list_item_1, sizes);
                listSizes.setAdapter(adapter);

                for(int i=0;i<sizes.length;i++){
                    if(sizes[i].split("x")[0].equals(""+heightPicture) && sizes[i].split("x")[1].equals(""+widthPicture))
                        listSizes.setSelection(i);
                }

                listSizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        String text=((TextView)view).getText().toString();
                        heightPicture=Integer.parseInt(text.split("x")[0]);
                        widthPicture=Integer.parseInt(text.split("x")[1]);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

                sizephotoDialogBuilder.setCancelable(false).setPositiveButton("Выбрать", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent();
                        dataMap.put("sizePhoto",heightPicture+" "+widthPicture);
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        heightPicture=oldHeightPicture;
                        widthPicture=oldWidthPicture;
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=sizephotoDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Обозначение устройства
        Button nameDeviceButton=findViewById(R.id.nameDeviceButton);
        nameDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(active);
                View sessionView=li.inflate(R.layout.session,null);
                AlertDialog.Builder deviceDialogBuilder=new AlertDialog.Builder(active);
                deviceDialogBuilder.setView(sessionView);
                TextView tv=sessionView.findViewById(R.id.tv);
                tv.setText("Введите обозначение устройства: ");
                final EditText deviceInput=sessionView.findViewById(R.id.input_text);
                deviceDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String nameDevice=""+deviceInput.getText();
                        Intent intent=new Intent();
                        dataMap.put("nameDevice",nameDevice);
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=deviceDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Местная система
        Button localSystemButton=findViewById(R.id.localSystemButton);
        localSystemButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LayoutInflater li=LayoutInflater.from(active);
                View localSystemView=li.inflate(R.layout.localsystem,null);
                AlertDialog.Builder localSystemDialogBuilder=new AlertDialog.Builder(active);
                localSystemDialogBuilder.setView(localSystemView);
                final EditText nameInput=localSystemView.findViewById(R.id.nameInput);
                final EditText dxInput=localSystemView.findViewById(R.id.dxInput);
                final EditText dyInput=localSystemView.findViewById(R.id.dyInput);
                final EditText dzInput=localSystemView.findViewById(R.id.dzInput);
                final EditText angleInput=localSystemView.findViewById(R.id.angleInput);

                //
                Intent intent=getIntent();
                if(intent.getStringExtra("mskSetting")!=null) {
                    String[] mskSettingArr = intent.getStringExtra("mskSetting").split(", ");
                    nameInput.setText(mskSettingArr[0]);
                    dxInput.setText(mskSettingArr[1]);
                    dyInput.setText(mskSettingArr[2]);
                    dzInput.setText(mskSettingArr[3]);
                    angleInput.setText(mskSettingArr[4]);
                }
                //

                //Выбрать местную систему
                Button selectMskButton=localSystemView.findViewById(R.id.selectMskButton);
                selectMskButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater liMsk=LayoutInflater.from(active);
                        View mskView=liMsk.inflate(R.layout.sizephoto,null);
                        AlertDialog.Builder mskDialogBuilder=new AlertDialog.Builder(active);
                        mskDialogBuilder.setView(mskView);

                        Spinner listMsk=mskView.findViewById(R.id.input_text);
                        ArrayList<String> mskArr=getMsk();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(active, android.R.layout.simple_list_item_1, mskArr);
                        listMsk.setAdapter(adapter);

                        TextView textView=mskView.findViewById(R.id.tv);
                        textView.setText("Выберите местную систему координат (МСК): ");

                        listMsk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                String textMsk=((TextView)view).getText().toString();
                                String[] textMskArr=textMsk.split(", ");
                                nameInput.setText(textMskArr[0]);
                                dxInput.setText(textMskArr[1]);
                                dyInput.setText(textMskArr[2]);
                                dzInput.setText(textMskArr[3]);
                                angleInput.setText(textMskArr[4]);
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });

                        mskDialogBuilder.setCancelable(false).setNegativeButton("Выбрать", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog=mskDialogBuilder.create();
                        alertDialog.show();
                    }
                });
                //Сохранить местную систему координат
                Button saveMskButton=localSystemView.findViewById(R.id.saveMskButton);
                saveMskButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Data sensors");
                        File mskFile = new File(directory.getPath() + "/MSK.txt");
                        try {
                            FileOutputStream fStream=new FileOutputStream(mskFile, true);
                            OutputStreamWriter oWriter=new OutputStreamWriter(fStream, "UTF-8");
                            BufferedWriter writer=new BufferedWriter(oWriter);
                            writer.newLine();
                            writer.append(nameInput.getText()+", "+dxInput.getText()+", "+dyInput.getText()
                                         +", "+dzInput.getText()+", "+angleInput.getText());
                            writer.close();
                        }
                        catch (IOException e) { }
                    }
                });
                //

                localSystemDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        Intent intent=new Intent();
                        dataMap.put("localSystem",nameInput.getText()+" "+dxInput.getText()+" "+dyInput.getText()+" "
                                +dzInput.getText()+" "+angleInput.getText());
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=localSystemDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Шаг трека
        Button stepTrackButton=findViewById(R.id.stepTrackButton);
        stepTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(active);
                View stepTrackView=li.inflate(R.layout.session,null);
                AlertDialog.Builder stepTrackDialogBuilder=new AlertDialog.Builder(active);
                stepTrackDialogBuilder.setView(stepTrackView);
                TextView tv=stepTrackView.findViewById(R.id.tv);
                tv.setText("Укажите шаг трека: ");
                final EditText stepTrackInput=stepTrackView.findViewById(R.id.input_text);
                //
                Intent intent=getIntent();
                final int stepTrack=intent.getIntExtra("stepTrack", 0);
                stepTrackInput.setText(""+stepTrack);
                //
                stepTrackDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        int step=Integer.parseInt(""+stepTrackInput.getText());
                        Intent intent=new Intent();
                        dataMap.put("stepTrack",""+step);
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=stepTrackDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Просмотр
        Button viewPhotosButton=findViewById(R.id.viewPhotosButton);
        viewPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                ArrayList<String> listFiles=intent.getStringArrayListExtra("listFiles");
                String nameLocalSystem="";
                if(intent.getStringExtra("mskSetting")!=null) {
                    String[] mskSettingArr = intent.getStringExtra("mskSetting").split(", ");
                    nameLocalSystem=mskSettingArr[0];
                }
                intent=new Intent(SettingForm.this,ViewPhotos.class);
                intent.putExtra("listFiles",listFiles);
                intent.putExtra("nameLocalSystem",nameLocalSystem);
                startActivity(intent);
            }
        });
        //Точка
        Button pointButton=findViewById(R.id.pointButton);
        pointButton.setOnClickListener(new View.OnClickListener() {
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
                Intent intent=getIntent();
                int numberPoint=intent.getIntExtra("numberPoint",0)+1;
                if(numberPoint<10)pointInput.setText("Точка-0"+numberPoint);
                else pointInput.setText("Точка-"+numberPoint);
                //
                pointDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String namePoint=""+pointInput.getText();
                        Intent intent=new Intent();
                        dataMap.put("point",namePoint);
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
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
        //Сессия
        Button sessionButton=findViewById(R.id.sessionButton);
        sessionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LayoutInflater li=LayoutInflater.from(active);
                View sessionView=li.inflate(R.layout.session,null);
                AlertDialog.Builder sessionDialogBuilder=new AlertDialog.Builder(active);
                sessionDialogBuilder.setView(sessionView);
                TextView tv=sessionView.findViewById(R.id.tv);
                tv.setText("Введите название сессии: ");
                final EditText sessionInput=sessionView.findViewById(R.id.input_text);
                //
                Intent intent=getIntent();
                int numberSession=intent.getIntExtra("numberSession",0)+1;
                if(numberSession<10)sessionInput.setText("Сессия-0"+numberSession);
                else sessionInput.setText("Сессия-"+numberSession);
                //
                sessionDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String nameSession=""+sessionInput.getText();
                        Intent intent=new Intent();
                        dataMap.put("session",nameSession);
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=sessionDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Объект
        Button objectButton=findViewById(R.id.objectButton);
        objectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(active);
                View objectView=li.inflate(R.layout.session,null);
                AlertDialog.Builder objectDialogBuilder=new AlertDialog.Builder(active);
                objectDialogBuilder.setView(objectView);
                TextView tv=objectView.findViewById(R.id.tv);
                tv.setText("Введите название объекта: ");
                final EditText objectInput=objectView.findViewById(R.id.input_text);
                //
                Intent intent=getIntent();
                int numberObject=intent.getIntExtra("numberObject",0)+1;
                if(numberObject<10)objectInput.setText("Объект-0"+numberObject);
                else objectInput.setText("Объект-"+numberObject);
                //
                objectDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String nameObject=""+objectInput.getText();
                        Intent intent=new Intent();
                        dataMap.put("object",nameObject);
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=objectDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Трек
        Button trackButton=findViewById(R.id.startTrackButton);
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(active);
                View trackView=li.inflate(R.layout.session,null);
                AlertDialog.Builder trackDialogBuilder=new AlertDialog.Builder(active);
                trackDialogBuilder.setView(trackView);
                TextView tv=trackView.findViewById(R.id.tv);
                tv.setText("Введите название трека: ");
                final EditText trackInput=trackView.findViewById(R.id.input_text);
                //
                Intent intent=getIntent();
                int numberTrack=intent.getIntExtra("numberTrack",0)+1;
                if(numberTrack<10)trackInput.setText("Трек-0"+numberTrack);
                else trackInput.setText("Трек-"+numberTrack);
                //
                trackDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String nameTrack=""+trackInput.getText();
                        Intent intent=new Intent();
                        dataMap.put("track",nameTrack);
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=trackDialogBuilder.create();
                alertDialog.show();
            }
        });
        //Усреднение
        Button countDataSensorsButton=findViewById(R.id.countDataSensorsButton);
        countDataSensorsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LayoutInflater li=LayoutInflater.from(active);
                View countDSView=li.inflate(R.layout.session,null);
                AlertDialog.Builder countDSDialogBuilder=new AlertDialog.Builder(active);
                countDSDialogBuilder.setView(countDSView);
                TextView tv=countDSView.findViewById(R.id.tv);
                tv.setText("Введите количество точек для усреднения показаний датчиков: ");
                final EditText countDSInput=countDSView.findViewById(R.id.input_text);
                countDSInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                //
                Intent intent=getIntent();
                int countDataSensors=intent.getIntExtra("countDataSensors",0);
                countDSInput.setText(""+countDataSensors);
                //
                countDSDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String countDataSensors=""+countDSInput.getText();
                        Intent intent=new Intent();
                        dataMap.put("countDataSensors",countDataSensors);
                        intent.putExtra("data", (Serializable)dataMap);
                        setResult(RESULT_OK,intent);
                        active.finish();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=countDSDialogBuilder.create();
                alertDialog.show();
            }
        });
        //
    }
}
