class ErrorMsg{
	final int OUTERR=0;
	final int INERR=1;
	final int DISKERR=2;
	final int INDEXERR=3;
	String[] msgs={"Ошибка вывода","Ошибка ввода","Отсутствует место на диске","Выход индекса за границы диапозона"};
	String getErrorMsg(int i){
		if(i>=0 & i<msgs.length)return msgs[i];
		else return "Несуществующий код ошибки";
	}
}