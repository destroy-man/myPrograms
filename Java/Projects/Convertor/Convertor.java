import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;
import org.json.simple.*;

public class Convertor {

//    static{
//        //System.load("C:\\Users\\user\\Documents\\Visual Studio 2017\\Projects\\CheckDate\\x64\\Release\\CheckDate.dll");
//        System.loadLibrary("CheckDate");
//    }
    //нативный метод
    static String pathDll;
    public native boolean checkDate(); 
    //
    
    static int countObjects;
    static String time;
    static String nameKE;
    
    static int countPoint;
    static int countPline;
    static int countBar;
    
    public static String sli2bsm(ArrayList<String> arrSli,String[] args,String name,long startTime){
        int x=0,y=0,z=0,ux=0,uy=0,uz=0; //параметры степени свободы       
        int pointNum=0; //количество узлов
        int supNum=0; //количество закреплений
        int barNum=0; //количество стержней
        int shellNum=0; //количество оболочек
        int matNum=0; //количество материалов
        int nloadNum=0; //количество нагрузок на узел
        int lnloadNum=0; //количество равномерно распределенных нагрузок на элемент
        int ltloadNum=0; //количество неравномерно распределенных нагрузок на элемент
        int maxLoad=0;
        ArrayList<String> arrPoint=new ArrayList<String>();
        ArrayList<String> arrNhsup=new ArrayList<String>();
        ArrayList<String> arrBar=new ArrayList<String>();
        ArrayList<String> arrShell=new ArrayList<String>();
        ArrayList<String> arrMat=new ArrayList<String>();
        ArrayList<String> arrNload=new ArrayList<String>();
        ArrayList<String> arrLnload=new ArrayList<String>();
        ArrayList<String> arrLtload=new ArrayList<String>();
        ArrayList<String> arrPline=new ArrayList<String>();
        ArrayList<String> arrLoop=new ArrayList<String>();
        ArrayList<String> arrPsurf=new ArrayList<String>();
        //Сообщение 3
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\002-M.txt"))){
            GregorianCalendar cal3=new GregorianCalendar();
            String hour3=""+cal3.get(Calendar.HOUR_OF_DAY);
            String minute3=""+cal3.get(Calendar.MINUTE);
            String second3=""+cal3.get(Calendar.SECOND);
            if(hour3.length()==1)hour3="0"+hour3;
            if(minute3.length()==1)minute3="0"+minute3;
            if(second3.length()==1)second3="0"+second3;
            writer.write("3: "+hour3+":"+minute3+":"+second3+" окончание обработки данных");
        }
        catch(IOException ex){}
        //
        for(int i=0;i<arrSli.size();i++){
            //Степень свободы
            if(arrSli.get(i).contains("DegreesOfFreedom ")){
                String[] s=arrSli.get(i).trim().split(" ");
                x=Integer.parseInt(s[1].replaceAll("\"","").split("=")[1]);
                y=Integer.parseInt(s[2].replaceAll("\"","").split("=")[1]);
                z=Integer.parseInt(s[3].replaceAll("\"","").split("=")[1]);
                ux=Integer.parseInt(s[4].replaceAll("\"","").split("=")[1]);
                uy=Integer.parseInt(s[5].replaceAll("\"","").split("=")[1]);
                uz=Integer.parseInt(s[6].replaceAll("\"","").split("=")[1]);
            }
            //Узлы (POINT)
            if(arrSli.get(i).contains("NodesCoordArray ")){
                int count=Integer.parseInt(arrSli.get(i).split("\"")[1]);
                for(int j=i+1;j<i+count+1;j++){
                    pointNum++;
                    String[] s=arrSli.get(j).split("\"");
                    arrPoint.add(pointNum+" "+s[1]+" "+s[3]+" "+s[5]);
                }
            }
            //Закрепления (NHSUP)
            if(arrSli.get(i).contains("RestrictionsArray ")){
                int count=Integer.parseInt(arrSli.get(i).split("\"")[1]);
                for(int j=i+1;j<i+count+1;j++){
                    String[] s=arrSli.get(j).split("\"");
                    int[] dof={x,y,z,ux,uy,uz};
                    if(s[3].equals("1"))dof[0]=1;
                    if(s[3].equals("2"))dof[1]=1;
                    if(s[3].equals("3"))dof[2]=-1;
                    if(s[3].equals("4"))dof[3]=1;
                    if(s[3].equals("5"))dof[4]=1;
                    if(s[3].equals("6"))dof[5]=1;
                    arrNhsup.add(supNum+" "+s[1]+" "+dof[0]+" "+dof[1]+" "+dof[2]+" "+dof[3]+" "+dof[4]+" "+dof[5]);
                    supNum++;
                }
            }
            if(arrSli.get(i).contains("ElementsArray ")){
                int countEnd=Integer.parseInt(arrSli.get(i).split("\"")[1]);
                int countStart=0;
                for(int j=i+1;countStart<countEnd*2;j++){
                    if(arrSli.get(j).contains("/Element"))
                        continue;
                    //Линии (PLINE)
                    else if(arrSli.get(j).contains("Nodes ")){
                        String[] s=arrSli.get(j).split("\"");
                        String pline=""+(arrPline.size()+1)+" ";
                        for(int k=1;k<s.length-1;k+=2){
                            if(k!=s.length-2)pline+=s[k]+" ";
                            else pline+=s[k];
                        }
                        arrPline.add(pline);
                        if(arrSli.get(j-1).split("\"")[1].equals("2")){
                            arrLoop.add((arrLoop.size()+1)+" 1 "+arrPline.size());
                            arrPsurf.add((arrPsurf.size()+1)+" "+arrLoop.size());
                        }
                        countStart++;
                    }
                    //Стержни (BAR)
                    else if(arrSli.get(j).split("\"")[1].equals("1")){
                        barNum++;
                        String numberMat=arrSli.get(j).split("\"")[3];
                        arrBar.add(barNum+" 1 "+numberMat+" 0 1 "+(arrBar.size()+1));
                        countStart++;
                    }
                    //Оболочки (SHELL)
                    else if(arrSli.get(j).split("\"")[1].equals("2")){
                        shellNum++;
                        String numberMat=arrSli.get(j).split("\"")[3];
                        arrShell.add(shellNum+" 1 "+numberMat+" 0 "+arrShell.size()+1);
                    }
                }
            }
            //Материалы (MAT)
            if(arrSli.get(i).contains("MaterialsArray ")){
                int countEnd=Integer.parseInt(arrSli.get(i).split("\"")[1]);
                int countStart=0;
                for(int j=i+1;countStart<countEnd;j++){
                    if(arrSli.get(j).contains("SectGeom") || arrSli.get(j).contains("/Material"))
                        continue;
                    else{
                        String[] s=arrSli.get(j).split("\"");
                        String mat=""+matNum+" "+matNum+" {";
                        for(int k=1;k<s.length-1;k+=2){
                            if(k==1){
                                String kind=s[0].split("<")[1].split("=")[0].split(" ")[1];
                                mat+=kind+"="+s[k]+" ";
                            }
                            else{
                                String kind=s[k-1].split("=")[0];
                                if(k!=s.length-2)mat+=kind+"="+s[k]+" ";
                                else mat+=kind+"="+s[k]+"}";
                            }
                        }
                        arrMat.add(mat);
                        matNum++;
                        countStart++;
                    }
                }
            }
            if(arrSli.get(i).contains("NodesLoadingArray ")){
                int count=Integer.parseInt(arrSli.get(i).split("\"")[1]);
                for(int j=i+1;j<i+count+1;j++){
                    String[] s=arrSli.get(j).split("\"");
                    //Нагрузки на узел (NLOAD)
                    if(s[5].equals("0")){
                        String numNode=s[1];
                        int ndof=Integer.parseInt(s[3]);
                        String loadNumber=s[7];
                        if(Integer.parseInt(loadNumber)>maxLoad)
                            maxLoad=Integer.parseInt(loadNumber);
                        double loadValue=Double.parseDouble(s[9]);
                        if(ndof==1)arrNload.add(nloadNum+" "+loadNumber+" "+numNode+" "+loadValue+" 0 0 0");
                        if(ndof==2)arrNload.add(nloadNum+" "+loadNumber+" "+numNode+" 0 "+loadValue+" 0 0");
                        if(ndof==3)arrNload.add(nloadNum+" "+loadNumber+" "+numNode+" 0 0 "+loadValue+" 0");
                        nloadNum++;
                    }
                }
            }
            if(arrSli.get(i).contains("ElemLoadingArray ")){
                int count=Integer.parseInt(arrSli.get(i).split("\"")[1]);
                for(int j=i+1;j<i+count+1;j++){
                    String[] s=arrSli.get(j).split("\"");
                    //Равномерно распределенные нагрузки на элемент (LNLOAD)
                    if(s[5].equals("3")){
                        String numElem=s[1];
                        String loadNumber=s[3];
                        int ndof=Integer.parseInt(s[9]);
                        double loadValue=Double.parseDouble(s[11]);
                        if(ndof==1)arrLnload.add(lnloadNum+" "+loadNumber+" "+numElem+" "+loadValue+" 0 0 0");
                        if(ndof==2)arrLnload.add(lnloadNum+" "+loadNumber+" "+numElem+" 0 "+loadValue+" 0 0");
                        if(ndof==3)arrLnload.add(lnloadNum+" "+loadNumber+" "+numElem+" 0 0 "+loadValue+" 0");
                        lnloadNum++;
                    }
                    //Неравномерно распределенные нагрузки на элемент (LTLOAD)
                    else if(s[5].equals("7")){
                        String numElem=s[1];
                        String loadNumber=s[3];
                        int ndof=Integer.parseInt(s[9]);
                        double loadValue=Double.parseDouble(s[11]);
                        double loadValueX=0.0, loadValueY=0.0, loadValueZ=0.0;
                        if(s.length>=14)loadValueX=Double.parseDouble(s[13]);
                        if(s.length>=16)loadValueY=Double.parseDouble(s[15]);
                        if(s.length>=17)loadValueZ=Double.parseDouble(s[17]);
                        if(ndof==1)
                            arrLtload.add(ltloadNum+" "+loadNumber+" "+numElem+" "+loadValue+" 0 0 "+loadValueX*9806.65+" 0 0 0");
                        if(ndof==2)
                            arrLtload.add(ltloadNum+" "+loadNumber+" "+numElem+" 0 "+loadValue+" 0 0 "+loadValueY+" 0 0");
                        if(ndof==3)
                            arrLtload.add(ltloadNum+" "+loadNumber+" "+numElem+" 0 0 "+loadValue+" 0 0 "+loadValueZ+" 0");
                        ltloadNum++;
                    }
                }
            }
        }
        //Сообщение 4
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\003-M.txt"))){
            GregorianCalendar cal4=new GregorianCalendar();
            String hour4=""+cal4.get(Calendar.HOUR_OF_DAY);
            String minute4=""+cal4.get(Calendar.MINUTE);
            String second4=""+cal4.get(Calendar.SECOND);
            if(hour4.length()==1)hour4="0"+hour4;
            if(minute4.length()==1)minute4="0"+minute4;
            if(second4.length()==1)second4="0"+second4;
            writer.write("4: "+hour4+":"+minute4+":"+second4+" преобразование данных");
        }
        catch(IOException ex){}
        //
        
        //Подготовка данных для записи в файл
        String strOut="///Материалы ("+arrMat.size()+")\n";
        strOut+="@MAT\n";
        for(String mat:arrMat)
            strOut+=mat+"\n";
        strOut+="&\n\n";
        
        strOut+="///Поперечные сечения элемента (1)\n";
        strOut+="@ELSEC\n1\n&\n\n";
        
        strOut+="///Точки ("+arrPoint.size()+")\n";
        strOut+="@POINT\n";
        for(String point:arrPoint)
            strOut+=point+"\n";
        strOut+="&\n\n";
        
        strOut+="///Полилинии ("+arrPline.size()+")\n";
        strOut+="@PLINE\n";
        for(String pline:arrPline)
            strOut+=pline+"\n";
        strOut+="&\n\n";
        
        if(arrLoop.size()>0){
            strOut+="///Замкнутые линии ("+arrLoop.size()+")\n";
            strOut+="@LINELOOP\n";
            for(String lineloop:arrLoop)
                strOut+=lineloop+"\n";
            strOut+="&\n\n";
        }
        
        if(arrPsurf.size()>0){
            strOut+="///Плоские элементы поверхностей ("+arrPsurf.size()+")\n";
            strOut+="@PSURF\n";
            for(String psurf:arrPsurf)
                strOut+=psurf+"\n";
            strOut+="&\n\n";
        }
        
        if(arrShell.size()>0){
            strOut+="///Оболочки ("+arrShell.size()+")\n";
            strOut+="@SHELL\n";
            for(String shell:arrShell)
                strOut+=shell+"\n";
            strOut+="&\n\n";
        }
        
        if(arrBar.size()>0){
            strOut+="///Стержни ("+arrBar.size()+")\n";
            strOut+="@BAR\n";
            for(String bar:arrBar)
                strOut+=bar+"\n";
            strOut+="&\n\n";
        }
        
        strOut+="///Масштаб отображения нагрузок (1)\n";
        strOut+="@LSCALE\n20000 1\n&\n\n";
        
        if(maxLoad>0){
            strOut+="///Отображения и названия загружений ("+maxLoad+")\n";
            strOut+="@LCASENAME\n";
            for(int i=1;i<=maxLoad;i++)
                strOut+=i+" 0\n";
            strOut+="&\n\n";
        }
        
        if(arrNload.size()>0){
            strOut+="///Узловые нагрузки ("+arrNload.size()+")\n";
            strOut+="@NLOAD\n";
            for(String nload:arrNload)
                strOut+=nload+"\n";
            strOut+="&\n\n";
        }
        
        if(arrLnload.size()>0){
            strOut+="///Линейные равномерные нагрузки на стержневой элемент ("+arrLnload.size()+")\n";
            strOut+="@LNLOAD\n";
            for(String lnload:arrLnload)
                strOut+=lnload+"\n";
            strOut+="&\n\n";
        }
        
        if(arrLtload.size()>0){
            strOut+="///Линейные неравномерные (трапециевидные) нагрузки на элемент ("+arrLtload.size()+")\n";
            strOut+="@LTLOAD\n";
            for(String ltload:arrLtload)
                strOut+=ltload+"\n";
            strOut+="&\n\n";
        }
        
        if(arrNhsup.size()>0){
            strOut+="///Узловые опоры ("+arrNhsup.size()+")\n";
            strOut+="@NHSUP\n";
            for(String nhsup:arrNhsup)
                strOut+=nhsup+"\n";
            strOut+="&\n\n";
        }
        
        //Сообщение 5
        GregorianCalendar cal5=new GregorianCalendar();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\004-M.txt"))){
            String hour5=""+cal5.get(Calendar.HOUR_OF_DAY);
            String minute5=""+cal5.get(Calendar.MINUTE);
            String second5=""+cal5.get(Calendar.SECOND);
            if(hour5.length()==1)hour5="0"+hour5;
            if(minute5.length()==1)minute5="0"+minute5;
            if(second5.length()==1)second5="0"+second5;
            writer.write("5: "+hour5+":"+minute5+":"+second5+" запись в файл "+args[1]+"\\"+name);
        }
        catch(IOException ex){}
        //
        
        //
        long timeSpent=System.currentTimeMillis()-startTime;
        int hours=(int)(timeSpent/(60*60*1000));
        timeSpent-=(hours*60*60*1000);
        int minutes =(int)(timeSpent/(60 * 1000));
        timeSpent-=(minutes*60*1000);
        int seconds =(int)(timeSpent/(1000));
        time=hours+":"+minutes+":"+seconds;
        
        String strOut1=strOut;
        strOut="/// --------------------------------------------\n";
        strOut+="/// Преобразование SLI в BSMT.\n";
        strOut+="/// Исходный файл: "+args[0]+".\n";
        strOut+="/// Выходной файл: "+args[1]+"\\"+name+".\n";
        strOut+="/// Дата конвертации: "+cal5.get(Calendar.DAY_OF_MONTH)+"."
                    +(cal5.get(Calendar.MONTH)+1)+"."+cal5.get(Calendar.YEAR)+".\n";
        strOut+="/// Время конвертации: "+time+".\n";
        strOut+="/// ---------------------------------------------\n\n";
        
        strOut+="/// MAT="+arrMat.size()+" ELSEC=1 POINT="+arrPoint.size()+" PLINE="+arrPline.size();
        if(arrLoop.size()>0)strOut+=" LINELOOP="+arrLoop.size();
        if(arrPsurf.size()>0)strOut+=" PSURF="+arrPsurf.size();
        if(arrShell.size()>0)strOut+=" SHELL="+arrShell.size();
        if(arrBar.size()>0)strOut+=" BAR="+arrBar.size();
        strOut+=" LSCALE=1";
        if(maxLoad>0)strOut+=" LCASENAME="+maxLoad;
        if(arrNload.size()>0)strOut+=" NLOAD="+arrNload.size();
        if(arrLnload.size()>0)strOut+=" LNLOAD="+arrLnload.size();
        if(arrLtload.size()>0)strOut+=" LTLOAD="+arrLtload.size();
        if(arrNhsup.size()>0)strOut+=" NHSUP="+arrNhsup.size();
        strOut+="\n\n\n";
        strOut+=strOut1;
        //
        countObjects=arrMat.size()+arrPoint.size()+arrPline.size()+arrLoop.size()
                +arrPsurf.size()+arrShell.size()+arrBar.size()+1+maxLoad+arrNload.size()
                +arrLnload.size()+arrLtload.size()+arrNhsup.size();
        return strOut;
    }
    
    public static String lira2bsm(ArrayList<String> arrFile,String[] args,String name,long startTime){        
        ArrayList<Object>Nodes_Lira=new ArrayList<Object>();
        ArrayList<Object>Elems_Lira=new ArrayList<Object>();
        ArrayList<Object>Mats_Lira=new ArrayList<Object>();
        int matnum=0;
        int elsecnum=0;
        ArrayList<Object>ElSec_Lira=new ArrayList<Object>();
        ArrayList<Object>Hinges_Lira=new ArrayList<Object>();
        ArrayList<Object>Supports_Lira=new ArrayList<Object>();
        ArrayList<Object>Node_Loads_Lira=new ArrayList<Object>();
        ArrayList<Object>Elem_Loads_Lira=new ArrayList<Object>();
        ArrayList<Object>Load_Values_Lira=new ArrayList<Object>();
        ArrayList<Object>Load_Cases_Lira=new ArrayList<Object>();
        String title="";
        int nn=1,en=1;
        
        String line="";
        String scheme="";
        
        ArrayList<String> file=new ArrayList<String>();
        for(String s:arrFile){
            if(!s.isEmpty() && s.substring(0,1).equals(" "))
                s=s.substring(1);
            if(s.contains("\t"))
                s=s.replaceAll("\t"," ");
            file.add(s+"\n");
        }
        
        //Сообщение 3
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\002-M.txt"))){
            GregorianCalendar cal3=new GregorianCalendar();
            String hour3=""+cal3.get(Calendar.HOUR_OF_DAY);
            String minute3=""+cal3.get(Calendar.MINUTE);
            String second3=""+cal3.get(Calendar.SECOND);
            if(hour3.length()==1)hour3="0"+hour3;
            if(minute3.length()==1)minute3="0"+minute3;
            if(second3.length()==1)second3="0"+second3;
            writer.write("3: "+hour3+":"+minute3+":"+second3+" окончание обработки данных");
        }
        catch(IOException ex){}
        //
        
        for(int i=0;i<file.size();){
            if(file.get(i).contains("Titles")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    if(line.startsWith("1;"))title="";
                    if(line.startsWith("2;"))
                        scheme=line.split(";")[1].replace("/","");
                    if(line.contains("39;")){
                        line=file.get(++i);
                        while(!line.contains("/")){
                            String[] loadcase=line.split(":");
                            HashMap<String,String> loadCasesLira=new HashMap<String,String>();
                            String key="N";
                            String value=loadcase[0].substring(0,loadcase[0].length()-1);
                            loadCasesLira.put(key,value);
                            key="LCName";
                            value=loadcase[1].substring(0,loadcase[1].length()-2);
                            loadCasesLira.put(key,value);
                            Load_Cases_Lira.add(loadCasesLira);
                            line=file.get(++i);
                        }
                    }
                    line=file.get(++i);
                }
            }
            
            if(file.get(i).contains("Rigid")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    if(line.equals("/") || line.equals("\n")){
                        line=file.get(++i);
                        if(line.contains(")\n"))break;
                    }
                    if(line.length()>=1){
                        String[] Line_s=line.split(" ");
                        //Изотропный материал
                        if(Line_s[0].equals("1") && Line_s[1].equals("1")){
                            //Удельный вес-кг/м3, модуль упругости кг/м2, коэффициент Пуассона, список элементов 
                            double D=Double.parseDouble(Line_s[2])*1000;
                            double E=Double.parseDouble(Line_s[3])*1000;
                            double Mu=Double.parseDouble(Line_s[4]);
                            //Список элементов с данным материалом
                            ArrayList<Integer> elems_for_mat=new ArrayList<Integer>();
                            ArrayList<String> elems_raw=new ArrayList<String>();
                            
                            int index=Arrays.asList(Line_s).indexOf("/");
                            if(index<0)index=Arrays.asList(Line_s).indexOf("\n");
                            if(index<0)index=Arrays.asList(Line_s).indexOf("/\n");
                            //Перенос строки
                            if(line.contains("/"))
                                for(int j=6;j<index;j++)
                                    elems_raw.add(Line_s[j]);
                            else if(!line.contains("/")){
                                for(int j=6;j<index;j++)
                                    elems_raw.add(Line_s[j]);
                                line=file.get(++i);
                                while(!line.contains("/")){
                                    String[] lines=line.split(" ");
                                    
                                    index=Arrays.asList(lines).indexOf("/");
                                    if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                    if(index<0)index=Arrays.asList(lines).indexOf("/\n");
                                    
                                    for(int j=0;j<index;j++)
                                        elems_raw.add(lines[j]);
                                    line=file.get(++i);
                                }
                                //while else
                                String[] lines=line.split(" ");
                                
                                index=Arrays.asList(lines).indexOf("/");
                                if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                if(index<0)index=Arrays.asList(lines).indexOf("/\n");
                                
                                for(int j=0;j<index;j++)
                                    elems_raw.add(lines[j]);
                                //
                            }
                            for(String elems:elems_raw)
                                if(elems.contains("-")){
                                    int begin=Integer.parseInt(elems.split("-")[0]);
                                    int end=Integer.parseInt(elems.split("-")[1])+1;
                                    for(int j=begin;j<=end-1;j++)
                                        elems_for_mat.add(j);   
                                }
                                else elems_for_mat.add(Integer.parseInt(elems));
                            HashMap<String,String> matsLira=new HashMap<String,String>();
                            matsLira.put("N",""+matnum);
                            matsLira.put("D",""+D);
                            matsLira.put("E",""+E);
                            matsLira.put("Mu",""+Mu);
                            matsLira.put("Elements",""+elems_for_mat);
                            Mats_Lira.add(matsLira);
                            matnum++;
                        }
                        //Сечение пластины
                        if(Line_s[0].equals("2") && Line_s[1].equals("1")){
                            ArrayList<Integer> elems_for_sec=new ArrayList<Integer>();
                            ArrayList<String> elems_raw=new ArrayList<String>();
                            
                            int index=Arrays.asList(Line_s).indexOf("/");
                            if(index<0)index=Arrays.asList(Line_s).indexOf("\n");
                            if(index<0)index=Arrays.asList(Line_s).indexOf("/\n");
                            //Толщина пластины в м
                            double H=Double.parseDouble(Line_s[2]);
                            //Список элементов с данным сечением
                            if(line.contains("/"))
                                for(int j=4;j<index;j++)
                                    elems_raw.add(Line_s[j]);
                            if(!line.contains("/")){
                                for(int j=4;j<index;j++)
                                    elems_raw.add(Line_s[j]);
                                //перенос строки
                                line=file.get(++i);
                                while(!line.contains("/")){
                                    String[] lines=line.split(" ");
                                    
                                    index=Arrays.asList(lines).indexOf("/");
                                    if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                    if(index<0)index=Arrays.asList(lines).indexOf("/\n");
                                    
                                    for(int j=0;j<index;j++)
                                        elems_raw.add(lines[j]);
                                    line=file.get(++i);
                                }
                                //while else
                                String[] lines=line.split(" ");
                                
                                index=Arrays.asList(lines).indexOf("/");
                                if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                if(index<0)index=Arrays.asList(lines).indexOf("/\n");
                                
                                for(int j=0;j<index;j++)
                                    elems_raw.add(lines[j]);    
                                //
                            }
                            for(String elems:elems_raw)
                                if(elems.contains("-")){
                                    int begin=Integer.parseInt(elems.split("-")[0]);
                                    int end=Integer.parseInt(elems.split("-")[1])+1;
                                    for(int j=begin;j<=end-1;j++)
                                        elems_for_sec.add(j);
                                }
                                else elems_for_sec.add(Integer.parseInt(elems));
                            HashMap<String,String> elsecLira=new HashMap<String,String>();
                            elsecLira.put("N",""+elsecnum);
                            elsecLira.put("H",""+H);
                            elsecLira.put("Elements",""+elems_for_sec);
                            ElSec_Lira.add(elsecLira);
                            elsecnum++;
                        }
                        //Сечение стержня
                        if(Line_s[0].equals("2") && Line_s[1].equals("2")){
                            ArrayList<Integer> elems_for_sec=new ArrayList<Integer>();
                            ArrayList<String> elems_raw=new ArrayList<String>();
                            
                            int index=Arrays.asList(Line_s).indexOf("/");
                            if(index<0)index=Arrays.asList(Line_s).indexOf("\n");
                            if(index<0)index=Arrays.asList(Line_s).indexOf("/\n");
                            //Брус
                            if(Line_s[2].equals("0")){
                                //Размеры сечения в м
                                double B=Double.parseDouble(Line_s[3])/100;
                                double H=Double.parseDouble(Line_s[4])/100;
                                //Список элементов с данным сечением
                                if(line.contains("/"))
                                    for(int j=6;j<index;j++)
                                        elems_raw.add(Line_s[j]);
                                if(!line.contains("/")){
                                    for(int j=6;j<index;j++)
                                        elems_raw.add(Line_s[j]);
                                    //Перенос строки
                                    line=file.get(++i);
                                    while(!line.contains("/")){
                                        String[] lines=line.split(" ");
                                    
                                        index=Arrays.asList(lines).indexOf("/");
                                        if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                        if(index<0)index=Arrays.asList(lines).indexOf("/\n");
                                        
                                        for(int j=0;j<index;j++)
                                            elems_raw.add(lines[j]);
                                        line=file.get(++i);
                                    }
                                    //while else
                                    String[] lines=line.split(" ");
                                
                                    index=Arrays.asList(lines).indexOf("/");
                                    if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                    if(index<0)index=Arrays.asList(lines).indexOf("/\n");

                                    for(int j=0;j<index;j++)
                                        elems_raw.add(lines[j]);
                                    //
                                }
                                for(String elems:elems_raw)
                                    if(elems.contains("-")){
                                        int begin=Integer.parseInt(elems.split("-")[0]);
                                        int end=Integer.parseInt(elems.split("-")[1])+1;
                                        for(int j=begin;j<=end-1;j++)
                                            elems_for_sec.add(j);
                                    }
                                    else elems_for_sec.add(Integer.parseInt(elems));
                                HashMap<String,String> elsecLira=new HashMap<String,String>();
                                elsecLira.put("N",""+elsecnum);
                                elsecLira.put("B",""+B);
                                elsecLira.put("H",""+H);
                                elsecLira.put("Elements",""+elems_for_sec);
                                ElSec_Lira.add(elsecLira);
                                elsecnum++;
                            }
                            //Тавр полка сверху
                            if(Line_s[2].equals("2")){
                                //Размеры сечения в м
                                double B1=Double.parseDouble(Line_s[3])/100;
                                double H=Double.parseDouble(Line_s[4])/100;
                                double B=Double.parseDouble(Line_s[5])/100;
                                double H1=Double.parseDouble(Line_s[6])/100;
                                //Список элементов с данным сечением
                                if(line.contains("/"))
                                    for(int j=8;j<index;j++)
                                        elems_raw.add(Line_s[j]);
                                if(!line.contains("/")){
                                    for(int j=8;j<Line_s.length;j++)
                                        elems_raw.add(Line_s[j]);
                                    //Перенос строки
                                    line=file.get(++i);
                                    while(!line.contains("/")){
                                        String[] lines=line.split(" ");
                                    
                                        index=Arrays.asList(lines).indexOf("/");
                                        if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                        if(index<0)index=Arrays.asList(lines).indexOf("/\n");
                                        
                                        for(int j=0;j<index;j++)
                                            elems_raw.add(lines[j]);
                                        line=file.get(++i);
                                    }
                                    //while else
                                    String[] lines=line.split(" ");
                                
                                    index=Arrays.asList(lines).indexOf("/");
                                    if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                    if(index<0)index=Arrays.asList(lines).indexOf("/\n");

                                    for(int j=0;j<index;j++)
                                        elems_raw.add(lines[j]);
                                    //
                                }
                                for(String elems:elems_raw)
                                    if(elems.contains("-")){
                                        int begin=Integer.parseInt(elems.split("-")[0]);
                                        int end=Integer.parseInt(elems.split("-")[1])+1;
                                        for(int j=begin;j<=end-1;j++)
                                            elems_for_sec.add(j);
                                    }
                                    else elems_for_sec.add(Integer.parseInt(elems));
                                HashMap<String,String> elsecLira=new HashMap<String,String>();
                                elsecLira.put("N",""+elsecnum);
                                elsecLira.put("B",""+B);
                                elsecLira.put("H",""+H);
                                elsecLira.put("B1",""+B1);
                                elsecLira.put("H1",""+H1);
                                elsecLira.put("Elements",""+elems_for_sec);
                                ElSec_Lira.add(elsecLira);
                                elsecnum++;
                            }
                        }
                        //Произвольное сечение
                        if(Line_s[0].equals("2") && Line_s[1].equals("7")){
                            ArrayList<Integer> elems_for_sec=new ArrayList<Integer>();
                            ArrayList<String> elems_raw=new ArrayList<String>();
                            
                            int index=Arrays.asList(Line_s).indexOf("/");
                            if(index<0)index=Arrays.asList(Line_s).indexOf("\n");
                            if(index<0)index=Arrays.asList(Line_s).indexOf("/\n");
                            //Характеристики сечения
                            Double A=0.0;
                            Double Ix1=0.0;
                            Double Iy1=0.0;
                            Double Iz1=0.0;
                            Double Ay1=0.0;
                            Double Az1=0.0;
                            if(Arrays.asList(Line_s).contains(":")){
                                A=Double.parseDouble(Line_s[2]); //м2
                                Ix1=Double.parseDouble(Line_s[3]); //м4
                                Iy1=Double.parseDouble(Line_s[4]); //м4
                                Iz1=Double.parseDouble(Line_s[5]); //м4
                                Ay1=Double.parseDouble(Line_s[6]); //м2
                                Az1=Double.parseDouble(Line_s[7]); //м2
                            }
                            else{
                                ArrayList<String> char_arr=new ArrayList<String>();
                                for(int j=2;j<index;j++)
                                    char_arr.add(Line_s[j]);
                                line=file.get(++i);
                                String[] Line_s_new=line.split(" ");
                                while(!Arrays.asList(Line_s_new).contains(":")){
                                    int index1=Arrays.asList(Line_s_new).indexOf(":");
                                    for(int j=0;j<index1;j++)
                                        char_arr.add(Line_s_new[j]);
                                    line=file.get(++i);
                                }
                                //while else                                
                                int index1=Arrays.asList(Line_s_new).indexOf("/");
                                if(index1<0)index1=Arrays.asList(Line_s_new).indexOf("\n");
                                if(index1<0)index1=Arrays.asList(Line_s_new).indexOf("/\n");
                                
                                for(int j=0;j<index1;j++)
                                    char_arr.add(Line_s_new[j]);
                                //
                                A=Double.parseDouble(char_arr.get(0)); //м2
                                Ix1=Double.parseDouble(char_arr.get(1)); //м4
                                Iy1=Double.parseDouble(char_arr.get(2)); //м4
                                Iz1=Double.parseDouble(char_arr.get(3)); //м4
                                Ay1=Double.parseDouble(char_arr.get(4)); //м2
                                Az1=Double.parseDouble(char_arr.get(5)); //м2
                            }
                            //Список элементов с данным сечением
                            if(line.contains("/"))
                                for(int j=9;j<index;j++)
                                    elems_raw.add(Line_s[j]);
                            if(!line.contains("/")){
                                for(int j=9;j<index;j++)
                                    elems_raw.add(Line_s[j]);
                                //Перенос строки
                                line=file.get(++i);
                                while(!line.contains("/")){
                                    String[] lines=line.split(" ");
                                    
                                    index=Arrays.asList(lines).indexOf("/");
                                    if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                    if(index<0)index=Arrays.asList(lines).indexOf("/\n");

                                    for(int j=0;j<index;j++)
                                        elems_raw.add(lines[j]);
                                    line=file.get(++i);
                                }
                                //while else
                                String[] lines=line.split(" ");
                                
                                index=Arrays.asList(lines).indexOf("/");
                                if(index<0)index=Arrays.asList(lines).indexOf("\n");
                                if(index<0)index=Arrays.asList(lines).indexOf("/\n");

                                for(int j=0;j<index;j++)
                                    elems_raw.add(lines[j]);
                                //
                            }
                            for(String elems:elems_raw)
                                if(elems.contains("-")){
                                    int begin=Integer.parseInt(elems.split("-")[0]);
                                    int end=Integer.parseInt(elems.split("-")[1])+1;
                                    for(int j=begin;j<=end-1;j++)
                                        elems_for_sec.add(j);
                                }
                                else elems_for_sec.add(Integer.parseInt(elems));
                            HashMap<String,String> elsecLira=new HashMap<String,String>();
                            elsecLira.put("N",""+elsecnum);
                            elsecLira.put("A",""+A);
                            elsecLira.put("Ix1",""+Ix1);
                            elsecLira.put("Iy1",""+Iy1);
                            elsecLira.put("Iz1",""+Iz1);
                            elsecLira.put("Ay1",""+Ay1);
                            elsecLira.put("Az1",""+Az1);
                            elsecLira.put("Elements",""+elems_for_sec);
                            ElSec_Lira.add(elsecLira);
                            elsecnum++;
                        }
                    }
                    line=file.get(++i);
                }
            }
            if(line.contains("Nodes")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    String[] point=line.split(" ");
                    int num=0;
                    try{
                        num=Integer.parseInt(point[4]);
                    }
                    catch(Exception e){
                        num=nn;
                    }
                    HashMap<String,String> nodesLira=new HashMap<String,String>();
                    nodesLira.put("N",""+num);
                    nodesLira.put("X",""+Double.parseDouble(point[0]));
                    nodesLira.put("Y",""+Double.parseDouble(point[1]));
                    nodesLira.put("Z",""+Double.parseDouble(point[2]));
                    Nodes_Lira.add(nodesLira);
                    nn++;
                    line=file.get(++i);
                }
            }
            if(line.contains("Elements")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    String[] Line_s=line.split(" ");
                    int num=0;
                    try{
                        num=Integer.parseInt(Line_s[5]);                        
                    }
                    catch(Exception e){
                        num=en;
                    }
                    //№5 - если без номера
                    if (Line_s.length==7 || Line_s.length==5){
                        HashMap<String,Object> elemsLira=new HashMap<String,Object>();
                        elemsLira.put("N",num);
                        elemsLira.put("T",Integer.parseInt(Line_s[0]));
                        elemsLira.put("P",new Object[]{Integer.parseInt(Line_s[2]),Integer.parseInt(Line_s[3])});
                        Elems_Lira.add(elemsLira);
                        en++;
                        if(Integer.parseInt(Line_s[0])==1 || Integer.parseInt(Line_s[0])==4)
                            for(int j=1;j<3;j++)
                                for(int k=4;k<7;k++){
                                    HashMap<String,Integer> hingesLira=new HashMap<String,Integer>();
                                    hingesLira.put("El",num);
                                    hingesLira.put("Node",j);
                                    hingesLira.put("DOF",k);
                                    Hinges_Lira.add(hingesLira);
                                }
                    }
                    if(Line_s.length==8){
                        HashMap<String,Object> elemsLira=new HashMap<String,Object>();
                        elemsLira.put("N",Integer.parseInt(Line_s[6]));
                        elemsLira.put("T",Integer.parseInt(Line_s[0]));
                        elemsLira.put("P",new Object[]{Integer.parseInt(Line_s[2]),Integer.parseInt(Line_s[3]),Integer.parseInt(Line_s[4])});
                        Elems_Lira.add(elemsLira);
                    }
                    if(Line_s.length==9){
                        HashMap<String,Object> elemsLira=new HashMap<String,Object>();
                        elemsLira.put("N",Integer.parseInt(Line_s[7]));
                        elemsLira.put("T",Integer.parseInt(Line_s[0]));
                        elemsLira.put("P",new Object[]{Integer.parseInt(Line_s[3]),Integer.parseInt(Line_s[2]),Integer.parseInt(Line_s[4]),Integer.parseInt(Line_s[5])});
                        Elems_Lira.add(elemsLira);
                    }
                    line=file.get(++i);
                }
            }
            if(line.contains("Hinge")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    String[] hinge=line.split(" ");
                    HashMap<String,Integer> hingesLira=new HashMap<String,Integer>();
                    hingesLira.put("El",Integer.parseInt(hinge[0]));
                    hingesLira.put("Node",Integer.parseInt(hinge[1]));
                    hinge[2]=hinge[2].replace("/","");
                    hinge[2]=hinge[2].replace("\n","");
                    hingesLira.put("DOF",Integer.parseInt(hinge[2]));
                    Hinges_Lira.add(hingesLira);
                    line=file.get(++i);
                }
            }
            if(line.contains("Axis of Orthotropy")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    String[] Axis_s=line.split(" ");
                    for(Object elem:Elems_Lira){
                        HashMap<String,Object> elem1=(HashMap<String,Object>)elem;
                        int Num=Integer.parseInt(""+elem1.get("N"));
                        Object[] P=(Object[]) elem1.get("P");
                        if(Integer.parseInt(Axis_s[0])==Num && P.length==2)
                            elem1.put("AV",Axis_s[1]+" "+Axis_s[2]+" "+Axis_s[3]); 
                    }
                    line=file.get(++i);
                }
            }
            if(line.contains("Restraint & UnionDOF")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    String[] sup=line.split(" ");
                    HashMap<String,Object> supportsLira=new HashMap<String,Object>();
                    supportsLira.put("Node",sup[0]);
                    
                    int index=Arrays.asList(sup).indexOf("/");
                    if(index<0)index=Arrays.asList(sup).indexOf("\n");
                    if(index<0)index=Arrays.asList(sup).indexOf("/\n");
                    
                    ArrayList<String> sup1=new ArrayList<String>();
                    for(int j=1;j<index;j++)
                        if(!sup[j].isEmpty())
                            sup1.add(sup[j]);
                    supportsLira.put("DOF",sup1);
                    Supports_Lira.add(supportsLira);
                    line=file.get(++i);
                }
            }
            if(line.contains("Load\n")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    String[] loadtype=line.split(" ");
                    //Узловые нагрузки
                    if(loadtype[1].equals("0")){
                        HashMap<String,Integer> nodeLoadsLira=new HashMap<String,Integer>();
                        nodeLoadsLira.put("Node",Integer.parseInt(loadtype[0]));
                        nodeLoadsLira.put("Dir",Integer.parseInt(loadtype[2]));
                        nodeLoadsLira.put("ValueNum",Integer.parseInt(loadtype[3]));
                        nodeLoadsLira.put("LC",Integer.parseInt(loadtype[4]));
                        Node_Loads_Lira.add(nodeLoadsLira);
                    }
                    //Нагрузки на элементы
                    else{
                        String[] arr={"16","17"};
                        String[] arr1={"1","2","3"};
                        //Равномерная нагрузка или трапециевидная нагрузка
                        if(Arrays.asList(arr).contains(loadtype[1]) && Arrays.asList(arr1).contains(loadtype[2])){
                            HashMap<String,Integer> elemLoadsLira=new HashMap<String,Integer>();
                            elemLoadsLira.put("El",Integer.parseInt(loadtype[0]));
                            elemLoadsLira.put("Dir",Integer.parseInt(loadtype[2]));
                            elemLoadsLira.put("ValueNum",Integer.parseInt(loadtype[3]));
                            elemLoadsLira.put("LC",Integer.parseInt(loadtype[4]));
                            Elem_Loads_Lira.add(elemLoadsLira);
                        }
                    }
                    line=file.get(++i);
                }
            }
            if(line.contains("Load Value")){
                i+=2;
                line=file.get(i);
                while(!line.contains(")\n")){
                    String[] loadvalue=line.split(" ");
                    HashMap<String,Object>loadValueLira=new HashMap<String,Object>();
                    loadValueLira.put("ValueNum",loadvalue[0]);
                    
                    int index=Arrays.asList(loadvalue).indexOf("/");
                    if(index<0)index=Arrays.asList(loadvalue).indexOf("\n");
                    if(index<0)index=Arrays.asList(loadvalue).indexOf("/\n");
                    
                    ArrayList<String> loadvalue1=new ArrayList<String>();
                    for(int j=1;j<index;j++)
                        loadvalue1.add(loadvalue[j]);
                    loadValueLira.put("Value",loadvalue1);
                    Load_Values_Lira.add(loadValueLira);
                    line=file.get(++i);
                }
            }
            else{
                if(i<file.size()-1)line=file.get(++i);
                else i++;
            }
        }
        //Сообщение 4
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\003-M.txt"))){
            GregorianCalendar cal4=new GregorianCalendar();
            String hour4=""+cal4.get(Calendar.HOUR_OF_DAY);
            String minute4=""+cal4.get(Calendar.MINUTE);
            String second4=""+cal4.get(Calendar.SECOND);
            if(hour4.length()==1)hour4="0"+hour4;
            if(minute4.length()==1)minute4="0"+minute4;
            if(second4.length()==1)second4="0"+second4;
            writer.write("4: "+hour4+":"+minute4+":"+second4+" преобразование данных");
        }
        catch(IOException ex){}
        //
        String strOut="$MODEL \""+name+"\"\n&\n\n";
        strOut+="///Тип модели\n";
        strOut+="$MTYPE\n";
        strOut+="10 300 ///Тип модели по уровню представления: расчетная модель\n";
        scheme=scheme.replace(" ","");
        scheme=scheme.replace("\n","");
        if(Integer.parseInt(scheme)==2)strOut+="21 220 ///Размерность пространства: плоская рама\n";
        if(Integer.parseInt(scheme)==3)strOut+="21 210 ///Размерность пространства: плоская плита\n";
        if(Integer.parseInt(scheme)==5)strOut+="21 300 ///Размерность пространства: 3D\n";
        strOut+="22 0 ///Размерность времени: нет времени\n";
        strOut+="30 900 ///Тип модели по уровню детализации: дискредитированная (конечно-элементная) модель\n";
        strOut+="40 210 ///Тип модели по представляемому физическому явлению: строительная механика, статика\n";
        strOut+="50 110 ///Тип модели по информации: недеформированная схема\n";
        strOut+="&\n\n";
        
        strOut+="///Материалы ("+Mats_Lira.size()+")\n";
        strOut+="@MAT\n";
        for(Object mat:Mats_Lira){
            HashMap<String,String> mat1=(HashMap<String,String>)mat;
            String Num=mat1.get("N");
            String D=mat1.get("D");
            String E=mat1.get("E");
            String Mu=mat1.get("Mu");
            strOut+=Num+" "+Num+" {D="+D+" E="+E+" Mu="+Mu+"}\n";
        }
        strOut+="&\n\n";

        strOut+="///Поперечные сечения элемента ("+ElSec_Lira.size()+")\n";
        strOut+="@ELSEC\n";
        for(Object sec:ElSec_Lira){
            HashMap<String,String> sec1=(HashMap<String,String>)sec;
            String Num=sec1.get("N");
            ArrayList<String> secArr=new ArrayList<String>();
            secArr.add(Num+" {");
            for(String key:sec1.keySet())
                if(!key.equals("N") && !key.equals("Elements"))
                    secArr.add(key+"="+sec1.get(key));
            secArr.add("}\n");

            for(int i=0;i<secArr.size();i++)
                if(i>0 && i<secArr.size()-2)strOut+=secArr.get(i)+" ";
                else strOut+=secArr.get(i);
        }
        strOut+="&\n\n";
        String p1="";
        int num=0,p2=0;
        String pointStr="@POINT\n";
        //Первая точка для всех векторов ориентации стержней
        boolean flag=false;
        for(Object point:Nodes_Lira){
            HashMap<String,String> point1=(HashMap<String,String>)point;
            String Num=point1.get("N");
            String X=point1.get("X");
            String Y=point1.get("Y");
            String Z=point1.get("Z");
            pointStr+=Num+" "+X+" "+Y+" "+Z+"\n";
            num=Integer.parseInt(point1.get("N"));
            if(Double.parseDouble(X)==0 && Double.parseDouble(Y)==0 && Double.parseDouble(Z)==0){
                flag=true;
                p1=""+num;
            }
        }
        if(flag)p2=num+1;
        else{
            p1=""+(num+1);
            p2=num+2;
            pointStr+=p1+" 0 0 0\n";
        }

        String plineStr="@PLINE\n";
        for(Object elem:Elems_Lira){
            HashMap<String,Object> elem1=(HashMap<String,Object>)elem;
            String Num=""+elem1.get("N");
            Object[] P=(Object[])elem1.get("P");
            plineStr+=Num+" ";
            for(int i=0;i<P.length;i++)
                plineStr+=P[i]+" ";
            plineStr+="\n";
            num=Integer.parseInt(Num);
        }
        int orient_line=num+1;

        int barsec=0,barmat=0;
        String barStr="@BAR\n";
        HashMap<Integer,Integer> BarConvDict=new HashMap<Integer,Integer>();
        int barnum=1;
        for(Object elem:Elems_Lira){
            HashMap<String,Object> elem1=(HashMap<String,Object>)elem;
            Object[] P=(Object[])elem1.get("P");
            String barstr="";
            if(P.length==2){
                int BarN=Integer.parseInt(""+elem1.get("N"));
                BarConvDict.put(BarN,barnum);
                barstr+=""+barnum+" ";
                for(Object sec:ElSec_Lira){
                    HashMap<String,String> sec1=(HashMap<String,String>)sec;
                    String Elements=sec1.get("Elements");
                    Elements=Elements.replace("[","");
                    Elements=Elements.replace("]","");
                    Elements=Elements.replace(" ","");
                    if(!Elements.isEmpty()){
                        int[] numbers=Arrays.stream(Elements.split(",")).mapToInt(Integer::parseInt).toArray();
                        for(int j=0;j<numbers.length;j++)
                            if(numbers[j]==BarN)
                                barsec=Integer.parseInt(sec1.get("N"));
                    }
                }
                barstr+=""+barsec+" ";
                for(Object mat:Mats_Lira){
                    HashMap<String,String> mat1=(HashMap<String,String>)mat;
                    String Elements=mat1.get("Elements");
                    Elements=Elements.replace("[","");
                    Elements=Elements.replace("]","");
                    Elements=Elements.replace(" ","");
                    int[] numbers=Arrays.stream(Elements.split(",")).mapToInt(Integer::parseInt).toArray();
                    for(int j=0;j<numbers.length;j++)
                        if(numbers[j]==BarN)
                            barmat=Integer.parseInt(mat1.get("N"));
                }
                barstr+=""+barmat+" 0 ";
                //Ориентация
                String p="";
                String point2str_for_vect=""+elem1.get("AV");
                flag=false;
                String[] pointStr1=pointStr.split("\n");
                for(String point:pointStr1){
                    String[] point1=point.split(" ");
                    String pointstr="";
                    for(int i=1;i<point1.length;i++)
                        if(i!=point1.length-1)pointstr+=point1[i]+" ";
                        else pointstr+=point1[i];
                    if(point2str_for_vect.equals(pointstr))
                        flag=true;
                    p=point;
                }
                if(flag){
                    String pointnum=p.split(" ")[0];
                    String pointline=p1+" "+pointnum;
                    String[] plineStr1=plineStr.split("\n");
                    String orient_line_found="";
                    for(String pline:plineStr1){
                        String[] pline1=pline.split(" ");
                        String linestr="";
                        for(int i=1;i<pline1.length;i++)
                            if(i!=pline1.length-1)linestr+=pline1[i]+" ";
                            else linestr+=pline1[i];
                        if(pointline.equals(linestr))
                            orient_line_found=pline.split(" ")[0];
                    }
                    barstr+=orient_line_found+" ";
                }
                else{
                    pointStr+=p2+" "+point2str_for_vect+"\n";
                    plineStr+=orient_line+" "+p1+" "+p2+"\n";
                    p2++;
                    barstr+=orient_line+" ";
                    orient_line++;
                }
                barstr+=elem1.get("N");
                barstr+="\n";
                barStr+=barstr;
                barnum++;
            }
        }
        barStr="///Стержни ("+(barnum-1)+")\n"+barStr;

        //
        strOut+="///Точки ("+(pointStr.split("\n").length-1)+")\n"+pointStr+"&\n\n";
        strOut+="///Полилинии ("+(plineStr.split("\n").length-1)+")\n"+plineStr+"&\n\n";
        //

        String lineloop="@LINELOOP\n";
        int loopnum=1;
        HashMap<Integer,String> elemloop_dict=new HashMap<Integer,String>();
        for(Object elem:Elems_Lira){
            HashMap<String,Object> elem1=(HashMap<String,Object>)elem;
            Object[] P=(Object[])elem1.get("P");
            if(P.length>2){
                String Num=""+elem1.get("N");
                elemloop_dict.put(loopnum,Num);
                String lineloopstr=""+loopnum+" ";
                lineloopstr+="1 "+Num+"\n";
                lineloop+=lineloopstr;
                loopnum++;
            }
        }
        lineloop="///Замкнутые линии ("+(loopnum-1)+")\n"+lineloop;
        if((loopnum-1)>0)strOut+=lineloop+"&\n\n";            

        if((loopnum-1)>0){
            strOut+="///Плоские элементы поверхностей ("+(loopnum-1)+")\n";
            strOut+="@PSURF\n";
            for(int i=0;i<loopnum-1;i++){
                String psurfstr=""+(i+1)+" "+(i+1)+"\n";
                strOut+=psurfstr;
            }
            strOut+="&\n\n";
        }

        int elsec=0,elmat=0;
        if((loopnum-1)>0){
            strOut+="///Оболочки ("+(loopnum-1)+")\n";
            strOut+="@SHELL\n";
            for(int i=0;i<loopnum-1;i++){
                int ElNum=Integer.parseInt(elemloop_dict.get(i+1));
                for(Object sec:ElSec_Lira){
                    HashMap<String,String> sec1=(HashMap<String,String>)sec;
                    String Elements=sec1.get("Elements");
                    Elements=Elements.replace("[","");
                    Elements=Elements.replace("]","");
                    Elements=Elements.replace(" ","");                       
                    int[] numbers=Arrays.stream(Elements.split(",")).mapToInt(Integer::parseInt).toArray();
                    for(int j=0;j<numbers.length;j++)
                        if(numbers[j]==ElNum)
                            elsec=Integer.parseInt(sec1.get("N"));
                }
                for(Object mat:Mats_Lira){
                    HashMap<String,String> mat1=(HashMap<String,String>)mat;
                    String Elements=mat1.get("Elements");
                    Elements=Elements.replace("[","");
                    Elements=Elements.replace("]","");
                    Elements=Elements.replace(" ","");
                    int[] numbers=Arrays.stream(Elements.split(",")).mapToInt(Integer::parseInt).toArray();
                    for(int j=0;j<numbers.length;j++)
                        if(numbers[j]==ElNum)
                            elmat=Integer.parseInt(mat1.get("N"));
                }
                String shellstr=""+(i+1)+" "+elsec+" "+elmat+" 0 "+(i+1);
                shellstr+="\n";
                strOut+=shellstr;
            }
            strOut+="&\n\n";
        }

        if((barnum-1)>0)strOut+=barStr+"&\n\n";

        strOut+="///Масштаб отображения нагрузок (1)\n";
        strOut+="@LSCALE\n";
        strOut+="20000 1\n&\n\n";

        if(Load_Cases_Lira.size()>0){
            strOut+="///Отображения и названия загружений ("+Load_Cases_Lira.size()+")\n";
            strOut+="@LCASENAME\n";
            for(Object lc:Load_Cases_Lira){
                HashMap<String,String> lc1=(HashMap<String,String>)lc;
                String lcnum=lc1.get("N");
                String Name=lc1.get("LCName");
                strOut+=lcnum+" 0 \n";
            }
            strOut+="&\n\n";
        }

        if(Node_Loads_Lira.size()>0){
            strOut+="///Узловые нагрузки ("+Node_Loads_Lira.size()+")\n";
            strOut+="@NLOAD\n";
            int load_num=0;
            for(Object nl:Node_Loads_Lira){
                HashMap<String,Integer> nl1=(HashMap<String,Integer>)nl;
                double LoadValue=0.0;
                num=nl1.get("Node");
                int dir=nl1.get("Dir");
                int VNum=nl1.get("ValueNum");
                int LC=nl1.get("LC");
                for(Object lv:Load_Values_Lira){
                    HashMap<String,Object> lv1=(HashMap<String,Object>)lv;
                    int Num=Integer.parseInt(""+lv1.get("ValueNum"));
                    ArrayList<String> value=(ArrayList<String>)lv1.get("Value");
                    if(VNum==Num)
                        //Перевод из ТС в Н
                        LoadValue=-Double.parseDouble(value.get(0))*9806.65;
                }
                if(dir==1)strOut+=load_num+" "+LC+" "+num+" "+LoadValue+" 0 0 0 \n";
                if(dir==2)strOut+=load_num+" "+LC+" "+num+" 0 "+LoadValue+" 0 0 \n";
                if(dir==3)strOut+=load_num+" "+LC+" "+num+" 0 0 "+LoadValue+" 0 \n";
                load_num++;
            }
            strOut+="&\n\n";
        }

        String lnloadStr="@LNLOAD\n";
        String ltloadStr="@LTLOAD\n";
        int l_load_num=0;
        int t_load_num=0;
        for(Object ell:Elem_Loads_Lira){
            HashMap<String,Integer> ell1=(HashMap<String,Integer>)ell;
            ArrayList<String> LoadValue=null;
            int elnum=ell1.get("El");
            int dir=ell1.get("Dir");
            int VNum=ell1.get("ValueNum");
            int LC=ell1.get("LC");
            for(Object lv:Load_Values_Lira){
                HashMap<String,Object> lv1=(HashMap<String,Object>)lv;
                int Num=Integer.parseInt(""+lv1.get("ValueNum"));
                ArrayList<String> value=(ArrayList<String>)lv1.get("Value");
                if(VNum==Num)
                    //Перевод из ТС в Н
                    LoadValue=value;
            }
            if(dir==1 && LoadValue.size()==4)
                lnloadStr+=l_load_num+" "+LC+" "+BarConvDict.get(elnum)+" "+(-Double.parseDouble(LoadValue.get(0))*9806.65)+" 0 0 0 \n";
            if(dir==2 && LoadValue.size()==4)
                lnloadStr+=l_load_num+" "+LC+" "+BarConvDict.get(elnum)+" 0 "+(-Double.parseDouble(LoadValue.get(0))*9806.65)+" 0 0 \n";
            if(dir==3 && LoadValue.size()==4)
                lnloadStr+=l_load_num+" "+LC+" "+BarConvDict.get(elnum)+" 0 0 "+(-Double.parseDouble(LoadValue.get(0))*9806.65)+" 0 \n";
            if(dir==1 && LoadValue.size()==6)
                ltloadStr+=t_load_num+" "+LC+" "+BarConvDict.get(elnum)+" "+(-Double.parseDouble(LoadValue.get(0))*9806.65)+" 0 0 "
                        +(-Double.parseDouble(LoadValue.get(2))*9806.65)+" 0 0 0 \n";
            if(dir==2 && LoadValue.size()==6)
                ltloadStr+=t_load_num+" "+LC+" "+BarConvDict.get(elnum)+" 0 "+(-Double.parseDouble(LoadValue.get(0))*9806.65)+" 0 0 "
                        +(-Double.parseDouble(LoadValue.get(2))*9806.65)+" 0 0 \n";
            if(dir==3 && LoadValue.size()==6)
                ltloadStr+=t_load_num+" "+LC+" "+BarConvDict.get(elnum)+" 0 0 "+(-Double.parseDouble(LoadValue.get(0))*9806.65)+" 0 0 "
                        +(-Double.parseDouble(LoadValue.get(2))*9806.65)+" 0 \n";
            l_load_num++;
            t_load_num++;
        }
        if((lnloadStr.split("\n").length-1)>0)
            strOut+="///Линейные равномерные нагрузки на стержневой элемент ("+(lnloadStr.split("\n").length-1)+")\n"+lnloadStr+"&\n\n";
        if((ltloadStr.split("\n").length-1)>0)
            strOut+="///Линейные неравномерные (трапециевидные) нагрузки на элемент ("+(ltloadStr.split("\n").length-1)+")\n"+ltloadStr+"&\n\n";

        String bhinge="@BHINGE\n";
        int H_num=0;
        for(Object elem:Elems_Lira){
            HashMap<String,Object> elem1=(HashMap<String,Object>)elem;
            int Num=Integer.parseInt(""+elem1.get("N"));
            //По умолчанию все повороты запрещены
            int[] U={0,0,0,0,0,0};
            int count=0;
            for(Object h:Hinges_Lira){
                HashMap<String,Integer> h1=(HashMap<String,Integer>)h;
                int ElNum=h1.get("El");
                if(Num==ElNum){
                    int Node=h1.get("Node");
                    int DOF=h1.get("DOF");
                    if(Node==1 && DOF==4){U[0]=1;count++;}
                    if(Node==1 && DOF==5){U[1]=1;count++;}
                    if(Node==1 && DOF==6){U[2]=1;count++;}
                    if(Node==2 && DOF==4){U[3]=1;count++;}
                    if(Node==2 && DOF==5){U[4]=1;count++;}
                    if(Node==2 && DOF==6){U[5]=1;count++;}
                }
            }
            if(count>=1){
                bhinge+=""+H_num+" "+BarConvDict.get(Num)+" ";
                for(int u:U)
                    bhinge+=u+" ";
                bhinge+="\n";
                H_num++;
            }
        }
        if(H_num>0)strOut+="///Шарниры в стержнях ("+H_num+")\n"+bhinge+"&\n\n";

        if(Supports_Lira.size()>0){
            strOut+="///Узловые опоры ("+Supports_Lira.size()+")\n";
            strOut+="@NHSUP\n";
            int S_num=0;
            for(Object sup:Supports_Lira){
                HashMap<String,Object> sup1=(HashMap<String,Object>)sup;
                String Node=""+sup1.get("Node");
                String DOF=""+sup1.get("DOF");
                DOF=DOF.replace("[","");
                DOF=DOF.replace("]","");
                DOF=DOF.replace(" ","");
                String[] DOF1=DOF.split(",");
                int[] S={0,0,0,0,0,0};
                int count1=0;
                int countMinus1=0;
                if(Arrays.asList(DOF1).contains("1")){S[0]=1;count1++;}
                if(Arrays.asList(DOF1).contains("2")){S[1]=1;count1++;}
                if(Arrays.asList(DOF1).contains("3")){S[2]=-1;countMinus1++;}
                if(Arrays.asList(DOF1).contains("4")){S[3]=1;count1++;}
                if(Arrays.asList(DOF1).contains("5")){S[4]=1;count1++;}
                if(Arrays.asList(DOF1).contains("6")){S[5]=1;count1++;}
                if(Integer.parseInt(scheme)==1){
                    S[1]=1;
                    S[3]=1;
                    S[4]=1;
                    S[5]=1;
                    count1+=4;
                }
                if(Integer.parseInt(scheme)==2){
                    S[1]=1;
                    S[3]=1;
                    S[5]=1;
                    count1+=3;
                }
                if(Integer.parseInt(scheme)==3){
                    S[0]=1;
                    S[1]=1;
                    S[5]=1;
                    count1+=3;
                }
                if(Integer.parseInt(scheme)==4){
                    S[3]=1;
                    S[4]=1;
                    S[5]=1;
                    count1+=3;
                }
                if(count1>=1 || countMinus1>=-1){
                    strOut+=S_num+" "+Node+" ";
                    for(int s:S)
                        strOut+=s+" ";
                    strOut+="\n";
                    S_num++;
                }
            }
            strOut+="&\n\n";
        }

        //Сообщение 5
        GregorianCalendar cal5=new GregorianCalendar();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\004-M.txt"))){
            String hour5=""+cal5.get(Calendar.HOUR_OF_DAY);
            String minute5=""+cal5.get(Calendar.MINUTE);
            String second5=""+cal5.get(Calendar.SECOND);
            if(hour5.length()==1)hour5="0"+hour5;
            if(minute5.length()==1)minute5="0"+minute5;
            if(second5.length()==1)second5="0"+second5;
            writer.write("5: "+hour5+":"+minute5+":"+second5+" запись в файл "+args[1]+"\\"+name);
        }
        catch(IOException ex){}
        //

        //
        long timeSpent=System.currentTimeMillis()-startTime;
        int hours=(int)(timeSpent/(60*60*1000));
        timeSpent-=(hours*60*60*1000);
        int minutes =(int)(timeSpent/(60 * 1000));
        timeSpent-=(minutes*60*1000);
        int seconds =(int)(timeSpent/(1000));
        time=hours+":"+minutes+":"+seconds;
        //
        String strOut1=strOut;
        strOut="/// --------------------------------------------\n";
        strOut+="/// Преобразование Lira в BSMT.\n";
        strOut+="/// Исходный файл: "+args[0]+".\n";
        strOut+="/// Выходной файл: "+args[1]+"\\"+name+".\n";
        strOut+="/// Дата конвертации: "+cal5.get(Calendar.DAY_OF_MONTH)+"."
                    +(cal5.get(Calendar.MONTH)+1)+"."+cal5.get(Calendar.YEAR)+".\n";
        strOut+="/// Время конвертации: "+time+".\n";
        strOut+="/// ---------------------------------------------\n\n";

        strOut+="/// MAT="+Mats_Lira.size()+" ELSEC="+ElSec_Lira.size()
                +" POINT="+(pointStr.split("\n").length-1)+" PLINE="+(plineStr.split("\n").length-1);
        if((loopnum-1)>0)strOut+=" LINELOOP="+(loopnum-1)+" PSURF="+(loopnum-1)+" SHELL="+(loopnum-1);
        if((barnum-1)>0)strOut+=" BAR="+(barnum-1);
        strOut+=" LSCALE=1";
        if(Load_Cases_Lira.size()>0)strOut+=" LCASENAME="+Load_Cases_Lira.size();
        if(Node_Loads_Lira.size()>0)strOut+=" NLOAD="+Node_Loads_Lira.size();
        if((lnloadStr.split("\n").length-1)>0)strOut+=" LNLOAD="+(lnloadStr.split("\n").length-1);
        if((ltloadStr.split("\n").length-1)>0)strOut+=" LTLOAD="+(ltloadStr.split("\n").length-1);
        if(H_num>0)strOut+=" BHINGE="+H_num;
        if(Supports_Lira.size()>0)strOut+=" NHSUP="+Supports_Lira.size();
        strOut+="\n\n\n";                   
        strOut+=strOut1;
        
        countObjects=Mats_Lira.size()+ElSec_Lira.size()+(pointStr.split("\n").length-1)
                +(plineStr.split("\n").length-1)+((loopnum-1)*3)+(barnum-1)+1+Load_Cases_Lira.size()
                +Node_Loads_Lira.size()+(lnloadStr.split("\n").length-1)+(ltloadStr.split("\n").length-1)
                +H_num+Supports_Lira.size();
        return strOut;
    }
    
    public static String fea2bsm(ArrayList<String> arrFea,String[] args,String name,long startTime){
        int N_knot=0, N_elem=0, N_mate=0, N_lakn=0, index=0;
        ArrayList<String> arrKnot=new ArrayList<String>(); //Создаем список узлов
        ArrayList<String> arrElem=new ArrayList<String>(); //Создаем список элементов
        ArrayList<String> arrMat=new ArrayList<String>(); //Создаем список материалов
        ArrayList<String> arrLakn=new ArrayList<String>(); //Создаем список узловых нагрузок

        //Определяем количество узлов, элементов, материалов и нагрузок на узел
        for(int i=0;i<arrFea.size();i++){
            if(arrFea.get(i).contains("KNOT")){
                index=i;
                String[] str=arrFea.get(i).split(" ");
                int maxIndex=Integer.parseInt(str[1])+i;
                for(int j=index+1;j<maxIndex+1;j++)
                    arrKnot.add(arrFea.get(j));
                N_knot=Integer.parseInt(str[1]);
            }
            if(arrFea.get(i).contains("ELEM")){
                index=i;
                String[] str=arrFea.get(i).split(" ");
                int maxIndex=Integer.parseInt(str[1])+i;
                for(int j=index+1;j<maxIndex+1;j++)
                    arrElem.add(arrFea.get(j));
                N_elem=Integer.parseInt(str[1]);
            }
            if(arrFea.get(i).contains("MATE")){
                index=i;
                String[] str=arrFea.get(i).split(" ");
                int maxIndex=Integer.parseInt(str[1])+i;
                for(int j=index+1;j<maxIndex+1;j++)
                    arrMat.add(arrFea.get(j));
                N_mate=Integer.parseInt(str[1]);
            }
            if(arrFea.get(i).contains("LAKN")){
                index=i;
                String[] str=arrFea.get(i).split(" ");
                int maxIndex=Integer.parseInt(str[1])+i;
                for(int j=index+1;j<maxIndex+1;j++)
                    arrLakn.add(arrFea.get(j));
                N_lakn=Integer.parseInt(str[1]);
                break;
            }
        }

        //Сообщение 3
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\002-M.txt"))){
            GregorianCalendar cal3=new GregorianCalendar();
            String hour3=""+cal3.get(Calendar.HOUR_OF_DAY);
            String minute3=""+cal3.get(Calendar.MINUTE);
            String second3=""+cal3.get(Calendar.SECOND);
            if(hour3.length()==1)hour3="0"+hour3;
            if(minute3.length()==1)minute3="0"+minute3;
            if(second3.length()==1)second3="0"+second3;
            writer.write("3: "+hour3+":"+minute3+":"+second3+" окончание обработки данных");
        }
        catch(IOException ex){}
        //

        //Строка для записи в файл BSM
        String strOut="///Материалы ("+N_mate+")\n";
        strOut+="@MAT\n";
        for(int i=0;i<N_mate;i++)
            strOut+=(i+1)+" 0\n";
        strOut+="&\n\n";
        //Сечения
        strOut+="///Поперечные сечения элемента (1)\n";
        strOut+="@ELSEC\n1\n&\n\n";
        //Создаем списки для Pline, Loop и т.д.
        ArrayList<Object> arrPline=new ArrayList<Object>();
        ArrayList<Object> arrLoop=new ArrayList<Object>();
        ArrayList<Object> arrPsurf=new ArrayList<Object>();
        ArrayList<Object> arrEbody=new ArrayList<Object>();
        ArrayList<Object> arrSbody=new ArrayList<Object>();
        ArrayList<Object> arrShell=new ArrayList<Object>();
        ArrayList<Object> arrBar=new ArrayList<Object>();
        //Для каждого материала создаем отдельный список
        for(int i=1;i<N_mate+1;i++){
            arrEbody.add(new ArrayList<String>());
            arrSbody.add(new ArrayList<String>());
            arrShell.add(new ArrayList<String>());
            arrBar.add(new ArrayList<String>());
        }
        ArrayList<Object> arrEbody_3d=new ArrayList<Object>();
        ArrayList<String> shellArray=null;
        ArrayList<String> barArray=null;
        ArrayList<String> ebodyArray=null;
        ArrayList<String> sbodyArray=null;

        //Разбираем элементы в зависимости от их типа, создаем для них Pline, Psurf и т.п.
        //Для линейных элементов - Pline и Bar
        //Для плоских элеметов - Pline, Loop, Psurf, Shell
        //Для трехмерных - Pline, Loop, Psurf, Ebdoy, Sbody
        for(String s:arrElem){
            String[] elem=s.split(" ");
            //Линейные
            if(elem[0].equals("1") || elem[0].equals("2")){
                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[4]});
                if(Integer.parseInt(elem[1])>0)
                    barArray=(ArrayList<String>)arrBar.get(Integer.parseInt(elem[1])-1);
                else
                    barArray=(ArrayList<String>)arrBar.get(arrBar.size()-(Integer.parseInt(elem[1])+1));
                barArray.add(""+arrPline.size());
            }
            //Плоские
            if(elem[0].equals("7")){
                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[4],elem[5]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                if(Integer.parseInt(elem[1])>0)
                    shellArray=(ArrayList<String>)arrShell.get(Integer.parseInt(elem[1])-1);
                else
                    shellArray=(ArrayList<String>)arrShell.get(arrShell.size()-(Integer.parseInt(elem[1])+1));
                shellArray.add(""+arrPsurf.size());
            }
            if(elem[0].equals("8")){
                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[4],elem[5],elem[6]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                if(Integer.parseInt(elem[1])>0)
                    shellArray=(ArrayList<String>)arrShell.get(Integer.parseInt(elem[1])-1);
                else
                    shellArray=(ArrayList<String>)arrShell.get(arrShell.size()-(Integer.parseInt(elem[1])+1));
                shellArray.add(""+arrPsurf.size());
            }
            //Трехмерные
            if(elem[0].equals("3") || elem[0].equals("4")){
                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[4],elem[5]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                if(Integer.parseInt(elem[1])>0){
                    ebodyArray=(ArrayList<String>)arrEbody.get(Integer.parseInt(elem[1])-1);
                    ebodyArray.add(""+arrPsurf.size());
                    sbodyArray=(ArrayList<String>)arrSbody.get(Integer.parseInt(elem[1])-1);
                    sbodyArray.add(""+arrEbody.get(Integer.parseInt(elem[1])-1).toString().length());
                }
                else{
                    ebodyArray=(ArrayList<String>)arrEbody.get(arrEbody.size()-(Integer.parseInt(elem[1])+1));
                    ebodyArray.add(""+arrPsurf.size());
                    sbodyArray=(ArrayList<String>)arrSbody.get(arrSbody.size()-(Integer.parseInt(elem[1])+1));
                    sbodyArray.add(""+arrEbody.get(arrEbody.size()-(Integer.parseInt(elem[1])+1)).toString().length());
                }
            }
            if(elem[0].equals("5") || elem[0].equals("6")){
                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[4],elem[5],elem[6]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                if(Integer.parseInt(elem[1])>0){
                    ebodyArray=(ArrayList<String>)arrEbody.get(Integer.parseInt(elem[1])-1);
                    ebodyArray.add(""+arrPsurf.size());
                    sbodyArray=(ArrayList<String>)arrSbody.get(Integer.parseInt(elem[1])-1);
                    sbodyArray.add(""+arrEbody.get(Integer.parseInt(elem[1])-1).toString().length());
                }
                else{
                    ebodyArray=(ArrayList<String>)arrEbody.get(arrEbody.size()-(Integer.parseInt(elem[1])+1));
                    ebodyArray.add(""+arrPsurf.size());
                    sbodyArray=(ArrayList<String>)arrSbody.get(arrSbody.size()-(Integer.parseInt(elem[1])+1));
                    sbodyArray.add(""+arrEbody.get(arrEbody.size()-(Integer.parseInt(elem[1])+1)).toString().length());
                }
            }
            if(elem[0].equals("34")){
                ArrayList<String> arr=new ArrayList<String>();
                Object[] arr3d={elem[1],arr};
                arrEbody_3d.add(arr3d);

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[4],elem[5],elem[6]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                Object[] arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                ArrayList<Object> a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[4],elem[5]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[5],elem[6]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[6],elem[4]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                if(Integer.parseInt(elem[1])>0)
                    sbodyArray=(ArrayList<String>)arrSbody.get(Integer.parseInt(elem[1])-1);
                else
                    sbodyArray=(ArrayList<String>)arrSbody.get(arrSbody.size()-(Integer.parseInt(elem[1])+1));
                sbodyArray.add(""+arrEbody_3d.size());
            }
            if(elem[0].equals("36")){
                ArrayList<String> arr=new ArrayList<String>();
                Object[] arr3d={elem[1],arr};
                arrEbody_3d.add(arr3d);

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[4],elem[5]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                Object[] arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                ArrayList<Object> a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[6],elem[7],elem[8]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[6],elem[7],elem[4]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[4],elem[7],elem[8],elem[5]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[5],elem[8],elem[6],elem[3]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                if(Integer.parseInt(elem[1])>0)
                    sbodyArray=(ArrayList<String>)arrSbody.get(Integer.parseInt(elem[1])-1);
                else
                    sbodyArray=(ArrayList<String>)arrSbody.get(arrSbody.size()-(Integer.parseInt(elem[1])+1));
                sbodyArray.add(""+arrEbody_3d.size());
            }
            if(elem[0].equals("38")){
                ArrayList<String> arr=new ArrayList<String>();
                Object[] arr3d={elem[1],arr};
                arrEbody_3d.add(arr3d);

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[4],elem[5],elem[6]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                Object[] arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                ArrayList<Object> a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[7],elem[8],elem[9],elem[10]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[3],elem[7],elem[8],elem[4]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[4],elem[8],elem[9],elem[5]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[5],elem[9],elem[10],elem[6]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                arrPline.add(new String[]{""+(arrPline.size()+1),elem[6],elem[10],elem[7],elem[3]});
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                arr3d_1=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d_1[1];
                a.add(""+arrPsurf.size());

                if(Integer.parseInt(elem[1])>0)
                    sbodyArray=(ArrayList<String>)arrSbody.get(Integer.parseInt(elem[1])-1);
                else
                    sbodyArray=(ArrayList<String>)arrSbody.get(arrSbody.size()-(Integer.parseInt(elem[1])+1));
                sbodyArray.add(""+arrEbody_3d.size());
            }
        }

        //Сообщение 4
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\003-M.txt"))){
            GregorianCalendar cal4=new GregorianCalendar();
            String hour4=""+cal4.get(Calendar.HOUR_OF_DAY);
            String minute4=""+cal4.get(Calendar.MINUTE);
            String second4=""+cal4.get(Calendar.SECOND);
            if(hour4.length()==1)hour4="0"+hour4;
            if(minute4.length()==1)minute4="0"+minute4;
            if(second4.length()==1)second4="0"+second4;
            writer.write("4: "+hour4+":"+minute4+":"+second4+" преобразование данных");
        }
        catch(IOException ex){}
        //

        //Подготовка данных для записи в файл BSM
        strOut+="///Точки ("+N_knot+")\n";
        strOut+="@POINT\n";
        for(int i=0;i<arrKnot.size();i++)
            strOut+=""+(i+1)+" "+arrKnot.get(i)+"\n";
        strOut+="&\n\n";

        strOut+="///Полилинии ("+arrPline.size()+")\n";
        strOut+="@PLINE\n";
        for(Object pline:arrPline){
            String elemToOut="";
            String[] elem=(String[])pline;
            for(int j=0;j<elem.length;j++)
                if(j!=elem.length-1)elemToOut+=elem[j]+" ";
                else elemToOut+=elem[j]+"\n";
            strOut+=elemToOut;
        }
        strOut+="&\n\n";

        if(arrLoop.size()>0){
            strOut+="///Замкнутые линии ("+arrLoop.size()+")\n";
            strOut+="@LINELOOP\n";
            for(Object lineloop:arrLoop){
                String elemToOut="";
                String[] elem=(String[])lineloop;
                for(int j=0;j<elem.length;j++)
                    if(j!=elem.length-1)elemToOut+=elem[j]+" ";
                    else elemToOut+=elem[j]+"\n";
                strOut+=elemToOut;
            }
            strOut+="&\n\n";
        }

        if(arrPsurf.size()>0){
            strOut+="///Плоские элементы поверхностей ("+arrPsurf.size()+")\n";
            strOut+="@PSURF\n";
            for(Object psurf:arrPsurf){
                String elemToOut="";
                String[] elem=(String[])psurf;
                for(int j=0;j<elem.length;j++)
                    if(j!=elem.length-1)elemToOut+=elem[j]+" ";
                    else elemToOut+=elem[j]+"\n";
                strOut+=elemToOut;
            }
            strOut+="&\n\n";
        }

        int N_ebody=0, N_sbody=0, N_shell=0, N_bar=0;
        String strEbody="", strSbody="", strShell="", strBar="";
        for(int i=0;i<N_mate;i++){
            ArrayList<String> ebody=(ArrayList<String>)arrEbody.get(i);
            if(!ebody.isEmpty())
                for(int j=0;j<ebody.size();j++){
                    N_ebody++;
                    strEbody+=""+N_ebody+" "+ebody.get(j)+"\n";
                }

            ArrayList<String> sbody=(ArrayList<String>)arrSbody.get(i);
            if(!sbody.isEmpty()){
                N_sbody++;
                strSbody+=""+N_sbody+" "+(i+1)+" 1";
                for(String s:sbody)
                    strSbody+=" "+s;
                strSbody+="\n";
            }

            ArrayList<String> shell=(ArrayList<String>)arrShell.get(i);
            if(!shell.isEmpty())
                for(int j=0;j<shell.size();j++){
                    N_shell++;
                    strShell+=""+N_shell+" 1 "+(i+1)+" 0 "+shell.get(j)+"\n";
                }

            ArrayList<String> bar=(ArrayList<String>)arrBar.get(i);
            if(!bar.isEmpty())
                for(int j=0;j<bar.size();j++){
                    N_bar++;
                    strBar+=""+N_bar+" 1 "+(i+1)+" 0 1 "+bar.get(j)+"\n";
                }
        }

        for(Object ebody:arrEbody_3d){
            Object[] ebody3d=(Object[])ebody;
            ArrayList<String> ebody3dArr=(ArrayList<String>)ebody3d[1];
            N_ebody++;
            strEbody+=""+N_ebody;
            for(String s:ebody3dArr)
                strEbody+=" "+s;
            strEbody+="\n";
        }

        if(N_ebody>0){
            strOut+="///Элементарные тела ("+N_ebody+")\n";
            strOut+="@EBODY\n"+strEbody+"&\n\n";
        }
        if(N_sbody>0){
            strOut+="///Тела ("+N_sbody+")\n";
            strOut+="@SBODY\n"+strSbody+"&\n\n";
        }
        if(N_shell>0){
            strOut+="///Оболочки ("+N_shell+")\n";
            strOut+="@SHELL\n"+strShell+"&\n\n";
        }
        if(N_bar>0){
            strOut+="///Стержни ("+N_bar+")\n";
            strOut+="@BAR\n"+strBar+"&\n\n";
        }

        //узловые нагрузки
        String nload="///Узловые нагрузки ("+arrLakn.size()+")\n";
        nload+="@NLOAD\n";
        int load_num=0, maxLC=0;
        for(String lakn:arrLakn){
            String[] lakn1=lakn.split(" ");
            int num=Integer.parseInt(lakn1[0]);
            int dir=Integer.parseInt(lakn1[2]);
            int LC=Integer.parseInt(lakn1[3]);
            if(LC>maxLC)maxLC=LC;
            double loadValue=Double.parseDouble(lakn1[4]);
            if(dir==1 || dir==4)nload+=load_num+" "+LC+" "+num+" "+loadValue+" 0 0 0\n";
            if(dir==2 || dir==5)nload+=load_num+" "+LC+" "+num+" 0 "+loadValue+" 0 0\n";
            if(dir==3 || dir==6)nload+=load_num+" "+LC+" "+num+" 0 0 "+loadValue+" 0\n";
            load_num++;
        }
        nload+="&\n\n";
        //

        //нагрузки
        strOut+="///Масштаб отображения нагрузок (1)\n";
        strOut+="@LSCALE\n20000 1\n&\n\n";
        //

        //Отображение нагрузок
        if(maxLC>0){
            strOut+="///Отображения и названия загружений ("+maxLC+")\n";
            strOut+="@LCASENAME\n";
            for(int i=1;i<=maxLC;i++)
                strOut+=i+" 0\n";
            strOut+="&\n\n";
        }
        //
        if(arrLakn.size()>0)strOut+=nload;

        //Сообщение 5
        GregorianCalendar cal5=new GregorianCalendar();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\004-M.txt"))){
            String hour5=""+cal5.get(Calendar.HOUR_OF_DAY);
            String minute5=""+cal5.get(Calendar.MINUTE);
            String second5=""+cal5.get(Calendar.SECOND);
            if(hour5.length()==1)hour5="0"+hour5;
            if(minute5.length()==1)minute5="0"+minute5;
            if(second5.length()==1)second5="0"+second5;
            writer.write("5: "+hour5+":"+minute5+":"+second5+" запись в файл "+args[1]+"\\"+name);
        }
        catch(IOException ex){}
        //

        //
        long timeSpent=System.currentTimeMillis()-startTime;
        int hours=(int)(timeSpent/(60*60*1000));
        timeSpent-=(hours*60*60*1000);
        int minutes =(int)(timeSpent/(60 * 1000));
        timeSpent-=(minutes*60*1000);
        int seconds =(int)(timeSpent/(1000));
        time=hours+":"+minutes+":"+seconds;

        String strOut1=strOut;
        strOut="/// --------------------------------------------\n";
        strOut+="/// Преобразование FEA в BSMT.\n";
        strOut+="/// Исходный файл: "+args[0]+".\n";
        strOut+="/// Выходной файл: "+args[1]+"\\"+name+".\n";
        strOut+="/// Дата конвертации: "+cal5.get(Calendar.DAY_OF_MONTH)+"."
                    +(cal5.get(Calendar.MONTH)+1)+"."+cal5.get(Calendar.YEAR)+".\n";
        strOut+="/// Время конвертации: "+time+".\n";
        strOut+="/// ---------------------------------------------\n\n";

        strOut+="/// MAT="+N_mate+" ELSEC=1 POINT="+N_knot+" PLINE="+arrPline.size();
        if(arrLoop.size()>0)strOut+=" LINELOOP="+arrLoop.size();
        if(arrPsurf.size()>0)strOut+=" PSURF="+arrPsurf.size();
        if(N_ebody>0)strOut+=" EBODY="+N_ebody;
        if(N_sbody>0)strOut+=" SBODY="+N_sbody;
        if(N_shell>0)strOut+=" SHELL="+N_shell;
        if(N_bar>0)strOut+=" BAR="+N_bar;
        strOut+=" LSCALE=1";
        if(maxLC>0)strOut+=" LCASENAME="+maxLC;
        if(arrLakn.size()>0)strOut+=" NLOAD="+arrLakn.size();
        strOut+="\n\n\n";
        strOut+=strOut1;
        
        countObjects=N_mate+N_knot+arrPline.size()+arrLoop.size()+arrPsurf.size()
                +N_ebody+N_sbody+N_shell+N_bar+1+maxLC+arrLakn.size();
        return strOut;
    }
    
    //Сравнение конкретной пары координат
    public static boolean unicNum(double a,double b,double dx){
        if(Math.abs(a-b)<=dx)return true;
        return false;
    }
    
    //Определение, являются ли два набора координат одинаковыми с точностью до dx
    public static boolean unicPoint(Double[] coords1,Double[] coords2,double dx){
        boolean m=true;
        for(int i=0;i<3;i++)
            m=m && unicNum(coords1[i],coords2[i],dx);
        return m;
    }
    
    public static String pos2bsm(ArrayList<String> arrPos,String[] args,String name,long startTime){
        int N=0,N_b=0;
        String strAllObj="";
        for(String line:arrPos){
            if(line.contains("Pos-Liste =")){ //В этой строке находится список всех объектов геометрии в файле
                String[] arr=line.split("Pos-Liste =");
                strAllObj=arr[arr.length-1];
                N=arrPos.indexOf(line);
            }
            //Метка окончания размела геометрии (начало раздела нагрузок)
            if(line.contains("[Belastung]"))
                N_b=arrPos.indexOf(line);
        }
        if(strAllObj.substring(strAllObj.length()-1).equals("\\")){
            int i=N+1;
            while(strAllObj.substring(strAllObj.length()-1).equals("\\")){
                strAllObj=strAllObj.replace("\\",arrPos.get(i));
                i++;
            }
        }
        //Для того, чтобы определить, какие строки относятся к какому объекту, получаем номера строк,
        //в которых название объекта, т.е. к объекту относятся строки от этого номера до номера следующего объекта
        String[] arrAllObj=strAllObj.split(" ");
        ArrayList<Integer> arrNumObj=new ArrayList<Integer>();
        for(String obj:arrAllObj)
            for(String line:arrPos)
                if(line.contains(obj+" ={")){
                    arrNumObj.add(arrPos.indexOf(line));
                    break;
                }
        arrNumObj.add(N_b);
        
        HashMap<String,Object> dictD=new HashMap<String,Object>();
        ArrayList<Object> arrCoords=new ArrayList<Object>();
        HashMap<String,Integer> dictMatl=new HashMap<String,Integer>();
        HashMap<String,Integer> dictSec=new HashMap<String,Integer>();
        HashMap<String,String> dictMatlStr=new HashMap<String,String>();
        //В зависимости от типа объектов получаем координаты и другие свойства объектов
        for(int i=0;i<arrAllObj.length-1;i++){
            //плита
            if(arrAllObj[i].split("-")[0].equals("D")){
                dictD.put(arrAllObj[i],new HashMap<String,Object>());
                //Определение материала
                String strMatl="";
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++)
                    if(arrPos.get(j).trim().equals("Material ={")){
                        String[] matl=arrPos.get(j+1).trim().split("=");
                        if(!dictMatlStr.containsKey(matl[1]))
                            dictMatlStr.put(matl[1],""+(dictMatlStr.keySet().size()+1));
                        strMatl=dictMatlStr.get(matl[1]);
                        if(!dictMatl.containsKey(strMatl))
                            dictMatl.put(strMatl,dictMatl.keySet().size()+1);
                        break;
                    }
                //тип фигуры, массив координаты, номер материала
                arrCoords.add(new Object[]{0,new ArrayList<Double[]>(),dictMatl.get(strMatl)});
                HashMap<String,Object> coords=(HashMap<String,Object>)dictD.get(arrAllObj[i]);
                //Идем по строкам, выбирая нужные данные
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                    String[] coordsArr=arrPos.get(j).trim().split("=");
                    if(coordsArr[0].equals("X "))
                        coords.put("X",arrPos.get(j).split("X =")[1].split(" "));
                    if(coordsArr[0].equals("Y "))
                        coords.put("Y",arrPos.get(j).split("Y =")[1].split(" "));
                    if(coordsArr[0].equals("d "))
                        coords.put("d",arrPos.get(j).split("d =")[1]);
                    if(coordsArr[0].equals("Ursprung "))
                        coords.put("Z",arrPos.get(j).split("Ursprung =")[1].split(" ")[2]);
                }
                String[] xArr=(String[])coords.get("X");
                String[] yArr=(String[])coords.get("Y");
                
                int length=0;
                if(xArr.length==1)length=1;
                else length=xArr.length-1;
                for(int j=0;j<length;j++){
                    Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                    ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                    arr1.add(new Double[]{Double.parseDouble(xArr[j]),Double.parseDouble(yArr[j]),
                        Double.parseDouble(""+coords.get("Z"))-Double.parseDouble(""+coords.get("d"))/2});
                }
                for(int j=0;j<length;j++){
                    Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                    ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                    arr1.add(new Double[]{Double.parseDouble(xArr[j]),Double.parseDouble(yArr[j]),
                        Double.parseDouble(""+coords.get("Z"))+Double.parseDouble(""+coords.get("d"))/2});
                }
            }
            //Стена
            if(arrAllObj[i].split("-")[0].equals("W")){
                dictD.put(arrAllObj[i],new HashMap<String,Object>());
                //Определение материала
                String strMatl="";
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++)
                    if(arrPos.get(j).trim().equals("Material ={")){
                        String[] matl=arrPos.get(j+1).trim().split("=");
                        if(!dictMatlStr.containsKey(matl[1]))
                            dictMatlStr.put(matl[1],""+(dictMatl.keySet().size()+1));
                        strMatl=dictMatlStr.get(matl[1]);
                        if(!dictMatl.containsKey(strMatl))
                            dictMatl.put(strMatl,dictMatl.keySet().size()+1);
                        break;
                    }
                //
                arrCoords.add(new Object[]{0,new ArrayList<Double[]>(),dictMatl.get(strMatl)});
                HashMap<String,Object> coords=(HashMap<String,Object>)dictD.get(arrAllObj[i]);
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                    String[] coordsArr=arrPos.get(j).trim().split("=");
                    if(coordsArr[0].equals("X ") && arrPos.get(j-2).contains("Geometrie ={"))
                        coords.put("X",arrPos.get(j).split("X =")[1].split(" "));
                    if(coordsArr[0].equals("Y ") && arrPos.get(j-3).contains("Geometrie ={"))
                        coords.put("Y",arrPos.get(j).split("Y =")[1].split(" "));
                    if(coordsArr[0].equals("d "))
                        coords.put("d",arrPos.get(j).split("d =")[1]);
                    if(coordsArr[0].equals("X ") && arrPos.get(j-10).contains("Oberflaeche ={"))
                        coords.put("X_0",arrPos.get(j).split("X =")[1].split(" "));
                    if(coordsArr[0].equals("Y ") && arrPos.get(j-11).contains("Oberflaeche ={"))
                        coords.put("Y_0",arrPos.get(j).split("Y =")[1].split(" "));
                    if(coordsArr[0].equals("Ursprung ") && arrPos.get(j-16).contains("Oberflaeche ={"))
                        coords.put("Z",arrPos.get(j).split("Ursprung =")[1].split(" ")[2]);
                }
                String[] xArr=(String[])coords.get("X");
                String[] yArr=(String[])coords.get("Y");
                String[] x0Arr=(String[])coords.get("X_0");
                String[] y0Arr=(String[])coords.get("Y_0");
                if(Double.parseDouble(y0Arr[1])-Double.parseDouble(yArr[0])!=0)
                    coords.put("alfa",Math.atan((Double.parseDouble(x0Arr[1])-Double.parseDouble(x0Arr[0]))
                        /(Double.parseDouble(y0Arr[1])-Double.parseDouble(y0Arr[0]))));
                else coords.put("alfa",Math.atan(0));
                double alfa=Double.parseDouble(""+coords.get("alfa"));
                double d=Double.parseDouble(""+coords.get("d"));
                double z=Double.parseDouble(""+coords.get("Z"));
                for(int j=0;j<xArr.length-1;j++){
                    double x=Double.parseDouble(xArr[j]);
                    double y=Double.parseDouble(yArr[j]);                    
                    Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                    ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                    arr1.add(new Double[]{x*Math.cos(alfa)-d/2*Math.sin(alfa),x*Math.sin(alfa)+d/2*Math.cos(alfa),y+z});
                }
                for(int j=0;j<xArr.length-1;j++){
                    double x=Double.parseDouble(xArr[j]);
                    double y=Double.parseDouble(yArr[j]);
                    Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                    ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                    arr1.add(new Double[]{x*Math.cos(alfa)+d/2*Math.sin(alfa),x*Math.sin(alfa)-d/2*Math.cos(alfa),y+z});
                }
            }
            //Колонна
            if(arrAllObj[i].split("-")[0].equals("ST")){
                dictD.put(arrAllObj[i],new HashMap<String,Object>());
                //Определение материала
                String strMatl="";
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++)
                    if(arrPos.get(j).trim().equals("Material ={")){
                        String[] matl=arrPos.get(j+1).trim().split("=");
                        if(!dictMatlStr.containsKey(matl[1]))
                            dictMatlStr.put(matl[1],""+(dictMatlStr.keySet().size()+1));
                        strMatl=dictMatlStr.get(matl[1]);
                        if(!dictMatl.containsKey(strMatl))
                            dictMatl.put(strMatl,dictMatl.keySet().size()+1);
                        break;
                    }
                //
                HashMap<String,Object> coords=(HashMap<String,Object>)dictD.get(arrAllObj[i]);
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                    String[] coordsArr=arrPos.get(j).trim().split("=");
                    if(coordsArr[0].equals("X "))
                        coords.put("X",Double.parseDouble(arrPos.get(j).split("X =")[1]));
                    if(coordsArr[0].equals("Y "))
                        coords.put("Y",Double.parseDouble(arrPos.get(j).split("Y =")[1]));
                    if(coordsArr[0].equals("Sect "))
                        coords.put("Sect",arrPos.get(j).split("Sect =")[1]);
                    if(coordsArr[0].equals("l "))
                        coords.put("L",Double.parseDouble(arrPos.get(j).split(" l =")[1]));
                    if(coordsArr[0].equals("Alpha "))
                        coords.put("alpha",Double.parseDouble(arrPos.get(j).split("Alpha =")[1]));
                    if(coordsArr[0].equals("Ursprung "))
                        coords.put("Z",Double.parseDouble(arrPos.get(j).split("Ursprung =")[1].split(" ")[2]));
                }
                double X=Double.parseDouble(""+coords.get("X"));
                double Y=Double.parseDouble(""+coords.get("Y"));
                double Z=Double.parseDouble(""+coords.get("Z"));
                double L=Double.parseDouble(""+coords.get("L"));
                if(!dictSec.containsKey(coords.get("Sect")))
                    dictSec.put(coords.get("Sect").toString(),dictSec.keySet().size()+1);
                arrCoords.add(new Object[]{2,new ArrayList<Double[]>(),dictMatl.get(strMatl),dictSec.get(coords.get("Sect"))});
                
                Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                arr1.add(new Double[]{X,Y,Z-L});
                arr1.add(new Double[]{X,Y,Z});
            }
            //свая
            if(arrAllObj[i].split("-")[0].equals("PF")){
                dictD.put(arrAllObj[i],new HashMap<String,Object>());
                //Определение материала
                String strMatl="";
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++)
                    if(arrPos.get(j).trim().equals("Material ={")){
                        String[] matl=arrPos.get(j+1).trim().split("=");
                        if(!dictMatlStr.containsKey(matl[1]))
                            dictMatlStr.put(matl[1],""+(dictMatlStr.keySet().size()+1));
                        strMatl=dictMatlStr.get(matl[1]);
                        if(!dictMatl.containsKey(strMatl))
                            dictMatl.put(strMatl,dictMatl.keySet().size()+1);
                        break;
                    }
                //
                HashMap<String,Object> coords=(HashMap<String,Object>)dictD.get(arrAllObj[i]);
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                    String[] coordsArr=arrPos.get(j).trim().split("=");
                    if(coordsArr[0].equals("X "))
                        coords.put("X",Double.parseDouble(arrPos.get(j).split("X =")[1]));
                    if(coordsArr[0].equals("Y "))
                        coords.put("Y",Double.parseDouble(arrPos.get(j).split("Y =")[1]));
                    if(coordsArr[0].equals("b "))
                        coords.put("Sect","1 "+arrPos.get(j).split(" b =")[1]+" ");
                    if(coordsArr[0].equals("d ")){
                        String sect=""+coords.get("Sect");
                        coords.put("Sect",sect+arrPos.get(j).split(" d =")[1]);
                    }
                    if(coordsArr[0].equals("Durchmesser "))
                        coords.put("Sect","8 "+arrPos.get(j).split("Durchmesser =")[1]);
                    if(coordsArr[0].equals("l "))
                        coords.put("L",Double.parseDouble(arrPos.get(j).split(" l =")[1]));
                    if(coordsArr[0].equals("Alpha "))
                        coords.put("alpha",Double.parseDouble(arrPos.get(j).split("Alpha =")[1]));
                    if(coordsArr[0].equals("Ursprung "))
                        coords.put("Z",Double.parseDouble(arrPos.get(j).split("Ursprung =")[1].split(" ")[2]));
                }
                double X=Double.parseDouble(""+coords.get("X"));
                double Y=Double.parseDouble(""+coords.get("Y"));
                double Z=Double.parseDouble(""+coords.get("Z"));
                double L=Double.parseDouble(""+coords.get("L"));
                if(!dictSec.containsKey(coords.get("Sect")))
                    dictSec.put(coords.get("Sect").toString(),dictSec.keySet().size()+1);
                arrCoords.add(new Object[]{2,new ArrayList<Double[]>(),dictMatl.get(strMatl),dictSec.get(coords.get("Sect"))});
                Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                arr1.add(new Double[]{X,Y,Z-L});
                arr1.add(new Double[]{X,Y,Z});
            }
            //балка, стержень
            if(arrAllObj[i].split("-")[0].equals("UZ") || arrAllObj[i].split("-")[0].equals("ROD")){
                dictD.put(arrAllObj[i],new HashMap<String,Object>());
                //Определение материала
                String strMatl="";
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++)
                    if(arrPos.get(j).trim().equals("Material ={")){
                        String[] matl=arrPos.get(j+1).trim().split("=");
                        if(!dictMatlStr.containsKey(matl[1]))
                            dictMatlStr.put(matl[1],""+(dictMatlStr.keySet().size()+1));
                        strMatl=dictMatlStr.get(matl[1]);
                        if(!dictMatl.containsKey(strMatl))
                            dictMatl.put(strMatl,dictMatl.keySet().size()+1);
                        break;
                    }
                //
                int typrLocks=-1;
                HashMap<String,Object> coords=(HashMap<String,Object>)dictD.get(arrAllObj[i]);
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                    String coordsArr=arrPos.get(j).trim();
                    if(coordsArr.equals("Typ =global")){
                        typrLocks=0;
                        break;
                    }
                    if(coordsArr.equals("Typ =3-Winkel")){
                        typrLocks=1;
                        break;
                    }
                }
                arrCoords.add(new Object[]{2,new ArrayList<Double[]>(),1});
                if(typrLocks==1){
                    for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                        String[] coordsArr=arrPos.get(j).trim().split("=");
                        if(coordsArr[0].equals("X "))
                            coords.put("L",Double.parseDouble(arrPos.get(j).split("X =")[1].split(" ")[1]));
                        if(coordsArr[0].equals("b "))
                            coords.put("Sect","1 "+arrPos.get(j).split(" b =")[1]+" ");
                        if(coordsArr[0].equals("d ")){
                            String sect=""+coords.get("Sect");
                            coords.put("Sect",sect+arrPos.get(j).split(" d =")[1]);
                        }
                        if(coordsArr[0].equals("Ursprung ")){
                            coords.put("X",Double.parseDouble(arrPos.get(j).split("Ursprung =")[1].split(" ")[0]));
                            coords.put("Y",Double.parseDouble(arrPos.get(j).split("Ursprung =")[1].split(" ")[1]));
                            coords.put("Z",Double.parseDouble(arrPos.get(j).split("Ursprung =")[1].split(" ")[2]));
                        }
                        if(coordsArr[0].equals("alpha "))
                            coords.put("alpha",Double.parseDouble(arrPos.get(j).split("alpha =")[1]));
                        if(coordsArr[0].equals("beta "))
                            coords.put("beta",Double.parseDouble(arrPos.get(j).split("beta =")[1]));
                        if(coordsArr[0].equals("gamma "))
                            coords.put("gamma",Double.parseDouble(arrPos.get(j).split("gamma =")[1]));
                    }
                    double L=Double.parseDouble(""+coords.get("L"));
                    double x=Double.parseDouble(""+coords.get("X"));
                    double y=Double.parseDouble(""+coords.get("Y"));
                    double z=Double.parseDouble(""+coords.get("Z"));
                    double a=Double.parseDouble(""+coords.get("alpha"));
                    double b=Double.parseDouble(""+coords.get("beta"));
                    double g=Double.parseDouble(""+coords.get("gamma"));
                    Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                    ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                    arr1.add(new Double[]{x,y,z});
                    arr1.add(new Double[]{x+L*Math.cos(b)*Math.cos(a)*Math.cos(g),
                        y+L*Math.cos(b)*Math.sin(a)*Math.cos(g),z+L*Math.sin(b)*Math.sin(g)});
                }
                if(typrLocks==0){
                    for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                        String[] coordsArr=arrPos.get(j).trim().split("=");
                        if(coordsArr[0].equals("X "))
                            coords.put("X",arrPos.get(j).split("X =")[1].split(" "));
                        if(coordsArr[0].equals("X ") && arrPos.get(j-2).trim().equals("Geometrie ={"))
                            coords.put("X",arrPos.get(j).split("X =")[1].split(" "));
                        if(coordsArr[0].equals("Y ") && arrPos.get(j-3).trim().equals("Geometrie ={"))
                            coords.put("Y",arrPos.get(j).split("Y =")[1].split(" "));
                        if(coordsArr[0].equals("b "))
                            coords.put("Sect","1 "+arrPos.get(j).split(" b =")[1]+" ");
                        if(coordsArr[0].equals("d ")){
                            String sect=""+coords.get("Sect");
                            coords.put("Sect",sect+arrPos.get(j).split(" d =")[1]);
                        }
                        if(coordsArr[0].equals("Ursprung "))
                            coords.put("Z",arrPos.get(j).split("Ursprung =")[1].split(" ")[2]);
                    }
                    String[] x=(String[])coords.get("X");
                    String[] y=(String[])coords.get("Y");
                    double z=Double.parseDouble(""+coords.get("Z"));
                    for(int j=0;j<x.length;j++){
                        Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                        ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                        arr1.add(new Double[]{Double.parseDouble(x[j]),Double.parseDouble(y[j]),z});
                    }
                }
            }
            //Отверстие
            if(arrAllObj[i].split("-")[0].equals("AUSP")){
                dictD.put(arrAllObj[i],new HashMap<String,Object>());
                //Определение материала
                String strMatl="0";
                if(!dictMatl.containsKey(strMatl))
                    dictMatl.put(strMatl,dictMatl.keySet().size()+1);
                //
                arrCoords.add(new Object[]{3,new ArrayList<Double[]>(),dictMatl.get(strMatl)});
                int typrLocks=-1;
                HashMap<String,Object> coords=(HashMap<String,Object>)dictD.get(arrAllObj[i]);
                for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                    String coordsArr=arrPos.get(j).trim();
                    //В стене
                    if(coordsArr.equals("Typ =3-Vektoren")){
                        typrLocks=1;
                        break;
                    }
                    //В рампе или в плите
                    if(coordsArr.equals("Typ =3-Punkte") || coordsArr.equals("Typ =global")){
                        typrLocks=2;
                        break;
                    }
                }
                if(typrLocks==2){
                    for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                        String[] coordsArr=arrPos.get(j).trim().split("=");
                        if(coordsArr[0].equals("X "))
                            coords.put("X",arrPos.get(j).split("X =")[1].split(" "));
                        if(coordsArr[0].equals("Y "))
                            coords.put("Y",arrPos.get(j).split("Y =")[1].split(" "));
                        if(coordsArr[0].equals("Ursprung "))
                            coords.put("Z",arrPos.get(j).split("Ursprung =")[1].split(" ")[2]);
                    }
                    String[] x=(String[])coords.get("X");
                    String[] y=(String[])coords.get("Y");
                    double z=Double.parseDouble(""+coords.get("Z"));
                    for(int j=0;j<x.length-1;j++){
                        Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                        ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                        arr1.add(new Double[]{Double.parseDouble(x[j]),Double.parseDouble(y[j]),z});
                    }
                }
                if(typrLocks==1){
                    for(int j=arrNumObj.get(i);j<arrNumObj.get(i+1);j++){
                        String[] coordsArr=arrPos.get(j).trim().split("=");
                        if(coordsArr[0].equals("X ") && arrPos.get(j-2).trim().equals("Geometrie ={"))
                            coords.put("X",arrPos.get(j).split("X =")[1].split(" "));
                        if(coordsArr[0].equals("Y ") && arrPos.get(j-3).trim().equals("Geometrie ={"))
                            coords.put("Y",arrPos.get(j).split("Y =")[1].split(" "));
                        if(coordsArr[0].equals("X ") && arrPos.get(j-10).trim().equals("Oberflaeche ={"))
                            coords.put("X_0",arrPos.get(j).split("X =")[1].split(" "));
                        if(coordsArr[0].equals("Y ") && arrPos.get(j-11).trim().equals("Oberflaeche ={"))
                            coords.put("Y_0",arrPos.get(j).split("Y =")[1].split(" "));
                        if(coordsArr[0].equals("Ursprung ") && arrPos.get(j-16).trim().equals("Oberflaeche ={"))
                            coords.put("Z",arrPos.get(j).split("Ursprung =")[1].split(" ")[2]);
                    }
                    String[] x0=(String[])coords.get("X_0");
                    String[] y0=(String[])coords.get("Y_0");
                    coords.put("alpha",Math.atan((Double.parseDouble(x0[1])-Double.parseDouble(x0[0]))/
                            (Double.parseDouble(y0[1])-Double.parseDouble(y0[0]))));
                    double alfa=Double.parseDouble(""+coords.get("alpha"));
                    double z=Double.parseDouble(""+coords.get("Z"));
                    String[] xArr=(String[])coords.get("X");
                    String[] yArr=(String[])coords.get("Y");
                    for(int j=0;j<xArr.length-1;j++){
                        double x=Double.parseDouble(xArr[j]);
                        double y=Double.parseDouble(yArr[j]);
                        Object[] arr=(Object[])arrCoords.get(arrCoords.size()-1);
                        ArrayList<Double[]> arr1=(ArrayList<Double[]>)arr[1];
                        arr1.add(new Double[]{x*Math.cos(alfa),x*Math.sin(alfa),y+z});
                    }
                }
            }
        }
        
        //Сообщение 3
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\002-M.txt"))){
            GregorianCalendar cal3=new GregorianCalendar();
            String hour3=""+cal3.get(Calendar.HOUR_OF_DAY);
            String minute3=""+cal3.get(Calendar.MINUTE);
            String second3=""+cal3.get(Calendar.SECOND);
            if(hour3.length()==1)hour3="0"+hour3;
            if(minute3.length()==1)minute3="0"+minute3;
            if(second3.length()==1)second3="0"+second3;
            writer.write("3: "+hour3+":"+minute3+":"+second3+" окончание обработки данных");
        }
        catch(IOException ex){}
        //
        
        //Определение уникального списка вершин с точностью до Dx по каждой координате
        double dx=0.01;
        ArrayList<Double[]> arrUnicVert=new ArrayList<Double[]>();
        for(Object coords:arrCoords){
            Object[] arr=(Object[])coords;
            ArrayList<Double[]> arrCoord=(ArrayList<Double[]>)arr[1];
            for(Double[] coord:arrCoord)
                arrUnicVert.add(coord);
        }
        for(int m=0;m<arrUnicVert.size();m++)
            for(int q=0;q<arrUnicVert.size();q++)
                if(arrUnicVert.get(m)!=null && arrUnicVert.get(q)!=null)
                    if(m!=q && unicPoint(arrUnicVert.get(m),arrUnicVert.get(q),dx))
                        arrUnicVert.set(q,null);
        int m=0;
        while(m<arrUnicVert.size())
            if(arrUnicVert.get(m)==null)arrUnicVert.remove(m);
            else m++;
        //Сопоставление точкам их номеров в arrCoords
        for(Object coord:arrCoords){
            Object[] arr=(Object[])coord;
            ArrayList<Object> arrCoord=(ArrayList<Object>)arr[1];
            for(int j=0;j<arrCoord.size();j++)
                for(m=0;m<arrUnicVert.size();m++){
                    Double[] arr1=(Double[])arrCoord.get(j);
                    if(unicPoint(arr1,arrUnicVert.get(m),dx)){
                        arrCoord.set(j,m+1);
                        break;
                    }
                }
        }
        
        //Выбираются данные из файла POS, связанные с нагрузками
        int n=0,n_b=0;
        strAllObj="";
        for(String line:arrPos){
            if(line.contains("Last-Liste =")){ //В этой строке все объекты, связанные с нагрузками
                String[] arr=line.split("Last-Liste =");
                strAllObj=arr[arr.length-1];
                n=arrPos.indexOf(line);
            }
            if(line.contains("[Gruppen]")) //Метка окончания раздела нагрузок
                n_b=arrPos.indexOf(line);
        }
        String[] arrAllObjL=strAllObj.split(" ");
        ArrayList<Integer> arrNumObjL=new ArrayList<Integer>();
        for(String obj:arrAllObjL)
            for(String line:arrPos)
                if(line.contains(obj+" ={")){
                    arrNumObjL.add(arrPos.indexOf(line));
                    break;
                }
        arrNumObjL.add(n_b);
        //Обработка строк, связанных с нагрузками
        ArrayList<String[]> load=new ArrayList<String[]>();
        for(int i=0;i<arrAllObjL.length;i++){
            //LF-номер точки
            //FG-степень свободы
            //Gen-номер загружения
            if(arrAllObjL[i].split("-")[0].equals("FLLA")){
                String[] flla=new String[7];
                for(int j=arrNumObjL.get(i);j<arrNumObjL.get(i+1);j++){
                    String[] sArr=arrPos.get(j).trim().split("=");
                    if(sArr[0].equals("FG "))
                        flla[0]=sArr[1];
                    if(sArr[0].equals("Gen "))
                        flla[1]=sArr[1];
                    if(sArr[0].equals("LF "))
                        flla[2]=sArr[1];
                    if(sArr[0].equals("Last ")){
                        if(flla[0].equals("1") || flla[0].equals("4"))
                            flla[3]=sArr[1].split(", ")[0];
                        if(flla[0].equals("2") || flla[0].equals("5"))
                            flla[4]=sArr[1].split(",")[1];
                        if(flla[0].equals("3") || flla[0].equals("6"))
                            flla[5]=sArr[1].split(", ")[2];
                    }
                }
                flla[6]="0";
                load.add(flla);
            }
            if(arrAllObjL[i].split("-")[0].equals("PULA")){
                String[] pula=new String[7];
                for(int j=arrNumObjL.get(i);j<arrNumObjL.get(i+1);j++){
                    String[] sArr=arrPos.get(j).trim().split("=");
                    if(sArr[0].equals("FG "))
                        pula[0]=sArr[1];
                    if(sArr[0].equals("Gen "))
                        pula[1]=sArr[1];
                    if(sArr[0].equals("LF "))
                        pula[2]=sArr[1];
                    if(sArr[0].equals("Last ")){
                        if(pula[0].equals("1") || pula[0].equals("4"))
                            pula[3]=sArr[1];
                        if(pula[0].equals("2") || pula[0].equals("5"))
                            pula[4]=sArr[1];
                        if(pula[0].equals("3") || pula[0].equals("6"))
                            pula[5]=sArr[1];
                    }
                }
                pula[6]="0";
                load.add(pula);
            }
            if(arrAllObjL[i].split("-")[0].equals("PUVF")){
                String[] puvf=new String[7];
                for(int j=arrNumObjL.get(i);j<arrNumObjL.get(i+1);j++){
                    String[] sArr=arrPos.get(j).trim().split("=");
                    if(sArr[0].equals("FG "))
                        puvf[0]=sArr[1];
                    if(sArr[0].equals("Gen "))
                        puvf[1]=sArr[1];
                    if(sArr[0].equals("LF "))
                        puvf[2]=sArr[1];
                    if(sArr[0].equals("Weg ")){
                        if(puvf[0].equals("1") || puvf[0].equals("4"))
                            puvf[3]=sArr[1];
                        if(puvf[0].equals("2") || puvf[0].equals("5"))
                            puvf[4]=sArr[1];
                        if(puvf[0].equals("3") || puvf[0].equals("6"))
                            puvf[5]=sArr[1];
                    }
                }
                puvf[6]="0";
                load.add(puvf);
            }
            if(arrAllObjL[i].split("-")[0].equals("LILA")){
                String[] lila=new String[7];
                for(int j=arrNumObjL.get(i);j<arrNumObjL.get(i+1);j++){
                    String[] sArr=arrPos.get(j).trim().split("=");
                    if(sArr[0].equals("FG "))
                        lila[0]=sArr[1];
                    if(sArr[0].equals("Gen "))
                        lila[1]=sArr[1];
                    if(sArr[0].equals("LF "))
                        lila[2]=sArr[1];
                    if(sArr[0].equals("Last ")){
                        if(lila[0].equals("1") || lila[0].equals("4"))
                            lila[3]=sArr[1].split(", ")[0];
                        if(lila[0].equals("2") || lila[0].equals("5"))
                            lila[4]=sArr[1].split(", ")[1];
                    }
                    if(sArr[0].equals("Ursprung "))
                        if(lila[0].equals("3") || lila[0].equals("6"))
                            lila[5]=sArr[1].split(", ")[2];
                }
                lila[6]="0";
                load.add(lila);
            }
        }
        //
        //Подготовка данных для записи в файл BSM
        String strOut="///Материалы ("+dictMatl.keySet().size()+")\n";
        strOut+="@MAT\n";
        for(String key:dictMatl.keySet())
            strOut+=dictMatl.get(key)+" "+key+"\n";
        strOut+="&\n\n";
        //Сечения
        strOut+="///Поперечные сечения элемента ("+dictSec.keySet().size()+")\n";
        strOut+="@ELSEC\n";
        for(String key:dictSec.keySet())
            strOut+=dictSec.get(key)+" "+key+"\n";
        strOut+="&\n\n";
        //Создаем списки для Pline, Loop и т.д.
        ArrayList<Object> arrPline=new ArrayList<Object>();
        ArrayList<Object> arrLoop=new ArrayList<Object>();
        ArrayList<Object> arrPsurf=new ArrayList<Object>();
        ArrayList<Object> arrEbody=new ArrayList<Object>();
        ArrayList<Object> arrSbody=new ArrayList<Object>();
        ArrayList<Object> arrShell=new ArrayList<Object>();
        ArrayList<Object> arrBar=new ArrayList<Object>();
        for(int i=0;i<dictMatl.keySet().size();i++){
            arrEbody.add(new ArrayList<String>());
            arrSbody.add(new ArrayList<String>());
            arrShell.add(new ArrayList<String>());
            arrBar.add(new ArrayList<String>());
        }
        ArrayList<Object> arrEbody_3d=new ArrayList<Object>();
        
        ArrayList<String> sbodyArray=null;
        ArrayList<String> barArray=null;
        ArrayList<String> shellArray=null;
        //Перебираем все объекты и в зависимости от типа элемента создаем для них Pline, Psurf и т.п.
        for(Object coord:arrCoords){
            Object[] elem=(Object[])coord;
            //Если элемент параллелепипед
            if(Integer.parseInt(""+elem[0])==0){
                arrEbody_3d.add(new Object[]{elem[2],new ArrayList<String>()}); //Добавляем массив для тела
                ArrayList<Integer> elem1=(ArrayList<Integer>)elem[1];
                int Nv=elem1.size()/2; //Количество пар вершин
                ArrayList<Integer> p=new ArrayList<Integer>();
                for(int j=0;j<Nv;j++)
                    p.add(elem1.get(j));
                ArrayList<Object> pline=new ArrayList<Object>();
                pline.add(""+(arrPline.size()+1));
                for(int j:p)
                    pline.add(j);
                arrPline.add(pline);
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                
                Object[] arr3d=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                ArrayList<Object> a=(ArrayList)arr3d[1];
                a.add(""+arrPsurf.size());
                
                p=new ArrayList<Integer>();
                for(int j=0;j<Nv;j++)
                    p.add(elem1.get(j+Nv));
                pline=new ArrayList<Object>();
                pline.add(""+(arrPline.size()+1));
                for(int j:p)
                    pline.add(j);
                arrPline.add(pline);
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                
                arr3d=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                a=(ArrayList)arr3d[1];
                a.add(""+arrPsurf.size());
                
                for(int j=0;j<Nv;j++){
                    if(j+1+Nv!=2*Nv){
                        p=new ArrayList<Integer>();
                        p.add(elem1.get(j));
                        p.add(elem1.get(j+1));
                        p.add(elem1.get(j+Nv+1));
                        p.add(elem1.get(j+Nv));
                    }
                    else{
                        p=new ArrayList<Integer>();
                        p.add(elem1.get(j));
                        p.add(elem1.get(0));
                        p.add(elem1.get(Nv));
                        p.add(elem1.get(j+Nv));
                    }
                    pline=new ArrayList<Object>();
                    pline.add(""+(arrPline.size()+1));
                    for(int k:p)
                        pline.add(k);
                    arrPline.add(pline);
                    arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                    arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                    
                    arr3d=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                    a=(ArrayList)arr3d[1];
                    a.add(""+arrPsurf.size());
                }
                if(Integer.parseInt(""+elem[2])>0)
                    sbodyArray=(ArrayList<String>)arrSbody.get(Integer.parseInt(""+elem[2])-1);
                else
                    sbodyArray=(ArrayList<String>)arrSbody.get(arrSbody.size()-(Integer.parseInt(""+elem[2])+1));
                sbodyArray.add(""+arrEbody_3d.size());
            }
            //Пирамида
            if(Integer.parseInt(""+elem[0])==1){
                arrEbody_3d.add(new Object[]{elem[2],new ArrayList<String>()});
                ArrayList<Integer> elem1=(ArrayList<Integer>)elem[1];
                int Nv=elem1.size();
                ArrayList<Integer> p=new ArrayList<Integer>();
                for(int j=1;j<Nv;j++)
                    p.add(elem1.get(j));
                ArrayList<Object> pline=new ArrayList<Object>();
                pline.add(""+(arrPline.size()+1));
                for(int j:p)
                    pline.add(j);
                arrPline.add(pline);
                arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                
                Object[] arr3d=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                ArrayList<Object> a=(ArrayList)arr3d[1];
                a.add(""+arrPsurf.size());
                
                for(int j=1;j<Nv;j++){
                    pline=new ArrayList<Object>();
                    pline.add(""+(arrPline.size()+1));
                    for(int k=0;k<p.size();k++)
                        //pline.add(p.get(j));
                        pline.add(p.get(k));
                    arrPline.add(pline);
                    arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                    arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                    
                    arr3d=(Object[])arrEbody_3d.get(arrEbody_3d.size()-1);
                    a=(ArrayList)arr3d[1];
                    a.add(""+arrPsurf.size());
                }
                if(Integer.parseInt(""+elem[2])>0)
                    sbodyArray=(ArrayList<String>)arrSbody.get(Integer.parseInt(""+elem[2])-1);
                else
                    sbodyArray=(ArrayList<String>)arrSbody.get(arrSbody.size()-(Integer.parseInt(""+elem[2])+1));
                sbodyArray.add(""+arrEbody_3d.size());
            }
            //Линейный элемент
            if(Integer.parseInt(""+elem[0])==2){
                ArrayList<Integer> elem1=(ArrayList<Integer>)elem[1];
                arrPline.add(new String[]{""+(arrPline.size()+1),""+elem1.get(0),""+elem1.get(1)});
                if(Integer.parseInt(""+elem[2])>0)
                    barArray=(ArrayList<String>)arrBar.get(Integer.parseInt(""+elem[2])-1);
                else
                    barArray=(ArrayList<String>)arrBar.get(arrBar.size()-(Integer.parseInt(""+elem[2])+1));
                barArray.add(""+arrPline.size());
            }
            //Плоский элемент
            if(Integer.parseInt(""+elem[0])==3){
                ArrayList<Integer> elem1=(ArrayList<Integer>)elem[1];
                int Nv=elem1.size();
                ArrayList<Integer> p=new ArrayList<Integer>();
                if(Nv!=0){
                    for(int j=0;j<Nv;j++)
                        p.add(elem1.get(j));
                    ArrayList<Object> pline=new ArrayList<Object>();
                    pline.add(""+(arrPline.size()+1));
                    for(int j:p)
                        pline.add(j);
                    arrPline.add(pline);
                    arrLoop.add(new String[]{""+(arrLoop.size()+1),"1",""+arrPline.size()});
                    arrPsurf.add(new String[]{""+(arrPsurf.size()+1),""+arrLoop.size()});
                    if(Integer.parseInt(""+elem[2])>0)
                        shellArray=(ArrayList<String>)arrShell.get(Integer.parseInt(""+elem[2])-1);
                    else
                        shellArray=(ArrayList<String>)arrShell.get(arrShell.size()-(Integer.parseInt(""+elem[2])+1));
                    shellArray.add(""+arrPsurf.size());
                }
            }
        }
        
        //Сообщение 4
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\003-M.txt"))){
            GregorianCalendar cal4=new GregorianCalendar();
            String hour4=""+cal4.get(Calendar.HOUR_OF_DAY);
            String minute4=""+cal4.get(Calendar.MINUTE);
            String second4=""+cal4.get(Calendar.SECOND);
            if(hour4.length()==1)hour4="0"+hour4;
            if(minute4.length()==1)minute4="0"+minute4;
            if(second4.length()==1)second4="0"+second4;
            writer.write("4: "+hour4+":"+minute4+":"+second4+" преобразование данных");
        }
        catch(IOException ex){}
        //
        
        //Записываем все в файл BSM
        strOut+="///Точки ("+arrUnicVert.size()+")\n";
        strOut+="@POINT\n";
        N=1;
        for(Double[] point:arrUnicVert){
            strOut+=N;
            for(double a:point)
                strOut+=" "+a;
            strOut+="\n";
            N++;
        }
        strOut+="&\n\n";
        
        strOut+="///Полилинии ("+arrPline.size()+")\n";
        strOut+="@PLINE\n";
        for(Object plines:arrPline)
            if(plines instanceof ArrayList){
                ArrayList<Object> pline=(ArrayList)plines;
                for(int j=0;j<pline.size();j++)
                    if(j!=pline.size()-1)strOut+=pline.get(j)+" ";
                    else strOut+=pline.get(j)+"\n";
            }
            else if(plines instanceof String[]){
                String[] pline=(String[])plines;
                for(int j=0;j<pline.length;j++)
                    if(j!=pline.length-1)strOut+=pline[j]+" ";
                    else strOut+=pline[j]+"\n";
            }
        strOut+="&\n\n";
        
        if(arrLoop.size()>0){
            strOut+="///Замкнутые линии ("+arrLoop.size()+")\n";
            strOut+="@LINELOOP\n";
            for(Object lineloop:arrLoop){
                String[] sArr=(String[])lineloop;
                for(int j=0;j<sArr.length;j++)
                    if(j!=sArr.length-1)strOut+=sArr[j]+" ";
                    else strOut+=sArr[j]+"\n";
            }
            strOut+="&\n\n";
        }
        
        if(arrPsurf.size()>0){
            strOut+="///Плоские элементы поверхностей ("+arrPsurf.size()+")\n";
            strOut+="@PSURF\n";
            for(Object psurf:arrPsurf){
                String[] sArr=(String[])psurf;
                for(int j=0;j<sArr.length;j++)
                    if(j!=sArr.length-1)strOut+=sArr[j]+" ";
                    else strOut+=sArr[j]+"\n";
            }
            strOut+="&\n\n";
        }
        
        int N_ebody=0, N_sbody=0, N_shell=0, N_bar=0;
        String strEbody="", strSbody="", strShell="", strBar="";
        for(int i=0;i<dictMatl.keySet().size();i++){
            ArrayList<String> sbody=(ArrayList<String>)arrSbody.get(i);
            if(sbody.size()!=0){
                N_sbody++;
                strSbody+=N_sbody+" "+(i+1)+" 1";
                for(String s:sbody)
                    strSbody+=" "+s;
                strSbody+="\n";
            }
            ArrayList<String> shell=(ArrayList<String>)arrShell.get(i);
            if(shell.size()!=0)
                for(String shellS:shell){
                    N_shell++;
                    strShell+=N_shell+" 1 "+(i+1)+" 0 "+shellS+"\n";
                }
            ArrayList<String> bar=(ArrayList<String>)arrBar.get(i);
            if(bar.size()!=0)
                for(String barS:bar){
                    N_bar++;
                    strBar+=N_bar+" 1 "+(i+1)+" 0 1 "+barS+"\n";
                }
        }
        for(Object ebody:arrEbody_3d){
            Object[] ebody3d=(Object[])ebody;
            N_ebody++;
            ebody3d[1]=ebody3d[1].toString().replace("[","");
            ebody3d[1]=ebody3d[1].toString().replace("]","");
            ebody3d[1]=ebody3d[1].toString().replaceAll(",","");
            strEbody+=N_ebody+" "+ebody3d[1]+"\n";
        }
        if(N_ebody>0){
            strOut+="///Элементарные тела ("+N_ebody+")\n";
            strOut+="@EBODY\n"+strEbody+"&\n\n";
        }
        if(N_sbody>0){
            strOut+="///Тела ("+N_sbody+")\n";
            strOut+="@SBODY\n"+strSbody+"&\n\n";
        }
        if(N_shell>0){
            strOut+="///Оболочки ("+N_shell+")\n";
            strOut+="@SHELL\n"+strShell+"&\n\n";
        }
        if(N_bar>0){
            strOut+="///Стержни ("+N_bar+")\n";
            strOut+="@BAR\n"+strBar+"&\n\n";
        }
        
        //нагрузки
        String nload="///Узловые нагрузки ("+load.size()+")\n";
        nload+="@NLOAD\n";
        int load_num=0;
        int maxLC=0;
        for(String[] loadS:load){
            int dir=Integer.parseInt(loadS[0]);
            int LC=Integer.parseInt(loadS[1])+1;
            int num=Integer.parseInt(loadS[2]);
            if(LC>maxLC)maxLC=LC;
            if(dir==1 || dir==4)nload+=load_num+" "+LC+" "+num+" "+Double.parseDouble(loadS[3])+" 0 0 0\n";
            if(dir==2 || dir==5)nload+=load_num+" "+LC+" "+num+" 0 "+Double.parseDouble(loadS[4])+" 0 0\n";
            if(dir==3 || dir==6)nload+=load_num+" "+LC+" "+num+" 0 0 "+Double.parseDouble(loadS[5])+" 0\n";
            load_num++;
        }
        nload+="&\n\n";
        
        strOut+="///Масштаб отображения нагрузок (1)\n";
        strOut+="@LSCALE\n20000 1\n&\n\n";
        
        if(maxLC>0){
            strOut+="///Отображения и названия загружений ("+maxLC+")\n";
            strOut+="@LCASENAME\n";
            for(int i=1;i<=maxLC;i++)
                strOut+=i+" 0\n";
            strOut+="&\n\n";
        }
        
        if(load.size()>0)strOut+=nload;
        //
        
        //Сообщение 5
        GregorianCalendar cal5=new GregorianCalendar();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\004-M.txt"))){
            String hour5=""+cal5.get(Calendar.HOUR_OF_DAY);
            String minute5=""+cal5.get(Calendar.MINUTE);
            String second5=""+cal5.get(Calendar.SECOND);
            if(hour5.length()==1)hour5="0"+hour5;
            if(minute5.length()==1)minute5="0"+minute5;
            if(second5.length()==1)second5="0"+second5;
            writer.write("5: "+hour5+":"+minute5+":"+second5+" запись в файл "+args[1]+"\\"+name);
        }
        catch(IOException ex){}
        //
        
        //
        time="";
        long timeSpent=System.currentTimeMillis()-startTime;
        int hours=(int)(timeSpent/(60*60*1000));
        timeSpent-=(hours*60*60*1000);
        int minutes =(int)(timeSpent/(60 * 1000));
        timeSpent-=(minutes*60*1000);
        int seconds =(int)(timeSpent/(1000));
        time=hours+":"+minutes+":"+seconds;
        
        String strOut1=strOut;
        strOut="/// --------------------------------------------\n";
        strOut+="/// Преобразование POS в BSMT.\n";
        strOut+="/// Исходный файл: "+args[0]+".\n";
        strOut+="/// Выходной файл: "+args[1]+"\\"+name+".\n";
        strOut+="/// Дата конвертации: "+cal5.get(Calendar.DAY_OF_MONTH)+"."
                    +(cal5.get(Calendar.MONTH)+1)+"."+cal5.get(Calendar.YEAR)+".\n";
        strOut+="/// Время конвертации: "+time+".\n";
        strOut+="/// ---------------------------------------------\n\n";
        
        strOut+="/// MAT="+dictMatl.keySet().size()+" ELSEC="+dictSec.keySet().size()
                +" POINT="+arrUnicVert.size()+" PLINE="+arrPline.size();
        if(arrLoop.size()>0)strOut+=" LINELOOP="+arrLoop.size();
        if(arrPsurf.size()>0)strOut+=" PSURF="+arrPsurf.size();
        if(N_ebody>0)strOut+=" EBODY="+N_ebody;
        if(N_sbody>0)strOut+=" SBODY="+N_sbody;
        if(N_shell>0)strOut+=" SHELL="+N_shell;
        if(N_bar>0)strOut+=" BAR="+N_bar;
        strOut+=" LSCALE=1";
        if(maxLC>0)strOut+=" LCASENAME="+maxLC;
        if(load.size()>0)strOut+=" NLOAD="+load.size();
        strOut+="\n\n\n";
        strOut+=strOut1;
        
        countObjects=dictMatl.keySet().size()+dictSec.keySet().size()+arrUnicVert.size()
                +arrPline.size()+arrLoop.size()+arrPsurf.size()+N_ebody+N_sbody+N_shell
                +N_bar+1+maxLC+load.size();
        return strOut;
    }
    
    public static String ke2bsm(String[] args,File file,long startTime){
        String name=args[0]+"\\AM-00";
        file=new File(name);
        
        String[] list=file.list();
        for(String s:list)
            if(s.endsWith(".bsmt"))
                name+="\\"+s;
        file=new File(name);
        String fileName=file.getName();
        int index1=fileName.lastIndexOf(".");
        String fileName1=fileName.substring(0,index1);
        nameKE=fileName1;
        
        //Сообщение 3
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\002-M.txt"))){
            GregorianCalendar cal3=new GregorianCalendar();
            String hour3=""+cal3.get(Calendar.HOUR_OF_DAY);
            String minute3=""+cal3.get(Calendar.MINUTE);
            String second3=""+cal3.get(Calendar.SECOND);
            if(hour3.length()==1)hour3="0"+hour3;
            if(minute3.length()==1)minute3="0"+minute3;
            if(second3.length()==1)second3="0"+second3;
            writer.write("3: "+hour3+":"+minute3+":"+second3+" окончание обработки данных");
        }
        catch(IOException ex){}
        //
        
        //Точки
        name=args[0]+"\\FM-01\\M-00\\"+fileName1+"-F1-N-A-0-K-S1-00-Z-vNC.txt";
        file=new File(name);
        if(!file.exists()){
            //Сообщение об ошибке
            try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"001-E.txt"))){
                writer.write("Не хватает папки: "+name+". Для дальнейшей работы необходимо разбить модель на конечные элементы (0103 Создание КЭ модели из протомодели)");
            }
            catch(IOException ex){}
            return "exit";
            //            
        }
        ArrayList<String> arrPoint=new ArrayList<String>();
        try(FileInputStream fStream=new FileInputStream(file);
            InputStreamReader iReader=new InputStreamReader(fStream,"windows-1251");
            BufferedReader reader=new BufferedReader(iReader)){
            String s;
            while((s=reader.readLine())!=null)
                arrPoint.add(s);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        
        //Сообщение 4
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\003-M.txt"))){
            GregorianCalendar cal4=new GregorianCalendar();
            String hour4=""+cal4.get(Calendar.HOUR_OF_DAY);
            String minute4=""+cal4.get(Calendar.MINUTE);
            String second4=""+cal4.get(Calendar.SECOND);
            if(hour4.length()==1)hour4="0"+hour4;
            if(minute4.length()==1)minute4="0"+minute4;
            if(second4.length()==1)second4="0"+second4;
            writer.write("4: "+hour4+":"+minute4+":"+second4+" преобразование данных");
        }
        catch(IOException exc){}
        //
        
        //Полилинии
        name=args[0]+"\\FM-01\\M-00\\"+fileName1+"-F1-E-P-0-K-S1-00-Z-pET.txt";
        file=new File(name);
        if(!file.exists()){
            //Сообщение об ошибке
            try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\001-E.txt"))){
                writer.write("Не хватает папки: "+name+". Для дальнейшей работы необходимо разбить модель на конечные элементы (0103 Создание КЭ модели из протомодели)");
            }
            catch(IOException ex){}
            return "exit";
            //            
        }
        ArrayList<String> arrPline=new ArrayList<String>();
        try(FileInputStream fStream=new FileInputStream(file);
            InputStreamReader iReader=new InputStreamReader(fStream,"windows-1251");
            BufferedReader reader=new BufferedReader(iReader)){
            String s;
            while((s=reader.readLine())!=null)
                arrPline.add(s);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        
        String sPoint="///Точки ("+arrPoint.size()+")\n";
        sPoint+="@POINT\n";
        for(String point:arrPoint)
            sPoint+=point+"\n";
        sPoint+="&\n\n";
        
        String sPline="///Полилинии ("+arrPline.size()+")\n";
        sPline+="@PLINE\n";
        for(int i=0;i<arrPline.size();i++){
            String[] pline=arrPline.get(i).split(" ");
            sPline+=(i+1)+" "+pline[2]+" "+pline[3]+" "+pline[4]+"\n";
        }
        sPline+="&\n\n";
        
        String sLineloop="///Замкнутые линии ("+arrPline.size()+")\n";
        sLineloop+="@LINELOOP\n";
        for(int i=0;i<arrPline.size();i++)
            sLineloop+=(i+1)+" 1 "+(i+1)+"\n";
        sLineloop+="&\n\n";
        
        String sPsurf="///Плоские элементы поверхностей ("+arrPline.size()+")\n";
        sPsurf+="@PSURF\n";
        for(int i=0;i<arrPline.size();i++)
            sPsurf+=(i+1)+" "+(i+1)+"\n";
        sPsurf+="&\n\n";
        
        String sShell="///Оболочки ("+arrPline.size()+")\n";
        sShell+="@SHELL\n";
        for(int i=0;i<arrPline.size();i++)
            sShell+=(i+1)+" 1 1 "+(i+1)+" "+(i+1)+"\n";
        sShell+="&\n\n";
        
        String sMat="///Материалы (1)\n";
        sMat+="@MAT\n1 1\n&\n\n";
        
        //Сообщение 5
        GregorianCalendar cal5=new GregorianCalendar();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\004-M.txt"))){
            String hour5=""+cal5.get(Calendar.HOUR_OF_DAY);
            String minute5=""+cal5.get(Calendar.MINUTE);
            String second5=""+cal5.get(Calendar.SECOND);
            if(hour5.length()==1)hour5="0"+hour5;
            if(minute5.length()==1)minute5="0"+minute5;
            if(second5.length()==1)second5="0"+second5;
            writer.write("5: "+hour5+":"+minute5+":"+second5+" запись в файл "+args[1]+"\\"+name);
        }
        catch(IOException ex){}
        //
        
        long timeSpent=System.currentTimeMillis()-startTime;
        int hours=(int)(timeSpent/(60*60*1000));
        timeSpent-=(hours*60*60*1000);
        int minutes =(int)(timeSpent/(60 * 1000));
        timeSpent-=(minutes*60*1000);
        int seconds =(int)(timeSpent/(1000));
        time=hours+":"+minutes+":"+seconds;
        
        String sBegin="/// --------------------------------------------\n";
        sBegin+="/// Разбиение протомодели нп конечные элементы.\n";
        sBegin+="/// Исходный файл: "+args[0]+".\n";
        sBegin+="/// Выходной файл: "+args[1]+"\\"+name+".\n";
        sBegin+="/// Дата разбиения: "+cal5.get(Calendar.DAY_OF_MONTH)+"."
                    +(cal5.get(Calendar.MONTH)+1)+"."+cal5.get(Calendar.YEAR)+".\n";
        sBegin+="/// Время разбиения: "+time+".\n";
        sBegin+="/// ---------------------------------------------\n\n";
        
        sBegin+="/// MAT=1 POINT="+arrPoint.size();
        if(arrPline.size()>0){
            sBegin+=" PLINE="+arrPline.size();
            sBegin+=" LINELOOP="+arrPline.size();
            sBegin+=" PSURF="+arrPline.size();
            sBegin+=" SHELL="+arrPline.size();
        }
        sBegin+="\n\n\n";
        
        countObjects=1+arrPoint.size()+arrPline.size()*4;
        
        String fileKE=sBegin+sMat+sPoint+sPline+sLineloop+sPsurf+sShell;
        return fileKE;
    }
    
    //Распаковка архива
    public static String unZip(String zipFileName){
        int bufferSize=1024;
        byte[] buffer=new byte[bufferSize];
        String dstDirectory=zipFileName.substring(0,zipFileName.lastIndexOf("."));
        //
        String s=dstDirectory.substring(dstDirectory.lastIndexOf("\\")+1);
        //
        File distDir=new File(dstDirectory);
        try(ZipInputStream zis=new ZipInputStream(new FileInputStream(zipFileName))){
            String nextFileName="";
            ZipEntry ze=zis.getNextEntry();
            while(ze!=null){
                nextFileName=ze.getName();
                //File nextFile=new File(dstDirectory+"\\"+nextFileName);
                //
                String s1=nextFileName.substring(nextFileName.lastIndexOf("."));
                File nextFile=new File(dstDirectory+"\\"+s+s1);
                nextFileName=nextFile.getName();
                //
                
                new File(nextFile.getParent()).mkdirs();
                try(FileOutputStream fos=new FileOutputStream(nextFile)){
                    int length;
                    while((length=zis.read(buffer))>0)
                        fos.write(buffer,0,length);
                }
                ze=zis.getNextEntry();
            }            
            return dstDirectory+"\\"+nextFileName;
        }
        catch(FileNotFoundException ex){}
        catch(IOException ex){}                
        return "";
    }
    
    //Обработка точек и полилиний объекта прототипа
    public static void putPointsAndLines(JSONArray points,JSONObject element1,boolean isFull,
            boolean isNotFull,Double[] coords,HashMap<Integer,String> mapPoint,
            HashMap<Integer,String> mapPline,ArrayList<String> arrBar){
        String pline="";
        JSONArray t=(JSONArray)element1.get("T");
        JSONArray tX=(JSONArray)t.get(0);
        double tX0=Double.parseDouble(""+tX.get(0));
        double tX1=Double.parseDouble(""+tX.get(1));
        double tX2=Double.parseDouble(""+tX.get(2));
        JSONArray tY=(JSONArray)t.get(1);
        double tY0=Double.parseDouble(""+tY.get(0));
        double tY1=Double.parseDouble(""+tY.get(1));
        double tY2=Double.parseDouble(""+tY.get(2));
        JSONArray tZ=(JSONArray)t.get(2);
        double tZ0=Double.parseDouble(""+tZ.get(0));
        double tZ1=Double.parseDouble(""+tZ.get(1));
        double tZ2=Double.parseDouble(""+tZ.get(2));
        JSONArray tT=(JSONArray)t.get(3);
        double tT0=Double.parseDouble(""+tT.get(0));
        double tT1=Double.parseDouble(""+tT.get(1));
        double tT2=Double.parseDouble(""+tT.get(2));
        boolean isInArea=true,havePoints=false;
        for(Object point1:points){
            JSONArray points1=(JSONArray)point1;
            for(Object point2:points1){
                JSONArray points2=(JSONArray)point2;
                for(Object point3:points2){
                    JSONArray points3=(JSONArray)point3;
                    //выборка по диапазону координат
                    isInArea=true;
                    double oldx=Double.parseDouble(""+points3.get(0));
                    double oldy=Double.parseDouble(""+points3.get(1));
                    double oldz=Double.parseDouble(""+points3.get(2));
                    double x=oldx*tX0+oldy*tX1+oldz*tX2+tT0;
                    double y=oldx*tY0+oldy*tY1+oldz*tY2+tT1;
                    double z=oldx*tZ0+oldy*tZ1+oldz*tZ2+tT2;
                    if(isFull || (isNotFull && !havePoints)){
                        if(coords[0]!=null && x<coords[0])isInArea=false;
                        else if(coords[1]!=null && x>coords[1])isInArea=false;
                        else if(coords[2]!=null && y<coords[2])isInArea=false;
                        else if(coords[3]!=null && y>coords[3])isInArea=false;
                        else if(coords[4]!=null && z<coords[4])isInArea=false;
                        else if(coords[5]!=null && z>coords[5])isInArea=false;

                        if(isNotFull && isInArea)havePoints=true;
                    }
                    if(isFull && !isInArea)break;
                    //
                    mapPoint.put(++countPoint,x+" "+y+" "+z);
                    pline+=" "+countPoint;
                }
                //
                if(isFull && !isInArea)continue;
                //
                if(!pline.isEmpty()){
                    if(isFull || (isNotFull && havePoints)){
                        mapPline.put(++countPline,pline);
                        if(pline.split(" ").length==3)
                            arrBar.add((++countBar)+" 1 1 0 "+countPline+" "+countPline);
                    }
                    pline="";
                }
            }
        }
    }
    
    //Обработка точек и полилиний обычного объекта
    public static void putPointsAndLines(JSONArray points,boolean isFull,boolean isNotFull,
            Double[] coords,HashMap<Integer,String> mapPoint,HashMap<Integer,String> mapPline,
            ArrayList<String> arrBar){
        String pline="";
        boolean isInArea=true,havePoints=false;
        for(Object point1:points){
            JSONArray points1=(JSONArray)point1;
            for(Object point2:points1){
                JSONArray points2=(JSONArray)point2;
                for(Object point3:points2){
                    JSONArray points3=(JSONArray)point3;
                    //выборка по диапазону координат
                    isInArea=true;
                    double x=Double.parseDouble(""+points3.get(0));
                    double y=Double.parseDouble(""+points3.get(1));
                    double z=Double.parseDouble(""+points3.get(2));
                    if(isFull || (isNotFull && !havePoints)){
                        if(coords[0]!=null && x<coords[0])isInArea=false;
                        else if(coords[1]!=null && x>coords[1])isInArea=false;
                        else if(coords[2]!=null && y<coords[2])isInArea=false;
                        else if(coords[3]!=null && y>coords[3])isInArea=false;
                        else if(coords[4]!=null && z<coords[4])isInArea=false;
                        else if(coords[5]!=null && z>coords[5])isInArea=false;

                        if(isNotFull && isInArea)havePoints=true;
                    }
                    if(isFull && !isInArea)break;
                    //
                    mapPoint.put(++countPoint,points3.get(0)+" "+points3.get(1)+" "+points3.get(2));
                    pline+=" "+countPoint;
                }
                //
                if(isFull && !isInArea)continue;
                //
                if(!pline.isEmpty()){
                    if(isFull || (isNotFull && havePoints)){
                        mapPline.put(++countPline,pline);
                        if(pline.split(" ").length==3)
                            arrBar.add((++countBar)+" 1 1 0 "+countPline+" "+countPline);
                    }
                    pline="";
                }
            }
        }
    }
    
    //Применение матрицы трансформации к объекту прототипу
    public static void transformPrototype(JSONObject elements,JSONObject element1,String ref
        ,ArrayList<Integer> arrPresentation,boolean isM,boolean isG,boolean isFull,boolean isNotFull
        ,Double[] coords,HashMap<Integer,String> mapPoint,HashMap<Integer,String> mapPline,ArrayList<String> arrBar){
        for(Object key:elements.keySet())
            if((""+key).equals(ref)){
                JSONObject element=(JSONObject)elements.get(key);
                //
                String pline="";
                if(isM)
                    for(int number:arrPresentation){
                        int numberPresentation=number;
                        JSONArray pointsM=null;
                        if(numberPresentation==1){
                            //M
                            pointsM=(JSONArray)element.get("M");
                            if(pointsM!=null && pointsM.size()>0)
                                putPointsAndLines(pointsM,element1,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                            //M1
                            pointsM=(JSONArray)element.get("M1");
                            if(pointsM!=null && pointsM.size()>0)
                                putPointsAndLines(pointsM,element1,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                            //
                        }
                        //M2,M3...
                        else{
                            pointsM=(JSONArray)element.get("M"+numberPresentation);
                            if(pointsM!=null && pointsM.size()>0)
                                putPointsAndLines(pointsM,element1,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                        }
                        //
                    }
                if(isG)
                    for(int number:arrPresentation){
                        int numberPresentation=number;
                        JSONArray pointsG=null;
                        if(numberPresentation==1){
                            //G
                            pointsG=(JSONArray)element.get("G");
                            if(pointsG!=null && pointsG.size()>0)
                                putPointsAndLines(pointsG,element1,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                            //G1
                            pointsG=(JSONArray)element.get("G1");
                            if(pointsG!=null && pointsG.size()>0)
                                putPointsAndLines(pointsG,element1,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                            //
                        }
                        //G2,G3...
                        else{
                            pointsG=(JSONArray)element.get("G"+numberPresentation);
                            if(pointsG!=null && pointsG.size()>0)
                                putPointsAndLines(pointsG,element1,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                        }
                        //
                    }
                //
                break;
            }
    }
    
    //поиск родителей
    public static void getParent(String parent,String ifcd,double[] plcD){
        parent=ifcd.substring(ifcd.indexOf(parent));
        parent=parent.substring(0,parent.indexOf("}"));        
        String plc=parent.substring(parent.lastIndexOf("LocalCoords"));
        plc=plc.substring(0,plc.indexOf("]"));
        String[] plcArr=plc.split(",");
        plcD[0]+=Double.parseDouble(plcArr[1]);
        plcD[1]+=Double.parseDouble(plcArr[2]);
        plcD[2]+=Double.parseDouble(plcArr[3]);
        //поиск нового родителя
        parent=parent.substring(parent.lastIndexOf("Prnt"),parent.lastIndexOf("TpO"));
        parent=parent.substring(6).replace(",","").replace("\"","");
        //
        if(!parent.isEmpty())getParent(parent,ifcd,plcD);
    }
    
    public static String bimdAndIfcd2bsm(ArrayList<String> arrStr,String[] args,String name,long startTime){
        StringBuilder arrFile=new StringBuilder();
        for(String s:arrStr)
            if(!args[0].endsWith(".ifczip"))
                arrFile.append(s);
            else
                arrFile.append(s+"\n");
        
        //Обработка ключей
        boolean isG=false,isM=false,isBimd=false,isIfcd=false,isFull=false,isNotFull=false;
        Double[] coords=new Double[6];
        ArrayList<Integer> arrPresentation=new ArrayList<Integer>();
        ArrayList<String> arrFilterConv=new ArrayList<String>();
        ArrayList<String> arrFilterNotConv=new ArrayList<String>();
        if(args.length==0)args[0]="-h";
        for(String arg:args)
            if(arg.equals("-h")){
                System.out.println("Для запуска программы необходимо указать:\n"
                        + "-исходный файл;\n"
                        + "-папку, куда будет сохраняться преобразованный файл bsmt;\n"
                        + "-формат исходного файла (d1-bimd,d2-ifcd).");
                break;
            }
            else if(arg.contains("-c=")){
                File configFile=new File(arg.substring(3));
                try(FileInputStream fStream=new FileInputStream(configFile);
                    InputStreamReader iReader=new InputStreamReader(fStream,"UTF-8");
                    BufferedReader reader=new BufferedReader(iReader)){
                    String s="";
                    while((s=reader.readLine())!=null){
                        if(s.equals("-h")){
                            System.out.println("Для запуска программы необходимо указать:\n"
                                    + "-формат исходного файла (d1-bimd,d2-ifcd).");
                            break;
                        }
                        else if(s.equals("-d1"))
                            isBimd=true;
                        else if(s.equals("-d2"))
                            isIfcd=true;
                        else if(s.equals("-g"))
                            isG=true;
                        else if(s.equals("-m"))
                            isM=true;
                        else if(s.contains("-l")){
                            s=s.substring(2);
                            try{
                                int numberPresentation=Integer.parseInt(s);
                                arrPresentation.add(numberPresentation);
                            }
                            catch(NumberFormatException e){
                                continue;
                            }
                        }
                        else if(s.contains("-f1"))
                            arrFilterConv.add(s.substring(4));
                        else if(s.contains("-f2"))
                            arrFilterNotConv.add(s.substring(4));
                        //
                        else if(s.contains("-x1"))
                            coords[0]=Double.parseDouble(s.substring(4));
                        else if(s.contains("-x2"))
                            coords[1]=Double.parseDouble(s.substring(4));
                        else if(s.contains("-y1"))
                            coords[2]=Double.parseDouble(s.substring(4));
                        else if(s.contains("-y2"))
                            coords[3]=Double.parseDouble(s.substring(4));
                        else if(s.contains("-z1"))
                            coords[4]=Double.parseDouble(s.substring(4));
                        else if(s.contains("-z2"))
                            coords[5]=Double.parseDouble(s.substring(4));
                        else if(s.equals("-b1"))
                            isFull=true;
                        else if(s.equals("-b2"))
                            isNotFull=true;
                        //
                    }
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
                break;
            }
            else if(arg.equals("-d1"))
                isBimd=true;
            else if(arg.equals("-d2"))
                isIfcd=true;
            else if(arg.equals("-g"))
                isG=true;
            else if(arg.equals("-m"))
                isM=true;
            else if(arg.contains("-l")){
                arg=arg.substring(2);
                try{
                    int numberPresentation=Integer.parseInt(arg);
                    arrPresentation.add(numberPresentation);
                }
                catch(NumberFormatException e){
                    continue;
                }
            }
            else if(arg.contains("-f1"))
                arrFilterConv.add(arg.substring(4));
            else if(arg.contains("-f2"))
                arrFilterNotConv.add(arg.substring(4));
            //
            else if(arg.contains("-x1"))
                coords[0]=Double.parseDouble(arg.substring(4));
            else if(arg.contains("-x2"))
                coords[1]=Double.parseDouble(arg.substring(4));
            else if(arg.contains("-y1"))
                coords[2]=Double.parseDouble(arg.substring(4));
            else if(arg.contains("-y2"))
                coords[3]=Double.parseDouble(arg.substring(4));
            else if(arg.contains("-z1"))
                coords[4]=Double.parseDouble(arg.substring(4));
            else if(arg.contains("-z2"))
                coords[5]=Double.parseDouble(arg.substring(4));
            else if(arg.equals("-b1"))
                isFull=true;
            else if(arg.equals("-b2"))
                isNotFull=true;
            //
        if(!isG && !isM)isG=true;
        if(arrPresentation.isEmpty())arrPresentation.add(1);
        if(arrFilterConv.size()>0 && arrFilterNotConv.size()>0){
            System.out.println("Используются одновременно ключи f1 и f2. Необходимо использовать только один из этих ключей.");
            System.exit(0);
        }
        if(!isFull && !isNotFull)isFull=true;
        //
        countPoint=0;
        countPline=0;
        countBar=0;
        int countLineloop=0,countPsurf=0,countShell=0,countG=0,countM=0;
        HashMap<Integer,String> mapPoint=new HashMap<Integer,String>();
        HashMap<Integer,String> mapPline=new HashMap<Integer,String>();
        ArrayList<String> arrBar=new ArrayList<String>();
        //Сообщение 3
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\002-M.txt"))){
            GregorianCalendar cal3=new GregorianCalendar();
            String hour3=""+cal3.get(Calendar.HOUR_OF_DAY);
            String minute3=""+cal3.get(Calendar.MINUTE);
            String second3=""+cal3.get(Calendar.SECOND);
            if(hour3.length()==1)hour3="0"+hour3;
            if(minute3.length()==1)minute3="0"+minute3;
            if(second3.length()==1)second3="0"+second3;
            writer.write("3: "+hour3+":"+minute3+":"+second3+" окончание обработки данных");
        }
        catch(IOException ex){}
        //
        if(isBimd){
            String json=arrFile.toString();
            JSONObject obj=(JSONObject)JSONValue.parse(json);
            JSONObject elements=(JSONObject)obj.get("ZElements");
            for(Object key:elements.keySet()){
                if(key.toString().contains("@"))continue;
                JSONObject element1=(JSONObject)elements.get(key);
                //
                if(element1.get("ref")!=null){
                    String ref=""+element1.get("ref");
                    transformPrototype(elements,element1,ref,arrPresentation,isM,isG,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                }
                //
                String pline="";
                if(isM)
                    for(int number:arrPresentation){
                        int numberPresentation=number;
                        JSONArray pointsM=null;
                        if(numberPresentation==1){
                            //M
                            pointsM=(JSONArray)element1.get("M");
                            if(pointsM!=null && pointsM.size()>0)
                                putPointsAndLines(pointsM,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                            //M1
                            pointsM=(JSONArray)element1.get("M1");
                            if(pointsM!=null && pointsM.size()>0)
                                putPointsAndLines(pointsM,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                            //
                        }
                        //M2,M3...
                        else{
                            pointsM=(JSONArray)element1.get("M"+numberPresentation);
                            if(pointsM!=null && pointsM.size()>0)
                                putPointsAndLines(pointsM,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                        }
                        //
                    }
                if(isG)
                    for(int number:arrPresentation){
                        int numberPresentation=number;
                        JSONArray pointsG=null;
                        if(numberPresentation==1){
                            //G
                            pointsG=(JSONArray)element1.get("G");
                            if(pointsG!=null && pointsG.size()>0)
                                putPointsAndLines(pointsG,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                            //G1
                            pointsG=(JSONArray)element1.get("G1");
                            if(pointsG!=null && pointsG.size()>0)
                                putPointsAndLines(pointsG,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                            //
                        }
                        //G2,G3...
                        else{
                            pointsG=(JSONArray)element1.get("G"+numberPresentation);
                            if(pointsG!=null && pointsG.size()>0)
                                putPointsAndLines(pointsG,isFull,isNotFull,coords,mapPoint,mapPline,arrBar);
                        }
                        //
                    }
            }
        }
        else if(isIfcd){
            String ifcd=arrFile.toString();
            ifcd=ifcd.replaceAll("\n","");
            String[] geom=ifcd.split("Geom");
            ArrayList<String> arrTypeObjects=new ArrayList<String>(); //Массив типов объектов
            
            //plc
            ArrayList<double[]> arrPlc=new ArrayList<double[]>();
            for(int i=1;i<geom.length;i++){                
                double[] plcD=new double[3];
                String parent=geom[i-1].substring(geom[i-1].lastIndexOf("Prnt"),geom[i-1].lastIndexOf("TpO"));
                parent=parent.substring(6).replace(",","").replace("\"","");
                getParent(parent,ifcd,plcD);
                //
                String plc=geom[i-1].substring(geom[i-1].lastIndexOf("LocalCoords"));
                plc=plc.substring(0,plc.indexOf("]"));
                String[] plcArr=plc.split(",");
                plcD[0]+=Double.parseDouble(plcArr[1]);
                plcD[1]+=Double.parseDouble(plcArr[2]);
                plcD[2]+=Double.parseDouble(plcArr[3]);
                arrPlc.add(plcD);
                
                //Нахождение типа объекта
                int indexTypeO=geom[i-1].lastIndexOf("TpO:");
                String type=geom[i-1].substring(indexTypeO+4);
                indexTypeO=type.indexOf(",");
                type=type.substring(0,indexTypeO);
                type=type.replaceAll("\"","");
                arrTypeObjects.add(type);
                //
            }
            //
            
            //
            if(arrFilterConv.size()>0 || arrFilterNotConv.size()>0)
                for(int i=1;i<geom.length;i++){
                    String type=arrTypeObjects.get(i-1);
                    //Обход списка конвертируемых объектов
                    for(String s:arrFilterConv)
                        if(s.contains("*")){
                            boolean haveObj=false;
                            Pattern pattern=Pattern.compile(s.replace("*",""));
                            Matcher matcher=pattern.matcher(type);
                            while(matcher.find()==true)
                                haveObj=true;
                            if(!haveObj)geom[i]=null;
                        }
                        else
                            if(!s.equals(type))
                                geom[i]=null;
                    //Обход списка неконвертируемых объектов
                    for(String s:arrFilterNotConv)
                        if(s.contains("*")){
                            Pattern pattern=Pattern.compile(s.replace("*",""));
                            Matcher matcher=pattern.matcher(type);
                            while(matcher.find()==true)
                                geom[i]=null;
                        }
                        else
                            if(s.equals(type))
                                geom[i]=null;
                    //
                }
            //            
            for(int i=1;i<geom.length;i++){
                if(geom[i]==null)continue;
                int index=geom[i].indexOf("]]]");
                if(geom[i].contains("Fs"))index=geom[i].indexOf("]]]]]");
                geom[i]=geom[i].substring(0,index+2);
                geom[i]=geom[i].replace("[[","\n[\n[");
                geom[i]=geom[i].replace("]]","]\n]\n");            
            }

            for(int i=1;i<geom.length;i++){
                if(geom[i]==null)continue;
                boolean isInArea=true,havePoints=false;
                //
                String geomStr="";
                int length=3;
                if(geom[i].contains("Pls"))
                    geomStr=geom[i].split("Pls")[1];
                else if(geom[i].contains("Fs")){
                    geomStr=geom[i].split("Fs")[1];
                    length=5;
                }
                //
                String[] arrPls=geomStr.split("\n");
                for(String s:arrPls){
                    if(s.length()<length)continue;
                    havePoints=false;
                    String pline="";
                    String[] arrS=s.split("],");
                    for(String s1:arrS){
                        isInArea=true;
                        s1=s1.replace("[","");
                        s1=s1.replace("]","");
                        String[] point=s1.split(",");
                        //Выборка по диапазону координат

                        //plc
                        double[] plc=arrPlc.get(i-1);
                        //

                        double x=Double.parseDouble(point[0])+plc[0];
                        double y=Double.parseDouble(point[1])+plc[1];
                        double z=Double.parseDouble(point[2])+plc[2];
                        if(isFull || (isNotFull && !havePoints)){
                            if(coords[0]!=null && x<coords[0])isInArea=false;
                            else if(coords[1]!=null && x>coords[1])isInArea=false;
                            else if(coords[2]!=null && y<coords[2])isInArea=false;
                            else if(coords[3]!=null && y>coords[3])isInArea=false;
                            else if(coords[4]!=null && z<coords[4])isInArea=false;
                            else if(coords[5]!=null && z>coords[5])isInArea=false;

                            if(isNotFull && isInArea)havePoints=true;
                        }
                        if(isFull && !isInArea)break;
                        //
                        mapPoint.put(++countPoint,x+" "+y+" "+z);
                        pline+=" "+countPoint;
                    }
                    if(isFull && !isInArea)continue;                    
                        if(isFull || (isNotFull && havePoints))
                            mapPline.put(++countPline,pline+" ///TypeObject: "+arrTypeObjects.get(i-1));
                    //
                }
            }
        }
        //Сообщение 4
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\003-M.txt"))){
            GregorianCalendar cal4=new GregorianCalendar();
            String hour4=""+cal4.get(Calendar.HOUR_OF_DAY);
            String minute4=""+cal4.get(Calendar.MINUTE);
            String second4=""+cal4.get(Calendar.SECOND);
            if(hour4.length()==1)hour4="0"+hour4;
            if(minute4.length()==1)minute4="0"+minute4;
            if(second4.length()==1)second4="0"+second4;
            writer.write("4: "+hour4+":"+minute4+":"+second4+" преобразование данных");
        }
        catch(IOException ex){}
        //Подготовка данных для записи в файл
        StringBuilder sPoint=new StringBuilder("///Точки ("+mapPoint.size()+")\n@POINT\n");
        for(int i=1;i<=mapPoint.size();i++)
            sPoint.append(i+" "+mapPoint.get(i)+"\n");
        sPoint.append("&\n\n");
        StringBuilder sPline=new StringBuilder("///Полилинии ("+mapPline.size()+")\n@PLINE\n");
        StringBuilder sBar=new StringBuilder("///Стержни ("+arrBar.size()+")\n@BAR\n");
        StringBuilder sLineloop=new StringBuilder("");
        StringBuilder sPsurf=new StringBuilder("");
        StringBuilder sShell=new StringBuilder("");
        for(int i=1;i<=mapPline.size();i++){
            sPline.append(i+mapPline.get(i)+"\n");
            String[] pline=mapPline.get(i).split(" ");
            if(pline.length>3){
                sLineloop.append((++countLineloop)+" 1 "+i+"\n");
                sPsurf.append((++countPsurf)+" "+countLineloop+"\n");
                sShell.append((++countShell)+" 1 1 0 "+countPsurf+"\n");
            }
        }
        sPline.append("&\n\n");
        sLineloop=new StringBuilder("///Замкнутые линии ("+sLineloop.toString().split("\n").length
                                    +")\n@LINELOOP\n"+sLineloop+"&\n\n");
        sPsurf=new StringBuilder("///Плоские элементы поверхностей ("+sPsurf.toString().split("\n").length
                                    +")\n@PSURF\n"+sPsurf+"&\n\n");
        sShell=new StringBuilder("///Оболочки ("+sShell.toString().split("\n").length
                                    +")\n@SHELL\n"+sShell+"&\n\n");
        for(String bar:arrBar)
            sBar.append(bar+"\n");
        sBar.append("&\n\n");
        GregorianCalendar cal5=new GregorianCalendar();
        String hour5=""+cal5.get(Calendar.HOUR_OF_DAY);
        String minute5=""+cal5.get(Calendar.MINUTE);
        String second5=""+cal5.get(Calendar.SECOND);
        if(hour5.length()==1)hour5="0"+hour5;
        if(minute5.length()==1)minute5="0"+minute5;
        if(second5.length()==1)second5="0"+second5;
        //Сообщение 5
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\004-M.txt"))){
            writer.write("5: "+hour5+":"+minute5+":"+second5+" запись в файл "+args[1]+"\\"+name);
        }
        catch(IOException ex){}
        //Вычисление за сколько выполняется приложение
        long timeSpent=System.currentTimeMillis()-startTime;
        int hours=(int)(timeSpent/(60*60*1000));
        timeSpent-=(hours*60*60*1000);
        int minutes=(int)(timeSpent/(60*1000));
        timeSpent-=(minutes*60*1000);
        int seconds=(int)(timeSpent/1000);
        time=hours+":"+minutes+":"+seconds;
        //
        String day=""+cal5.get(Calendar.DAY_OF_MONTH);
        if(day.length()==1)day="0"+day;
        String month=""+(cal5.get(Calendar.MONTH)+1);
        if(month.length()==1)month="0"+month;
        String year=""+cal5.get(Calendar.YEAR);
        String sBegin="/// --------------------------------------------\n";
        sBegin+="/// Преобразование Bimd/Ifcd в BSMT.\n";
        sBegin+="/// Исходный файл: "+args[0]+".\n";
        sBegin+="/// Выходной файл: "+args[1]+"\\"+name+".\n";
        sBegin+="/// Дата создания файла: "+day+"."+month+"."+year+" "+hour5+":"
                +minute5+":"+second5+".\n";
        sBegin+="/// ---------------------------------------------\n\n";
        
        sBegin+="/// MAT=1 ELSEC=1 POINT="+mapPoint.size()+" PLINE="+mapPline.size();
        if((sLineloop.toString().split("\n").length-3)>0)sBegin+=" LINELOOP="+(sLineloop.toString().split("\n").length-3);
        else sLineloop=new StringBuilder("");
        if((sPsurf.toString().split("\n").length-3)>0)sBegin+=" PSURF="+(sPsurf.toString().split("\n").length-3);
        else sPsurf=new StringBuilder("");
        if((sShell.toString().split("\n").length-3)>0)sBegin+=" SHELL="+(sShell.toString().split("\n").length-3);
        else sShell=new StringBuilder("");
        if((sBar.toString().split("\n").length-3)>0)sBegin+=" BAR="+(sBar.toString().split("\n").length-3);
        else sBar=new StringBuilder("");
        
        String sMat="///Материалы (1)\n@MAT\n";
        sMat+="1 1\n";
        sMat+="&\n\n";
        
        String sElsec="///Поперечные сечения элемента (1)\n@ELSEC\n1\n&\n\n";
        
        sBegin+="\n\n\n"+sMat+sElsec;
        
        String strOut=sBegin+sPoint+sPline+sLineloop+sPsurf+sShell+sBar;
        
        countObjects=2+mapPoint.size()+mapPline.size()+(sLineloop.toString().split("\n").length-3)
                +(sPsurf.toString().split("\n").length-3)+(sShell.toString().split("\n").length-3)
                +(sBar.toString().split("\n").length-3);
        return strOut;
    }
        
    /**
     * \param
     * args[0]-исходный файл, который будет конвертирован
     * args[1]-папка, куда будет сохранен файл bsmt
     * args[4]-папка, куда сохраняются файлы с сообщениями
     * sli-конвертация файлов sli в файлы bsmt
     * lira-конвертация файлов lira(txt) в файлы bsmt
     * fea-конвертация файлов fea в файлы bsmt
     * pos-конвертация файлов pos в файлы bsmt
     * bsmt-разбиение протомодели (файл bsmt) на конечные элементы (файл bsmt)
     * bimd-конвертация bimd,bimdzip,ifcd,ifczip файлов в файлы bsmt
     * h-вывод справочной информации по конвертации файлов bimd,bimdzip,ifcd,ifczip
     * d1-конвертация файлов bimd,bimdzip (если используется ключ bimd)
     * d2-конвертация файлов ifcd,ifczip (если используется ключ bimd)
     * m-использовать твердотельное (массивное) представление (для файлов bimd и bimdzip)
     * g-использовать геометрическое представление (по умолчанию для файлов bimd и bimdzip)
     * lXX-использовать представление с номером XX, где XX от 1 до 99 (по умолчанию 1 для файлов bimd и bimdzip)
     * c-конфигурационный файл, где указаны все ключи (для файлов bimd,bimdzip,ifcd,ifczip)
     * f1-фильтр, конвертировать объекты данного типа (для файлов ifcd,ifczip)
     * f2-фильтр, не конвертировать объекты данного типа (для файлов ifcd,ifczip)
     * x1-минимальное значение координаты X зоны выборки (для файлов bimd,bimdzip,ifcd,ifczip)
     * x2-максимальное значение координаты X зоны выборки (для файлов bimd,bimdzip,ifcd,ifczip)
     * y1-минимальное значение координаты Y зоны выборки (для файлов bimd,bimdzip,ifcd,ifczip)
     * y2-максимальное значение координаты Y зоны выборки (для файлов bimd,bimdzip,ifcd,ifczip)
     * z1-минимальное значение координаты Z зоны выборки (для файлов bimd,bimdzip,ifcd,ifczip)
     * z2-максимальное значение координаты Z зоны выборки (для файлов bimd,bimdzip,ifcd,ifczip)
     * b1-конвертировать объекты, полностью попадающие в зону выборки, т.е. все точки в зоне выборки (по умолчанию для файлов bimd,bimdzip,ifcd,ifczip)
     * b2-конвертировать объекты, затрагивающие зону выборки, т.е. хотя бы одна точка в зоне выборки (для файлов bimd,bimdzip,ifcd,ifczip)
     */
    public static void main(String[] args) {        
        //Получение пути для dll
        if(!new File("").getAbsolutePath().contains("ModelS")){
            pathDll=WindowsReqistry.readRegistry("HKCR\\ConvertorProject\\shell\\open\\command","jarPath");
            int indexDll=pathDll.lastIndexOf("\\");
            pathDll=pathDll.substring(0,indexDll)+"\\checkDate.dll";
            System.load(pathDll);
        }
        else{
            pathDll=new File("").getAbsolutePath()+"\\Plugins\\ModuleConvertor\\checkDate.dll";
            System.load(pathDll);
        }
        //
        
        //Проверка даты
        boolean rightDate=new Convertor().checkDate();
        if(!rightDate)System.exit(0);
        //
        
        long startTime=System.currentTimeMillis();
        boolean sli=false,lira=false,fea=false,pos=false,bsmt=false,bimd=false;
        
        //Сообщение 1
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\_start.txt"))){
            GregorianCalendar cal1=new GregorianCalendar();
            String hour1=""+cal1.get(Calendar.HOUR_OF_DAY);
            String minute1=""+cal1.get(Calendar.MINUTE);
            String second1=""+cal1.get(Calendar.SECOND);
            if(hour1.length()==1)hour1="0"+hour1;
            if(minute1.length()==1)minute1="0"+minute1;
            if(second1.length()==1)second1="0"+second1;
            writer.write("1: "+hour1+":"+minute1+":"+second1+" начало обработки --------------------");
        }
        catch(IOException ex){}
        //
        
        for(String arg:args)
            if(arg.equals("-sli"))sli=true;
            else if(arg.equals("-lira"))lira=true;
            else if(arg.equals("-fea"))fea=true;
            else if(arg.equals("-pos"))pos=true;
            else if(arg.equals("-bsmt"))bsmt=true;
            else if(arg.equals("-bimd"))bimd=true;
        File file=new File(args[0]);
        //Обработка архивов bimdzip и ifczip;
        boolean bimdzip=false;
        if(args[0].endsWith(".bimdzip")){
            String nameFile=unZip(args[0]);
            file=new File(nameFile);
            bimdzip=true;
        }
        else if(args[0].endsWith(".ifczip")){
            try{
                String nameFile=args[0].substring(0,args[0].lastIndexOf("."))+".ifcd";
                Process process=new ProcessBuilder("BimS.exe",
                    args[0],nameFile,args[2],args[3],new File(args[0]).getParent(),"-plugin","-idf=3").start();
                try{
                    process.waitFor();
                }
                catch(InterruptedException ex){}
                file=new File(nameFile);
            }
            catch(IOException ex){}
        }
        //
        ArrayList<String> arrFile=new ArrayList<String>();
        
        //Сообщение 2
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\001-M.txt"))){
            GregorianCalendar cal2=new GregorianCalendar();
            String hour2=""+cal2.get(Calendar.HOUR_OF_DAY);
            String minute2=""+cal2.get(Calendar.MINUTE);
            String second2=""+cal2.get(Calendar.SECOND);
            if(hour2.length()==1)hour2="0"+hour2;
            if(minute2.length()==1)minute2="0"+minute2;
            if(second2.length()==1)second2="0"+second2;
            writer.write("2: "+hour2+":"+minute2+":"+second2+" открытие файла "+args[0]);
        }
        catch(IOException ex){}
        //
        if(!file.isDirectory()){
            try(FileInputStream fStream=new FileInputStream(file);
                InputStreamReader iReader=new InputStreamReader(fStream,"windows-1251");
                BufferedReader reader=new BufferedReader(iReader)){
                    String s="";
                    while((s=reader.readLine())!=null)
                        arrFile.add(s);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        String result="",name="";
        if(sli){
            name=file.getName().replace(".sli",".bsmt");
            result=sli2bsm(arrFile,args,name,startTime);
        }
        else if(lira){
            name=file.getName().replace(".txt",".bsmt");
            result=lira2bsm(arrFile,args,name,startTime);
        }
        else if(fea){
            name=file.getName().replace(".fea",".bsmt");
            result=fea2bsm(arrFile,args,name,startTime);
        }
        else if(pos){
            name=file.getName().replace(".pos",".bsmt");
            result=pos2bsm(arrFile,args,name,startTime);
        }
        else if(bimd){
            if(file.getName().endsWith(".bimd"))name=file.getName().replace(".bimd",".bsmt");
            else if(file.getName().endsWith(".txt"))name=file.getName().replace(".txt",".bsmt");
            else if(file.getName().endsWith(".ifcd"))name=file.getName().replace(".ifcd",".bsmt");
            result=bimdAndIfcd2bsm(arrFile,args,name,startTime);
        }
        else if(bsmt){
            result=ke2bsm(args,file,startTime);
            name=nameKE+".bsmt";
        }
        else{
            if(file.getName().endsWith(".sli")){
                name=file.getName().replace(".sli",".bsmt");
                result=sli2bsm(arrFile,args,name,startTime);
            }
            else if(file.getName().endsWith(".txt")){
                name=file.getName().replace(".txt",".bsmt");
                result=lira2bsm(arrFile,args,name,startTime);
            }
            else if(file.getName().endsWith(".fea")){
                name=file.getName().replace(".fea",".bsmt");
                result=fea2bsm(arrFile,args,name,startTime);
            }
            else if(file.getName().endsWith(".pos")){
                name=file.getName().replace(".pos",".bsmt");
                result=pos2bsm(arrFile,args,name,startTime);
            }
            else if(file.getName().endsWith(".bimd")){
                name=file.getName().replace(".bimd",".bsmt");
                result=bimdAndIfcd2bsm(arrFile,args,name,startTime);
            }
            else if(bimdzip){
                name=file.getName().replace(".txt",".bsmt");
                result=bimdAndIfcd2bsm(arrFile,args,name,startTime);
            }
            else if(file.getName().endsWith(".ifcd")){
                name=file.getName().replace(".ifcd",".bsmt");
                result=bimdAndIfcd2bsm(arrFile,args,name,startTime);
            }
            else if(file.isDirectory()){
                result=ke2bsm(args,file,startTime);
                name=nameKE+".bsmt";
            }
        }        
        //Непосредственная запись в файл
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[1]+"\\"+name))){
            String[] str=result.split("\n");
            for(String s:str){
                writer.write(s);
                writer.newLine();
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        //
        
        //Сообщение 6
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(args[4]+"\\_finish.txt"))){
            GregorianCalendar cal6=new GregorianCalendar();
            String hour6=""+cal6.get(Calendar.HOUR_OF_DAY);
            String minute6=""+cal6.get(Calendar.MINUTE);
            String second6=""+cal6.get(Calendar.SECOND);
            if(hour6.length()==1)hour6="0"+hour6;
            if(minute6.length()==1)minute6="0"+minute6;
            if(second6.length()==1)second6="0"+second6;
            writer.write("6: "+hour6+":"+minute6+":"+second6+" время выполнения: "+time);
            writer.newLine();
            //Вычисление используемой памяти
            long bits=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            int count=0;
            String memory="";
            long kbits=0,mbits=0,gbits=0;
            if(bits>1073741824){
                gbits=bits/1073741824;
                bits%=1073741824;
                count=1;
            }
            if(bits>1048576){
                mbits=bits/1048576;
                bits%=1048576;
                if(count==0)count=2;
            }
            if(bits>1024){
                kbits=bits/1024;
                bits%=1024;
                if(count==0)count=3;
            }
            
            if(count==1 || count==2){
                memory+=gbits+".";
                if(mbits>0 && mbits<10)memory+="000"+mbits+" гб";
                else if(mbits>9 && mbits<100)memory+="00"+mbits+" гб";
                else if(mbits>99 && mbits<1000)memory+="0"+mbits+" гб";
                else memory+=mbits+" гб";
            }
            else if(count==3){
                memory+=mbits+".";
                if(kbits>0 && kbits<10)memory+="000"+kbits+" мб";
                else if(kbits>9 && kbits<100)memory+="00"+kbits+" мб";
                else if(kbits>99 && kbits<1000)memory+="0"+kbits+" мб";
                else memory+=kbits+" мб";
            }
            else{
                memory+=kbits+".";
                if(bits>0 && bits<10)memory+="000"+bits+" кб";
                else if(bits>9 && bits<100)memory+="00"+bits+" кб";
                else if(bits>99 && bits<1000)memory+="0"+bits+" кб";
                else memory+=bits+" кб";
            }
            //
            writer.write("6: "+hour6+":"+minute6+":"+second6+" объектов: "+countObjects+". Использованная память: "+memory);
            writer.newLine();
            writer.write("6: "+hour6+":"+minute6+":"+second6+" завершение обработки ----------------");
        }
        catch(IOException ex){}
        //
    }
    
}
