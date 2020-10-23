class Finalize{
	public static void main(String[] args){
		int count;
		FDemo ob=new FDemo(0);
		for(count=1;count<100000;count++)
			ob.generator(count);
	}
}