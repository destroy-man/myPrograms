/*Глава 3. Вопрос 1:
Напишите программу, которая получает символы, введенные с клавиатуры, до
тех пор, пока не встретится точка. Предусмотрите в программе счетчик пробелов.
Сведения о количестве пробелов должны выводиться в конце программы.
*/
class p3_1{
	public static void main(String[] args) throws java.io.IOException{
		int countProbels=0;
		for(;;){
			System.out.print("Пожалуйста, введите любой символ: ");
			char probel,symbol=(char)System.in.read();
			do{
				probel=(char)System.in.read();
				if(probel==' ')countProbels++;
			}while(probel!='\n');
			if(symbol=='.')break;
		}
		System.out.println("Количество пробелов: "+countProbels);
	}
}