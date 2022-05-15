import model.SmartHousesManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            SmartHousesManager smartHousesManager = SmartHousesManager.fromFile(args[0]);

            String filename = "fileObject";
            System.out.println("Write object file");
            smartHousesManager.saveObjectFile(filename);
            System.out.println("Read object file");
            smartHousesManager = SmartHousesManager.readObjectFile(filename);
            System.out.println(smartHousesManager);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
