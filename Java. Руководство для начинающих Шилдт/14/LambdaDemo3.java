class LambdaDemo3{
	public static void main(String[] args){
		StringTest isIn=(a,b)->a.indexOf(b)!=-1;
		String str="��� ����";
		System.out.println("����������� ������: "+str);
		if(isIn.test(str,"���"))
			System.out.println("'���' �������");
		else
			System.out.println("'���' �� �������");
		if(isIn.test(str,"xyz"))
			System.out.println("'xyz' �������");
		else
			System.out.println("'xyz' �� �������");
	}
}