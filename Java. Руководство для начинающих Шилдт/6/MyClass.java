class MyClass{
	int x;
	MyClass(){
		System.out.println("������ MyClass().");
		x=0;
	}
	MyClass(int i){
		System.out.println("������ MyClass(int).");
		x=i;
	}
	MyClass(double d){
		System.out.println("������ MyClass(double).");
		x=(int)d;
	}
	MyClass(int i,int j){
		System.out.println("������ MyClass(int,int).");
		x=i*j;
	}
}