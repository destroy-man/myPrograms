class FileHelp{
	public static void main(String[] args){
		Help hlpobj=new Help("helpfile.txt");
		String topic;
		System.out.println("�������������� ���������� ��������.\n��� ������ �� ������� ������� 'stop'.");
		do{
			topic=hlpobj.getSelection();
			if(!hlpobj.helpon(topic))
				System.out.println("���� �� �������.\n");
		}while(topic.compareTo("stop")!=0);
	}
}