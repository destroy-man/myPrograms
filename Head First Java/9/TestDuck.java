public class TestDuck{
	public static void main(String[] args){
		int weight=8;
		float density=2.3F;
		String name="Дональд";
		long[] feathers={1,2,3,4,5,6};
		boolean canFly=true;
		int airSpeed=22;
		Duck[] d=new Duck[7];
		d[0]=new Duck();
		d[1]=new Duck(density,weight); //5
		d[2]=new Duck(name,feathers); //3
		d[3]=new Duck(canFly); //2
		d[4]=new Duck(3.3F,airSpeed); //5
		d[5]=new Duck(false); //2
		d[6]=new Duck(airSpeed,density); //4
	}
}