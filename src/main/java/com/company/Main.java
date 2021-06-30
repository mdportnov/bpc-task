package com.company;

import java.util.*;

public class Main {
    public static void main(String... args) {
        UnluckyVassal unluckyVassal = new UnluckyVassal();
        List<String> pollResults = Arrays.asList(
                "служанка Аня",
                "управляющий Семен Семеныч: крестьянин Федя, доярка Нюра",
                "дворянин Кузькин: управляющий Семен Семеныч, жена Кузькина, экономка Лидия Федоровна",
                "экономка Лидия Федоровна: дворник Гена, служанка Аня",
                "доярка Нюра",
                "кот Василий: человеческая особь Катя",
                "дворник Гена: посыльный Тошка",
                "киллер Гена",
                "зажиточный холоп: крестьянка Таня",
                "секретарь короля: зажиточный холоп, шпион Т",
                "шпион Т: кучер Д",
                "посыльный Тошка: кот Василий",
                "аристократ Клаус",
                "просветленный Антон"
        );
        unluckyVassal.printReportForKing(pollResults);
    }
}


class UnluckyVassal {
    private ArrayList<String> parse(String s) {
        ArrayList<String> res = new ArrayList<>();
        StringBuilder now = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            char tmp = s.charAt(i);
            if (tmp == ':' || tmp == ',') {
                res.add(now.toString());
                now = new StringBuilder();
                i++;
            } else
                now.append(tmp);
        }
        res.add(now.toString());
        return res;
    }

    private void printRecursive(ArrayList<String> currentLevel, TreeMap<String, ArrayList<String>> subordinates, int tabs) {
        Collections.sort(currentLevel);

        for (String human : currentLevel) {
            for (int i = 0; i < tabs; ++i)
                System.out.print("\t");

            System.out.println(human);
            ArrayList<String> newLevel = subordinates.get(human);

            if (newLevel != null && !newLevel.isEmpty())
                printRecursive(newLevel, subordinates, tabs + 1);
        }
    }

    public void printReportForKing(List<String> pollResults) {
        TreeMap<String, ArrayList<String>> subordinates = new TreeMap<>(); // человек и его подчиненные
        TreeSet<String> hasChief = new TreeSet<>(); // у кого есть начальник
        TreeSet<String> allHumans = new TreeSet<>();

        for (String human : pollResults) {
            ArrayList<String> res = parse(human);
            String key = res.get(0);
            allHumans.add(key);

            res.remove(0);
            hasChief.addAll(res); // добавляем тех, у кого есть начальник
            subordinates.put(key, res); // добавляем человека и его подчиненных
        }

        ArrayList<String> kings = new ArrayList<>();
        for (String s : allHumans)
            if (!hasChief.contains(s))
                kings.add(s); // это будут ближайшие подчиненные короля

        System.out.println("король");
        printRecursive(kings, subordinates, 1);
    }
}
