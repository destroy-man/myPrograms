public class PhraseOMatic{
	public static void main(String[] args){
		String[] wordListOne={"круглосуточный","трех-звенный","30000-футовый","взаимный","обоюдный выигрыш","фронтэнд","на основе веб-технологий","проникающий",
		"умный","шесть сигм","метод критического пути","динамический"};
		String[] wordListTwo={"уполномоченный","трудный","чистый продукт","ориентированный","центральный","распределенный","кластеризированный","фирменный",
		"нестандартный ум","позиционированный","сетевой","сфокусированный","использованный с выгодой","выровненный","выделенный на","общий","совместный",
		"ускоренный"};
		String[] wordListThree={"процесс","пункт разгрузки","выход из положени€","тип структуры","талант","подход","уровень завоеванного внимани€","портал",
		"период времени","обзор","образец","пункт следовани€"};
		int oneLength=wordListOne.length;
		int twoLength=wordListTwo.length;
		int threeLength=wordListThree.length;
		int rand1=(int)(Math.random()*oneLength);
		int rand2=(int)(Math.random()*twoLength);
		int rand3=(int)(Math.random()*threeLength);
		String phrase=wordListOne[rand1]+" "+wordListTwo[rand2]+" "+wordListThree[rand3];
		System.out.println("¬се, что нам нужно, - это "+phrase);
	}
}