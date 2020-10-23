/*Глава 15.Вопрос 4
Создайте аплет, который отображает текущее
время, обновляя содержимое окна каждую секунду. Для того чтобы справиться
с этим заданием, вам придется провести дополнительное исследование. Вот
подсказка: получить текущее время можно, воспользовавшись объектом класса
Calendar, входящего в пакет java.util. (Напомним, что компания Oracle предоставляет
онлайновую документацию по всем стандартным классам Java.) Приобретенных
вами к этому моменту знаний должно быть достаточно для того, чтобы
самостоятельно изучить класс Calendar и использовать ero методы для выполнения
задания.
*/
/*
<applet code="p15_4" width=300 height=50>
</applet>
*/
import java.util.*;
import java.awt.*;
import java.applet.*;
public class p15_4 extends Applet implements Runnable{
	String msg;
	Thread t;
	boolean stopFlag;
	public void init(){
		t=null;
	}
	public void start(){
		stopFlag=false;
		t=new Thread(this);
		GregorianCalendar cal=new GregorianCalendar();
		msg=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		t.start();
	}
	public void run(){
		for(;;){
			try{
				repaint();
				Thread.sleep(1000);
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
		GregorianCalendar cal=new GregorianCalendar();
		msg=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		g.drawString(msg,50,30);
	}
}