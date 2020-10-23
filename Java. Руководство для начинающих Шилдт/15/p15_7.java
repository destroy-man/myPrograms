/*Глава 15. Вопрос 7
Среди языковых средств Java, предназначенных
для отображения информации, имеется метод drawLine(). Метод определен
в классе Graphics и позволяет отобразить отрезок прямой линии между
двумя заданными точками с использованием текущего цвета. Используя метод
drawLine(), создайте аплет, отслеживающий перемещение мыши. Если кнопка
мыши нажата, аплет должен рисовать на экране сплошную линию до тех пор,
пока кнопка не будет отпущена.
*/
/*
<applet code="p15_7" width=300 height=50>
</applet>
*/
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
public class p15_7 extends Applet implements MouseListener,MouseMotionListener{
	int mouseX1=0,mouseY1=0,mouseX2=0,mouseY2=0;
	public void init(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mouseMoved(MouseEvent me){}
	
	public void mousePressed(MouseEvent me){
		mouseX1=me.getX();
		mouseY1=me.getY();
		repaint();
	}
	public void mouseReleased(MouseEvent me){
		mouseX1=0;
		mouseY1=0;
		mouseX2=0;
		mouseY2=0;
		repaint();
	}
	public void mouseDragged(MouseEvent me){
		mouseX2=me.getX();
		mouseY2=me.getY();
		repaint();
	}
	public void paint(Graphics g){
		g.drawLine(mouseX1,mouseY1,mouseX2,mouseY2);
	}
}