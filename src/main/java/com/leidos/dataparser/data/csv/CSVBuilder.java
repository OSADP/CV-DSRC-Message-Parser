package com.leidos.dataparser.data.csv;

import com.leidos.dataparser.io.formatting.Formatter;
import com.leidos.dataparser.io.formatting.OutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Acts as a string builder for CSV rows
 */
public class CSVBuilder implements Formatter {
    private List<String> headers = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();

    private static final String EMPTY_CELL_VALUE = "";
    private static final String CELL_DELIMITER = ",";
    private static final String ROW_DELIMITER = "\n";

    private int nestingCount = 0;
    private int currentRow = 0;

    public CSVBuilder() {
        // No context at start
    }

    @Override
    public String toString() {
        normalizeRows();
        return headers.stream().collect(Collectors.joining(CELL_DELIMITER))
                + ROW_DELIMITER
                + rows.stream()
                .map(l -> l.stream().collect(Collectors.joining(CELL_DELIMITER)))
                .collect(Collectors.joining(ROW_DELIMITER));
    }

    @Override
    public void formatString(String name, String s) {
        getRow(currentRow).set(getColumn(name), s);
    }

    @Override
    public void formatInt(String name, int i) {
        getRow(currentRow).set(getColumn(name), Integer.toString(i));
    }

    @Override
    public void formatDouble(String name, double d) {
        getRow(currentRow).set(getColumn(name), Double.toString(d));
    }

    @Override
    public void formatLong(String name, long l) {
        getRow(currentRow).set(getColumn(name), Long.toString(l));
    }

    @Override
    public void formatByte(String name, byte b) {
        getRow(currentRow).set(getColumn(name), Byte.toString(b));
    }

    @Override
    public void formatBoolean(String name, boolean b) {
        getRow(currentRow).set(getColumn(name), Boolean.toString(b));
    }

    @Override
    public void formatShort(String name, short s) {
        getRow(currentRow).set(getColumn(name), Short.toString(s));
    }

    @Override
    public void formatChar(String name, char c) {
        getRow(currentRow).set(getColumn(name), Character.toString(c));
    }

    @Override
    public void formatFloat(String name, float f) {
        getRow(currentRow).set(getColumn(name), Float.toString(f));
    }

    @Override
    public void formatOutputData(String name, OutputData p) {
        if (p == null) {
            return;
        }

        nestingCount++;

        p.getParsedValues().stream().forEach(field -> {
            try {
                dispatch(name + "." + field.getName(), field.get(p));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        nestingCount--;
        if (nestingCount == 0) {
            currentRow++;
        }
    }

    @Override
    public String getFormattedData() {
        return toString();
    }

    /**
     * Check if a row exists, if it does, return it. If it doesn't create it and return it.
     * @param i The index of the row
     * @return A row with that index number
     */
    private List<String> getRow(int i) {
        try {
            return rows.get(i);
        } catch (IndexOutOfBoundsException iobe) {
            rows.add(i, new ArrayList<>());
            return rows.get(i);
        }
    }

    private int getColumn(String col) {
        if (!headers.contains(col)) {
            headers.add(col);

        }
        while (rows.get(currentRow).size() < headers.size()) {
            rows.get(currentRow).add(EMPTY_CELL_VALUE);
        }
        return headers.indexOf(col);
    }

    /**
     * Normalize the length of all rows to the length of the longest row
     */
    private void normalizeRows() {
        Optional<List<String>> maxLen = rows.stream().max((r1, r2) -> r1.size() - r2.size());

        if (maxLen.isPresent()) {
            rows.forEach(r -> {
                while (r.size() < maxLen.get().size()) {
                    r.add(EMPTY_CELL_VALUE);
                }
            });
        }
    }
}
