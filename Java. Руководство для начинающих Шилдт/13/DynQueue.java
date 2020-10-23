public class DynQueue<T> implements IGenQ<T>{
	private T[] q;
	private int putloc,getloc;
	public DynQueue(int size){
		q=(T[])new Object[size+1];
		putloc=getloc=0;
	}
	public void put(T ch){
		if(putloc+1==getloc | (putloc==q.length-1 && getloc==0)){
			T[] t=(T[])new Object[q.length*2];
			for(int i=0;i<q.length;i++)
				t[i]=q[i];
			q=t;
		}
		putloc++;
		if(putloc==q.length)putloc=0;
		q[putloc]=ch;
	}
	public T get(){
		if(getloc==putloc){
			System.out.println(" - Очередь пуста");
			return null;
		}
		getloc++;
		if(getloc==q.length)getloc=0;
		return q[getloc];
	}
	public void reset(){
		q=null;
		putloc=getloc=0;
	}
	public void Queue(T[] a,T[] b){
		for(int i=0;i<a.length;i++)
			b[i]=a[i];
	}
}