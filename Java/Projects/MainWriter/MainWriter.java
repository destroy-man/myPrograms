//MainWriter - программа для создания чертежа звёздочки к приводной роликовой (втулочной) цепи в формате DXF из исходных данных

import com.jsevy.jdxf.*;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.*;

public class MainWriter {

    public static void createPDF(Object[] primitives,int typeDetail){
        //Создание вспомогательного контейнера
        File folder = new File("files.vctg");
        if (!folder.exists())
            folder.mkdir();
        folder = new File("files.vctg\\PRJ"); //папка с инструкциями
        if (!folder.exists())
            folder.mkdir();
        folder = new File("files.vctg\\V2D"); //папка с v2d объектами
        if (!folder.exists())
            folder.mkdir();
        folder = new File("files.vctg\\FNT"); //папка с векторным шрифтом и штриховками
        if (!folder.exists())
            folder.mkdir();
        folder = new File("files.vctg\\DAT"); //папка с вложениями
        if (!folder.exists())
            folder.mkdir();
        File file=null;
        StringBuilder text2=new StringBuilder(); //текст файла инструкций
        StringBuilder text3=new StringBuilder(); //текст для v2d объектов
        
        text2.append("1101 300 0 0 0\n"+
                        "1102 5 0 5 0 0 0\n"+
                        "12021 0 0 10 0 0 0 255\n"+
                        "1501 5 5 \"2D.txt\"\n"+
                        "12021 1 0 10 0 0 0 255\n"+
                        "1201 1\n");
        text3.append("1210 0 1 0 0 0 0 255\n"+
                "1216 0 255 255 255 255\n"+
                "1215 0\n");
        if(typeDetail==101){
            TreeMap<Double,double[]> line2=(TreeMap)primitives[0];
            TreeMap<Double,double[]> line3=(TreeMap)primitives[1];
            TreeMap<Double,double[]> line4=(TreeMap)primitives[2];
            TreeMap<Double,double[]> arc1=(TreeMap)primitives[3];
            TreeMap<Double,double[]> arc2=(TreeMap)primitives[4];
            TreeMap<Double,double[]> arc3=(TreeMap)primitives[5];
            TreeMap<Double,double[]> arc4=(TreeMap)primitives[6];
            TreeMap<Double,double[]> arc5=(TreeMap)primitives[7];
            double Z=(double)primitives[8];
            double[] circle1=(double[])primitives[9];
            TreeMap<Double,double[]> circle2=(TreeMap)primitives[10];
            text3.append("5104 "+circle1[0]+" "+circle1[1]+" "+circle1[2]+"\n");
            for(Double i=90.0;i<450.0;i+=(360.0/Z)){
                text3.append("51121 "+arc1.get(i)[0]+" "+arc1.get(i)[1]+" "+arc1.get(i)[2]+" "+arc1.get(i)[2]+" "+arc1.get(i)[4]+" "+arc1.get(i)[3]+" 0 0\n");
                text3.append("51121 "+arc2.get(i)[0]+" "+arc2.get(i)[1]+" "+arc2.get(i)[2]+" "+arc2.get(i)[2]+" "+arc2.get(i)[4]+" "+arc2.get(i)[3]+" 0 0\n");
                text3.append("51121 "+arc3.get(i)[0]+" "+arc3.get(i)[1]+" "+arc3.get(i)[2]+" "+arc3.get(i)[2]+" "+arc3.get(i)[3]+" "+arc3.get(i)[4]+" 0 0\n");
                text3.append("51121 "+arc4.get(i)[0]+" "+arc4.get(i)[1]+" "+arc4.get(i)[2]+" "+arc4.get(i)[2]+" "+arc4.get(i)[3]+" "+arc4.get(i)[4]+" 0 0\n");
                text3.append("51121 "+arc5.get(i)[0]+" "+arc5.get(i)[1]+" "+arc5.get(i)[2]+" "+arc5.get(i)[2]+" "+arc5.get(i)[4]+" "+arc5.get(i)[3]+" 0 0\n");
                
//                text3.append("5105 "+arc1.get(i)[0]+" "+arc1.get(i)[1]+" "+arc1.get(i)[2]+" "+(-(arc1.get(i)[3]-90))+" "+(-(arc1.get(i)[4]-90))+"\n");            
//                text3.append("5105 "+arc2.get(i)[0]+" "+arc2.get(i)[1]+" "+arc2.get(i)[2]+" "+(-(arc2.get(i)[3]-90))+" "+(-(arc2.get(i)[4]-90))+"\n");
//                text3.append("5105 "+arc3.get(i)[0]+" "+arc3.get(i)[1]+" "+arc3.get(i)[2]+" "+(-(arc3.get(i)[3]-90))+" "+(-(arc3.get(i)[4]-90))+"\n");
//                text3.append("5105 "+arc4.get(i)[0]+" "+arc4.get(i)[1]+" "+arc4.get(i)[2]+" "+(-(arc4.get(i)[3]-90))+" "+(-(arc4.get(i)[4]-90))+"\n");
//                text3.append("5105 "+arc5.get(i)[0]+" "+arc5.get(i)[1]+" "+arc5.get(i)[2]+" "+(-(arc5.get(i)[3]-90))+" "+(-(arc5.get(i)[4]-90))+"\n");

                text3.append("5101 "+line2.get(i)[0]+" "+line2.get(i)[1]+" "+line2.get(i)[2]+" "+line2.get(i)[3]+"\n");
                text3.append("5101 "+line3.get(i)[0]+" "+line3.get(i)[1]+" "+line3.get(i)[2]+" "+line3.get(i)[3]+"\n");
                text3.append("5101 "+line4.get(i)[0]+" "+line4.get(i)[1]+" "+line4.get(i)[2]+" "+line4.get(i)[3]+"\n");
                text3.append("5104 "+circle2.get(i)[0]+" "+circle2.get(i)[1]+" "+circle2.get(i)[2]+"\n");
            }
        }
        else if(typeDetail==201){
            double[] circle1=(double[])primitives[0];
            TreeMap<Double,double[]> circle2=(TreeMap)primitives[1];
            double[] line1=(double[])primitives[2];
            double[] line2=(double[])primitives[3];
            double[] line3=(double[])primitives[4];
            double[] arc1=(double[])primitives[5];
            int N=(int)primitives[6];            
            text3.append("5104 "+circle1[0]+" "+circle1[1]+" "+circle1[2]+"\n");
            text3.append("5101 "+line1[0]+" "+line1[1]+" "+line1[2]+" "+line1[3]+"\n");
            text3.append("5101 "+line2[0]+" "+line2[1]+" "+line2[2]+" "+line2[3]+"\n");
            text3.append("5101 "+line3[0]+" "+line3[1]+" "+line3[2]+" "+line3[3]+"\n");
            text3.append("51121 "+arc1[0]+" "+arc1[1]+" "+arc1[2]+" "+arc1[2]+" "+arc1[3]+" "+arc1[4]+" 0 0\n");            
            double mainPercent=(double)100/N;
            for(double percent=mainPercent;percent<=100;percent+=mainPercent)
                text3.append("5104 "+circle2.get(percent)[0]+" "+circle2.get(percent)[1]+" "+circle2.get(percent)[2]+"\n");
        }
        else if(typeDetail==102){
            double[] circle1=(double[])primitives[0];
            double[] arc2=(double[])primitives[1];
            TreeMap<Double,double[]> circle2=(TreeMap)primitives[2];
            TreeMap<Double,double[]> line1=(TreeMap)primitives[3];
            TreeMap<Double,double[]> line2=(TreeMap)primitives[4];
            TreeMap<Double,double[]> line3=(TreeMap)primitives[5];
            TreeMap<Double,double[]> arc1=(TreeMap)primitives[6];
            int N=(int)primitives[7];
            
            text3.append("5104 "+circle1[0]+" "+circle1[1]+" "+circle1[2]+"\n");
            text3.append("51121 "+arc2[0]+" "+arc2[1]+" "+arc2[2]+" "+arc2[2]+" "+arc2[4]+" "+arc2[3]+" 0 0\n");
            double mainPercent=(double)100/N;
            for(double percent=mainPercent;percent<=100;percent+=mainPercent){
                text3.append("5104 "+circle2.get(percent)[0]+" "+circle2.get(percent)[1]+" "+circle2.get(percent)[2]+"\n");
                text3.append("5101 "+line1.get(percent)[0]+" "+line1.get(percent)[1]+" "+line1.get(percent)[2]+" "+line1.get(percent)[3]+"\n");
                text3.append("5101 "+line2.get(percent)[0]+" "+line2.get(percent)[1]+" "+line2.get(percent)[2]+" "+line2.get(percent)[3]+"\n");
                text3.append("5101 "+line3.get(percent)[0]+" "+line3.get(percent)[1]+" "+line3.get(percent)[2]+" "+line3.get(percent)[3]+"\n");
                if(percent+mainPercent>100)continue;
                text3.append("51121 "+arc1.get(percent+mainPercent)[0]+" "+arc1.get(percent+mainPercent)[1]+" "+arc1.get(percent+mainPercent)[2]+" "
                        +arc1.get(percent+mainPercent)[2]+" "+arc1.get(percent+mainPercent)[4]+" "+arc1.get(percent+mainPercent)[3]+" 0 0\n");
            }
        }
//        double alpha_temp = 0;
//        Double delta = Math.toDegrees(Math.asin(Math.toRadians((Math.sin(Y2) * Math.sin(Math.toRadians(90))) / Math.sin(OO2))));
//        
//        Arc2D.Double aDoubleLeft2 = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(90)) - r, d / 2 * Math.sin(Math.toRadians(90)) - r, 2 * r, 2 * r, -270 - alpha_temp, -alpha, Arc2D.Double.OPEN);
//        Double x52 = aDoubleLeft2.getEndPoint().getX();
//        Double y52 = aDoubleLeft2.getEndPoint().getY();
//
//        double dx2 = aDoubleLeft2.getCenterX() - x52;
//        double dy2 = aDoubleLeft2.getCenterY() - y52;
//        double r_temp2 = Math.sqrt(Math.pow(dx2, 2) + Math.pow(dy2, 2));
//        double xx2 = dx2 * (r1 / r_temp2);
//        double yy2 = dy2 * (r1 / r_temp2);
//        Double newX2 = x52 + xx2;
//        Double newY2 = y52 + yy2;
//
//        Double a = Math.sqrt(Math.pow(aDoubleLeft2.getCenterX() - newX2 - r2, 2) + Math.pow(aDoubleLeft2.getCenterY() - newY2 - r2, 2));
//        Double b = Math.sqrt(Math.pow(newX2 - r2 - center[0], 2) + Math.pow(newY2 - r2 - center[1], 2));
//        Double c = Math.sqrt(Math.pow(aDoubleLeft2.getCenterX() - center[0], 2) + Math.pow(aDoubleLeft2.getCenterY() - center[1], 2));
        
//        for (Double i = 90.0; i < 450.0; i += (360.0 / Z)) {
//            dxfDocument.addEntity(new DXFLine(new RealPoint(center[0], center[1], 0), new RealPoint(d / 2 * Math.cos(Math.toRadians(i)), d / 2 * Math.sin(Math.toRadians(i)), 0), graphics));
//
////            DXFCircle dxfCircle = new DXFCircle(new RealPoint(d / 2 * Math.cos(Math.toRadians(i)), d / 2 * Math.sin(Math.toRadians(i)), 0), r, graphics);
//            // дуга впадины
//            DXFArc dxfArc = new DXFArc(new RealPoint(d / 2 * Math.cos(Math.toRadians(i)), d / 2 * Math.sin(Math.toRadians(i)), 0), r, Math.toRadians(-90 + alpha_temp + alpha), Math.toRadians(-90 + alpha_temp - alpha), false, graphics);
//            dxfDocument.addEntity(dxfArc);
//
//            Arc2D.Double aDoubleLeft = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i)) - r, d / 2 * Math.sin(Math.toRadians(i)) - r, 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
////            dxfDocument.addEntity(new DXFLine(new RealPoint(r * Math.cos(Math.toRadians(Math.toRadians(alpha_temp + (270 - alpha)))) + d / 2 * Math.cos(Math.toRadians(i)), r * Math.sin(Math.toRadians(Math.toRadians(alpha_temp + (270 - alpha)))) + d / 2 * Math.cos(Math.toRadians(i)), 0), new RealPoint(r * Math.cos(Math.toRadians(Math.toRadians(alpha_temp + (270 + alpha)))), r * Math.sin(Math.toRadians(Math.toRadians(alpha_temp + (270 + alpha)))), 0), graphics));
//            Double x5 = aDoubleLeft.getStartPoint().getX();
//            Double y5 = aDoubleLeft.getStartPoint().getY();
//
//            double dx = aDoubleLeft.getCenterX() - x5;
//            double dy = aDoubleLeft.getCenterY() - y5;
//            double r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
//            double xx = dx * (r1 / r_temp);
//            double yy = dy * (r1 / r_temp);
//            Double newX = x5 + xx;
//            Double newY = y5 + yy;
//
//            DXFArc dugaSopr = new DXFArc(new RealPoint(newX, newY, 0), r1, Math.toRadians(-90 + alpha_temp - alpha), Math.toRadians(-90 + alpha_temp - alpha - beta), false, graphics);
//
//            // дуга сопряжения
//            dxfDocument.addEntity(new DXFArc(new RealPoint(newX, newY, 0), r1, Math.toRadians(-90 + alpha_temp - alpha), Math.toRadians(-90 + alpha_temp - alpha - beta), false, graphics));
//
//            x5 = aDoubleLeft.getEndPoint().getX();
//            y5 = aDoubleLeft.getEndPoint().getY();
//
//            dx = aDoubleLeft.getCenterX() - x5;
//            dy = aDoubleLeft.getCenterY() - y5;
//            r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
//            xx = dx * (r1 / r_temp);
//            yy = dy * (r1 / r_temp);
//            newX = x5 + xx;
//            newY = y5 + yy;
//
//            DXFArc dugaSopr2 = new DXFArc(new RealPoint(newX, newY, 0), r1, Math.toRadians(-90 + alpha_temp + alpha), Math.toRadians(-90 + alpha_temp + alpha + beta), true, graphics);
//
//            dxfDocument.addEntity(new DXFArc(new RealPoint(newX, newY, 0), r1, Math.toRadians(-90 + alpha_temp + alpha), Math.toRadians(-90 + alpha_temp + alpha + beta), true, graphics));
//
//            Arc2D.Double aDoubleLeftLastPrev = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i + (360 / Z))) - r, d / 2 * Math.sin(Math.toRadians(i + (360 / Z))) - r, 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
//
////            dxfDocument.addEntity(new DXFLine(new RealPoint(aDoubleLeftLastPrev.getCenterX(), aDoubleLeftLastPrev.getCenterY(), 0), new RealPoint(aDoubleLeft.getCenterX(), aDoubleLeft.getCenterY(), 0), graphics));
//            dx = aDoubleLeftLastPrev.getCenterX() - aDoubleLeft.getCenterX();
//            dy = aDoubleLeftLastPrev.getCenterY() - aDoubleLeft.getCenterY();
//            r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
//            xx = dx * (OO2 / r_temp);
//            yy = dy * (OO2 / r_temp);
//            newX = aDoubleLeft.getCenterX() + xx;
//            newY = aDoubleLeft.getCenterY() + yy;
//
//            // дуга головки зуба
//            DXFArc dugaGolZuba = new DXFArc(new RealPoint(newX, newY, 0), r2, Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD), Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD + 2 * phi), true, graphics);
//
//            dxfDocument.addEntity(new DXFArc(new RealPoint(newX, newY, 0), r2, Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD), Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD + 2 * phi), true, graphics));
//
//            Arc2D.Double aDoubleRightNext = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i - (360 / Z))) - r, d / 2 * Math.sin(Math.toRadians(i - (360 / Z))) - r, 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
//
//            dx = aDoubleRightNext.getCenterX() - aDoubleLeft.getCenterX();
//            dy = aDoubleRightNext.getCenterY() - aDoubleLeft.getCenterY();
//            r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
//            xx = dx * (OO2 / r_temp);
//            yy = dy * (OO2 / r_temp);
//            newX = aDoubleLeft.getCenterX() + xx;
//            newY = aDoubleLeft.getCenterY() + yy;
//
//            DXFArc dugaGolZuba2 = new DXFArc(new RealPoint(newX, newY, 0), r2, Math.toRadians(180 + alpha_temp - delta - (180 / Z) - angleD), Math.toRadians(180 + alpha_temp - delta - (180 / Z) - angleD - 2 * phi), false, graphics);
//
//            dxfDocument.addEntity(new DXFArc(new RealPoint(newX, newY, 0), r2, Math.toRadians(180 + alpha_temp - delta - (180 / Z) - angleD), Math.toRadians(180 + alpha_temp - delta - (180 / Z) - angleD - 2 * phi), false, graphics));
//
//            // прямой участок профиля
//            dxfDocument.addEntity(new DXFLine(new RealPoint(dugaSopr.getEndPoint()[0], dugaSopr.getEndPoint()[1], 0), new RealPoint(dugaGolZuba.getStartPoint()[0], dugaGolZuba.getStartPoint()[1], 0), graphics));
//
//            dxfDocument.addEntity(new DXFLine(new RealPoint(dugaSopr2.getEndPoint()[0], dugaSopr2.getEndPoint()[1], 0), new RealPoint(dugaGolZuba2.getStartPoint()[0], dugaGolZuba2.getStartPoint()[1], 0), graphics));
//
//            Arc2D.Double aDoubleLeftPrev = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i)) - r, d / 2 * Math.sin(Math.toRadians(i)) - r, 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
//            Arc2D.Double aDoubleLeftNext = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i - (360 / Z))) - r, d / 2 * Math.sin(Math.toRadians(i - (360 / Z))) - r, 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
//
////            dxfDocument.addEntity(new DXFLine(new RealPoint(aDoubleLeftLastPrev.getCenterX(), aDoubleLeftLastPrev.getCenterY(), 0), new RealPoint(aDoubleLeft.getCenterX(), aDoubleLeft.getCenterY(), 0), graphics));
//            dx = aDoubleLeftPrev.getCenterX() - aDoubleLeftNext.getCenterX();
//            dy = aDoubleLeftPrev.getCenterY() - aDoubleLeftNext.getCenterY();
//            r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
//            xx = dx * (OO2 / r_temp);
//            yy = dy * (OO2 / r_temp);
//            newX = aDoubleLeftNext.getCenterX() + xx;
//            newY = aDoubleLeftNext.getCenterY() + yy;
//
//            alpha_temp -= (360 / Z);
//            DXFArc dugaGolZubaNext = new DXFArc(new RealPoint(newX, newY, 0), r2, Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD), Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD + 2 * phi), true, graphics);
//            alpha_temp += (360 / Z);
//            // прямой участок профиля на кончике зуба
////            dxfDocument.addEntity(new DXFLine(new RealPoint(dugaGolZubaNext.getStartPoint()[0], dugaGolZubaNext.getStartPoint()[1], 0), new RealPoint(dugaGolZubaNext.getEndPoint()[0], dugaGolZubaNext.getEndPoint()[1], 0), graphics));
//            dxfDocument.addEntity(new DXFLine(new RealPoint(dugaGolZuba2.getEndPoint()[0], dugaGolZuba2.getEndPoint()[1], 0), new RealPoint(dugaGolZubaNext.getEndPoint()[0], dugaGolZubaNext.getEndPoint()[1], 0), graphics));
//
//            alpha_temp += (360.0 / Z);
//            graphics.setColor(Color.RED);
//        }
//
////        dxfDocument.addEntity(new DXFArc);
//        String dxtText = dxfDocument.toDXFString();
//        try (FileWriter fileWriter = new FileWriter("dxffile.dxf")) {
//            fileWriter.write(dxtText);
//            fileWriter.flush();
//            System.out.println("Записан файл dxffile.dxf");
//        } catch (IOException e) {
//            System.out.println("Ошибка записи файла: " + e);
//        }

        //Создание файла инструкций
        file=new File("files.vctg\\PRJ\\inst.01.txt");
        try(FileOutputStream fStream=new FileOutputStream(file);
            OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"UTF-8");
            BufferedWriter writer=new BufferedWriter(oWriter)){
            String[] sArr=text2.toString().split("\n");
            for(String s:sArr){
                writer.write(s);
                writer.newLine();
            }
        }
        catch(IOException ex){}
        //Создание v2d файла
        file=new File("files.vctg\\V2D\\2D.txt");
        try(FileOutputStream fStream=new FileOutputStream(file);
            OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"UTF-8");
            BufferedWriter writer=new BufferedWriter(oWriter)){
            String[] text3Arr=text3.toString().split("\n");
            for(String s:text3Arr){
                writer.write(s);
                writer.newLine();
            }
        }
        catch(IOException ex){}
        //Создание bat файла и запуск инфографа
        File batFile=new File("run.bat");
        try(FileOutputStream fStream=new FileOutputStream(batFile);
            OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"windows-1251");
            BufferedWriter writer=new BufferedWriter(oWriter)){
            writer.write("chcp 1251 >nul");
            writer.newLine();
            writer.write("graph.exe \"files.vctg\" -o=\"files.pdf\"");
        }
        catch(IOException ex){}
        
        try{
            Process p=new ProcessBuilder("cmd","/C",batFile.getPath()).start();
            try{
                p.waitFor();
            }
            catch(InterruptedException ex){}
        }
        catch(IOException ex){}
        //
    }
    
    public static void main(String[] args) throws IOException {
        // центр
        //double[] center = {0, 0};
        double[] center = {100, 150};
        // диаметр окружности впадин
        double Di = 0;
        // диаметр окружности выступов
        double De = 0;
        double Du = 0;
        double d = 0;
        double Z = 0;
        double r = 0;
        double alpha = 0;
        double beta = 0;
        double phi = 0;
        double lambda = 0;
        double r1 = 0;
        double r2 = 0;
        double X1 = 0;
        double Y1 = 0;
        double X2 = 0;
        double Y2 = 0;
        double OO2 = 0;
        double t = 0;
        double K = 0;
        //
        double D=0;
        double d0=0;
        double A=0;
        double B=0;
        int N=0;
        double d1=0;
        double R=0,R1=0,R2=0;
        boolean isPdf=false;
        for(String arg:args)
            if(arg.equals("-pdf"))
                isPdf=true;
        double tInFile=0;
        double DuInFile=0;
        
        int typeDetail=101;
        //
        
        // чтение исходного файла
        List<String> data = null;
        try {
            data = Files.readAllLines(new File("data.txt").toPath());
            System.out.println("Прочитан исходный файл");
        } catch (Exception e) {
            System.out.println("Ошибка чтения исходного файла: " + e);
        }
        for (String string : data) {
            String[] split = string.split("=");
            switch (split[0]) {
                //Параметры для звездочки
                case "Du": {//значение количества зубьев цепи
                    DuInFile=Double.parseDouble(split[1]);
                    Du = Double.parseDouble(split[1]) * (3.7795275591);
                    break;
                }
                case "Z": //значение шага цепи
                    Z = Double.parseDouble(split[1]);
                    break;
                case "t": {//размер диаметра элемента заципления цепей
                    tInFile=Double.parseDouble(split[1]);
                    t = Double.parseDouble(split[1]) * (3.7795275591);
                    break;
                }
                //Параметры для внутренней детали
                case "D": //диаметр основной окружности
                    D=Double.parseDouble(split[1]);
                    break;
                case "d": //диаметр малой окружности
                    d0=Double.parseDouble(split[1]);
                    break;
                case "A": //длина стороны A
                    A=Double.parseDouble(split[1]);
                    break;
                case "B": //длина стороны B
                    B=Double.parseDouble(split[1]);
                    break;
                case "N": //количество отверстий
                    N=Integer.parseInt(split[1]);
                    break;
                case "d1": //диаметр отверстий
                    d1=Double.parseDouble(split[1]);
                    break;
                case "R": //радиус удаленности отверстий
                    R=Double.parseDouble(split[1]);
                    break;
                case "R1":
                    R1=Double.parseDouble(split[1]);
                    break;
                case "R2":
                    R2=Double.parseDouble(split[1]);
                    break;
                case "tp": //тип детали
                    typeDetail=Integer.parseInt(split[1]);
                    break;
                //Если параметр в файле не удалось обработать
                default:
                    if (!string.contains("//") && !string.isEmpty())
                        System.out.println(string);
                    break;
                //
            }
        }

        // расчёты
        alpha = 55.0 - (60.0 / Z);
        phi = 17 - (64 / Z);
        r = 0.5025 * Du + 0.05;
        r1 = 0.8 * Du + r;
        X1 = 0.8 * Du * Math.sin(alpha);
        Y1 = 0.8 * Du * Math.cos(alpha);
        X2 = 1.24 * Du * Math.cos(180 / Z);
        Y2 = 1.24 * Du * Math.sin(Math.toRadians(180 / Z));
        beta = 18.0 - (56.0 / Z);
        r2 = Du * (1.24 * Math.cos(Math.toRadians(phi)) + 0.8 * Math.cos(Math.toRadians(beta)) - 1.3025) - 0.05;
        OO2 = 1.24 * Du;
        lambda = t / Du;

        if (lambda >= 1.40 && lambda < 1.50)
            K = 0.480;
        else if (lambda >= 1.50 && lambda < 1.60)
            K = 0.532;
        else if (lambda >= 1.60 && lambda < 1.70)
            K = 0.555;
        else if (lambda >= 1.70 && lambda < 1.80)
            K = 0.575;
        else if (lambda >= 1.80 && lambda < 2.00)
            K = 0.565;

        De = t * (K + (1 / Math.tan(Math.toRadians(180 / Z))));
        d = t / Math.sin(Math.toRadians(180 / Z));
        Di = d - 2 * r;

        // создание dxf
        DXFDocument dxfDocument = new DXFDocument("Example");
        DXFGraphics graphics = dxfDocument.getGraphics();
        Object[] primitives=null;
        
        if(typeDetail==201){
            //Ступица
            double[] circle1=new double[]{center[0],center[1],D/2};
            double x=Math.sqrt(Math.abs(Math.pow(d0/2,2)-Math.pow(A/2,2)));
            double[] line1=new double[]{center[0]+x,center[1]+A/2,center[0]+x+B,center[1]+A/2};
            double[] line2=new double[]{center[0]+x,center[1]-A/2,center[0]+x+B,center[1]-A/2};
            double[] line3=new double[]{center[0]+x+B,center[1]+A/2,center[0]+x+B,center[1]-A/2};
            
            //Создание дуги
            double x1=center[0];
            double y1=center[1];
            double x2=center[0]+x;
            double y2=center[1]+A/2;
            double xNapr=center[0]+R;
            double yNapr=center[1];
            //Нахождение угла
            double x01=x1-xNapr;
            double x02=x1-x2;
            double y01=y1-yNapr;
            double y02=y1-y2;
            double D1=Math.sqrt(x01*x01+y01*y01);
            double D2=Math.sqrt(x02*x02+y02*y02);
            double angle=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
            //
            double[] arc1=new double[]{center[0],center[1],d0/2,angle,-angle};            
                        
            //Создание отверстий
            TreeMap<Double,double[]> circle2=new TreeMap<Double,double[]>();
            double mainPercent=(double)100/N;
            for(double percent=mainPercent;percent<=100;percent+=mainPercent){
                //Отверстия в верхней левой области
                if(percent<=25){
                    double segmentPercent=percent*4;
                    angle=(int)Math.round(90*(segmentPercent/100));
                    x=R*Math.cos(Math.toRadians(90-angle));
                    double y=R*Math.sin(Math.toRadians(90-angle));
                    circle2.put(percent,new double[]{center[0]-x,center[1]+y,d1/2});
                }
                //Отверстия в нижней левой области
                else if(percent>25 && percent<=50){
                    double segment=percent-25;
                    double segmentPercent=segment*4;
                    angle=(int)Math.round(90*(segmentPercent)/100);
                    if(angle==90)x=0;
                    else x=R*Math.cos(Math.toRadians(angle));
                    double y=R*Math.sin(Math.toRadians(angle));
                    circle2.put(percent,new double[]{center[0]-x,center[1]-y,d1/2});
                }
                //Отверстия в нижней правой области
                else if(percent>50 && percent<=75){
                    double segment=percent-50;
                    double segmentPercent=segment*4;
                    angle=(int)Math.round(90*(segmentPercent/100));
                    x=R*Math.cos(Math.toRadians(90-angle));
                    double y=R*Math.sin(Math.toRadians(90-angle));
                    circle2.put(percent,new double[]{center[0]+x,center[1]-y,d1/2});
                }
                //Отверстия в верхней правой области
                else{
                    double segment=percent-75;
                    double segmentPercent=segment*4;
                    angle=(int)Math.round(90*(segmentPercent/100));
                    if(angle==90)x=0;
                    else x=R*Math.cos(Math.toRadians(angle));
                    double y=R*Math.sin(Math.toRadians(angle));
                    circle2.put(percent,new double[]{center[0]+x,center[1]+y,d1/2});
                }
                //
            }
            
            primitives=new Object[]{circle1,circle2,line1,line2,line3,arc1,N};
            if(isPdf)createPDF(primitives,typeDetail);
            
            dxfDocument.addEntity(new DXFCircle(new RealPoint(circle1[0],circle1[1],0),circle1[2],graphics));
            dxfDocument.addEntity(new DXFLine(new RealPoint(line1[0],line1[1],0),new RealPoint(line1[2],line1[3],0),graphics));
            dxfDocument.addEntity(new DXFLine(new RealPoint(line2[0],line2[1],0),new RealPoint(line2[2],line2[3],0),graphics));
            dxfDocument.addEntity(new DXFLine(new RealPoint(line3[0],line3[1],0),new RealPoint(line3[2],line3[3],0),graphics));
            dxfDocument.addEntity(new DXFArc(new RealPoint(arc1[0],arc1[1],0),arc1[2],Math.toRadians(arc1[3]),Math.toRadians(arc1[4]),true,graphics));
            for(double percent=mainPercent;percent<=100;percent+=mainPercent)
                dxfDocument.addEntity(new DXFCircle(new RealPoint(circle2.get(percent)[0],circle2.get(percent)[1],0),circle2.get(percent)[2],graphics));
        }
        //
        else if(typeDetail==101){
            //Предварительные расчеты для создания примитивов
            double alpha_temp = 0;
            Double delta = Math.toDegrees(Math.asin(Math.toRadians((Math.sin(Y2) * Math.sin(Math.toRadians(90))) / Math.sin(OO2))));

            Arc2D.Double aDoubleLeft2 = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(90)) - r+center[0], d / 2 * Math.sin(Math.toRadians(90)) - r+center[1], 2 * r, 2 * r, -270 - alpha_temp, -alpha, Arc2D.Double.OPEN);
            Double x52 = aDoubleLeft2.getEndPoint().getX();
            Double y52 = aDoubleLeft2.getEndPoint().getY();

            double dx2 = aDoubleLeft2.getCenterX() - x52;
            double dy2 = aDoubleLeft2.getCenterY() - y52;
            double r_temp2 = Math.sqrt(Math.pow(dx2, 2) + Math.pow(dy2, 2));
            double xx2 = dx2 * (r1 / r_temp2);
            double yy2 = dy2 * (r1 / r_temp2);
            Double newX2 = x52 + xx2;
            Double newY2 = y52 + yy2;

            Double a = Math.sqrt(Math.pow(aDoubleLeft2.getCenterX() - newX2 - r2, 2) + Math.pow(aDoubleLeft2.getCenterY() - newY2 - r2, 2));
            Double b = Math.sqrt(Math.pow(newX2 - r2 - center[0], 2) + Math.pow(newY2 - r2 - center[1], 2));
            Double c = Math.sqrt(Math.pow(aDoubleLeft2.getCenterX() - center[0], 2) + Math.pow(aDoubleLeft2.getCenterY() - center[1], 2));

            Double angleD = Math.toDegrees(Math.acos((Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2)) / (2 * b * c)));
            //Создание примитивов
