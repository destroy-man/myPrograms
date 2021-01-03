import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.DecimalFormat;
import java.util.*;
import vtk.*;

public class GSDtoBSM {

    // -----------------------------------------------------------------
    // Load VTK library and print which library was not properly loaded
    static {
      if (!vtkNativeLibrary.LoadAllNativeLibraries()) {
        for (vtkNativeLibrary lib : vtkNativeLibrary.values()) {
          if (!lib.IsLoaded()) {
            System.out.println(lib.GetLibraryName() + " not loaded");
          }
        }
      }
      vtkNativeLibrary.DisableOutputWindow(null);
    }
    // -----------------------------------------------------------------
    
    //
    static boolean isSimple=false;
    //
    
    static int plineNum; //счетчик полилиний
    static TreeMap<Integer,String> resultPoint; //массив точек, полученных в результате преобразования
    static ArrayList<String> resultPline; //массив фигур, полученных в результате преобразования
    static int lastPline; //номер последней полилинии
    
    //Преобразование поверхности
    public static void generalization(ArrayList<String> arrPline,TreeMap<Integer,String> mapPoint){
        vtkPoints points=new vtkPoints();
        vtkCellArray polygons=new vtkCellArray();
        for(String figure:arrPline){
            String[] figure1s=figure.split(" ");
            if(figure1s.length<=3)continue;
            int[] figure1=new int[figure1s.length-1];
            for(int i=1;i<figure1s.length;i++)
                figure1[i-1]=Integer.parseInt(figure1s[i]);
            
            vtkPolygon polygon=new vtkPolygon();
            polygon.GetPointIds().SetNumberOfIds(figure1.length);
            for(int i=0;i<figure1.length;i++){
                String[] point=mapPoint.get(figure1[i]).split(" ");
                double X=Double.parseDouble(point[1]);
                double Y=Double.parseDouble(point[2]);
                double Z=Double.parseDouble(point[3]);
                points.InsertNextPoint(X,Y,Z);
                polygon.GetPointIds().SetId(i,points.GetNumberOfPoints()-1);
            }
            polygons.InsertNextCell(polygon);
        }
        
        vtkPolyData polyData=new vtkPolyData();
        polyData.SetPoints(points);
        polyData.SetPolys(polygons);
        
        vtkCleanPolyData a=new vtkCleanPolyData();
        a.AddInputData(polyData);
        a.Update();
        
        vtkPolyData b=a.GetOutput();
        //
        
        //Выделение внешнего контура
        vtkFeatureEdges featureEdges=new vtkFeatureEdges();
        featureEdges.SetInputData(b);
        featureEdges.BoundaryEdgesOn();
        featureEdges.FeatureEdgesOff();
        featureEdges.ManifoldEdgesOff();
        featureEdges.NonManifoldEdgesOff();
        featureEdges.Update();

        vtkPolyData polyDataFe=featureEdges.GetOutput();

        vtkPolyData polyDataEdge=new vtkPolyData();
        polyDataEdge.SetPoints(polyDataFe.GetPoints());
        polyDataEdge.SetLines(polyDataFe.GetLines());
        //
        
        resultPoint=new TreeMap<Integer,String>();
        resultPline=new ArrayList<String>();
        
        points=polyDataEdge.GetPoints();
        for(int i=0;i<points.GetNumberOfPoints();i++)
            resultPoint.put(i+1," "+points.GetPoint(i)[0]+" "+points.GetPoint(i)[1]+" "
            +points.GetPoint(i)[2]);
        
        plineNum=0;
        String longPline="";
        for(int i=0;i<polyDataEdge.GetNumberOfCells();i++)
            longPline+=(++plineNum)+" "+(polyDataEdge.GetCell(i).GetPointId(0)+1+lastPline)+" "
                    +(polyDataEdge.GetCell(i).GetPointId(1)+1+lastPline)+"\n";
        
        //plines Нахождение полилиний, связанных внешним контуром
        String longStr="";
        String shortStr="";
        String beginStr="";
        int beginNumber=0,lastPoint=0,longCount=0;
        boolean isFirst=true;
        String[] plines=longPline.split("\n");
        for(int i=beginNumber;;){
            String[] pline1=plines[i].split(" ");
            boolean findPoint=false;
            if(isFirst){
                longStr+=(++longCount)+" "+pline1[1]+" "+pline1[2];
                lastPoint=Integer.parseInt(pline1[2]);
                beginStr=pline1[1];
            }
            for(int j=i+1;j<plines.length;j++){
                String[] pline2=plines[j].split(" ");
                if(pline1[2].equals(pline2[1])){
                    isFirst=false;
                    if(pline2[2].equals(beginStr)){
                        i=beginNumber;
                        break;
                    }
                    longStr+=" "+pline2[2];
                    if(Integer.parseInt(pline2[2])>lastPoint)
                        lastPoint=Integer.parseInt(pline2[2]);
                    i=j;
                    findPoint=true;
                    break;
                }
            }
            if(!findPoint)
                for(int j=i-1;j>=beginNumber;j--){
                    String[] pline2=plines[j].split(" ");
                    if(pline1[2].equals(pline2[1])){
                        isFirst=false;
                        if(pline2[2].equals(beginStr)){
                            i=beginNumber;
                            break;
                        }
                        longStr+=" "+pline2[2];
                        i=j;
                        findPoint=true;
                        break;
                    }
                }
            if(i==beginNumber && !isFirst)
                if((longStr.split(" ").length-1)==plines.length){                        
                    longStr+="\n";
                    beginNumber=lastPoint+1;
                    break;
                }
                else
                    for(int j=beginNumber+1;j<plines.length;j++){
                        String[] pline2=plines[j].split(" ");
                        boolean isPline=false;
                        String[] longStrArr=longStr.split(" ");
                        for(String s:longStrArr)
                            if(s.equals(pline2[1]) || s.equals(pline2[2])){
                                isPline=true;
                                break;
                            }
                        if(!isPline){
                            longStr+="\n";
                            i=j;
                            beginNumber=j;
                            isFirst=true;
                            break;
                        }
                    }
            else if(!findPoint){
                longStr+=" null";
                boolean isPline=false;
                for(int j=beginNumber+1;j<plines.length;j++){
                    String[] pline2=plines[j].split(" ");
                    isPline=false;
                    String[] longStrArr=longStr.split(" ");
                    for(String s:longStrArr)
                        if(s.equals(pline2[1]) || s.equals(pline2[2])){
                            isPline=true;
                            break;
                        }
                    if(!isPline){
                        longStr+="\n";
                        i=j;
                        beginNumber=j;
                        isFirst=true;
                        break;
                    }
                }
                if(isPline)break;
            }
        }
        String[] longStrArr=longStr.split("\n");
        longStr="";
        for(String s:longStrArr)
            if(!s.endsWith("null"))
                longStr+=s+"\n";
        //
        
        //Упрощение преобразованной линии (удаление не угловых точек)
        int typeCoord=0;
        if(!longStr.isEmpty()){
            String[] strArr=longStr.split("\n");
            for(String s:strArr){
                String[] str=s.split(" ");
                String[] oldPoint=null;
                String[] nextPoint=null;
                shortStr="";
                int oldTypeCoord=0;
                for(int i=1;i<str.length;i++){
                    String[] point=resultPoint.get(Integer.parseInt(str[i])-lastPline).split(" ");
                    if(i==1)shortStr+=str[i];
                    else{
                        String raznostPoints="";
                        oldPoint=resultPoint.get(Integer.parseInt(str[i-1])-lastPline).split(" ");
                        if(!oldPoint[0].equals(point[0]) && oldPoint[1].equals(point[1]) && oldPoint[2].equals(point[2]))
                            oldTypeCoord=1;
                        else if(oldPoint[0].equals(point[0]) && !oldPoint[1].equals(point[1]) && oldPoint[2].equals(point[2]))
                            oldTypeCoord=2;
                        else if(oldPoint[0].equals(point[0]) && oldPoint[1].equals(point[1]) && !oldPoint[2].equals(point[2]))
                            oldTypeCoord=3;
                        else oldTypeCoord=4;
                        if(oldTypeCoord==4){
                            if(!oldPoint[0].equals(point[0]))raznostPoints+="0 ";
                            if(!oldPoint[1].equals(point[1]))raznostPoints+="1 ";
                            if(!oldPoint[2].equals(point[2]))raznostPoints+="2 ";
                        }
                        if(i!=str.length-1)nextPoint=resultPoint.get(Integer.parseInt(str[i+1])-lastPline).split(" ");
                        else nextPoint=resultPoint.get(Integer.parseInt(str[1])-lastPline).split(" ");

                        if(!point[0].equals(nextPoint[0]) && point[1].equals(nextPoint[1]) && point[2].equals(nextPoint[2]))
                            typeCoord=1;
                        else if(point[0].equals(nextPoint[0]) && !point[1].equals(nextPoint[1]) && point[2].equals(nextPoint[2]))
                            typeCoord=2;
                        else if(point[0].equals(nextPoint[0]) && point[1].equals(nextPoint[1]) && !point[2].equals(nextPoint[2]))
                            typeCoord=3;
                        else typeCoord=4;

                        if(typeCoord==4)
                            if(oldTypeCoord==4){
                                if(!oldPoint[0].equals(nextPoint[0]) && oldPoint[1].equals(nextPoint[1]) && oldPoint[2].equals(nextPoint[2])){
                                    shortStr+=" "+str[i];
                                    continue;
                                }
                                else if(oldPoint[0].equals(nextPoint[0]) && !oldPoint[1].equals(nextPoint[1]) && oldPoint[2].equals(nextPoint[2])){
                                    shortStr+=" "+str[i];
                                    continue;
                                }
                                else if(oldPoint[0].equals(nextPoint[0]) && oldPoint[1].equals(nextPoint[1]) && !oldPoint[2].equals(nextPoint[2])){
                                    shortStr+=" "+str[i];
                                    continue;
                                }
                                else continue;
                            }
                            else{
                                shortStr+=" "+str[i];
                                continue;
                            }

                        if(oldTypeCoord!=typeCoord)shortStr+=" "+str[i];
                    }
                }
                lastPline+=plineNum;
                resultPline.add(shortStr);
            }
        }
    }
    
    /**
     * \param
     * arrPline-массив полилиний из файла BSMT
     * mapPoint-массив точек из файла BSMT 
     */
    //Создание структуры данных файла BSMT
    public static Object[] createStructure(ArrayList<String> arrPline,TreeMap<Integer,String> mapPoint){
        TreeMap<Integer,String> discret=new TreeMap<Integer,String>();
        discret.put(1,"Type:11 Fe_size:5");
        TreeMap<Integer,String> femodel=new TreeMap<Integer,String>();
        femodel.put(1,"Task:100 Discret:1");
        int countPline=0;
        TreeMap<Integer,String> mapPointCont=new TreeMap<Integer,String>(); //Массив точек для структуры       
        TreeMap<Integer,String> mapPline=new TreeMap<Integer,String>(); //Массив полилиний для структуры
        TreeMap<Integer,String> mapLineloop=new TreeMap<Integer,String>(); //Массив замкнутых линий для структуры
        TreeMap<Integer,String> mapPsurf=new TreeMap<Integer,String>(); //Массив плоских поверхностей для структуры
        TreeMap<Integer,String> mapShell=new TreeMap<Integer,String>(); //Массив оболочек для структуры
        for(String pline:arrPline){
            mapPline.put(++countPline,pline);
            mapLineloop.put(countPline,"1 "+countPline);
            mapPsurf.put(countPline,""+countPline);
            mapShell.put(countPline,"1 1 0 "+countPline);
            String[] points=pline.split(" ");
            for(String point:points){
                int numberPoint=Integer.parseInt(point);
                mapPointCont.put(numberPoint,mapPoint.get(numberPoint));
            }
        }
        Object[] structure={discret,femodel,mapPointCont,mapPline,mapLineloop,mapPsurf,mapShell};
        return structure;
    }
    
