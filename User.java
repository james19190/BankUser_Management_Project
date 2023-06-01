public class User {

    //constructors
    public User(String id, String pw, String an, int bal, boolean card, int cash){
        this.id = id;
        this.pw = pw;
        accountNumber = an;
        balance = bal;
        hasCard = card;
        this.cash = cash;
    }

    //user variables
    private String id;
    private String pw;
    private String accountNumber;
    private int balance;
    private boolean hasCard;
    private int cash;

    //getter methods
    public String getId() {
        return id;
    }
    public String getPw() {
        return pw;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public int getBalance() {
        return balance;
    }
    public boolean isHasCard() {
        return hasCard;
    }
    public int getCash() {
        return cash;
    }

    //methods
    public void withdraw(int value){
        balance -= value;
        cash += value;
    }
    public void deposit(int value){
        balance += value;
        cash -= value;
    }
    public void transfer(int value, User receiver){
        this.balance -= value;
        receiver.balance += value;
    }
    public void purchaseCash(int value){
        this.cash -= value;
    }

    public void purchaseCard(int value){
        this.balance -= value;
    }
}
