package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //checkInt(); // 1. Напиши метод, проверяющий, является ли число чётным.
        //nameCheck(); // 2. Напиши метод, проверяющий, что введённое имя не больше 20 символов.
            /* int[] res = new int[20]; // 3. Задаем массив
            reverse(res); // 3. Принимает массив и возвращает новый массив в обратном порядке. */
        //capitalLetter("JavaRoad"); // 4. Напиши метод, который проверяет, начинается ли строка с большой буквы.
        //checkedMail() ;// 5. Напиши метод, который принимает email и проверяет, есть ли в нём @ и точка после него
    }
    // 1. Метод позволяющий определить, четное ли число поступило на входе
    public static void checkInt(){
        Scanner console = new Scanner(System.in);
        int a = console.nextInt();
        String info = (a % 2 == 0) ? "число четное" : "число не четное";
        System.out.println(info);
    }
    // 2. Метод, ограничивающий ввод, свыше 20 символов
    public static void nameCheck(){
        Scanner console = new Scanner(System.in);
        String name = console.nextLine();
        if (name.length() > 20){
            System.out.println("имя слишком длинное");
        }
        else {
            System.out.println(name);
        }
    }
    // 3. Метод, принимающий массив и выводящий его, в обратном порядке
    public static void reverse(int[]array){
        for (int i = array.length - 1; i > 0; i--) {
            array[i] = i;
            System.out.println(array[i]);
        }
    }
    // 4. Метод, проверяющий, начинается ли слово с большой буквы (с русской раскладкой почему-то не робит)
    public static void capitalLetter(String letter){
        char cFirstIndex = letter.charAt(0);
        String firstIndex = String.valueOf(cFirstIndex);
        String lowIndex = firstIndex.toLowerCase();
        Boolean checkIndex = firstIndex.equals(lowIndex);
        if (checkIndex == true){
            System.out.println("Строка начинается с маленькой буквы");
        }
        else {
            System.out.println("Строка начинается с большой буквы");
        }
    }
    //5. Метод, который принимает email и проверяет, есть ли в нём @ и точка после него
    public static void checkedMail(){
        Scanner scr = new Scanner(System.in);
        String mail = scr.nextLine();
        int sobachka = mail.indexOf("@") + 1;
        int tochka = mail.indexOf(".") + 1;
        if((tochka > sobachka) && (sobachka != 0)){
            System.out.println("Email отправлен");
        }
        else {
            System.out.println("ВСЕ СЛОМАЛОСЬ!");
        }

    }


}