//            double[] circle1=new double[]{center[0],center[1],Di/2}; //Меньшая окружность
//            double[] circle2=new double[]{center[0],center[1],De/2}; //Большая окружность
//            double[] circle3=new double[]{center[0],center[1],d/2}; //Средняя окружность

            double[] circle1=new double[]{center[0],center[1],D/2};

            StringBuilder textObjects=new StringBuilder();
            textObjects.append("//Тип шестеренки - Тип-1 (звездочка к приводной роликовой (втулочной) цепи)\n");
            textObjects.append("//Исходные данные:\n");
            textObjects.append("//1. Значение количества зубьев цепи (Z)="+Z+".\n");
            textObjects.append("//2. Значение шага цепи (t)="+tInFile+".\n");
            textObjects.append("//3. Размер диаметра элемента зацепления цепей (Du)="+DuInFile+".\n\n");

//            textObjects.append("Окружность1 "+circle1[0]+" "+circle1[1]+" "+circle1[2]+"\n");
//            textObjects.append("Окружность2 "+circle2[0]+" "+circle2[1]+" "+circle2[2]+"\n");
//            textObjects.append("Окружность3 "+circle3[0]+" "+circle3[1]+" "+circle3[2]+"\n");

            int numLine=0,numArc=0;
            TreeMap<Double,double[]> line1=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> line2=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> line3=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> line4=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> arc1=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> arc2=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> arc3=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> arc4=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> arc5=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> circle2=new TreeMap<Double,double[]>();
            for(Double i=90.0;i<450.0;i+=(360.0/Z)){
                line1.put(i,new double[]{
                            center[0],
                            center[1],
                            d / 2 * Math.cos(Math.toRadians(i))+center[0],
                            d / 2 * Math.sin(Math.toRadians(i))+center[1]});
                textObjects.append("Линия"+(++numLine)+" "+line1.get(i)[0]+" "+line1.get(i)[1]+" "+line1.get(i)[2]+" "+line1.get(i)[3]+"\n");

                arc1.put(i,new double[]{
                            d / 2 * Math.cos(Math.toRadians(i))+center[0],
                            d / 2 * Math.sin(Math.toRadians(i))+center[1],
                            r,
                            (-90 + alpha_temp + alpha),
                            (-90 + alpha_temp - alpha),
                            0});
                textObjects.append("Дуга"+(++numArc)+" "+arc1.get(i)[0]+" "+arc1.get(i)[1]+" "+arc1.get(i)[2]+" "+arc1.get(i)[3]+" "+arc1.get(i)[4]+"\n");

                Arc2D.Double aDoubleLeft = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i)) - r+center[0], d / 2 * Math.sin(Math.toRadians(i)) - r+center[1], 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
                Double x5 = aDoubleLeft.getStartPoint().getX();
                Double y5 = aDoubleLeft.getStartPoint().getY();
                double dx = aDoubleLeft.getCenterX() - x5;
                double dy = aDoubleLeft.getCenterY() - y5;
                double r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                double xx = dx * (r1 / r_temp);
                double yy = dy * (r1 / r_temp);
                Double newX = x5 + xx;
                Double newY = y5 + yy;
                DXFArc dugaSopr = new DXFArc(new RealPoint(newX, newY, 0), r1, Math.toRadians(-90 + alpha_temp - alpha), Math.toRadians(-90 + alpha_temp - alpha - beta), false, graphics);

                arc2.put(i,new double[]{
                            newX,
                            newY,
                            r1,
                            (-90 + alpha_temp - alpha),
                            (-90 + alpha_temp - alpha - beta),
                            0});
                textObjects.append("Дуга"+(++numArc)+" "+arc2.get(i)[0]+" "+arc2.get(i)[1]+" "+arc2.get(i)[2]+" "+arc2.get(i)[3]+" "+arc2.get(i)[4]+"\n");

                x5 = aDoubleLeft.getEndPoint().getX();
                y5 = aDoubleLeft.getEndPoint().getY();
                dx = aDoubleLeft.getCenterX() - x5;
                dy = aDoubleLeft.getCenterY() - y5;
                r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                xx = dx * (r1 / r_temp);
                yy = dy * (r1 / r_temp);
                newX = x5 + xx;
                newY = y5 + yy;
                DXFArc dugaSopr2 = new DXFArc(new RealPoint(newX, newY, 0), r1, Math.toRadians(-90 + alpha_temp + alpha), Math.toRadians(-90 + alpha_temp + alpha + beta), true, graphics);

                arc3.put(i,new double[]{
                            newX,
                            newY,
                            r1,
                            (-90 + alpha_temp + alpha),
                            (-90 + alpha_temp + alpha + beta),
                            1});
                textObjects.append("Дуга"+(++numArc)+" "+arc3.get(i)[0]+" "+arc3.get(i)[1]+" "+arc3.get(i)[2]+" "+arc3.get(i)[3]+" "+arc3.get(i)[4]+"\n");

                Arc2D.Double aDoubleLeftLastPrev = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i + (360 / Z))) - r+center[0], d / 2 * Math.sin(Math.toRadians(i + (360 / Z))) - r+center[1], 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
                dx = aDoubleLeftLastPrev.getCenterX() - aDoubleLeft.getCenterX();
                dy = aDoubleLeftLastPrev.getCenterY() - aDoubleLeft.getCenterY();
                r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                xx = dx * (OO2 / r_temp);
                yy = dy * (OO2 / r_temp);
                newX = aDoubleLeft.getCenterX() + xx;
                newY = aDoubleLeft.getCenterY() + yy;
                // дуга головки зуба
                DXFArc dugaGolZuba = new DXFArc(new RealPoint(newX, newY, 0), r2, Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD), Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD + 2 * phi), true, graphics);

                arc4.put(i,new double[]{
                            newX,
                            newY,
                            r2,
                            (0 + alpha_temp + delta + (180 / Z) + angleD),
                            (0 + alpha_temp + delta + (180 / Z) + angleD + 2 * phi),
                            1});
                textObjects.append("Дуга"+(++numArc)+" "+arc4.get(i)[0]+" "+arc4.get(i)[1]+" "+arc4.get(i)[2]+" "+arc4.get(i)[3]+" "+arc4.get(i)[4]+"\n");

                Arc2D.Double aDoubleRightNext = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i - (360 / Z))) - r+center[0], d / 2 * Math.sin(Math.toRadians(i - (360 / Z))) - r+center[1], 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
                dx = aDoubleRightNext.getCenterX() - aDoubleLeft.getCenterX();
                dy = aDoubleRightNext.getCenterY() - aDoubleLeft.getCenterY();
                r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                xx = dx * (OO2 / r_temp);
                yy = dy * (OO2 / r_temp);
                newX = aDoubleLeft.getCenterX() + xx;
                newY = aDoubleLeft.getCenterY() + yy;
                DXFArc dugaGolZuba2 = new DXFArc(new RealPoint(newX, newY, 0), r2, Math.toRadians(180 + alpha_temp - delta - (180 / Z) - angleD), Math.toRadians(180 + alpha_temp - delta - (180 / Z) - angleD - 2 * phi), false, graphics);

                arc5.put(i,new double[]{
                            newX,
                            newY,
                            r2,
                            (180 + alpha_temp - delta - (180 / Z) - angleD),
                            (180 + alpha_temp - delta - (180 / Z) - angleD - 2 * phi),
                            0});
                textObjects.append("Дуга"+(++numArc)+" "+arc5.get(i)[0]+" "+arc5.get(i)[1]+" "+arc5.get(i)[2]+" "+arc5.get(i)[3]+" "+arc5.get(i)[4]+"\n");

                line2.put(i,new double[]{
                            dugaSopr.getEndPoint()[0],
                            dugaSopr.getEndPoint()[1],
                            dugaGolZuba.getStartPoint()[0],
                            dugaGolZuba.getStartPoint()[1]});
                textObjects.append("Линия"+(++numLine)+" "+line2.get(i)[0]+" "+line2.get(i)[1]+" "+line2.get(i)[2]+" "+line2.get(i)[3]+"\n");

                line3.put(i,new double[]{
                            dugaSopr2.getEndPoint()[0],
                            dugaSopr2.getEndPoint()[1],
                            dugaGolZuba2.getStartPoint()[0],
                            dugaGolZuba2.getStartPoint()[1]});
                textObjects.append("Линия"+(++numLine)+" "+line3.get(i)[0]+" "+line3.get(i)[1]+" "+line3.get(i)[2]+" "+line3.get(i)[3]+"\n");

                Arc2D.Double aDoubleLeftPrev = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i)) - r+center[0], d / 2 * Math.sin(Math.toRadians(i)) - r+center[1], 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
                Arc2D.Double aDoubleLeftNext = new Arc2D.Double(d / 2 * Math.cos(Math.toRadians(i - (360 / Z))) - r+center[0], d / 2 * Math.sin(Math.toRadians(i - (360 / Z))) - r+center[1], 2 * r, 2 * r, -270 - alpha_temp + alpha, -2 * alpha, Arc2D.Double.OPEN);
                dx = aDoubleLeftPrev.getCenterX() - aDoubleLeftNext.getCenterX();
                dy = aDoubleLeftPrev.getCenterY() - aDoubleLeftNext.getCenterY();
                r_temp = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                xx = dx * (OO2 / r_temp);
                yy = dy * (OO2 / r_temp);
                newX = aDoubleLeftNext.getCenterX() + xx;
                newY = aDoubleLeftNext.getCenterY() + yy;
                alpha_temp -= (360 / Z);
                DXFArc dugaGolZubaNext = new DXFArc(new RealPoint(newX, newY, 0), r2, Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD), Math.toRadians(0 + alpha_temp + delta + (180 / Z) + angleD + 2 * phi), true, graphics);
                alpha_temp += (360 / Z);

                line4.put(i,new double[]{
                            dugaGolZuba2.getEndPoint()[0],
                            dugaGolZuba2.getEndPoint()[1],
                            dugaGolZubaNext.getEndPoint()[0],
                            dugaGolZubaNext.getEndPoint()[1]});
                textObjects.append("Линия"+(++numLine)+" "+line4.get(i)[0]+" "+line4.get(i)[1]+" "+line4.get(i)[2]+" "+line4.get(i)[3]+"\n");

                alpha_temp += (360.0 / Z);
                
                //Рисование дополнительных отверстий
                double addX=Math.abs(line4.get(i)[0]-line4.get(i)[2])/2;
                double addY=Math.abs(line4.get(i)[1]-line4.get(i)[3])/2;
                double x2=line4.get(i)[0]+addX;
                double y2=line4.get(i)[1]+addY;
                if(line4.get(i)[0]>line4.get(i)[2])x2=line4.get(i)[2]+addX;
                if(line4.get(i)[1]>line4.get(i)[3])y2=line4.get(i)[3]+addY;
                
                double x1=center[0];
                double y1=center[1];
                double xNapr=center[0]+R;
                double yNapr=center[1];
                //Нахождение угла
                double x01=x1-xNapr;
                double x02=x1-x2;
                double y01=y1-yNapr;
                double y02=y1-y2;
                double D1=Math.sqrt(x01*x01+y01*y01);
                double D2=Math.sqrt(x02*x02+y02*y02);
                double angle=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                if(y02>0)angle=-angle; //если точка находится в нижней половине детали
                
                double xNew=R*Math.cos(Math.toRadians(angle))+center[0];
                double yNew=R*Math.sin(Math.toRadians(angle))+center[1];
                
                //dxfDocument.addEntity(new DXFCircle(new RealPoint(xNew,yNew,0),d1/2,graphics));
                circle2.put(i,new double[]{xNew,yNew,d1/2});
                //
            }
            //
            File objectsFile=new File("Objects.txt");
            try(FileOutputStream fStream=new FileOutputStream(objectsFile);
                OutputStreamWriter oWriter=new OutputStreamWriter(fStream,"UTF-8");
                BufferedWriter writer=new BufferedWriter(oWriter)){
                String[] textObjectsArr=textObjects.toString().split("\n");
                for(String s:textObjectsArr){
                    writer.write(s);
                    writer.newLine();
                }
            }
            catch(IOException ex){}
            //

            // рисование
            // окружности
            dxfDocument.addEntity(new DXFCircle(new RealPoint(circle1[0],circle1[1],0),circle1[2],graphics)); //создание основного отверстия
            
            //Проверка
