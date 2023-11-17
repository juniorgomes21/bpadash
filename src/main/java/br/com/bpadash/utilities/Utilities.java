package br.com.bpadash.utilities;

import java.time.LocalDate;

public class Utilities {

    public static LocalDate formatDate(String dateString) {
        return LocalDate.of(Integer.valueOf(dateString.split("-")[0]), Integer.valueOf(dateString.split("-")[1]), Integer.valueOf(dateString.split("-")[2]));
    }
}
