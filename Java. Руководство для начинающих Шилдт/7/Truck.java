class Truck extends Vehicle{
	private int cargocap;
	Truck(int p,int f,int m,int c){
		super(p,f,m);
		cargocap=c;
	}
	int getCargo(){return cargocap;}
	void putCargo(int c){cargocap=c;}
}