import java.io.*;
class RWData{
	public static void main(String[] args){
		int i=10;
		double d=1023.56;
		boolean b=true;
		try(DataOutputStream dataOut=new DataOutputStream(new FileOutputStream("testdata"))){
			System.out.println("��������: "+i);
			dataOut.writeInt(i);
			System.out.println("��������: "+d);
			dataOut.writeDouble(d);
			System.out.println("��������: "+b);
			dataOut.writeBoolean(b);
			System.out.println("��������: "+12.2*7.4);
			dataOut.writeDouble(12.2*7.4);
		}
		catch(IOException exc){
			System.out.println("������ ��� ������");
			return;
		}
		System.out.println();
		try(DataInputStream dataIn=new DataInputStream(new FileInputStream("testdata"))){
			i=dataIn.readInt();
			System.out.println("���������: "+i);
			d=dataIn.readDouble();
			System.out.println("���������: "+d);
			b=dataIn.readBoolean();
			System.out.println("���������: "+b);
			d=dataIn.readDouble();
			System.out.println("���������: "+d);
		}
		catch(IOException exc){
			System.out.println("������ ��� ������");
		}
	}
}