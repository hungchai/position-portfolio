package com.option.portfolio.portfolio.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
@NoArgsConstructor
@Getter
@Slf4j
public class PositionCsvReader {
    @Value("${position-csv.path}")
    private String positionCsvPath;

    @PostConstruct
    public void init() {
        log.info("positioncsv path " + positionCsvPath);
        readFile();

    }

    public void readFile()
    {
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

                // Process the data as needed
                System.out.println("Symbol: " + symbol + ", Position Size: " + positionSize);
            }

            // Close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
