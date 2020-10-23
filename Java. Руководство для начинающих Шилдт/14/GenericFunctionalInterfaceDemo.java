class GenericFunctionalInterfaceDemo{
	public static void main(String[] args){
		SomeTest<Integer> isFactor=(n,d)->(n%d)==0;
		if(isFactor.test(10,2))
			System.out.println("2 �������� ��������� 10");
		System.out.println();
		SomeTest<Double> isFactorD=(n,d)->(n%d)==0;
		if(isFactorD.test(212.0,4.0))
			System.out.println("4.0 �������� ��������� 212.0");
		System.out.println();
		SomeTest<String> isIn=(a,b)->a.indexOf(b)!=-1;
		String str="���������� �������������� ���������";
		System.out.println("����������� ������: "+str);
		if(isIn.test(str,"face"))
			System.out.println("'face' �������");
		else
			System.out.println("'face' �� �������");
	}
}