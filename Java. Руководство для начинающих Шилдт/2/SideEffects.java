class SideEffects{
	public static void main(String[] args){
		int i;
		i=0;
		if(false & (++i<100))
			System.out.println("��� ������ �� ����� ������������");
		System.out.println("�������� if �����������: "+i);
		if(false && (++i<100))
			System.out.println("��� ������ �� ����� ������������");
		System.out.println("�������� if �����������: "+i);
	}
}