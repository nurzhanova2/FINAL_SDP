import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RegistrationManager manager = RegistrationManager.getInstance();
        manager.addObserver(new LoggingObserver());
        manager.addObserver(new EmailNotificationObserver());

        CourseFactory programmingCourseFactory = new ProgrammingCourseFactory();
        CourseFactory designCourseFactory = new DesignCourseFactory();

        StudentFactory regularStudentFactory = new RegularStudentFactory();
        StudentFactory premiumStudentFactory = new PremiumStudentFactory();

        StudentBuilder studentBuilder = new StudentBuilder();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите ваше имя:");
        String name = scanner.nextLine();

        System.out.println("Выберите курс (1 - Programming Course, 2 - Design Course):");
        int courseChoice = scanner.nextInt();
        scanner.nextLine();

        CourseFactory courseFactory;
        if (courseChoice == 1) {
            courseFactory = programmingCourseFactory;
        } else {
            courseFactory = designCourseFactory;
        }

        System.out.println("Вы премиум студент? (true/false):");
        boolean isPremium = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println("Введите ваш email:");
        String email = scanner.nextLine();

        StudentFactory studentFactory;
        if (isPremium) {
            studentFactory = premiumStudentFactory;
        } else {
            studentFactory = regularStudentFactory;
        }

        Student student = studentBuilder.setName(name).setCourse(courseFactory.createCourse()).setIsPremium(isPremium).setEmail(email).build(studentFactory);

        manager.registerStudent(student);
        Database database = new Database();
        database.saveStudent(student);

        manager.displayRegisteredStudents();

        System.out.println("--------------------------------------------------");

        List<Student> studentsFromDatabase = database.getStudents();
        for (Student students : studentsFromDatabase) {
            System.out.println(students);
        }
    }
}