    /**
     * \param
     * structure-структура данных файла BSMT
     * container-контейнер исходного файла
     * n-номер слоя 
     */
    //Создание файла geo
    public static String[] convertBsmToGeo(Object[] structure,String container,int n){
        TreeMap<Integer,String> discret=(TreeMap<Integer,String>)structure[0];
        TreeMap<Integer,String> femodel=(TreeMap<Integer,String>)structure[1];
        int numDiscret=Integer.parseInt(femodel.get(1).split("Discret:")[1]);
        TreeMap<Integer,String> mapPoint=(TreeMap<Integer,String>)structure[2];
        TreeMap<Integer,String> mapPline=(TreeMap<Integer,String>)structure[3];
        TreeMap<Integer,String> mapLineloop=(TreeMap<Integer,String>)structure[4];
        TreeMap<Integer,String> mapPsurf=(TreeMap<Integer,String>)structure[5];
        TreeMap<Integer,String> mapShell=(TreeMap<Integer,String>)structure[6];
        
        int fe_size=Integer.parseInt(discret.get(numDiscret).split("Fe_size:")[1]);
        File file=new File(container);
        String alias=file.getName().replace(".gsd.txt","");
        alias=alias.replace(".txt","");
        String geo_file_name=alias+".geo";
        
        ArrayList<Integer> arrSurf=new ArrayList<Integer>();
        ArrayList<Integer> arrLoop=new ArrayList<Integer>();
        ArrayList<Integer> arrLine=new ArrayList<Integer>();
        
        for(int key:mapShell.keySet()){
            String[] shell=mapShell.get(key).split(" ");
            arrSurf.add(Integer.parseInt(shell[3]));
        }
        for(int key:arrSurf){
            int surf=Integer.parseInt(mapPsurf.get(key));
            arrLoop.add(surf);
        }
        for(int key:arrLoop){
            int line=Integer.parseInt(mapLineloop.get(key).split(" ")[1]);
            arrLine.add(line);
        }
                
        File geoFile=new File(geo_file_name);
        String points="";
        for(int key:mapPoint.keySet()){
            String[] point=mapPoint.get(key).split(" ");
            points+="Point("+key+")={"+point[1]+", "+point[2]+", "+point[3]+", "+fe_size+"};\n";
        }
        
        String lines="",lineloops="",planeSurface="";
        int iLine=1,iSurface=1;
        for(int key:mapLineloop.keySet()){
            String line="";
            String[] lineloop=mapLineloop.get(key).split(" ");
            int numberPline=Integer.parseInt(lineloop[1]);
            String[] pline=mapPline.get(numberPline).split(" ");
            for(int i=0;i<pline.length;i++){
                if(i<pline.length-1){
                    lines+="Line("+iLine+")={"+pline[i]+", "+pline[i+1]+"};\n";
                    line+=iLine+", ";
                }
                else{
                    lines+="Line("+iLine+")={"+pline[i]+", "+pline[0]+"};\n";
                    line+=iLine;
                }
                iLine++;
            }
            lineloops+="Curve Loop("+iSurface+")={"+line+"};\n";
            planeSurface+="Plane Surface("+(iSurface++)+")={"+(iSurface-1)+"};\n";
        }
                
        try(FileOutputStream fStream=new FileOutputStream(geoFile);
            OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"windows-1251");
            BufferedWriter writer=new BufferedWriter(oWriter)){
            writer.write(points+lines+lineloops+planeSurface);
        }
        catch(IOException ex){}
        return new String[]{alias,geo_file_name};
    }
    
    /**
     * \param
     * geo_file_name-имя geo файла
     * fe_type-порядок элемента
     * fe_shape-форма элемента 
     */
    //Запуск приложения gmsh и создание файла сетки
    public static String runGmsh(String geo_file_name,int fe_type,int fe_shape){
        File gmshBatFile=new File("gmsh.bat");
        try(FileOutputStream fStream=new FileOutputStream(gmshBatFile);
            OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"windows-1251");
            BufferedWriter writer=new BufferedWriter(oWriter)){
            writer.write("chcp 65001\n");
            writer.write("%~dp0gmsh-4.4.1-Windows64\\gmsh.exe \""+geo_file_name+"\" -"+fe_shape+" -order "+fe_type+" -format msh2");
        }
        catch(IOException ex){}
        
        try{
            Process p=new ProcessBuilder("cmd","/C",gmshBatFile.getPath()).start();
            try{
                p.waitFor();
            }
            catch(InterruptedException ex){}
        }
        catch(IOException ex){}        
        String gmshMshFileName=geo_file_name.replace(".geo",".msh");
        return gmshMshFileName;
    }
    
    //Кривые
    public static String createGeoFile(TreeMap<Integer,String> mshPoints,TreeMap<Integer,String> mshLines,
                                    TreeMap<Integer,String> mshSplines,String container){
        File file=new File(container);
        String alias=file.getName().replace(".gsd.txt","");
        alias=alias.replace(".txt","");
        String geo_file_name=alias+".geo";
        File geoFile=new File(geo_file_name);
        
        String points="",lines="",splines="",curveLoops="",surfaces="";
        int countLoops=0;
        for(int key:mshPoints.keySet()){
            String[] point=mshPoints.get(key).split(" ");
            points+="Point("+key+")={"+point[0]+", "+point[1]+", "+point[2]+", 1.0};\n";
        }
        for(int key:mshLines.keySet()){
            String[] line=mshLines.get(key).split(" ");
            lines+="Line("+key+")={"+line[0]+", "+line[1]+"};\n";
            curveLoops+="Curve Loop("+(++countLoops)+")={"+key+", -"+(key+1)+"};\n";
            surfaces+="Plane Surface("+countLoops+")={"+countLoops+"};\n";
        }
        curveLoops+="Curve Loop("+(++countLoops)+")={";
        for(int key:mshSplines.keySet()){
            String[] spline=mshSplines.get(key).split(" ");
            splines+="Spline("+key+")={";
            if(key!=mshSplines.lastKey())curveLoops+=key+", ";
            else curveLoops+=key+"};\n";
            for(int i=0;i<spline.length;i++){
                if(i<spline.length-1)
                    splines+=spline[i]+", ";
                else
                    splines+=spline[i]+"};\n";
            }
        }
        surfaces+="Surface("+countLoops+")={"+countLoops+"};\n";
        try(FileOutputStream fStream=new FileOutputStream(geoFile);
            OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"windows-1251");
            BufferedWriter writer=new BufferedWriter(oWriter)){
            writer.write(points+lines+splines+curveLoops+surfaces);
        }
        catch(IOException ex){}
        return "";
    }
    //
    
    /**
     * \param
     * gmsh_file_name-файл сетки gmsh
     * fe_type-порядок элемента
     * fe_shape-форма элемента
     * mapPoint-массив точек
     */
    //Обработка файла сетки gmsh и создание списков узлов и элементов
    public static Object[] createMshDict(String gmsh_file_name,int fe_type,int fe_shape,TreeMap<Integer,String> mapPoint){
        double tol=0.00001;
        ArrayList<String> arrMsh=new ArrayList<String>();
        ArrayList<String> nodeList=new ArrayList<String>();
        ArrayList<String> elemList=new ArrayList<String>();
        String flag="";
        File gmshMshFile=new File(gmsh_file_name);
        try(FileInputStream fStream=new FileInputStream(gmshMshFile);
            InputStreamReader iReader=new InputStreamReader(fStream,"windows-1251");
            BufferedReader reader=new BufferedReader(iReader)){
            String s="";
            while((s=reader.readLine())!=null)
                arrMsh.add(s);
        }
        catch(IOException ex){}
        for(String line:arrMsh){
            if(line.contains("$Nodes"))flag="Nodes";
            else if(line.contains("$Elements"))flag="Elements";
            String[] splitLine=line.split(" ");
            if(splitLine.length==4 && flag.equals("Nodes"))
                nodeList.add(splitLine[0]+" "+splitLine[1]+" "+splitLine[2]+" "+splitLine[3]);
            else if(splitLine.length==8 && flag.equals("Elements"))
                elemList.add(splitLine[0]+" 5 "+splitLine[5]+" "+splitLine[6]+" "+splitLine[7]);
        }
        Object[] structure={nodeList,elemList};
        return structure;
    }
    
    /**
     * \param
     * structure-структура состоящая из списка узлов и списка элементов
     * alias-имя исходного файла
     * fe_type-порядок элемента
     * n-номер слоя
     * feFolder-папка для текстовых файлов сетки
     * numberModel-номер модели
     * arrBrush-список кистей 
     */
    //Создание текстовых файлов сетки в формате BSDC
    public static void expModel(Object[] structure,String alias,int fe_type,int n,
            String feFolder,int numberModel,ArrayList<String> arrBrush){
        StringBuilder points=new StringBuilder();
        StringBuilder plines=new StringBuilder();
        StringBuilder mats=new StringBuilder();
        ArrayList<String> arrPoints=(ArrayList<String>)structure[0];
        
        int indexFe=feFolder.lastIndexOf(".BSDC_");
        feFolder=feFolder.substring(0,indexFe+6);
        String styleName="";
        if(numberModel<10){
            styleName=feFolder+"\\FM-0"+numberModel+"\\styles.bsm.txt";
            feFolder=feFolder+"\\FM-0"+numberModel+"\\M-00";
        }
        else{
            styleName=feFolder+"\\FM-"+numberModel+"\\styles.bsm.txt";
            feFolder=feFolder+"\\FM-"+numberModel+"\\M-00";
        }
        File fe=new File(feFolder);
        if(!fe.exists())
            fe.mkdirs();
        
        //style
        String[] brush=null;
        for(int i=0;i<arrBrush.size();i++)
            if(i+1==n)brush=arrBrush.get(i).split(" ");
        StringBuilder style=new StringBuilder("@BRUSH\n");
        style.append("0 1 "+brush[1]+" "+brush[2]+" "+brush[3]+" "+brush[4]+"\n&\n\n");
        style.append("@PEN\n");
        style.append("0 0 "+brush[1]+" "+brush[2]+" "+brush[3]+" "+brush[4]+"\n&\n\n");
        style.append("@MSTYLE\n0 0 0 0 0\n&\n\n");
        style.append("@MAT\n1 0\n&");
        File styleFile=new File(styleName);
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(styleFile.getPath()))){
            writer.write(style.toString());
        }
        catch(IOException ex){}
        //
        
        for(String s:arrPoints)
            points.append(s+"\n");
        
        String pathFile=feFolder+"\\"+alias+"-F1-N-A-0-K-S1-00-Z-vNC.txt";
        File feFile=new File(pathFile);
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(pathFile))){
            writer.write(points.toString());
        }
        catch(IOException ex){}
        
        ArrayList<String> arrPlines=(ArrayList<String>)structure[1];
        for(String s:arrPlines)
            plines.append(s+"\n");
        pathFile=feFolder+"\\"+alias+"-F1-E-P-0-K-S1-00-Z-pET.txt";
        feFile=new File(pathFile);
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(pathFile))){
            writer.write(plines.toString());
        }
        catch(IOException ex){}
        
        pathFile=feFolder+"\\"+alias+"-F1-M-P-0-K-S1-00-Z-pMT.txt";
        feFile=new File(pathFile);
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(pathFile))){
            String[] plinesArr=plines.toString().split("\n");
            for(String pline:plinesArr)
                mats.append(pline.split(" ")[0]+" 1\n");
            writer.write(mats.toString());
        }
        catch(IOException ex){}
    }
    
    /**
     * \param
     * args[0]-файл GSD, который необходимо конвертировать
     * args[1]-папка, куда будет сохранен файл BSM
     */
    public static void main(String[] args) {
        //Получение данных из файла GSD
        File file=new File(args[0]);
        String name=file.getName();
        if(name.endsWith(".gsd.txt"))name=name.replace(".gsd.txt",".bsmt");
        else name=name.replace(".txt",".bsmt");
        ArrayList<String> arrGsd=new ArrayList<String>();
        try(FileInputStream fStream=new FileInputStream(file);
            InputStreamReader iReader=new InputStreamReader(fStream,"windows-1251");
            BufferedReader reader=new BufferedReader(iReader)){
            String s;
            while((s=reader.readLine())!=null)
                arrGsd.add(s);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        //
        
        //
        vtkPoints points=new vtkPoints();
        vtkCellArray polygons=new vtkCellArray();
        points.InsertNextPoint(10,30,5);
        points.InsertNextPoint(30,30,15);
        points.InsertNextPoint(50,30,5);
        points.InsertNextPoint(0,15,5);
        points.InsertNextPoint(20,15,15);
        points.InsertNextPoint(40,15,5);
        
        vtkPolygon polygon=new vtkPolygon();
        polygon.GetPointIds().SetId(0,0);
        polygon.GetPointIds().SetId(1,4);
        polygon.GetPointIds().SetId(2,3);
        polygons.InsertNextCell(polygon);
        
        polygon=new vtkPolygon();
        polygon.GetPointIds().SetId(0,0);
        polygon.GetPointIds().SetId(1,1);
        polygon.GetPointIds().SetId(2,4);
        polygons.InsertNextCell(polygon);
        
        polygon=new vtkPolygon();
        polygon.GetPointIds().SetId(0,1);
        polygon.GetPointIds().SetId(1,5);
        polygon.GetPointIds().SetId(2,4);
        polygons.InsertNextCell(polygon);
        
        polygon=new vtkPolygon();
        polygon.GetPointIds().SetId(0,1);
        polygon.GetPointIds().SetId(1,2);
        polygon.GetPointIds().SetId(2,5);
        polygons.InsertNextCell(polygon);
        
//        for(String figure:arrPline){
//            String[] figure1s=figure.split(" ");
//            if(figure1s.length<=3)continue;
//            int[] figure1=new int[figure1s.length-1];
//            for(int i=1;i<figure1s.length;i++)
//                figure1[i-1]=Integer.parseInt(figure1s[i]);
//            
//            vtkPolygon polygon=new vtkPolygon();
//            polygon.GetPointIds().SetNumberOfIds(figure1.length);
//            for(int i=0;i<figure1.length;i++){
//                String[] point=mapPoint.get(figure1[i]).split(" ");
//                double X=Double.parseDouble(point[1]);
//                double Y=Double.parseDouble(point[2]);
//                double Z=Double.parseDouble(point[3]);
//                points.InsertNextPoint(X,Y,Z);
//                polygon.GetPointIds().SetId(i,points.GetNumberOfPoints()-1);
//            }
//            polygons.InsertNextCell(polygon);
//        }
//        
//        vtkPolyData polyData=new vtkPolyData();
//        polyData.SetPoints(points);
//        polyData.SetPolys(polygons);
        
        vtkDelaunay2D del=new vtkDelaunay2D();
        //
        
        //Кривые
        int countApprom=4,sizeZ=2;
        double stepDisc=0,percent=0.1;
        boolean isCurve=false,isMin=false,isMax=false,isAvg=false;
        for(String arg:args){
            if(arg.contains("-countPoints="))
                countApprom=Integer.parseInt(arg.split("=")[1]);
            if(arg.contains("-curve"))
                isCurve=true;
            if(arg.contains("-minDiscret=")){
                percent=Double.parseDouble(arg.split("=")[1])/100;
                isMin=true;
            }
            if(arg.contains("-maxDiscret=")){
                percent=Double.parseDouble(arg.split("=")[1])/100;
                isMax=true;
            }
            if(arg.contains("-avgDiscret=")){
                percent=Double.parseDouble(arg.split("=")[1])/100;
                isAvg=true;
            }
            if(arg.contains("-discret="))
                stepDisc=Double.parseDouble(arg.split("=")[1]);
            if(arg.contains("-sizeZ="))
                sizeZ=Integer.parseInt(arg.split("=")[1]);
        }
        //
        
        int pointNum=0,lineloopNum=0,psurfNum=0,shellNum=0,barNum=0,countGroup=0; //Счетчики точек, замкнутых линий, плоских поверхностей, оболочек, стержней
        plineNum=0;
        TreeMap<Integer,String> mapPoint=new TreeMap<Integer,String>(); //Общий массив точек
        TreeMap<Integer,String> mapAjs=new TreeMap<Integer,String>(); //Массив смежностей колонок
        TreeMap<Integer,String> mapBar=new TreeMap<Integer,String>(); //Массив точек колонок с указанием материала каждой точки
        
        ArrayList<String> arrLineloop=new ArrayList<String>(); //Массив замкнутых линий
        ArrayList<String> arrPsurf=new ArrayList<String>(); //Массив плоских элементов
        ArrayList<String> arrShell=new ArrayList<String>(); //Массив оболочек
        ArrayList<String> arrBrush=new ArrayList<String>(); //Массив кистей
        ArrayList<String> arrGroup=new ArrayList<String>();
        
        TreeMap<Integer,String> mapGroup=new TreeMap<Integer,String>();
        //для рисования колонок и кривых
        ArrayList<String> arrBar=new ArrayList<String>(); //Массив стержней
        //Кривые
        ArrayList<String> arrTCurve=new ArrayList<String>();
        ArrayList<String> arrMCurve=new ArrayList<String>();
        ArrayList<String> arrTrianglesCurve=new ArrayList<String>();
        TreeMap<Integer,String> mapLayers=new TreeMap<Integer,String>();
        //
        
        String cont=""; //Точки внешнего контура
        int j=0;
        for(int i=0;i<arrGsd.size();i++){
            //Заполнение общего массива точек точками верхней грани фигуры
            if(arrGsd.get(i).contains("@COL")){
                for(j=i+1;j<arrGsd.size();j++){
                    if(arrGsd.get(j).contains("@"))break;
                    if(arrGsd.get(j).isEmpty())continue;
                    String[] sArr=arrGsd.get(j).split(" ");
                    String col="";
                    for(int k=2;k<sArr.length;k++)
                        if(!sArr[k].isEmpty())
                            col+=" "+sArr[k];
                    mapPoint.put(++pointNum,col+" ///Номер колонки: "+sArr[0]);
                }
                i=j;
            }
            //Заполнение массива точек колонок и общего массива точек нижележащими точками
            if(arrGsd.get(i).contains("@STRAT")){
                for(j=i+1;j<arrGsd.size();j++){
                    if(arrGsd.get(j).contains("@"))break;
                    if(arrGsd.get(j).isEmpty())continue;
                    String[] sArr=arrGsd.get(j).split(" ");
                    String strat=sArr[0];
                    for(int k=1;k<sArr.length;k++)
                        if(!sArr[k].isEmpty())
                            strat+=" "+sArr[k];
                    String[] stratArr=strat.split(" ");
                    
                    int key=Integer.parseInt(stratArr[0]);
                    String[] point=mapPoint.get(key).split(" ");
                    if(Integer.parseInt(stratArr[1])>1)point=mapPoint.get(pointNum).split(" ");
                    int z=Integer.parseInt(point[3]);
                    if(!isSimple){
                        mapPoint.put(++pointNum," "+point[1]+" "+point[2]+" "+(z-Integer.parseInt(stratArr[2]))
                            +" ///Номер колонки: "+stratArr[0]+". Номер слоя: "+stratArr[1]);
                    }
                    if(!mapBar.containsKey(key))
                        mapBar.put(key,key+" mat="+stratArr[3]+" "+pointNum);
                    else
                        mapBar.put(key,mapBar.get(key)+" mat="+stratArr[3]+" "+pointNum);
                    //
                    int numMat=Integer.parseInt(stratArr[3]);
                    if(!mapLayers.containsKey(numMat))
                        mapLayers.put(numMat,key+"="+pointNum);
                    else
                        mapLayers.put(numMat,mapLayers.get(numMat)+" "+key+"="+pointNum);
                    //
                }
                i=j;
            }
            //Формирование внешнего контура
            if(arrGsd.get(i).contains("@CONT")){
                for(j=i+1;j<arrGsd.size();j++){
                    if(arrGsd.get(j).contains("@"))break;
                    if(arrGsd.get(j).isEmpty())continue;
                    String[] sArr=arrGsd.get(j).split(" ");
                    for(int k=0;k<sArr.length;k++)
                        if(!sArr[k].isEmpty())
                            if(cont.isEmpty())cont=sArr[k];
                            else cont+=" "+sArr[k];
                }
                i=j;
            }
            //Заполнение массива смежностей колонок
            if(arrGsd.get(i).contains("@AJS")){
                for(j=i+1;j<arrGsd.size();j++){
                    if(arrGsd.get(j).contains("@"))break;
                    if(arrGsd.get(j).isEmpty())continue;
                    String[] sArr=arrGsd.get(j).split(" ");
                    String ajs="";
                    for(int k=1;k<sArr.length;k++)
                        if(!sArr[k].isEmpty())
                            ajs+=" "+sArr[k];
                    mapAjs.put(Integer.parseInt(sArr[0]),ajs);
                }
                i=j;
            }
            //Кривые
            if(isCurve){
                if(arrGsd.get(i).contains("@TCURVE")){
                    for(j=i+1;j<arrGsd.size();j++){
                        if(arrGsd.get(j).contains("@"))break;
                        if(arrGsd.get(j).isEmpty())continue;
                        String[] sArr=arrGsd.get(j).split(" ");
                        String tcurve="";
                        for(int k=1;k<sArr.length;k++)
                            if(!sArr[k].isEmpty())
                                tcurve+=sArr[k]+" ";
                        arrTCurve.add(tcurve);
                    }
                    i=j;
                }
                if(arrGsd.get(i).contains("@MCURVE")){
                    for(j=i+1;j<arrGsd.size();j++){
                        if(arrGsd.get(j).contains("@"))break;
                        if(arrGsd.get(j).isEmpty())continue;
                        String[] sArr=arrGsd.get(j).split(" ");
                        String mcurve="";
                        for(int k=1;k<sArr.length;k++)
                            if(!sArr[k].isEmpty())
                                mcurve+=sArr[k]+" ";
                        arrMCurve.add(mcurve);
                    }
                    i=j;
                }
            }
            //Заполнение массива кистей
            if(arrGsd.get(i).contains("@BRUSH")){
                for(j=i+1;j<arrGsd.size();j++){
                    if(arrGsd.get(j).contains("@"))break;
                    if(arrGsd.get(j).isEmpty())continue;
                    String[] sArr=arrGsd.get(j).split(" ");
                    String brush=sArr[0];
                    for(int k=1;k<sArr.length;k++)
                        if(!sArr[k].isEmpty())
                            brush+=" "+sArr[k];
                    arrBrush.add(brush);
                }
                i=j;
            }
        }
          
        TreeMap<String,Integer> mapPlines=new TreeMap<String,Integer>();        
        //Рисование ребер
//        for(int key:mapBar.keySet()){
//            String[] points1=mapBar.get(key).split(" ");
//            String[] ajsArr=mapAjs.get(key).split(" ");
//            for(int i=1;i<ajsArr.length;i++){
//                String pline=++plineNum+" "+key+" "+ajsArr[i];
//                sPline.append(pline+"\n");
//                arrLineloop.add(++lineloopNum+" 1 "+plineNum);
//                arrPsurf.add(++psurfNum+" "+lineloopNum);
//                arrShell.add(++shellNum+" 1 1 0 "+psurfNum);
//            }
//            for(int i=1;i<points1.length;i+=2){
//                String mat1=points1[i].split("=")[1];
//                String point1=points1[i+1];
//                //
//                if(i>1){
//                    String[] point=mapPoint.get(Integer.parseInt(points1[i+1])).split(" ");
//                    String[] pointPrev=mapPoint.get(Integer.parseInt(mapBar.get(key).split(" ")[i-1])).split(" ");
//                    int z1=Integer.parseInt(pointPrev[3]);
//                    int z2=Integer.parseInt(point[3]);
//                    if(z1==z2)continue;
//                }
//                //
//                for(j=1;j<ajsArr.length;j++){
//                    String[] points2=mapBar.get(Integer.parseInt(ajsArr[j])).split(" ");
//                    for(int k=1;k<points2.length;k+=2){
//                        String mat2=points2[k].split("=")[1];
//                        String point2=points2[k+1];
//                        if(mat1.equals(mat2)){
//                            sPline.append(++plineNum+" "+point1+" "+point2+"\n");
//                            arrLineloop.add(++lineloopNum+" 1 "+plineNum);
//                            arrPsurf.add(++psurfNum+" "+lineloopNum);
//                            arrShell.add(++shellNum+" 1 "+mat1+" 0 "+psurfNum);
//                        }
//                    }
//                }
//            }
//        }
        //
        
        //Кривые
        TreeMap<String,String> mapSplines=new TreeMap<String,String>();
        if(isCurve){
//            for(String mcurve:arrMCurve){
//                String[] mcurveArr=mcurve.split(" ");
//                String curve="";
//                int numCol=0,numLayer=0;
//                for(int i=0;i<mcurveArr.length;i+=2){
//                    numCol=Integer.parseInt(mcurveArr[i]);
//                    numLayer=Integer.parseInt(mcurveArr[i+1]);
//                    String[] layers=mapBar.get(numCol).split(" mat=");
//                    for(j=1;j<layers.length;j++){
//                        String[] layer=layers[j].split(" ");
//                        if(Integer.parseInt(layer[0])==numLayer){
//                            curve+=layer[1]+" ";
//                            break;
//                        }
//                    }
//                }
//                arrTCurve.add(curve);
//            }
            for(String layer:mapLayers.values()){
                String[] layerArr=layer.split(" ");
                String curve="";
                for(String tcurve:arrTCurve){
                    String[] tcurveArr=tcurve.split(" ");
                    int numCol=0;
                    for(String numPointTcurve:tcurveArr){
                        numCol=Integer.parseInt(numPointTcurve);
                        for(String sNumComLayer:layerArr){
                            int numComLayer=Integer.parseInt(sNumComLayer.split("=")[0]);
                            if(numComLayer==numCol){
                                curve+=sNumComLayer.split("=")[1]+" ";
                                break;
                            }
                        }
                    }
                    curve+="\n";
                }
                String[] curveArr=curve.split("\n");
                for(String layerCurve:curveArr)
                    if(layerCurve.split(" ").length>2)
                        arrTCurve.add(layerCurve);
            }
            
            for(int l=0;l<arrTCurve.size();l++){
                String[] tcurveArr=arrTCurve.get(l).split(" ");
                ArrayList<Point> points=new ArrayList<Point>();
                for(String point:tcurveArr){
                    String[] pointArr=mapPoint.get(Integer.parseInt(point)).split(" ");
                    double[] pointCoords={Double.parseDouble(pointArr[1]),Double.parseDouble(pointArr[2]),
                                Double.parseDouble(pointArr[3])};
                    points.add(new Point(pointCoords));
                }
                Spline3D spline=new Spline3D();
                spline.setPoints(points);
                spline.calcSpline();
                
                double segment=0, subSegment=0;
                double sizeSegment=1.0/(points.size()-1);
                double sizeSubSegment=sizeSegment/(countApprom+1);

                for(int i=0;i<tcurveArr.length-1;i++){
                    ArrayList<Point> p=new ArrayList<Point>();
                    subSegment+=sizeSubSegment;
                    int countSubSegment=0;
                    while(countSubSegment<countApprom){
                        p.add(spline.getPoint(subSegment));
                        subSegment+=sizeSubSegment;
                        countSubSegment++;
                    }
                    int numPoint1=Integer.parseInt(tcurveArr[i]);
                    int numPoint2=Integer.parseInt(tcurveArr[i+1]);

                    if(mapSplines.containsKey(numPoint1+" "+numPoint2) || mapSplines.containsKey(numPoint2+" "+numPoint1))
                        continue;
                    
                    double[] point3=new double[3];
                    String pline=""+numPoint1;
                    for(Point point:p){
                        point3[0]=point.x;
                        point3[1]=point.y;
                        String formatZ="##.";
                        for(int k=0;k<sizeZ;k++)
                            formatZ+="0";
                        DecimalFormat f=new DecimalFormat(formatZ);
                        point3[2]=Double.parseDouble(f.format(point.z).replace(",","."));
                        mapPoint.put(++pointNum," "+point3[0]+" "+point3[1]+" "+point3[2]+" ///Точка между "+numPoint1+" и "+numPoint2);
                        pline+=" "+pointNum;
                    }
                    pline+=" "+numPoint2;
                    mapSplines.put(numPoint1+" "+numPoint2,pline+" Направляющая="+(l+1)+" Сегмент="+(i+1));
                    segment+=sizeSegment;
                }
            }
        }
        //Рисование направляющей
//        for(int l=0;l<arrTCurve.size();l++){
//            String[] tcurveArr=arrTCurve.get(l).split(" ");
//            ArrayList<Point> points=new ArrayList<Point>();
//            for(String point:tcurveArr){
//                String[] pointArr=mapPoint.get(Integer.parseInt(point)).split(" ");
//                double[] p={Double.parseDouble(pointArr[1]),Double.parseDouble(pointArr[2]),
//                            Double.parseDouble(pointArr[3])};
//                points.add(new Point(p));
//            }
//            Spline3D spline=new Spline3D();
//            spline.setPoints(points);
//            spline.calcSpline();
//
//            double segment=0, subSegment=0;
//            double sizeSegment=1.0/(points.size()-1);
//            double sizeSubSegment=sizeSegment/50;
//
//            for(int i=0;i<tcurveArr.length-1;i++){
//                ArrayList<Point> p=new ArrayList<Point>();
//                subSegment+=sizeSubSegment;
//                int countSubSegment=0;
//                //while(subSegment<(segment+sizeSegment)){
//                while(countSubSegment<49){
//                    p.add(spline.getPoint(subSegment));
//                    subSegment+=sizeSubSegment;
//                    DecimalFormat f=new DecimalFormat("##.###");
//                    subSegment=Double.parseDouble(f.format(subSegment).replace(",","."));
//                    countSubSegment++;
//                }
//                int numPoint1=Integer.parseInt(tcurveArr[i]);
//                int numPoint2=Integer.parseInt(tcurveArr[i+1]);
//
//                if(mapSplines.containsKey(numPoint1+" "+numPoint2) || mapSplines.containsKey(numPoint2+" "+numPoint1))
//                    continue;
//                //test
//                if(!isSimple){
//                    String[] point1=null;
//                    String[] point2=null;
//                    String[] sArr=mapBar.get(numPoint1).split("mat=");
//                    for(int k=1;k<sArr.length;k++){
//                        int numPoint1layer=Integer.parseInt(sArr[k].split(" ")[1]);
//                        point1=mapPoint.get(numPoint1layer).split(" ");
//                        break;
//                    }
//                    sArr=mapBar.get(numPoint2).split("mat=");
//                    for(int k=1;k<sArr.length;k++){
//                        int numPoint2layer=Integer.parseInt(sArr[k].split(" ")[1]);
//                        point2=mapPoint.get(numPoint2layer).split(" ");
//                        break;
//                    }
//                    ArrayList<Point> points1=new ArrayList<Point>();
//                    points1.add(new Point(Double.parseDouble(point1[1]),Double.parseDouble(point1[2]),
//                                            Double.parseDouble(point1[3])));
//                    points1.add(new Point(Double.parseDouble(point2[1]),Double.parseDouble(point2[2]),
//                                            Double.parseDouble(point2[3])));
//                    Spline3D spline1=new Spline3D();
//                    spline1.setPoints(points1);
//                    spline1.calcSpline();
//                    double segment1=0;
//                    double subSegment1=1.0/(countApprom+1);
//                    for(Point point:p){
//                        segment1+=subSegment1;
//                        Point p1=spline1.getPoint(segment1);
//                        if(p1.z>point.z){
//                            double raznost=Math.abs(p1.z)-Math.abs(point.z);
//                            point.z+=Math.ceil(raznost);
//                        }
//                    }
//                }
//                //
//                double[] point3=new double[3];
//                String pline=""+numPoint1;
//                for(Point point:p){
//                    point3[0]=point.x;
//                    point3[1]=point.y;
//                    //point3[2]=point.z;
//                    String formatZ="##.";
//                    for(int k=0;k<sizeZ;k++)
//                        formatZ+="0";
//                    DecimalFormat f=new DecimalFormat(formatZ);
//                    point3[2]=Double.parseDouble(f.format(point.z).replace(",","."));
//                    mapPoint.put(++pointNum," "+point3[0]+" "+point3[1]+" "+point3[2]);
//                    pline+=" "+pointNum;
//                }
//                pline+=" "+numPoint2;
//                mapSplines.put(numPoint1+" "+numPoint2,pline);
//                //
//                String[] plineArr=pline.split(" ");
//                for(int k=0;k<plineArr.length-1;k++){
//                    mapPlines.put(plineArr[k]+" "+plineArr[k+1],++plineNum);
//                    arrBar.add(++barNum+" 1 1 0 "+plineNum+" "+plineNum);
//                }
//                //
//                segment+=sizeSegment;
//            }
//        }
        //
        
        //
        
        //Рисование поверхностей
        TreeMap<Integer,String> mapLayersDown=new TreeMap<Integer,String>(); //Массив точек нижней грани фигуры
        TreeMap<Integer,String> mapLayersUp=new TreeMap<Integer,String>(); //Массив точек верхней грани фигуры
        ArrayList<String> arrPlines=new ArrayList<String>(); //Массив уже использующихся фигур
        ArrayList<String> arrTriangles=new ArrayList<String>();
        for(int key:mapBar.keySet()){
            String[] points1=mapBar.get(key).split(" ");
            String[] ajsArr=mapAjs.get(key).split(" ");
            for(int i=1;i<ajsArr.length-1;i++){
                
                //
                boolean[] hasPoints={false,false,false};
                String[] points={""+key,ajsArr[i],ajsArr[i+1]};
                for(String s:arrTriangles){
                    String[] pline=s.split(" ");
                    for(int count=0;count<pline.length;count++){
                        String pointOne=pline[count];
                        for(String pointTwo:points){
                            if(pointOne.equals(pointTwo)){
                                hasPoints[count]=true;
                            }
                        }
                    }
                    if(hasPoints[0] && hasPoints[1] && hasPoints[2])
                        break;
                    else{
                        hasPoints[0]=false;
                        hasPoints[1]=false;
                        hasPoints[2]=false;
                    }
                }
                if(hasPoints[0] && hasPoints[1] && hasPoints[2])
                    continue;
                //
                
                //
                if(!mapLayersUp.containsKey(1))
                    mapLayersUp.put(1,key+" "+ajsArr[i]+" "+ajsArr[i+1]+"\n");
                else
                    mapLayersUp.put(1,mapLayersUp.get(1)+key+" "+ajsArr[i]+" "+ajsArr[i+1]+"\n");
                //
                
                String lineloop="";
                String pline1="";
                String pline2="";
                if(!mapPlines.containsKey(key+" "+ajsArr[i]) && !mapPlines.containsKey(ajsArr[i]+" "+key)){
                    mapPlines.put(key+" "+ajsArr[i],++plineNum);
                    pline1=key+" "+ajsArr[i];
                    lineloop+=" "+plineNum;
                }
                else if(mapPlines.containsKey(ajsArr[i]+" "+key)){
                    int num=mapPlines.get(ajsArr[i]+" "+key);
                    pline1=ajsArr[i]+" "+key;
                    lineloop+=" "+num;
                }
                else if(mapPlines.containsKey(key+" "+ajsArr[i])){
                    int num=mapPlines.get(key+" "+ajsArr[i]);
                    pline1=key+" "+ajsArr[i];
                    lineloop+=" "+num;
                }
                if(!mapPlines.containsKey(ajsArr[i]+" "+ajsArr[i+1]) && !mapPlines.containsKey(ajsArr[i+1]+" "+ajsArr[i])){
                    mapPlines.put(ajsArr[i]+" "+ajsArr[i+1],++plineNum);
                    pline2=ajsArr[i]+" "+ajsArr[i+1];
                    lineloop+=" "+plineNum;
                }
                else if(mapPlines.containsKey(ajsArr[i+1]+" "+ajsArr[i])){
                    int num=mapPlines.get(ajsArr[i+1]+" "+ajsArr[i]);
                    pline2=ajsArr[i+1]+" "+ajsArr[i];
                    lineloop+=" "+num;
                }
                else if(mapPlines.containsKey(ajsArr[i]+" "+ajsArr[i+1])){
                    int num=mapPlines.get(ajsArr[i]+" "+ajsArr[i+1]);
                    pline2=ajsArr[i]+" "+ajsArr[i+1];
                    lineloop+=" "+num;
                }
                if(!mapPlines.containsKey(ajsArr[i+1]+" "+key) && !mapPlines.containsKey(key+" "+ajsArr[i+1])){
                    mapPlines.put(ajsArr[i+1]+" "+key,++plineNum);
                    lineloop+=" "+plineNum;
                }
                else if(mapPlines.containsKey(key+" "+ajsArr[i+1])){
                    int num=mapPlines.get(key+" "+ajsArr[i+1]);
                    lineloop+=" "+num;
                }
                else if(mapPlines.containsKey(ajsArr[i+1]+" "+key)){
                    int num=mapPlines.get(ajsArr[i+1]+" "+key);
                    lineloop+=" "+num;
                }
                
                //Проверка правильности направления lineloop
                if(pline1.split(" ")[0].equals(pline2.split(" ")[0])){
                    String[] line=lineloop.split(" ");
                    lineloop="";
                    String s=line[2];
                    line[2]=line[3];
                    line[3]=s;
                    for(int k=1;k<line.length;k++)
                        lineloop+=" "+line[k];
                }
                else if(!pline1.split(" ")[1].equals(pline2.split(" ")[0])
                        && !pline1.split(" ")[1].equals(pline2.split(" ")[1])){
                    String[] line=lineloop.split(" ");
                    lineloop="";
                    String s=line[2];
                    line[2]=line[3];
                    line[3]=s;
                    for(int k=1;k<line.length;k++)
                        lineloop+=" "+line[k];
                }
                //
                
                arrLineloop.add(++lineloopNum+" 1"+lineloop+" ///Номер слоя: 1");
                arrPsurf.add(++psurfNum+" "+lineloopNum+" ///Горизонтальная поверхность над слоем 1");
                arrShell.add(++shellNum+" 1 1 0 "+psurfNum);
                //group+=" "+shellNum;
                if(!mapGroup.containsKey(1))
                    mapGroup.put(1,++countGroup+" 11 195 "+shellNum);
                else
                    mapGroup.put(1,mapGroup.get(1)+" "+shellNum);
                //
                arrTriangles.add(key+" "+ajsArr[i]+" "+ajsArr[i+1]);
            }
            if(!isSimple)
                for(int i=1;i<points1.length;i+=2){
                    String mat1=points1[i].split("=")[1];
                    String point1=points1[i+1];
                    for(j=1;j<ajsArr.length-1;j++){
                        String point2="",point3="";
                        String[] points2=mapBar.get(Integer.parseInt(ajsArr[j])).split(" ");
                        for(int k=1;k<points2.length;k+=2){
                            String mat2=points2[k].split("=")[1];
                            if(mat1.equals(mat2))
                                point2=points2[k+1];
                        }
                        if(point2.isEmpty())continue;
                        String[] points3=mapBar.get(Integer.parseInt(ajsArr[j+1])).split(" ");
                        for(int k=1;k<points3.length;k+=2){
                            String mat3=points3[k].split("=")[1];
                            if(mat1.equals(mat3))
                                point3=points3[k+1];
                        }
                        if(point3.isEmpty())continue;

                        //
                        boolean[] hasPoints={false,false,false};
                        String[] points={point1,point2,point3};
                        for(String s:arrTriangles){
                            String[] pline=s.split(" ");
                            for(int count=0;count<pline.length;count++){
                                String pointOne=pline[count];
                                for(String pointTwo:points){
                                    if(pointOne.equals(pointTwo)){
                                        hasPoints[count]=true;
                                    }
                                }
                            }
                            if(hasPoints[0] && hasPoints[1] && hasPoints[2])
                                break;
                            else{
                                hasPoints[0]=false;
                                hasPoints[1]=false;
                                hasPoints[2]=false;
                            }
                        }
                        if(hasPoints[0] && hasPoints[1] && hasPoints[2])
                            continue;
                        //
                        if(mat1.equals("1")){
                            //
                            if(!mapLayersDown.containsKey(1))
                                mapLayersDown.put(1,point1+" "+point2+" "+point3+"\n");
                            else
                                mapLayersDown.put(1,mapLayersDown.get(1)+point1+" "+point2+" "+point3+"\n");
                            //

                            String lineloop="";
                            String pline1="";
                            String pline2="";
                            if(!mapPlines.containsKey(point1+" "+point2) && !mapPlines.containsKey(point2+" "+point1)){
                                mapPlines.put(point1+" "+point2,++plineNum);
                                pline1=point1+" "+point2;
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point2+" "+point1)){
                                int num=mapPlines.get(point2+" "+point1);
                                pline1=point2+" "+point1;
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point1+" "+point2)){
                                int num=mapPlines.get(point1+" "+point2);
                                pline1=point1+" "+point2;
                                lineloop+=" "+num;
                            }
                            if(!mapPlines.containsKey(point2+" "+point3) && !mapPlines.containsKey(point3+" "+point2)){
                                mapPlines.put(point2+" "+point3,++plineNum);
                                pline2=point2+" "+point3;
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point3+" "+point2)){
                                int num=mapPlines.get(point3+" "+point2);
                                pline2=point3+" "+point2;
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point2+" "+point3)){
                                int num=mapPlines.get(point2+" "+point3);
                                pline2=point2+" "+point3;
                                lineloop+=" "+num;
                            }
                            if(!mapPlines.containsKey(point3+" "+point1) && !mapPlines.containsKey(point1+" "+point3)){
                                mapPlines.put(point3+" "+point1,++plineNum);
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point1+" "+point3)){
                                int num=mapPlines.get(point1+" "+point3);
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point3+" "+point1)){
                                int num=mapPlines.get(point3+" "+point1);
                                lineloop+=" "+num;
                            }

                            //Проверка правильности направления lineloop
                            if(pline1.split(" ")[0].equals(pline2.split(" ")[0])){
                                String[] line=lineloop.split(" ");
                                lineloop="";
                                String s=line[2];
                                line[2]=line[3];
                                line[3]=s;
                                for(int k=1;k<line.length;k++)
                                    lineloop+=" "+line[k];
                            }
                            else if(!pline1.split(" ")[1].equals(pline2.split(" ")[0])
                                    && !pline1.split(" ")[1].equals(pline2.split(" ")[1])){
                                String[] line=lineloop.split(" ");
                                lineloop="";
                                String s=line[2];
                                line[2]=line[3];
                                line[3]=s;
                                for(int k=1;k<line.length;k++)
                                    lineloop+=" "+line[k];
                            }
                            //
                            
                            arrLineloop.add(++lineloopNum+" 1"+lineloop+" ///Номер слоя: 1");
                            arrPsurf.add(++psurfNum+" "+lineloopNum+" ///Горизонтальная поверхность между слоем 1 и слоем 2");
                            arrShell.add(++shellNum+" 1 "+mat1+" 0 "+psurfNum);
                            //group
                            if(!mapGroup.containsKey(1+arrBrush.size()))
                                mapGroup.put(1+arrBrush.size(),++countGroup+" 11 195 "+shellNum);
                            else
                                mapGroup.put(1+arrBrush.size(),mapGroup.get(1+arrBrush.size())+" "+shellNum);
                            //
                            arrTriangles.add(point1+" "+point2+" "+point3);
                        }
                        else{
                            //
                            String mat4=points1[i-2].split("=")[1];
                            String point4=points1[i-1];
                            String point5="",point6="";

                            for(int k=1;k<points2.length;k+=2){
                                String mat2=points2[k].split("=")[1];
                                if(mat4.equals(mat2))
                                    point5=points2[k+1];
                            }
                            if(point5.isEmpty())continue;

                            for(int k=1;k<points3.length;k+=2){
                                String mat3=points3[k].split("=")[1];
                                if(mat4.equals(mat3))
                                    point6=points3[k+1];
                            }
                            if(point6.isEmpty())continue;

                            //
                            int numberMat=Integer.parseInt(mat1);
                            if(!mapLayersUp.containsKey(numberMat))
                                mapLayersUp.put(numberMat,point4+" "+point5+" "+point6+"\n");
                            else
                                mapLayersUp.put(numberMat,mapLayersUp.get(numberMat)+point4+" "+point5+" "+point6+"\n");
                            //

                            String lineloop="";
                            String pline1="";
                            String pline2="";
                            if(!mapPlines.containsKey(point4+" "+point5) && !mapPlines.containsKey(point5+" "+point4)){
                                mapPlines.put(point4+" "+point5,++plineNum);
                                pline1=point4+" "+point5;
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point5+" "+point4)){
                                int num=mapPlines.get(point5+" "+point4);
                                pline1=point5+" "+point4;
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point4+" "+point5)){
                                int num=mapPlines.get(point4+" "+point5);
                                pline1=point4+" "+point5;
                                lineloop+=" "+num;
                            }
                            if(!mapPlines.containsKey(point5+" "+point6) && !mapPlines.containsKey(point6+" "+point5)){
                                mapPlines.put(point5+" "+point6,++plineNum);
                                pline2=point5+" "+point6;
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point6+" "+point5)){
                                int num=mapPlines.get(point6+" "+point5);
                                pline2=point6+" "+point5;
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point5+" "+point6)){
                                int num=mapPlines.get(point5+" "+point6);
                                pline2=point5+" "+point6;
                                lineloop+=" "+num;
                            }
                            if(!mapPlines.containsKey(point6+" "+point4) && !mapPlines.containsKey(point4+" "+point6)){
                                mapPlines.put(point6+" "+point4,++plineNum);
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point4+" "+point6)){
                                int num=mapPlines.get(point4+" "+point6);
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point6+" "+point4)){
                                int num=mapPlines.get(point6+" "+point4);
                                lineloop+=" "+num;
                            }

                            //Проверка правильности направления lineloop
                            if(pline1.split(" ")[0].equals(pline2.split(" ")[0])){
                                String[] line=lineloop.split(" ");
                                lineloop="";
                                String s=line[2];
                                line[2]=line[3];
                                line[3]=s;
                                for(int k=1;k<line.length;k++)
                                    lineloop+=" "+line[k];
                            }
                            else if(!pline1.split(" ")[1].equals(pline2.split(" ")[0])
                                    && !pline1.split(" ")[1].equals(pline2.split(" ")[1])){
                                String[] line=lineloop.split(" ");
                                lineloop="";
                                String s=line[2];
                                line[2]=line[3];
                                line[3]=s;
                                for(int k=1;k<line.length;k++)
                                    lineloop+=" "+line[k];
                            }
                            //
                            
                            arrLineloop.add(++lineloopNum+" 1"+lineloop+" ///Номер слоя: "+numberMat);
                            arrPsurf.add(++psurfNum+" "+lineloopNum+
                                    " ///Горизонтальная поверхность между слоем "+mat4+" и слоем "+numberMat);
                            arrShell.add(++shellNum+" 1 "+mat1+" 0 "+psurfNum);
                            //group
                            if(!mapGroup.containsKey(numberMat))
                                mapGroup.put(numberMat,++countGroup+" 11 195 "+shellNum);
                            else
                                mapGroup.put(numberMat,mapGroup.get(numberMat)+" "+shellNum);
                            //

                            //

                            //
                            if(!mapLayersDown.containsKey(numberMat))
                                mapLayersDown.put(numberMat,point1+" "+point2+" "+point3+"\n");
                            else
                                mapLayersDown.put(numberMat,mapLayersDown.get(numberMat)+point1+" "+point2+" "+point3+"\n");
                            //

                            lineloop="";
                            pline1="";
                            pline2="";
                            if(!mapPlines.containsKey(point1+" "+point2) && !mapPlines.containsKey(point2+" "+point1)){
                                mapPlines.put(point1+" "+point2,++plineNum);
                                pline1=point1+" "+point2;
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point2+" "+point1)){
                                int num=mapPlines.get(point2+" "+point1);
                                pline1=point2+" "+point1;
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point1+" "+point2)){
                                int num=mapPlines.get(point1+" "+point2);
                                pline1=point1+" "+point2;
                                lineloop+=" "+num;
                            }
                            if(!mapPlines.containsKey(point2+" "+point3) && !mapPlines.containsKey(point3+" "+point2)){
                                mapPlines.put(point2+" "+point3,++plineNum);
                                pline2=point2+" "+point3;
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point3+" "+point2)){
                                int num=mapPlines.get(point3+" "+point2);
                                pline2=point3+" "+point2;
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point2+" "+point3)){
                                int num=mapPlines.get(point2+" "+point3);
                                pline2=point2+" "+point3;
                                lineloop+=" "+num;
                            }
                            if(!mapPlines.containsKey(point3+" "+point1) && !mapPlines.containsKey(point1+" "+point3)){
                                mapPlines.put(point3+" "+point1,++plineNum);
                                lineloop+=" "+plineNum;
                            }
                            else if(mapPlines.containsKey(point1+" "+point3)){
                                int num=mapPlines.get(point1+" "+point3);
                                lineloop+=" "+num;
                            }
                            else if(mapPlines.containsKey(point3+" "+point1)){
                                int num=mapPlines.get(point3+" "+point1);
                                lineloop+=" "+num;
                            }
                            
                            //Проверка правильности направления lineloop
                            if(pline1.split(" ")[0].equals(pline2.split(" ")[0])){
                                String[] line=lineloop.split(" ");
                                lineloop="";
                                String s=line[2];
                                line[2]=line[3];
                                line[3]=s;
                                for(int k=1;k<line.length;k++)
                                    lineloop+=" "+line[k];
                            }
                            else if(!pline1.split(" ")[1].equals(pline2.split(" ")[0])
                                    && !pline1.split(" ")[1].equals(pline2.split(" ")[1])){
                                String[] line=lineloop.split(" ");
                                lineloop="";
                                String s=line[2];
                                line[2]=line[3];
                                line[3]=s;
                                for(int k=1;k<line.length;k++)
                                    lineloop+=" "+line[k];
                            }
                            //
                            
                            arrLineloop.add(++lineloopNum+" 1"+lineloop+" ///Номер слоя: "+numberMat);
                            arrPsurf.add(++psurfNum+" "+lineloopNum+
                                    " ///Горизонтальная поверхность между слоем "+mat4+" и слоем "+numberMat);
                            arrShell.add(++shellNum+" 1 "+mat1+" 0 "+psurfNum);
                            //group
                            if(!mapGroup.containsKey(numberMat+arrBrush.size()))
                                mapGroup.put(numberMat+arrBrush.size(),++countGroup+" 11 195 "+shellNum);
                            else
                                mapGroup.put(numberMat+arrBrush.size(),mapGroup.get(numberMat+arrBrush.size())+" "+shellNum);
                            //
                            arrTriangles.add(point1+" "+point2+" "+point3);
                        }
                    }
                }
        }
        for(int key:mapGroup.keySet())
            arrGroup.add(mapGroup.get(key)+" \"Горизонтальная плоскость №"+key+"\"");
        mapGroup.clear();
        //

        //Нахождение шага сетки
