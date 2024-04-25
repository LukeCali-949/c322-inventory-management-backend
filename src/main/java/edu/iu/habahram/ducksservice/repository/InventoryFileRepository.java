package edu.iu.habahram.ducksservice.repository;

import edu.iu.habahram.ducksservice.model.Builder;
import edu.iu.habahram.ducksservice.model.Guitar;
import edu.iu.habahram.ducksservice.model.Type;
import edu.iu.habahram.ducksservice.model.Wood;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


@Component
public class InventoryFileRepository {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String DATABASE_NAME = "guitars_database.txt";
    private List<Guitar> guitars = new ArrayList<>();

    public InventoryFileRepository() {
        clearDatabaseFile();
    }

    private void clearDatabaseFile() {
        Path path = Paths.get(DATABASE_NAME);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendToFile(Path path, String content) throws IOException {
        Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public boolean addGuitar(Guitar guitarData) throws IOException {
        Path path = Paths.get(DATABASE_NAME);
        String data = guitarData.getSerialNumber() + "," + guitarData.getPrice() + "," + guitarData.getBuilder() + "," + guitarData.getModel() + "," + guitarData.getType().toString() + "," + guitarData.getBackWood() + "," + guitarData.getTopWood();
        System.out.println(data);
        appendToFile(path, data + NEW_LINE);

        guitars.add(guitarData);

        return true;
    }


    public Guitar getGuitar(String serialNumber) throws IOException {
        Path path = Paths.get(DATABASE_NAME);

        // Use BufferedReader to read the file line by line. Easier to work with in my opinion.
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");

                if (words.length < 2) {
                    continue;
                }

                if (words[0].equals(serialNumber)) {
                    double price = Double.parseDouble(words[1]);
                    Builder builderEnum = Builder.valueOf(words[2].toUpperCase());
                    String model = words[3];
                    Type typeEnum = Type.valueOf(words[4].toUpperCase());
                    Wood backWoodEnum = Wood.valueOf(words[5].toUpperCase());
                    Wood topWoodEnum = Wood.valueOf(words[6].toUpperCase());

                    return new Guitar(serialNumber, price, builderEnum, model, typeEnum, backWoodEnum, topWoodEnum);
                }
            }
        }

        return null;
    }

    public List<Guitar> search(Guitar searchGuitar) {
        System.out.println("ran here too");
        List<Guitar> matchingGuitars = new ArrayList<>();


        for (Guitar guitar : guitars) {
            System.out.println(guitar);
            System.out.println("----------");
            System.out.println(searchGuitar);

            if (searchGuitar.getBuilder() != guitar.getBuilder())
                continue;

            if (searchGuitar.getType() != guitar.getType())
                continue;

            if (searchGuitar.getBackWood() != guitar.getBackWood())
                continue;

            if (searchGuitar.getTopWood() != guitar.getTopWood())
                continue;

            matchingGuitars.add(guitar);
        }

        return matchingGuitars;
    }




}

