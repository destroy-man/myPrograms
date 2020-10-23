/*Глава 6. Вопрос 4
Предположим, имеется следующий класс.
	class Test{
		int а;
		Test(int i) { а = i; }
	}
Напишите метод swap () , реализующий обмен содержимым между двумя объектами
типа Test, на которые ссылаются две переменные данного типа.
*/
class p6_4{
	static void swap(){
		Test a=new Test(1);
		Test b=new Test(2);
		int c=a.a;
		a.a=b.a;
		b.a=c;
		System.out.println("a: "+a.a+" b: "+b.a);
	}
	public static void main(String[] args){
		swap();
	}
}
