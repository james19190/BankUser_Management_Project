
import Exceptions.InvalidPasswordException;
import Exceptions.NegativeBalanceException;
import Exceptions.UserNotFoundException;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Main {
    public static void main (String[] args) throws FileNotFoundException {

        Bank bank = new Bank();

        //receive account logic DATA from data.txt file
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        //input text data and create new users
        while (sc.hasNextLine()){
            String temp = sc.nextLine();
            String[] tempData = temp.split("[|][|]");
            String id = tempData[0];
            String pw = tempData[1];
            String accountNumber = tempData[2];
            int balance = Integer.parseInt(tempData[3]);
            boolean hasCard = tempData[4].compareTo("y") == 0;
            int cash = Integer.parseInt(tempData[5]);
            bank.addUser(new User(id,pw,accountNumber,balance,hasCard,cash));
        }

        try {
            bank.login();
        }
        catch (UserNotFoundException e){
            System.out.println("존재하지 않는 회원입니다.");
        }
        catch (InvalidPasswordException e){
            System.out.println("잘못된 비밀번호입니다.");
        } catch (NegativeBalanceException e) {
            throw new RuntimeException(e);
        }


    }
}

