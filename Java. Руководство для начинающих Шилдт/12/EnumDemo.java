enum Transport{
	CAR,TRUCK,AIRPLANE,TRAIN,BOAT;
}
class EnumDemo{
	public static void main(String[] args){
		Transport tp;
		tp=Transport.AIRPLANE;
		System.out.println("Значение tp: "+tp);
		System.out.println();
		tp=Transport.TRAIN;
		if(tp==Transport.TRAIN)
			System.out.println("tp содержит TRAIN\n");
		switch(tp){
			case CAR:
				System.out.println("Автомобиль перевозит людей");
				break;
			case TRUCK:
				System.out.println("Грузовик перевозит груз");
				break;
			case AIRPLANE:
				System.out.println("Самолет летит");
				break;
			case TRAIN:
				System.out.println("Поезд движется по рельсам");
				break;
			case BOAT:
				System.out.println("Лодка плывет по воде");
				break;
		}
	}
}