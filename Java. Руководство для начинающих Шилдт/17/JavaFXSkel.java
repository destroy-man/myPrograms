import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
public class JavaFXSkel extends Application{
	public static void main(String[] args){
		System.out.println("Запуск приложения JavaFX");
		launch(args);
	}
	public void init(){
		System.out.println("В теле метода init()");
	}
	public void start(Stage myStage){
		System.out.println("В теле метода start()");
		myStage.setTitle("Каркас приложения JavaFX");
		FlowPane rootNode=new FlowPane();
		Scene myScene=new Scene(rootNode,300,200);
		myStage.setScene(myScene);
		myStage.show();
	}
	public void stop(){
		System.out.println("В теле метода stop()");
	}
}