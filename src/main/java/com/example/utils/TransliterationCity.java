package com.example.utils;

import java.util.Map;

// города из отелей и авиабилетов имеют разный язык, надо перевести их
// тут надо добавить все города, но пока решил оставить парочку
public class TransliterationCity {
    public static final Map<String, String> mapCityTranslit = Map.of(
            "Moscow", "Москва",
            "Saint Petersburg","Санкт-Петербург",
            "Samara", "Самара"
    );
}
