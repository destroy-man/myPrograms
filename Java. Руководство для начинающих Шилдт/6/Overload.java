class Overload{
	void ovlDemo(){
		System.out.println("��� ����������");
	}
	void ovlDemo(int a){
		System.out.println("���� ��������: "+a);
	}
	int ovlDemo(int a,int b){
		System.out.println("��� ���������: "+a+" "+b);
		return a+b;
	}
	double ovlDemo(double a,double b){
		System.out.println("��� ��������� ���� double: "+a+" "+b);
		return a+b;
	}
}