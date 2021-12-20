package pdp.uz.jwtproject.helpers;

import java.util.List;
import java.util.Random;

public class Utils {

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isEmpty(List<?> items) {
        return items == null || items.isEmpty();
    }

    public static boolean isEmpty(Object l) {
        return l == null;
    }

    public static String generateRandom() {
        Random rand = new Random(); //instance of random class
        int upperbound = 999999;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);
        return int_random > 99999 ? "" + int_random : "" + (100000 + int_random);
    }
}
