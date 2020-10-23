class UseMain{
	public static void main(String[] args){
		Thread thrd;
		thrd=Thread.currentThread();
		System.out.println("Имя основного потока: "+thrd.getName());
		System.out.println("Приоритет: "+thrd.getPriority());
		System.out.println();
		System.out.println("Установка имени и приоритета\n");
		thrd.setName("Thread #1");
		thrd.setPriority(Thread.NORM_PRIORITY+3);
		System.out.println("Новое имя основного потока: "+thrd.getName());
		System.out.println("Новое значение приоритета: "+thrd.getPriority());
	}
}