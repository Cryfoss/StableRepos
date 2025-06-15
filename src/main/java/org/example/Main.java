package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //isIntEvenNumber(); // 1. Напиши метод, проверяющий, является ли число чётным.
        //isLetterNoMore20Symvols(); // 2. Напиши метод, проверяющий, что введённое имя не больше 20 символов.
        /* int[] res = new int[20];
        for (int i = 0; i < res.length; i++) {
            res[i] = i;
        }// 3. Задаем массив
        reverse(res); // 3. Принимает массив и возвращает новый массив в обратном порядке. */
        //isFirstSymvolsCapital("JavaRoad"); // 4. Напиши метод, который проверяет, начинается ли строка с большой буквы.
        //isMailCorrect() ;// 5. Напиши метод, который принимает email и проверяет, есть ли в нём @ и точка после него
    }
    // 1. Метод позволяющий определить, четное ли число поступило на входе
    public static boolean isIntEvenNumber(int num){
        boolean info = (num % 2 == 0) ? true : false;
        System.out.println(info);
        return info;

    }
    // 2. Метод, ограничивающий ввод, свыше 20 символов
    public static boolean isLetterNoMore20Symvols(String letter){
        boolean letChecked = letter.length() > 20 ? false : true;
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
    public static boolean isFirstSymvolsCapital(String letter){
        boolean asd = Character.isUpperCase(letter.charAt(0));
        System.out.println(asd);
        return asd;
    }
    //5. Метод, который принимает email и проверяет, есть ли в нём @ и точка после него
    public static boolean isMailCorrect(String adress){
        int at = adress.indexOf("@") + 1;
        int dot = adress.indexOf(".") + 1;
        boolean check = ((dot > at) && (at != 0))? true : false;
        System.out.println(check);
        return check;

    }


}