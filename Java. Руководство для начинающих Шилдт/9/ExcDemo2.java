class ExcDemo2{
	public static void main(String[] args){
		try{
			ExcTest.genException();
		}
		catch(ArrayIndexOutOfBoundsException exc){
			System.out.println("Выход за границы массива");
		}
		System.out.println("После оператора catch");
	}
}