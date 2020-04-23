package edu.njit.cs114.tests;

import edu.njit.cs114.RelationalQueryProcessor;
import edu.njit.cs114.RelationalTable;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/20/20
 */
public class RelationalQueryProcessorTests extends UnitTests {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static class MultiColComparator  implements Comparator<RelationalTable.DataRow> {

        private final String col1, col2;

        public MultiColComparator(String col1, String col2) {
            this.col1 = col1;
            this.col2 = col2;
        }

        @Override
        public int compare(RelationalTable.DataRow row1, RelationalTable.DataRow row2) {
            Object val1 = row1.getValue(col1) + "," + row1.getValue(col2);
            Object val2 = row2.getValue(col1) + "," + row2.getValue(col2);
            return((Comparable) val1).compareTo((Comparable) val2);
        }
    }

    private static void insertStudentRow(RelationalTable t,
                                          Object [] columnVals) {
        RelationalTable.DataRow row = t.createEmptyRow();
        int idx = 0;
        row.setValue("studentId", columnVals[idx++]);
        row.setValue("firstName", columnVals[idx++]);
        row.setValue("lastName", columnVals[idx++]);
        row.setValue("enrolledDate", columnVals[idx++]);
        t.addRow(row);
    }

    private static void insertStudentCourseRow(RelationalTable t,
                                         Object [] columnVals) {
        RelationalTable.DataRow row = t.createEmptyRow();
        int idx = 0;
        row.setValue("studentId", columnVals[idx++]);
        row.setValue("course", columnVals[idx++]);
        row.setValue("year", columnVals[idx++]);
        row.setValue("semester", columnVals[idx++]);
        row.setValue("grade", columnVals[idx++]);
        t.addRow(row);
    }

    private static RelationalTable.DataRow[] getRows(RelationalTable t, String col1, String col2) {
        RelationalTable.DataRow[] rows = new RelationalTable.DataRow[t.size()];
        Iterator<RelationalTable.DataRow> iter = t.getRowIterator();
        int idx = 0;
        while (iter.hasNext()) {
            rows[idx++] = iter.next();
        }
        Arrays.sort(rows, new MultiColComparator(col1, col2));
        return rows;
    }

    private static Map<Object,RelationalTable.DataRow> getRowMap(RelationalTable t,
                                                                   String col) {
        Map<Object,RelationalTable.DataRow> map = new HashMap();
        RelationalTable.DataRow[] rows = new RelationalTable.DataRow[t.size()];
        Iterator<RelationalTable.DataRow> iter = t.getRowIterator();
        int idx = 0;
        while (iter.hasNext()) {
            RelationalTable.DataRow row = iter.next();
            map.put(row.getValue(col), row);
        }
        return map;
    }

    private boolean verifyJoin(RelationalTable jt, RelationalTable lt,  RelationalTable rt,
                               String lAlias, String rAlias, String lCol1, String lCol2,
                               String rCol) {
        Iterator<RelationalTable.DataRow> iter = jt.getRowIterator();
        String [] lCols = lt.columns();
        String [] rCols = rt.columns();
        RelationalTable.DataRow[] rows1 = getRows(lt, lCol1, lCol2);
        Map<Object,RelationalTable.DataRow> rowMap = getRowMap(rt, rCol);
        RelationalTable.DataRow[] jRows = getRows(jt, lAlias+"."+lCol1, lAlias+"."+lCol2);
        int idx = 0;
        for (RelationalTable.DataRow row : jRows) {
            for (String col : lCols) {
                if (!row.getValue(lAlias+"."+col).equals(rows1[idx].getValue(col))) {
                    return false;
                }
            }
            RelationalTable.DataRow row2 = rowMap.get(row.getValue(rAlias+"."+rCol));
            for (String col : rCols) {
                if (!row.getValue(rAlias+"."+col).equals(row2.getValue(col))) {
                    return false;
                }
            }
            idx++;
        }
        return true;
    }

