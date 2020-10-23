/*Глава 15. Вопрос 10
Попытайтесь ввести поддержку события типа
MouseWheelEvent в пример аплета MouseEvents, рассмотренный в разделе "Применение
модели делегирования событий". Для этого реализуйте интерфейс
MouseWheelListener и сделайте аплет слушателем события, связанного с прокруткой
колесика мыши, воспользовавшись методом addМouseWheelListener().
В процессе выполнения этого задания вам придется обратиться к документации
API языка Java и ознакомиться с вышеупомянутыми средствами обработки подобных
событий. На этот вопрос ответа не дается, поэтому вам придется призвать
на помощь все свои знания и навыки, чтобы найти собственное решение.
*/
import java.awt.event.*;
import java.applet.*;
import java.awt.*;
/*
<applet code="p15_10" width=300 height=100>
</applet>
*/
public class p15_10 extends Applet implements MouseListener,MouseMotionListener,MouseWheelListener{
	String msg="";
	int mouseX=0,mouseY=0,numberScrolls=0;
	public void init(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void mouseClicked(MouseEvent me){
		mouseX=0;
		mouseY=10;
		msg="Mouse clicked.";
		repaint();
	}
	public void mouseEntered(MouseEvent me){
		mouseX=0;
		mouseY=10;
		msg="Mouse entered.";
		repaint();
	}
	public void mouseExited(MouseEvent me){
		mouseX=0;
		mouseY=10;
		msg="Mouse exited.";
		repaint();
	}
	public void mousePressed(MouseEvent me){
		mouseX=me.getX();
		mouseY=me.getY();
		msg="Down";
		repaint();
	}
	public void mouseReleased(MouseEvent me){
		mouseX=me.getX();
		mouseY=me.getY();
		msg="Up";
		repaint();
	}
	public void mouseDragged(MouseEvent me){
		mouseX=me.getX();
		mouseY=me.getY();
		msg="*";
		showStatus("Dragging mouse at "+mouseX+", "+mouseY);
		repaint();
	}
	public void mouseMoved(MouseEvent me){
		showStatus("Moving mouse at "+me.getX()+", "+me.getY());
	}
	public void mouseWheelMoved(MouseWheelEvent me){
		numberScrolls+=me.getWheelRotation();
	}
	public void paint(Graphics g){
		g.drawString(msg,mouseX,mouseY);
		g.drawString(""+numberScrolls,10,10);
	}
}