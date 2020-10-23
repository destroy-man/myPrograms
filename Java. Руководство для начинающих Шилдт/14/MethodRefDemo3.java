class MethodRefDemo3{
	public static void main(String[] args){
		boolean result;
		MyIntNum myNum=new MyIntNum(12);
		MyIntNum myNum2=new MyIntNum(16);
		MyIntNumPredicate inp=MyIntNum::isFactor;
		result=inp.test(myNum,3);
		if(result)System.out.println("3 является делителем "+myNum.getNum());
		result=inp.test(myNum2,3);
		if(!result)System.out.println("3 не является делителем "+myNum2.getNum());
	}
}