class PriorityDemo{
	public static void main(String[] args){
		Priority mt1=new Priority("High Priority");
		Priority mt2=new Priority("Low Priority");
		mt1.thrd.setPriority(Thread.NORM_PRIORITY+2);
		mt2.thrd.setPriority(Thread.NORM_PRIORITY-2);
		mt1.thrd.start();
		mt2.thrd.start();
		try{
			mt1.thrd.join();
			mt2.thrd.join();
		}
		catch(InterruptedException exc){
			System.out.println("Прерывание основного потока");
		}
		System.out.println("\nСчетчик потока High Priority: "+mt1.count);
		System.out.println("Счетчик потока Low Priority: "+mt2.count);
	}
}