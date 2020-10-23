/*Глава 5. Вопрос 4
Измените программу, написанную в упражнении 5.1, таким образом, чтобы она
сортировала массив символьных строк. Продемонстрируйте ее работоспособность.
*/
class p5_4{
	public static void main(String[] args){
		String[] str={"de","bc","cd","ab","ef"};
		int a,b;
		String str1;
		int size;
		size=5;
		System.out.print("Исходный массив:");
		for(int i=0;i<size;i++)
			System.out.print(" "+str[i]);
		System.out.println();
		for(a=1;a<size;a++)
			for(b=size-1;b>=a;b--){
				//if(str[b-1]>str[b]){
				if(str[b-1].compareTo(str[b])>0){
					str1=str[b-1];
					str[b-1]=str[b];
					str[b]=str1;
				}
			}
		System.out.print("Отсортированный массив:");
		for(int i=0;i<size;i++)
			System.out.print(" "+str[i]);
		System.out.println();
	}
}