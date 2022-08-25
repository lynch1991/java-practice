import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    String inputString;
    LibrarySystem MalekLibrary = new LibrarySystem();
    Scanner inputStatus = new Scanner(System.in);

    public void start() throws Exception {
        System.out.println("""
                 Please enter a number to begin:  
                 1- add a member
                 2- deactivate a member
                 3- reactive a member
                 4- update a member
                 5- search a member
                 6- exit""");
        String s = "just enter a digit";

        inputString = inputStatus.nextLine();

        char menuStatus = inputString.charAt(0);
        System.out.println(menuStatus);

        if (menuStatus=='1'||menuStatus=='2'||menuStatus=='3'||menuStatus=='4'||menuStatus=='5'||menuStatus=='6') {
            switch (menuStatus) {
                case '1' -> MalekLibrary.add();
                case '2' -> MalekLibrary.activate(0);
                case '3'-> MalekLibrary.activate(1);
                case '4' -> MalekLibrary.update();
                case '5' -> MalekLibrary.search();
                default -> System.exit(0);

            }
        }else {
                throw new CustomException("please enter a number between 1 and 6");

            }



    }

}
