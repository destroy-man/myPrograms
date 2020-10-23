/*Глава 15. Вопрос 3
Видоизмените аплет, созданный в упражнении 15.1, таким образом, чтобы в нем
отображалась строка, переданная ему в качестве параметра. Добавьте еще один
параметр, позволяющий задавать время задержки (в миллисекундах) между последовательными
сдвигами строки.
*/
import java.awt.*;
import java.applet.*;
/*
<applet code="p15_3" width=300 height=50>
	<param name=stroka value="transmitted string">
	<param name=timeSdvig value=1000>
</applet>
*/
public class p15_3 extends Applet implements Runnable{
	String msg;
	Thread t;
	boolean stopFlag;
	public void init(){
		t=null;
	}
	public void start(){
		msg=getParameter("stroka");
		t=new Thread(this);
		stopFlag=false;
		t.start();
	}
	public void run(){
		for(;;){
			try{
				repaint();
				int time=250;
				String temp=getParameter("timeSdvig");
				try{
					if(temp!=null)
						time=Integer.parseInt(temp);
				}
				catch(NumberFormatException exc){}
				Thread.sleep(time);
				if(stopFlag)
					break;
			}
			catch(InterruptedException exc){}
		}
	}
	public void stop(){
		stopFlag=true;
		t=null;
	}
	public void paint(Graphics g){
		char ch;
		if(msg!=null){
			ch=msg.charAt(0);
			msg=msg.substring(1,msg.length());
			msg+=ch;
			g.drawString(msg,50,30);
		}
		else g.drawString("String not found",50,30);
	}
}