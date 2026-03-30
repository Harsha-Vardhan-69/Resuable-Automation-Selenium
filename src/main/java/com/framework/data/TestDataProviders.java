package com.framework.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TestDataProviders {
    private static final Logger LOGGER = LogManager.getLogger(TestDataProviders.class);
    private static final String TESTDATA_ROOT = "testdata/";
    private static final String EXCEL_RESOURCE = TESTDATA_ROOT + "testdata.xlsx";

    private TestDataProviders() {
    }

    @DataProvider(name = "csv-provider")
    public static Object[][] csvProvider(java.lang.reflect.Method m) throws IOException, CsvException {
        String fileName = m.getName() + ".csv";
        String resourcePath = TestDataResourceLoader.exists(TESTDATA_ROOT + fileName)
                ? TESTDATA_ROOT + fileName
                : fileName;
        List<Map<String, String>> rows = CsvDataReader.read(resourcePath);
        Object[][] out = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++)
            out[i] = new Object[] { rows.get(i) };
        return out;
    }

    /**
     * Flexible DataProvider: specify sheet names as parameters in @Test annotation.
     * Usage: @Test(dataProvider = "multi-sheet-provider", dataProviderClass =
     * TestDataProviders.class, dataProviderArguments = {"Login", "NewAgent"})
     * public void test(Map<String, String> login, Map<String, String> newAgent) {
     * ... }
     */
    @DataProvider(name = "multi-sheet-provider")
    public static Object[][] multiSheetProvider(java.lang.reflect.Method method) throws IOException {
        com.framework.data.SheetNames sheetNamesAnnotation = method.getAnnotation(com.framework.data.SheetNames.class);
        String[] sheets;
        if (sheetNamesAnnotation != null && sheetNamesAnnotation.value().length > 0) {
            sheets = sheetNamesAnnotation.value();
        } else {
            sheets = new String[] { "Login", "AgentSearch", "NewAgent", "ConvertAgent", "Agencyinformation",
                    "Companies", "Comments", "RecogRollupTerr", "SupportServices", "AlpsSegment", "ConnectionAuto",
                    "PersRoleTerr", "PersPosComb", "PersRoleGlobal", "PosAuthority" };
        }
        LOGGER.debug("[multiSheetProvider] Using sheets: {}", Arrays.toString(sheets));

        // Determine test case prefix from class name (e.g., TC00 from TC00_LoginTest)
        String testClassName = method.getDeclaringClass().getSimpleName();
        String testCasePrefix = testClassName.split("_")[0];
        LOGGER.debug("[multiSheetProvider] Filtering for TestCase='{}'", testCasePrefix);

        // Optional: further filter rows by running @Test method name.
        // If the Excel sheet does not have a 'Method' column (or it's blank), rows are
        // treated as shared.
        String runningMethodName = method.getName();
        LOGGER.debug("[multiSheetProvider] Running method='{}'", runningMethodName);

        java.util.List<java.util.List<Map<String, String>>> filteredSheetRows = new java.util.ArrayList<>();
        for (String sheet : sheets) {
            java.util.List<java.util.Map<String, String>> allRows = ExcelDataReader.read(EXCEL_RESOURCE, sheet);
            java.util.List<java.util.Map<String, String>> filtered = new java.util.ArrayList<>();
            for (java.util.Map<String, String> row : allRows) {
                if (!testCasePrefix.equalsIgnoreCase(row.getOrDefault("TestCase", "").trim())) {
                    continue;
                }
                String methodFilter = row.getOrDefault("Method", "").trim();
                boolean sharedRow = methodFilter.isEmpty();
                boolean matchesMethod = sharedRow || runningMethodName.equalsIgnoreCase(methodFilter);
                if (matchesMethod) {
                    filtered.add(row);
                }
            }
            filteredSheetRows.add(filtered);
        }

        return buildMultiSheetData(sheets, filteredSheetRows, testCasePrefix, runningMethodName);
    }

    static Object[][] buildMultiSheetData(String[] sheets, List<List<Map<String, String>>> filteredSheetRows,
            String testCasePrefix, String runningMethodName) {
        if (sheets == null || filteredSheetRows == null || sheets.length != filteredSheetRows.size()) {
            throw new IllegalArgumentException("Sheet metadata and filtered rows must align.");
        }
        if (sheets.length == 0) {
            return new Object[0][];
        }

        // Optional: If all sheets contain a non-empty 'DataSet' column after filtering,
        // join rows by DataSet key.
        // This avoids relying on row index alignment across sheets.
        boolean canJoinByDataSet = true;
        for (List<Map<String, String>> sheetRows : filteredSheetRows) {
            boolean hasAnyKey = false;
            for (Map<String, String> row : sheetRows) {
                if (!row.getOrDefault("DataSet", "").trim().isEmpty()) {
                    hasAnyKey = true;
                    break;
                }
            }
            if (!hasAnyKey) {
                canJoinByDataSet = false;
                break;
            }
        }

        if (canJoinByDataSet) {
            List<String> orderedKeys = new ArrayList<>();
            List<Map<String, Map<String, String>>> keyed = new ArrayList<>();

            for (int s = 0; s < sheets.length; s++) {
                Map<String, Map<String, String>> m = new LinkedHashMap<>();
                for (Map<String, String> row : filteredSheetRows.get(s)) {
                    String k = row.getOrDefault("DataSet", "").trim();
                    if (k.isEmpty()) {
                        continue;
                    }
                    if (s == 0 && !m.containsKey(k)) {
                        orderedKeys.add(k);
                    }
                    if (m.containsKey(k)) {
                        throw invalidMultiSheetData("Duplicate DataSet key '" + k + "' found in sheet '"
                                + sheets[s] + "'.", sheets, filteredSheetRows, testCasePrefix, runningMethodName);
                    }
                    m.put(k, row);
                }
                keyed.add(m);
            }

            validateMatchingDataSetKeys(sheets, keyed, filteredSheetRows, testCasePrefix, runningMethodName);

            List<Object[]> outList = new ArrayList<>();
            for (String k : orderedKeys) {
                Object[] rowData = new Object[sheets.length];
                for (int j = 0; j < sheets.length; j++) {
                    rowData[j] = keyed.get(j).get(k);
                }
                outList.add(rowData);
            }

            Object[][] out = new Object[outList.size()][];
            for (int i = 0; i < outList.size(); i++) {
                out[i] = outList.get(i);
            }
            return out;
        }

        int expectedRows = requireConsistentRowCount(sheets, filteredSheetRows, testCasePrefix, runningMethodName);
        Object[][] out = new Object[expectedRows][];
        for (int i = 0; i < expectedRows; i++) {
            Object[] rowData = new Object[sheets.length];
            for (int j = 0; j < sheets.length; j++) {
                rowData[j] = filteredSheetRows.get(j).get(i);
            }
            out[i] = rowData;
        }
        return out;
    }

    @DataProvider(name = "excel-provider")
    public static Object[][] excelDataProvider(String sheetName) throws IOException {
        List<Map<String, String>> rows = ExcelDataReader.read(EXCEL_RESOURCE, sheetName);
        Object[][] out = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++)
            out[i] = new Object[] { rows.get(i) };
        return out;
    }

    private static int requireConsistentRowCount(String[] sheets, List<List<Map<String, String>>> filteredSheetRows,
            String testCasePrefix, String runningMethodName) {
        int expectedRows = filteredSheetRows.get(0).size();
        for (int index = 1; index < filteredSheetRows.size(); index++) {
            int actualRows = filteredSheetRows.get(index).size();
            if (actualRows != expectedRows) {
                throw invalidMultiSheetData(
                        "Mismatched filtered row counts between sheets '" + sheets[0] + "' and '" + sheets[index]
                                + "' (" + expectedRows + " vs " + actualRows + ").",
                        sheets, filteredSheetRows, testCasePrefix, runningMethodName);
            }
        }
        return expectedRows;
    }

    private static void validateMatchingDataSetKeys(String[] sheets, List<Map<String, Map<String, String>>> keyed,
            List<List<Map<String, String>>> filteredSheetRows, String testCasePrefix, String runningMethodName) {
        Set<String> expectedKeys = new LinkedHashSet<>(keyed.get(0).keySet());
        for (int index = 1; index < keyed.size(); index++) {
            Set<String> actualKeys = new LinkedHashSet<>(keyed.get(index).keySet());
            if (!expectedKeys.equals(actualKeys)) {
                Set<String> missingKeys = new LinkedHashSet<>(expectedKeys);
                missingKeys.removeAll(actualKeys);

                Set<String> unexpectedKeys = new LinkedHashSet<>(actualKeys);
                unexpectedKeys.removeAll(expectedKeys);

                StringBuilder message = new StringBuilder("Mismatched DataSet keys between sheets '")
                        .append(sheets[0]).append("' and '").append(sheets[index]).append("'.");
                if (!missingKeys.isEmpty()) {
                    message.append(" Missing in '").append(sheets[index]).append("': ").append(missingKeys).append('.');
                }
                if (!unexpectedKeys.isEmpty()) {
                    message.append(" Extra in '").append(sheets[index]).append("': ").append(unexpectedKeys)
                            .append('.');
                }
                throw invalidMultiSheetData(message.toString(), sheets, filteredSheetRows, testCasePrefix,
                        runningMethodName);
            }
        }
    }

    private static IllegalStateException invalidMultiSheetData(String detail, String[] sheets,
            List<List<Map<String, String>>> filteredSheetRows, String testCasePrefix, String runningMethodName) {
        StringBuilder counts = new StringBuilder();
        for (int index = 0; index < sheets.length; index++) {
            if (index > 0) {
                counts.append(", ");
            }
            counts.append(sheets[index]).append('=').append(filteredSheetRows.get(index).size());
        }
        return new IllegalStateException(detail + " TestCase='" + testCasePrefix + "', method='"
                + runningMethodName + "', sheetRowCounts={" + counts + "}.");
    }

}
