package edu.njit.cs114;

import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/14/20
 */
public class RelationalTable {

    private List<DataRow> rows = new ArrayList<>();

    private final String[] columns;
    private final String name;

    // row of the table
    public class DataRow {

        private Map<String, Object> data = new HashMap<>();

        public Object setValue(String column, Object val) {
            if (!isAColumn(column)) {
                throw new IllegalArgumentException("Invalid column " + column);
            }
            return data.put(column, val);
        }

        public Object getValue(String column) {
            if (!isAColumn(column)) {
                throw new IllegalArgumentException("Invalid column " + column);
            }
            return data.get(column);
        }

        /**
         * Gets a subset of columns of the row add then to given row
         *
         * @param row
         * @param columns
         */
        public void project(DataRow row, String[] columns) {
            for (String col : columns) {
                if (!isAColumn(col)) {
                    throw new IllegalArgumentException("Invalid column " + col);
                }
                row.setValue(col, data.get(col));
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof DataRow)) {
                return false;
            }
            DataRow other = (DataRow) obj;
            if (data.size() != other.data.size()) {
                return false;
            }
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String col = entry.getKey();
                Object val = entry.getValue();
                Object val1 = other.getValue(col);
                if (val1 == null || !val.equals(val1)) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (String col : columns) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(col + ": ");
                builder.append(data.get(col));
            }
            return builder.toString();
        }
    }


    public class RowIterator implements Iterator<DataRow> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < rows.size();
        }

        @Override
        public DataRow next() {
            return rows.get(index++);
        }
    }

    public static class RowComparator implements Comparator<DataRow> {

        private final String column1;
        private final String column2;

        public RowComparator(String column1, String column2) {
            this.column1 = column1;
            this.column2 = column2;
        }

        @Override
        public int compare(DataRow row1, DataRow row2) {
            Object val1 = row1.getValue(column1);
            Object val2 = row2.getValue(column2);
            if (val1 == null) {
                if (val2 == null) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (val2 == null) {
                    return -1;
                } else {
                    int r = ((Comparable) val1).compareTo((Comparable) val2);
                    return r;
                }
            }
        }
    }

    public RelationalTable(String name, String[] columns) {
        this.name = name;
        this.columns = Arrays.copyOf(columns, columns.length);
    }

    public DataRow createEmptyRow() {
        return new DataRow();
    }

    /**
     * Add a row to the table
     *
     * @param row
     */
    public void addRow(DataRow row) {
        rows.add(row);
    }


    public Iterator<DataRow> getRowIterator() {
        return new RowIterator();
    }

    public String[] columns() {
        return Arrays.copyOf(this.columns, this.columns.length);
    }

    public boolean isAColumn(String column) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals(column)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return rows.size();
    }

    public String name() {
        return this.name;
    }

    /**
     * Gets subset of columns of  the rows
     *
     * @param name
     * @param columns
     * @return
     */
    public RelationalTable project(String name, String[] columns) {
        RelationalTable result = new RelationalTable(name, columns);
        for (DataRow row : rows) {
            DataRow newRow = result.createEmptyRow();
            row.project(newRow, columns);
            result.addRow(newRow);
        }
        return result;
    }

    public void print() {
        System.out.println("Table: " + name);
        for (DataRow row : rows) {
            System.out.println(row.toString());
        }
    }

}
