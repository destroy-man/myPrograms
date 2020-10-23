/*Глава 16. Вопрос 17
Добавьте в утилиту сравнения файлов, созданную в упражнении 16.1, флажок со
следующей пояснительной надписью: Show position of mismatch (Показывать
позицию расхождения). Если этот флажок установлен, программа должна отображать
позицию, в которой обнаружено первое расхождение в содержимом сравниваемых
файлов.
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
class p16_17 implements ActionListener{
	JTextField jtfFirst;
	JTextField jtfSecond;
	JButton jbtnComp;
	JLabel jlabFirst,jlabSecond;
	JLabel jlabResult;
	JCheckBox jcbMismatch;
	p16_17(){
		JFrame jfrm=new JFrame("Compare files");
		jfrm.setLayout(new FlowLayout());
		jfrm.setSize(200,190);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jtfFirst=new JTextField(14);
		jtfSecond=new JTextField(14);
		jtfFirst.setActionCommand("fileA");
		jtfSecond.setActionCommand("fileB");
		JButton jbtnComp=new JButton("Compare");
		jbtnComp.addActionListener(this);
		jlabFirst=new JLabel("First file: ");
		jlabSecond=new JLabel("Second file: ");
		jlabResult=new JLabel("");
		jcbMismatch=new JCheckBox("Show position of mismatch");
		jfrm.add(jcbMismatch);
		jfrm.add(jlabFirst);
		jfrm.add(jtfFirst);
		jfrm.add(jlabSecond);
		jfrm.add(jtfSecond);
		jfrm.add(jbtnComp);
		jfrm.add(jlabResult);
		jfrm.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae){
		int i=0,j=0;
		if(jtfFirst.getText().equals("")){
			jlabResult.setText("First file name missing.");
			return;
		}
		if(jtfSecond.getText().equals("")){
			jlabResult.setText("Second file name missing.");
			return;
		}
		try(FileInputStream f1=new FileInputStream(jtfFirst.getText());
		    FileInputStream f2=new FileInputStream(jtfSecond.getText())){
			int numPosition=0;
			do{
				numPosition++;
				i=f1.read();
				j=f2.read();
				if(i!=j)break;
			}while(i!=-1 && j!=-1);
			if(i!=j && !jcbMismatch.isSelected())
				jlabResult.setText("Files are not the same.");
			else if(i!=j && jcbMismatch.isSelected())
				jlabResult.setText("Files are not the same. First mismatch is position: "+numPosition);
			else
				jlabResult.setText("Files compare equal.");
		}
		catch(IOException exc){
			jlabResult.setText("File Error");
		}
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new p16_17();
			}
		});
	}
}