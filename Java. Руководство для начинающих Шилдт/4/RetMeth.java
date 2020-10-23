class RetMeth{
	public static void main(String[] args){
		Vehicle minivan=new Vehicle();
		Vehicle sportscar=new Vehicle();
		minivan.passengers=7;
		minivan.fuelcap=16;
		minivan.mpg=21;
		sportscar.passengers=2;
		sportscar.fuelcap=14;
		sportscar.mpg=12;
		System.out.println("Мини-фургон может перевезти "+minivan.passengers+" на расстояние "+minivan.range()+" миль");
		System.out.println("Спортивный автомобиль может перевезти "+sportscar.passengers+" на расстояние "+sportscar.range()+" миль");
	}
}