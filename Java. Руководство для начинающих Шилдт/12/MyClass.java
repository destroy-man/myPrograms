@Deprecated
class MyClass{
	private String msg;
	MyClass(String m){
		msg=m;
	}
	@Deprecated
	String getMsg(){
		return msg;
	}
}