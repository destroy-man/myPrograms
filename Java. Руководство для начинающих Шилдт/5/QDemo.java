class QDemo{
	public static void main(String[] args){
		Queue bigQ=new Queue(100);
		Queue smallQ=new Queue(4);
		Object ch;
		int i;
		System.out.println("������������� ������� bigQ ��� ���������� ��������");
		for(i=0;i<26;i++)
			bigQ.put('A'+i);
		System.out.print("���������� ������� bigQ: ");
		for(i=0;i<26;i++){
			ch=bigQ.get();
			if(!ch.equals(0))System.out.print(ch);
		}
		System.out.println("\n");		
		System.out.println("������������� ������� smallQ ��� ��������� ������");
		for(i=0;i<5;i++){
			System.out.print("������� ���������� "+('Z'-i));
			smallQ.put('Z'-i);
			System.out.println();
		}
		System.out.println();
		System.out.print("���������� smallQ: ");
		for(i=0;i<5;i++){
			ch=smallQ.get();
			if(!ch.equals(0))System.out.print(ch);
		}
	}
}