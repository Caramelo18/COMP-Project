public class UndeclaredVariable extends Exception {
    String message;

    public UndeclaredVariable(String variable){
        super();
        message = "Variable " + variable + " is not declared";
    }

    public String getMessage(){
        return message;
    }
}
