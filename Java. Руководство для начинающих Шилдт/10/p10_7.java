/*Глава 10. Вопрос 7
Напишите программу для копирования текстовых файлов. Видоизмените её таким образом, чтобы все пробелы заменялись 
дефисами. Используйте при написании программы классы, представляющие байтовые потоки, а также традиционный 
способ закрытия файла явным вызовом метода close().
*/
import java.io.*;
class p10_7{
	public static void main(String[] args){
		char ch;
		int n;
		try{
			FileInputStream fin=new FileInputStream(args[0]);
			FileOutputStream fout=new FileOutputStream(args[1]);
			do{
				n=fin.read();
				if(n!=-1){
					ch=(char)n;
					if(ch==' ')ch='-';
					fout.write(ch);
				}
			}while(n!=-1);
			fin.close();
			fout.close();			
		}
		catch(IOException exc){
			System.out.println("Возникла ошибка при обработке файлов: "+exc);
		}
	}
}