import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Money {

    static Person[] persons;
    static Bank[] list_of_banks;
    static int[] list_of_persons;

    public static Bank arrange_bank(String st) {
        String name, total_money;
        int count = 0;

        for (int i = 0; i < st.length(); i++) {
            if (st.charAt(i) == ',') {
                break;
            } else {
                count++;
            }
        }
        name = st.substring(1, count);
        total_money = st.substring(count + 1, st.length() - 2);

        return new Bank(name, Integer.parseInt(total_money));
    }

    public static void notify(int i) {
        list_of_persons[i] = -1;
    }

    public static boolean completed() {
        for (int i = 0; i < list_of_persons.length; i++) {
            if (list_of_persons[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public static synchronized void request_money(Person p, Bank b, int amount) {

        int bank = getBank(p, amount);
        if (bank == -2) {
            System.out.println("the bank :: " + list_of_banks[bank].name + " was unable to lend ::" + amount + " to ::" + p.name);
            p.banks_which_cant_lend_loan.add(bank);
            return;
            
        } else if (bank == -1) {
            System.out.println("no bank can lend loan to :: " + p.name);
            notify(p.id);
            return;
        } else if((list_of_banks[bank].total_money-amount)>0){
            list_of_banks[bank].total_money -= amount;
            p.amount_needed -= amount;
            System.out.println("person :: " + p.name + " recieved " + amount + " from bank :: " + list_of_banks[bank].name);
            return;
            
        }
    }

    public static int getRandomMoney(Person p) {
        if (p.amount_needed > 50) {
            while (true) {
                int random = Math.round((float) Math.random() * 50);
                if (random > 0) {
                    return random;
                }
            }
        } else {
            return p.amount_needed;
        }
    }

    public static int getBank(Person p, int money_needed) {

        int bank_number;
        if (p.banks_which_cant_lend_loan.size() == list_of_banks.length) {
            return -1;
        }
        while (true) {
            bank_number = Math.round((float) Math.random() * list_of_banks.length);
            if (bank_number >= 0 && bank_number < list_of_banks.length) {
                break;
            }
        }

        if (!p.banks_which_cant_lend_loan.contains(bank_number)) {
            return bank_number;
        } else {
            for (int i = 0; i < list_of_banks.length; i++) {
                if (!p.banks_which_cant_lend_loan.contains(i)) {
                    return bank_number;
                }
            }
        }
        return -2;
    }

    public static Person arrange_person(String st) {
        String name, amount_needed;
        int count = 0;

        for (int i = 0; i < st.length(); i++) {
            if (st.charAt(i) == ',') {
                break;
            } else {
                count++;
            }
        }
        name = st.substring(1, count);
        amount_needed = st.substring(count + 1, st.length() - 2);

        return new Person(name, Integer.parseInt(amount_needed), 0);
    }

    public static void main(String[] args) {
        Bank b = new Bank();
        int i = 0, count = 0;
        try {
            String st;
            File file = new File("banks.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
                count++;
            }
            list_of_banks = new Bank[count];
            br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
                b = arrange_bank(st);
                //System.out.println(b.toString());
                list_of_banks[i] = new Bank();
                list_of_banks[i] = b;
                // System.out.println(list_of_banks[i].toString());
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        i = count = 0;
        Person p = new Person();
        try {
            File file = new File("customers.txt");
            String st;
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((st = br.readLine()) != null) {
                count++;
            }
            list_of_persons = new int[count];
            persons = new Person[count];
            br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
                persons[i] = new Person();
                persons[i] = arrange_person(st);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int person_number;
        while (!completed()) {
            person_number = Math.round((float) Math.random() * (list_of_persons.length - 1));
                 

            p = persons[person_number];
            if (p.amount_needed > 0) {
                int randommoney = getRandomMoney(p);
                int banknumber = getBank(p, randommoney);
                request_money(p, list_of_banks[banknumber], randommoney);
            }else{
                notify(person_number);
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {

            }
        }
        for(i=0;i<list_of_banks.length;i++){
            System.out.println("bank :: "+list_of_banks[i].name+" has remaining balance :: "+list_of_banks[i].total_money);
        }
    }

}
