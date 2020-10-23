class CallByValue{
	public static void main(String[] args){
		Test ob=new Test();
		int a=15,b=20;
		System.out.println("a ט b ןונוה גûחמגמל: "+a+" "+b);
		ob.noChange(a,b);
		System.out.println("a ט b ןמסכו גûחמגא: "+a+" "+b);
	}
}