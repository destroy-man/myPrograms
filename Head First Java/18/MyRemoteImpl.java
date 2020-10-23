import java.rmi.*;
import java.rmi.server.*;
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote{
	public String sayHello(){
		return "������ ������� '������'";
	}
	public MyRemoteImpl() throws RemoteException{}
	public static void main(String[] args){
		try{
			MyRemote service=new MyRemoteImpl();
			Naming.rebind("��������� ������",service);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}