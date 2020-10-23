class MixFor5{
	public static void main(String[] args){
		int x=0;
		int y=30;
		for(int outer=0;outer<3;outer++){
			for(int inner=4;inner>1;inner--){
				x+=0;
				y-=2;
				if(x==6)break;
				x+=3;
			}
			y-=2;
		}
		System.out.println(x+" "+y);
	}
}
/*
x+=3;
54 6

x+=6;
60 10

x+=2;
45 6

x++;
36 6

x--;
18 6

x+=0;
6 14
*/