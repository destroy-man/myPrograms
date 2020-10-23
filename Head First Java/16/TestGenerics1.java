import java.io.*;
public class TestGenerics1{
	public static void main(String[] args){
		new TestGenerics1().go();
	}
	public void go(){
		Dog[] dogs={new Dog(),new Dog(),new Dog()};
		takeAnimals(dogs);
	}
	public void takeAnimals(Animal[] animals){
		animals[0]=new Cat();
	}
}