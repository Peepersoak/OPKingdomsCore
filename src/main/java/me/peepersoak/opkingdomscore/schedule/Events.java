package me.peepersoak.opkingdomscore.schedule;

import java.util.ArrayList;
import java.util.List;

public class Events {

    private static final List<String> registerEventName = new ArrayList<>();

    public static List<String> getRegisterEventName() {
        return registerEventName;
    }

    public static void addEvents(String str) {
        registerEventName.add(str);
    }

    public static void removeEvents(String str) {
        registerEventName.remove(str);
    }
}
