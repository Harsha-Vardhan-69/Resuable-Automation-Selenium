package com.framework.data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ExcelDataReader {
    private static final ConcurrentHashMap<String, List<Map<String, String>>> CACHE = new ConcurrentHashMap<>();

    private ExcelDataReader() {
    }

    public static List<Map<String, String>> read(String resourcePath, String sheetName) throws IOException {
        String normalizedPath = TestDataResourceLoader.normalize(resourcePath);
        String cacheKey = normalizedPath + "|" + String.valueOf(sheetName);
        List<Map<String, String>> cached = CACHE.get(cacheKey);
        if (cached != null) {
            return deepCopy(cached);
        }

        try (InputStream inputStream = TestDataResourceLoader.open(resourcePath);
                Workbook wb = new XSSFWorkbook(inputStream)) {
            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null)
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in " + normalizedPath);
            Iterator<Row> rows = sheet.rowIterator();
            if (!rows.hasNext())
                return new ArrayList<>();
            Row header = rows.next();

            // IMPORTANT: do not iterate header cells directly.
            // Apache POI cell-iterators skip missing/blank cells, which breaks alignment
            // when there are gaps in the header row (common when users leave an empty
            // column).
            // We must preserve the real column indexes.
            short lastCellNum = header.getLastCellNum();
            if (lastCellNum < 0) {
                return new ArrayList<>();
            }

            DataFormatter formatter = new DataFormatter();
            List<String> headers = new ArrayList<>(lastCellNum);
            for (int col = 0; col < lastCellNum; col++) {
                Cell hc = header.getCell(col);
                String name = hc == null ? "" : formatter.formatCellValue(hc);
                if (name == null) {
                    name = "";
                }
                name = name.trim();
                if (!name.isEmpty() && (name.charAt(0) == '`' || name.charAt(0) == '\'')) {
                    name = name.substring(1).trim();
                }
                if (name.isEmpty()) {
                    name = "COL_" + col;
                }
                headers.add(name);
            }
            List<Map<String, String>> out = new ArrayList<>();
            while (rows.hasNext()) {
                Row r = rows.next();
                Map<String, String> map = new HashMap<>();
                for (int col = 0; col < headers.size(); col++) {
                    Cell cell = r.getCell(col);
                    String val = cell == null ? "" : getCellString(cell);
                    map.put(headers.get(col), val);
                }
                out.add(map);
            }
            List<Map<String, String>> cacheValue = deepCopy(out);
            CACHE.putIfAbsent(cacheKey, cacheValue);
            return deepCopy(CACHE.get(cacheKey));
        }
    }

    public static void clearCache() {
        CACHE.clear();
    }

    private static List<Map<String, String>> deepCopy(List<Map<String, String>> rows) {
        List<Map<String, String>> copy = new ArrayList<>();
        for (Map<String, String> row : rows) {
            copy.add(new HashMap<>(row));
        }
        return copy;
    }

    private static String getCellString(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        String raw = formatter.formatCellValue(cell);
        if (raw == null)
            return "";
        String v = raw.trim();
        // Users sometimes prefix values with ` or ' in Excel to force text.
        if (!v.isEmpty() && (v.charAt(0) == '`' || v.charAt(0) == '\'')) {
            v = v.substring(1).trim();
        }
        return v;
    }
}
