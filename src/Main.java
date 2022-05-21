import controller.IState;
import controller.State;
import view.IIO;
import view.IO;

public class Main {
    public static void main(String[] args) {
        IState state = new State();
        IIO io = new IO(state);
        io.run();
    }
}
