class Overload{
	void ovlDemo(){
		System.out.println("Без параметров");
	}
	void ovlDemo(int a){
		System.out.println("Один параметр: "+a);
	}
	int ovlDemo(int a,int b){
		System.out.println("Два параметра: "+a+" "+b);
		return a+b;
	}
	double ovlDemo(double a,double b){
		System.out.println("Два параметра типа double: "+a+" "+b);
		return a+b;
	}
}