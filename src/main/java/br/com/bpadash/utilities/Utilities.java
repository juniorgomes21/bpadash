package br.com.bpadash.utilities;

import java.time.LocalDate;

public class Utilities {

    public static LocalDate formatDate(String dateString) {
        return LocalDate.of(Integer.parseInt(dateString.split("-")[0]), Integer.parseInt(dateString.split("-")[1]), Integer.parseInt(dateString.split("-")[2]));
    }
}
