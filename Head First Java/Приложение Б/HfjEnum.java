public class HfjEnum{
	enum Names{
		JERRY("����-������")
		{
			public String sings(){
				return "�������";
			}
		},
		BOBBY("����-������")
		{
			public String sings(){
				return "�����";
			}
		},
		PHIL("���-������");
		private String instrument;
		Names(String instrument){
			this.instrument=instrument;
		}
		public String getInstrument(){
			return this.instrument;
		}
		public String sings(){
			return "�����";
		}
	}
	public static void main(String[] args){
		for(Names n:Names.values()){
			System.out.print(n);
			System.out.print(", ����������: "+n.getInstrument());
			System.out.println(", ������: "+n.sings());
		}
	}
}