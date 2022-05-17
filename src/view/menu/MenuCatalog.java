package view.menu;

import view.IO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static view.Colors.ANSI_BOLD;
import static view.Colors.ANSI_RESET;

public class MenuCatalog<T> {
    private final List<Menu<T>> menus;
    private int option;

    // agregacao
    public MenuCatalog(List<Menu<T>> menus) {
        this.menus = menus;
        this.option = -1;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int sizeOption() {
        return this.menus.size();
    }

    public int readOption() {
        int op;
        var is = new Scanner(System.in);

        IO.print(ANSI_BOLD + "Option: ");
        try {
            op = is.nextInt();
        } catch (InputMismatchException e) {
            op = -1;
        }
        if (op < 0 || op > this.menus.size()) {
            IO.printLine("Invalid Option!!!");
            op = -1;
        }
        return op;
    }

    public Boolean isValid() {
        return this.option - 1 >= 0 && this.option - 1  < this.menus.size();
    }

    public String showMenus() {
        var s = new StringBuilder();
        int i = 0;
        s.append("\n");
        s.append(ANSI_BOLD).append("MENU").append(ANSI_RESET).append("\n");
        for (Menu<T> o : this.menus) {
            i++;
            s.append("\t").append(ANSI_BOLD).append(i).append(": ").append(ANSI_RESET);
            s.append(o.getDescription());
            s.append("\n");
        }
        s.append("\t").append(ANSI_BOLD).append(0).append(": ").append(ANSI_RESET).append("Exit\n");
        return s.toString();
    }

    private Menu<T> getMenu() {
        return this.menus.get(this.option - 1);
    }

    public void run(T that) {
        this.showMenus();
        do {
            IO.printLine(this.showMenus());
            this.setOption(this.readOption());
            IO.printLine("");
            if (this.isValid()) {
                var menu = this.getMenu();
                menu.runMenu(that);
            }
            IO.printLine("");
        } while (this.getOption() != 0);
    }
}