//        TreeMap<String,Double> sizeSides=new TreeMap<String,Double>();
//        for(String s:sPline.toString().split("\n")){
//            String[] sArr=s.split(" ");
//            for(int i=1;i<sArr.length;i++){
//                int numPoint1=Integer.parseInt(sArr[i]);
//                int numPoint2=Integer.parseInt(sArr[1]);
//                if(i<sArr.length-1)numPoint2=Integer.parseInt(sArr[i+1]);
//                
//                if(sizeSides.containsKey(numPoint1+" "+numPoint2) 
//                        || sizeSides.containsKey(numPoint2+" "+numPoint1))
//                    continue;
//                
//                String[] point=mapPoint.get(numPoint1).split(" ");
//                double p1_x=Double.parseDouble(point[1]);
//                double p1_y=Double.parseDouble(point[2]);
//                double p1_z=Double.parseDouble(point[3]);
//                point=mapPoint.get(numPoint2).split(" ");
//                double p2_x=Double.parseDouble(point[1]);
//                double p2_y=Double.parseDouble(point[2]);
//                double p2_z=Double.parseDouble(point[3]);
//                if(p1_x!=p2_x)sizeSides.put(numPoint1+" "+numPoint2,Math.abs(p2_x-p1_x));
//                else if(p1_y!=p2_y)sizeSides.put(numPoint1+" "+numPoint2,Math.abs(p2_y-p1_y));
//                else if(p1_z!=p2_z)sizeSides.put(numPoint1+" "+numPoint2,Math.abs(p2_z-p1_z));
//                else sizeSides.put(numPoint1+" "+numPoint2,0.0);
//            }
//        }
//        if(isAvg || (!isMin && !isMax && !isAvg && stepDisc==0)){
//            double avgSize=0;
//            for(String key:sizeSides.keySet())
//                avgSize+=sizeSides.get(key);
//            avgSize/=sizeSides.size();
//            stepDisc=avgSize*percent;
//        }
//        else if(isMin){
//            Double minSize=null;
//            for(String key:sizeSides.keySet()){
//                double size=sizeSides.get(key);
//                if(minSize==null || size<minSize)minSize=size;
//            }
//            stepDisc=minSize*percent;
//        }
//        else if(isMax){
//            Double maxSize=null;
//            for(String key:sizeSides.keySet()){
//                double size=sizeSides.get(key);
//                if(maxSize==null || size>maxSize)maxSize=size;
//            }
//            stepDisc=maxSize*percent;
//        }
        //
                
        //Закрашивание внешних ребер
