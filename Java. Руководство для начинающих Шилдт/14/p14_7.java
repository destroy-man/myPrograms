/*����� 14. ������ 7
�������� ������� ������-��������� ��� ���������� ���������� ������ �����.
����������������� ��� �������������. � �������� ��������������� ����������
����������� ��������� NumericFunc, ������� �������������� � ���� �����.
*/
class p14_7{
	public static void main(String[] args){
		NumericFunc nf=(n)->{
			int fact=1;
			for(int i=2;i<=n;i++)
				fact*=i;
			return fact;
		};
		System.out.println("��������� 4: "+nf.func(4));
	}
}