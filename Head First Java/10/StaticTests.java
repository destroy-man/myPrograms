public class StaticTests extends StaticSuper{
	static int rand;
	static{
		rand=(int)(Math.random()*6);
		System.out.println("����������� ���� "+rand);
	}
	StaticTests(){
		System.out.println("�����������");
	}
	public static void main(String[] args){
		System.out.println("������ main");
		StaticTests st=new StaticTests();
	}
}