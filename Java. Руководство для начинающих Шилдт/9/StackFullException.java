class StackFullException extends Exception{
	int size;
	StackFullException(int s){
		size=s;
	}
	public String toString(){
		return "Стек заполнен. Помещать элементы в него больше нельзя.";
	}
}