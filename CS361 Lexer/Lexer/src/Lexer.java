import java.io.*;
//import java.util.Scanner;

public class Lexer{
  private char currentChar;
  private static int line = 1;
  private BufferedReader inFile = null;
  private StringBuffer currentSpelling;
  private int currentKind;
  
  public Lexer(){
    try{
//    	  System.out.print("Enter the file name: ");
    	  //Scanner keyboard = new Scanner(System.in);
    	  String FileName = "Text.txt";//keyboard.next();
    	  inFile = new BufferedReader(new FileReader(FileName));
          int i = inFile.read();
          if(i == -1) //end of file
            currentChar = '\u0000';
          else
            currentChar = (char)i;
    	  takeIt();
      }catch(Exception e){}
  }
  
  private void takeIt(){
    currentSpelling.append(currentChar);
    try{
    	int i = (int)inFile.read();
    	if(i == -1) //end of file
    		currentChar = '\u0000';
    	else
    		currentChar = (char)i;
    }catch(Exception e){}     
  }

  private void discard(){
	  try{
		  int i = (int)inFile.read();
		  if(i == -1) //end of file
			  currentChar = '\u0000';
	    else
	    	currentChar = (char)i;
	   }catch(Exception e){}
  }

  private int scanToken(){
	    switch(currentChar){
	      case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': 
	      case 'g': case 'h': case 'i': case 'j': case 'k': case 'l': 
	      case 'm': case 'n': case 'o': case 'p': case 'q': case 'r':
	      case 's': case 't': case 'u': case 'v': case 'w': case 'x': 
	      case 'y': case 'z':
	      case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': 
	      case 'G': case 'H': case 'I': case 'J': case 'K': case 'L': 
	      case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R':
	      case 'S': case 'T': case 'U': case 'V': case 'W': case 'X': 
	      case 'Y': case 'Z':
	        takeIt();
	        while(isLetter(currentChar) || isDigit(currentChar))
	          takeIt();
	        return Token.IDENTIFIER;
	      case '0': case '1': case '2': case '3': case '4':
	      case '5': case '6': case '7': case '8': case '9':
	        takeIt();
	        while(isDigit(currentChar))
	          takeIt();
	        return Token.INT_VALUE;
	      case '+': 
	    	  takeIt(); 
	    	  return Token.PLUS;
	      case '-': 
	    	  takeIt(); 
	    	  return Token.MINUS; 
	      case '*': 
	    	  takeIt(); 
	    	  return Token.MULTIPLY;
	      case '(': 
	    	  takeIt(); 
	    	  return Token.LPAREN;
	      case ')': 
	    	  takeIt(); 
	    	  return Token.RPAREN;
	      case '\u0000':
	    	  takeIt(); 
	    	  return Token.EOT;    	  

	      case ';':
	          takeIt();         
	          return Token.SEMICOLON;
	          
	      case '.':
	          takeIt();     
	          if(currentChar == '.'){
		            takeIt();
		            return Token.RANGE;
	          }
	          return Token.PERIOD;
	          
	     
	          
	      case '[':
	          takeIt();         
	          return Token.LBRACKET;
	          
	      case ']':
	          takeIt();         
	          return Token.RBRACKET;
	          
	      case ',':
	          takeIt();         
	          return Token.COMMA;
	          
	      case ':':
	          takeIt();         
	          if(currentChar == '='){
		            takeIt();
		            return Token.ASSIGN;
		           
		          }
	          return Token.COLON;
		          
	      
	      case '{':
	          takeIt();         
	          return Token.LBRACE;
	          
	      case '}':
	          takeIt();         
	          return Token.RBRACE;
	           
	      case '<':
	          takeIt();
	          if(currentChar == '>'){
	            takeIt();
	            return Token.NOTEQUAL;
	          }
	          return Token.LESS;
	          
	      case '>':
	          takeIt();
	         // if(currentChar == '='){
	           // takeIt();
	            //return Token.GEQ;
	         // }
	          return Token.GREATER;
	     
	      case '=':
	          takeIt();         
	          return Token.EQUAL;
	          
	          //new Error("Error: The symbol = is not a token", line);
	          //return Token.NOTHING;
	      case '&':
	          takeIt();
	          if(currentChar == '&'){
	            takeIt();
	            return Token.AND;
	            
	   	    /*  case '/':
		    	  takeIt(); 
		    	  if(currentChar == '/'){
		    		  line++;
		    		  while(currentChar != '\n' && currentChar != '\u0000')
		    		    discard();
		    		  if(currentChar == '\n')
		    			  discard();
		    		  return Token.NOTHING;
		    	  }else
		    		  return Token.DIVIDE;
		      */
	          }
	          new Error("Error: The symbol & is not a token", line);
	          return Token.NOTHING;
	      case '|':
	          takeIt();
	          if(currentChar == '|'){
	            takeIt();
	            return Token.OR;
	          }
	       	  new Error("Error: The symbol | is not a token", line);
	       	  return Token.NOTHING;
	      	default:
	      	  new Error("Error: The symbol " + currentChar + " is not a token", line);
	      	  return Token.NOTHING;
	      	  
	      	  
	    }
	  }

	  private void scanEscapeCharacters(){
	    switch(currentChar){
	      case ' ': case '\n': case '\r': case '\t':
	        if(currentChar == '\n')
	          line++;
	        discard();
	    }
	  }

	  public Token nextToken(){
	    currentSpelling = new StringBuffer("");
	    while(currentChar == ' ' || currentChar == '\n' || 
	      currentChar == '\r' || currentChar == '\t')
	      scanEscapeCharacters();
	    currentKind = scanToken();
	    if(currentKind == Token.NOTHING)
	    	nextToken();
	    return new Token(currentKind, currentSpelling.toString(), line);
	  }

	  private boolean isDigit(char c){
	    return '0' <= c && c <= '9';
	  }

	  private boolean isLetter(char c){
	    return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
	  }
	}
