class Vehicle{
	private int passengers;
	private int fuelcap;
	private int mpg;
	Vehicle(int p,int f,int m){
		passengers=p;
		fuelcap=f;
		mpg=m;
	}
	int range(){
		return mpg*fuelcap;
	}
	double fuelneeded(int miles){
		return (double)miles/mpg;
	}
	int getPassengers(){return passengers;}
	void setPassengers(int p){passengers=p;}
	int getFuelcap(){return fuelcap;}
	void setFuelcap(int f){fuelcap=f;}
	int getMpg(){return mpg;}
	void setMpg(int m){mpg=m;}
}