//            if(D/2<=Di/2-10)
//                dxfDocument.addEntity(new DXFCircle(new RealPoint(circle1[0],circle1[1],0),D/2,graphics));
//            else{
//                System.out.println("Задан слишком большой диаметр основного отверстия (D). "
//                        + "Максимально допустимое значение: "+(Di/2-10));
//                System.exit(0);
//            }
//                
//            if(R<=D/2 || R>=Di/2){
//                if(R<=D/2)
//                    System.out.println("Неправильно задан радиус удаленности дополнительных отверстий (R)."
//                            + "Дополнительные отверстия должны быть за пределами основного.");
//                else
//                    System.out.println("Неправильно задан радиус удаленности дополнительных отверстий (R)."
//                            + "Дополнительные отверстия не должны лежать на зубе шестеренки.");
//                System.out.println("Радиус удаленности дополнительных отверстий (R) "
//                        + "должен находиться в интервале от "+(D/2)+" до "+(Di/2));
//            }
            //

            graphics.setColor(Color.BLACK);
            //
            primitives=new Object[]{line2,line3,line4,arc1,arc2,arc3,arc4,arc5,Z,circle1,circle2};
            if(isPdf)createPDF(primitives,typeDetail);
            //
            for (Double i = 90.0; i < 450.0; i += (360.0 / Z)) {
                //dxfDocument.addEntity(new DXFLine(new RealPoint(line1.get(i)[0],line1.get(i)[1], 0),new RealPoint(line1.get(i)[2],line1.get(i)[3], 0), graphics));

                // дуга впадины
                DXFArc dxfArc = new DXFArc(new RealPoint(arc1.get(i)[0], arc1.get(i)[1], 0), arc1.get(i)[2], Math.toRadians(arc1.get(i)[3]), Math.toRadians(arc1.get(i)[4]), arc1.get(i)[5]==1?true:false, graphics); //false
                dxfDocument.addEntity(dxfArc);

                // дуга сопряжения
                dxfDocument.addEntity(new DXFArc(new RealPoint(arc2.get(i)[0], arc2.get(i)[1], 0), arc2.get(i)[2], Math.toRadians(arc2.get(i)[3]), Math.toRadians(arc2.get(i)[4]), arc2.get(i)[5]==1?true:false, graphics)); //false

                dxfDocument.addEntity(new DXFArc(new RealPoint(arc3.get(i)[0], arc3.get(i)[1], 0), arc3.get(i)[2], Math.toRadians(arc3.get(i)[3]), Math.toRadians(arc3.get(i)[4]), arc3.get(i)[5]==1?true:false, graphics)); //true

                dxfDocument.addEntity(new DXFArc(new RealPoint(arc4.get(i)[0], arc4.get(i)[1], 0), arc4.get(i)[2], Math.toRadians(arc4.get(i)[3]), Math.toRadians(arc4.get(i)[4]), arc4.get(i)[5]==1?true:false, graphics)); //true

                dxfDocument.addEntity(new DXFArc(new RealPoint(arc5.get(i)[0], arc5.get(i)[1], 0), arc5.get(i)[2], Math.toRadians(arc5.get(i)[3]), Math.toRadians(arc5.get(i)[4]), arc5.get(i)[5]==1?true:false, graphics)); //false

                // прямой участок профиля
                dxfDocument.addEntity(new DXFLine(new RealPoint(line2.get(i)[0],line2.get(i)[1], 0), new RealPoint(line2.get(i)[2],line2.get(i)[3], 0), graphics));

                dxfDocument.addEntity(new DXFLine(new RealPoint(line3.get(i)[0], line3.get(i)[1], 0), new RealPoint(line3.get(i)[2], line3.get(i)[3], 0), graphics));

                // прямой участок профиля на кончике зуба
                dxfDocument.addEntity(new DXFLine(new RealPoint(line4.get(i)[0], line4.get(i)[1], 0), new RealPoint(line4.get(i)[2], line4.get(i)[3], 0), graphics));
                
                //Рисование дополнительных отверстий                
                dxfDocument.addEntity(new DXFCircle(new RealPoint(circle2.get(i)[0],circle2.get(i)[1],0),circle2.get(i)[2],graphics));                                
                //
            }
        }
        else if(typeDetail==102){
            //Шестеренка гусеницы
            //Исходные данные: A,B,R1,R2,D=R3,R=R4,d1,N
            double[] circle1=new double[]{center[0],center[1],D/2};
            TreeMap<Double,double[]> circle2=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> line1=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> line2=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> line3=new TreeMap<Double,double[]>();
            TreeMap<Double,double[]> arc1=new TreeMap<Double,double[]>();
            
            //Создание отверстий
            double mainPercent=(double)100/N;
            Double prevAngle=null,firstAngle=null,lastAngle=null;
            for(double percent=mainPercent;percent<=100;percent+=mainPercent){
                //Отверстия в верхней левой области
                if(percent<=25){
                    double segmentPercent=percent*4;
                    double angle=Math.round(90*(segmentPercent/100));
                    double x=R*Math.cos(Math.toRadians(90-angle));
                    double y=R*Math.sin(Math.toRadians(90-angle));
                    //dxfDocument.addEntity(new DXFCircle(new RealPoint(center[0]-x,center[1]+y,0),d1/2,graphics));
                    circle2.put(percent,new double[]{center[0]-x,center[1]+y,d1/2});
                    //Головка зуба
                    double xMiddle=center[0]-R1*Math.cos(Math.toRadians(90-angle));
                    double yMiddle=center[1]+R1*Math.sin(Math.toRadians(90-angle));
                    double addX=A/2*Math.cos(Math.toRadians(angle));
                    double addY=A/2*Math.sin(Math.toRadians(angle));
                    double xBegin=xMiddle-addX;
                    double yBegin=yMiddle-addY;
                    double xEnd=xMiddle+addX;
                    double yEnd=yMiddle+addY;
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xBegin,yBegin,0),new RealPoint(xEnd,yEnd,0),graphics));
                    line1.put(percent,new double[]{xBegin,yBegin,xEnd,yEnd});
                    //Рисование зубьев шестеренки
                    xMiddle=center[0]-R2*Math.cos(Math.toRadians(90-angle));
                    yMiddle=center[1]+R2*Math.sin(Math.toRadians(90-angle));
                    addX=B/2*Math.cos(Math.toRadians(angle));
                    addY=B/2*Math.sin(Math.toRadians(angle));
                    
                    double x1=center[0];
                    double y1=center[1];
                    double x2=xMiddle-addX;
                    double y2=yMiddle-addY;
                    double xNapr=center[0]+R2;
                    double yNapr=center[1];
                    //Нахождение угла
                    double x01=x1-xNapr;
                    double x02=x1-x2;
                    double y01=y1-yNapr;
                    double y02=y1-y2;
                    double D1=Math.sqrt(x01*x01+y01*y01);
                    double D2=Math.sqrt(x02*x02+y02*y02);
                    double angle1=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                    if(y02>0)angle1=-angle1;
                    //
                    double xLine=center[0]+R2*Math.cos(Math.toRadians(angle1));
                    double yLine=center[1]+R2*Math.sin(Math.toRadians(angle1));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xBegin,yBegin,0),new RealPoint(xLine,yLine,0),graphics));
                    line2.put(percent,new double[]{xBegin,yBegin,xLine,yLine});
                    
                    x2=xMiddle+addX;
                    y2=yMiddle+addY;
                    x02=x1-x2;
                    y02=y1-y2;
                    D2=Math.sqrt(x02*x02+y02*y02);
                    double angle2=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                    if(y02>0)angle2=-angle2;
                    
                    xLine=center[0]+R2*Math.cos(Math.toRadians(angle2));
                    yLine=center[1]+R2*Math.sin(Math.toRadians(angle2));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xEnd,yEnd,0),new RealPoint(xLine,yLine,0),graphics));
                    line3.put(percent,new double[]{xEnd,yEnd,xLine,yLine});
                    //
                    if(prevAngle==null){
                        prevAngle=angle1;
                        firstAngle=angle2;
                        continue;
                    }
                    //dxfDocument.addEntity(new DXFArc(new RealPoint(center[0],center[1],0),R2,Math.toRadians(angle2),Math.toRadians(prevAngle),false,graphics));
                    arc1.put(percent,new double[]{center[0],center[1],R2,angle2,prevAngle,0});
                    prevAngle=angle1;
                    lastAngle=angle1;
                    //
                }
                //Отверстия в нижней левой области
                else if(percent>25 && percent<=50){
                    double segment=percent-25;
                    double segmentPercent=segment*4;
                    double angle=Math.round(90*(segmentPercent)/100);
                    double x=0;
                    if(angle!=0)x=R*Math.cos(Math.toRadians(angle));
                    double y=R*Math.sin(Math.toRadians(angle));
                    //dxfDocument.addEntity(new DXFCircle(new RealPoint(center[0]-x,center[1]-y,0),d1/2,graphics));
                    circle2.put(percent,new double[]{center[0]-x,center[1]-y,d1/2});
                    //Головка зуба
                    double xMiddle=center[0]-R1*Math.cos(Math.toRadians(angle));
                    double yMiddle=center[1]-R1*Math.sin(Math.toRadians(angle));
                    double addX=A/2*Math.cos(Math.toRadians(90-angle));
                    double addY=A/2*Math.sin(Math.toRadians(90-angle));
                    double xBegin=xMiddle-addX;
                    double yBegin=yMiddle+addY;
                    double xEnd=xMiddle+A/2*Math.cos(Math.toRadians(90-angle));
                    double yEnd=yMiddle-A/2*Math.sin(Math.toRadians(90-angle));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xBegin,yBegin,0),new RealPoint(xEnd,yEnd,0),graphics));
                    line1.put(percent,new double[]{xBegin,yBegin,xEnd,yEnd});
                    //Рисование зубьев шестеренки
                    xMiddle=center[0]-R2*Math.cos(Math.toRadians(angle));
                    yMiddle=center[1]-R2*Math.sin(Math.toRadians(angle));
                    addX=B/2*Math.cos(Math.toRadians(90-angle));
                    addY=B/2*Math.sin(Math.toRadians(90-angle));
                    
                    double x1=center[0];
                    double y1=center[1];
                    double x2=xMiddle-addX;
                    double y2=yMiddle+addY;
                    double xNapr=center[0]+R2;
                    double yNapr=center[1];
                    //Нахождение угла
                    double x01=x1-xNapr;
                    double x02=x1-x2;
                    double y01=y1-yNapr;
                    double y02=y1-y2;
                    double D1=Math.sqrt(x01*x01+y01*y01);
                    double D2=Math.sqrt(x02*x02+y02*y02);
                    double angle1=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                    if(y02>0)angle1=-angle1;
                    //
                    double xLine=center[0]+R2*Math.cos(Math.toRadians(angle1));
                    double yLine=center[1]+R2*Math.sin(Math.toRadians(angle1));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xBegin,yBegin,0),new RealPoint(xLine,yLine,0),graphics));
                    line2.put(percent,new double[]{xBegin,yBegin,xLine,yLine});
                    
                    x2=xMiddle+addX;
                    y2=yMiddle-addY;
                    x02=x1-x2;
                    y02=y1-y2;
                    D2=Math.sqrt(x02*x02+y02*y02);
                    double angle2=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                    if(y02>0)angle2=-angle2;
                    
                    xLine=center[0]+R2*Math.cos(Math.toRadians(angle2));
                    yLine=center[1]+R2*Math.sin(Math.toRadians(angle2));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xEnd,yEnd,0),new RealPoint(xLine,yLine,0),graphics));
                    line3.put(percent,new double[]{xEnd,yEnd,xLine,yLine});
                    //
                    if(prevAngle==null){
                        prevAngle=angle2;
                        firstAngle=angle1;
                        continue;
                    }
                    //dxfDocument.addEntity(new DXFArc(new RealPoint(center[0],center[1],0),R2,Math.toRadians(angle1),Math.toRadians(prevAngle),false,graphics));
                    arc1.put(percent,new double[]{center[0],center[1],R2,angle1,prevAngle,0});
                    
                    prevAngle=angle2;
                    lastAngle=angle2;
                    //
                }
                //Отверстия в нижней правой области
                else if(percent>50 && percent<=75){
                    double segment=percent-50;
                    double segmentPercent=segment*4;
                    double angle=Math.round(90*(segmentPercent/100));
                    double x=R*Math.cos(Math.toRadians(90-angle));
                    double y=R*Math.sin(Math.toRadians(90-angle));
                    //dxfDocument.addEntity(new DXFCircle(new RealPoint(center[0]+x,center[1]-y,0),d1/2,graphics));
                    circle2.put(percent,new double[]{center[0]+x,center[1]-y,d1/2});
                    //Головка зуба
                    double xMiddle=center[0]+R1*Math.cos(Math.toRadians(90-angle));
                    double yMiddle=center[1]-R1*Math.sin(Math.toRadians(90-angle));
                    double addX=A/2*Math.cos(Math.toRadians(angle));
                    double addY=A/2*Math.sin(Math.toRadians(angle));
                    double xBegin=xMiddle-addX;
                    double yBegin=yMiddle-addY;
                    double xEnd=xMiddle+addX;
                    double yEnd=yMiddle+addY;
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xBegin,yBegin,0),new RealPoint(xEnd,yEnd,0),graphics));
                    line1.put(percent,new double[]{xBegin,yBegin,xEnd,yEnd});
                    //Рисование зубьев шестеренки
                    xMiddle=center[0]+R2*Math.cos(Math.toRadians(90-angle));
                    yMiddle=center[1]-R2*Math.sin(Math.toRadians(90-angle));
                    addX=B/2*Math.cos(Math.toRadians(angle));
                    addY=B/2*Math.sin(Math.toRadians(angle));
                    
                    double x1=center[0];
                    double y1=center[1];
                    double x2=xMiddle-addX;
                    double y2=yMiddle-addY;
                    double xNapr=center[0]+R2;
                    double yNapr=center[1];
                    //Нахождение угла
                    double x01=x1-xNapr;
                    double x02=x1-x2;
                    double y01=y1-yNapr;
                    double y02=y1-y2;
                    double D1=Math.sqrt(x01*x01+y01*y01);
                    double D2=Math.sqrt(x02*x02+y02*y02);
                    double angle1=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                    if(y02>0)angle1=-angle1;
                    //
                    double xLine=center[0]+R2*Math.cos(Math.toRadians(angle1));
                    double yLine=center[1]+R2*Math.sin(Math.toRadians(angle1));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xBegin,yBegin,0),new RealPoint(xLine,yLine,0),graphics));
                    line2.put(percent,new double[]{xBegin,yBegin,xLine,yLine});
                    
                    x2=xMiddle+addX;
                    y2=yMiddle+addY;
                    x02=x1-x2;
                    y02=y1-y2;
                    D2=Math.sqrt(x02*x02+y02*y02);
                    double angle2=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                    if(y02>0)angle2=-angle2;
                    
                    xLine=center[0]+R2*Math.cos(Math.toRadians(angle2));
                    yLine=center[1]+R2*Math.sin(Math.toRadians(angle2));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xEnd,yEnd,0),new RealPoint(xLine,yLine,0),graphics));
                    line3.put(percent,new double[]{xEnd,yEnd,xLine,yLine});
                    //
                    if(prevAngle==null){
                        prevAngle=angle2;
                        firstAngle=angle1;
                        continue;
                    }
                    //dxfDocument.addEntity(new DXFArc(new RealPoint(center[0],center[1],0),R2,Math.toRadians(angle1),Math.toRadians(prevAngle),false,graphics));
                    arc1.put(percent,new double[]{center[0],center[1],R2,angle1,prevAngle,0});
                    prevAngle=angle2;
                    lastAngle=angle2;
                    //
                }
                //Отверстия в верхней правой области
                else{
                    double segment=percent-75;
                    double segmentPercent=segment*4;
                    double angle=Math.round(90*(segmentPercent/100));
                    double x=0;
                    if(angle!=90)x=R*Math.cos(Math.toRadians(angle));
                    double y=R*Math.sin(Math.toRadians(angle));
                    //dxfDocument.addEntity(new DXFCircle(new RealPoint(center[0]+x,center[1]+y,0),d1/2,graphics));
                    circle2.put(percent,new double[]{center[0]+x,center[1]+y,d1/2});
                    //Головка зуба
                    double xMiddle=center[0]+R1*Math.cos(Math.toRadians(angle));
                    double yMiddle=center[1]+R1*Math.sin(Math.toRadians(angle));
                    double addX=A/2*Math.cos(Math.toRadians(90-angle));
                    double addY=A/2*Math.sin(Math.toRadians(90-angle));
                    double xBegin=xMiddle-addX;
                    double yBegin=yMiddle+addY;
                    double xEnd=xMiddle+addX;
                    double yEnd=yMiddle-addY;
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xMiddle-addX,yMiddle+addY,0),new RealPoint(xMiddle+addX,yMiddle-addY,0),graphics));
                    line1.put(percent,new double[]{xBegin,yBegin,xEnd,yEnd});
                    
                    //Рисование зубьев шестеренки
                    xMiddle=center[0]+R2*Math.cos(Math.toRadians(angle));
                    yMiddle=center[1]+R2*Math.sin(Math.toRadians(angle));
                    addX=B/2*Math.cos(Math.toRadians(90-angle));
                    addY=B/2*Math.sin(Math.toRadians(90-angle));
                    
                    double x1=center[0];
                    double y1=center[1];
                    double x2=xMiddle-addX;
                    double y2=yMiddle+addY;
                    double xNapr=center[0]+R2;
                    double yNapr=center[1];
                    //Нахождение угла
                    double x01=x1-xNapr;
                    double x02=x1-x2;
                    double y01=y1-yNapr;
                    double y02=y1-y2;
                    double D1=Math.sqrt(x01*x01+y01*y01);
                    double D2=Math.sqrt(x02*x02+y02*y02);
                    double angle1=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                    if(y02>0)angle1=-angle1;
                    //
                    double xLine=center[0]+R2*Math.cos(Math.toRadians(angle1));
                    double yLine=center[1]+R2*Math.sin(Math.toRadians(angle1));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xBegin,yBegin,0),new RealPoint(xLine,yLine,0),graphics));
                    line2.put(percent,new double[]{xBegin,yBegin,xLine,yLine});
                    
                    x2=xMiddle+addX;
                    y2=yMiddle-addY;
                    x02=x1-x2;
                    y02=y1-y2;
                    D2=Math.sqrt(x02*x02+y02*y02);
                    double angle2=Math.toDegrees(Math.acos((x01*x02+y01*y02)/(D1*D2)));
                    if(y02>0)angle2=-angle2;
                    
                    xLine=center[0]+R2*Math.cos(Math.toRadians(angle2));
                    yLine=center[1]+R2*Math.sin(Math.toRadians(angle2));
                    //dxfDocument.addEntity(new DXFLine(new RealPoint(xEnd,yEnd,0),new RealPoint(xLine,yLine,0),graphics));
                    line3.put(percent,new double[]{xEnd,yEnd,xLine,yLine});
                    //
                    if(prevAngle==null){
                        prevAngle=angle1;
                        firstAngle=angle2;
                        continue;
                    }
                    //dxfDocument.addEntity(new DXFArc(new RealPoint(center[0],center[1],0),R2,Math.toRadians(angle2),Math.toRadians(prevAngle),false,graphics));
                    arc1.put(percent,new double[]{center[0],center[1],R2,angle2,prevAngle,0});
                    prevAngle=angle1;
                    lastAngle=angle1;
                    //
                }
                //
            }
            //dxfDocument.addEntity(new DXFArc(new RealPoint(center[0],center[1],0),R2,Math.toRadians(firstAngle),Math.toRadians(lastAngle),false,graphics));
            double[] arc2=new double[]{center[0],center[1],R2,firstAngle,lastAngle,0};
            
            //dxfDocument.addEntity(new DXFCircle(new RealPoint(center[0],center[1],0),D/2,graphics));
            
            primitives=new Object[]{circle1,arc2,circle2,line1,line2,line3,arc1,N};
            if(isPdf)createPDF(primitives,typeDetail);
            
            dxfDocument.addEntity(new DXFCircle(new RealPoint(circle1[0],circle1[1],0),circle1[2],graphics));
            dxfDocument.addEntity(new DXFArc(new RealPoint(arc2[0],arc2[1],0),arc2[2],Math.toRadians(arc2[3]),Math.toRadians(arc2[4]),false,graphics));
            for(double percent=mainPercent;percent<=100;percent+=mainPercent){
                dxfDocument.addEntity(new DXFCircle(new RealPoint(circle2.get(percent)[0],circle2.get(percent)[1],0),circle2.get(percent)[2],graphics));
                dxfDocument.addEntity(new DXFLine(new RealPoint(line1.get(percent)[0],line1.get(percent)[1],0),
                    new RealPoint(line1.get(percent)[2],line1.get(percent)[3],0),graphics));
                dxfDocument.addEntity(new DXFLine(new RealPoint(line2.get(percent)[0],line2.get(percent)[1],0),
                    new RealPoint(line2.get(percent)[2],line2.get(percent)[3],0),graphics));
                dxfDocument.addEntity(new DXFLine(new RealPoint(line3.get(percent)[0],line3.get(percent)[1],0),
                    new RealPoint(line3.get(percent)[2],line3.get(percent)[3],0),graphics));
                if(percent+mainPercent>100)continue;
                dxfDocument.addEntity(new DXFArc(new RealPoint(arc1.get(percent+mainPercent)[0],arc1.get(percent+mainPercent)[1],0),
                    arc1.get(percent+mainPercent)[2],Math.toRadians(arc1.get(percent+mainPercent)[3]),
                    Math.toRadians(arc1.get(percent+mainPercent)[4]),false,graphics));
            }
            //
        }
        else{
            System.out.println("Указан некорректный номер детали");
            System.exit(0);
        }
            
        String dxtText = dxfDocument.toDXFString();
        try (FileWriter fileWriter = new FileWriter("dxffile.dxf")) {
            fileWriter.write(dxtText);
            fileWriter.flush();
            System.out.println("Записан файл dxffile.dxf");
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e);
        }
    }
}
