public class TestExceptions{
	public static void main(String[] args){
		String test="���";
		try{
			System.out.println("������ try");
			doRisky(test);
			System.out.println("����� try");
		}
		catch(ScaryException se){
			System.out.println("������ ����������");
		}
		finally{
			System.out.println("finally");
		}
		System.out.println("����� main");
	}
	static void doRisky(String test) throws ScaryException{
		System.out.println("������ �������� ������");
		if("yes".equals(test))throw new ScaryException();
		System.out.println("����� �������� ������");
		return;
	}
}