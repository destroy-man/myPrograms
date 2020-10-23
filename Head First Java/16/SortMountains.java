import java.util.*;
public class SortMountains{
	LinkedList<Mountain> mtn=new LinkedList<Mountain>();
	class NameCompare implements Comparator<Mountain>{
		public int compare(Mountain one,Mountain two){
			return one.name.compareTo(two.name);
		}
	}
	class HeightCompare implements Comparator<Mountain>{
		public int compare(Mountain one,Mountain two){
			return (Integer.toString(two.height).compareTo(Integer.toString(one.height)));
			//return (two.height-one.height)
		}
	}
	public static void main(String[] args){
		new SortMountains().go();
	}
	public void go(){
		mtn.add(new Mountain("Лонг-Рейндж",14255));
		mtn.add(new Mountain("Эльберт",14433));
		mtn.add(new Mountain("Марун",14156));
		mtn.add(new Mountain("Касл",14265));
		System.out.println("В порядке добавления:\n"+mtn);
		NameCompare nc=new NameCompare();
		Collections.sort(mtn,nc);
		System.out.println("По названию:\n"+mtn);
		HeightCompare hc=new HeightCompare();
		Collections.sort(mtn,hc);;
		System.out.println("По высоте:\n"+mtn);
	}
	class Mountain{
		String name;
		int height;
		Mountain(String n,int h){
			name=n;
			height=h;
		}
		public String toString(){
			return name+" "+height;
		}
	}
}