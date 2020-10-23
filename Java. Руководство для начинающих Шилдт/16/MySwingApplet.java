import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
<object code="MySwingApplet" width=200 height=80>
</object>
*/
public class MySwingApplet extends JApplet{
	JButton jbtnUp;
	JButton jbtnDown;
	JLabel jlab;
	public void init(){
		try{
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					makeGUI();
				}
			});
		}
		catch(Exception exc){
			System.out.println("Can't create because of "+exc);
		}
	}
	private void makeGUI(){
		setLayout(new FlowLayout());
		jbtnUp=new JButton("Up");
		jbtnDown=new JButton("Down");
		jbtnUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jlab.setText("You pressed Up.");
			}
		});
		jbtnDown.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jlab.setText("You pressed down.");
			}
		});
		add(jbtnUp);
		add(jbtnDown);
		jlab=new JLabel("Press a button.");
		add(jlab);
	}
}