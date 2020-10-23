/*Глава 16. Вопрос 19
Преобразуйте класс Help, созданный в упражнении
4.1, в программу Swing с графическим пользовательским интерфейсом. Сведения
о ключевых словах (for, while, switch и т.д.) должны отображаться с помощью
компонента JList. При выборе пользователем элемента списка должно
выводиться описание синтаксиса выбранного ключевого слова. Для отображения
многострочноrо текста вместо метки можно использовать средства HTML. В этом
случае текст должен начинаться с дескриптора <html> и завершаться дескриптором
</html>. В итоге текст будет автоматически размечен в виде НТМL-документа.
Помимо прочих преимуществ, такая разметка текста позволяет создавать мноrострочные
метки. В качестве примера ниже приведена строка кода, в которой
создается метка, отображающая две текстовые строки: первой выводится строка
"Тор", а под ней - строка "Bottom".
JLabel jlabhtml = new JLabel("<html>Top<br>Bottom</html>");
Решение этого упражнения не приводится. Ведь уровень вашей подготовки уже
настолько высок, что вы в состоянии самостоятельно разрабатывать программы
на Java!
*/
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
class p16_19 implements ListSelectionListener{
	JList jlst;
	JLabel lab;
	String[] operators={"if","switch","for","while","do-while","break","continue"};
	p16_19(){
		JFrame frame=new JFrame("Help");
		frame.setLayout(new FlowLayout());
		frame.setSize(300,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jlst=new JList(operators);
		jlst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lab=new JLabel("Choose operator");
		JScrollPane jsp=new JScrollPane(jlst);
		jsp.setPreferredSize(new Dimension(200,100));
		jlst.addListSelectionListener(this);
		frame.add(jsp);
		frame.add(lab);
		frame.setVisible(true);
	}
	public void valueChanged(ListSelectionEvent le){
		int idx=jlst.getSelectedIndex();
		String s="";
		if(idx!=-1)
			switch(idx){
				case 0:
					s="if(условие) оператор;\nelse оператор;";
					lab.setText(s);
					break;
				case 1:
					s="switch(выражение) {\n";
					s+="	case константа:\n";
					s+="	последовательность операторов\n";
					s+="	break;\n";
					s+="	// ...\n";
					s+="}";
					lab.setText(s);
					break;
				case 2:
					s="for(инициализация; условие; итерация)\n";
					s+="	оператор;";
					lab.setText(s);
					break;
				case 3:
					s="while(условие) оператор;";
					lab.setText(s);
					break;
				case 4:
					s="do {\n";
					s+="	оператор;\n";
					s+="} while(условие);";
					lab.setText(s);
					break;
				case 5:
					s="break; или break метка;";
					lab.setText(s);
					break;
				case 6:
					s="continue; или continue метка;";
					lab.setText(s);
					break;
			}
		else
			lab.setText("Please choose operator");
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new p16_19();
			}
		});
	}
}