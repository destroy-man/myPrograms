/*����� 12. ������ 3
�������� ��� ������������ ���� ������������ ���������, � ������� ����� values() ������������ ��� ����������� 
������ �������� � �� ��������.
enum Tools{
	SCREWDRIVER,WRENCH,HAMMER,PLIERS
}
*/
enum Tools{
	SCREWDRIVER,WRENCH,HAMMER,PLIERS
}
class p12_3{
	public static void main(String[] args){
		for(Tools t:Tools.values())
			System.out.println(t.ordinal()+" "+t);
	}
}