//        if(!isSimple)
//            for(int key:mapBar.keySet()){
//                String[] col=mapBar.get(key).split(" ");
//                for(int i=0;i<col.length-1;i+=2){
//                    if(i==0){
//                        String[] ajsArr=mapAjs.get(key).split(" ");
//                        for(j=1;j<ajsArr.length;j++){
//                            String point1=col[i];
//                            String point2=mapBar.get(Integer.parseInt(ajsArr[j])).split(" ")[i];
//                            String point3=mapBar.get(Integer.parseInt(ajsArr[j])).split(" ")[i+2];
//                            String mat3=mapBar.get(Integer.parseInt(ajsArr[j])).split(" ")[i+1].split("=")[1];
//                            String point4="";
//                            for(int k=1;k<col.length;k++)
//                                if(col[k].equals("mat="+mat3))
//                                    point4=col[k+1];
//                            if(point4.isEmpty())continue;
//                            if(Arrays.asList(cont.split(" ")).contains(point1) &&
//                                    Arrays.asList(cont.split(" ")).contains(point2)){
//
//                                //Проверка на дубликацию фигур
//                                boolean[] hasPoints={false,false,false,false};
//                                String[] points={point1,point2,point3,point4};
//                                for(String s:arrPlines){
//                                    String[] pline=s.split(" ");
//                                    for(int count=0;count<pline.length;count++){
//                                        String pointOne=pline[count];
//                                        for(String pointTwo:points){
//                                            if(pointOne.equals(pointTwo)){
//                                                hasPoints[count]=true;
//                                            }
//                                        }
//                                    }
//                                    if(hasPoints[0] && hasPoints[1] && hasPoints[2] && hasPoints[3])
//                                        break;
//                                    else{
//                                        hasPoints[0]=false;
//                                        hasPoints[1]=false;
//                                        hasPoints[2]=false;
//                                        hasPoints[3]=false;
//                                    }
//                                }
//                                if(hasPoints[0] && hasPoints[1] && hasPoints[2] && hasPoints[3])
//                                    continue;
//                                //
//                                String lineloop="";
//                                String pline1="";
//                                String pline2="";
//                                if(!mapPlines.containsKey(point1+" "+point2) && !mapPlines.containsKey(point2+" "+point1)){
//                                    mapPlines.put(point1+" "+point2,++plineNum);
//                                    pline1=point1+" "+point2;
//                                    lineloop+=" "+plineNum;
//                                }
//                                else if(mapPlines.containsKey(point2+" "+point1)){
//                                    int num=mapPlines.get(point2+" "+point1);
//                                    pline1=point2+" "+point1;
//                                    lineloop+=" "+num;
//                                }
//                                else if(mapPlines.containsKey(point1+" "+point2)){
//                                    int num=mapPlines.get(point1+" "+point2);
//                                    pline1=point1+" "+point2;
//                                    lineloop+=" "+num;
//                                }
//                                if(!mapPlines.containsKey(point2+" "+point3) && !mapPlines.containsKey(point3+" "+point2)){
//                                    mapPlines.put(point2+" "+point3,++plineNum);
//                                    pline2=point2+" "+point3;
//                                    lineloop+=" "+plineNum;
//                                }
//                                else if(mapPlines.containsKey(point3+" "+point2)){
//                                    int num=mapPlines.get(point3+" "+point2);
//                                    pline2=point3+" "+point2;
//                                    lineloop+=" "+num;
//                                }
//                                else if(mapPlines.containsKey(point2+" "+point3)){
//                                    int num=mapPlines.get(point2+" "+point3);
//                                    pline2=point2+" "+point3;
//                                    lineloop+=" "+num;
//                                }
//                                if(!mapPlines.containsKey(point3+" "+point4) && !mapPlines.containsKey(point4+" "+point3)){
//                                    mapPlines.put(point3+" "+point4,++plineNum);
//                                    lineloop+=" "+plineNum;
//                                }
//                                else if(mapPlines.containsKey(point4+" "+point3)){
//                                    int num=mapPlines.get(point4+" "+point3);
//                                    lineloop+=" "+num;
//                                }
//                                else if(mapPlines.containsKey(point3+" "+point4)){
//                                    int num=mapPlines.get(point3+" "+point4);
//                                    lineloop+=" "+num;
//                                }
//                                if(!mapPlines.containsKey(point4+" "+point1) && !mapPlines.containsKey(point1+" "+point4)){
//                                    mapPlines.put(point4+" "+point1,++plineNum);
//                                    lineloop+=" "+plineNum;
//                                }
//                                else if(mapPlines.containsKey(point1+" "+point4)){
//                                    int num=mapPlines.get(point1+" "+point4);
//                                    lineloop+=" "+num;
//                                }
//                                else if(mapPlines.containsKey(point4+" "+point1)){
//                                    int num=mapPlines.get(point4+" "+point1);
//                                    lineloop+=" "+num;
//                                }
//
//                                //Проверка правильности направления lineloop
//                                if(pline1.split(" ")[0].equals(pline2.split(" ")[0])){
//                                    String[] line=lineloop.split(" ");
//                                    lineloop="";
//                                    String s=line[2];
//                                    line[2]=line[4];
//                                    line[4]=s;
//                                    for(int k=1;k<line.length;k++)
//                                        lineloop+=" "+line[k];
//                                }
//                                else if(!pline1.split(" ")[1].equals(pline2.split(" ")[0])
//                                        && !pline1.split(" ")[1].equals(pline2.split(" ")[1])){
//                                    String[] line=lineloop.split(" ");
//                                    lineloop="";
//                                    String s=line[2];
//                                    line[2]=line[4];
//                                    line[4]=s;
//                                    for(int k=1;k<line.length;k++)
//                                        lineloop+=" "+line[k];
//                                }
//                                //
//                                
//                                arrLineloop.add(++lineloopNum+" 1"+lineloop+" ///Номер слоя: "+mat3);
//                                arrPsurf.add(++psurfNum+" "+lineloopNum+" ///Боковая поверхность слоя "+mat3);
//                                arrShell.add(++shellNum+" 1 "+mat3+" 0 "+psurfNum);
//                                //group
//                                int numberMat=Integer.parseInt(mat3);
//                                if(!mapGroup.containsKey(numberMat))
//                                    mapGroup.put(numberMat,++countGroup+" 11 195 "+shellNum);
//                                else
//                                    mapGroup.put(numberMat,mapGroup.get(numberMat)+" "+shellNum);
//                                //
//                                arrPlines.add(point1+" "+point2+" "+point3+" "+point4);
//                            }
//                        }
//                    }
//                    else{
//                        String[] ajsArr=mapAjs.get(key).split(" ");
//                        for(j=1;j<ajsArr.length;j++){
//                            String point1=col[i];
//                            String point2="";
//                            String mat1=mapBar.get(key).split(" ")[i-1].split("=")[1];
//                            String[] col1=mapBar.get(Integer.parseInt(ajsArr[j])).split(" ");
//                            for(int k=1;k<col1.length;k+=2)
//                                if(col1[k].equals("mat="+mat1))
//                                    point2=col1[k+1];
//
//                            if(point2.isEmpty())continue;
//
//                            String point3=""+(Integer.parseInt(point2)+1);
//                            String mat3="";
//                            for(int k=1;k<col1.length;k+=2)
//                                if(col1[k+1].equals(point3))
//                                    mat3=col1[k];
//                            String point4="";
//                            for(int k=1;k<col.length;k++)
//                                if(col[k].equals(mat3))
//                                    point4=col[k+1];
//
//                            if(point4.isEmpty())continue;
//
//                            if(Arrays.asList(cont.split(" ")).contains(""+key) &&
//                                    Arrays.asList(cont.split(" ")).contains(ajsArr[j])){
//
//                                //Проверка на дубликацию фигур
//                                boolean[] hasPoints={false,false,false,false};
//                                String[] points={point1,point2,point3,point4};
//                                for(String s:arrPlines){
//                                    String[] pline=s.split(" ");
//                                    for(int count=0;count<pline.length;count++){
//                                        String pointOne=pline[count];
//                                        for(String pointTwo:points){
//                                            if(pointOne.equals(pointTwo)){
//                                                hasPoints[count]=true;
//                                            }
//                                        }
//                                    }
//                                    if(hasPoints[0] && hasPoints[1] && hasPoints[2] && hasPoints[3])
//                                        break;
//                                    else{
//                                        hasPoints[0]=false;
//                                        hasPoints[1]=false;
//                                        hasPoints[2]=false;
//                                        hasPoints[3]=false;
//                                    }
//                                }
//                                if(hasPoints[0] && hasPoints[1] && hasPoints[2] && hasPoints[3])
//                                    continue;
//                                //
//
//                                String lineloop="";
//                                String pline1="";
//                                String pline2="";
//                                if(!mapPlines.containsKey(point1+" "+point2) && !mapPlines.containsKey(point2+" "+point1)){
//                                    mapPlines.put(point1+" "+point2,++plineNum);
//                                    pline1=point1+" "+point2;
//                                    lineloop+=" "+plineNum;
//                                }
//                                else if(mapPlines.containsKey(point2+" "+point1)){
//                                    int num=mapPlines.get(point2+" "+point1);
//                                    pline1=point2+" "+point1;
//                                    lineloop+=" "+num;
//                                }
//                                else if(mapPlines.containsKey(point1+" "+point2)){
//                                    int num=mapPlines.get(point1+" "+point2);
//                                    pline1=point1+" "+point2;
//                                    lineloop+=" "+num;
//                                }
//                                if(!mapPlines.containsKey(point2+" "+point3) && !mapPlines.containsKey(point3+" "+point2)){
//                                    mapPlines.put(point2+" "+point3,++plineNum);
//                                    pline2=point2+" "+point3;
//                                    lineloop+=" "+plineNum;
//                                }
//                                else if(mapPlines.containsKey(point3+" "+point2)){
//                                    int num=mapPlines.get(point3+" "+point2);
//                                    pline2=point3+" "+point2;
//                                    lineloop+=" "+num;
//                                }
//                                else if(mapPlines.containsKey(point2+" "+point3)){
//                                    int num=mapPlines.get(point2+" "+point3);
//                                    pline2=point2+" "+point3;
//                                    lineloop+=" "+num;
//                                }
//                                if(!mapPlines.containsKey(point3+" "+point4) && !mapPlines.containsKey(point4+" "+point3)){
//                                    mapPlines.put(point3+" "+point4,++plineNum);
//                                    lineloop+=" "+plineNum;
//                                }
//                                else if(mapPlines.containsKey(point4+" "+point3)){
//                                    int num=mapPlines.get(point4+" "+point3);
//                                    lineloop+=" "+num;
//                                }
//                                else if(mapPlines.containsKey(point3+" "+point4)){
//                                    int num=mapPlines.get(point3+" "+point4);
//                                    lineloop+=" "+num;
//                                }
//                                if(!mapPlines.containsKey(point4+" "+point1) && !mapPlines.containsKey(point1+" "+point4)){
//                                    mapPlines.put(point4+" "+point1,++plineNum);
//                                    lineloop+=" "+plineNum;
//                                }
//                                else if(mapPlines.containsKey(point1+" "+point4)){
//                                    int num=mapPlines.get(point1+" "+point4);
//                                    lineloop+=" "+num;
//                                }
//                                else if(mapPlines.containsKey(point4+" "+point1)){
//                                    int num=mapPlines.get(point4+" "+point1);
//                                    lineloop+=" "+num;
//                                }
//
//                                //Проверка правильности направления lineloop
//                                if(pline1.split(" ")[0].equals(pline2.split(" ")[0])){
//                                    String[] line=lineloop.split(" ");
//                                    lineloop="";
//                                    String s=line[2];
//                                    line[2]=line[4];
//                                    line[4]=s;
//                                    for(int k=1;k<line.length;k++)
//                                        lineloop+=" "+line[k];
//                                }
//                                else if(!pline1.split(" ")[1].equals(pline2.split(" ")[0])
//                                        && !pline1.split(" ")[1].equals(pline2.split(" ")[1])){
//                                    String[] line=lineloop.split(" ");
//                                    lineloop="";
//                                    String s=line[2];
//                                    line[2]=line[4];
//                                    line[4]=s;
//                                    for(int k=1;k<line.length;k++)
//                                        lineloop+=" "+line[k];
//                                }
//                                //
//                                
//                                arrLineloop.add(++lineloopNum+" 1"+lineloop+" ///Номер слоя: "+mat3.split("=")[1]);
//                                arrPsurf.add(++psurfNum+" "+lineloopNum+" ///Боковая поверхность слоя "+mat3.split("=")[1]);
//                                arrShell.add(++shellNum+" 1 "+mat3.split("=")[1]+" 0 "+psurfNum);
//                                //group
//                                int numberMat=Integer.parseInt(mat3.split("=")[1]);
//                                if(!mapGroup.containsKey(numberMat))
//                                    mapGroup.put(numberMat,++countGroup+" 11 195 "+shellNum);
//                                else
//                                    mapGroup.put(numberMat,mapGroup.get(numberMat)+" "+shellNum);
//                                //
//                                arrPlines.add(point1+" "+point2+" "+point3+" "+point4);
//                            }
//                        }
//                    }
//                }
//            }
//        for(int key:mapGroup.keySet())
//            arrGroup.add(mapGroup.get(key)+" \"Боковая поверхность №"+key+"\"");
//        mapGroup.clear();
        //
        
        //Кривые
        ArrayList<String> deleteList=new ArrayList<String>();
        ArrayList<String> newList=new ArrayList<String>();
        if(isCurve){
            for(String key:mapPlines.keySet()){
                int num=mapPlines.get(key);
                String keyRev="";
                keyRev=key.split(" ")[1]+" "+key.split(" ")[0];
                for(String key1:mapSplines.keySet()){
                    if(key.equals(key1)){
                        deleteList.add(key);
                        newList.add(num+";"+mapSplines.get(key1));
                    }
                    if(keyRev.equals(key1)){
                        deleteList.add(key);
                        String[] spline=mapSplines.get(key1).split(" ");
                        String revSpline="";
                        for(int i=spline.length-3;i>=0;i--)
                            revSpline+=spline[i]+" ";
                        revSpline+=spline[spline.length-2]+" "+spline[spline.length-1];
                        newList.add(num+";"+revSpline);
                    }
                }
            }
            for(String s:deleteList){
                mapPlines.remove(s);
            }
            for(String s:newList){
                String[] sArr=s.split(";");
                String[] sArr1=sArr[1].split(" ");
                String numCurve=sArr1[sArr1.length-2].split("=")[1];
                String numSegment=sArr1[sArr1.length-1].split("=")[1];
                String pline="";
                for(int i=0;i<sArr1.length-2;i++)
                    pline+=sArr1[i]+" ";
                //mapPlines.put(sArr[1]+" {t=2} ///Заменено ребро "+sArr[0],Integer.parseInt(sArr[0]));
                mapPlines.put(pline+"{t=2} ///Направляющяя "+numCurve+". Сегмент "+numSegment
                        +". Колонки: "+sArr1[0]+" "+sArr1[sArr1.length-3]+".",Integer.parseInt(sArr[0]));
            }
        }
        //
        
