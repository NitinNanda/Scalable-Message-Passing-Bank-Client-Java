
public class Bank {

    String name;
    int total_money;

    public Bank(String name, int total_money) {
        this.name = name;
        this.total_money = total_money;
    }

    @Override
    public String toString() {
        return "Bank{" + "name=" + name + ", total_money=" + total_money + '}';
    }

    public Bank() {
    }

}
