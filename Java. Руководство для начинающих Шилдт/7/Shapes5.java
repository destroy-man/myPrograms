class Shapes5{
	public static void main(String[] args){
		Triangle t1=new Triangle();
		Triangle t2=new Triangle("���������",8.0,12.0);
		Triangle t3=new Triangle(4.0);
		t1=t2;
		System.out.println("���������� � t1: ");
		t1.showStyle();
		t1.showDim();
		System.out.println("������� - "+t1.area());
		System.out.println();
		System.out.println("���������� � t2: ");
		t2.showStyle();
		t2.showDim();
		System.out.println("������� - "+t2.area());
		System.out.println();
		System.out.println("���������� � t3: ");
		t3.showStyle();
		t3.showDim();
		System.out.println("������� - "+t3.area());
		System.out.println();
	}
}