class UseThrowableMethods{
	public static void main(String[] args){
		try{
			ExcTest.genException();
		}
		catch(ArrayIndexOutOfBoundsException exc){
			System.out.println("����������� ���������: ");
			System.out.println(exc);
			System.out.println("\n���� �������:");
			exc.printStackTrace();
		}
		System.out.println("����� ��������� catch");
	}
}