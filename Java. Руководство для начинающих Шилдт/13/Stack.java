/*Глава 13. Вопрос 12
Видоизмените свой ответ на вопрос 10 в упражнении для самопроверки из главы
9 таким образом, чтобы сделать класс обобщенным. Для этого создайте интерфейс
стека IGenStack, объявив в нем обобщенные методы push () и рор ().
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