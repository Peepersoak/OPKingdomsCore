package me.peepersoak.opkingdomscore.dragon_event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DragonEggData {

    private static final List<String> eggData = new ArrayList<>();

    public void getYMLData() {
        try {
            String url = "/egg.yml";
            InputStream is = DragonEggData.class.getResourceAsStream(url);
            if (is == null) {
                System.out.println("------");
                System.out.println("Restart the server to load the egg data properly!");
                System.out.println("------");
                return;
            }
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                eggData.add(line);
            }
            reader.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            //
        }
    }

    public static List<String> getEggData() {
        return eggData;
    }
}
