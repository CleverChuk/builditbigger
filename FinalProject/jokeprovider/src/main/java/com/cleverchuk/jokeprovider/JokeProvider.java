package com.cleverchuk.jokeprovider;

import java.util.Random;

public class JokeProvider {
    private static final String[] JOKES = {
            "this guy says hey, what did the people say? BOO YAA!!",
            "You know the meaning of SOHCAHTOA: some old hippie caught another hippie" +
                    " tripping on acid",
            "you know what's funny? your momma's face"
    };

    public static String getJoke(){
        return JOKES[new Random().nextInt(3)];
    }
}
