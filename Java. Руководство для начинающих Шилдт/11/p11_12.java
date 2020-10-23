/*����� 11. ������ 12
����������� �������������� ����������� �������� ������������� � ������ Queue, ������������� � ���������� ������.
���� ���� - ���������� ���������� ���������������� ������ � �������� ������������� ���������.
*/
class p11_12 implements ICharQ{
	private char[] q;
	private int putloc,getloc;
	public p11_12(int size){
		q=new char[size+1];
		putloc=getloc=0;
	}
	public synchronized void put(char ch){
		try{
			if(putloc==q.length-1)
				wait();
			notify();
			putloc++;
			q[putloc]=ch;
		}
		catch(InterruptedException exc){
			System.out.println("������� �����");
		}
	}
	public synchronized char get(){
		try{
			if(getloc==putloc)
				wait();
			notify();
			getloc++;
			return q[getloc];
		}
		catch(InterruptedException exc){
			System.out.println("������� �����");
		}
		return (char)-1;
	}
}