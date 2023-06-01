import Exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    ArrayList<User> userList = new ArrayList<>();
    public void addUser(User user){
        userList.add(user);
    }
    public User getUser(String id) throws UserNotFoundException {
        for ( User user : userList) {
            if (user.getId().compareTo(id) == 0)
                return user;
        }
        throw new UserNotFoundException();
    }

    public User getUserByAccountNumber(String an){
        try {
            for ( User user : userList) {
                if (user.getAccountNumber().compareTo(an) == 0)
                    return user;
            }
            throw new UserNotFoundException();
        }
        catch (UserNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public void login() throws UserNotFoundException, InvalidPasswordException, NegativeBalanceException {
        Scanner sc = new Scanner(System.in);

        System.out.print("아이디를 입력하세요: ");
        String inputId = sc.nextLine();
        if (!idExists(inputId)){ //if user does not exist
            throw new UserNotFoundException();
        }

        System.out.print("비밀번호를 입력하세요: ");
        String inputPwd = sc.nextLine();
        if (getUser(inputId).getPw().compareTo(inputPwd) != 0){ // if password is incorrect
            throw new InvalidPasswordException();
        }

        System.out.println("로그인되었습니다.");

        User currUser = getUser(inputId);
        int choice = 0;
        System.out.print("1. 인출\n2. 입금\n3. 송금\n4. 문건 구매\n원하는 기능의 번호를 입력하세요: ");
        try{
            choice = sc.nextInt();
            if (choice != 1 && choice != 2 && choice != 3 && choice != 4 ){
                throw new Exception();
            }
        }
        catch (Exception e){
            System.out.println("없는 기능입니다.");
        }

        switch (choice) {
            case 1 -> //인출
                    withdrawCash(currUser);
            case 2 -> //입금
                    depositCash(currUser);
            case 3 -> //송금
                    sendCash(currUser);
            case 4 -> //물건 구매
                    buyItem(currUser);
        }
    }

    public void buyItem(User currUser) {
        int amount;
        System.out.print("물건 가견을 입력하세요: ");
        Scanner sc = new Scanner(System.in);
        try {
            amount = sc.nextInt();

            //has card 카드 소지자는 카드(잔액 이용) 우선 결제, 부족하면 현금도 혼합해 결제
            if (currUser.isHasCard()){
                if (amount > currUser.getCash() + currUser.getBalance()){ //not enough money from both cash and balance
                    throw new NegativeCashException("잔고 부족 (카드 단독 결제 실패)\n현금 부족 (카드 + 현금 결제 실패)");
                }
                else if (amount < currUser.getBalance()){// card is enough
                    currUser.purchaseCard(amount);
                    System.out.println("물건이 구매되었습니다. (카드 결제)");
                    System.out.println("잔고: " + currUser.getBalance() + "원"+ "\n현금: " + currUser.getCash()+ "원");
                }
                else { //in-between card and cash sums
                    int cardAmount = currUser.getBalance();
                    int cashAmount = amount - cardAmount;
                    currUser.purchaseCard(cardAmount);
                    currUser.purchaseCash(cashAmount);
                    System.out.println("물건이 구매되었습니다. (카드 + 현금 결제)");
                    System.out.println("잔고: " + currUser.getBalance() + "원"+ "\n현금: " + currUser.getCash()+ "원");
                }

            }
            else { //미 소지자는 현금으로만 결제 가능
                if (amount > currUser.getCash()){
                    throw new NegativeCashException("현금 부족 (현금 결제 실패)");
                } else {
                    currUser.purchaseCash(amount);
                    System.out.println("물건이 구매되었습니다. (현금 결제)");
                    System.out.println("잔고: " + currUser.getBalance() + "원"+ "\n현금: " + currUser.getCash()+ "원");
                }
            }
        } catch (NegativeCashException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("잘못된 금액");
        }
    }

    public void sendCash(User currUser) {
        String acNum;
        int amount;
        System.out.print("송금할 상대방 계좌를 입력하세요: ");
        Scanner sc = new Scanner(System.in);
        try {
            acNum = sc.nextLine();
            if (!acctExists(acNum)){
                throw new AccountNotFoundException("해당 계좌는 없습니다.");
            }
            System.out.print("송금할 금액을 입력하세요: " );
            amount = sc.nextInt();
            if (amount > currUser.getBalance()){ //not enough balance to send amount
                throw new NegativeBalanceException("잔고 부족");
            }

            //adjust balances accordingly
            currUser.transfer(amount,getUserByAccountNumber(acNum));
            System.out.println(getUserByAccountNumber(acNum).getId() + "님 " + amount + "워이 입금되었습니다.");
            System.out.println("잔고: " + getUserByAccountNumber(acNum).getBalance() + "원");
            System.out.println(currUser.getId() + "님 " + amount + "워이 솧금되었습니다."+ "원");
            System.out.println("잔고: " + currUser.getBalance());

        } catch (AccountNotFoundException | NegativeBalanceException e) {
            System.out.println(e.getMessage());
        }

    }
    public void depositCash(User currUser){
        int amount;
        System.out.print("입금할 금액을 입력하세요: " );
        Scanner sc = new Scanner(System.in);
        try{
            amount = sc.nextInt();
            if (amount > currUser.getCash()){ //not enough cash
                throw new NegativeCashException("현금 부족");
            }
            else if (amount < 0){ //minus input
                throw new NegativeCashException("음수 현금액.");
            }
            currUser.deposit(amount);
            System.out.print(currUser.getId() + "님 " + amount + "원이 입금되었습니다.\n" + "잔고: " + currUser.getBalance() + "원"+ "\n현금: " + currUser.getCash()+ "원");

        }
        catch (NegativeCashException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println("잘못된 금액");
        }
    }
    public void withdrawCash(User currUser) {
        int amount;
        System.out.print("인출할 금액을 입력하세요: " );
        Scanner sc = new Scanner(System.in);
        try{
            amount = sc.nextInt();
            if (amount > currUser.getBalance()){ //not enough balance
                throw new NegativeBalanceException("잔고 부족");
            }
            else if (amount < 0){ //minus input
                throw new NegativeBalanceException("음수 입금액.");
            }
            currUser.withdraw(amount);
            System.out.print(currUser.getId() + "님 " + amount + "원이 인출되었습니다.\n" + "잔고: " + currUser.getBalance() + "\n현금: " + currUser.getCash());

        }
        catch (NegativeBalanceException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println("잘못된 금액");
        }
    }
    public boolean idExists(String id){
        for ( User user : userList) {
            if (user.getId().compareTo(id) == 0)
                return true;
        }
        return false;
    }
    public boolean acctExists(String acNum){
        for ( User user : userList) {
            if (user.getAccountNumber().compareTo(acNum) == 0)
                return true;
        }
        return false;
    }
}