//        String[] namesFile={"","",""};
//        int numberModel=0;
//        for(int key:mapLayersUp.keySet()){
//            numberModel++;
//            ArrayList<String> arrPline=new ArrayList<String>();
//            String[] layer=mapLayersUp.get(key).split("\n");
//            for(String pline:layer)
//                arrPline.add(pline);
//            Object[] structure=createStructure(arrPline,mapPoint);
//            String[] geo=convertBsmToGeo(structure,args[0],key);
//            int fe_type=1,fe_shape=2;
//            String gmshMsh=runGmsh(geo[1],fe_type,fe_shape);
//            structure=createMshDict(gmshMsh,fe_type,fe_shape,(TreeMap<Integer,String>)structure[2]);
//            expModel(structure,geo[0],fe_type,key,args[1],numberModel,arrBrush);
//            if(namesFile[0].isEmpty())namesFile[0]=geo[0]+".geo";
//            if(namesFile[1].isEmpty())namesFile[1]=gmshMsh;
//            if(namesFile[2].isEmpty())namesFile[2]="gmsh.bat";
//        }
//        
//        for(int key:mapLayersDown.keySet()){
//            numberModel++;
//            ArrayList<String> arrPline=new ArrayList<String>();
//            String[] layer=mapLayersDown.get(key).split("\n");
//            for(String pline:layer)
//                arrPline.add(pline);
//            Object[] structure=createStructure(arrPline,mapPoint);
//            String[] geo=convertBsmToGeo(structure,args[0],key);
//            int fe_type=1,fe_shape=2;
//            String gmshMsh=runGmsh(geo[1],fe_type,fe_shape);
//            structure=createMshDict(gmshMsh,fe_type,fe_shape,(TreeMap<Integer,String>)structure[2]);
//            expModel(structure,geo[0],fe_type,key,args[1],numberModel,arrBrush);
//        }
//        
//        for(int i=0;i<3;i++){
//            File file1=new File(namesFile[i]);
//            if(file1.exists())
//                file1.delete();
//        }
                
        //group
