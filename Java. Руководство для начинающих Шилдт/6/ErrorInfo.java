class ErrorInfo{
	String[] msgs={"������ ������","������ �����","����������� ���� �� �����","����� ������� �� ������� ���������"};
	int[] howbad={3,3,2,4};
	Err getErrorInfo(int i){
		if(i>=0 & i<msgs.length)return new Err(msgs[i],howbad[i]);
		else return new Err("�������������� ��� ������",0);
	}
}