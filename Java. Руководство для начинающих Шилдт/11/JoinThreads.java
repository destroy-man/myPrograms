class JoinThreads{
	public static void main(String[] args){
		System.out.println("������ ��������� ������");
		MyThread mt1=new MyThread("Child #1");
		MyThread mt2=new MyThread("Child #2");
		MyThread mt3=new MyThread("Child #3");
		try{
			mt1.thrd.join();
			System.out.println("Child #1 - �����������");
			mt2.thrd.join();
			System.out.println("Child #2 - �����������");
			mt3.thrd.join();
			System.out.println("Child #3 - �����������");
		}
		catch(InterruptedException exc){
			System.out.println("���������� ��������� ������");
		}
		System.out.println("���������� ��������� ������");
	}
}