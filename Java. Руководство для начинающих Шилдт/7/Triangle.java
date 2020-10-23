class Triangle extends TwoDShape{
	private String style;
	Triangle(){
		super();
		style="none";
	}
	Triangle(String s,double w,double h){
		super(w,h,"�����������");
		style=s;
	}
	Triangle(double x){
		super(x,"�����������");
		style="�����������";
	}
	Triangle(Triangle ob){
		super(ob);
		style=ob.style;
	}
	double area(){
		return getWidth()*getHeight()/2;
	}
	void showStyle(){
		System.out.println("����������� "+style);
	}
}