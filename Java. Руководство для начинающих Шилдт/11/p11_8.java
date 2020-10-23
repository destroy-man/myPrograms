/*Глава 11. Вопрос 8
Внесите в класс TickTock изменения для организации фактического отчета времени. Первую половину секунды должен 
занимать вывод на экран слова "Tick", а вторую вывод слова "Tock". Таким образом, сообщение "Tick-Tock" должно 
соответствовать одной секунде отсчитываемого времени. (Время переключения контекстов можно не учитывать.)
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
			System.out.println("Прерывание потока");
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
			System.out.println("Прерывание потока");
		}
	}
}