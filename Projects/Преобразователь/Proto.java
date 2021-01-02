import java.io.*;
import java.net.*;
import java.util.*;
import java.util.ArrayList;
import vtk.*;

public class Proto {
//    static{
//        //System.load("C:\\Users\\user\\Documents\\Visual Studio 2017\\Projects\\CheckKeys\\x64\\Release\\CheckKeys.dll");
//        System.loadLibrary("CheckKeys");
//    }
    //нативный метод
    static String pathDll;
    public native boolean[] checkKeys(boolean[] keys,char[] charStr,int length);
    //
    
    static String[] Sargs; //Аргументы командной строки
    static ArrayList<String> arrBsmt; //Содержимое файла
    static TreeMap<Integer,String> mapPointM; //Массив точек
    
    static TreeMap<Integer,String> mapPlineM=new TreeMap<Integer,String>(); //массив полилиний
    static TreeMap<Integer,String> mapLineloopM=new TreeMap<Integer,String>(); //массив замкнутых линий
    static TreeMap<Integer,String> mapPsurfM=new TreeMap<Integer,String>(); //массив плоских элементов
    static TreeMap<Integer,String> mapShellM=new TreeMap<Integer,String>();
    static TreeMap<Integer,String> mapEbodyM=new TreeMap<Integer,String>();
    static TreeMap<Integer,String> mapSbodyM=new TreeMap<Integer,String>();
    static TreeMap<Integer,String> mapBarM=new TreeMap<Integer,String>();
    static TreeMap<Integer,String> mapMatM=new TreeMap<Integer,String>();
    
    static ArrayList<ArrayList<double[]>> arrCoords; //Отсортированные списки по X,Y и Z
    static ArrayList<vtkPolyData> polyDataContours; //массив фигур
    //storey
    static ArrayList<Double> arrLevels; //массив уровней
    static TreeMap<Double,double[]> mapLevels; //массив координат уровней
    //
    
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
    
    //Выделение внешнего контура
    public static void boundaryEdgesArr(ArrayList<vtkPolyData> polyData){
        polyDataContours=new ArrayList<vtkPolyData>();          
        //
        for(int i=0;i<polyData.size();i++){
            vtkFeatureEdges featureEdges=new vtkFeatureEdges();
            featureEdges.SetInputData(polyData.get(i));
            featureEdges.BoundaryEdgesOn();
            featureEdges.FeatureEdgesOff();
            featureEdges.ManifoldEdgesOff();
            featureEdges.NonManifoldEdgesOff();
            featureEdges.Update();

            vtkNamedColors Color = new vtkNamedColors();
            double ActorColor[]=new double[4];
            String[] colors=Color.GetColorNames().split("\n");
            Color.GetColorRGB(colors[(i+1)*20],ActorColor);
                        
            //Получение внешнего контура
            vtkPolyData polyDataFe=featureEdges.GetOutput();
            
            vtkPolyData polyDataEdge=new vtkPolyData();
            polyDataEdge.SetPoints(polyDataFe.GetPoints());
            polyDataEdge.SetLines(polyDataFe.GetLines());
            //
            
            //Получение точек внешнего контура
            vtkPoints points=polyDataFe.GetPoints();            
            //
            polyDataContours.add(polyDataEdge);
            //
        }
    }
    
    //Сортировка массива точек по координатам X,Y и Z. Для каждой координаты указывается
    //количество точек, у которых имеется данная координата
    public static String sortPoints(TreeMap<Integer,String> mapPointS){
        ArrayList<double[]> arrPoint=new ArrayList<double[]>();
        for(int i:mapPointS.keySet()){
            String[] sArr=mapPointS.get(i).split(" ");
            double x=Double.parseDouble(sArr[1]);
            double y=Double.parseDouble(sArr[2]);
            double z=Double.parseDouble(sArr[3]);
            arrPoint.add(new double[]{i,x,y,z});
        }

        TreeMap<Double,Integer> coordX=new TreeMap<Double,Integer>();
        for(double[] point:arrPoint){
            double coord=point[1];
            if(coordX.containsKey(coord))
                coordX.put(coord,coordX.get(coord)+1);
            else
                coordX.put(coord,1);
        }

        TreeMap<Double,Integer> coordY=new TreeMap<Double,Integer>();
        for(double[] point:arrPoint){
            double coord=point[2];
            if(coordY.containsKey(coord))
                coordY.put(coord,coordY.get(coord)+1);
            else
                coordY.put(coord, 1);
        }

        //storey
        mapLevels=new TreeMap<Double,double[]>();
        //

        TreeMap<Double,Integer> coordZ=new TreeMap<Double,Integer>();
        for(double[] point:arrPoint){
            double coord=point[3];
            if(coordZ.containsKey(coord)){
                coordZ.put(coord,coordZ.get(coord)+1);
                //storey Получение координат уровней
                double minX=mapLevels.get(coord)[0];
                double maxX=mapLevels.get(coord)[1];
                double minY=mapLevels.get(coord)[2];
                double maxY=mapLevels.get(coord)[3];
                if(minX>point[1])minX=point[1];
                if(maxX<point[1])maxX=point[1];
                if(minY>point[2])minY=point[2];
                if(maxY<point[2])maxY=point[2];
                mapLevels.put(coord,new double[]{minX,maxX,minY,maxY});
                //
            }
            else{
                coordZ.put(coord,1);
                //storey Заполнение массива координат
                double[] minMax={point[1],point[1],point[2],point[2]};
                mapLevels.put(coord,minMax);
                //
            }
        }

        //storey Заполнение массива уровней
        arrLevels=new ArrayList<Double>();
        for(double key:coordZ.keySet())
            arrLevels.add(key);
        //

        arrCoords=new ArrayList<ArrayList<double[]>>();
        ArrayList<double[]> coord=new ArrayList<double[]>();
        for(double key:coordX.keySet())
            coord.add(new double[]{key,coordX.get(key)});
        arrCoords.add(coord);
        coord=new ArrayList<double[]>();
        for(double key:coordY.keySet())
            coord.add(new double[]{key,coordY.get(key)});
        arrCoords.add(coord);
        coord=new ArrayList<double[]>();
        for(double key:coordZ.keySet())
            coord.add(new double[]{key,coordZ.get(key)});
        arrCoords.add(coord);
        return "";
    }
    
