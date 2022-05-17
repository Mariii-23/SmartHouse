package view.menu;

import view.IO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import static view.Colors.ANSI_BOLD;
import static view.Colors.ANSI_RESET;

public class Menu<T> {
    private final List<OptionCommand<T>> options;
    private final String description;
    private int option;
    private Boolean stop;

    // agregacao
    public Menu(String description, List<OptionCommand<T>> options, boolean stop) {
        this.options = options;
        this.option = -1;
        this.description = description;
        this.stop = stop;
    }

    public Menu(String description, List<OptionCommand<T>> options) {
        this(description, options, false);
    }

    public String getDescription() {
        return description;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int sizeOption() {
        return this.options.size();
    }

    public int readOption() {
        int op;
        var is = new Scanner(System.in);

        IO.print(ANSI_BOLD + "Option: ");
        try {
            op = is.nextInt();
        } catch (InputMismatchException e) { // Não foi inscrito um int
            op = -1;
        }
        if (op < 0 || op > this.options.size()) {
            IO.printLine("Invalid Option!!!");
            op = -1;
        }
        return op;
    }

    public Boolean isValid() {
        return this.option - 1  >= 0 && this.option - 1 < this.options.size();
    }

    public Consumer<T> getFunction() {
        return this.options.get(this.option - 1).getFunction();
    }

    public void runMenu(T that) {
        this.showMenu();
        do {
            IO.printLine(this.showMenu());
            this.setOption(this.readOption());
            IO.printLine("");
            if (this.isValid()) {
                Consumer<T> function = this.getFunction();
                function.accept(that);
                if (stop() || this.stop) {
                    this.option = this.sizeOption() - 1;
                }
            }
            IO.printLine("");
        } while (this.getOption() != 0);
    }

    public Boolean stop() {
        if (isValid())
            return this.options.get(option).stop();
        return false;
    }

    public String showMenu() {
        var s = new StringBuilder();
        int i = 0;
        s.append("\n");
        s.append(ANSI_BOLD).append("MENU").append(ANSI_RESET).append("\n");
        for (OptionCommand o : this.options) {
            i++;
            s.append("\t").append(ANSI_BOLD).append(i).append(": ").append(ANSI_RESET);
            s.append(o.getCommand());
            s.append("\n");
        }
        s.append("\t").append(ANSI_BOLD).append(0).append(": ").append(ANSI_RESET).append("Go Back\n");
        return s.toString();
    }
}