class ThrowDemo{
	public static void main(String[] args){
		try{
			System.out.println("�� ��������� throw");
			throw new ArithmeticException();
		}
		catch(ArithmeticException exc){
			System.out.println("���������� �����������");
		}
		System.out.println("����� ����� try/catch");
	}
}