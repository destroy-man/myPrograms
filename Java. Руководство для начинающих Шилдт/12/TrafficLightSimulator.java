/*����� 12. ������ 4
��������� � ���������� 12.1 ���������, ����������� ������������������ ��������, ����� �����������������, ����� 
��� ������� ���������, ����������� ������� ��������������� ������������� ������������. � �������� ������ ���� 
��������� ����������������� ����������� ������� ����� ��������� �������������� � ������ TrafficLightSimulator, 
������ �������� �������� ���� ������ ����������������� � ������ run(). �������� �������� ��� ��������� ����� 
�������, ����� ����������������� ����������� ������� ����� ���������� ���������� ����������� ������������� ���� 
TrafficLightColor. ��� ����� ��� ����������� �����������, ���������� ����������, ����������� ��� private, � 
����� getDelay(). ��������� � ���, ��� ��� ����� �������� ������ ���������. (���������: ���������� ���������� �� 
��������� switch � ��������������� ����������� ���������� ������� ����� ��� ������������ ���������.)
*/
class TrafficLightSimulator implements Runnable{
	private Thread thrd;
	private TrafficLightColor tlc;
	boolean stop=false;
	boolean changed=false;
	TrafficLightSimulator(TrafficLightColor init){
		tlc=init;
		thrd=new Thread(this);
		thrd.start();
	}
	TrafficLightSimulator(){
		tlc=TrafficLightColor.RED;
		thrd=new Thread(this);
		thrd.start();
	}
	public void run(){
		while(!stop){
			try{
				if(tlc.ordinal()==1)Thread.sleep(tlc.GREEN.getDelay());
				else if(tlc.ordinal()==2)Thread.sleep(tlc.YELLOW.getDelay());
				else Thread.sleep(tlc.RED.getDelay());
			}
			catch(InterruptedException exc){
				System.out.println(exc);
			}
			changeColor();
		}
	}
	synchronized void changeColor(){
		switch(tlc){
			case RED:
				tlc=TrafficLightColor.GREEN;
				break;
			case YELLOW:
				tlc=TrafficLightColor.RED;
				break;
			case GREEN:
				tlc=TrafficLightColor.YELLOW;
		}
		changed=true;
		notify();
	}
	synchronized void waitForChange(){
		try{
			while(!changed)
				wait();
			changed=false;
		}
		catch(InterruptedException exc){
			System.out.println(exc);
		}
	}
	TrafficLightColor getColor(){
		return tlc;
	}
	void cancel(){
		stop=true;
	}
}