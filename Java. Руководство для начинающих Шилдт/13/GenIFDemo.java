class GenIFDemo{
	public static void main(String[] args){
		Integer[] x={1,2,3};
		MyClass<Integer> ob=new MyClass<Integer>(x);
		if(ob.contains(2))
			System.out.println("2 ���������� � ob");
		else
			System.out.println("2 �� ���������� � ob");
		if(ob.contains(5))
			System.out.println("5 ���������� � ob");
		else
			System.out.println("5 �� ���������� � ob");
	}
}