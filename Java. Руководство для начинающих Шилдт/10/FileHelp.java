class FileHelp{
	public static void main(String[] args){
		Help hlpobj=new Help("helpfile.txt");
		String topic;
		System.out.println("Воспользуйтесь справочной системой.\nДля выхода из системы введите 'stop'.");
		do{
			topic=hlpobj.getSelection();
			if(!hlpobj.helpon(topic))
				System.out.println("Тема не найдена.\n");
		}while(topic.compareTo("stop")!=0);
	}
}