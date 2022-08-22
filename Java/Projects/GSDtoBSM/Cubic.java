/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Cubic {
   private double a,b,c,d;
   
   public Cubic(double a, double b, double c, double d) {
      this.a =a;
      this.b =b;
      this.c =c;
      this.d =d;
   }
   
   public double eval(double u) { 
      return (((d*u) + c)*u + b)*u + a;   
   }
}
