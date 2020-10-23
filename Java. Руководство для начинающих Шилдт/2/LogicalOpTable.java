class LogicalOpTable{
	public static int getNumber(boolean znac){
		if(znac)return 1;
		else return 0;
	}
	public static void main(String[] args){
		boolean p,q;
		System.out.println("P\tQ\tAND\tOR\tXOR\tNOT");
		p=true;q=true;
		System.out.print(getNumber(p)+"\t"+getNumber(q)+"\t");
		System.out.print(getNumber(p&q)+"\t"+getNumber(p|q)+"\t");
		System.out.println(getNumber(p^q)+"\t"+getNumber(!p));
		p=true;q=false;
		System.out.print(getNumber(p)+"\t"+getNumber(q)+"\t");
		System.out.print(getNumber(p&q)+"\t"+getNumber(p|q)+"\t");
		System.out.println(getNumber(p^q)+"\t"+getNumber(!p));
		p=false;q=true;
		System.out.print(getNumber(p)+"\t"+getNumber(q)+"\t");
		System.out.print(getNumber(p&q)+"\t"+getNumber(p|q)+"\t");
		System.out.println(getNumber(p^q)+"\t"+getNumber(!p));
		p=false;q=false;
		System.out.print(getNumber(p)+"\t"+getNumber(q)+"\t");
		System.out.print(getNumber(p&q)+"\t"+getNumber(p|q)+"\t");
		System.out.print(getNumber(p^q)+"\t"+getNumber(!p));
	}
}