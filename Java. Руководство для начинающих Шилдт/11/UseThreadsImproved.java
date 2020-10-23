class UseThreadsImproved{
	public static void main(String[] args){
		System.out.println("Запуск основного потока");
		MyThread mt=new MyThread("Child #1");
		for(int i=0;i<50;i++){
			System.out.print(".");
			try{
				Thread.sleep(100);
			}
			catch(InterruptedException exc){
				System.out.println("Прерывание основного потока");
			}
		}
		System.out.println("Завершение основного потока");
	}
}