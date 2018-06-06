public class Error {
  public Error(String message, int line) {
    System.out.println("Line " + line + ": " + message);
    System.exit(0);
  }
  
  public Error(String message){
	    System.out.println(message);
	    System.exit(0);
  }
}