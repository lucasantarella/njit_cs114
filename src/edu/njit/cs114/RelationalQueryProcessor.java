package edu.njit.cs114;


import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/14/20
 */
public class RelationalQueryProcessor {

    private static RelationalTable.DataRow[] getRows(RelationalTable t) {
        RelationalTable.DataRow[] rows = new RelationalTable.DataRow[t.size()];
        Iterator<RelationalTable.DataRow> iter = t.getRowIterator();
        int idx = 0;
        while (iter.hasNext()) {
            rows[idx++] = iter.next();
        }
        return rows;
    }

    private String colName(String alias, String col) {
        int idx = col.lastIndexOf(".");
        if (idx >= 0) {
            return alias + "." + col.substring(idx + 1);
        } else {
            return alias + "." + col;
        }
    }

    /**
     * Create an empty table for join
     *
     * @param name   table name
     * @param table1 left table
     * @param table2 right table
     * @param alias1 alias to use for columns of left table
     * @param alias2 alias to use for columns of right table
     * @return
     */
    private RelationalTable createJoinResultTable(String name,
                                                  RelationalTable table1,
                                                  RelationalTable table2,
                                                  String alias1,
                                                  String alias2) {
        List<String> columns = new ArrayList<>();
        for (String col : table1.columns()) {
            columns.add(colName(alias1, col));
        }
        for (String col : table2.columns()) {
            columns.add(colName(alias2, col));
        }
        return new RelationalTable(name, columns.toArray(new String[0]));
    }

    /**
     * Creates a data row merging the combining columns for table1 and table2
     * Used by Join operation
     *
     * @param table1      left table
     * @param table2      right table
     * @param alias1      alias to use for columns of left table
     * @param alias2      alias to use for columns of right table
     * @param mergedTable join table
     * @param row1        row of left table
     * @param row2        row of right table
     * @return
     */
    private RelationalTable.DataRow mergeRows(
            RelationalTable table1,
            RelationalTable table2,
            String alias1,
            String alias2,
            RelationalTable mergedTable,
            RelationalTable.DataRow row1,
            RelationalTable.DataRow row2) {
        RelationalTable.DataRow mergedRow = mergedTable.createEmptyRow();
        for (String col : table1.columns()) {
            mergedRow.setValue(colName(alias1, col), row1.getValue(col));
        }
        for (String col : table2.columns()) {
            mergedRow.setValue(colName(alias2, col), row2.getValue(col));
        }
        return mergedRow;
    }


    /**
     * Joins table1 and table2 using the nested loop join columns specified in joinColumns
     * NOTE : DO not sort rows !!!
     *
     * @param table1          left table (can have many rows with the same value in joinColumns[0] column
     * @param table2          right table (assumed to have unique values in joinColumns[1] column
     * @param alias1          alias to use for left table columns in the result
     * @param alias2          alias to use for right table columns in the result
     * @param joinColumns     joinColumns[0] use for left table and joinColumns[1] for right table
     * @param resultTableName
     * @return
     */
    public RelationalTable nestedLoopJoin(RelationalTable table1, RelationalTable table2,
                                          String alias1, String alias2,
                                          String[] joinColumns, String resultTableName) {
        // Create an empty table for the result
        RelationalTable joinTable = createJoinResultTable(resultTableName, table1,
                table2, alias1, alias2);

        RelationalTable.RowComparator comp =
                new RelationalTable.RowComparator(joinColumns[0], joinColumns[1]);

        Iterator<RelationalTable.DataRow> iter1 = table1.getRowIterator();
        while (iter1.hasNext()) {
            /**
             * Complete code for homework
             * Get row for table 1
             * Get iterator for rows of table2 and loop through the rows of table 2
             */
            RelationalTable.DataRow row1 = iter1.next();

            Iterator<RelationalTable.DataRow> iter2 = table2.getRowIterator();
            while (iter2.hasNext()) {
                RelationalTable.DataRow row2 = iter2.next();
                if (comp.compare(row1, row2) == 0)
                    joinTable.addRow(mergeRows(table1, table2, alias1, alias2, joinTable, row1, row2));
            }
        }
        return joinTable;
    }

