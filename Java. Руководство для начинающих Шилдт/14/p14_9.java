/*����� 14. ������ 9
��������� ���������, ��������� � ���������� 14.1, �������� ������-���������,
������� ������� ��� ������� �� �������� ������ � ���������� ���������.
����������������� ������ ����� ������, ������� ��� ������ changeStr () .
*/
class p14_9{
	static String changeStr(StringFunc sf,String s){
		return sf.func(s);
	}
	public static void main(String[] args){
		String inStr="������-��������� ��������� Java";
		String outStr;
		System.out.println("������� ������: "+inStr);
		outStr=changeStr((str)->str.replace(" ",""),inStr);
		System.out.println("������ � ���������� ��������� ���������: "+outStr);
	}
}