class SumArray{
	private int sum;
	int sumArray(int[] nums){
		sum=0;
		for(int i=0;i<nums.length;i++){
			sum+=nums[i];
			System.out.println("������� �������� ����� ��� "+Thread.currentThread().getName()+": "+sum);
			try{
				Thread.sleep(10);
			}
			catch(InterruptedException exc){
				System.out.println("���������� ��������� ������");
			}
		}
		return sum;
	}
}