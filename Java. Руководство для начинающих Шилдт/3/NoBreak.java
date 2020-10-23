class NoBreak{
	public static void main(String[] args){
		int i;
		for(i=0;i<=5;i++){
			switch(i){
				case 0: System.out.println("i меньше единицы");
				case 1: System.out.println("i меньше двух");
				case 2: System.out.println("i меньше трех");
				case 3: System.out.println("i меньше четырех");
				case 4: System.out.println("i меньше пяти");
			}
			System.out.println();
		}
	}
}