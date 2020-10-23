/*Глава 8. Вопрос 9
Создайте интерфейс для класса Vehicle, рассмотренного в главе 7, назвав его
IVehicle.
*/
interface IVehicle{
	int range();
	double fuelneeded(int miles);
	int getPassengers();
	void setPassengers(int p);
	int getFuelCap();
	void setFuelCap(int f);
	int getMpg();
	void setMpg(int m);
}