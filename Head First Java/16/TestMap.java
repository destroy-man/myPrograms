import java.util.*;
public class TestMap{
	public static void main(String[] args){
		HashMap<String,Integer> scores=new HashMap<String,Integer>();
		scores.put("����",42);
		scores.put("����",343);
		scores.put("�������",420);
		System.out.println(scores);
		System.out.println(scores.get("����"));
	}
}