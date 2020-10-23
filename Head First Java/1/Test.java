class Test{
	public static void main(String[] args){
		int x=0;
		int y=0;
		while(x<5){
			if(y<5){
				x++;
				if(y<3)x--;
			}
			y+=2;
			System.out.print(x+""+y+" ");
			x++;
		}
	}
}
/*
y=x-y;
00 11 21 32 42

y+=x;
00 11 23 36 410

y+=2;
if(y>4)y--;
02 14 25 36 47

x++;
y+=x;
11 34 59

if(y<5){
	x++;
	if(y<3)x--;
}
y+=2;
02 14 36 48
*/