import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
class Quadratic{
	public static void main(String[] args){
		double a,b,c,x;
		a=4;
		b=1;
		c=-3;
		x=(-b+sqrt(pow(b,2)-4*a*c))/(2*a);
		System.out.println("Первый корень: "+x);
		x=(-b-sqrt(pow(b,2)-4*a*c))/(2*a);
		System.out.println("Второй корень: "+x);
	}
}