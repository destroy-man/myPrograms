/*����� 10. ������ 7
�������� ��������� ��� ����������� ��������� ������. ������������ � ����� �������, ����� ��� ������� ���������� 
��������. ����������� ��� ��������� ��������� ������, �������������� �������� ������, � ����� ������������ 
������ �������� ����� ����� ������� ������ close().
*/
import java.io.*;
class p10_7{
	public static void main(String[] args){
		char ch;
		int n;
		try{
			FileInputStream fin=new FileInputStream(args[0]);
			FileOutputStream fout=new FileOutputStream(args[1]);
			do{
				n=fin.read();
				if(n!=-1){
					ch=(char)n;
					if(ch==' ')ch='-';
					fout.write(ch);
				}
			}while(n!=-1);
			fin.close();
			fout.close();			
		}
		catch(IOException exc){
			System.out.println("�������� ������ ��� ��������� ������: "+exc);
		}
	}
}