/*����� 6. ������ 4
�����������, ������� ��������� �����.
	class Test{
		int �;
		Test(int i) { � = i; }
	}
�������� ����� swap () , ����������� ����� ���������� ����� ����� ���������
���� Test, �� ������� ��������� ��� ���������� ������� ����.
*/
class p6_4{
	static void swap(){
		Test a=new Test(1);
		Test b=new Test(2);
		int c=a.a;
		a.a=b.a;
		b.a=c;
		System.out.println("a: "+a.a+" b: "+b.a);
	}
	public static void main(String[] args){
		swap();
	}
}
