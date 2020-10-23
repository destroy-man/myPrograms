class Queue{
	Object[] q;
	int putloc, getloc;
	Queue(int size){
		q=new Object[size+1];
		putloc=getloc=0;
	}
	void put(Object ch){
		if(putloc==q.length-1){
			System.out.println(" - ������� ���������");
			return;
		}
		putloc++;
		q[putloc]=ch;
	}
	Object get(){
		if(getloc==putloc){
			System.out.println(" - ������� �����");
			return 0;
		}
		getloc++;
		return q[getloc];
	}
}