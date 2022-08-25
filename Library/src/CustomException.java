public class CustomException extends Exception{
    String str1;

    CustomException(String str2) {
        str1=str2;
    }
    public String toString(){
        return ("Exception Occurred: "+str1) ;
    }
}
