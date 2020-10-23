class ExcTest{
	static void genException(){
		int[] nums=new int[4];
		System.out.println("До генерации исключения");
		nums[7]=10;
		System.out.println("Эта строка не будет отображаться");
	}
}