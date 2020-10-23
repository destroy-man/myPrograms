/*Глава 12. Вопрос 4
Созданную в упражнении 12.1 программу, имитирующую автоматизированный светофор, можно усовершенствовать, внеся 
ряд простых изменений, позволяющих выгодно воспользоваться возможностями перечислений. В исходной версии этой 
программы продолжительность отображения каждого цвета светофора регулировалась в классе TrafficLightSimulator, 
причем значения задержек были жестко запрограммированы в методе run(). Измените исходный код программы таким 
образом, чтобы продолжительность отображения каждого цвета светоформа задавалась константами перечислимого типа 
TrafficLightColor. Для этого вам понадобятся конструктор, переменная экземпляра, объявленная как private, и 
метод getDelay(). Подумайте о том, как еще можно улучшить данную программу. (Подсказка: попробуйте отказаться от 
оператора switch и воспользоваться порядковыми значениями каждого цвета для переключения светофора.)
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