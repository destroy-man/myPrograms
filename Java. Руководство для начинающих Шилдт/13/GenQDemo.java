class GenQDemo{
	public static void main(String[] args){
		Integer[] iStore=new Integer[10];
		GenQueue<Integer> q=new GenQueue<Integer>(iStore);
		Integer iVal;
		System.out.println("ƒемонстрации очереди чисел типа Integer");
		try{
			for(int i=0;i<5;i++){
				System.out.println("ƒобавление "+i+" в очередь q");
				q.put(i);
			}
		}
		catch(QueueFullException exc){
			System.out.println(exc);
		}
		System.out.println();
		try{
			for(int i=0;i<5;i++){				
				System.out.print("ѕолучение следующего числа типа Integer из очереди q: ");
				iVal=q.get();
				System.out.println(iVal);
			}
		}
		catch(QueueEmptyException exc){
			System.out.println(exc);
		}
		System.out.println();
		Double[] dStore=new Double[10];
		GenQueue<Double> q2=new GenQueue<Double>(dStore);
		Double dVal;
		System.out.println("ƒемонстраци€ очереди чисел типа Double");
		try{
			for(int i=0;i<5;i++){
				System.out.println("ƒобавление "+(double)i/2+" в очередь q2");
				q2.put((double)i/2);
			}
		}
		catch(QueueFullException exc){
			System.out.println(exc);
		}
		System.out.println();
		try{
			for(int i=0;i<5;i++){
				System.out.print("получение следующего числа типа Double из очереди q2: ");
				dVal=q2.get();
				System.out.println(dVal);
			}
		}
		catch(QueueEmptyException exc){
			System.out.println(exc);
		}
	}
}