class StringDemo{
	public static void main(String[] args){
		String str1=new String("В Java строки - объекты.");
		String str2="Их можно создавать разными способами.";
		String str3=new String(str2);
		System.out.println(str1);
		System.out.println(str2);
		System.out.println(str3);
	}
}