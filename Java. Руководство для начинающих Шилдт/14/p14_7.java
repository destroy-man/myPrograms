/*Глава 14. Вопрос 7
Создайте блочное лямбда-выражение для вычисления факториала целого числа.
Продемонстрируйте его использование. В качестве функционального интерфейса
используйте интерфейс NumericFunc, который рассматривался в этой главе.
*/
class p14_7{
	public static void main(String[] args){
		NumericFunc nf=(n)->{
			int fact=1;
			for(int i=2;i<=n;i++)
				fact*=i;
			return fact;
		};
		System.out.println("Факториал 4: "+nf.func(4));
	}
}