
import java.util.ArrayList;

public class Person {

    String name;
    int amount_needed;
    ArrayList<Integer> banks_which_cant_lend_loan = new ArrayList<>();
    int id;

    public Person(String name, int amount_needed, int id) {
        this.name = name;
        this.amount_needed = amount_needed;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", amount_needed=" + amount_needed + '}';
    }

    public Person() {
    }

}
