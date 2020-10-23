class Gen<T extends Number>{
	T ob;
	T[] vals;
	Gen(T o,T[] nums){
		ob=o;
		vals=nums;
	}
	T getob(){
		return ob;
	}
}