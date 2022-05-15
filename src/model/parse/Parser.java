package model.parse;

import model.EnergySupplierDoesNotExistException;
import model.SmartHousesManager;
import model.proprietary.Proprietary;
import model.smart_house.Division;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.bulb.SmartBulb;
import model.smart_house.smart_devices.bulb.Tone;
import model.smart_house.smart_devices.camera.SmartCamera;
import model.smart_house.smart_devices.speaker.SmartSpeaker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static SmartHousesManager parse(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        Iterator<String> it = lines.iterator();

        SmartHousesManager smartHousesManager = new SmartHousesManager();

        String nextLine = it.hasNext() ? it.next() : null;
        while (nextLine != null) {
            String[] splitLine = nextLine.split(":", 2);
            switch (splitLine[0]) {
                case "Fornecedor" -> {
                    smartHousesManager.addEnergySupplier(splitLine[1]);
                    nextLine = it.hasNext() ? it.next() : null;
                }
                case "Casa" -> {
                    String[] houseInfo = splitLine[1].split(",", 3);
                    Proprietary proprietary = new Proprietary(houseInfo[0], houseInfo[1]);
                    SmartHouse smartHouse = new SmartHouse(proprietary, houseInfo[2]);
                    nextLine = parseSmartHouse(it, smartHouse);
                    try {
                        smartHousesManager.addSmartHouse(smartHouse);
                    } catch (EnergySupplierDoesNotExistException _e) {
                        System.out.println("Energy supplier does not exist... Skipping");
                    }
                }
                default -> {
                    System.out.printf("Line not expected: '%s'. Skipping...\n", nextLine);
                    nextLine = it.hasNext() ? it.next() : null;
                }
            }
        }

        return smartHousesManager;
    }

    private static String parseSmartHouse(Iterator<String> it, SmartHouse smartHouse) {


        for (String nextLine = it.hasNext() ? it.next() : null;
             nextLine != null;
        ) {
            String[] splitLine = nextLine.split(":", 2);
            if ("Divisao".equals(splitLine[0])) {
                Division div = new Division(splitLine[1]);
                nextLine = parseDivision(it, div);
                smartHouse.addDivision(div);
            } else {
                return nextLine;
            }
        }

        return null;
    }

    private static String parseDivision(Iterator<String> it, Division division) {
        for (String nextLine = it.hasNext() ? it.next() : null;
             nextLine != null;
             nextLine = it.hasNext() ? it.next() : null
        ) {
            String[] splitLine = nextLine.split(":", 2);
            switch (splitLine[0]) {
                case "SmartBulb" -> {
                    String[] bulbInfo = splitLine[1].split(",", 3);
                    SmartBulb bulb = new SmartBulb(
                        Float.parseFloat(bulbInfo[2]),
                        Tone.valueOf(bulbInfo[0].toUpperCase()),
                        Float.parseFloat(bulbInfo[1])
                    );
                    division.addSmartDevice(bulb);
                }
                case "SmartSpeaker" -> {
                    String[] speakerInfo = splitLine[1].split(",", 4);
                    SmartSpeaker speaker = new SmartSpeaker(
                        Float.parseFloat(speakerInfo[3]),
                        Integer.parseInt(speakerInfo[0]),
                        speakerInfo[1],
                        speakerInfo[2]
                    );
                    division.addSmartDevice(speaker);
                }
                case "SmartCamera" -> {
                    String[] cameraInfo = splitLine[1].split(",", 3);
                    Pattern resPattern = Pattern.compile("\\((?<width>\\d+)x(?<height>\\d+)\\)");
                    Matcher resMatcher = resPattern.matcher(cameraInfo[0]);
                    resMatcher.find();
                    SmartCamera camera = new SmartCamera(
                        Float.parseFloat(cameraInfo[2]),
                        Integer.parseInt(resMatcher.group("width")),
                        Integer.parseInt(resMatcher.group("height")),
                        Float.parseFloat(cameraInfo[1])
                    );
                    division.addSmartDevice(camera);
                }
                default -> {
                    return nextLine;
                }
            }
        }

        return null;
    }
}
