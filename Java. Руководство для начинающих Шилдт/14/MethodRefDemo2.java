/*Глава 14. Вопрос 16
В программе MetodRefDemo2 добавьте в класс MyIntNum новый метод
hasCommonFactor(). Этот метод должен возвращать true, если его аргумент
типа int и значение, которое хранится в вызывающем объекте MyIntNum, имеют
по крайней мере один общий делитель. Продемонстрируйте работу метода
hasCommonFactor(), используя ссылку на метод.
*/
class MethodRefDemo2{
	public static void main(String[] args){
		boolean result;
		MyIntNum myNum=new MyIntNum(12);
		MyIntNum myNum2=new MyIntNum(16);
		IntPredicate ip=myNum::isFactor;
		result=ip.test(3);
		if(result)System.out.println("3 является делителем "+myNum.getNum());
		ip=myNum2::isFactor;
		result=ip.test(3);
		if(!result)System.out.println("3 не является делителем "+myNum2.getNum());
		
		ip=myNum::hasCommonFactor;
		result=ip.test(4);
		if(result)System.out.println("У 4 и 12 есть общий делитель");
		ip=myNum::hasCommonFactor;
		result=ip.test(5);
		if(!result)System.out.println("У 5 и 16 нет общего делителя");
	}
}