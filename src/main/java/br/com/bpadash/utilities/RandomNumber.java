package br.com.bpadash.utilities;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber {
    public static int numeroAleatorioInt(int lengthListPlayer) {
        Random random = new Random();

        return random.nextInt(lengthListPlayer);
    }

    public static Long numeroAleatorioLong() {
        return ThreadLocalRandom.current().nextLong(100000, 999999);
    }

    public static String createIndentifier() {

        return String.valueOf(ThreadLocalRandom.current().nextLong(100000000, 999999999));
    }
}