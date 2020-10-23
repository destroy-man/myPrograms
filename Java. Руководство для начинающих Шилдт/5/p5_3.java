/*Глава 5. Вопрос 3
Напишите программу, в которой массив используется для нахождения среднего
арифметического десяти значений типа douЫe. Используйте любые десять чисел.
*/
class p5_3{
	public static void main(String[] args){
		double[] arr={1,2,3,4,5,6,7,8,9,10};
		double sum=0;
		for(double d:arr)
			sum+=d;
		System.out.println("Среднее арифметическое="+sum/arr.length);
	}
}