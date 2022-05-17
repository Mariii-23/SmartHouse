package view;

import control.IState;
import model.proprietary.Proprietary;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.SmartDevice;
import model.smart_house.smart_devices.bulb.SmartBulb;
import model.smart_house.smart_devices.bulb.Tone;
import model.smart_house.smart_devices.camera.SmartCamera;
import model.smart_house.smart_devices.speaker.SmartSpeaker;
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
        System.out.println(ANSI_RED + (error.getMessage() != null ? error.getMessage() : error) + ANSI_RESET);
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

    public static Float readFloat() throws InputMismatchException {
        var s = new Scanner(System.in);
        return s.nextFloat();
    }

    public static Boolean readBoolean() throws InputMismatchException {
        var s = new Scanner(System.in);
        return s.nextBoolean();
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

    //FIXME We have to check if the tin is unique
    private Proprietary createProprietary() {
        IO.printLine("Proprietary Creation:");
        String tin = readTinProprietary();
        IO.print("Enter the name of the proprietary: ");
        String name = IO.readString();
        return new Proprietary(name,tin);
    }

    private void addNewSmartHouse() {
        try {
            Proprietary proprietary = createProprietary();
            IO.print("Enter the name of the energy supplier: ");
            String energySupplier = IO.readString();
            this.state.addSmartHouse(new SmartHouse(proprietary, energySupplier));
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
            Tone tone = Tone.valueOf(toneName.toUpperCase());
            IO.print("Enter the fixedConsumption: ");
            Float fixedConsumption = IO.readFloat();
            IO.print("Enter the diameter: ");
            Float diameter = IO.readFloat();

            SmartDevice device = new SmartBulb(fixedConsumption, tone, diameter);
            this.state.addSmartDevice(device, division, proprietary);
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

            SmartDevice device = new SmartCamera(fixedConsumption,width,height,fileSize);
            this.state.addSmartDevice(device, division, proprietary);
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

            SmartDevice device = new SmartSpeaker(fixedConsumption, volume, channel, brand);
            this.state.addSmartDevice(device, division, proprietary);
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
            int id = IO.readInt();
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
            int id = IO.readInt();
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

    private MenuCatalog<IO> menuCatalog() {
        var list = new ArrayList<Menu<IO>>();
        list.add(menuReadSaveState());
        list.add(menuAddNewInformation());
        list.add(menuTurnOnOffDevices());
        return new MenuCatalog<>(list);
    }

    public void run() {
        menuCatalog().run(this);
    }
}
