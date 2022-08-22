/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Spline3D extends BasicSpline{
   private ArrayList<Point> points;
   
   private ArrayList<Cubic> xCubics;
   private ArrayList<Cubic> yCubics;
   private ArrayList<Cubic> zCubics;
   
   private static final String vector3DgetXMethodName = "getX";
   private static final String vector3DgetYMethodName = "getY";
   private static final String vector3DgetZMethodName = "getZ";
   
   private Method vector2DgetXMethod;
   private Method vector2DgetYMethod;
   private Method vector2DgetZMethod;
   
   
   private static final Object[] EMPTYOBJ = new Object[] { };
   
   public Spline3D() {
      this.points = new ArrayList<>();

      this.xCubics = new ArrayList<>();
      this.yCubics = new ArrayList<>();
      this.zCubics = new ArrayList<>();
      
      try {
         vector2DgetXMethod = Point.class.getDeclaredMethod(vector3DgetXMethodName, new Class[] { });
         vector2DgetYMethod = Point.class.getDeclaredMethod(vector3DgetYMethodName, new Class[] { });
         vector2DgetZMethod = Point.class.getDeclaredMethod(vector3DgetZMethodName, new Class[] { });
      } catch (SecurityException | NoSuchMethodException e) {
          // TODO Auto-generated catch block
      }
   }
   
   public void addPoint(Point point) {
      this.points.add(point);
   }
   
   public void addPoints(ArrayList<Point> points) {
      this.points.addAll(points);
   }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }
   
   
   
   public ArrayList<Point> getPoints() {
      return points;
   }
   
   public void calcSpline() {
        try {
            calcNaturalCubic(points, vector2DgetXMethod, xCubics);
            calcNaturalCubic(points, vector2DgetYMethod, yCubics);
            calcNaturalCubic(points, vector2DgetZMethod, zCubics);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("");
        }

    }
   
   public Point getPoint(double position) {
      position = position * xCubics.size();
      int      cubicNum = (int) position;
      double   cubicPos = (position - cubicNum);
      
      return new Point(xCubics.get(cubicNum).eval(cubicPos),
                     yCubics.get(cubicNum).eval(cubicPos),
                     zCubics.get(cubicNum).eval(cubicPos));
   }
   
   public ArrayList<Point> getPointsNew(int numPoint){
       ArrayList<Point> pNew=new ArrayList<>();
       if (numPoint<=0){
           numPoint=points.size()*4;
       }
       double sh=1.0/numPoint;
       for (double i=0; i<1; i+=sh){
           pNew.add(getPoint(i));
       }
       //Добавляем последнююю точку если она отcутствует
       Point pEnd=points.get(points.size()-1);
       if (!pNew.contains(pEnd)){
           pNew.add(pEnd);
       }
       return pNew;
   }
}
