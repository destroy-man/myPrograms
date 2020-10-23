/*����� 14. ������ 16
� ��������� MetodRefDemo2 �������� � ����� MyIntNum ����� �����
hasCommonFactor(). ���� ����� ������ ���������� true, ���� ��� ��������
���� int � ��������, ������� �������� � ���������� ������� MyIntNum, �����
�� ������� ���� ���� ����� ��������. ����������������� ������ ������
hasCommonFactor(), ��������� ������ �� �����.
*/
class MethodRefDemo2{
	public static void main(String[] args){
		boolean result;
		MyIntNum myNum=new MyIntNum(12);
		MyIntNum myNum2=new MyIntNum(16);
		IntPredicate ip=myNum::isFactor;
		result=ip.test(3);
		if(result)System.out.println("3 �������� ��������� "+myNum.getNum());
		ip=myNum2::isFactor;
		result=ip.test(3);
		if(!result)System.out.println("3 �� �������� ��������� "+myNum2.getNum());
		
		ip=myNum::hasCommonFactor;
		result=ip.test(4);
		if(result)System.out.println("� 4 � 12 ���� ����� ��������");
		ip=myNum::hasCommonFactor;
		result=ip.test(5);
		if(!result)System.out.println("� 5 � 16 ��� ������ ��������");
	}
}