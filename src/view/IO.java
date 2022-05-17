package view;

import control.IState;
import view.menu.Menu;
import view.menu.MenuCatalog;
import view.menu.OptionCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static view.Colors.ANSI_RED;
import static view.Colors.ANSI_RESET;

public class IO implements IIO {
    private final IState state;

    public static void print(String string) {
        System.out.print(string);
    }

    public static void printLine(String string) {
        System.out.println(string);
    }

    public static void showErrors(Exception error) {
        System.out.println(ANSI_RED + error + ANSI_RESET);
    }

    public static void showErrors(String error) {
        System.out.println(ANSI_RED + error + ANSI_RESET);
    }

    public static String readString() {
        var s = new Scanner(System.in);
        return s.nextLine();
    }

    public static int readInt() throws InputMismatchException {
        var s = new Scanner(System.in);
        return s.nextInt();
    }

    public IO(IState state) {
        this.state = state;
    }

    private void readFromFile() {
        IO.printLine("Enter the name of file to be read:");
        String filepath = IO.readString();
        try {
            this.state.readFromFile(filepath);
        } catch (IOException e) {
            IO.showErrors(e);
            return;
        }
        IO.printLine("File read with success");
    }

    private void readFromObjectFile() {
        IO.printLine("Enter the name of file to be read:");
        String filepath = IO.readString();
        try {
            this.state.readFromObjectFile(filepath);
        } catch (IOException | ClassNotFoundException e) {
            IO.showErrors(e);
            return;
        }
        IO.printLine("File read with success");
    }

    private void saveObjectFile() {
        IO.printLine("Enter the name of file to be saved:");
        String filepath = IO.readString();
        try {
            this.state.saveObjectFile(filepath);
        } catch (IOException e) {
            IO.showErrors(e);
            return;
        }
        IO.printLine("File saved with success");
    }

    private Menu<IO> menuReadSaveState() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<OptionCommand<IO>>();
        list.add(new OptionCommand<>("Read file by name", IO::readFromFile,true));
        list.add(new OptionCommand<>("Read object file by name", IO::readFromObjectFile, true));
        list.add(new OptionCommand<>("Save object file by name", IO::saveObjectFile, true));
        return new Menu<>("Write and save state options",list,true);
    }


    private MenuCatalog<IO> menuCatalog() {
        var list = new ArrayList<Menu<IO>>();
        list.add(menuReadSaveState());
        return new MenuCatalog<>(list);
    }

    public void run() {
        menuCatalog().run(this);
    }
}
