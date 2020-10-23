class MyClass<T> implements Containment<T>{
	T[] arrayRef;
	MyClass(T[] o){
		arrayRef=o;
	}
	public boolean contains(T o){
		for(T x:arrayRef)
			if(x.equals(o))return true;
		return false;
	}
}