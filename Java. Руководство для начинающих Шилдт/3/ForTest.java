class ForTest{
	public static void main(String[] args) throws java.io.IOException{
		System.out.println("��� ��������� ������� ������� S");
		for(int i=0;(char)System.in.read()!='S';i++)
			System.out.println("������ #"+i);
	}
}