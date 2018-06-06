public class Main{
  public static void main(String[] args){
    Token token;
    Lexer s = new Lexer();
    do{
      token = s.nextToken(); 
      System.out.println("Line: " + token.line + ", spelling = [" + token.spelling + "], " + "kind = " + token.kind);
    }while(token.kind != Token.EOT);
  }
}