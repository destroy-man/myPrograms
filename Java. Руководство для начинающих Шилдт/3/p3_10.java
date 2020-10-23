/*Глава 3. Вопрос 10:
Код ASCII символов нижнего регистра отличается от кода соответствующих символов
верхнего регистра на величину 32. Следовательно, для преобразования
строчной буквы в прописную нужно уменьшить ее код на 32. Используйте это обстоятельство
для написания программы, осуществляющей получение символов с
клавиатуры. При выводе результатов данная программа должна преобразовывать
строчные буквы в прописные, а прописные - в строчные. Остальные символы не
должны изменяться. Работа программы должна завершаться после того, как пользователь
введет с клавиатуры точку. И наконец, сделайте так, чтобы программа
отображала число символов, для которых бьш изменен регистр.
*/
class p3_10{
	public static void main(String[] args) throws java.io.IOException{
		int countSymbols=0;
		for(;;){
			System.out.print("Введите символы: ");
			char symbols,ch=(char)System.in.read();
			if((int)ch>64 && (int)ch<91){
				ch+=32;
				System.out.print(ch);
				countSymbols++;
			}
			else if((int)ch>96 && (int)ch<123){
				ch-=32;
				System.out.print(ch);
				countSymbols++;
			}
			do{
				symbols=(char)System.in.read();
				if((int)symbols>64 && (int)symbols<91){
					symbols+=32;
					System.out.print(symbols);
					countSymbols++;
				}
				else if((int)symbols>96 && (int)symbols<123){
					symbols-=32;
					System.out.print(symbols);
					countSymbols++;
				}
				else System.out.print(symbols);
			}while(symbols!='\n');
			if(ch=='.')break;
			System.out.println();
		}
		System.out.println("Число измененных символов: "+countSymbols);
	}
}