//        for(int i=0;i<arrBar.size();i++){
//            String[] bar=arrBar.get(i).split(" ");
//            if(i==0)arrGroup.add(++countGroup+" 11 200 "+bar[0]+" \"Направляющая\"");
//            else if(i==1)group+=(++countGroup)+" 11 200 "+bar[0];
//            else group+=" "+bar[0];
//        }
//        arrGroup.add(group+" \"Сегменты\"");
//        group=(++countGroup)+" 11 195";
//        for(String s:arrShell){
//            String[] shell=s.split(" ");
//            group+=" "+shell[0];
//        }
//        arrGroup.add(group+" \"Фигура\"");
        //
        
        //Информация для разбиения сетки
        //StringBuilder discret=new StringBuilder("@DISCRET\n");
        StringBuilder discret=new StringBuilder("///Дискретизации (1)\n");
        discret.append("@DISCRET\n");
        discret.append("1 11 "+stepDisc+"\n");
        discret.append("&\n\n");
        discret.append("///Задачи (1)\n");
        discret.append("@TASK\n");
        discret.append("100 //GMSH+CodeAster\n");
        discret.append("&\n\n");
        discret.append("///Мономодели (1)\n");
        discret.append("@FEMODEL\n");
        discret.append("1 100 1\n");
        discret.append("&\n\n");
        //
        
        //Формирование сечений
        StringBuilder elsec=new StringBuilder("@380\n");
        elsec.append("1 31 50 0 0 1 0 0 \"Разрез\"\n"
                    +"2 21 10 0 0 1 0 0 \"Сечение\"\n"
                    +"3 21 30 0 0 1 0 0 \"Сечение2\"\n"
                    +"4 21 50 0 0 1 0 0 \"Сечение3\"\n"
                    +"5 21 70 0 0 1 0 0 \"Сечение4\"\n"
                    +"6 21 0 0 0 0 1 0 \"Сечение5\"\n"
                    +"7 21 0 10 0 0 1 0 \"Сечение6\"\n"
                    +"8 21 0 20 0 0 1 0 \"Сечение7\"\n"
                    +"9 21 0 0 -1 0 0 1 \"Сечение8\"\n"
                    +"10 21 0 0 -5 0 0 1 \"Сечение9\"\n"
                    +"11 21 0 0 -10 0 0 1 \"Сечение10\"\n"
                    +"12 22 10 0 0 1 0 0 {step=20 numStep=5} \"Группа_сечений1\"\n"
                    +"13 33 25 0 0 1 0 0 \"Отрез\" {w=5}\n"
                    +"14 32 20 0 0 1 0 0 \"Вырез\" {w=16}\n"
                    +"15 20 0 10 0 0 1 0 \"Группа_сечений2\" {section=2;3;4;5;6;7;8}\n"
                    +"&\n");
        //
        
        //generalization
