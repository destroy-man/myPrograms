/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.List;

public class Point {
    double x, y, z;

    public Point() {
    }

    public Point(double[] p) {
        this.x = p[0];
        this.y = p[1];
        this.z = p[2];
    }
    
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    
    public boolean equalsPoint(Point p) {
        if (x==p.x&& y==p.y && z==p.z){
            return true;
        }
        return false;
    }
    
    

    @Override
    public String toString() {
        return x+" "+y+" "+z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    
    public double[] getPoint(){
        return new double[] {x,y,z};
    }
    
    public List<Double> getPointL(){
        return Arrays.asList(x,y,z);
    }
    
    
}
