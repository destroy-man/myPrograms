/*����� 3. ������ 1:
�������� ���������, ������� �������� �������, ��������� � ����������, ��
��� ���, ���� �� ���������� �����. ������������� � ��������� ������� ��������.
�������� � ���������� �������� ������ ���������� � ����� ���������.
*/
class p3_1{
	public static void main(String[] args) throws java.io.IOException{
		int countProbels=0;
		for(;;){
			System.out.print("����������, ������� ����� ������: ");
			char probel,symbol=(char)System.in.read();
			do{
				probel=(char)System.in.read();
				if(probel==' ')countProbels++;
			}while(probel!='\n');
			if(symbol=='.')break;
		}
		System.out.println("���������� ��������: "+countProbels);
	}
}