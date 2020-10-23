/*Глава 16. Вопрос 18
Измените программу ListDemo таким образом, чтобы она допускала выбор нескольких
элементов списка.
*/
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
class p16_18 implements ListSelectionListener{
	JList<String> jlst;
	JLabel jlab;
	JScrollPane jscrlp;
	String[] names={"Sherry","Jon","Rachel","Sasha","Josselyn","Randy",
			"Tom","Mary","Ken","Andrew","Matt","Todd"};
	p16_18(){
		JFrame jfrm=new JFrame("JList Demo");
		jfrm.setLayout(new FlowLayout());
		jfrm.setSize(200,160);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jlst=new JList<String>(names);
		jlst.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jscrlp=new JScrollPane(jlst);
		jscrlp.setPreferredSize(new Dimension(120,90));
		jlab=new JLabel("Please choose a name");
		jlst.addListSelectionListener(this);
		jfrm.add(jscrlp);
		jfrm.add(jlab);
		jfrm.setVisible(true);
	}
	public void valueChanged(ListSelectionEvent le){
		int[] idx=jlst.getSelectedIndices();
		if(idx!=null){
			String selectNames="Current selection:";
			for(int i=0;i<idx.length;i++)
				selectNames+=" "+names[idx[i]];
			jlab.setText(selectNames);			
		}
		else
			jlab.setText("Please choose a name");
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new p16_18();
			}
		});
	}
}