//        for(int key:mapPoint.keySet()){
//            TreeMap<Integer,String> mapPointLayer=new TreeMap<Integer,String>();
//            ArrayList<String> arrPlineLayer=new ArrayList<String>();
//            mapPointLayer=mapPoint.get(key);
//            for(String pline:arrPline)
//                arrPlineLayer.add(pline);
//            generalization(arrPlineLayer,mapPointLayer);
//            for(int point:resultPoint.keySet()){
//                sPoint.append(++countPoint+" "+resultPoint.get(point)+"\n");
//                sHPoint.append(countPoint+" "+countPoint+" 130 "+(key+1)+"\n");
//            }
//            for(String pline:resultPline){
//                sPline.append(++countPline+" "+pline+"\n");
//                arrLineloop.add(++lineloopNum+" 1 "+countPline);
//                arrPsurf.add(++psurfNum+" "+lineloopNum);
//                if(key==0)arrShell.add(++shellNum+" 1 1 0 "+psurfNum);
//                else arrShell.add(++shellNum+" 1 "+key+" 0 "+psurfNum);
//            }
//        }
        //
        
        
        //Подготовка данных для записи в файл
        BasicFileAttributes attr = null;
        Path path=file.toPath();
        try{
            attr = Files.readAttributes(path, BasicFileAttributes.class);
        }
        catch (IOException exception){
            System.out.println("Exception handled when trying to get file " +
                    "attributes: " + exception.getMessage());
        }
        Date creationDate=new Date(attr.creationTime().toMillis());
        String crDay=""+creationDate.getDate();
        if(crDay.length()==1)crDay="0"+crDay;
        String crMonth=""+(creationDate.getMonth()+1);
        if(crMonth.length()==1)crMonth="0"+crMonth;
        String crYear=""+(creationDate.getYear()+1900);
        String crHour=""+creationDate.getHours();
        if(crHour.length()==1)crHour="0"+crHour;
        String crMinute=""+creationDate.getMinutes();
        if(crMinute.length()==1)crMinute="0"+crMinute;
        String crSecond=""+creationDate.getSeconds();
        if(crSecond.length()==1)crSecond="0"+crSecond;
        
        StringBuilder strOut=new StringBuilder("/// --------------------------------------------\n");
        strOut.append("/// Преобразование файла GSD в файл BSMT.\n");
        strOut.append("/// Исходный файл: "+file.getName()+".\n");
        strOut.append("/// Дата создания исходного файла: "+crDay+"."+crMonth+"."+crYear+" "
            +crHour+":"+crMinute+":"+crSecond+".\n");
        strOut.append("/// Размер исходного файла: "+attr.size()+" байт.\n");
        strOut.append("/// Результирующий файл: "+name+".\n");
        
        GregorianCalendar cal=new GregorianCalendar();
        String hour=""+cal.get(Calendar.HOUR_OF_DAY);
        String minute=""+cal.get(Calendar.MINUTE);
        String second=""+cal.get(Calendar.SECOND);
        if(hour.length()==1)hour="0"+hour;
        if(minute.length()==1)minute="0"+minute;
        if(second.length()==1)second="0"+second;
        String day=""+cal.get(Calendar.DAY_OF_MONTH);
        String month=""+(cal.get(Calendar.MONTH)+1);
        String year=""+cal.get(Calendar.YEAR);
        if(day.length()==1)day="0"+day;
        if(month.length()==1)month="0"+month;
        strOut.append("/// Дата преобразования: "+day+"."+month+"."+year+" "+hour+":"
            +minute+":"+second+".\n\n");
        
        String sBegin="///BRUSH="+arrBrush.size()+" PEN="+arrBrush.size()
                +" MSTYLE="+arrBrush.size()+" MAT="+arrBrush.size()+" ELSEC=1 DISCRET=1 TASK=1 FEMODEL=1";
        if(mapPoint.size()>0)sBegin+=" POINT="+mapPoint.size();
        if(plineNum>0)sBegin+=" PLINE="+plineNum;
        if(arrLineloop.size()>0)sBegin+=" LINELOOP="+arrLineloop.size();
        if(arrPsurf.size()>0)sBegin+=" PSURF="+arrPsurf.size();
        if(arrShell.size()>0)sBegin+=" SHELL="+arrShell.size();
        if(arrGroup.size()>0)sBegin+=" GROUP="+arrGroup.size();
        sBegin+=".\n\n";
        
        strOut.append(sBegin);
        
        //StringBuilder strOut=new StringBuilder("///Кисти ("+arrBrush.size()+")\n@BRUSH\n");
        strOut.append("///Кисти ("+arrBrush.size()+")\n@BRUSH\n");
        for(int i=0;i<arrBrush.size();i++){
            String[] brush=null;
            if(i<arrBrush.size())brush=arrBrush.get(i).split(" ");
            //else if(i==arrBrush.size())brush=new String[]{"1","0","0","0","255"};
            //else if(i==arrBrush.size()+1)brush=new String[]{"1","255","0","0","255"};
            strOut.append(i+" 1 "+brush[1]+" "+brush[2]+" "+brush[3]+" "+brush[4]+"\n");
        }
        strOut.append("&\n\n");
        
        strOut.append("///Перья ("+arrBrush.size()+")\n@PEN\n");
        for(int i=0;i<arrBrush.size();i++){
            String[] brush=null;
            if(i<arrBrush.size())brush=arrBrush.get(i).split(" ");
            //else if(i==arrBrush.size())brush=new String[]{"1","0","0","0","255"};
            //else if(i==arrBrush.size()+1)brush=new String[]{"1","255","0","0","255"};
            
            if(i==0 || i==arrBrush.size() || i==arrBrush.size()+1)
                strOut.append(i+" 0 "+brush[1]+" "+brush[2]+" "+brush[3]+" "+brush[4]+"\n");
            else
                strOut.append(i+" 0 "+brush[1]+" "+brush[2]+" "+brush[3]+" 0\n");
        }
        strOut.append("&\n\n");
        
        strOut.append("///Стили отображения материалов ("+arrBrush.size()+")\n@MSTYLE\n");
        for(int i=0;i<arrBrush.size();i++)
            strOut.append(i+" "+i+" "+i+" "+i+" "+i+"\n");
        strOut.append("&\n\n");
        
        strOut.append("///Материалы ("+arrBrush.size()+")\n@MAT\n");
        for(int i=0;i<arrBrush.size();i++)
            strOut.append((i+1)+" "+i+"\n");
        strOut.append("&\n\n");
        
        strOut.append("///Поперечные сечения элемента (1)\n");
        strOut.append("@ELSEC\n1\n&\n\n");
        
        strOut.append(discret);
        
        strOut.append("///Точки ("+mapPoint.size()+")\n@POINT\n");
        for(int key:mapPoint.keySet())
            strOut.append(key+mapPoint.get(key)+"\n");
        strOut.append("&\n\n");
        
        //
        TreeMap<Integer,String> mapPlines1=new TreeMap<Integer,String>();
        for(String key:mapPlines.keySet())
            mapPlines1.put(mapPlines.get(key),key);
        
        strOut.append("///Полилинии ("+plineNum+")\n@PLINE\n");
        for(int key:mapPlines1.keySet())
            strOut.append(key+" "+mapPlines1.get(key)+"\n");
        strOut.append("&\n\n");
        //
        
        //Рисование колонок
