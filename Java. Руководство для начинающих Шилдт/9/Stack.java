/*����� 9. ������ 10
������� �� ������ 3 ���������� ��� ������������ �� ��������� ����� 6, �� �������
����� Stack. �������� � ���� ����������� ����������, ����������� ��
������� ��������� ������� � ������������� ���� � ������� ������� �� �������
�����.
*/
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
	private Stack(char[] a) throws StackFullException{
		putloc=0;
		getloc=0;
		q=new char[a.length+1];
		for(int i=0;i<a.length;i++)push(a[i]);
	}
	void push(char ch) throws StackFullException{
		if(putloc==q.length-1)
			throw new StackFullException(q.length);
		putloc++;
		q[putloc]=ch;
	}
	char pop() throws StackEmptyException{
		if(getloc==putloc)
			throw new StackEmptyException();
		getloc++;
		return q[getloc];
	}
}