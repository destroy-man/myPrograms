/*Глава 17. Вопрос 10
Одним из способов прекращения работы автономного приложения JavaFX является
вызов метода Patform.exit(). Класс Platform находится в пакете javafx.
Application. При вызове метода exit() работа программы немедленно прекращается.
Учитывая это, измените программу JavaFXEventDemo, представленную в
данной главе, таким образом, чтобы она отображала две кнопки: Run и Exit. При
нажатии кнопки Run программа должна отобразить соответствующее сообщение
в метке. При нажатии кнопки Exit приложение должно завершить свою работу.
В обработчиках событий используйте лямбда-выражения.
*/
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
public class p17_10 extends Application{
	Label response;
	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage myStage){
		myStage.setTitle("Use JavaFX Buttons and Events.");
		FlowPane rootNode=new FlowPane(10,10);
		rootNode.setAlignment(Pos.CENTER);
		Scene myScene=new Scene(rootNode,300,100);
		myStage.setScene(myScene);
		response=new Label("Push a Button");
		Button btnRun=new Button("Run");
		Button btnExit=new Button("Exit");
		/*btnUp.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent ae){
				response.setText("You pressed Up.");
			}
		});
		btnDown.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent ae){
				response.setText("You pressed Down.");
			}
		});*/
		btnRun.setOnAction(ae->response.setText("Run!!!"));
		btnExit.setOnAction(ae->Platform.exit());
		rootNode.getChildren().addAll(btnRun,btnExit,response);
		myStage.show();
	}
}