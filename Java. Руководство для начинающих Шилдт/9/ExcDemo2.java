class ExcDemo2{
	public static void main(String[] args){
		try{
			ExcTest.genException();
		}
		catch(ArrayIndexOutOfBoundsException exc){
			System.out.println("����� �� ������� �������");
		}
		System.out.println("����� ��������� catch");
	}
}