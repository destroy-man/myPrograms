class RethrowDemo{
	public static void main(String[] args){
		try{
			Rethrow.genException();
		}
		catch(ArrayIndexOutOfBoundsException exc){
			System.out.println("Фатальная ошибка - выполнение программы прервано!");
		}
	}
}