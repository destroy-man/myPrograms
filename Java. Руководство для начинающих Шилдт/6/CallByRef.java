class CallByRef{
	public static void main(String[] args){
		Test ob=new Test(15,20);
		System.out.println("ob.a ט ob.b ןונוה גûחמגמל: "+ob.a+" "+ob.b);
		ob.change(ob);
		System.out.println("ob.a ט ob.b ןמסכו גûחמגא: "+ob.a+" "+ob.b);
	}
}