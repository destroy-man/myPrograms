class ThrowDemo{
	public static void main(String[] args){
		try{
			System.out.println("До оператора throw");
			throw new ArithmeticException();
		}
		catch(ArithmeticException exc){
			System.out.println("Исключение перехвачено");
		}
		System.out.println("После блока try/catch");
	}
}