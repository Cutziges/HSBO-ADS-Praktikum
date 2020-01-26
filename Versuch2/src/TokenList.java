/*
	interface TokenList

	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2

	Die Schnittstelle TokenList stellt die Konstanten für die
	Knotentypen eines Syntaxbaumes (Token) für Ziffernfolgen
	zur Verfügung.
*/

interface TokenList {
	// Konstanten zur Bezeichnung der Knoten des Syntaxbaumes
	
	final byte	NO_TYPE=0,
				NUM=1,
				DIGIT=2,
				INPUT_SIGN=3,
				EPSILON=4;
				
	// Konstante, die angibt, dass die Semantische Funktion eines Knotens 
	// undefiniert ist
	final int	UNDEFINED=0x10000001;	

				
}