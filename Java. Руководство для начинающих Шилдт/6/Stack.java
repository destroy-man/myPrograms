class Stack{
	private char[] q;
	private int putloc,getloc;
	private Stack(int size){
		q=new char[size+1];
		putloc=getloc=0;
	}
	private Stack(Stack ob){
		putloc=ob.putloc;
		getloc=ob.getloc;
		q=new char[ob.q.length];
		for(int i=getloc+1;i<=putloc;i++)
			q[i]=ob.q[i];
	}
	private Stack(char[] a){
		putloc=0;
		getloc=0;
		q=new char[a.length+1];
		for(int i=0;i<a.length;i++)push(a[i]);
	}
	void push(char ch){
		if(putloc==q.length-1){
			System.out.println(" - Очередь заполнена");
			return;
		}
		putloc++;
		q[putloc]=ch;
	}
	char pop(){
		if(getloc==putloc){
			System.out.println(" - Очередь пуста");
			return (char)0;
		}
		getloc++;
		return q[getloc];
	}
}