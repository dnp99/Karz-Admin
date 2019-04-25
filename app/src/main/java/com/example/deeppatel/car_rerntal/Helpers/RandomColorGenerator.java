package com.example.deeppatel.car_rerntal.Helpers;

import android.graphics.Color;

import java.util.Random;

public class RandomColorGenerator {

    public static int getRandColor(){

        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

    }

}
