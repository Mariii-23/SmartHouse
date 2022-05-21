package view;

import controller.IState;
import model.DivisionDoesNotExistException;
import model.ProprietaryDoesNotExistException;
import model.energy_suppliers.Invoice;
import model.smart_house.proprietary.Proprietary;
import model.smart_house.smart_devices.SmartDevice;
import model.smart_house.smart_devices.bulb.SmartBulb;
import model.smart_house.smart_devices.camera.SmartCamera;
import model.smart_house.smart_devices.speaker.SmartSpeaker;
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

    private Optional<String> tin = Optional.empty();
    private Optional<String> division = Optional.empty();
    private int id = -1;

    public IO(IState state) {
        this.state = state;
    }

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

    public static LocalDate readLocalDate() throws InputMismatchException {
        IO.printLine("Please just enter numbers");
        IO.print("Year: ");
        int year = IO.readInt();
        IO.print("Month: ");
        int month = IO.readInt();
        IO.print("Day: ");
        int day = IO.readInt();
        return LocalDate.of(year, month, day);
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

    private void readEvents() {
        IO.printLine("Enter the name of file to be read:");
        String filepath = IO.readString();
        try {
            this.state.readEventsFromFile(filepath);
        } catch (Exception e) {
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
        list.add(new OptionCommand<>("Read logs file", IO::readFromFile, true));
        list.add(new OptionCommand<>("Read object file", IO::readFromObjectFile, true));
        list.add(new OptionCommand<>("Save object file", IO::saveObjectFile, true));
        list.add(new OptionCommand<>("Read events file", IO::readEvents, true));
        return new Menu<>("Write and save state options", list, true);
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
            this.state.addSmartHouse(tin, name, energySupplier);
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
            this.state.addSmartBulb(division, proprietary, fixedConsumption, toneName, diameter);
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
            this.state.addSmartCamera(division, proprietary, fixedConsumption, width, height, fileSize);
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

    public void addEnergySupplier() {
        try {
            IO.print("Enter the energy supplier name: ");
            String energySupplier = IO.readString();
            this.state.addEnergySupplier(energySupplier);
            IO.printLine("Energy Supllier added with success");
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private Menu<IO> menuAddNewDevice() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("SmartBulb", IO::addSmartBulb, true));
        list.add(new OptionCommand<>("SmartCamera", IO::addSmartCamera, true));
        list.add(new OptionCommand<>("SmartSpeaker", IO::addSmartSpeaker, true));
        return new Menu<>("Add New SmartDevice", list, true);
    }

    private void addNewDevice() {
        menuAddNewDevice().runMenu(this);
    }

    private Menu<IO> menuAddNewInformation() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Add new Proprietary and smart house", IO::addNewSmartHouse, false));
        list.add(new OptionCommand<>("Add new Devices", IO::addNewDevice, false));
        list.add(new OptionCommand<>("Add new Energy Supplier", IO::addEnergySupplier, false));
        return new Menu<>("Add New Information", list, false);
    }

    /***  Turn off/on devices */
    private void turnOnAllDevicesByTin() {
        try {
            String tin = readTinProprietary();
            this.state.turnOnAllDevicesByTin(tin);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void turnOffAllDevicesByTin() {
        try {
            String tin = readTinProprietary();
            this.state.turnOffAllDevicesByTin(tin);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void turnOffDeviceInDivision() {
        try {
            if (tin.isEmpty() || division.isEmpty() || id < 0) {
                getDevice();
            }
            this.state.turnOffDeviceInDivision(tin.get(), division.get(), id);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        } finally {
            id = -1;
            this.tin = Optional.empty();
            this.division = Optional.empty();
        }
    }

    private void turnOnDeviceInDivision() {
        try {
            if (tin.isEmpty() || division.isEmpty() || id < 0) {
                getDevice();
            }
            this.state.turnOnDeviceInDivision(tin.get(), division.get(), id);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        } finally {
            id = -1;
            this.tin = Optional.empty();
            this.division = Optional.empty();
        }
    }

    private void runMenuTurnOnOffDevices() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Turn ON all devices by Proprietary", IO::turnOnAllDevicesByTin, true));
        list.add(new OptionCommand<>("Turn ON one device", IO::turnOnDeviceInDivision));
        list.add(new OptionCommand<>("Turn OFF all devices by Proprietary", IO::turnOffAllDevicesByTin, true));
        list.add(new OptionCommand<>("Turn OFF one device", IO::turnOffDeviceInDivision));
        Menu<IO> menu = new Menu<>("Turn ON/OFF smart devices", list);
        menu.runMenu(this);
    }

    private void showTodaysDate() {
        LocalDate date = this.state.todaysDate();
        date.format(DateTimeFormatter.ISO_DATE);
        IO.printLine("Date :: " + date);
    }

    private void skipDays() {
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

    private void skipTODate() {
        try {
            LocalDate date = IO.readLocalDate();
            this.state.skipToDate(date);
            showTodaysDate();
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void runMenuDate() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Show Date", IO::showTodaysDate));
        list.add(new OptionCommand<>("Skip Days", IO::skipDays));
        list.add(new OptionCommand<>("Skip to date", IO::skipTODate));
        Menu<IO> menu = new Menu<>("Date", list);
        menu.runMenu(this);
    }

    private void highestProfitSupplier() {
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

    private void mostCostlyHouseBetween() {
        LocalDate startDate, endDate;
        try {
            startDate = IO.readLocalDate();
            endDate = IO.readLocalDate();
        } catch (InputMismatchException e) {
            IO.showErrors("The date you entered contains errors");
            return;
        }
        var result = this.state.mostCostlyHouseBetween(startDate, endDate);
        if (result.isEmpty()) {
            //FIXME change msg
            IO.printLine("There aren't enough information");
        } else {
            Pair<String, Double> profile = result.get();
            IO.printLine("The most costly house is : "
                    + profile.getFirst() + " [" + profile.getSecond() + "]");
        }
    }

    private void invoicesByEnergySupplier() {
        IO.print("Energy Supplier: ");
        try {
            String energy = IO.readString();
            List<Invoice> invoices = this.state.invoicesByEnergySupplier(energy);
            invoices.forEach(e -> IO.printLine(e.toString()));
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void energySuppliersRankedByInvoiceVolumeBetween() {
        LocalDate startDate, endDate;
        try {
            startDate = IO.readLocalDate();
            endDate = IO.readLocalDate();
        } catch (InputMismatchException e) {
            IO.showErrors("The date you entered contains errors");
            return;
        }
        try {
            List<Pair<String, Double>> result = this.state.energySuppliersRankedByInvoiceVolumeBetween(startDate, endDate);
            result.forEach(e -> IO.printLine(e.getFirst() + " :: " + e.getSecond()));
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void proprietariesRankedByEnergyConsumptionBetween() {
        LocalDate startDate, endDate;
        try {
            startDate = IO.readLocalDate();
            endDate = IO.readLocalDate();
        } catch (InputMismatchException e) {
            IO.showErrors("The date you entered contains errors");
            return;
        }
        try {
            List<Pair<Proprietary, Double>> result = this.state.proprietariesRankedByEnergyConsumptionBetween(startDate, endDate);
            result.forEach(e -> IO.printLine(e.getFirst().getName() + " :: " + e.getSecond()));
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private Menu<IO> menuStatics() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Highest profit supplier", IO::highestProfitSupplier));
        list.add(new OptionCommand<>("Most costly house between two dates", IO::mostCostlyHouseBetween));
        list.add(new OptionCommand<>("Invoices by energy supplier", IO::invoicesByEnergySupplier));
        list.add(new OptionCommand<>("Energy supplier ranked by invoice volume between two dates",
                IO::energySuppliersRankedByInvoiceVolumeBetween));
        list.add(new OptionCommand<>("Proprietaries ranked by energy consumption between two dates",
                IO::proprietariesRankedByEnergyConsumptionBetween));
        return new Menu<>("Statics", list);
    }


    // SHOW

    private void showAllDevicesByTin() {
        String tin = readTinProprietary();
        try {
            HashMap<String, List<SmartDevice>> result = this.state.allDevicesByTin(tin);
            IO.printLine("");
            for (Map.Entry<String, List<SmartDevice>> division : result.entrySet()) {
                IO.printLine("\n" + division.getKey());
                int i = 1;
                for (var device : division.getValue()) {
                    IO.printLine(i + " :: " + device.getSimpleName());
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
            var result = this.state.allDevicesByTinAndDivision(tin, division);
            int i = 1;
            IO.printLine("");
            for (var device : result) {
                IO.printLine(i + " :: " + device.getSimpleName());
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

    private void showAllEnergyPlans() {
        IO.printLine("All energy plans\n");
        for (String energyPlan : this.state.getAllEnergyPlans()) {
            IO.printLine(energyPlan);
            IO.printLine("");
        }
    }

    private void showAllEnergySuplliersName() {
        IO.printLine("All  Energy Suplliers Name\n");
        for (String energy : this.state.allEnergySuppliersName()) {
            IO.printLine(energy);
        }
    }

    private Menu<IO> menuShow() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Show all proprietaries", IO::showAllProprietaries));
        list.add(new OptionCommand<>("Show all devices by proprietary", IO::showAllDevicesByTin));
        list.add(new OptionCommand<>("Show all devices by proprietary and Division", IO::showAllDevicesByTinAndDivision));
        list.add(new OptionCommand<>("Show all energy plans", IO::showAllEnergyPlans));
        list.add(new OptionCommand<>("Show all energy suplliers name", IO::showAllEnergySuplliersName));
        list.add(new OptionCommand<>("Show Date", IO::showTodaysDate));
        return new Menu<>("Show information", list);
    }

    //Control
    private void changeEnergyPlan() {
        try {
            showAllEnergySuplliersName();
            IO.printLine("");
            showAllEnergyPlans();
            IO.print("Energy Supplier name");
            String energySupplier = IO.readString();
            IO.print("Energy Plan name");
            String energyPlan = IO.readString();
            this.state.changeEnergyPlan(energySupplier, energyPlan);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void changeEnergySupllierDiscount() {
        try {
            showAllEnergySuplliersName();
            IO.print("Energy Supplier name");
            String energySupplier = IO.readString();
            IO.print("Discount");
            int discount = IO.readInt();
            this.state.changeEnergySupplierDiscount(energySupplier, discount);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    // control devices

    private void smartBulbChangeTone() {
        try {
            if (tin.isEmpty() || division.isEmpty() || id < 0) {
                IO.printLine("");
                IO.printLine(getDevice().toString());
            }
            IO.print("Tone: ");
            String tone = IO.readString();
            this.state.smartBulbChangeTone(tin.get(), division.get(), id, tone);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void controlSmartBulb() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Change Tone", IO::smartBulbChangeTone));
        list.add(new OptionCommand<>("Turn OFF", IO::turnOffDeviceInDivision));
        list.add(new OptionCommand<>("Turn On", IO::turnOnDeviceInDivision));
        var menu = new Menu<>("Control SmartSpeaker", list);
        menu.runMenu(this);
    }

    private void controlSmartCamera() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Turn OFF", IO::turnOffDeviceInDivision));
        list.add(new OptionCommand<>("Turn On", IO::turnOnDeviceInDivision));
        var menu = new Menu<>("Control SmartCamera", list);
        menu.runMenu(this);
    }

    private void smartSpeakerVolumeUp() {
        try {
            if (tin.isEmpty() || division.isEmpty() || id < 0) {
                IO.printLine("");
                IO.printLine(getDevice().toString());
            }
            this.state.smartSpeakerVolumeUp(tin.get(), division.get(), id);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void smartSpeakerVolumeDown() {
        try {
            if (tin.isEmpty() || division.isEmpty() || id < 0) {
                IO.printLine("");
                IO.printLine(getDevice().toString());
            }
            this.state.smartSpeakerVolumeDown(tin.get(), division.get(), id);
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        }
    }

    private void controlSmartSpeaker() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Volume Down", IO::smartSpeakerVolumeDown));
        list.add(new OptionCommand<>("Volume Up", IO::smartSpeakerVolumeUp));
        list.add(new OptionCommand<>("Turn OFF", IO::turnOffDeviceInDivision));
        list.add(new OptionCommand<>("Turn On", IO::turnOnDeviceInDivision));
        var menu = new Menu<>("Control SmartSpeaker", list);
        menu.runMenu(this);
    }

    private SmartDevice getDevice() throws ProprietaryDoesNotExistException, DivisionDoesNotExistException {
        tin = Optional.of(readTinProprietary());
        IO.print("Enter the division: ");
        division = Optional.of(IO.readString());
        List<SmartDevice> result = this.state.allDevicesByTinAndDivision(tin.get(), division.get());
        int i = 1;
        IO.printLine("");
        for (var device : result) {
            IO.printLine(i + " :: " + device.getSimpleName());
            i++;
        }

        IO.printLine("Which device do you want?");
        IO.print("Id: ");
        id = IO.readInt();
        id--;
        if (id > result.size() || id < 0) {
            throw new IndexOutOfBoundsException();
        }
        return result.get(id);
    }

    private void controlDevices() {
        try {
            SmartDevice device = getDevice();
            IO.printLine("");
            IO.printLine(device.toString());
            if (device instanceof SmartBulb) {
                controlSmartBulb();
            } else if (device instanceof SmartCamera) {
                controlSmartCamera();
            } else if (device instanceof SmartSpeaker) {
                controlSmartSpeaker();
            }
        } catch (Exception e) {
            IO.showErrors("Error occurred");
            IO.showErrors(e);
        } finally {
            id = -1;
            this.tin = Optional.empty();
            this.division = Optional.empty();
        }
    }

    private Menu<IO> menuControl() {
        ArrayList<OptionCommand<IO>> list = new ArrayList<>();
        list.add(new OptionCommand<>("Control date", IO::runMenuDate));
        list.add(new OptionCommand<>("Turn ON/OFF smart devices", IO::runMenuTurnOnOffDevices));
        list.add(new OptionCommand<>("Change energy plan", IO::changeEnergyPlan));
        list.add(new OptionCommand<>("Change Energy supllier discount", IO::changeEnergySupllierDiscount));
        list.add(new OptionCommand<>("Control devices", IO::controlDevices));
        return new Menu<>("Control the Houses", list);
    }

    private MenuCatalog<IO> menuCatalog() {
        var list = new ArrayList<Menu<IO>>();
        list.add(menuReadSaveState());
        list.add(menuShow());
        list.add(menuAddNewInformation());
        list.add(menuControl());
        list.add(menuStatics());
        return new MenuCatalog<>(list);
    }

    public void run() {
        menuCatalog().run(this);
    }
}
