public class BeerSong{
	public static void main(String[] args){
		int beerNum=99;
		String word="������� (�������)";
		while(beerNum>0){
			System.out.println(beerNum+" "+word+" ���� �� �����");
			System.out.println(beerNum+" "+word+" ����.");
			System.out.println("������ ����.");
			System.out.println("����� �� �����.");
			beerNum--;
			if(beerNum==1)word="�������";
			if(beerNum>0)System.out.println(beerNum+" "+word+" ���� �� �����");
			else System.out.println("��� ������� ���� �� �����");
		}
	}
}