class Suspend{
	public static void main(String[] args){
		MyThread ob1=new MyThread("MyThread");
		try{
			Thread.sleep(1000);
			ob1.mysuspend();
			System.out.println("������������ ������");
			Thread.sleep(1000);
			ob1.myresume();
			System.out.println("������������� ������");
			Thread.sleep(1000);
			ob1.mysuspend();
			System.out.println("������������ ������");
			Thread.sleep(1000);
			ob1.myresume();
			System.out.println("������������� ������");
			Thread.sleep(1000);
			ob1.mysuspend();
			System.out.println("��������� ������");
			ob1.mystop();
		}
		catch(InterruptedException e){
			System.out.println("���������� ��������� ������");
		}
		try{
			ob1.thrd.join();
		}
		catch(InterruptedException e){
			System.out.println("���������� ��������� ������");
		}
		System.out.println("����� �� ��������� ������");
	}
}