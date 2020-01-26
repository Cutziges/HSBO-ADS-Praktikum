import java.io.*;

/*
	NumParserClass.java
	
	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2
	
	Diese Java Klasse implementiert einen
	einfachen Parser zum Erkennen von Ziffernfolgen
	gemäß der folgenden Grammatik:
	
	num -> digit num | digit
	digit -> '1' | '2' | '3' | '4' | '5' |'6' | '7' | '8' | '9' | '0'
	
	Epsilon steht hier für das "leere Wort"
	
	Der Parser ist nach dem Prinzip des rekursiven Abstiegs programmiert,
	d.h. jedes nicht terminale Symbol der Grammatik wird durch eine 
	Methode in Java repräsentiert, die die jeweils anderen nicht terminalen
	Symbole auf der rechten Seite der Grammatik Regeln ggf. auch rekursiv
	aufruft.
	
	Der zu parsende Ausdruck wird aus einer Datei gelesen und in einem
	Array of Char abgespeichert. Pointer zeigt beim Parsen auf den aktuellen
	Eingabewert.
	
	Ist der zu parsende Ausdruck syntaktisch nicht korrekt, so werden 
	über die Methode syntaxError() entsprechende Fehlermeldungen ausgegeben.
	
	Zusätzlich werden den Methoden der Klasse neben der Rekursionstiefe auch
	eine Referenz auf eine Instanz der Klasse SyntaxTree übergeben.
	
	Über die Instanzen der Klasse SyntaxTree wird beim rekursiven Abstieg
	eine konkreter Syntaxbaum des geparsten Ausdrucks aufgebaut.
	
	Dieser Syntaxbaum kann dann zur Semantischen Analyse des Ausdrucks mit
	Hilfe der semantschen Fuktionen seiner Knoten genutzt werden. 

*/

public class NumParserClass implements TokenList{
	// Konstante für Ende der Eingabe
	public final char EOF=(char)255;
	// Zeiger auf das aktuelle Eingabezeichen
	private int pointer;
	// Zeiger auf das Ende der Eingabe
	private int maxPointer;
	// Eingabe zeichenweise abgelegt
	private char input[];
	// Syntaxbaum
	private SyntaxTree parseTree;
	
	//-------------------------------------------------------------------------
	//------------Konstruktor der Klasse ArithmetikParserClass-----------------
	//-------------------------------------------------------------------------
	
	NumParserClass(SyntaxTree parseTree){
		this.parseTree=parseTree;
		this.input = new char[256];
		this.pointer=0;
		this.maxPointer=0;
	}
	
	//-------------------------------------------------------------------------
	//-------------------Methoden der Grammatik--------------------------------
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------			
	// num -> digit num | digit
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean num(SyntaxTree sT){
		char [] digitSet = {'1','2','3','4','5','6','7','8','9','0'};

   		if (lookAhead(digitSet))
   			//num->digit num
    		return digit(sT.insertSubtree(DIGIT))&& num(sT.insertSubtree(NUM));		 
   		else 
   			//num->digit
     		return digit(sT.insertSubtree(DIGIT));					   
	}//num

	
	//-------------------------------------------------------------------------	
	// digit -> '1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean digit(SyntaxTree sT){
		char [] matchSet = {'1','2','3','4','5','6','7','8','9','0'};

		if (match(matchSet,sT)){	//digit->'1'|'2'...|'9'|'0'
      		return true;            // korrekte Ableitung der Regel mäglich
   		}else{
      		syntaxError("Ziffer erwartet"); // korrekte Ableitung der Regel  
      		return false;                   // nicht mäglich
   		}
	}//digit
	
	//-------------------------------------------------------------------------
	//-------------------Hilfsmethoden-----------------------------------------
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------		
	// Methode, die testet, ob das aktuele Eingabezeichen unter den Zeichen
	// ist, die als Parameter (matchSet) übergeben wurden.
	// Ist das der Fall, so gibt match() true zurück und setzt den Eingabe-
	// zeiger auf das nächste Zeichen, sonst wird false zurückgegeben.
	//-------------------------------------------------------------------------
	boolean match(char [] matchSet, SyntaxTree sT){
		SyntaxTree node;
		for (int i=0;i<matchSet.length;i++)
			if (input[pointer]==matchSet[i]){
				//Eingabezeichen als entsprechendem Knoten des Syntaxbaumes
				//eintragen
				node=sT.insertSubtree(INPUT_SIGN);
				node.setCharacter(input[pointer]);

				pointer++;	//Eingabepointer auf das nächste Zeichen setzen 
				return true;
			}
		return false;
	}//match
	
	//-------------------------------------------------------------------------
	//Methode, die testet, ob das auf das aktuelle Zeichen folgende Zeichen
	//unter den Zeichen ist, die als Parameter (aheadSet) übergeben wurden.
	//Der Eingabepointer wird nicht verändert!
	//-------------------------------------------------------------------------
	boolean lookAhead(char [] aheadSet){
		for (int i=0;i<aheadSet.length;i++)
			if (input[pointer+1]==aheadSet[i])
				return true;
		return false;
	}//lookAhead
	

	//-------------------------------------------------------------------------
	// Methode zum zeichenweise Einlesen der Eingabes aus
	// einer Eingabedatei mit dem Namen "testdatei.txt".
	// Die Metode berücksichtigt beim Einlesen schon die maximale Grösse
	// des Arrays input von 256 Zeichen.
	// Das Ende der Eingabe wird mit EOF markiert
	//-------------------------------------------------------------------------
	boolean readInput(String name){
		int c=0;
		try{
			FileReader f=new FileReader(name);
			for(int i=0;i<256;i++){
				c = f.read();
				if (c== -1){
					maxPointer=i;
					input[i]=EOF;
					break;
				}else
					input[i]=(char)c;
			} 
		}
		catch(Exception e){
			System.out.println("Fehler beim Dateizugriff: "+name);
			return false;
		}
		return true;	
	}//readInput


	//-------------------------------------------------------------------------	
	// Methode, die testet, ob das Ende der Eingabe erreicht ist
	// (pointer == maxPointer)
	//-------------------------------------------------------------------------
	boolean inputEmpty(){
		if (pointer==maxPointer){
			ausgabe("Eingabe leer!",0);
			return true;
		}else{
			syntaxError("Eingabe bei Ende des Parserdurchlaufs nicht leer");
			return false;
		}
		
	}//inputEmpty


	//-------------------------------------------------------------------------	
	// Methode zum korrekt eingerückten Ausgeben des Syntaxbaumes auf der 
	// Konsole 
	//-------------------------------------------------------------------------
	void ausgabe(String s, int t){
		for(int i=0;i<t;i++)
		  System.out.print("  ");
		System.out.println(s);
	}//ausgabe

	//-------------------------------------------------------------------------
	// Methode zum Ausgeben eines Syntaxfehlers mit Angabe des vermuteten
	// Zeichens, bei dem der Fehler gefunden wurde 
	//-------------------------------------------------------------------------
	void syntaxError(String s){
		char z;
		if (input[pointer]==EOF)
			System.out.println("Syntax Fehler beim "+(pointer+1)+". Zeichen: "
							+"EOF");
		else
			System.out.println("Syntax Fehler beim "+(pointer+1)+". Zeichen: "
							+input[pointer]);
		System.out.println(s);	
	}//syntaxError

}//ArithmetikParserClass