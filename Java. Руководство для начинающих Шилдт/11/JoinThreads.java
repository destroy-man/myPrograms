class JoinThreads{
	public static void main(String[] args){
		System.out.println("«апуск основного потока");
		MyThread mt1=new MyThread("Child #1");
		MyThread mt2=new MyThread("Child #2");
		MyThread mt3=new MyThread("Child #3");
		try{
			mt1.thrd.join();
			System.out.println("Child #1 - присоединен");
			mt2.thrd.join();
			System.out.println("Child #2 - присоединен");
			mt3.thrd.join();
			System.out.println("Child #3 - присоединен");
		}
		catch(InterruptedException exc){
			System.out.println("ѕрерывание основного потока");
		}
		System.out.println("«авершение основного потока");
	}
}