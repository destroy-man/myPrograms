class FDemo{
	int x;
	FDemo(int i){
		x=i;
	}
	protected void finalize(){
		System.out.println("����������� "+x);
	}
	void generator(int i){
		FDemo o=new FDemo(i);
	}
}