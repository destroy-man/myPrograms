/*����� 5. ������ 7
��������� ����� Encode ����� �������, ����� � �������� ����� ���������� ��������������
������ �� ������ ��������.
*/
class p5_7{
	public static void main(String[] args){
		String msg="This is a test";
		String encmsg="";
		String decmsg="";
		int key=88;
		key<<=1;
		System.out.println(key);
		System.out.print("�������� ���������: ");
		System.out.println(msg);
		for(int i=0;i<msg.length();i++)
			encmsg+=(char)(msg.charAt(i)^key);
		System.out.print("������������� ���������: ");
		System.out.println(encmsg);
		for(int i=0;i<msg.length();i++)
			decmsg+=(char)(encmsg.charAt(i)^key);
		System.out.print("������������� ���������: ");
		System.out.println(decmsg);
	}
}