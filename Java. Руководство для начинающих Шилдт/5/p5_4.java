/*����� 5. ������ 4
�������� ���������, ���������� � ���������� 5.1, ����� �������, ����� ���
����������� ������ ���������� �����. ����������������� �� �����������������.
*/
class p5_4{
	public static void main(String[] args){
		String[] str={"de","bc","cd","ab","ef"};
		int a,b;
		String str1;
		int size;
		size=5;
		System.out.print("�������� ������:");
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
		System.out.print("��������������� ������:");
		for(int i=0;i<size;i++)
			System.out.print(" "+str[i]);
		System.out.println();
	}
}