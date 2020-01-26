/*
	class SyntaxTree
	
	Praktikum Algorithmen und Datenstrukturen
	Grundlage zum Versuch 2

	SyntaxTree beschreibt die Knoten eines Syntaxbaumes
	mit den Methoden zum Aufbau des Baums.
*/

import java.util.*;

class SyntaxTree implements TokenList{
	// Attribute 
	
	// linker bzw. rechter Teilbaum (null bei Blättern), rightNode=null,
	// wenn Operator nur einen Operanden hat
	private LinkedList <SyntaxTree> childNodes; 
	
	// Art des Knotens gemäß der Beschreibung in der Schnittstelle TokenList
	private byte token;
	
	// Zeichen des Knotens, falls es sich um einen Blätterknoten, der ein
	// Eingabezeichen repräsentiert, handelt, d.h. einen Knoten mit dem Token  
	// DIGIT oder MATH_SIGN.
	private char character;
	
	
	// value enthält die semantsiche Funktion des Teilbaums
	// mit Wurzelknoten this
	public Semantic value;
	
		
	//-------------------------------------------------------------------------
	// Konstruktor des Syntaxbaumes 
	//-------------------------------------------------------------------------
	
	// Der Konstruktor bekommt den TokenTyp t des Knotens übergeben
	SyntaxTree(byte t){
		this.childNodes= new LinkedList<SyntaxTree>();
		character=0;
		setToken(t);
		setSemantikFunction(t);
		}	
	//-------------------------------------------------------------------------
	// get und set Methoden des Syntaxbaumes
	//-------------------------------------------------------------------------
	
	// Setzt den Typ des Tokens auf den Übergabeparameter t
	// Zu den möglichen TokenTypen siehe Interface TokenList.java
	void setToken(byte t){
		this.token=t;
		}
	
	// Gibt den aktuellen Konten des Syntaxbaumes zurück
	byte getToken(){
		return this.token;
	}
	
	// Bei einem Knoten, der ein Eingabezeichen repräsentiert (INPUT_SIGN)
	// wird mit dieser Methode das Zeichen im Knoten gespeichert
	void setCharacter(char character){
		this.character=character;
	}
	
	// Gibt das zum Knoten gehörende Eingabezeichen zurück
	char getCharacter(){
		return this.character;
	}
	

	// Gibt den Syntaxbaum mit entsprechenden Einrückungen auf der Konsole
	// aus.
	void printSyntaxTree(int t){
		for(int i=0;i<t;i++)
		  System.out.print("  ");
		System.out.print(this.getTokenString());  		
		if(this.character!=0)
			System.out.println(":"+this.getCharacter());
		else
			System.out.println("");	
		for(int i=0;i<this.childNodes.size();i++){
			this.childNodes.get(i).printSyntaxTree(t+1);
		}
	}
	
	// Gibt den zum Zahlenwert passenden String des Tokentyps zurück
	String getTokenString(){
		switch(this.token){
			case 0: return "NO_TYPE";
			case 1: return "NUMBER";
			case 2: return "DIGIT";
			case 3: return "INPUT_SIGN";
			case 4: return "EPSILON";
			default: return "";
		}
	}
	
	
	
	// Bestimmt und speichert die semantsiche Funktion des Kontens in
	// Abhängigkeit vom Knotentyp
	void setSemantikFunction(byte b){
		switch(b){
			case 1: value=new Num();
					break;
			case 2: value=new Digit();
					break;
		default: value=new Semantic();
				 break;
		}
	}
	
	
	
	// Legt einen neuen Teilbaum als Kind des aktuellen Knotens an und gibt die
	// Referenz auf seine Wurzel zurück
	SyntaxTree insertSubtree(byte b){
		SyntaxTree node;
		node=new SyntaxTree(b); 
		this.childNodes.addLast(node);
		return node;
		}
	
	// Gibt die Refernz der Wurzel des i-ten Kindes des aktuellen 
	// Knotens zurück
	SyntaxTree getChild(int i){
		if (i>this.childNodes.size())
			return null;
		else
			return this.childNodes.get(i);
		}
		
	// Gibt die Referenz auf die Liste der Kinder des aktuellen Knotens zurück
	LinkedList getChildNodes(){
		return this.childNodes;
		}	
	
	// Gibt die Zahl der Kinder des aktuellen Konotens zurück
	int getChildNumber(){
		return childNodes.size();
	}
	

}