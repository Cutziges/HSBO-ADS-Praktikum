import java.io.*;

/*
  NumParser.java
  Beispiel zur Vorlesung
  
  Realisiert die folgende kontextfreie Grammatik für Strings aus Ziffern
  num -> digit num | num
  digit -> '1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'
  
  Der Parser ist nach dem Prinzip des rekursiven Abstiegs programmiert,
  d.h. jedes nicht terminale Symbol der Grammatik wird durch eine 
  Methode in Java repräsentiert, die die jeweils anderen nicht terminalen
  Symbole auf der rechten Seite der Grammatik Regeln ggf. auch rekursiv
  aufruft.
  
  Der zu parsende Ausdruck wird aus einer Datei gelesen und in einem
  Array of Char abgespeichert. Pointer zeigt beim Parsen auf den aktuellen
  Eingabewert.
  
  Ist der zu parsende Ausdruck syntaktisch nicht korrekt, so werden 
  über die Methode syntaxError() entsprechende Fehlermeldungen ausgegeben

*/

public class parser{
  // Konstante für Ende der Eingabe

  // Anfang Attribute
  static final char EOF=(char)255;
  // Zeiger auf das aktuelle Eingabezeichen
  static int pointer = 0;
  // Zeiger auf das Ende der Eingabe
  static int maxPointer = 0;
  // Eingabe zeichenweise abgelegt
  static char input[];
  // Ende Attribute
  
  // Anfang eigene Attribute
  static int klammer = 0;
  // Ende eigene Attribute


  //----------------- EIGENER TEIL - BEARBEITUNG AUFGABE 1 ------------------
  //-------------------------------------------------------------------------
  //-------------------------------------------------------------------------
  // 						Author: Sarah Grugiel
  //-------------------------------------------------------------------------
  //-------------------------------------------------------------------------
  
  //-------------------------------------------------------------------------
  // rightExpression -> '+' oder '-' term rightExpression | Epsilon
  //
  // Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------

  static boolean expression(int t){
	ausgabe("expression ->", t);
	//-> term rightExpression
	return term(t+1)&& rightExpression(t+1);
  }//expression
  
  
  
  //-------------------------------------------------------------------------
  // rightExpression -> '+' oder '-' term rightExpression | Epsilon
  //
  // Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------

  static boolean rightExpression(int t){
	char [] matchSet={'+','-'};
	char [] addsubSet={'+','-'};
		if (lookAhead(addsubSet)){
			ausgabe("rightExpression ->",t);
		//-> '+' oder '-' term rightExpression
			return match(matchSet,t+1) &&term(t+1) &&rightExpression(t+1);
		}
         else{
        	 ausgabe("Epsilon",t);
        	 return true;
         }// Epsilon
  }//rightExpression



  //-------------------------------------------------------------------------
  //term -> operator rightTerm
  //
  //Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------

  static boolean term(int t){
	  ausgabe("term ->", t);
	return operator(t) &&rightTerm(t);
  }//term



  //-------------------------------------------------------------------------
  //rightTerm -> '*' oder '/' operator rightTerm | Epsilon
  //
  //Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------

  static boolean rightTerm(int t){
	char [] matchSet={'*','/'};
	char [] muldivSet={'*','/'};
		if (lookAhead(muldivSet)){
			ausgabe("rightTerm ->",t);
		//-> '*'oder '/' operator rightTerm
			return match(matchSet,t+1) &&operator(t+1) &&rightTerm(t+1);
		}
		else{
     	 ausgabe("Epsilon",t);
     	 return true;
     	 } // Epsilon
  }//rightTerm



  //-------------------------------------------------------------------------
  //operator -> '('expression')' | num
  //
  //Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------

  static boolean operator(int t){
	char [] klammerSet={'('};
	ausgabe("operator ->", t); // operator
		if (lookAhead(klammerSet)){
			klammer = klammer+1;
			ausgabe("(expression) ->",t+1);
		// im (expression) Fall
			return match(klammerSet,t+1) &&term(t+1) &&rightExpression(t+1);
		}
		else{
			return num(t+1);
		}
  }//operator

  
  
  //-------------------------------------------------------------------------
  // digit -> '1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'
  //
  // Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------

  //static boolean digit(int t){
  //  char [] matchSet = {'1','2','3','4','5','6','7','8','9','0'};
  //  ausgabe("digit->",t);     //Syntaxbaum ausgeben
  //  if (match(matchSet,t+1))   //digit->'1'|'2'...|'9'|'0'
  //       return true;                // korrekte Ableitung der Regel möglich
  //    else
  //        syntaxError("Ziffer erwartet"); // korrekte Ableitung der Regel  
  //        return false;                   // nicht mäglich
  //}//digit
  
  
  
  //-------------------------------------------------------------------------
  // digit -> '1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'
  //
  // Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------

