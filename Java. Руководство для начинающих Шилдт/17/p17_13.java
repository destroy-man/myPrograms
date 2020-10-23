/*Глава 17.Вопрос 13
Преобразуйте Swing-nporpaммy для сравнения файлов из упражнения 16.1 в приложение
JavaFX. При этом воспользуйтесь предоставляемой в JavaFX возможностью
запускать события действий для кнопки программными средствами. Это
делается путем вызова метода fire () для экземпляра кнопки. К примеру, если
имеется экземпляр класса Button, который вы назвали myButton, то для запуска
событий необходимо использовать вызов myButton.fire(). Воспользуйтесь этим
при реализации обработчиков событий для текстовых полей, в которых хранятся
имена сравниваемых файлов. В тех случаях, когда пользователь нажимает клавишу
<Enter> и при этом фокус ввода находится в одном из указанных текстовых
полей, запускайте событие действия для кнопки Compare. После этого код обработчика
событий для кнопки Compare должен выполнить сравнение файлов.
*/
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import java.io.*;
public class p17_13 extends Application{
	TextField jtfFirst;
	TextField jtfSecond;
	Button jbtnComp;
	Label jlabFirst,jlabSecond;
	Label jlabResult;
	public void start(Stage myStage){
		myStage.setTitle("Compare files");
		FlowPane rootNode=new FlowPane(20,20);
		Scene myScene=new Scene(rootNode,200,250);
		myStage.setScene(myScene);
		jtfFirst=new TextField();
		jtfFirst.setPrefColumnCount(15);
		jtfSecond=new TextField();
		jtfSecond.setPrefColumnCount(15);
		Button jbtnComp=new Button("Compare");
		jlabFirst=new Label("First file: ");
		jlabSecond=new Label("Second file: ");
		jlabResult=new Label("");		
		jtfFirst.setOnAction(ae->jbtnComp.fire());
		jtfSecond.setOnAction(ae->jbtnComp.fire());
		jbtnComp.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent ae){
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
					do{
						i=f1.read();
						j=f2.read();
						if(i!=j)break;
					}while(i!=-1 && j!=-1);
					if(i!=j)
						jlabResult.setText("Files are not the same.");
					else
						jlabResult.setText("Files compare equal.");
				}
				catch(IOException exc){
					jlabResult.setText("File Error");
				}
			}
		});
		rootNode.getChildren().addAll(jlabFirst,jtfFirst,jlabSecond,jtfSecond,jbtnComp,jlabResult);		
		myStage.show();
	}
	public static void main(String[] args){
		launch(args);
	}
}