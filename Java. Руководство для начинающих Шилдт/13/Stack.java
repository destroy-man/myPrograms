/*����� 13. ������ 12
������������ ���� ����� �� ������ 10 � ���������� ��� ������������ �� �����
9 ����� �������, ����� ������� ����� ����������. ��� ����� �������� ���������
����� IGenStack, ������� � ��� ���������� ������ push () � ��� ().
*/
public class Stack<T> implements IGenStack<T>{
	private T[] q;
	private int putloc,getloc;
	private Stack(int size){
		q=(T[])new Object[size+1];
		putloc=getloc=0;
	}
	private Stack(Stack ob){
		putloc=ob.putloc;
		getloc=ob.getloc;
		q=(T[])new Object[ob.q.length];
		for(int i=getloc+1;i<=putloc;i++)
			q[i]=(T)ob.q[i];
	}
	private Stack(T[] a) throws StackFullException{
		putloc=0;
		getloc=0;
		q=(T[])new Object[a.length+1];
		for(int i=0;i<a.length;i++)push(a[i]);
	}
	public void push(T ch) throws StackFullException{
		if(putloc==q.length-1)
			throw new StackFullException(q.length);
		putloc++;
		q[putloc]=ch;
	}
	public T pop() throws StackEmptyException{
		if(getloc==putloc)
			throw new StackEmptyException();
		getloc++;
		return q[getloc];
	}
}