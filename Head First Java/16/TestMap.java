import java.util.*;
public class TestMap{
	public static void main(String[] args){
		HashMap<String,Integer> scores=new HashMap<String,Integer>();
		scores.put("Кэти",42);
		scores.put("Берт",343);
		scores.put("Скайлер",420);
		System.out.println(scores);
		System.out.println(scores.get("Берт"));
	}
}