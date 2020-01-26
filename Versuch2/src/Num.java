/*
	Num.java
	
	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2
	
	Diese Klasse repräsentiert als Unterklasse von Semantic die
	semantische Funktion der Regeln mit dem Nonterminal
	num auf der linken Seite. 

*/

class Num extends Semantic{
	
	//-------------------------------------------------------------------------
	// Berechnet die nächst größere 10er Potenz von v über die Rekursionstiefe
	// von num
	// Hilfsmethode für num.f
	//-------------------------------------------------------------------------
	private int potenz(SyntaxTree t){
		SyntaxTree ch;
		int p;
		ch=t;
		p=1;
		// Im Syntaxbaum von num solange nach unten steigen, bis Blatt erreicht
		while(ch.getChildNumber()!=0){
			// Regel num->digt num
			if (ch.getChildNumber()==2){
				ch=ch.getChild(1);
				p=p*10;
			// Regel num->digit (Blatt)
			}else{
				p=p*10;
				break;
			}
		}
		System.out.println("Potenz:"+p);
		return p;
	}//potenz
	
	
	//-------------------------------------------------------------------------
	// num -> digit num | digit                              n
	// num.f = digit.f*potenz(num)+num.f, mit potenz(num)= 10  , falls
	// die Tiefe des SyntaxBaums unter num die Tiefe n hat (Zahl hat n Stellen)
	// num -> digit
	// num.f=digit.f
	//-------------------------------------------------------------------------
	int f(SyntaxTree t, int n){
		if (t.getChildNumber()==2){
			SyntaxTree digit=t.getChild(0),
					             num=t.getChild(1);
			int v=num.value.f(num,UNDEFINED);
			return digit.value.f(digit,UNDEFINED)*potenz(num)+v;
		}else{
			SyntaxTree digit=t.getChild(0);
			return digit.value.f(digit,UNDEFINED);
		}
	}//f 	
}//Num