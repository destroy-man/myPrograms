/*Глава 10. Вопрос 8
Перепишите программу, созданную в предыдущем пункте, таким образом, чтобы в ней использовались классы, 
представляющие символьные потоки. На этот раз воспользуйтесь операторм try с ресурсами для автоматического 
закрытия файла.
*/
import java.io.*;
class p10_8{
	public static void main(String[] args){
		String str;
		try(BufferedReader reader=new BufferedReader(new FileReader(args[0]));
		    BufferedWriter writer=new BufferedWriter(new FileWriter(args[1]))){
			do{
				str=reader.readLine();
				if(str!=null){
					str=str.replaceAll(" ","-");
					writer.write(str);
					writer.newLine();
				}
			}while(str!=null);			
		}
		catch(IOException exc){
			System.out.println("Возникла ошибка при обработке файлов: "+exc);
		}
	}
}