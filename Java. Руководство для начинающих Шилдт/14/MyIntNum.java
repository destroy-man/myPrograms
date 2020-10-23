/*Глава 14. Вопрос 16
В программе MetodRefDemo2 добавьте в класс MyIntNum новый метод
hasCommonFactor(). Этот метод должен возвращать true, если его аргумент
типа int и значение, которое хранится в вызывающем объекте MyIntNum, имеют
по крайней мере один общий делитель. Продемонстрируйте работу метода
hasCommonFactor(), используя ссылку на метод.
*/
import java.util.*;
class MyIntNum{
	private int v;
	MyIntNum(int x){v=x;}
	int getNum(){return v;}
	boolean isFactor(int n){
		return (v%n)==0;
	}
	boolean hasCommonFactor(int n){
		ArrayList<Integer> commonFactorN=new ArrayList<Integer>();
		ArrayList<Integer> commonFactorV=new ArrayList<Integer>();
		for(int i=2;i<n;i++)
			if(n%i==0)
				commonFactorN.add(i);
		for(int i=2;i<v;i++)
			if(v%i==0)
				commonFactorV.add(i);
		for(int i:commonFactorN)
			if(commonFactorV.contains(i))
				return true;
		return false;
	}
}