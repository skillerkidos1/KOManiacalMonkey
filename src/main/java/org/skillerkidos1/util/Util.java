package org.skillerkidos1.util;

import org.powbot.api.rt4.Players;

public class Util {




    public static String state(String s) {
        System.out.println(s);
        return s;
    }

    public boolean isAnimating(){
        return Players.local().animation()!=-1;
    }












}