    public RelationalTable nestedLoopJoin(RelationalTable table1, RelationalTable table2,
                                          String[] joinColumns, String resultTableName) {
        return nestedLoopJoin(table1, table2, table1.name(), table2.name(), joinColumns,
                resultTableName);
    }

    /**
     * Joins using index join table1 and table2 using the join columns specified in joinColumns
     *
     * @param table1          left table (can have many rows with the same value in joinColumns[0] column
     * @param table2          right table (assumed to have unique values in joinColumns[1] column
     * @param alias1          alias to use for left table columns in the result
     * @param alias2          alias to use for right table columns in the result
     * @param joinColumns     joinColumns[0] use for left table and joinColumns[1] for right table
     * @param resultTableName
     * @return
     */
    public RelationalTable indexJoin(RelationalTable table1, RelationalTable table2,
                                     String alias1, String alias2,
                                     String[] joinColumns, String resultTableName) {
        // Create an empty table for the result
        RelationalTable joinTable = createJoinResultTable(resultTableName, table1,
                table2, alias1, alias2);

        RelationalTable.RowComparator comp =
                new RelationalTable.RowComparator(joinColumns[0], joinColumns[1]);

        Iterator<RelationalTable.DataRow> iter1 = table1.getIndexRowIterator(joinColumns[0]);
        Iterator<RelationalTable.DataRow> iter2 = table2.getIndexRowIterator(joinColumns[1]);
        /**
         * Complete code for the homework assignment
         * Note rows will be retrieved in sorted order (do not sort!!)
         */

        // initialize default values
        RelationalTable.DataRow row1;
        RelationalTable.DataRow row2 = null;
        int compResult = -1;
        while (iter1.hasNext()) {
            row1 = iter1.next();

            // compare the rows and determine if the second needs to be advanced
            while (iter2.hasNext() && compResult < 0) {
                row2 = iter2.next();
                compResult = comp.compare(row1, row2);
            }

            // if they match, add the joined row
            if (compResult == 0) {
                joinTable.addRow(mergeRows(table1, table2, alias1, alias2, joinTable, row1, row2));
            }

            // if they dont, go to the next row
            // compResult > 0
        }

        return joinTable;
    }

    public RelationalTable indexJoin(RelationalTable table1, RelationalTable table2,
                                     String[] joinColumns, String resultTableName) {
        return indexJoin(table1, table2, table1.name(), table2.name(), joinColumns,
                resultTableName);
    }

    /**
     * Joins table1 and table2 using the join columns specified in joinColumns
     *
     * @param table1          left table (can have many rows with the same value in joinColumns[0] column
     * @param table2          right table (assumed to have unique values in joinColumns[1] column
     * @param alias1          alias to use for left table columns in the result
     * @param alias2          alias to use for right table columns in the result
     * @param joinColumns     joinColumns[0] use for left table and joinColumns[1] for right table
     * @param resultTableName
     * @return
     */
    public RelationalTable equiJoin(RelationalTable table1, RelationalTable table2,
                                    String alias1, String alias2,
                                    String[] joinColumns, String resultTableName) {
        RelationalTable.RowComparator comp1 =
                new RelationalTable.RowComparator(joinColumns[0], joinColumns[0]);
        RelationalTable.RowComparator comp2 =
                new RelationalTable.RowComparator(joinColumns[1], joinColumns[1]);
        // sort left table rows according to column joinColumn[0]
        RelationalTable.DataRow[] rows1 = getRows(table1);
        Arrays.sort(rows1, comp1);

        // sort right table rows according to column joinColumn[1]
        RelationalTable.DataRow[] rows2 = getRows(table2);
        Arrays.sort(rows2, comp2);

        // Create an empty table for the result
        RelationalTable joinTable = createJoinResultTable(resultTableName, table1,
                table2, alias1, alias2);
        // merge
        RelationalTable.RowComparator comp3 =
                new RelationalTable.RowComparator(joinColumns[0], joinColumns[1]);

        int i = 0;
        int j = 0;
        while (i < table1.size() && j < table2.size()) {
            RelationalTable.DataRow row1 = rows1[i];
            RelationalTable.DataRow row2 = rows2[j];

            int comp = comp3.compare(row1, row2);
            if (comp < 0)
                i++;
            else if (comp == 0) {
                joinTable.addRow(mergeRows(table1, table2, alias1, alias2, joinTable, row1, row2));
                i++;
            } else {
                j++;
            }
        }

        return joinTable;
    }


