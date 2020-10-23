/*����� 16. ������ 19
������������ ����� Help, ��������� � ����������
4.1, � ��������� Swing � ����������� ���������������� �����������. ��������
� �������� ������ (for, while, switch � �.�.) ������ ������������ � �������
���������� JList. ��� ������ ������������� �������� ������ ������
���������� �������� ���������� ���������� ��������� �����. ��� �����������
������������r� ������ ������ ����� ����� ������������ �������� HTML. � ����
������ ����� ������ ���������� � ����������� <html> � ����������� ������������
</html>. � ����� ����� ����� ������������� �������� � ���� ���L-���������.
������ ������ �����������, ����� �������� ������ ��������� ��������� ���r���������
�����. � �������� ������� ���� ��������� ������ ����, � �������
��������� �����, ������������ ��� ��������� ������: ������ ��������� ������
"���", � ��� ��� - ������ "Bottom".
JLabel jlabhtml = new JLabel("<html>Top<br>Bottom</html>");
������� ����� ���������� �� ����������. ���� ������� ����� ���������� ���
��������� �����, ��� �� � ��������� �������������� ������������� ���������
�� Java!
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
					s="if(�������) ��������;\nelse ��������;";
					lab.setText(s);
					break;
				case 1:
					s="switch(���������) {\n";
					s+="	case ���������:\n";
					s+="	������������������ ����������\n";
					s+="	break;\n";
					s+="	// ...\n";
					s+="}";
					lab.setText(s);
					break;
				case 2:
					s="for(�������������; �������; ��������)\n";
					s+="	��������;";
					lab.setText(s);
					break;
				case 3:
					s="while(�������) ��������;";
					lab.setText(s);
					break;
				case 4:
					s="do {\n";
					s+="	��������;\n";
					s+="} while(�������);";
					lab.setText(s);
					break;
				case 5:
					s="break; ��� break �����;";
					lab.setText(s);
					break;
				case 6:
					s="continue; ��� continue �����;";
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