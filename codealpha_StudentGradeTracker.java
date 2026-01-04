import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    int grade;

    Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for student " + (i + 1));

            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Grade: ");
            int grade = sc.nextInt();
            sc.nextLine();
            students.add(new Student(name, grade));
        }
        int total = 0;
        int highest = students.get(0).grade;
        int lowest = students.get(0).grade;
        for (Student s : students) {
            total += s.grade;

            if (s.grade > highest)
                highest = s.grade;

            if (s.grade < lowest)
                lowest = s.grade;
        }

        double average = (double) total / n;
        System.out.println("\n========== Student Summary Report ==========");
        System.out.println("Name\t\tGrade");

        for (Student s : students) {
            System.out.println(s.name + "\t\t" + s.grade);
        }

        System.out.println("--------------------------------------------");
        System.out.println("Average Score : " + average);
        System.out.println("Highest Score : " + highest);
        System.out.println("Lowest Score  : " + lowest);

        sc.close();
    }
}
