public class RyanAndMonicaJob implements Runnable{
	private BankAccount account=new BankAccount();
	public static void main(String[] args){
		RyanAndMonicaJob theJob=new RyanAndMonicaJob();
		Thread one=new Thread(theJob);
		Thread two=new Thread(theJob);
		one.setName("�����");
		two.setName("������");
		one.start();
		two.start();
	}
	public void run(){
		for(int x=0;x<10;x++){
			makeWithdrawal(10);
			if(account.getBalance()<0)System.out.println("���������� ������!");
		}
	}
	private synchronized void makeWithdrawal(int amount){
		if(account.getBalance()>=amount){
			System.out.println(Thread.currentThread().getName()+" ���������� ����� ������");
			try{
				System.out.println(Thread.currentThread().getName()+" ���� ���������");
				Thread.sleep(500);
			}
			catch(InterruptedException ex){
				ex.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" �����������");
			account.withdraw(amount);
			System.out.println(Thread.currentThread().getName()+" ����������� ����������");
		}
		else System.out.println("��������, ��� ������� "+Thread.currentThread().getName()+" ������������ �����");
	}
}