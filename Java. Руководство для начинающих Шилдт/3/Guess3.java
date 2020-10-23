class Guess3{
	public static void main(String[] args) throws java.io.IOException{
		char ch,answer='S';
		System.out.println("Задумана буква из диапозона A-Z.");
		System.out.print("Попытайтесь ее угадать: ");
		ch=(char)System.in.read();
		if(ch==answer)System.out.println("** Правильно! **");
		else{
			System.out.print("...Извините, нужная буква находится ");
			if(ch<answer)System.out.println("ближе к концу алфавита");
			else System.out.println("ближе к началу алфавита");
		}
	}
}