    public RelationalTable equiJoin(RelationalTable table1, RelationalTable table2,
                                    String[] joinColumns, String resultTableName) {
        return equiJoin(table1, table2, table1.name(), table2.name(), joinColumns,
                resultTableName);
    }

    private static void insertEmployeeRow(RelationalTable t,
                                          Object[] columnVals) {
        RelationalTable.DataRow row = t.createEmptyRow();
        int idx = 0;
        row.setValue("employeeId", columnVals[idx++]);
        row.setValue("firstName", columnVals[idx++]);
        row.setValue("lastName", columnVals[idx++]);
        row.setValue("deptId", columnVals[idx++]);
        row.setValue("annualSalary", columnVals[idx++]);
        t.addRow(row);
    }

    private static void insertDeptRow(RelationalTable t,
                                      Object[] columnVals) {
        RelationalTable.DataRow row = t.createEmptyRow();
        int idx = 0;
        row.setValue("deptId", columnVals[idx++]);
        row.setValue("deptName", columnVals[idx++]);
        row.setValue("managerId", columnVals[idx++]);
        t.addRow(row);
    }

    public static void main(String[] args) {
        RelationalTable empTable = new RelationalTable("Employee",
                new String[]{"employeeId", "firstName", "lastName",
                        "annualSalary", "deptId"});
        insertEmployeeRow(empTable, new Object[]{
                "5000", "Lowly", "Worker1", 101, 30000});
        insertEmployeeRow(empTable, new Object[]{
                "5001", "Lowly", "Worker2", 102, 50000});
        insertEmployeeRow(empTable, new Object[]{
                "5002", "Lowly", "Worker3", 102, 60000});
        insertEmployeeRow(empTable, new Object[]{
                "5003", "Boss1", "Worker4", 102, 150000});
        insertEmployeeRow(empTable, new Object[]{
                "5004", "Boss2", "Worker5", 101, 200000});
        empTable.print();
        RelationalTable deptTable = new RelationalTable("Department",
                new String[]{"deptId", "deptName", "managerId"});

        insertDeptRow(deptTable, new Object[]{101, "Sales", "5004"});
        insertDeptRow(deptTable, new Object[]{102, "Manufacturing", "5003"});
        deptTable.print();

        RelationalQueryProcessor proc = new RelationalQueryProcessor();

        // join employee table and dept table on the deptId column
        RelationalTable t3 = proc.equiJoin(empTable, deptTable, "t1", "t2",
                new String[]{"deptId", "deptId"}, "Emp-Dept");
        t3.print();
        RelationalTable t4 = t3.project("Emp-Dept1", new String[]{"t1.employeeId",
                "t1.firstName", "t1.lastName", "t1.deptId", "t2.deptName", "t2.managerId"});
        t4.print();

        // join t4 and employee table to get manager information also
        RelationalTable t5 = proc.equiJoin(t4, empTable, "emp", "mgr",
                new String[]{"t2.managerId", "employeeId"}, "t5")
                .project("Emp-Mgr", new String[]{"emp.employeeId",
                        "emp.firstName", "emp.lastName", "emp.deptId", "emp.deptName",
                        "mgr.firstName", "mgr.lastName"});
        t5.print();
    }
}
