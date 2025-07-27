package org.example;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collection;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static org.example.Main.TicketChecker.isLuckyTicket;

public class Main {
    public static void main(String[] args) throws Exception {
        isNumberEven(6); // 1. Напиши метод, проверяющий, является ли число чётным.
        //isLetterNoMore20Symvols(); // 2. Напиши метод, проверяющий, что введённое имя не больше 20 символов.
        /*int[] res = new int[20];
        for (int i = 0; i < res.length; i++)
        {
            res[i] = i;
        }
        reverse(res); // 3. Принимает массив и возвращает новый массив в обратном порядке. */
        //isFirstSymvolsCapital("JavaRoad"); // 4. Напиши метод, который проверяет, начинается ли строка с большой буквы.
        //isMailCorrect() ;// 5. Напиши метод, который принимает email и проверяет, есть ли в нём @ и точка после него
        //(1) Напиши метод, который принимает строку из ровно 6 цифр (например, "385916") и возвращает true, если это счастливый билет. Счастливый билет тот, чья сумма первых трёх цифр равна сумме последних трёх.
        System.out.println(isLuckyTicket("123321")); // True
        System.out.println(isLuckyTicket("111222")); // False
        //(2) Реализуй метод, который проверяет, являются ли две строки анаграммами (т.е. содержат одинаковые буквы, но в разном порядке). игнорируй регистр и пробелы.
        //System.out.println(areAnagrams("salt","T als"));
        //System.out.println(areAnagrams("For","rof"));
        //System.out.println(areAnagrams("pipu","ppiu"));
        //System.out.println(areAnagrams("hehe","haha"));
        //(3) Есть список сотрудников, у каждого есть имя и список навыков (например: Java, SQL, Selenium). Нужно получить отсортированный список уникальных навыков всех сотрудников.

        /*List<Employee> employees = List.of(
                new Employee("Alice", List.of("Java","Selenium","Git")),
                new Employee("Bob", List.of("Java","Docker")),
                new Employee("Eve", List.of("Python","Selenium","Git"))
        );
        List <String> sortedSkillsWithoutDuplicates = employees.stream()
                .flatMap(a -> a.skills.stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(sortedSkillsWithoutDuplicates); */




    }
    // 1. Метод позволяющий определить, четное ли число поступило на входе
    public static Boolean isNumberEven(Integer num){
        return num % 2 == 0;
    }
    // 2. Метод, ограничивающий ввод, свыше 20 символов
    public static Boolean isStringLessThan20Symbols(String letter){
        Boolean letChecked = letter.length() > 20 ? false : true;
        System.out.println(letChecked);
        return letChecked;
    }
    // 3. Метод, принимающий массив и выводящий его, в обратном порядке
    public static void reverse(int[]array){
        for (int i = array.length - 1; i >= 0; i--) {
            System.out.println(array[i]);
        }
    }
    // 4. Метод, проверяющий, начинается ли слово с большой буквы (с русской раскладкой почему-то не робит)
    public static Boolean isFirstSymvolsCapital(String letter){
        Boolean asd = Character.isUpperCase(letter.charAt(0));
        System.out.println(asd);
        return asd;
    }
    //5. Метод, который принимает email и проверяет, есть ли в нём @ и точка после него
    public static Boolean isMailCorrect(String adress){
        int at = adress.indexOf("@") + 1;
        int dot = adress.indexOf(".") + 1;
        Boolean check = ((dot > at) && (at != 0))? true : false;
        System.out.println(check);
        return check;
    }
    //Практика Java 1.Lucky Ticket
    public class TicketChecker{
        public static Boolean isLuckyTicket(String ticket) throws Exception {
            int length = ticket.length();
            try {
                Integer.parseInt(ticket);
            }
            catch (NumberFormatException e){

                throw new Exception("Введите число!");
            }
            if (length != 6) {
                throw new Exception("Введите 6 чисел");
            }
            int sum = 0;
            int sum2 = 0;
            for (int i = 0; i < 3; i++) {
                sum += Character.getNumericValue(ticket.charAt(i));
            }
            for (int i = 3; i < 6; i++) {
                sum2 += Character.getNumericValue(ticket.charAt(i));
            }
            return sum == sum2;
        }

    }
    //Практика Java 2.Проверка анаграмм
    public static Boolean areAnagrams(String word1,String word2){
        //Удаляем пробелы
        String firstWordWithoutSpaces = word1.replace(" ", "");
        String secondWordWithoutSpaces = word2.replace(" ", "");
        // Переводим в нижний регистр, дли его игнорирования
        String wordCorrectCase1 = firstWordWithoutSpaces.toLowerCase();
        String wordCorrectCase2 = secondWordWithoutSpaces.toLowerCase();
        // Создаем массив символов с наших строк и сортируем их для дальнейшего сравнения
        char [] charArrayWord1 = wordCorrectCase1.toCharArray();
        Arrays.sort(charArrayWord1);
        char [] charArrayWord2 = wordCorrectCase2.toCharArray();
        Arrays.sort(charArrayWord2);
        return Arrays.equals(charArrayWord1,charArrayWord2);
    }
    }




