//Глава 1 вопрос 10
class InchToMetrTable{
	public static void main(String[] args){
		double inchs,metrs;
		int counter;
		counter=0;
		for(inchs=1;inchs<=100;inchs++){
			metrs=inchs/39.37;
			System.out.println(inchs+" дюймам соответствует "+metrs+" метров.");
			counter++;
			if(counter==12){
				System.out.println();
				counter=0;
			}
		}
	}
}