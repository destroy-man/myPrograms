class GoodDog{
	private int size;
	public int getSize(){
		return size;
	}
	public void setSize(int s){
		size=s;
	}
	void bark(){
		if(size>60)System.out.println("Ãàâ Ãàâ!");
		else if(size>14)System.out.println("Âóô Âóô!");
		else System.out.println("Òÿô Òÿô!");
	}
}