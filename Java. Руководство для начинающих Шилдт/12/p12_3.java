/*√лава 12. ¬опрос 3
Ќапишите дл€ приведенного ниже перечислени€ программу, в которой метод values() используетс€ дл€ отображени€ 
списка констант и их значений.
enum Tools{
	SCREWDRIVER,WRENCH,HAMMER,PLIERS
}
*/
enum Tools{
	SCREWDRIVER,WRENCH,HAMMER,PLIERS
}
class p12_3{
	public static void main(String[] args){
		for(Tools t:Tools.values())
			System.out.println(t.ordinal()+" "+t);
	}
}