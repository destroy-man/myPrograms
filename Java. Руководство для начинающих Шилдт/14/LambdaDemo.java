class LambdaDemo{
	public static void main(String[] args){
		MyValue myVal;
		myVal=()->98.6;
		System.out.println("���������� ��������: "+myVal.getValue());
		MyParamValue myPval=(n)->1.0/n;
		System.out.println("�������� �������� 4 ����� "+myPval.getValue(4.0));
		System.out.println("�������� �������� 8 ����� "+myPval.getValue(8.0));
	}
}