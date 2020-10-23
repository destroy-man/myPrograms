class ConstructorRefDemo{
	public static void main(String[] args){
		MyFunc myClassCons=MyClass::new;
		MyClass mc=myClassCons.func("Тестирование");
		System.out.println("Строка str в mc: "+mc.getStr());
	}
}