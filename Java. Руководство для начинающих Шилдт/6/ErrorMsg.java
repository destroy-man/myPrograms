class ErrorMsg{
	String[] msgs={"������ ������","������ �����","����������� ����� �� �����","����� ������� �� ������� ���������"};
	String getErrorMsg(int i){
		if(i>=0 & i<msgs.length)return msgs[i];
		else return "�������������� ��� ������";
	}
}