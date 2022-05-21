package view;

import controller.IState;
import utils.Pair;
import view.menu.Menu;
import view.menu.MenuCatalog;
import view.menu.OptionCommand;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        System.out.println(ANSI_RED + (error.getMessage() != null ? error.getMessage() : error) + ANSI_RESET);
    }

    public static void showErrors(String error) {
        System.out.println(ANSI_RED + error + ANSI_RESET);
    }

    public static String readString() {
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    public static int readInt() throws InputMismatchException {
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }

    public static Float readFloat() throws InputMismatchException {
        Scanner s = new Scanner(System.in);
        return s.nextFloat();
    }

    public static Boolean readBoolean() throws InputMismatchException {
        Scanner s = new Scanner(System.in);
        return s.nextBoolean();
    }

    public static LocalDate readLocalDate() throws InputMismatchException {
        Scanner s = new Scanner(System.in);
        IO.printLine("Please just enter numbers");
        IO.print("Year: ");
        int year = readInt();
        IO.print("Month: ");
        int month = readInt();
        IO.print("Day: ");
        int day = readInt();
        return LocalDate.of(year,month,day);
    }

    public IO(IState state) {
        this.state = state;
    }

    private String readTinProprietary() {
        IO.print("Enter the tin of the proprietary: ");
        return IO.readString();
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
        try {
            String filepath = IO.readString();
            this.state.saveObjectFile(filepath);
        } catch (IOException e) {
            IO.showErrors(e);
            return;
        }
        IO.printLine("File saved with success");
    }

    private Menu<IO> menuReadSaveState() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Read file by name", IO::readFromFile,true));
        list.add(new OptionCommand<>("Read object file by name", IO::readFromObjectFile, true));
        list.add(new OptionCommand<>("Save object file by name", IO::saveObjectFile, true));
        return new Menu<>("Write and save state options",list,true);
    }

    /*** Create new Devices, Houses, EnergySupplier */

    private void addNewSmartHouse() {
        try {
            IO.printLine("Proprietary Creation:");
            String tin = readTinProprietary();
            IO.print("Enter the name of the proprietary: ");
            String name = IO.readString();
            IO.print("Enter the name of the energy supplier: ");
            String energySupplier = IO.readString();
            this.state.addSmartHouse(tin,name,energySupplier);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void addSmartBulb() {
        try {
            String proprietary = readTinProprietary();
            IO.print("Enter the division: ");
            String division = IO.readString();

            IO.printLine("Create SmartBulb");
            IO.print("Enter the Tone: ");
            String toneName = IO.readString();
            IO.print("Enter the fixedConsumption: ");
            Float fixedConsumption = IO.readFloat();
            IO.print("Enter the diameter: ");
            Float diameter = IO.readFloat();
            this.state.addSmartBulb(division, proprietary, fixedConsumption, toneName,diameter);
            IO.printLine("Smart Device added with success");
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void addSmartCamera() {
        try {
            String proprietary = readTinProprietary();
            IO.print("Enter the division: ");
            String division = IO.readString();

            IO.printLine("Create SmartCamera");
            IO.print("Enter the fixedConsumption: ");
            Float fixedConsumption = IO.readFloat();
            IO.print("Enter the width: ");
            int width = IO.readInt();
            IO.print("Enter the height: ");
            int height = IO.readInt();
            IO.print("Enter the fileSize separeted with \".\": ");
            Float fileSize = IO.readFloat();
            this.state.addSmartCamera(division, proprietary, fixedConsumption,width,height,fileSize);
            IO.printLine("Smart Device added with success");
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void addSmartSpeaker() {
        try {
            String proprietary = readTinProprietary();
            IO.print("Enter the division: ");
            String division = IO.readString();

            IO.printLine("Create SmartCamera");
            IO.print("Enter the fixedConsumption: ");
            Float fixedConsumption = IO.readFloat();
            IO.print("Enter the volume: ");
            int volume = IO.readInt();
            IO.print("Enter the channel: ");
            String channel = IO.readString();
            IO.print("Enter the brand: ");
            String brand = IO.readString();
            this.state.addSmartSpeaker(division, proprietary, fixedConsumption, volume, channel, brand);
            IO.printLine("Smart Device added with success");
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private Menu<IO> menuAddNewDevice() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("SmartBulb", IO::addSmartBulb,true));
        list.add(new OptionCommand<>("SmartCamera", IO::addSmartCamera,true));
        list.add(new OptionCommand<>("SmartSpeaker", IO::addSmartSpeaker,true));
        return new Menu<>("Add New SmartDevice",list,true);
    }

    private void addNewDevice() {
        menuAddNewDevice().runMenu(this);
    }

    private Menu<IO> menuAddNewInformation() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Add new Proprietary and smart house", IO::addNewSmartHouse,false));
        list.add(new OptionCommand<>("Add new Devices", IO::addNewDevice,false));
        //TODO
        // add create energy supplier
        return new Menu<>("Add New Information",list, false);
    }

    /***  Turn off/on devices */
    private void turnOnAllDevicesByTin(){
        try {
            String tin = readTinProprietary();
            this.state.turnOnAllDevicesByTin(tin);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void turnOffAllDevicesByTin(){
        try {
            String tin = readTinProprietary();
            this.state.turnOffAllDevicesByTin(tin);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void turnOffDeviceInDivision(){
        try {
            String tin = readTinProprietary();
            IO.print("Enter the disivion: ");
            String division = IO.readString();
            IO.print("Enter the device's id: ");
            int id = IO.readInt() - 1;
            this.state.turnOffDeviceInDivision(tin,division,id);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void turnOnDeviceInDivision(){
        try {
            String tin = readTinProprietary();
            IO.print("Enter the disivion: ");
            String division = IO.readString();
            IO.print("Enter the device's id: ");
            int id = IO.readInt() - 1;
            this.state.turnOnDeviceInDivision(tin,division,id);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private Menu<IO> menuTurnOnOffDevices() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Turn ON all devices by Proprietary", IO::turnOnAllDevicesByTin,true));
        list.add(new OptionCommand<>("Turn ON one device", IO::turnOnDeviceInDivision));
        list.add(new OptionCommand<>("Turn OFF all devices by Proprietary", IO::turnOffAllDevicesByTin,true));
        list.add(new OptionCommand<>("Turn OFF one device", IO::turnOffDeviceInDivision));
        return new Menu<>("Turn ON/OFF smart devices",list);
    }

    private void showTodaysDate(){
        LocalDate date = this.state.todaysDate();
        date.format(DateTimeFormatter.ISO_DATE);
        IO.printLine("Date :: " + date);
    }

    private void skipDays(){
        try {
            //FIXME change msg
            IO.printLine("How many days do you want to walk forward?");
            int skipDays = IO.readInt();
            this.state.skipDays(skipDays);
            showTodaysDate();
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private Menu<IO> menuDate() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Show Date", IO::showTodaysDate));
        list.add(new OptionCommand<>("Skip Days", IO::skipDays));
        return new Menu<>("Date",list);
    }

    public void highestProfitSupplier() {
        Optional<Pair<String, Double>> result = this.state.highestProfitSupplier();
        if (result.isEmpty()) {
            //FIXME change msg
            IO.printLine("There aren't enough infomation");
        } else {
            Pair<String, Double> profile = result.get();
            IO.printLine("The highest profit supplier is : "
                    + profile.getFirst() + " [" + profile.getSecond() + "]");
        }
    }

    public void mostCostlyHouseBetween() {
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = IO.readLocalDate();
            endDate = IO.readLocalDate();
        } catch (InputMismatchException e) {
            IO.showErrors("The date you entered contains errors");
            return;
        }
        var result = this.state.mostCostlyHouseBetween(startDate,endDate);
        if (result.isEmpty()) {
            //FIXME change msg
            IO.printLine("There aren't enough information");
        } else {
            Pair<String, Double> profile = result.get();
            IO.printLine("The most costly house is : "
                    + profile.getFirst() + " [" + profile.getSecond() + "]");
        }
    }

    //TODO
    private Menu<IO> menuStatics() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Highest profit supplier", IO::highestProfitSupplier));
        list.add(new OptionCommand<>("Most costly house between two dates", IO::mostCostlyHouseBetween));
        //list.add(new OptionCommand<>("Invoices by energy supplier", IO::));
        //list.add(new OptionCommand<>("Energy supplier ranked by invoice volume between two dates", IO::));
        //list.add(new OptionCommand<>("Proprietaries ranked by energy consumption between two dates", IO::));
        return new Menu<>("Statics",list);
    }


    // SHOW

    private void showAllDevicesByTin() {
        String tin = readTinProprietary();
        try {
            HashMap<String, List<String>> result = this.state.allDevicesByTin(tin);
            IO.printLine("");
            for (Map.Entry<String, List<String>> division : result.entrySet()) {
                IO.printLine("\n"+division.getKey());
                int i = 1;
                for( var device : division.getValue()) {
                    IO.printLine(i + " :: " + device);
                    i++;
                }
            }
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void showAllDevicesByTinAndDivision() {
        String tin = readTinProprietary();
        try {
            IO.print("Enter the division: ");
            String division = IO.readString();
            var result = this.state.allDevicesByTinAndDivision(tin,division);
            int i = 1;
            IO.printLine("");
            for( var device : result) {
                IO.printLine(i + " :: " + device);
                i++;
            }
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void showAllProprietaries() {
        this.state.allProprietaries().forEach(proprietary ->
            IO.printLine(proprietary.toString())
        );
    }

    private Menu<IO> menuShow() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Show all proprietaries", IO::showAllProprietaries));
        list.add(new OptionCommand<>("Show all devices by proprietary", IO::showAllDevicesByTin));
        list.add(new OptionCommand<>("Show all devices by proprietary and Division", IO::showAllDevicesByTinAndDivision));
        return new Menu<>("Show information",list);
    }

    private MenuCatalog<IO> menuCatalog() {
        var list = new ArrayList<Menu<IO>>();
        list.add(menuReadSaveState());
        list.add(menuShow());
        list.add(menuAddNewInformation());
        list.add(menuTurnOnOffDevices());
        list.add(menuDate());
        list.add(menuStatics());
        return new MenuCatalog<>(list);
    }

    public void run() {
        menuCatalog().run(this);
    }
}
