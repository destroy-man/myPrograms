class ExcDemo1{
	public static void main(String[] args){
		int[] nums=new int[4];
		try{
			System.out.println("�� ��������� ����������");
			nums[7]=10;
			System.out.println("��� ������ �� ����� ������������");
		}
		catch(ArrayIndexOutOfBoundsException exc){
			System.out.println("����� �� ������� �������!");
		}
		System.out.println("����� ��������� catch");
	}
}