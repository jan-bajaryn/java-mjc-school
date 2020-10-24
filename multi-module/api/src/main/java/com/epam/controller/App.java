package com.epam.controller;

import com.epam.service.Utils;

public class App {
    public static void main(String[] args) {
        Utils utils = new Utils();
        System.out.println("utils.isAllPositiveNumbers(\"23\",\"23\") = " + utils.isAllPositiveNumbers("-4.4"));
    }
}
