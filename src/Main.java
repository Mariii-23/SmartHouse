import model.SmartHousesManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            SmartHousesManager smartHousesManager = SmartHousesManager.fromFile(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
