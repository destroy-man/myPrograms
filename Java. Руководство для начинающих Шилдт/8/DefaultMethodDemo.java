class DefaultMethodDemo{
	public static void main(String[] args){
		MyIFImp obj=new MyIFImp();
		System.out.println("Идентификатор пользователя: "+obj.getUserID());
		System.out.println("Идентификатор администратора: "+obj.getAdminID());
	}
}