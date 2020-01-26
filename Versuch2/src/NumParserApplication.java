/*
	ArithmetikParserApplikation.java
	
	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2
	
	Diese Java Klasse implementiert die Applikation eines
	einfachen Parsers zum Erkennen von Ziffernfolgen
	Ausdrücke.
	
	Der eigentliche Parser wird in der Klasse NumParserClass
	defifiert.
	
*/


class NumParserApplication implements TokenList{
	public static void main(String args[]){
		
		// Anlegen des Wurzelknotens für den Syntaxbaum. Dem Konstruktor
		// wid als Token das Startsymbol der Grammatik übergeben
		SyntaxTree parseTree = new SyntaxTree(NUM);
		
		// Anlegen des Parsers als Instanz der Klasse ArithmetikParserClass
		NumParserClass parser = new NumParserClass(parseTree);

		// Einlesen der Datei 		
		if (parser.readInput("testdatei_num.txt"))
			//Aufruf des Parsers und Test, ob gesamte Eingabe gelesen
			if (parser.num(parseTree)&& parser.inputEmpty()){
				//Ausgabe des Syntaxbaumes und des sematischen Wertes
				parseTree.printSyntaxTree(0);
				System.out.println("Korrekter Ausdruck mit Wert:"
				+parseTree.value.f(parseTree,UNDEFINED));

			}else
				//Fehlermeldung, falls Ausdruck nicht zu parsen war
				System.out.println("Fehler im Ausdruck");	
	}//main
}//ArithmetikParserApplikation