    //Формирование массива фигур
    public static ArrayList<vtkPolyData> stage3(String[] args,boolean isGen){
        double tochnost=Double.parseDouble(args[1]);
        int typeCoord=0;
        double coord=0.0;
        double[] xyzArray=new double[args.length-2];
        for(int i=0;i<xyzArray.length;i++){
            String[] sCoord=args[i+2].split("=");
            if(sCoord[0].equals("-X"))typeCoord=1;
            else if(sCoord[0].equals("-Y"))typeCoord=2;
            else if(sCoord[0].equals("-Z"))typeCoord=3;
            else{
                System.out.println("Неправильно задан уровень координаты");
                System.exit(0);
            }
            coord=Double.parseDouble(sCoord[1]);
            xyzArray[i]=coord;
        }

        TreeMap<Integer,String> mapPoint=new TreeMap<Integer,String>(); //массив точек
        for(int point:mapPointM.keySet())
            mapPoint.put(point,mapPointM.get(point));
        TreeMap<Integer,String> mapPline=new TreeMap<Integer,String>(); //массив полилиний
        for(int pline:mapPlineM.keySet())
            mapPline.put(pline,mapPlineM.get(pline));
        TreeMap<Integer,String> mapLineloop=new TreeMap<Integer,String>(); //массив замкнутых линий
        TreeMap<Integer,String> mapPsurf=new TreeMap<Integer,String>(); //массив плоских элементов
        TreeMap<Integer,String> mapShell=new TreeMap<Integer,String>(); //массив оболочек
        //ebody,sbody
        TreeMap<Integer,String> mapEbody=new TreeMap<Integer,String>(); //массив элементарных тел
        TreeMap<Integer,String> mapSbody=new TreeMap<Integer,String>(); //массив тел
        //Заполнение массивов
        int j=0;
        for(int i=0;i<arrBsmt.size();i++){
            if(arrBsmt.get(i).contains("@LINELOOP") || arrBsmt.get(i).contains("@127")){
                for(j=i+1;j<arrBsmt.size();j++){
                    if(arrBsmt.get(j).contains("&"))break;
                    String[] sArr=arrBsmt.get(j).split(" ");
                    String lineloop="";
                    for(int k=1;k<sArr.length;k++)
                        if(!sArr[k].equals(""))
                            lineloop+=" "+sArr[k];
                    if(!lineloop.equals(""))mapLineloop.put(Integer.parseInt(sArr[0]),lineloop);
                    if(!isGen && !lineloop.equals(""))mapLineloopM.put(Integer.parseInt(sArr[0]),lineloop);
                }
                i=j;
            }
            if(arrBsmt.get(i).contains("@PSURF") || arrBsmt.get(i).contains("@130")){
                for(j=i+1;j<arrBsmt.size();j++){
                    if(arrBsmt.get(j).contains("&"))break;
                    String[] sArr=arrBsmt.get(j).split(" ");
                    String psurf="";
                    for(int k=1;k<sArr.length;k++)
                        if(!sArr[k].equals(""))
                            psurf+=" "+sArr[k];
                    if(!psurf.equals(""))mapPsurf.put(Integer.parseInt(sArr[0]),psurf);
                    if(!isGen && !psurf.equals(""))mapPsurfM.put(Integer.parseInt(sArr[0]),psurf);
                }
                i=j;
            }
            if(arrBsmt.get(i).contains("@SHELL") || arrBsmt.get(i).contains("@195")){
                for(j=i+1;j<arrBsmt.size();j++){
                    if(arrBsmt.get(j).contains("&"))break;
                    String[] sArr=arrBsmt.get(j).split(" ");
                    String shell="";
                    for(int k=1;k<sArr.length;k++)
                        if(!sArr[k].equals(""))
                            shell+=" "+sArr[k];
                    if(!shell.equals(""))mapShell.put(Integer.parseInt(sArr[0]),shell);
                    if(!isGen && !shell.equals(""))mapShellM.put(Integer.parseInt(sArr[0]),shell);
                }
                i=j;
            }
            //ebody,sbody
            if(arrBsmt.get(i).contains("@EBODY") || arrBsmt.get(i).contains("@135")){
                for(j=i+1;j<arrBsmt.size();j++){
                    if(arrBsmt.get(j).contains("&"))break;
                    String[] sArr=arrBsmt.get(j).split(" ");
                    String ebody="";
                    for(int k=1;k<sArr.length;k++)
                        if(!sArr[k].equals(""))
                            ebody+=" "+sArr[k];
                    if(!ebody.equals(""))mapEbody.put(Integer.parseInt(sArr[0]),ebody);
                    if(!isGen && !ebody.equals(""))mapEbodyM.put(Integer.parseInt(sArr[0]),ebody);
                }
                i=j;
            }
            if(arrBsmt.get(i).contains("@SBODY") || arrBsmt.get(i).contains("@190")){
                for(j=i+1;j<arrBsmt.size();j++){
                    if(arrBsmt.get(j).contains("&"))break;
                    String[] sArr=arrBsmt.get(j).split(" ");
                    String sbody="";
                    for(int k=1;k<sArr.length;k++)
                        if(!sArr[k].equals(""))
                            sbody+=" "+sArr[k];
                    if(!sbody.equals(""))mapSbody.put(Integer.parseInt(sArr[0]),sbody);
                    if(!isGen && !sbody.equals(""))mapSbodyM.put(Integer.parseInt(sArr[0]),sbody);
                }
                i=j;
            }
            if(!isGen){
                if(arrBsmt.get(i).contains("@MAT") || arrBsmt.get(i).contains("@300")){
                    for(j=i+1;j<arrBsmt.size();j++){
                        if(arrBsmt.get(j).contains("&"))break;
                        String[] sArr=arrBsmt.get(j).split(" ");
                        String mat="";
                        for(int k=1;k<sArr.length;k++)
                            if(!sArr[k].equals(""))
                                mat+=" "+sArr[k];
                        if(!mat.equals(""))mapMatM.put(Integer.parseInt(sArr[0]),mat);
                    }
                    i=j;
                }
                if(arrBsmt.get(i).contains("@BAR") || arrBsmt.get(i).contains("@200")){
                    for(j=i+1;j<arrBsmt.size();j++){
                        if(arrBsmt.get(j).contains("&"))break;
                        String[] sArr=arrBsmt.get(j).split(" ");
                        String bar="";
                        for(int k=1;k<sArr.length;k++)
                            if(!sArr[k].equals(""))
                                bar+=" "+sArr[k];
                        if(!bar.equals(""))mapBarM.put(Integer.parseInt(sArr[0]),bar);
                    }
                    i=j;
                }
            }
        }
        
        //фильтрация точек по заданным координатам X,Y и Z
        for(j=0;j<xyzArray.length;j++){
            coord=xyzArray[j];
            if(j==0)
                for(int i:mapPoint.keySet()){
                    String[] point=mapPoint.get(i).split(" ");
                    double pointCoord=Double.parseDouble(point[typeCoord]);
                    if((pointCoord<(coord-tochnost)) || (pointCoord>(coord+tochnost)))
                        mapPoint.put(i,mapPoint.get(i)+" null");
                }
            else
                for(int i:mapPoint.keySet()){
                    if(!mapPoint.get(i).endsWith("null"))continue;
                    String[] point=mapPoint.get(i).split(" ");
                    double pointCoord=Double.parseDouble(point[typeCoord]);
                    if((pointCoord>=(coord-tochnost)) && (pointCoord<=(coord+tochnost)))
                        mapPoint.put(i,mapPoint.get(i).substring(0,mapPoint.get(i).length()-5));
                }
        }
        //

        //Фильтрация полилиний по заданным координатам X,Y и Z
        for(int key:mapPline.keySet()){
            String[] pline=mapPline.get(key).split(" ");
            for(int i=1;i<pline.length;i++){
                String[] point=mapPoint.get(Integer.parseInt(pline[i])).split(" ");
                if(point[point.length-1].endsWith("null")){
                    mapPline.put(key,mapPline.get(key)+" null");
                    break;
                }
            }
        }
        //Фильтрация оболочек
        for(int key:mapShell.keySet()){
            boolean haveMat=false;
            String[] shell=mapShell.get(key).split(" ");
            for(int i=4;i<shell.length;i++){
                String[] psurf=mapPsurf.get(Integer.parseInt(shell[i])).split(" ");
                for(j=1;j<psurf.length;j++){
                    String[] lineloop=mapLineloop.get(Integer.parseInt(psurf[j])).split(" ");
                    for(int k=2;k<lineloop.length;k++){
                        String pline=mapPline.get(Integer.parseInt(lineloop[k]));
                        if(!pline.endsWith("null")){
                            haveMat=true;
                            mapPline.put(Integer.parseInt(lineloop[k]),
                                    mapPline.get(Integer.parseInt(lineloop[k]))+" mat="+shell[2]);
                        }
                    }
                }
            }
        }
        //ebody,sbody Фильтрация тел
        for(int keySbody:mapSbody.keySet()){
            String[] sbody=mapSbody.get(keySbody).split(" ");
            boolean haveMat=false;
            for(int i=3;i<sbody.length;i++){
                String[] ebody=mapEbody.get(Integer.parseInt(sbody[i])).split(" ");
                for(j=1;j<ebody.length;j++){
                    String[] psurf=mapPsurf.get(Integer.parseInt(ebody[j])).split(" ");
                    for(int k=1;k<psurf.length;k++){
                        String[] lineloop=mapLineloop.get(Integer.parseInt(psurf[k])).split(" ");
                        for(int l=2;l<lineloop.length;l++){
                            String pline=mapPline.get(Integer.parseInt(lineloop[l]));
                            if(!pline.endsWith("null")){
                                haveMat=true;
                                mapPline.put(Integer.parseInt(lineloop[k]),
                                        mapPline.get(Integer.parseInt(lineloop[k]))+" mat="+sbody[3]);
                            }
                        }
                    }
                }
            }
        }
        //

        HashMap<Integer,vtkCellArray> polygonsArr=new HashMap<Integer,vtkCellArray>();
        ArrayList<vtkPolyData> polyDataArr=new ArrayList<vtkPolyData>();

        //shell Заполнение массива фигур оболочками
        int pline=-1;
        vtkPoints points=new vtkPoints();
        for(int key:mapShell.keySet()){
            boolean haveMat=false;
            String[] shell=mapShell.get(key).split(" ");
            for(int i=4;i<shell.length;i++){
                String[] psurf=mapPsurf.get(Integer.parseInt(shell[i])).split(" ");
                for(j=1;j<psurf.length;j++){
                    String[] lineloop=mapLineloop.get(Integer.parseInt(psurf[j])).split(" ");
                    for(int k=2;k<lineloop.length;k++){
                        String figure=mapPline.get(Integer.parseInt(lineloop[k]));
                        if(figure.endsWith("null"))continue;
                        String[] figure1s=figure.split(" ");
                        int[] figure1=new int[figure1s.length-1]; 
                        for(int l=1;l<figure1s.length-1;l++)
                            figure1[l-1]=Integer.parseInt(figure1s[l]);

                        vtkPolygon polygon = new vtkPolygon();
                        polygon.GetPointIds().SetNumberOfIds(figure1.length-1);  //количество элементов
                        for(int l=0;l<figure1.length-1;l++){
                            String[] point=mapPoint.get(figure1[l]).split(" ");
                            double X=Double.parseDouble(point[1]);
                            double Y=Double.parseDouble(point[2]);
                            double Z=Double.parseDouble(point[3]);
                            points.InsertNextPoint(X,Y,Z);
                            polygon.GetPointIds().SetId(l,points.GetNumberOfPoints()-1); //порядковый номер элемента и номер точки
                        }

                        int num=Integer.parseInt(figure1s[figure1s.length-1].split("=")[1]);
                        if(!polygonsArr.containsKey(num)){
                            vtkCellArray polygons=new vtkCellArray();
                            polygons.InsertNextCell(polygon);
                            polygonsArr.put(num,polygons);
                        }
                        else{
                            vtkCellArray polygons=polygonsArr.get(num);
                            polygons.InsertNextCell(polygon);
                            polygonsArr.put(num,polygons);
                        }
                    }
                }
            }
        }
        //

        //ebody,sbody Заполнение массива фигур телами
        for(int keySbody:mapSbody.keySet()){
            String[] sbody=mapSbody.get(keySbody).split(" ");
            boolean haveMat=false;
            for(int i=3;i<sbody.length;i++){
                String[] ebody=mapEbody.get(Integer.parseInt(sbody[i])).split(" ");
                for(j=1;j<ebody.length;j++){
                    String[] psurf=mapPsurf.get(Integer.parseInt(ebody[j])).split(" ");
                    for(int k=1;k<psurf.length;k++){
                        String[] lineloop=mapLineloop.get(Integer.parseInt(psurf[k])).split(" ");
                        for(int l=2;l<lineloop.length;l++){
                            String figure=mapPline.get(Integer.parseInt(lineloop[l]));
                            if(figure.endsWith("null"))continue;
                            String[] figure1s=figure.split(" ");
                            int[] figure1=new int[figure1s.length-2];
                            for(int a=1;a<figure1s.length-1;a++)
                                figure1[a-1]=Integer.parseInt(figure1s[a]);

                            vtkPolygon polygon=new vtkPolygon();
                            polygon.GetPointIds().SetNumberOfIds(figure1.length);
                            for(int a=0;a<figure1.length;a++){
                                String[] point=mapPoint.get(figure1[a]).split(" ");
                                double X=Double.parseDouble(point[1]);
                                double Y=Double.parseDouble(point[2]);
                                double Z=Double.parseDouble(point[3]);
                                points.InsertNextPoint(X,Y,Z);
                                polygon.GetPointIds().SetId(a,points.GetNumberOfPoints()-1);
                            }
                            int num=Integer.parseInt(figure1s[figure1s.length-1].split("=")[1]);
                            if(!polygonsArr.containsKey(num)){
                                vtkCellArray polygons=new vtkCellArray();
                                polygons.InsertNextCell(polygon);
                                polygonsArr.put(num,polygons);
                            }
                            else{
                                vtkCellArray polygons=polygonsArr.get(num);
                                polygons.InsertNextCell(polygon);
                                polygonsArr.put(num,polygons);
                            }
                        }
                    }
                }
            }
        }
        //

        for(int i:polygonsArr.keySet()){
            vtkPolyData polyData=new vtkPolyData();
            polyData.SetPoints(points);
            polyData.SetPolys(polygonsArr.get(i));

            vtkCleanPolyData a=new vtkCleanPolyData();
            a.AddInputData(polyData);
            a.Update();

            vtkPolyData b=a.GetOutput();
            polyDataArr.add(b);
        }

        return polyDataArr;
    }
    
