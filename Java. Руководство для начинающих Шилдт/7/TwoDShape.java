abstract class TwoDShape{
	private double width;
	private double height;
	private String name;
	TwoDShape(){
		width=height=0.0;
		name="none";
	}
	TwoDShape(double w,double h,String n){
		width=w;
		height=h;
		name=n;
	}
	TwoDShape(double x,String n){
		width=height=x;
		name=n;
	}
	TwoDShape(TwoDShape ob){
		width=ob.width;
		height=ob.height;
		name=ob.name;
	}
	double getWidth(){return width;}
	double getHeight(){return height;}
	void setWidth(double w){width=w;}
	void setHeight(double h){height=h;}
	String getName(){return name;}
	void showDim(){
		System.out.println("Ўирина и высота - "+width+" и "+height);
	}
	abstract double area();
}