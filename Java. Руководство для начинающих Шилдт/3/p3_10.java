/*����� 3. ������ 10:
��� ASCII �������� ������� �������� ���������� �� ���� ��������������� ��������
�������� �������� �� �������� 32. �������������, ��� ��������������
�������� ����� � ��������� ����� ��������� �� ��� �� 32. ����������� ��� ��������������
��� ��������� ���������, �������������� ��������� �������� �
����������. ��� ������ ����������� ������ ��������� ������ ���������������
�������� ����� � ���������, � ��������� - � ��������. ��������� ������� ��
������ ����������. ������ ��������� ������ ����������� ����� ����, ��� ������������
������ � ���������� �����. � �������, �������� ���, ����� ���������
���������� ����� ��������, ��� ������� ��� ������� �������.
*/
class p3_10{
	public static void main(String[] args) throws java.io.IOException{
		int countSymbols=0;
		for(;;){
			System.out.print("������� �������: ");
			char symbols,ch=(char)System.in.read();
			if((int)ch>64 && (int)ch<91){
				ch+=32;
				System.out.print(ch);
				countSymbols++;
			}
			else if((int)ch>96 && (int)ch<123){
				ch-=32;
				System.out.print(ch);
				countSymbols++;
			}
			do{
				symbols=(char)System.in.read();
				if((int)symbols>64 && (int)symbols<91){
					symbols+=32;
					System.out.print(symbols);
					countSymbols++;
				}
				else if((int)symbols>96 && (int)symbols<123){
					symbols-=32;
					System.out.print(symbols);
					countSymbols++;
				}
				else System.out.print(symbols);
			}while(symbols!='\n');
			if(ch=='.')break;
			System.out.println();
		}
		System.out.println("����� ���������� ��������: "+countSymbols);
	}
}
