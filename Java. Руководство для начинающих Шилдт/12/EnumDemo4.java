enum Transport{
	CAR,TRUCK,AIRPLANE,TRAIN,BOAT
}
class EnumDemo4{
	public static void main(String[] args){
		Transport tp,tp2,tp3;
		System.out.println("Константы перечисления Transport и их порядковые значения: ");
		for(Transport t:Transport.values())
			System.out.println(t+" "+t.ordinal());
		tp=Transport.AIRPLANE;
		tp2=Transport.TRAIN;
		tp3=Transport.AIRPLANE;
		System.out.println();
		if(tp.compareTo(tp2)<0)
			System.out.println(tp+" идет перед "+tp2);
		if(tp.compareTo(tp2)>0)
			System.out.println(tp2+" идет перед "+tp);
		if(tp.compareTo(tp3)==0)
			System.out.println(tp+" совпадает с "+tp3);
	}
}