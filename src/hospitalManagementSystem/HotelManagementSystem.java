package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HotelManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String username = "root";
    private static final String password = "Nanda@1324";

    public static void main(String[] args) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);

            Scanner scanner = new Scanner(System.in);

            Patient patient = new Patient(connection);
            Doctor doctor = new Doctor(connection);
            Appointment appointment = new Appointment(connection);


            while (true){

                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){

                    case 1:

                        System.out.print("Enter Patient Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Patient Age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter Patient Gender: ");
                        String gender = scanner.nextLine();

                        int successError = patient.addPatient(name,age,gender);

                        if (successError == 1){
                            System.out.println("Patient Added Successfully");
                        }else {
                            System.out.println("Failed To Add Patient Info");
                        }

                        break;

                    case 2:
                        patient.viewPatients();
                        break;

                    case 3:
                        doctor.viewDoctors();
                        break;
                    case 4:
                        System.out.println("Enter Patient Id: ");
                        int patientId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter Doctor Id: ");
                        int doctorId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
                        String appointmentDate = scanner.nextLine();

                        int successErrors = appointment.bookAppoint(doctorId,patientId,appointmentDate,doctor,patient);

                        if (successErrors == 1){
                            System.out.println("Thank You! Your Appointment Has Been Booked Successfully");
                        }else {
                            System.out.println("Oops! Failed To Book Appointment Doctor Is Not Available");
                        }
                        break;

                    case 5:
                        System.out.println("THANK YOU FOR USING REGE HOSPITAL MANAGEMENT SYSTEM");
                        return;
                    default:
                        System.out.println("Enter Valid Choice");
                        break;
                }
            }

        }catch(ClassNotFoundException | SQLException e){

            System.out.println("Error Occurred: "+e.getMessage());
        }
    }
}