  static boolean digit(int t){
	  char [] matchSet = {'1','2','3','4','5','6','7','8','9','0'};
		  ausgabe("digit->",t);
		  match(matchSet, t+1);
		  if(input[pointer] == ')'){
			  char[] geschlossen = {')'};
			  match(geschlossen, t+1);
			  klammer--;
			  return true;}
		  else{
		  		syntaxError("Ziffer erwartet");
		  		return false;}
  }//digit
  
  
  
  //-------------------------------------------------------------------------
  //-------------------Methoden der Grammatik--------------------------------
  //-------------------------------------------------------------------------
  
  //-------------------------------------------------------------------------
  // num -> digit num | digit
  //
  // Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------


  // Anfang Methoden
  static boolean num(int t){
    char [] digitSet = {'1','2','3','4','5','6','7','8','9','0'};
      ausgabe("num->", t);          //Syntaxbaum ausgeben
      if (lookAhead(digitSet)){
        return digit(t+1)&& num(t+1);
      }//num->digit num
      else {
        return digit(t+1);          //num->digit
      }
  }//num
  
  
  //-------------------------------------------------------------------------
  //-------------------Hilfsmethoden-----------------------------------------
  //-------------------------------------------------------------------------
  
  //-------------------------------------------------------------------------   
  // Methode, die testet, ob das aktuele Eingabezeichen unter den Zeichen
  // ist, die als Parameter (matchSet) übergeben wurden.
  // Ist das der Fall, so gibt match() true zurück und setzt den Eingabe-
  // zeiger auf das nächste Zeichen, sonst wird false zurückgegeben und der
  // Eingabezeiger bleibt unverändert.
  //
  // Der Parameter matchSet übergibt die zu prüfenden Eingabezeichen
  // Der Parameter t gibt die aktuelle Rekursionstiefe an
  //-------------------------------------------------------------------------

  static boolean match(char [] matchSet,int t){
    for (int i=0;i<matchSet.length;i++)
      if (input[pointer]==matchSet[i]){
        ausgabe(" match: "+input[pointer],t);
        pointer++;  //Eingabepointer auf das nächste Zeichen setzen 
        return true;
      }
    return false;
  }//match
  


  //-------------------------------------------------------------------------
  //Methode, die testet, ob das auf das aktuelle Zeichen folgende Zeichen
  //unter den Zeichen ist, die als Parameter (aheadSet) übergeben wurden.
  //Der Eingabepointer wird nicht verändert!
  //
  // Der Parameter aheadSet übergibt die zu prüfenden Lookahead-Zeichen
  //-------------------------------------------------------------------------
    static boolean lookAhead(char [] aheadSet){
    for (int i=0;i<aheadSet.length;i++)
      if (input[pointer+1]==aheadSet[i])
        return true;
    return false;
  }//lookAhead
  


  //-------------------------------------------------------------------------
  // Methode zum zeichenweise Einlesen der Eingabes aus
  // einer Eingabedatei.
  // Die Metode berücksichtigt beim Einlesen schon die maximale Grösse
  // des Arrays input von 256 Zeichen.
  // Das Ende der Eingabe wird mit EOF markiert
  //
  // Der Parameter name enthält den Dateinamen
  //-------------------------------------------------------------------------

  static boolean readInput(String name){
    int c=0;
    try{
      FileReader f = new FileReader(name);
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

  static boolean inputEmpty(){
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
  //
  // Der Parameter s übergibt die Beschreibung des Knotens als String
  // Der Parameter t übergibt die Einrück-Tiefe
  //-------------------------------------------------------------------------

  static void ausgabe(String s, int t){
    for(int i=0;i<t;i++)
      System.out.print("  ");
    System.out.println(s);
  }//ausgabe
  


  //-------------------------------------------------------------------------
  // Methode zum Ausgeben eines Syntaxfehlers mit Angabe des vermuteten
  // Zeichens, bei dem der Fehler gefunden wurde 
  //
  // Der Parameter s übergibt die Fehlermeldung als String
  //-------------------------------------------------------------------------

  static void syntaxError(String s){
    System.out.println("Syntax Fehler beim "+(pointer+1)+" Zeichen: "
              +input[pointer]);
    System.out.println(s);  
  }//syntaxError
  

  
  //-------------------------------------------------------------------------
  // Main Methode, startet den Parser und gibt das Ergebnis des Parser-
  // durchlaufs auf der Konsole aus
  //-------------------------------------------------------------------------

  public static void main(String args[]){
    // Anlegen des Arrays für den zu parsenden Ausdruck
    input = new char[256];
    
    // Einlesen der Datei und Aufruf des Parsers    
    if (readInput("testdatei.txt"))
      if (expression(0)&& inputEmpty()&& klammer==0)
        System.out.println("Korrekter Ausdruck");
      else
        System.out.println("Fehler im Ausdruck"); 
  }//main
  // Ende Methoden
}