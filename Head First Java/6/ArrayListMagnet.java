import java.util.*;
public class ArrayListMagnet{
	public static void printAL(ArrayList<String> al){
		for(String element: al){
			System.out.print(element+" ");
		}
		System.out.println(" ");
	}
	public static void main(String[] args){
		ArrayList<String> a=new ArrayList<String>();
		a.add(0,"����");
		a.add(1,"����");
		a.add(2,"���");
		a.add(3,"���");
		printAL(a);
		a.remove(2);
		if(a.contains("���"))a.add("2.2");
		if(a.contains("���"))a.add("������");
		printAL(a);
		if(a.indexOf("������")!=4)a.add(4,"4.2");
		printAL(a);
		printAL(a);
	}
}