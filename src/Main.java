import controller.IState;
import controller.State;
import view.IIO;
import view.IO;

public class Main {
    public static void main(String[] args) {
        IState state = new State();
        IIO io = new IO(state);
        io.run();
        //try {
        //    SmartHousesManager smartHousesManager = SmartHousesManager.fromFile(args[0]);

        //    String filename = "fileObject";
        //    System.out.println("Write object file");
        //    smartHousesManager.saveObjectFile(filename);
        //    System.out.println("Read object file");
        //    smartHousesManager = SmartHousesManager.readObjectFile(filename);
        //    System.out.println(smartHousesManager);

        //} catch (IOException | ClassNotFoundException e) {
        //    e.printStackTrace();
        //}
    }
}
