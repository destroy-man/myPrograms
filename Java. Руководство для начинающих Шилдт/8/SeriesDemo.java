class SeriesDemo{
	public static void main(String[] args){
		ByTwos ob=new ByTwos();
		for(int i=0;i<5;i++)
			System.out.println("��������� ��������: "+ob.getNext());
		System.out.println("\n�����");
		ob.reset();
		for(int i=0;i<5;i++)
			System.out.println("��������� ��������: "+ob.getNext());
		System.out.println("\n��������� ��������: 100");
		ob.setStart(100);
		for(int i=0;i<5;i++)
			System.out.println("��������� ��������: "+ob.getNext());
	}
}