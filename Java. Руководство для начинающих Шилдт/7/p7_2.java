/*����� 7. ������ 2
�������� �������� Circle, ����������� �� ������ TwoDShape. � ��� ������
���� ��������� ����� area (), ����������� ������� �����, � ����� �����������
� �������� ������ super ��� ������������� ������, �������������� �� ������
TwoDShape.
*/
class p7_2 extends TwoDShape{
	p7_2(){
		super();
	}
	p7_2(double w,double h){
		super(w,h,"����");
	}
	p7_2(double x){
		super(x,"����");
	}
	p7_2(p7_2 ob){
		super(ob);
	}
	double area(){
		return Math.pow((getWidth()/2),2)*3.14;
	}
}