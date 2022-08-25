import java.sql.SQLException;
import java.util.Scanner;
import java.util.Date;

public class LibrarySystem {

    Database LibraryDB = new Database();



    Scanner input = new Scanner(System.in);


    private static String getMemberInfo(String message, int bound) throws CustomException {
        System.out.println(message);
        Scanner input =new Scanner(System.in);
        String userInput = input.next();
        if (userInput.length() <= bound) {
            return userInput;
        }else {
            throw new CustomException(String.format("please enter an input between 1 and %d character", bound));
        }
    }



    public void add() throws SQLException, CustomException {

        int newId = idGenerator();

        Date newRegisterDate = new Date();
        
        String newNationalNumber = getMemberInfo("Please enter National Number: ",10);

        String newName = getMemberInfo("Please enter the firstname: ",20);

        String newFamilyName = getMemberInfo("Please enter the lastname: ",25);

        String newGender = getMemberInfo("Please enter gender: ",10);

        String newAge = getMemberInfo("Please enter the Age: ",2);

        String newAddress = getMemberInfo("Please enter the Address: ",50);

        
        
        LibraryDB.transaction("INSERT INTO members "+
                "(ID,NATIONAL_NUMBER,NAME,LAST_NAME,GENDER,AGE,ADDRESS,REGISTER_DATE,STATUS) " +
                String.format("VALUES (%d,'%s','%s','%s','%s',%s,'%s','%s','active');"
                        ,newId,newNationalNumber,newName,newFamilyName,newGender,newAge,newAddress,newRegisterDate));
        System.out.print("member successfully registered!");
    }

    private int idGenerator() throws SQLException {

            return this.LibraryDB.getResultCount("SELECT COUNT(*) AS total FROM members;","total")+1;
            }
 

    public void activate(int mod) throws CustomException {
        String Id = getMemberInfo("Please enter an ID:  ", 6);
        switch (mod) {
            case 0 ->
                    this.LibraryDB.transaction(String.format("UPDATE members SET STATUS = 'deactivate' WHERE ID='%s';", Id));
            case 1 ->
                    this.LibraryDB.transaction(String.format("UPDATE members SET STATUS = 'activate' WHERE ID='%s';", Id));
        }


    }


    public void update() throws CustomException {
        System.out.print("""
                please select your update field number:
                1-National number
                2-firstname
                3-lastname
                4-gender
                5-age
                6-address
                """);
        String inputString = input.nextLine();
        char mod = inputString.charAt(0);

        if (mod=='1'||mod=='2'||mod=='3'||mod=='4'||mod=='5'||mod=='6') {
            makeUpdate(mod);
        }
        else {
            throw new CustomException("please enter a number between 1 and 6");
        }
         
    }
    private void makeUpdate(char mod) throws CustomException {

        String updateId = getMemberInfo("Please enter the member ID for updating",6);
        
        switch (mod) {
            case 1 -> {
                String updateNationalNumber = getMemberInfo("Please enter new National Number (10 digit)",10);
                this.LibraryDB.transaction(String.format("UPDATE members SET NATIONAL_NUMBER = '%s' WHERE ID=%s;", updateNationalNumber, updateId));
            }
            case 2 -> {
                String updateName = getMemberInfo("Please enter firstname",20);
                this.LibraryDB.transaction(String.format("UPDATE members SET NAME = '%s' WHERE ID=%s;", updateName, updateId));
            }
            case 3 -> {
                String updateFamilyName = getMemberInfo("Please enter lastname",25);
                this.LibraryDB.transaction(String.format("UPDATE members SET LAST_NAME = '%s' WHERE ID=%s;", updateFamilyName, updateId));
            }
            case 4 -> {
                System.out.println();
                String updateGender = getMemberInfo("Please enter gender: ",10);
                this.LibraryDB.transaction(String.format("UPDATE members SET GENDER = '%s' WHERE ID=%s;", updateGender, updateId));
            }
            case 5 -> {
                String updateAge = getMemberInfo("Please enter the Age: ",2);
                this.LibraryDB.transaction(String.format("UPDATE members SET AGE = %s WHERE ID=%s;", updateAge, updateId));
            }
            case 6 -> {
                String updateAddress = getMemberInfo("Please enter the Address",50);
                this.LibraryDB.transaction(String.format("UPDATE members SET ADDRESS = '%s' WHERE ID=%s;", updateAddress, updateId));
            }
        }
    }

    public void search() throws SQLException, CustomException {
        System.out.println("""
                Please select your search method number:
                1- by Firstname and Last name
                2- by National Number
                3- by ID
                """);
        String inputString = input.nextLine();
        char searchMethod = inputString.charAt(0);
        if (searchMethod == '1'||searchMethod == '2' || searchMethod =='3') {
            switch (searchMethod) {
                case '1' -> {
                    String firstName = getMemberInfo("enter firstname: ",20);
                    String lastName = getMemberInfo("enter lastName: ",25);
                    searchByName(firstName, lastName);
                }
                case '2' -> {
                    String nationalNumber = getMemberInfo("enter National Number: ",10);
                    searchByNationalNumber(nationalNumber);
                }
                case '3' -> {
                    String Id = getMemberInfo("enter ID: ",6);
                    searchById(Id);
                }
            }
        }else {
            throw new CustomException("please enter a number between 1 and 3");
        }


    }
    private void searchByName(String firstName,String lastName) throws SQLException {
        LibraryDB.getResult(String.format("SELECT * FROM members where NAME='%s' " +
                "AND LAST_NAME='%s';", firstName,lastName));
    }



    private void searchByNationalNumber(String nationalNumber) throws SQLException {
       LibraryDB.getResult(String.format("SELECT * FROM members where NATIONAL_NUMBER='%s';", nationalNumber));
    }

    private void searchById(String Id) throws SQLException {
        LibraryDB.getResult(String.format("SELECT * FROM members where ID='%s';", Id));
    }



}
