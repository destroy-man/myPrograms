class Triangle extends TwoDShape{
	private String style;
	Triangle(){
		super();
		style="none";
	}
	Triangle(String s,double w,double h){
		super(w,h,"треугольник");
		style=s;
	}
	Triangle(double x){
		super(x,"треугольник");
		style="закрашенный";
	}
	Triangle(Triangle ob){
		super(ob);
		style=ob.style;
	}
	double area(){
		return getWidth()*getHeight()/2;
	}
	void showStyle(){
		System.out.println("Треугольник "+style);
	}
}