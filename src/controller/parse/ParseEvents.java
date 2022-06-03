package controller.parse;

import controller.parse.exceptions.*;
import model.*;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.EnergySupplierAlreadyExistsException;
import model.smart_house.WrongTypeOfDeviceException;
import model.smart_house.smart_devices.SmartDevice;
import model.smart_house.smart_devices.bulb.SmartBulb;
import model.smart_house.smart_devices.bulb.Tone;
import model.smart_house.smart_devices.camera.SmartCamera;
import model.smart_house.smart_devices.speaker.SmartSpeaker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseEvents {
    public static void fromFile(ISmartHousesManager smartHousesManager, String path)
            throws IOException, ParseEventException, DeviceDoesNotExistException, ProprietaryDoesNotExistException,
            DivisionDoesNotExistException, EnergySupplierDoesNotExistException, ClassNotFoundException,
            WrongTypeOfDeviceException, EnergySupplierAlreadyExistsException, ProprietaryAlreadyExistException {

        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        Iterator<String> it = lines.iterator();
        LocalDate simulationDate = smartHousesManager.getDate();

        Pattern pattern = Pattern.compile("(?<date>\\d{4}-\\d{2}-\\d{2})\\s*:\\s*(?<fn>\\w+)\\((?<args>([\\w\\d,]|\\([\\w\\d,]*\\))*)\\)");
        while (it.hasNext()) {
            String line = it.next();
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                throw new InvalidLineSyntaxException("Invalid syntax on line: " + line);
            }

            LocalDate newDate = LocalDate.parse(matcher.group("date"));
            if (newDate.isAfter(simulationDate)) {
                smartHousesManager.skipToDate(newDate);
                simulationDate = newDate;
            } else if (newDate.isBefore(simulationDate)) {
                throw new DateBeforeSimulationDateException("The date " + newDate + " is earlier than the simulation date: " + simulationDate);
            }

            String functionName = matcher.group("fn");
            String[] args = matcher.group("args").split(",");
            try {
                switch (functionName) {
                    case "addEnergySupplier" -> smartHousesManager.addEnergySupplier(args[0]);

                    case "changeEnergyPlan" -> smartHousesManager.changeEnergyPlan(args[0], args[1]);

                    case "changeEnergySupplierDiscount" -> smartHousesManager.changeEnergySupplierDiscount(args[0], Integer.parseInt(args[1]));

                    case "addSmartHouse" -> smartHousesManager.addSmartHouse(args[0], args[1], args[2]);

                    case "addSmartDeviceToHouse" -> smartHousesManager.addSmartDeviceToHouse(args[0], args[1], parseSmartDevice(args[2]));

                    case "turnOffAllHouseDevices" -> smartHousesManager.turnOffAllHouseDevices(args[0]);

                    case "turnOnAllHouseDevices" -> smartHousesManager.turnOnAllHouseDevices(args[0]);

                    case "turnOffDeviceInDivision" -> smartHousesManager.turnOffDeviceInDivision(args[0], args[1], Integer.parseInt(args[2]));

                    case "turnOnDeviceInDivision" -> smartHousesManager.turnOnDeviceInDivision(args[0], args[1], Integer.parseInt(args[2]));

                    case "smartBulbChangeTone" -> smartHousesManager.smartBulbChangeTone(args[0], args[1], Integer.parseInt(args[2]), Tone.valueOf(args[3].toUpperCase()));

                    case "smartSpeakerVolumeDown" -> smartHousesManager.smartSpeakerVolumeDown(args[0], args[1], Integer.parseInt(args[2]));

                    case "smartSpeakerVolumeUp" -> smartHousesManager.smartSpeakerVolumeUp(args[0], args[1], Integer.parseInt(args[2]));

                    default -> throw new FunctionNotDefinedException("Function " + functionName + " is not defined");
                }
            } catch (IllegalArgumentException _e) {
                throw new FunctionErrorParsingArgumentException("Error parsing arguments of function " + functionName);
            } catch (ArrayIndexOutOfBoundsException _e) {
                throw new FunctionWrongNumberOfArgumentsException("Wrong number of arguments passed to function " + functionName);
            }
        }
    }

    private static SmartDevice parseSmartDevice(String str) throws ParseEventException {
        Pattern pattern = Pattern.compile("(?<sd>\\w+)\\((?<args>[\\w\\d,]*)\\)");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            throw new FunctionErrorParsingArgumentException("Error parsing Smart Device");
        }
        String ctorName = matcher.group("sd");
        String[] args = matcher.group("args").split(",");
        try {
            return switch (ctorName) {
                case "SmartBulb" -> new SmartBulb(
                        Float.parseFloat(args[0]),
                        Tone.valueOf(args[1].toUpperCase()),
                        Float.parseFloat(args[2])
                );
                case "SmartSpeaker" -> new SmartSpeaker(
                        Float.parseFloat(args[0]),
                        Integer.parseInt(args[1]),
                        args[2],
                        args[3]
                );
                case "SmartCamera" -> new SmartCamera(
                        Float.parseFloat(args[0]),
                        Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]),
                        Float.parseFloat(args[3])
                );
                default -> throw new FunctionNotDefinedException("Smart Device " + ctorName + " is not defined");
            };

        } catch (IllegalArgumentException _e) {
            throw new FunctionErrorParsingArgumentException("Error parsing arguments of constructor " + ctorName);
        } catch (ArrayIndexOutOfBoundsException _e) {
            throw new FunctionWrongNumberOfArgumentsException("Wrong number of arguments passed to constructor " + ctorName);
        }
    }

}
