package control;

import model.ISmartHouseManager;
import model.SmartHousesManager;
import model.parse.Parser;

import java.io.IOException;

public class State implements IState {
    private ISmartHouseManager smartHouseManage;

    public State() {
        this.smartHouseManage = new SmartHousesManager();
    }

    public void readFromObjectFile(final String filepath) throws IOException, ClassNotFoundException {
        this.smartHouseManage = SmartHousesManager.readObjectFile(filepath);
    }

    public void readFromFile(final String filepath) throws IOException {
        this.smartHouseManage = Parser.parse(filepath);
    }

    public void saveObjectFile(final String filepath) throws IOException {
        this.smartHouseManage.saveObjectFile(filepath);
    }
}
