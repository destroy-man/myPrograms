class FailSoftArray{
	private int[] a;
	private int errval;
	public int length;
	public FailSoftArray(int size,int errv){
		a=new int[size];
		errval=errv;
		length=size;
	}
	public int get(int index){
		if(indexOK(index))return a[index];
		return errval;
	}
	public boolean put(int index,int val){
		if(indexOK(index)){
			a[index]=val;
			return true;
		}
		return false;
	}
	private boolean indexOK(int index){
		if(index>=0 & index<length)return true;
		return false;
	}
}