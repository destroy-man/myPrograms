/*Глава 5. Вопрос 7
Расширьте класс Encode таким образом, чтобы в качестве ключа шифрования использовалась
строка из восьми символов.
*/
class p5_7{
	public static void main(String[] args){
		String msg="This is a test";
		String encmsg="";
		String decmsg="";
		int key=88;
		key<<=1;
		System.out.println(key);
		System.out.print("Исходное сообщение: ");
		System.out.println(msg);
		for(int i=0;i<msg.length();i++)
			encmsg+=(char)(msg.charAt(i)^key);
		System.out.print("Зашифрованное сообщение: ");
		System.out.println(encmsg);
		for(int i=0;i<msg.length();i++)
			decmsg+=(char)(encmsg.charAt(i)^key);
		System.out.print("Дешифрованное сообщение: ");
		System.out.println(decmsg);
	}
}