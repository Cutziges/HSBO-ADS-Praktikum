/*
	Digit.java
	
	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2
	
	Diese Klasse repräsentiert als Unterklasse von Semantic die
	semantische Funktion der Regeln mit dem Nonterminal
	digit auf der linken Seite. 

*/

class Digit extends Semantic{
	//-------------------------------------------------------------------------
	// digit -> '1' | '2' | '3' | '4' | '5' |'6' | '7' | '8' | '9' | '0'
	// digit.f = 1; falls charcter=='1'
	// ...
	// digit.f = 9; falls charcter=='9'	
	// digit.f = 0; falls charcter=='0'	
	//-------------------------------------------------------------------------
	int f(SyntaxTree t, int n){
		SyntaxTree symbol=t.getChild(0);
		switch(symbol.getCharacter()){
			case '0' : 	return 0;
			case '1' : 	return 1;
			case '2' : 	return 2;
			case '3' : 	return 3;
			case '4' : 	return 4;
			case '5' : 	return 5;
			case '6' : 	return 6;
			case '7' : 	return 7;
			case '8' : 	return 8;
			case '9' : 	return 9;
		default: return UNDEFINED;} //Fehler Fall
	}//f 	
}//Digit