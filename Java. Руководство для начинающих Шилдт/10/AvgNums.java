import java.io.*;
class AvgNums{
	public static void main(String[] args) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String str;
		int n;
		double sum=0.0;
		double avg,t;
		System.out.print("������� ����� �� �������: ");
		str=br.readLine();
		try{
			n=Integer.parseInt(str);
		}
		catch(NumberFormatException exc){
			System.out.println("�������� ������");
			n=0;
		}
		System.out.println("���� "+n+" ��������");		
		for(int i=0;i<n;i++){
			System.out.print(": ");
			str=br.readLine();
			try{
				t=Double.parseDouble(str);
			}
			catch(NumberFormatException exc){
				System.out.println("�������� ������");
				t=0.0;
			}
			sum+=t;
		}
		avg=sum/n;
		System.out.println("������� ��������: "+avg);
	}
}