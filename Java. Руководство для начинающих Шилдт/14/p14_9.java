/*Глава 14. Вопрос 9
Используя программу, созданную в упражнении 14.1, создайте лямбда-выражение,
которое удаляет все пробелы из заданной строки и возвращает результат.
Продемонстрируйте работу этого метода, передав его методу changeStr () .
*/
class p14_9{
	static String changeStr(StringFunc sf,String s){
		return sf.func(s);
	}
	public static void main(String[] args){
		String inStr="Лямбда-выражения расширяют Java";
		String outStr;
		System.out.println("Входная строка: "+inStr);
		outStr=changeStr((str)->str.replace(" ",""),inStr);
		System.out.println("Строка с удаленными пробелами пробелами: "+outStr);
	}
}