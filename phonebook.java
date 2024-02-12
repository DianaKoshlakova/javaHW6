
/*
 * Урок 6. Хранение и обработка данных ч3: множество коллекций Set
 *  Формат сдачи: ссылка на подписанный git-проект.
 * Задание
 *  Реализуйте структуру телефонной книги с помощью HashMap.
 *  Программа также должна учитывать, что в во входной структуре 
 * будут повторяющиеся имена с разными телефонами, 
 *  их необходимо считать, как одного человека с разными 
 * телефонами. Вывод должен быть отсортирован по убыванию 
 * числа телефонов.
 */

class PhoneRow {
    private String uName;
    private ArrayList<Integer> phNumber = new ArrayList<>();

    PhoneRow(String name, int num) {
        uName = name;
        phNumber.add(num);
    }

    public int getPhoneNumberCounter() {
        return this.getPhones().size();
    }

    public String toString() {
        return String.format("{%s:%s}", this.uName, this.phNumber.toString());
    }

    public String getName() {
        return this.uName;
    }

    public ArrayList<Integer> getPhones() {
        return this.phNumber;
    }
}

class PhoneBook {
    private int a = 0;
    private static HashMap<Integer, PhoneRow> phoneBook = new HashMap<>();
    public ArrayList<Integer> sortedKeys = new ArrayList<>();

    public void add(String unitName, Integer phoneNum) {
        Boolean[] isExists = { false };
        phoneBook.forEach((k,v) -> {
            String name = v.getName();
            ArrayList<Integer> phones = v.getPhones();
            if (name.equals(unitName)) {
                phones.add(phoneNum);
                isExists[0] = true;
            }
        });
        if (!isExists[0]) {
            phoneBook.put(++this.a, new PhoneRow(unitName, phoneNum));
        }
        this.wasAdded(this.a);
    }

    public static HashMap<Integer, PhoneRow> getPhoneBook() {
        return phoneBook;
    }

    public void wasAdded(int id) {
        String str = new String(
            String.format("**** User %s with id(%d) has %d numbers", 
                phoneBook.get(id).getName(), 
                id, 
                phoneBook.get(id).getPhoneNumberCounter()
            )
        );
        System.out.println(str);
    }
}

class Printer {
    public static void main(String[] args) {
        String name1;
        String name2;
        int phone1;
        int phone2;

        if (args.length == 0) {
            name1 = "Kozlovskiy";
            name2 = "Petrov";
            phone1 = 23640898;
            phone2 = 65648675;
        } else {
            name1 = args[0];
            name2 = args[1];
            phone1 = Integer.parseInt(args[2]);
            phone2 = Integer.parseInt(args[3]);
        }
        
        PhoneBook myPhoneBook = new PhoneBook();
        
        myPhoneBook.add(name1, phone1);
        myPhoneBook.add(name1, phone2);
        myPhoneBook.add(name2, phone2);
        myPhoneBook.add(name2, phone1);
        myPhoneBook.add(name2, phone2);
        
        System.out.println();

        Map<Integer, PhoneRow> pb = PhoneBook.getPhoneBook();
        LinkedHashMap<Integer, PhoneRow> lhm = pb.entrySet().stream().sorted(
            (x1, x2) -> Integer.compare(
                x2.getValue().getPhoneNumberCounter(), 
                x1.getValue().getPhoneNumberCounter()
            )
        ).collect(
            Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (x1, x2) -> x1, 
                LinkedHashMap::new
            )
        );
        
        for (var item : lhm.entrySet()) {
            System.out.println(item.toString());
        }
    }
}