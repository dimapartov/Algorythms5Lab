public class Main {
    public static void main(String[] args) {

        HashTable<String, Integer> table = new HashTable<>();
        for (int i = 0; i < 30000; i++) {
            String key = "Ключ №" + i;
            table.add(key, i);
        }

        System.out.println("Размер таблицы: " + table.size());
        System.out.println("Количество коллизий: " + table.countCollisions());
        System.out.println("Коэффициент заполнения: " + table.getLoadFactor());

        table.add("A", 1);
        table.add("B", 2);
        table.add("C", 3);
        table.add("E", 4);
        table.add("F", 5);
        table.add("G", 6);
        table.add("H", 7);
        table.add("I", 8);
        table.add("J", 9);
        table.add("K", 10);

        table.addOrReplace("A", 11);

        System.out.println("Значение для А: " + table.get("A"));
        System.out.println("Ключ-значение для B: " + table.find("B"));
        System.out.println("Проверка на наличие ключа C: " + table.containsKey("C"));
        System.out.println("Проверка на наличие ключа Z: " + table.containsKey("Z"));
        System.out.println("Удаление ключа B: " + table.remove("B"));

        table.clear();
        System.out.println("Размер таблицы после очистки: " + table.size());

        table.add("A", 1);
        table.add("B", 2);

        Iterable<String> keys = table.keys();
        System.out.println("Получение ключей итератором: " + keys);

        Iterable<Integer> values = table.values();
        System.out.println("Получение значений итератором: " + values);

        System.out.println("Получение ключа-значения итератором: ");
        for (KeyValue<String, Integer> entry : table) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }
}