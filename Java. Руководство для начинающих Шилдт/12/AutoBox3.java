class AutoBox3{
	public static void main(String[] args){
		Integer iOb,iOb2;
		int i;
		iOb=99;
		System.out.println("Исходное значение iOb: "+iOb);
		++iOb;
		System.out.println("После ++iOb: "+iOb);
		iOb+=10;
		System.out.println("После iOb+=10: "+iOb);
		iOb2=iOb+(iOb/3);
		System.out.println("iOb2 после вычисления выражения: "+iOb2);
		i=iOb+(iOb/3);
		System.out.println("i после вычисления выражения: "+i);
	}
}