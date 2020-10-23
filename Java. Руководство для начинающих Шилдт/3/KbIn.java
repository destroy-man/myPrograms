class KbIn{
	public static void main(String[] args) throws java.io.IOException{
		char ch;
		System.out.print("Нажмите нужную клавишу, а затем нажмите ENTER: ");
		ch=(char)System.in.read();
		System.out.println("Вы нажали клавишу "+ch);
	}
}