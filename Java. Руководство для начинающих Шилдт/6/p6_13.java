/*Глава 6. Вопрос 13
Создайте метод sum () , принимающий список аргументов переменной длины и
предназначенный для суммирования передаваемых ему значений типа int. Метод
должен возвращать результат суммирования. Продемонстрируйте работу этого
метода.
*/
class p6_13{
	public static void main(String[] args){
		int summa=sum(1,2,3,4);
		System.out.println(summa);
	}
	public static int sum(int...n){
		int summa=0;
		for(int i=0;i<n.length;i++)
			summa+=n[i];
		return summa;
	}
}