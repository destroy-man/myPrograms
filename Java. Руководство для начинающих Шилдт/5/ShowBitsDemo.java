class ShowBitsDemo{
	public static void main(String[] args){
		ShowBits b=new ShowBits(8);
		ShowBits i=new ShowBits(32);
		ShowBits li=new ShowBits(64);
		System.out.println("123 � �������� �������������: ");
		b.show(123);
		System.out.println("\n87987 � �������� �������������: ");
		i.show(87987);
		System.out.println("\n237658768 � �������� �������������: ");
		li.show(237658768);
		System.out.println("\n������� 8 ����� ����� 87987 � �������� �������������: ");
		b.show(87987);
	}
}