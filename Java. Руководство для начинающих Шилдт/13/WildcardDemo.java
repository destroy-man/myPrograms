class WildcardDemo{
	public static void main(String[] args){
		NumericFns<Integer> iOb=new NumericFns<Integer>(6);
		NumericFns<Double> dOb=new NumericFns<Double>(-6.0);
		NumericFns<Long> lOb=new NumericFns<Long>(5L);
		System.out.println("��������� iOb � dOb");
		if(iOb.absEqual(dOb))
			System.out.println("���������� �������� ���������.");
		else
			System.out.println("���������� �������� ����������.");
		System.out.println();
		System.out.println("��������� iOb � lOb");
		if(iOb.absEqual(lOb))
			System.out.println("���������� �������� ���������.");
		else
			System.out.println("���������� �������� ����������.");
	}
}