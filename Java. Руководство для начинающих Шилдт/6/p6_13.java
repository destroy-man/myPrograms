/*����� 6. ������ 13
�������� ����� sum () , ����������� ������ ���������� ���������� ����� �
��������������� ��� ������������ ������������ ��� �������� ���� int. �����
������ ���������� ��������� ������������. ����������������� ������ �����
������.
*/
class p6_13{
	public static void main(String[] args){
		int summa=sum(1,2,3,4);
		System.out.println(summa);
	}
	public static int sum(int...n){
		int summa=0;
		for(int i=0;i<n.length;i++)
			summa+=n[i];
		return summa;
	}
}