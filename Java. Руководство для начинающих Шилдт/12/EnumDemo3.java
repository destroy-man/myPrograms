enum Transport{
	CAR(65),TRUCK(55),AIRPLANE(600),TRAIN(70),BOAT(22);
	private int speed;
	Transport(int s){speed=s;}
	int getSpeed(){return speed;}
}
class EnumDemo3{
	public static void main(String[] args){
		Transport tp;
		System.out.println("�������� �������� ��������: "+Transport.AIRPLANE.getSpeed()+" ���� � ���\n");
		System.out.println("�������� �������� �������� ������������ �������");
		for(Transport t:Transport.values())
			System.out.println(t+": "+t.getSpeed()+" ���� � ���");
	}
}