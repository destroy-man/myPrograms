class TickTock{
	String state;
	synchronized void tick(boolean running){
		if(!running){
			state="ticked";
			return;
		}
		System.out.print("Tick ");
		state="ticked";
	}
	synchronized void tock(boolean running){
		if(!running){
			state="tocked";
			return;
		}
		System.out.println("Tock");
		state="tocked";
	}
}