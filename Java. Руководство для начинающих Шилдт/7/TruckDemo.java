class TruckDemo{
	public static void main(String[] args){
		Truck semi=new Truck(2,200,7,44000);
		Truck pickup=new Truck(3,28,15,2000);
		double gallons;
		int dist=252;
		gallons=semi.fuelneeded(dist);
		System.out.println("�������� ����� ��������� "+semi.getCargo()+" ������.");
		System.out.println("��� ����������� "+dist+" ���� ��������� ��������� "+gallons+" �������� �������.\n");
		gallons=pickup.fuelneeded(dist);
		System.out.println("����� ����� ��������� "+pickup.getCargo()+" ������.");
		System.out.println("��� ����������� "+dist+" ���� ������ ��������� "+gallons+" �������� �������.");
	}
}