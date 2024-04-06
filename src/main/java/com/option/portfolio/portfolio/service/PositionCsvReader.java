package com.option.portfolio.portfolio.service;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class PositionCsvReader {
    @Value("${position-csv.path}")
    private String positionCsvPath;
    Logger log = LoggerFactory.getLogger(PositionCsvReader.class);

    @PostConstruct
    public void init() {
        log.info("positioncsv path " + positionCsvPath);
        readSampleFile();
    }

    public Map<String, Pair<Integer, Double>> readSampleFile() {
        Map<String, Pair<Integer, Double>> result = new HashMap<>();
        try {
            // Create a File object representing the CSV file
            File file = new File(positionCsvPath);

            // Create a Scanner to read from the CSV file
            Scanner scanner = new Scanner(file);

            // Skip the header line
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip the header line
            }

            // Read the data line by line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Extract symbol and positionSize from each line
                String symbol = parts[0];
                int positionSize = Integer.parseInt(parts[1]);

                Double stockPrice = parts.length == 3? Double.parseDouble(parts[2]) : -1;

                // Process the data as needed
                System.out.println("Symbol: " + symbol + ", Position Size: " + positionSize+" stockPrice "+stockPrice);
                result.put(symbol, Pair.of(positionSize, stockPrice));
            }

            // Close the scanner
            scanner.close();
            return result;
        } catch (Exception e) {
            log.error("positioncsv error ", e);
            return result;
        }
    }
}