    //Получение данных о конечно-элементной модели
    public static String getDataFM01(String[] args,String lPath){
        File file=new File(args[0]);
        if(file.isFile()){
            System.out.println("Выберите контейнер BSDC");
            System.exit(0);
        }
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
        
        //Точки
        name=args[0]+"\\FM-01\\M-00\\"+fileName1+"-F1-N-A-0-K-S1-00-Z-vNC.txt";
        file=new File(name);
        if(!file.exists()){
            //Сообщение об ошибке
            try{
                BufferedWriter writer=null;
                if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\001-E.txt"));
                else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\001-E.txt"));
                writer.write("Не хватает папки: "+name+". Для дальнейшей работы необходимо разбить модель на конечные элементы (0103 Создание КЭ модели из протомодели)");
                writer.close();
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //Полилинии
        name=args[0]+"\\FM-01\\M-00\\"+fileName1+"-F1-E-P-0-K-S1-00-Z-pET.txt";
        file=new File(name);
        if(!file.exists()){
            //Сообщение об ошибке
            try{
                BufferedWriter writer=null;
                if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\001-E.txt"));
                else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\001-E.txt"));
                writer.write("Не хватает папки: "+name+". Для дальнейшей работы необходимо разбить модель на конечные элементы (0103 Создание КЭ модели из протомодели)");
                writer.close();
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //Оболочки
        name=args[0]+"\\FM-01\\M-00\\"+fileName1+"-F1-M-P-0-K-S1-00-Z-pMT.txt";
        file=new File(name);
        if(!file.exists()){
            //Сообщение об ошибке
            try{
                BufferedWriter writer=null;
                if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\001-E.txt"));
                else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\001-E.txt"));
                writer.write("Не хватает папки: "+name+". Для дальнейшей работы необходимо разбить модель на конечные элементы (0103 Создание КЭ модели из протомодели)");
                writer.close();
            }
            catch(IOException ex){}
            return "exit";
            //
        }
        ArrayList<String> arrShell=new ArrayList<String>();
        try(FileInputStream fStream=new FileInputStream(file);
            InputStreamReader iReader=new InputStreamReader(fStream,"windows-1251");
            BufferedReader reader=new BufferedReader(iReader)){
            String s;
            while((s=reader.readLine())!=null)
                arrShell.add(s);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //Стержни
        name=args[0]+"\\FM-01\\M-00\\"+fileName1+"-F1-E-B-0-K-S1-00-Z-pET.txt";
        file=new File(name);
        if(!file.exists()){
            //Сообщение об ошибке
            try{
                BufferedWriter writer=null;
                if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\001-E.txt"));
                else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\001-E.txt"));
                writer.write("Не хватает папки: "+name+". Для дальнейшей работы необходимо разбить модель на конечные элементы (0103 Создание КЭ модели из протомодели)");
                writer.close();
            }
            catch(IOException ex){}
            return "exit";
            //            
        }
        ArrayList<String> arrBar=new ArrayList<String>();
        try(FileInputStream fStream=new FileInputStream(file);
            InputStreamReader iReader=new InputStreamReader(fStream,"windows-1251");
            BufferedReader reader=new BufferedReader(iReader)){
            String s;
            while((s=reader.readLine())!=null)
                arrBar.add(s);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
                
        String sPoint="@POINT\n";
        for(String point:arrPoint)
            sPoint+=point+"\n";
        sPoint+="&\n\n";
        
        String sPline="@PLINE\n";
        for(int i=0;i<arrPline.size();i++){
            String[] pline=arrPline.get(i).split(" ");
            sPline+=(i+1)+" "+pline[2]+" "+pline[3]+" "+pline[4]+"\n";
        }
        for(int i=0;i<arrBar.size();i++){
            String[] bar=arrBar.get(i).split(" ");
            sPline+=(arrPline.size()+i+1)+" "+bar[2]+" "+bar[3]+"\n";
        }
        sPline+="&\n\n";
        
        String sLineloop="@LINELOOP\n";
        for(int i=0;i<arrPline.size();i++)
            sLineloop+=(i+1)+" 1 "+(i+1)+"\n";
        sLineloop+="&\n\n";
        
        String sPsurf="@PSURF\n";
        for(int i=0;i<arrPline.size();i++)
            sPsurf+=(i+1)+" "+(i+1)+"\n";
        sPsurf+="&\n\n";
        
        ArrayList<Integer> matArr=new ArrayList<Integer>();
        String sShell="@SHELL\n";
        for(int i=0;i<arrShell.size();i++){
            String[] shell=arrShell.get(i).split(" ");
            if(!matArr.contains(Integer.parseInt(shell[1])))
                matArr.add(Integer.parseInt(shell[1]));
            sShell+=(i+1)+" 1 "+shell[1]+" 0 "+(i+1)+"\n";
        }
        sShell+="&\n\n";
        
        String sMat="@MAT\n";
        for(int i=0;i<matArr.size();i++)
            sMat+=matArr.get(i)+" "+(i+1)+"\n";
        sMat+="&\n\n";
        
        String sBar="@BAR\n";
        for(int i=0;i<arrBar.size();i++)
            sBar+=(arrPline.size()+i+1)+" 1 1 0 1 "+(arrPline.size()+i+1)+"\n";
        sBar+="&\n\n";
        
        String fileKE=sMat+sPoint+sPline+sLineloop+sPsurf+sShell+sBar;
        return fileKE;
    }
    
    //Получение списка групп элементов
    public static void getGroups(TreeMap<Integer,String> mapPoint,TreeMap<Integer,String> mapPline,
            TreeMap<Integer,String> mapLineloop,TreeMap<Integer,String> mapPsurf, TreeMap<Integer,String> mapShell,
            TreeMap<Integer,String> mapBar, ArrayList<String> arrGroup){
        int countGroup=0;
        TreeMap<Double,ArrayList<Integer>> shellGroup=new TreeMap<Double,ArrayList<Integer>>();
        TreeMap<Double,ArrayList<Integer>> barGroup=new TreeMap<Double,ArrayList<Integer>>();
        for(int keyShell:mapShell.keySet()){
            String[] shell=mapShell.get(keyShell).split(" ");
            for(int i=4;i<shell.length;i++){
                String[] psurf=mapPsurf.get(Integer.parseInt(shell[i])).split(" ");
                for(int j=1;j<2;j++){
                    String[] lineloop=mapLineloop.get(Integer.parseInt(psurf[j])).split(" ");
                    for(int k=2;k<lineloop.length;k++){
                        String[] pline=mapPline.get(Integer.parseInt(lineloop[k])).split(" ");
                        Double levelZ=null;
                        ArrayList<Integer> numberShells=new ArrayList<Integer>();
                        for(int l=1;l<pline.length;l++){
                            int index=Integer.parseInt(pline[l]);
                            String[] point=mapPoint.get(index).split(" ");
                            if(levelZ==null || levelZ>Double.parseDouble(point[3]))
                                levelZ=Double.parseDouble(point[3]);
                        }
                        if(!shellGroup.containsKey(levelZ)){
                            numberShells.add(keyShell);
                            shellGroup.put(levelZ,numberShells);
                        }
                        else{
                            numberShells=shellGroup.get(levelZ);
                            numberShells.add(keyShell);
                            shellGroup.put(levelZ,numberShells);
                        }
                    }
                }
            }
        }
        
        for(double level:shellGroup.keySet()){
            ArrayList<Integer> numberShells=shellGroup.get(level);
            StringBuilder group=new StringBuilder(++countGroup+" 11 195");
            for(int numberShell:numberShells)
                group.append(" "+numberShell);
            group.append(" ///уровень "+level);
            arrGroup.add(group.toString());
        }
        
        for(int keyBar:mapBar.keySet()){
            String[] bar=mapBarM.get(keyBar).split(" ");
            for(int i=5;i<bar.length;i++){
                String[] pline=mapPline.get(Integer.parseInt(bar[i])).split(" ");
                Double levelZ=null;
                ArrayList<Integer> numberBars=new ArrayList<Integer>();
                for(int j=1;j<pline.length;j++){
                    int index=Integer.parseInt(pline[j]);
                    String[] point=mapPoint.get(index).split(" ");
                    if(levelZ==null || levelZ>Double.parseDouble(point[3]))
                        levelZ=Double.parseDouble(point[3]);
                }
                if(!barGroup.containsKey(levelZ)){
                    numberBars.add(keyBar);
                    barGroup.put(levelZ,numberBars);
                }
                else{
                    numberBars=barGroup.get(levelZ);
                    numberBars.add(keyBar);
                    barGroup.put(levelZ,numberBars);
                }
            }
        }
        
        for(double level:barGroup.keySet()){
            ArrayList<Integer> numberBars=barGroup.get(level);
            StringBuilder group=new StringBuilder(++countGroup+" 11 200");
            for(int numberBar:numberBars)
                group.append(" "+numberBar);
            group.append(" ///уровень "+level);
            arrGroup.add(group.toString());
        }
    }
    
    //Основной метод - преобразование
    public static void Generalization(){
        //Проверка даты
//        Date data1=new Date();
//        GregorianCalendar cal=new GregorianCalendar();
//        cal.set(2019,7,1,0,0,0);
//        Date data2=cal.getTime();
//        if(!data1.before(data2))
//            System.exit(0);
        //
        
        //Обработка ключей
        double r=0.001; //Область
        int p=2; //Минимальное количество элементов
        String lPath=""; //Путь к папке с сообщениями
        //ke-setka
        String typeFile="0"; //преобразование КЕ-сетки: 0-нет; 1-да
        //
        
        //caxis Счетчик осей X, Y и Z
        int countCaxisX=0,countCaxisY=0,countCaxisZ=0;
        //
        
        //преобразователь
        if(!new File("").getAbsolutePath().contains("ModelS")){        
            pathDll=WindowsReqistry.readRegistry("HKCR\\Module-ProtoProject\\shell\\open\\command","jarPath");
            int indexDll=pathDll.lastIndexOf("\\");
            pathDll=pathDll.substring(0,indexDll)+"\\CheckKeys.dll";
            System.load(pathDll);
        }
        else{
            pathDll=new File("").getAbsolutePath()+"\\Plugins\\ModuleProto\\CheckKeys.dll";
            System.load(pathDll);
        }        
        boolean isGen=false,isCoord=false,isGroup=false;
        String argsStr=String.join(" ",Sargs);
        char[] charStr=argsStr.toCharArray();
        boolean[] keysArr=new Proto().checkKeys(new boolean[]{isGen,isCoord,isGroup},charStr,charStr.length);
        isGen=keysArr[0];
        isCoord=keysArr[1];
        isGroup=keysArr[2];
        if(!isGen && !isCoord && !isGroup){
            try(BufferedWriter writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\001-E.txt"))){
                writer.write("Необходимо указать один из следующих ключей:\n"
                            +"gen-преобразование\n"
                            +"coord-координация\n"
                            +"group-группировка\n"
                            +"Перед ключем необходимо ставить -");
            }
            catch(IOException ex){}
            try(BufferedWriter writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\_finish.txt"))){
                writer.write("Работа плагина завершена с ошибкой");
            }
            catch(IOException ex){}
            System.exit(0);
        }
        //
        
        ArrayList<Integer> x=new ArrayList<Integer>(); //Координаты x
        ArrayList<Integer> y=new ArrayList<Integer>(); //Координаты y
        ArrayList<Integer> z=new ArrayList<Integer>(); //Координаты z
        for(String s:Sargs)
            if(s.contains("-r="))r=Double.parseDouble(s.split("=")[1]);
            else if(s.contains("-p="))p=Integer.parseInt(s.split("=")[1]);
            else if(s.contains("-x="))x.add(Integer.parseInt(s.split("=")[1]));
            else if(s.contains("-y="))y.add(Integer.parseInt(s.split("=")[1]));
            else if(s.contains("-z="))z.add(Integer.parseInt(s.split("=")[1]));
            else if(s.contains("-l="))lPath=s.split("=")[1];
            //caxis Обработка ключей, связанных с осями
            else if(s.contains("-xCaxis="))countCaxisX=Integer.parseInt(s.split("=")[1]);
            else if(s.contains("-yCaxis="))countCaxisY=Integer.parseInt(s.split("=")[1]);
            else if(s.contains("-zCaxis="))countCaxisZ=Integer.parseInt(s.split("=")[1]);
            //
        //
        
                
        //Сообщение 1
        try{
            GregorianCalendar cal1=new GregorianCalendar();
            String hour1=""+cal1.get(Calendar.HOUR_OF_DAY);
            String minute1=""+cal1.get(Calendar.MINUTE);
            String second1=""+cal1.get(Calendar.SECOND);
            if(hour1.length()==1)hour1="0"+hour1;
            if(minute1.length()==1)minute1="0"+minute1;
            if(second1.length()==1)second1="0"+second1;
            BufferedWriter writer=null;
            if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\_start.txt"));
            else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\_start.txt"));
            writer.write("1: "+hour1+":"+minute1+":"+second1+" начало преобразования --------------------");
            writer.close();
        }
        catch(IOException ex){}
        
        File file=null;
        //ke-setka Получение исходного файла для КЕ-сетки
        String fileKE="",fileName="";
        if(typeFile.equals("1")){
            String name=Sargs[0]+"\\AM-00";
            file=new File(name);
            String[] list=file.list();
            for(String s:list)
                name+="\\"+s;
            file=new File(name);
            fileName=file.getName();
            
            fileKE=getDataFM01(Sargs,lPath);
            if(!fileKE.equals("exit")){
                arrBsmt=new ArrayList<String>();
                String[] ke=fileKE.split("\n");
                for(String s:ke)
                    arrBsmt.add(s);
            }
        }
        else{
            //Получение исходного файла без КЕ-сетки
            file=new File(Sargs[0]);
            String name="";
            if(file.isFile())
                name=file.getName();
            else{
                String path=file.getPath();
                name=path+"\\AM-00";
                file=new File(name);
                String[] list=file.list();
                for(String s:list)
                    name+="\\"+s;
                file=new File(name);
            }
            //

            arrBsmt=new ArrayList<String>();
            try(FileInputStream fStream=new FileInputStream(file);
                InputStreamReader iReader=new InputStreamReader(fStream,"windows-1251");
                BufferedReader reader=new BufferedReader(iReader)){
                String s="";
                while((s=reader.readLine())!=null)
                    arrBsmt.add(s);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        //Проверка на существование файла или файлов (в случае КЭ-модели)
        if(arrBsmt!=null){
            TreeMap<Integer,String> mapPoint=new TreeMap<Integer,String>();
            TreeMap<Integer,String> mapPline=new TreeMap<Integer,String>();
            ArrayList<String> arrNhsup=new ArrayList<String>();
            int j=0;
            for(int i=0;i<arrBsmt.size();i++){
                if(arrBsmt.get(i).contains("@POINT") || arrBsmt.get(i).contains("@120")){
                    for(j=i+1;j<arrBsmt.size();j++){
                        if(arrBsmt.get(j).contains("&"))break;
                        String[] sArr=arrBsmt.get(j).split(" ");
                        String point="";
                        for(int k=1;k<sArr.length;k++)
                            if(!sArr[k].equals(""))
                                point+=" "+sArr[k];
                        if(!point.equals(""))mapPoint.put(Integer.parseInt(sArr[0]),point);
                    }
                }
                if(arrBsmt.get(i).contains("@PLINE") || arrBsmt.get(i).contains("@125")){
                    for(j=i+1;j<arrBsmt.size();j++){
                        if(arrBsmt.get(j).contains("&"))break;
                        String[] sArr=arrBsmt.get(j).split(" ");
                        String pline="";
                        for(int k=1;k<sArr.length;k++)
                            if(!sArr[k].equals(""))
                                pline+=" "+sArr[k];
                        if(!pline.equals(""))mapPline.put(Integer.parseInt(sArr[0]),pline);
                    }
                    i=j;
                }            
                //nhsup
                if(arrBsmt.get(i).contains("@NHSUP") || arrBsmt.get(i).contains("@550")){
                    for(j=i+1;j<arrBsmt.size();j++){
                        if(arrBsmt.get(j).contains("&"))break;
                        String[] sArr=arrBsmt.get(j).split(" ");
                        String nhsup=sArr[0];
                        for(int k=1;k<sArr.length;k++)
                            if(!sArr[k].equals(""))
                                nhsup+=" "+sArr[k];
                        if(!nhsup.equals(""))arrNhsup.add(nhsup);
                    }
                    i=j;
                }
                //
            }
            //заполнение точек и полилиний, сортировка точек 
            mapPointM=mapPoint;
            mapPlineM=mapPline;
            sortPoints(mapPoint);
            //

            //caxis Заполнение массивов осей
            ArrayList<String> arrCaxis=new ArrayList<String>();
            if(isCoord){
                ArrayList<Double> arrCaxisX=new ArrayList<Double>();
                ArrayList<Double> arrCaxisY=new ArrayList<Double>();
                ArrayList<Double> arrCaxisZ=new ArrayList<Double>();
                if(countCaxisX>0)
                    for(int i=0;i<arrCoords.get(0).size();i++)
                        if(arrCoords.get(0).get(i)[1]>=countCaxisX)
                            arrCaxisX.add(arrCoords.get(0).get(i)[0]);
                if(countCaxisY>0)
                    for(int i=0;i<arrCoords.get(1).size();i++)
                        if(arrCoords.get(1).get(i)[1]>=countCaxisY)
                            arrCaxisY.add(arrCoords.get(1).get(i)[0]);
                if(countCaxisZ>0)
                    for(int i=0;i<arrCoords.get(2).size();i++)
                        if(arrCoords.get(2).get(i)[1]>=countCaxisZ)
                            arrCaxisZ.add(arrCoords.get(2).get(i)[0]);

                int countCaxis=0;
                countCaxisX=countCaxisY=countCaxisZ=0;
                if(arrCaxisX.size()>0)
                    for(double caxis:arrCaxisX)
                        arrCaxis.add(++countCaxis+" 11 "+caxis+" 0 0 0 0 0 0 \"ось Y #"+(++countCaxisX)+"\"");
                if(arrCaxisY.size()>0)
                    for(double caxis:arrCaxisY)
                        arrCaxis.add(++countCaxis+" 12 0 "+caxis+" 0 0 0 0 0 \"ось X #"+(++countCaxisY)+"\"");
                if(arrCaxisZ.size()>0)
                    for(double caxis:arrCaxisZ)
                        arrCaxis.add(++countCaxis+" 13 0 0 "+caxis+" 0 0 0 0 \"уровень #"+(++countCaxisZ)+"\"");
            }
            //
            
            //storey Заполнение массива этажей
            int countZone=0,countStorey=0;
            ArrayList<String> arrZone=new ArrayList<String>();
            ArrayList<String> arrStorey=new ArrayList<String>();
            for(int i=0;i<arrLevels.size()-1;i++){
                double minZ=arrLevels.get(i);
                double maxZ=arrLevels.get(i+1);
                double[] xy1=mapLevels.get(minZ);
                double[] xy2=mapLevels.get(maxZ);
                double minX=xy1[0],maxX=xy1[1],minY=xy1[2],maxY=xy1[3];
                if(minX>xy2[0])minX=xy2[0];
                if(maxX<xy2[1])maxX=xy2[1];
                if(minY>xy2[2])minY=xy2[2];
                if(maxY<xy2[3])maxY=xy2[3];
                arrZone.add(++countZone+" 22 "+minX+" "+maxX+" "+minY+" "+maxY+" "+minZ+" "+maxZ);
                arrStorey.add(++countStorey+" "+countZone);
            }
            //
            
            //Формирование списка координат для обработки
            String textCoords="";
            if(x.size()>0 || y.size()>0 || z.size()>0){
                for(ArrayList<double[]> arrCoord:arrCoords)
                    for(double[] coord:arrCoord)
                        if(coord[1]>=p)
                            if(arrCoords.indexOf(arrCoord)==0)
                                for(int n:x){
                                    double lValue=n-r;
                                    double rValue=n+r;
                                    if(coord[0]>=lValue && coord[0]<=rValue){
                                        textCoords+="X "+coord[0]+" ";
                                        break;
                                    }
                                }
                            else if(arrCoords.indexOf(arrCoord)==1)
                                for(int n:y){
                                    double lValue=n-r;
                                    double rValue=n+r;
                                    if(coord[0]>=lValue && coord[0]<=rValue){
                                        textCoords+="Y "+coord[0]+" ";
                                        break;
                                    }
                                }
                            else if(arrCoords.indexOf(arrCoord)==2)
                                for(int n:z){
                                    double lValue=n-r;
                                    double rValue=n+r;
                                    if(coord[0]>=lValue && coord[0]<=rValue){
                                        textCoords+="Z "+coord[0]+" ";
                                        break;
                                    }
                                }         
            }
            else
                for(ArrayList<double[]> arrCoord:arrCoords)
                    for(double[] coord:arrCoord)
                        if(coord[1]>=p)
                            if(arrCoords.indexOf(arrCoord)==0)
                                textCoords+="X "+coord[0]+" ";
                            else if(arrCoords.indexOf(arrCoord)==1)
                                textCoords+="Y "+coord[0]+" ";
                            else if(arrCoords.indexOf(arrCoord)==2)
                                textCoords+="Z "+coord[0]+" ";
            //

            String[] coordsAll=textCoords.split(" ");
            String[][] coords=new String[coordsAll.length/2][2];
            for(int i=0;i<coords.length;i++){
                coords[i][0]=coordsAll[i*2];
                coords[i][1]=coordsAll[i*2+1];
            }

            //Заполнение списка координат по которым будет происходить фильтрация
            ArrayList<vtkPolyData> arrPolyData;
            String[] args=new String[coords.length+2];
            args[0]=Sargs[0];
            args[1]="0";
            for(int i=0;i<coords.length;i++)
                args[i+2]="-"+coords[i][0].toUpperCase()+"="+coords[i][1];
            //получение массива фигур и выделение внешних контуров для этих фигур
            arrPolyData=stage3(args,isGen);
            if(isGen)boundaryEdgesArr(arrPolyData);
            else polyDataContours=arrPolyData;
            //

            int typeCoord=0;
            
            //file Получение фигур            
            vtkPolyData[] polyDataArr=new vtkPolyData[polyDataContours.size()];
            for(int i=0;i<polyDataContours.size();i++)
                polyDataArr[i]=polyDataContours.get(i);
            //

            //nload
            ArrayList<String> arrNload=new ArrayList<String>();
            ArrayList<Integer> nloadNumbers=new ArrayList<Integer>();
            ArrayList<String> pointNload=new ArrayList<String>();
            //

            vtkPolyData linesData=new vtkPolyData();            
            for(int i=0;i<coords.length;i++){
                if(coords[i][0].equals("X"))
                    typeCoord=1;
                else if(coords[i][0].equals("Y"))
                    typeCoord=2;
                else if(coords[i][0].equals("Z"))
                    typeCoord=3;
                double coord=Double.parseDouble(coords[i][1]);

                //nload
                for(j=0;j<arrBsmt.size();j++)
                    if(arrBsmt.get(j).contains("@NLOAD") || arrBsmt.get(j).contains("@420"))
                        for(int k=j+1;k<arrBsmt.size();k++){
                            if(arrBsmt.get(k).contains("&"))break;
                            String[] sArr=arrBsmt.get(k).split(" ");

                            int pointNumber=0;
                            for(int l:mapPoint.keySet()){
                                String[] pointArr=mapPoint.get(l).split(" ");
                                if((double)l==Double.parseDouble(sArr[2])){
                                    pointNumber=l;
                                    break;
                                }
                            }

                            String[] point=mapPointM.get(pointNumber).split(" ");
                            if(point[typeCoord].equals(""+coord)){
                                String nload=sArr[0];
                                for(int l=1;l<sArr.length;l++)
                                    nload+=" "+sArr[l];
                                arrNload.add(nload);
                                pointNload.add(sArr[0]+" "+point[1]+" "+point[2]+" "+point[3]+" "+i);
                            }
                        }
                //

                vtkPoints pointsLines=new vtkPoints();
                vtkCellArray polygonsLines=new vtkCellArray();
                for(int k:mapPlineM.keySet()){
                    String[] figure1s=mapPlineM.get(k).split(" ");
                    int[] figure1=new int[figure1s.length-1];
                    for(int l=1;l<figure1s.length;l++)
                        figure1[l-1]=Integer.parseInt(figure1s[l]);

                    vtkPolygon polygon=new vtkPolygon();
                    polygon.GetPointIds().SetNumberOfIds(figure1.length);
                    for(int l=0;l<figure1.length;l++)
                        if(figure1.length==2){
                            int pointNumber=0;
                            for(int m:mapPoint.keySet()){
                                String[] pointArr=mapPoint.get(m).split(" ");
                                if((""+m).equals(""+figure1[l])){
                                    pointNumber=m;
                                    break;
                                }
                            }
                            String[] point=mapPointM.get(pointNumber).split(" ");
                            double X=Double.parseDouble(point[1]);
                            double Y=Double.parseDouble(point[2]);
                            double Z=Double.parseDouble(point[3]);
                            pointsLines.InsertNextPoint(X,Y,Z);
                            polygon.GetPointIds().SetId(l,pointsLines.GetNumberOfPoints()-1);
                        }
                    if(figure1.length==2)polygonsLines.InsertNextCell(polygon);
                }
                vtkPolyData pointData=new vtkPolyData();
                
                //lines
                if(i==0){                    
                    pointData.SetPoints(pointsLines);
                    pointData.SetLines(polygonsLines);
                    linesData=pointData;
                }
                //
            }

            //Сообщение 2
            try{
                GregorianCalendar cal2=new GregorianCalendar();
                String hour2=""+cal2.get(Calendar.HOUR_OF_DAY);
                String minute2=""+cal2.get(Calendar.MINUTE);
                String second2=""+cal2.get(Calendar.SECOND);
                if(hour2.length()==1)hour2="0"+hour2;
                if(minute2.length()==1)minute2="0"+minute2;
                if(second2.length()==1)second2="0"+second2;
                BufferedWriter writer=null;
                if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\001-M.txt"));
                else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\001-M.txt"));
                writer.write("2: "+hour2+":"+minute2+":"+second2+" окончание преобразования");
                writer.close();
            }
            catch(IOException ex){}
            //

            //Подготовка данных для записи в файл
            ArrayList<Integer>pointsArr=new ArrayList<Integer>();
            pointsArr.add(0);
            int countPoint=0,countPline=0,countLineloop=0,countPsurf=0,countShell=0;
            int longCount=0;
            StringBuilder sPoint=new StringBuilder("@POINT\n");
            StringBuilder sPline=new StringBuilder("@PLINE\n");
            StringBuilder sLineloop=new StringBuilder("@LINELOOP\n");
            StringBuilder sPsurf=new StringBuilder("@PSURF\n");
            StringBuilder sShell=new StringBuilder("@SHELL\n");
            int plineCount=0;
            if(isGen)
                for(int i=0;i<polyDataArr.length;i++){
                    if(polyDataArr[i]==null)break;
                    vtkPoints points=polyDataArr[i].GetPoints();
                    //upr Массив упрощенных преобразованных полилиний
                    HashMap<Integer,String> pointsMap=new HashMap<Integer,String>();
                    //
                    for(j=0;j<points.GetNumberOfPoints();j++){                    
                        sPoint.append((++countPoint)+" "+points.GetPoint(j)[0]+" "+points.GetPoint(j)[1]+" "
                                +points.GetPoint(j)[2]+"\n");
                        //upr Заполнение преобразованных полилиний
                        pointsMap.put(countPoint,points.GetPoint(j)[0]+" "+points.GetPoint(j)[1]+" "
                                +points.GetPoint(j)[2]);
                        //
                    }
                    pointsArr.add(countPoint);

                    String longPline="";
                    for(j=0;j<polyDataArr[i].GetNumberOfCells();j++){
                        longPline+=(++countPline)+" "+(polyDataArr[i].GetCell(j).GetPointId(0)+1+pointsArr.get(i))
                                +" "+(polyDataArr[i].GetCell(j).GetPointId(1)+1+pointsArr.get(i))+"\n";
                    }

                    //plines Нахождение полилиний, связанных общим контуром
                    String longStr="";
                    String shortStr="";
                    String beginStr="";
                    int beginNumber=0;
                    int lastPoint=0;
                    boolean isFirst=true;
                    String[] plines=longPline.split("\n");
                    for(j=beginNumber;;){
                        String[] pline1=plines[j].split(" ");
                        boolean findPoint=false;                
                        if(isFirst){
                            longStr+=(++longCount)+" "+pline1[1]+" "+pline1[2];
                            lastPoint=Integer.parseInt(pline1[2]);
                            beginStr=pline1[1];
                        }
                        for(int k=j+1;k<plines.length;k++){
                            String[] pline2=plines[k].split(" ");
                            if(pline1[2].equals(pline2[1])){
                                isFirst=false;
                                if(pline2[2].equals(beginStr)){
                                    j=beginNumber;
                                    break;
                                }
                                longStr+=" "+pline2[2];
                                if(Integer.parseInt(pline2[2])>lastPoint)
                                    lastPoint=Integer.parseInt(pline2[2]);
                                j=k;
                                findPoint=true;
                                break;
                            }
                        }
                        if(!findPoint)
                            for(int k=j-1;k>=beginNumber;k--){
                                String[] pline2=plines[k].split(" ");
                                if(pline1[2].equals(pline2[1])){
                                    isFirst=false;
                                    if(pline2[2].equals(beginStr)){
                                        j=beginNumber;
                                        break;
                                    }
                                    longStr+=" "+pline2[2];
                                    j=k;
                                    findPoint=true;
                                    break;
                                }
                            }
                        if(j==beginNumber && !isFirst)
                            if((longStr.split(" ").length-1)==plines.length){                        
                                longStr+="\n";
                                beginNumber=lastPoint+1;
                                break;
                            }
                            else
                                for(int k=beginNumber+1;k<plines.length;k++){
                                    String[] pline2=plines[k].split(" ");
                                    boolean isPline=false;
                                    String[] longStrArr=longStr.split(" ");
                                    for(String s:longStrArr)
                                        if(s.equals(pline2[1]) || s.equals(pline2[2])){
                                            isPline=true;
                                            break;
                                        }
                                    if(!isPline){
                                        longStr+="\n";
                                        j=k;
                                        beginNumber=k;
                                        isFirst=true;
                                        break;
                                    }
                                }
                        else if(!findPoint){                    
                            longStr+=" null";
                            boolean isPline=false;
                            for(int k=beginNumber+1;k<plines.length;k++){
                                String[] pline2=plines[k].split(" ");
                                isPline=false;
                                String[] longStrArr=longStr.split(" ");
                                for(String s:longStrArr)
                                    if(s.equals(pline2[1]) || s.equals(pline2[2])){
                                        isPline=true;
                                        break;
                                    }
                                if(!isPline){
                                    longStr+="\n";
                                    j=k;
                                    beginNumber=k;
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

                    if(!longStr.isEmpty()){ 
                        //Упрощение преобразованной полилинии
                        String[] strArr=longStr.split("\n");
                        for(String s:strArr){
                            String[] str=s.split(" ");
                            String[] oldPoint=null;
                            String[] nextPoint=null;
                            shortStr="";
                            int oldTypeCoord=0;
                            for(int k=1;k<str.length;k++){
                                String[] point=pointsMap.get(Integer.parseInt(str[k])).split(" ");
                                if(k==1)shortStr+=str[k];
                                else{
                                    String raznostPoints="";
                                    oldPoint=pointsMap.get(Integer.parseInt(str[k-1])).split(" ");
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
                                    if(k!=str.length-1)nextPoint=pointsMap.get(Integer.parseInt(str[k+1])).split(" ");                            
                                    else nextPoint=pointsMap.get(Integer.parseInt(str[1])).split(" ");

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
                                                shortStr+=" "+str[k];
                                                continue;
                                            }
                                            else if(oldPoint[0].equals(nextPoint[0]) && !oldPoint[1].equals(nextPoint[1]) && oldPoint[2].equals(nextPoint[2])){
                                                shortStr+=" "+str[k];
                                                continue;
                                            }
                                            else if(oldPoint[0].equals(nextPoint[0]) && oldPoint[1].equals(nextPoint[1]) && !oldPoint[2].equals(nextPoint[2])){
                                                shortStr+=" "+str[k];
                                                continue;
                                            }
                                            else continue;
                                        }
                                        else{
                                            shortStr+=" "+str[k];
                                            continue;
                                        }

                                    if(oldTypeCoord!=typeCoord)shortStr+=" "+str[k];
                                }
                            }
                            shortStr=(++plineCount)+" "+shortStr+"\n";
                            sPline.append(shortStr);
                        }
                        //

                        for(j=0;j<longStr.split("\n").length;j++)
                            sLineloop.append((++countLineloop)+" 1 "+countLineloop+"\n");

                        //Проверка на отверстия внутри оболочек
                        vtkPolyData[] polyArr=new vtkPolyData[longStr.split("\n").length];
                        int countArr=0;
                        for(String s:longStr.split("\n")){
                            String[] arr=s.split(" ");
                            vtkCellArray polygons = new vtkCellArray();
                            vtkPolygon polygon=new vtkPolygon();
                            polygon.GetPointIds().SetNumberOfIds(arr.length-1);
                            for(j=1;j<arr.length;j++){
                                int numberPoint=Integer.parseInt(arr[j]);
                                polygon.GetPointIds().SetId(j-1,numberPoint-pointsArr.get(i)-1);
                            }
                            polygons.InsertNextCell(polygon);
                            polyArr[countArr]=new vtkPolyData();
                            polyArr[countArr].SetPoints(points);
                            polyArr[countArr].SetPolys(polygons);
                            countArr++;                    
                        }
                        String psurf="";
                        if(polyArr.length>1)
                            for(j=0;j<polyArr.length;j++){
                                if(polyArr[j]==null)continue;
                                psurf+=(++countPsurf)+" "+countPsurf;
                                for(int k=j+1;k<polyArr.length;k++){
                                    if(polyArr[k]==null)continue;
                                    boolean isInside=true;
                                    vtkSelectEnclosedPoints sel=new vtkSelectEnclosedPoints();
                                    sel.SetSurfaceData(polyArr[j]);
                                    sel.SetInputData(polyArr[k]);
                                    sel.Update();
                                    for(int l=0;l<polyArr[k].GetNumberOfPoints();l++)
                                        if(sel.IsInside(l)==0){
                                            isInside=false;
                                            break;
                                        }
                                    if(!isInside)continue;
                                    else{
                                        psurf+=" "+(countPsurf+k);
                                        polyArr[k]=null;
                                    }
                                }
                                psurf+="\n";
                            }
                        else psurf+=(++countPsurf)+" "+countPsurf+"\n";
                        //

                        sPsurf.append(psurf);

                        for(j=0;j<psurf.split("\n").length;j++)
                            sShell.append((++countShell)+" 1 "+(i+1)+" 0 "+countShell+"\n");
                    }
                }
            else{
                for(int number:mapPointM.keySet())
                    sPoint.append(number+mapPointM.get(number)+"\n");
                for(int number:mapPlineM.keySet())
                    sPline.append(number+mapPlineM.get(number)+"\n");
                for(int number:mapLineloopM.keySet())
                    sLineloop.append(number+mapLineloopM.get(number)+"\n");
                for(int number:mapPsurfM.keySet())
                    sPsurf.append(number+mapPsurfM.get(number)+"\n");
                for(int number:mapShellM.keySet())
                    sShell.append(number+mapShellM.get(number)+"\n");
            }

            //nload                        
            double min=0;
            ArrayList<String> arrNloadNew=new ArrayList<String>();
            ArrayList<Integer> lcasename=new ArrayList<Integer>();
            ArrayList<String> numberNload=new ArrayList<String>();
            //for(int i=0;i<pointNload.size();i++){
            for(String nload:pointNload){
                String point="";
                double[] points1=new double[3];
                points1[0]=Double.parseDouble(nload.split(" ")[1]);
                points1[1]=Double.parseDouble(nload.split(" ")[2]);
                points1[2]=Double.parseDouble(nload.split(" ")[3]);
                int numberCoord=Integer.parseInt(nload.split(" ")[4]);
                for(j=0;j<polyDataArr.length;j++){
                    if(numberCoord!=j)continue;
                    vtkPoints points=polyDataArr[j].GetPoints();
                    for(int k=0;k<points.GetNumberOfPoints();k++){
                        if((points.GetPoint(k)[0]-points1[0])<=-0.1 || (points.GetPoint(k)[0]-points1[0])>=0.1)
                            if(k==0){
                                min=Math.abs(points.GetPoint(k)[0]-points1[0]);
                                point=points.GetPoint(k)[0]+" "+points.GetPoint(k)[1]+" "+points.GetPoint(k)[2];
                            }
                            else if(Math.abs(points.GetPoint(k)[0]-points1[0])<=min){
                                if(Math.abs(points.GetPoint(k)[0]-points1[0])==min){
                                    if(arrNloadNew.contains(point))
                                        point=points.GetPoint(k)[0]+" "+points.GetPoint(k)[1]+" "+points.GetPoint(k)[2];
                                }
                                else{
                                    min=Math.abs(points.GetPoint(k)[0]-points1[0]);
                                    point=points.GetPoint(k)[0]+" "+points.GetPoint(k)[1]+" "+points.GetPoint(k)[2];
                                }
                            }
                        if((points.GetPoint(k)[1]-points1[1])<=-0.1 || (points.GetPoint(k)[1]-points1[1])>=0.1)
                            if(Math.abs(points.GetPoint(j)[1]-points1[1])<=min){
                                if(Math.abs(points.GetPoint(k)[1]-points1[1])==min){
                                    if(arrNloadNew.contains(point))
                                        point=points.GetPoint(k)[0]+" "+points.GetPoint(k)[1]+" "+points.GetPoint(k)[2];
                                }
                                else{
                                    min=Math.abs(points.GetPoint(k)[1]-points1[1]);
                                    point=points.GetPoint(k)[0]+" "+points.GetPoint(k)[1]+" "+points.GetPoint(k)[2];
                                }
                            }
                        if((points.GetPoint(k)[2]-points1[2])<=-0.1 || (points.GetPoint(k)[2]-points1[2])>=0.1)
                            if(Math.abs(points.GetPoint(k)[2]-points1[2])<=min){
                                if(Math.abs(points.GetPoint(k)[2]-points1[2])==min){
                                    if(arrNloadNew.contains(point))
                                        point=points.GetPoint(k)[0]+" "+points.GetPoint(k)[1]+" "+points.GetPoint(k)[2];
                                }
                                else{
                                    min=Math.abs(points.GetPoint(k)[2]-points1[2]);
                                    point=points.GetPoint(k)[0]+" "+points.GetPoint(k)[1]+" "+points.GetPoint(k)[2];
                                }
                            }
                    }
                }
                if(!arrNloadNew.contains(point) && !point.equals("")){
                    arrNloadNew.add(point);
                    numberNload.add(nload.split(" ")[0]);
                }
            }

            for(int i=0;i<arrNloadNew.size();i++)
                arrNloadNew.set(i,numberNload.get(i)+" "+arrNloadNew.get(i));

            for(int i=0;i<arrNload.size();i++){
                boolean isNload=false;
                for(String nload:arrNloadNew){
                    String nload1=arrNload.get(i).split(" ")[0];
                    String nload2=nload.split(" ")[0];
                    if(nload1.equals(nload2)){
                        isNload=true;
                        break;
                    }
                }
                if(!isNload){
                    arrNload.remove(i);
                    i--;
                    continue;
                }
            }

            for(int i=0;i<arrNload.size();i++)
                for(j=i+1;j<arrNload.size();j++)
                    if(arrNload.get(i).equals(arrNload.get(j))){
                        arrNload.remove(j);
                        j--;
                    }

            for(int i=0;i<arrNload.size();i++){
                String[] nload=arrNload.get(i).split(" ");

                //
                int numberPoint=0;
                String[] point=sPoint.toString().split("\n");
                for(j=0;j<point.length;j++){
                    String nloadnewPoint=arrNloadNew.get(i).split(" ")[1]+" "
                            +arrNloadNew.get(i).split(" ")[2]+" "+arrNloadNew.get(i).split(" ")[3];
                    if(point[j].contains(nloadnewPoint)){
                        numberPoint=j;
                        nload[2]=""+numberPoint;
                        break;
                    }
                }
                //

                String nloadNew=nload[0];
                for(j=1;j<nload.length;j++)
                    nloadNew+=" "+nload[j];
                arrNload.set(i,nloadNew);
                if(!lcasename.contains(Integer.parseInt(nload[1])))
                    lcasename.add(Integer.parseInt(nload[1]));
            }
            //end nload

            String sLscale="///Масштаб отображения нагрузок (1)\n";
            sLscale+="@LSCALE\n20000 1\n&\n\n";

            String sLcasename="";
            if(lcasename.size()>0){
                sLcasename="///Отображения и названия загружений ("+lcasename.size()+")\n";
                sLcasename+="@LCASENAME\n";
                for(int i:lcasename)
                    sLcasename+=i+" 0\n";
                sLcasename+="&\n\n";
            }

            String sNload="///Узловые нагрузки ("+arrNload.size()+")\n";
            sNload+="@NLOAD\n";
            for(String s:arrNload)
                sNload+=s+"\n";
            sNload=sLscale+sLcasename+sNload+"&\n\n";
            //

            //bar Формирование списка стержней для файла
            StringBuilder sBar=new StringBuilder("@BAR\n");
            if(isGen){
                int countBar=0;
                for(int i=0;i<linesData.GetNumberOfPoints();i++)
                    sPoint.append((++countPoint)+" "+linesData.GetPoint(i)[0]+" "+linesData.GetPoint(i)[1]+" "
                            +linesData.GetPoint(i)[2]+"\n");     
                for(int i=0;i<linesData.GetNumberOfCells();i++){
                    sPline.append((++countPline)+" "+(linesData.GetCell(i).GetPointId(0)+1+pointsArr.get(pointsArr.size()-1))
                            +" "+(linesData.GetCell(i).GetPointId(1)+1+pointsArr.get(pointsArr.size()-1))+"\n");
                    sBar.append((++countBar)+" 1 "+(polyDataArr.length+1)+" 0 "+countPline+" "+countPline+"\n");
                }
            }
            else
                for(int number:mapBarM.keySet())
                    sBar.append(number+mapBarM.get(number)+"\n");
            sBar=new StringBuilder("///Стержни ("+(sBar.toString().split("\n").length-1)+")\n"+sBar+"&\n\n");
            //

            //nhsup Формирование списка опор для файла
            int countNhsup=0;
            StringBuilder sNhsup=new StringBuilder();
            if(arrNhsup.size()>0){
                for(String s:arrNhsup){
                    String[] nhsup=s.split(" ");
                    double[] coordsPoint=new double[3];
                    String point1=mapPoint.get(Integer.parseInt(nhsup[1]));
                    coordsPoint[0]=Double.parseDouble(point1.split(" ")[1]);
                    coordsPoint[1]=Double.parseDouble(point1.split(" ")[2]);
                    coordsPoint[2]=Double.parseDouble(point1.split(" ")[3]);
                    String[] points=sPoint.toString().split("\n");
                    for(int i=1;i<points.length;i++){
                        String[] pointStr=points[i].split(" ");
                        double[] point={Double.parseDouble(pointStr[1]),Double.parseDouble(pointStr[2])
                                        ,Double.parseDouble(pointStr[3])};
                        if(Math.abs(point[0]-coordsPoint[0])<0.1 && Math.abs(point[1]-coordsPoint[1])<0.1
                                && Math.abs(point[2]-coordsPoint[2])<0.1){
                            sNhsup.append((countNhsup++)+" "+pointStr[0]+" "+nhsup[2]+" "+nhsup[3]
                                    +" "+nhsup[4]+" "+nhsup[5]+" "+nhsup[6]+" "+nhsup[7]+"\n");
                            break;
                        }
                    }
                }
                sNhsup=new StringBuilder("///Узловые опоры ("+countNhsup+")\n@NHSUP\n"+sNhsup+"&\n\n");
            }
            //

            //caxis Формирование списка осей для файла
            StringBuilder sCaxis=new StringBuilder("///Координационные оси и уровни ("+arrCaxis.size()+")\n@CAXIS\n");
            for(String caxis:arrCaxis)
                sCaxis.append(caxis+"\n");
            sCaxis.append("&\n\n");
            //
            
            //storey Формирование списка этажей для файла
            StringBuilder sZone=new StringBuilder("///Зоны ("+arrZone.size()+")\n@ZONE\n");
            for(String zone:arrZone)
                sZone.append(zone+"\n");
            sZone.append("&\n\n");

            StringBuilder sStorey=new StringBuilder("///Этажи ("+arrStorey.size()+")\n@STOREY\n");
            for(String storey:arrStorey)
                sStorey.append(storey+"\n");
            sStorey.append("&\n\n");
            //
            
            //group заполнение групп преобразованного объекта
            ArrayList<String> arrGroup=new ArrayList<String>();
            if(isGroup){
                if(isGen){
                    mapPointM.clear();
                    mapPlineM.clear();
                    String[] pointArr=sPoint.toString().replace("@POINT\n","").split("\n");
                    for(String s:pointArr){
                        String[] point=s.split(" ");
                        mapPointM.put(Integer.parseInt(point[0])," "+point[1]+" "+point[2]+" "+point[3]);
                    }

                    String[] plineArr=sPline.toString().replace("@PLINE\n","").split("\n");
                    for(String s:plineArr){
                        String[] pline=s.split(" ");
                        s="";
                        for(int i=1;i<pline.length;i++)
                            s+=" "+pline[i];
                        mapPlineM.put(Integer.parseInt(pline[0]),s);
                    }

                    String[] lineloopArr=sLineloop.toString().replace("@LINELOOP\n","").split("\n");
                    for(String s:lineloopArr){
                        String[] lineloop=s.split(" ");
                        s="";
                        for(int i=1;i<lineloop.length;i++)
                            s+=" "+lineloop[i];
                        mapLineloopM.put(Integer.parseInt(lineloop[0]),s);
                    }

                    String[] psurfArr=sPsurf.toString().replace("@PSURF\n","").split("\n");
                    for(String s:psurfArr){
                        String[] psurf=s.split(" ");
                        s="";
                        for(int i=1;i<psurf.length;i++)
                            s+=" "+psurf[i];
                        mapPsurfM.put(Integer.parseInt(psurf[0]),s);
                    }

                    String[] shellArr=sShell.toString().replace("@SHELL\n","").split("\n");
                    for(String s:shellArr){
                        String[] shell=s.split(" ");
                        s="";
                        for(int i=1;i<shell.length;i++)
                            s+=" "+shell[i];
                        mapShellM.put(Integer.parseInt(shell[0]),s);
                    }

                    String[] barArr=sBar.toString().split("\n");
                    for(int i=2;i<barArr.length-1;i++){
                        String[] bar=barArr[i].split(" ");
                        String s="";
                        for(j=1;j<bar.length;j++)
                            s+=" "+bar[j];
                        mapBarM.put(Integer.parseInt(bar[0]),s);
                    }
                }
                getGroups(mapPointM,mapPlineM,mapLineloopM,mapPsurfM,mapShellM,mapBarM,arrGroup);
            }
            
            StringBuilder sGroup=new StringBuilder("///Группы ("+arrGroup.size()+")\n@GROUP\n");
            for(String group:arrGroup)
                sGroup.append(group+"\n");
            sGroup.append("&\n\n");
            //
            
            sPoint=new StringBuilder("///Точки ("+(sPoint.toString().split("\n").length-1)+")\n"+sPoint+"&\n\n");
            sPline=new StringBuilder("///Полилинии ("+(sPline.toString().split("\n").length-1)+")\n"+sPline+"&\n\n");
            sLineloop=new StringBuilder("///Замкнутые линии ("+(sLineloop.toString().split("\n").length-1)+")\n"+sLineloop+"&\n\n");
            sPsurf=new StringBuilder("///Плоские элементы поверхностей ("+(sPsurf.toString().split("\n").length-1)+")\n"+sPsurf+"&\n\n");
            sShell=new StringBuilder("///Оболочки ("+(sShell.toString().split("\n").length-1)+")\n"+sShell+"&\n\n");
            //Непосредственная запись в файл
            String fileS="";
            if(Sargs[1].charAt(0)!='-'){
                File folder=new File(Sargs[1]+"\\AM-00");
                folder.mkdirs();
                if(!typeFile.equals("1"))fileS=folder.getPath()+"\\"+file.getName();
                else fileS=folder.getPath()+"\\"+fileName;
                file=new File(fileS);
            }
            else{
                fileS=Sargs[0];
                int indexPoint=file.getName().lastIndexOf(".");
                String filename=file.getName().substring(0,indexPoint)+"_(fe).bsmt";
                File fileFe=new File(file.getParent()+"\\"+filename);
                try(FileOutputStream fStream=new FileOutputStream(fileFe);
                    OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"windows-1251");
                    BufferedWriter writer=new BufferedWriter(oWriter)){
                    for(String s:arrBsmt){
                        writer.write(s);
                        writer.newLine();
                    }
                }
                catch(IOException ex){}
            }

            //Начало bsmt файла
            String sBegin="/// --------------------------------------------\n";
            sBegin+="/// Преобразование файла BSMT.\n";
            sBegin+="/// Исходный файл: "+Sargs[0]+".\n";
            sBegin+="/// Выходной файл: "+fileS+".\n";

            String sMstyle="///Стили отображения материалов ("+(polyDataArr.length+1)+")\n@MSTYLE\n";
            String sMat="@MAT\n";
            if(isGen)
                for(int i=0;i<polyDataArr.length+1;i++){
                    sMstyle+=i+" "+i+" "+i+" "+i+" "+i+"\n";
                    sMat+=(i+1)+" "+i+"\n";
                }
            else
                for(int number:mapMatM.keySet())
                    sMat+=number+mapMatM.get(number)+"\n";
            sMstyle+="&\n\n";

            String sElsec="";
            for(int i=0;i<arrBsmt.size();i++){
                String line=arrBsmt.get(i);
                if(line.contains("@ELSEC")){
                    sElsec+=line+"\n";
                    for(j=i+1;j<arrBsmt.size();j++){
                        line=arrBsmt.get(j);
                        sElsec+=line+"\n";
                        if(line.contains("&"))
                            break;
                    }
                    break;
                }
            }
            sMat+="&\n\n";
            sElsec+="\n";

            //Сообщение 3
            try{
                GregorianCalendar cal3=new GregorianCalendar();
                String hour3=""+cal3.get(Calendar.HOUR_OF_DAY);
                String minute3=""+cal3.get(Calendar.MINUTE);
                String second3=""+cal3.get(Calendar.SECOND);
                if(hour3.length()==1)hour3="0"+hour3;
                if(minute3.length()==1)minute3="0"+minute3;
                if(second3.length()==1)second3="0"+second3;
                //
                String day=""+cal3.get(Calendar.DAY_OF_MONTH);
                if(day.length()==1)day="0"+day;
                String month=""+(cal3.get(Calendar.MONTH)+1);
                if(month.length()==1)month="0"+month;
                String year=""+cal3.get(Calendar.YEAR);
                sBegin+="/// Дата создания файла: "+day+"."+month+"."+year+" "+hour3
                        +":"+minute3+":"+second3+".\n";
                sBegin+="///---------------------------------------------\n\n";
                //
                BufferedWriter writer=null;
                if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\002-M.txt"));
                else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\002-M.txt"));
                writer.write("3: "+hour3+":"+minute3+":"+second3+" запись в файл");
                writer.close();
            }
            catch(IOException ex){}
            //
            
            //ebody,sbody
            StringBuilder sEbody=new StringBuilder("///Элементарные тела ("+mapEbodyM.size()+")\n@EBODY\n"),
                          sSbody=new StringBuilder("/// Тела ("+mapSbodyM.size()+")\n@SBODY\n");
            if(!isGen && !mapEbodyM.isEmpty() && mapSbodyM.isEmpty()){
                for(int number:mapEbodyM.keySet())
                    sEbody.append(number+mapEbodyM.get(number)+"\n");
                for(int number:mapSbodyM.keySet())
                    sSbody.append(number+mapSbodyM.get(number)+"\n");
            }
            //
            
            //Подсчет количества объектов
            if(isGen)sBegin+="/// BRUSH=9 PEN=9 MSTYLE="+(polyDataArr.length+1)+" MAT="+(polyDataArr.length+1);
            else sBegin+="/// MAT="+mapMatM.size();
            
            if((sElsec.split("\n").length-2)>0)sBegin+=" ELSEC="+(sElsec.split("\n").length-2);
            else sElsec="";

            if((sPoint.toString().split("\n").length-3)>0)sBegin+=" POINT="+(sPoint.toString().split("\n").length-3);
            else sPoint=new StringBuilder();

            if((sPline.toString().split("\n").length-3)>0)sBegin+=" PLINE="+(sPline.toString().split("\n").length-3);
            else sPline=new StringBuilder();

            if((sLineloop.toString().split("\n").length-3)>0)sBegin+=" LINELOOP="+(sLineloop.toString().split("\n").length-3);
            else sLineloop=new StringBuilder();

            if((sPsurf.toString().split("\n").length-3)>0)sBegin+=" PSURF="+(sPsurf.toString().split("\n").length-3);
            else sPsurf=new StringBuilder();

            if((sShell.toString().split("\n").length-3)>0)sBegin+=" SHELL="+(sShell.toString().split("\n").length-3);
            else sShell=new StringBuilder();

            if((sBar.toString().split("\n").length-3)>0)sBegin+=" BAR="+(sBar.toString().split("\n").length-3);
            else sBar=new StringBuilder();

            //
            if((sEbody.toString().split("\n").length-3)>0)sBegin+=" EBODY="+(sEbody.toString().split("\n").length-3);
            else sEbody=new StringBuilder();
            
            if((sSbody.toString().split("\n").length-3)>0)sBegin+=" SBODY="+(sSbody.toString().split("\n").length-3);
            else sSbody=new StringBuilder();
            //
            
            if(countNhsup>0)sBegin+=" NHSUP="+countNhsup;
            else sNhsup=new StringBuilder();
            //caxis
            if(arrCaxis.size()>0)sBegin+=" CAXIS="+arrCaxis.size();
            else sCaxis=new StringBuilder();
            //storey
            if(arrZone.size()>0)sBegin+=" ZONE="+arrZone.size();
            else sZone=new StringBuilder();

            if(arrStorey.size()>0)sBegin+=" STOREY="+arrStorey.size();
            else sStorey=new StringBuilder();
            //group
            if(arrGroup.size()>0)sBegin+=" GROUP="+arrGroup.size();
            else sGroup=new StringBuilder();
            //
            
            if(isGen)sMat="///Материалы ("+(polyDataArr.length+1)+")\n"+sMat;
            else sMat="///Материалы ("+mapMatM.size()+")\n"+sMat;
            
            if(!sElsec.isEmpty())sElsec="///Поперечные сечения элемента ("+(sElsec.split("\n").length-2)+")\n"+sElsec;
            
            if(isGen){
                String sBrush="///Кисти (9)\n";
                sBrush+="@BRUSH\n";
                sBrush+="0 1 255 0 0 255 0 ///Красный\n";
                sBrush+="1 1 0 255 0 255 ///Зеленый\n";
                sBrush+="2 1 0 0 255 255 1 ///Синий\n";
                sBrush+="3 1 255 51 102 255 0 ///Розовый\n";
                sBrush+="4 1 255 255 0 255 ///Ядовито желтый\n";
                sBrush+="5 1 51 153 255 255 1 ///Небесно голубой\n";
                sBrush+="6 1 204 51 255 255 1 ///Сирень\n";
                sBrush+="7 1 204 102 0 255 1 ///Оранжевый\n";
                sBrush+="8 1 0 0 0 255 3 ///Черный\n&\n\n";

                String sPen="///Перья (9)\n";
                sPen+="@PEN\n";
                sPen+="0 0 255 0 0 255 0 ///Красный\n";
                sPen+="1 0 0 255 0 255 ///Зеленый\n";
                sPen+="2 0 0 0 255 255 1 ///Синий\n";
                sPen+="3 0 255 51 102 255 0 ///Розовый\n";
                sPen+="4 0 255 255 0 255 ///Ядовито желтый\n";
                sPen+="5 0 51 153 255 255 1 ///Небесно голубой\n";
                sPen+="6 0 204 51 255 255 1 ///Сирень\n";
                sPen+="7 0 204 102 0 255 1 ///Оранжевый\n";
                sPen+="8 0 0 0 0 255 3 ///Черный\n&\n\n";
                sBegin+="\n\n\n"+sBrush+sPen;
            }
            else{
                sMstyle="";
                sBegin+="\n\n\n";
            }
            
            sBegin+=sMstyle;
            sBegin+=sMat;
            sBegin+=sElsec;
            //запись в файл
            try(FileOutputStream fStream=new FileOutputStream(file);
                OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"windows-1251");
                BufferedWriter writer=new BufferedWriter(oWriter)){
                writer.append(sBegin);
                if(sPoint.length()>0)writer.append(sPoint);
                if(sPline.length()>0)writer.append(sPline);
                if(sLineloop.length()>0)writer.append(sLineloop);
                if(sPsurf.length()>0)writer.append(sPsurf);
                if(sShell.length()>0)writer.append(sShell);
                if(sBar.length()>0)writer.append(sBar);
                if(sEbody.length()>0)writer.append(sEbody);
                if(sSbody.length()>0)writer.append(sSbody);
                if(sNhsup.length()>0)writer.append(sNhsup);
                if(sCaxis.length()>0)writer.append(sCaxis); 
                if(sZone.length()>0)writer.append(sZone);
                if(sStorey.length()>0)writer.append(sStorey);             
                if(sGroup.length()>0)writer.append(sGroup);                
            }
            catch(IOException e){}
            //

            //Сообщение 4
            try{
                GregorianCalendar cal4=new GregorianCalendar();
                String hour4=""+cal4.get(Calendar.HOUR_OF_DAY);
                String minute4=""+cal4.get(Calendar.MINUTE);
                String second4=""+cal4.get(Calendar.SECOND);
                if(hour4.length()==1)hour4="0"+hour4;
                if(minute4.length()==1)minute4="0"+minute4;
                if(second4.length()==1)second4="0"+second4;
                BufferedWriter writer=null;
                if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\_finish.txt"));
                else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\_finish.txt"));
                writer.write("4: "+hour4+":"+minute4+":"+second4+" окончание записи в файл --------------------");
                writer.close();
            }
            catch(IOException ex){}
            //
        }
        else{
            //Сообщение об  ошибочном окончании работы
            try{
                BufferedWriter writer=null;
                if(!lPath.isEmpty())writer=new BufferedWriter(new FileWriter(lPath+"\\_finish.txt"));
                else writer=new BufferedWriter(new FileWriter(Sargs[4]+"\\_finish.txt"));
                writer.write("Работа плагина завершена с ошибкой");
                writer.close();
                System.exit(0);
            }
            catch(IOException ex){}
            //
        }
    }
    
    /**
     * \param
     * args[0]-файл Bsmt, который будет преобразован
     * args[1]-папка, куда будет сохранен файл Bsmt
     * args[4]-папка, куда сохраняются файлы с сообщениями
     * r-область вокруг объекта, в которой точка считается принадлежащей этому объекту.
     * К примеру если заданы r=0.1 и z=3, то будут обрабатываться точки от 2.9 до 3.1.
     * p-минимальное количество элементов
     * z-горизонтальная плоскость, задаваемая координатой Z, в которой осуществляется 
     * поиск групп конечных элементов и их преобразование.
     * x-вертикальная плоскость, задаваемая координатой X, в которой осуществляется
     * поиск групп конечных элементов и их преобразование.
     * y-вертикальная плоскость, задаваемая координатой Y, в которой осуществляется
     * поиск групп конечных элементов и их преобразование.
     * l-путь к папке для вывода сообщений
     * gen-преобразование исходного объекта
     * coord-создание осей для объекта (исходного или преобразованного)
     * group-группировка по уровню для объекта (исходного или преобразованного)
     * xCaxis=LL-создание осей по координате X, где LL минимальное количество элементов
     * для создания оси по этой координате
     * yCaxis=LL-создание осей по координате Y, где LL минимальное количество элементов
     * для создания оси по этой координате
     * zCaxis=LL-создание осей по координате Z, где LL минимальное количество элементов
     * для создания оси по этой координате
     */
    public static void main(String[] args) {
        Sargs=args;
        Generalization();
    }
    
}
