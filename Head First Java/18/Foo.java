class Foo{
	void go(){
		Bar b=new Bar();
		b.doStuff();
	}
	public static void main(String[] args){
		Foo f=new Foo();
		f.go();
	}
}