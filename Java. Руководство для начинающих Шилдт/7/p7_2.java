/*Глава 7. Вопрос 2
Создайте подкласс Circle, производный от класса TwoDShape. В нем должен
быть определен метод area (), вычисляющий площадь круга, а также конструктор
с ключевым словом super для инициализации членов, унаследованных от класса
TwoDShape.
*/
class p7_2 extends TwoDShape{
	p7_2(){
		super();
	}
	p7_2(double w,double h){
		super(w,h,"круг");
	}
	p7_2(double x){
		super(x,"круг");
	}
	p7_2(p7_2 ob){
		super(ob);
	}
	double area(){
		return Math.pow((getWidth()/2),2)*3.14;
	}
}