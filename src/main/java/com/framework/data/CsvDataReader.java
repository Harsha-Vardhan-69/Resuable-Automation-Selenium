package com.framework.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public final class CsvDataReader {
    private CsvDataReader() {
    }

    public static List<Map<String, String>> read(String resourcePath) throws IOException, CsvException {
        try (InputStream inputStream = TestDataResourceLoader.open(resourcePath);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                CSVReader reader = new CSVReader(streamReader)) {
            List<String[]> allRows = reader.readAll();
            if (allRows.isEmpty())
                return new ArrayList<>();
            String[] headers = allRows.get(0);
            List<Map<String, String>> rows = new ArrayList<>();
            for (int r = 1; r < allRows.size(); r++) {
                String[] cols = allRows.get(r);
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < headers.length && i < cols.length; i++) {
                    map.put(headers[i].trim(), cols[i].trim());
                }
                rows.add(map);
            }
            return rows;
        }
    }
}