//        for(int key:mapBar.keySet()){
//            String[] barArr=mapBar.get(key).split(" ");
//            for(int i=0;i<barArr.length-1;i+=2){
//                strOut.append(++plineNum+" "+barArr[i]+" "+barArr[i+2]+"\n");
//                arrBar.add(plineNum+" 1 "+barArr[i+1].split("=")[1]+" 1 "+plineNum+" "+plineNum);
//            }
//        }
                  
        //Колонки
//        strOut.append("///Стержни ("+arrBar.size()+")\n@BAR\n");
//        for(String bar:arrBar)
//            strOut.append(bar+"\n");
//        strOut.append("&\n\n");
        
        strOut.append("///Замкнутые линии ("+arrLineloop.size()+")\n@LINELOOP\n");
        for(String lineloop:arrLineloop)
            strOut.append(lineloop+"\n");
        strOut.append("&\n\n");
        
        strOut.append("///Плоские элементы поверхностей ("+arrPsurf.size()+")\n@PSURF\n");
        for(String psurf:arrPsurf)
            strOut.append(psurf+"\n");
        strOut.append("&\n\n");
        
        strOut.append("///Оболочки ("+arrShell.size()+")\n@SHELL\n");
        for(String shell:arrShell)
            strOut.append(shell+"\n");
        strOut.append("&\n\n");
        
        //group
        strOut.append("///Группы ("+arrGroup.size()+")\n@GROUP\n");
        for(String s:arrGroup)
            strOut.append(s+"\n");
        strOut.append("&\n\n");
        //
        
        strOut.append(elsec);
        //
        
        file=new File(args[1]+"\\"+name);
        //Непосредственная запись в файл
        try(FileOutputStream fStream=new FileOutputStream(file);
            OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"windows-1251");
            BufferedWriter writer=new BufferedWriter(oWriter)){
            writer.write(strOut.toString());
        }
        catch(IOException ex){}
        //
    }
}
