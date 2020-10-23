public class HfjEnum{
	enum Names{
		JERRY("соло-гитара")
		{
			public String sings(){
				return "грустно";
			}
		},
		BOBBY("ритм-гитара")
		{
			public String sings(){
				return "грубо";
			}
		},
		PHIL("бас-гитара");
		private String instrument;
		Names(String instrument){
			this.instrument=instrument;
		}
		public String getInstrument(){
			return this.instrument;
		}
		public String sings(){
			return "редко";
		}
	}
	public static void main(String[] args){
		for(Names n:Names.values()){
			System.out.print(n);
			System.out.print(", инструмент: "+n.getInstrument());
			System.out.println(", звучит: "+n.sings());
		}
	}
}