    @Test
    public void equiJoinTest() throws Exception {
        try {
            RelationalTable.RowComparator.reset();
            RelationalTable st = new RelationalTable("Student",
                    new String[]{"studentId", "firstName", "lastName",
                            "enrolledDate"});
            insertStudentRow(st, new Object [] {"abc12", "John", "Doe", dateFormat.parse("2017-09-15")});
            insertStudentRow(st, new Object [] {"def23", "Jane", "Doe", dateFormat.parse("2018-12-10")});
            insertStudentRow(st, new Object [] {"raj12", "Ram", "Rajan", dateFormat.parse("2019-01-15")});
            insertStudentRow(st, new Object [] {"ema12", "Eva", "Ma", dateFormat.parse("2019-01-15")});
            insertStudentRow(st, new Object [] {"aga12", "Andy", "Garcia", dateFormat.parse("2018-01-15")});
            RelationalTable ct = new RelationalTable("CourseEnrollment",
                    new String[]{"studentId", "course", "year","semester", "grade"});
            insertStudentCourseRow(ct, new Object []  {"abc12", "CS100", 2017, "Fall", "B"} );
            insertStudentCourseRow(ct, new Object []  {"abc12", "CS114", 2019, "Fall", "B+"} );
            insertStudentCourseRow(ct, new Object []  {"ema12", "CS114", 2019, "Spring", "A"} );
            insertStudentCourseRow(ct, new Object []  {"ema12", "CS341", 2019, "Fall", "A"} );
            insertStudentCourseRow(ct, new Object []  {"raj12", "CS341", 2019, "Fall", "A"} );
            insertStudentCourseRow(ct, new Object []  {"aga12", "CS114", 2019, "Fall", "B+"} );
            insertStudentCourseRow(ct, new Object []  {"aga12", "CS240", 2019, "Fall", "A-"} );
            RelationalQueryProcessor proc = new RelationalQueryProcessor();
            RelationalTable result = proc.equiJoin(ct,st, "ct", "st", new String [] {"studentId", "studentId"}, "student-course");
            assertEquals(7, result.size());
            totalScore += 5;
            assertEquals(true,verifyJoin(result, ct, st, "ct", "st", "studentId", "course", "studentId"));
            totalScore += 10;
            assertEquals(true, RelationalTable.RowComparator.nComps() <= 35);
            totalScore += 10;
            success("equiJoinTest1");
        } catch (Exception e) {
            failure("equiJoinTest1", e);
        }
    }

    @Test
    public void nestedLoopJoinTest() throws Exception {
        try {
            RelationalTable.RowComparator.reset();
            RelationalTable st = new RelationalTable("Student",
                    new String[]{"studentId", "firstName", "lastName",
                            "enrolledDate"});
            insertStudentRow(st, new Object [] {"abc12", "John", "Doe", dateFormat.parse("2017-09-15")});
            insertStudentRow(st, new Object [] {"def23", "Jane", "Doe", dateFormat.parse("2018-12-10")});
            insertStudentRow(st, new Object [] {"raj12", "Ram", "Rajan", dateFormat.parse("2019-01-15")});
            insertStudentRow(st, new Object [] {"ema12", "Eva", "Ma", dateFormat.parse("2019-01-15")});
            insertStudentRow(st, new Object [] {"aga12", "Andy", "Garcia", dateFormat.parse("2018-01-15")});
            RelationalTable ct = new RelationalTable("CourseEnrollment",
                    new String[]{"studentId", "course", "year","semester", "grade"});
            insertStudentCourseRow(ct, new Object []  {"abc12", "CS100", 2017, "Fall", "B"} );
            insertStudentCourseRow(ct, new Object []  {"abc12", "CS114", 2019, "Fall", "B+"} );
            insertStudentCourseRow(ct, new Object []  {"ema12", "CS114", 2019, "Spring", "A"} );
            insertStudentCourseRow(ct, new Object []  {"ema12", "CS341", 2019, "Fall", "A"} );
            insertStudentCourseRow(ct, new Object []  {"raj12", "CS341", 2019, "Fall", "A"} );
            insertStudentCourseRow(ct, new Object []  {"aga12", "CS114", 2019, "Fall", "B+"} );
            insertStudentCourseRow(ct, new Object []  {"aga12", "CS240", 2019, "Fall", "A-"} );
            RelationalQueryProcessor proc = new RelationalQueryProcessor();
            RelationalTable result = proc.nestedLoopJoin(ct,st, "ct", "st", new String [] {"studentId", "studentId"}, "student-course");
            assertEquals(7, result.size());
            totalScore += 5;
            assertEquals(true,verifyJoin(result, ct, st, "ct", "st", "studentId", "course", "studentId"));
            totalScore += 10;
            //System.out.println("number of comparisons = " + RelationalTable.RowComparator.nComps());
            assertEquals(true, RelationalTable.RowComparator.nComps() <= 40);
            totalScore += 10;
            success("nestedLoopJoinTest");
        } catch (Exception e) {
            failure("nestedLoopJoinTest", e);
        }
    }

