public class StudentManagement {
    private Student[] students = new Student[100];
    private int studentCount = 0;

    /**
     * Checks if two students belong to the same group.
     *
     * @param s1 First student to compare.
     * @param s2 Second student to compare.
     * @return True if both students are in the same group, otherwise false.
     */
    public static boolean sameGroup(Student s1, Student s2) {
        // TODO:
        return s1.getGroup().equals(s2.getGroup()); // xoa dong nay sau khi cai dat
    }

    /**
     * Adds a new student to the list of students.
     *
     * @param newStudent The new student to add.
     */
    public void addStudent(Student newStudent) {
        // TODO:
        students[studentCount] = newStudent;
        studentCount++;
    }

    /**
     * Generates a string containing students grouped by their groups.
     *
     * @return A string containing students grouped by their groups.
     */
    public String studentsByGroup() {
        // TODO:
        boolean[] read = new boolean[studentCount];

        for (int i = 0; i < studentCount; i++) {
            read[i] = false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < studentCount; i++) {
            if (read[i]) {
                continue;
            }
            stringBuilder.append(students[i].getGroup()).append("\n");
            stringBuilder.append(students[i].getInfo()).append("\n");
            read[i] = true;
            for (int j = i + 1; j < studentCount; j++) {
                if (!sameGroup(students[i], students[j])) {
                    continue;
                }
                stringBuilder.append(students[j].getInfo()).append("\n");
                read[j] = true;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Removes a student with the specified ID from the list of students.
     *
     * @param id The ID of the student to remove.
     */
    public void removeStudent(String id) {
        // TODO:
        int cnt = -1;
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                cnt = i;
                break;
            }
        }
        if (cnt != -1) {
            for (int i = cnt; i < studentCount - 1; i++) {
                students[i] = students[i + 1];
            }
            studentCount--;
        }
    }

    /**
     * main funtion.
     * @param args main funtion to solve.
     */
    public static void main(String[] args) {
        // TODO:

    }
}