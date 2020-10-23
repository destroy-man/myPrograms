/*����� 11. ������ 8
������� � ����� TickTock ��������� ��� ����������� ������������ ������ �������. ������ �������� ������� ������ 
�������� ����� �� ����� ����� "Tick", � ������ ����� ����� "Tock". ����� �������, ��������� "Tick-Tock" ������ 
��������������� ����� ������� �������������� �������. (����� ������������ ���������� ����� �� ���������.)
*/
class p11_8{
	String state;
	synchronized void tick(boolean running){
		if(!running){
			state="ticked";
			notify();
			return;
		}
		try{
			Thread.sleep(500);
			System.out.print("Tick ");
			state="ticked";
			notify();
			while(!state.equals("tocked"))
				wait();
		}
		catch(InterruptedException exc){
			System.out.println("���������� ������");
		}
	}
	synchronized void tock(boolean running){
		if(!running){
			state="tocked";
			notify();
			return;
		}
		try{
			Thread.sleep(500);
			System.out.println("Tock");
			state="tocked";
			notify();
			while(!state.equals("ticked"))
				wait();
		}
		catch(InterruptedException exc){
			System.out.println("���������� ������");
		}
	}
}