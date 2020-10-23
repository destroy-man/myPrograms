/*Глава 6. Вопрос 6
Напишите рекурсивный метод, отображающий символы, составляющие строку в
обратном порядке.
*/
class p6_6{
	public static void main(String[] args){
		String str="abcde";
		String rev="";
		int n=str.length()-1;
		rev=reverse(str,rev,n);
		System.out.println(rev);			
	}
	public static String reverse(String str,String rev,int n){
		if(n==-1)return rev;
		char ch=str.charAt(n);
		rev+=ch+reverse(str,rev,n-1);
		return rev;
	}
}