    @Test
    public void indexJoinTest() throws Exception {
        try {
            RelationalTable st = new RelationalTable("Student",
                    new String[]{"studentId", "firstName", "lastName",
                            "enrolledDate"});
            st.addIndex("studentId");
            insertStudentRow(st, new Object [] {"abc12", "John", "Doe", dateFormat.parse("2017-09-15")});
            insertStudentRow(st, new Object [] {"def23", "Jane", "Doe", dateFormat.parse("2018-12-10")});
            insertStudentRow(st, new Object [] {"raj12", "Ram", "Raj", dateFormat.parse("2019-01-15")});
            insertStudentRow(st, new Object [] {"ema12", "Eva", "Ma", dateFormat.parse("2019-01-15")});
            insertStudentRow(st, new Object [] {"aga12", "Andy", "Garcia", dateFormat.parse("2018-01-15")});
            RelationalTable ct = new RelationalTable("CourseEnrollment",
                    new String[]{"studentId", "course", "year","semester", "grade"});
            ct.addIndex("studentId");
            insertStudentCourseRow(ct, new Object []  {"abc12", "CS100", 2017, "Fall", "B"} );
            insertStudentCourseRow(ct, new Object []  {"abc12", "CS114", 2019, "Fall", "B+"} );
            insertStudentCourseRow(ct, new Object []  {"ema12", "CS114", 2019, "Spring", "A"} );
            insertStudentCourseRow(ct, new Object []  {"ema12", "CS341", 2019, "Fall", "A"} );
            insertStudentCourseRow(ct, new Object []  {"raj12", "CS341", 2019, "Fall", "A"} );
            insertStudentCourseRow(ct, new Object []  {"aga12", "CS114", 2019, "Fall", "B+"} );
            insertStudentCourseRow(ct, new Object []  {"aga12", "CS240", 2019, "Fall", "A-"} );
            RelationalTable.RowComparator.reset();
            RelationalQueryProcessor proc = new RelationalQueryProcessor();
            RelationalTable result = proc.indexJoin(ct,st, "ct", "st", new String [] {"studentId", "studentId"}, "student-course");
            assertEquals(7, result.size());
            totalScore += 5;
            assertEquals(true,verifyJoin(result, ct, st, "ct", "st", "studentId", "course", "studentId"));
            totalScore += 10;
            //System.out.println("number of comparisons = " + RelationalTable.RowComparator.nComps());
            assertEquals(true, RelationalTable.RowComparator.nComps() <= 15);
            totalScore += 10;
            success("indexJoinTest");
        } catch (Exception e) {
            failure("indexJoinTest", e);
        }
    }

    @Test
    public void performanceTest() {
        try {
            RelationalTable.RowComparator.reset();
            RelationalTable st = new RelationalTable("Student",
                    new String[]{"studentId", "firstName", "lastName",
                            "enrolledDate"});
            st.addIndex("studentId");
            RelationalTable ct = new RelationalTable("CourseEnrollment",
                    new String[]{"studentId", "course", "year","semester", "grade"});
            ct.addIndex("studentId");
            int nStudents = 1000;
            int nCourses = 20;
            Set<String> ids = new HashSet<>();
            Random random = new Random();
            for (int i = 1; i <= nStudents; i++) {
                String studentId = "abc" + random.nextInt(nStudents);
                while (ids.contains(studentId)) {
                    studentId = "abc" + random.nextInt(nStudents);
                }
                ids.add(studentId);
                insertStudentRow(st, new Object []
                        {studentId, studentId, "Doe", dateFormat.parse("2017-09-15")});
                for (int j = 1; j <= nCourses; j++) {
                    insertStudentCourseRow(ct, new Object []  {studentId, "CS" + 100 +i , 2017, "Fall", "A"} );
                }
            }
            RelationalQueryProcessor proc = new RelationalQueryProcessor();

            RelationalTable.RowComparator.reset();
            RelationalTable t3 = proc.indexJoin(ct, st, "t1", "t2",
                    new String[]{"studentId", "studentId"}, "Student-course");
            //System.out.println("Size of Student-course with index join = " + t3.size());
            //System.out.println("Number of comparisons made with index join = " + RelationalTable.RowComparator.nComps());
            assertEquals(true, RelationalTable.RowComparator.nComps() <= 21500);
            totalScore += 5;
            RelationalTable.RowComparator.reset();
            t3 = proc.equiJoin(ct, st, "t1", "t2",
                    new String[]{"studentId", "studentId"}, "Student-course");
            //System.out.println("Size of Student-course with index join = " + t3.size());
            //System.out.println("Number of comparisons made with sort-merge join = " + RelationalTable.RowComparator.nComps());
            assertEquals(true, RelationalTable.RowComparator.nComps() <= 100000);
            totalScore += 5;

            RelationalTable.RowComparator.reset();
            t3 = proc.nestedLoopJoin(ct, st, "t1", "t2",
                    new String[]{"studentId", "studentId"}, "Student-course");
            //System.out.println("Size of Student-course with index join = " + t3.size());
            //System.out.println("Number of comparisons made with nested loop join = " + RelationalTable.RowComparator.nComps());
            assertEquals(true, RelationalTable.RowComparator.nComps() == nStudents * nCourses * nStudents);
            totalScore += 5;
            success("performanceTest");
        } catch (Exception e) {
            failure("performanceTest", e);
        }
    }
}
