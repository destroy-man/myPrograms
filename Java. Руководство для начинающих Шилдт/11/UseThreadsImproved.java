class UseThreadsImproved{
	public static void main(String[] args){
		System.out.println("������ ��������� ������");
		MyThread mt=new MyThread("Child #1");
		for(int i=0;i<50;i++){
			System.out.print(".");
			try{
				Thread.sleep(100);
			}
			catch(InterruptedException exc){
				System.out.println("���������� ��������� ������");
			}
		}
		System.out.println("���������� ��������� ������");
	}
}