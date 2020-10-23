class GenDemo{
	public static void main(String[] args){
		Gen<Integer> iOb;
		iOb=new Gen<Integer>(88);
		iOb.showType();
		int v=iOb.getob();
		System.out.println("значение: "+v);
		System.out.println();
		Gen<String> strOb=new Gen<String>("“естирование обобщений");
		strOb.showType();
		String str=strOb.getob();
		System.out.println("значение: "+str);
	}
}