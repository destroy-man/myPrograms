class ConstructorRefDemo{
	public static void main(String[] args){
		MyFunc myClassCons=MyClass::new;
		MyClass mc=myClassCons.func("������������");
		System.out.println("������ str � mc: "+mc.getStr());
	}
}