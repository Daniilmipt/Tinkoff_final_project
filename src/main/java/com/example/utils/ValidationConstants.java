package com.example.utils;

public class ValidationConstants {

    private ValidationConstants() {
    }

    public static final String LOGIN_PATTERN = "[a-zA-Z0-9-_]*";

    public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";

    public static final String FIRST_NAME_PATTERN = "^(?=.{1,}$)[а-яА-Я]+(?:[-' ][а-яА-Я]+)*$";

    public static final String LAST_NAME_PATTERN = "^(?=.{1,}$)[а-яА-Я]+(?:[-' ][а-яА-Я]+)*$";

    public static final String PHONE_PATTERN = "^(?=.{0,}$)[0-9]*$";

    public static final String INTEGER_PATTERN = "^[1-9]\\d*$";

    public static final String STRING_PATTERN = "^[- а-яА-Я]+$";


}