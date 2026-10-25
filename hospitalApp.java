
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class hospitalApp {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ArrayList<String> appointments = new ArrayList<>();
            ArrayList<String> patients = new ArrayList<>();
            int choice;

            do {
                System.out.println("\n======================================");
                System.out.println(" HOSPITAL APPOINTMENT AND HEALTH APP");
                System.out.println("======================================");
                System.out.println("1. Patient Registration");
                System.out.println("2. View Doctors");
                System.out.println("3. Book Appointment");
                System.out.println("4. View Appointments");
                System.out.println("5. Cancel Appointment");
                System.out.println("6. Health Information");
                System.out.println("7. View Patients");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.println("\n--- Patient Registration ---");

                        System.out.print("Enter Patient ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Patient Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();

                        System.out.print("Enter Phone Number: ");
                        String phone = sc.nextLine();

                        patients.add("Patient ID: " + id
                                + " | Name: " + name
                                + " | Age: " + age
                                + " | Gender: " + gender
                                + " | Phone: " + phone);
                                try {
    String sql = "INSERT INTO Patients "
            + "(patient_id, name, age, gender, phone) "
            + "VALUES (?, ?, ?, ?, ?)";

                            try (java.sql.Connection con = java.sql.DriverManager.getConnection(
                                    "jdbc:mysql://localhost:3306/HospitalDB",
                                    "root",
                                    "Suji3101_@.ravi"
                            )) {
                                java.sql.PreparedStatement pst = con.prepareStatement(sql);
                                
                                pst.setInt(1, id);
                                pst.setString(2, name);
                                pst.setInt(3, age);
                                pst.setString(4, gender);
                                pst.setString(5, phone);
                                
                                pst.executeUpdate();
                                
                                System.out.println("Patient saved to MySQL successfully!");
                                
                                pst.close();            }

} catch (java.sql.SQLException e) {
    System.out.println("Database Error: " + e.getMessage());
}
                                try {
                            try (FileWriter writer = new FileWriter("patients.txt", true)) {
                                writer.write(
                                        "Patient ID: " + id
                                                + " | Name: " + name
                                                + " | Age: " + age
                                                + " | Gender: " + gender
                                                + " | Phone: " + phone
                                                + "\n"
                                );                      }

    System.out.println("Patient details saved successfully!");

} catch (IOException e) {
    System.out.println("Error saving patient details.");
}

                        System.out.println("\nRegistration Successful!");
                        System.out.println("Patient ID: " + id);
                        System.out.println("Name: " + name);
                        System.out.println("Age: " + age);
                        System.out.println("Gender: " + gender);
                        System.out.println("Phone: " + phone);
                    }
                    case 2 -> {
    System.out.println("\n--- Available Doctors ---");

    String sql = "SELECT * FROM Doctors";

    try (
        java.sql.Connection con =
            java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/HospitalDB",
                "root",
                "Suji3101_@.ravi"
            );

        java.sql.PreparedStatement pst =
            con.prepareStatement(sql);

        java.sql.ResultSet rs = pst.executeQuery()
    ) {
        while (rs.next()) {
            System.out.println(
                rs.getInt("doctor_id") + ". "
                + rs.getString("doctor_name")
                + " - "
                + rs.getString("specialization")
                + " | Time: "
                + rs.getString("available_time")
            );
        }
    } catch (java.sql.SQLException e) {
        System.out.println("Database Error: " + e.getMessage());
    }
}
                    case 3 -> {
                        
    System.out.println("\n--- Book Appointment ---");

    System.out.print("Enter Patient ID: ");
    int patientId = sc.nextInt();
    sc.nextLine();

    boolean patientFound = false;

    for (String patient : patients) {
        if (patient.startsWith("Patient ID: " + patientId + " |")) {
            patientFound = true;
            break;
        }
    }

    if (!patientFound) {
        System.out.println("Patient not registered!");
        System.out.println("Please register first.");
        break;
    }

                        System.out.println("1. Dr. Kumar - Cardiology");
                        System.out.println("2. Dr. Priya - Dermatology");
                        System.out.println("3. Dr. Ravi - General physician");
                       
                        System.out.print("Choose Doctor (1-4): ");
                        int doctorChoice = sc.nextInt();
                        sc.nextLine();

                        String doctorName;
                        switch (doctorChoice) {
                            case 1 -> doctorName = "Dr. Kumar - Cardiologist";
                            case 2 -> doctorName = "Dr. Priya - Dermatologist";
                            case 3 -> doctorName = "Dr. Ravi - General Physician";
                           

                            default -> {
                                System.out.println("Invalid doctor choice!");
                                doctorName = "";
                                break;
                            }
                        }

                        if (doctorName.isEmpty()) {
                            break;
                        }

                        System.out.print("Enter Appointment Date (DD-MM-YYYY): ");
String date = sc.nextLine();

if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
    System.out.println("Invalid date format!");
    System.out.println("Please enter date as DD-MM-YYYY.");
    break;
}
System.out.println("\nAvailable Appointment Times:");
System.out.println("1. 10:00 AM");
System.out.println("2. 11:00 AM");
System.out.println("3. 02:00 PM");
System.out.println("4. 04:00 PM");

System.out.print("Choose appointment time (1-4): ");
int timeChoice = sc.nextInt();
sc.nextLine();

String appointmentTime = null;

switch (timeChoice) {
    case 1 -> appointmentTime = "10:00 AM";
    case 2 -> appointmentTime = "11:00 AM";
    case 3 -> appointmentTime = "02:00 PM";
    case 4 -> appointmentTime = "04:00 PM";
    default -> System.out.println("Invalid time choice!");
}boolean slotAvailable = true;

for (String appointment : appointments) {
    if (appointment.contains("Doctor: " + doctorName)
            && appointment.contains("Date: " + date)
            && appointment.contains("Time: " + appointmentTime)) {

        slotAvailable = false;
        break;
    }
}

if (!slotAvailable) {
    System.out.println("\nAppointment slot already booked!");
    System.out.println("Please choose another date or time.");
    break;
}
                        appointments.add(
    "Patient ID: " + patientId
    + " | Doctor: " + doctorName
    + " | Date: " + date
    + " | Time: " + appointmentTime
                        );
                        try {
    String sql = "INSERT INTO Appointments "
            + "(patient_id, doctor_id, appointment_date, appointment_time, status) "
            + "VALUES (?, ?, ?, ?, ?)";

    int doctorId = doctorChoice;

    try (java.sql.Connection con =
            java.sql.DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/HospitalDB",
                    "root",
                "Suji3101_@.ravi")) {
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, patientId);
        pst.setInt(2, doctorId);
        pst.setString(3, date);
        pst.setString(4, appointmentTime);
        pst.setString(5, "Booked");

        pst.executeUpdate();

        System.out.println("Appointment saved to MySQL successfully!");
    }

} catch (java.sql.SQLException e) {
    System.out.println("Database Error: " + e.getMessage());
}
                        try {
        try (FileWriter writer = new FileWriter("appointments.txt", true)) {
            writer.write(
                    "Patient ID:( " + patientId
                            + " | Doctor: " + doctorName
                            + " | Date: " + date
                            + " | Time: " + appointmentTime
                            + "\n"
            );  }

    System.out.println("Appointment details saved successfully!");

} catch (IOException e) {
    System.out.println("Error saving appointment details.");
}
                        System.out.println("\nAppointment Booked Successfully!");
                        System.out.println("Patient ID: " + patientId);
                        System.out.println("Doctor: " + doctorName);
                        System.out.println("Date: " + date);
                    }
                    case 4 -> {
    System.out.println("\n--- View Appointments ---");

    String sql = "SELECT * FROM Appointments";

    try (
        java.sql.Connection con =
            java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/HospitalDB",
                "root",
                "Suji3101_@.ravi"
            );

        java.sql.PreparedStatement pst =
            con.prepareStatement(sql);

        java.sql.ResultSet rs = pst.executeQuery()
    ) {
        boolean found = false;

        while (rs.next()) {
            found = true;

            System.out.println("-------------------------");
            System.out.println("Appointment ID: "
                + rs.getInt("appointment_id"));
            System.out.println("Patient ID: "
                + rs.getInt("patient_id"));
            System.out.println("Doctor ID: "
                + rs.getInt("doctor_id"));
            System.out.println("Date: "
                + rs.getString("appointment_date"));
            System.out.println("Time: "
                + rs.getString("appointment_time"));
            System.out.println("Status: "
                + rs.getString("status"));
        }

        if (!found) {
            System.out.println("No appointments booked yet.");
        }

    } catch (java.sql.SQLException e) {
        System.out.println("Database Error: " + e.getMessage());
    }
}
                    case 5 -> {
    System.out.println("\n--- Cancel Appointment ---");

    System.out.print("Enter Appointment ID to cancel: ");
    int appointmentId = sc.nextInt();
    sc.nextLine();

    String sql = "UPDATE Appointments "
            + "SET status = 'Cancelled' "
            + "WHERE appointment_id = ?";

    try (
        java.sql.Connection con =
            java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/HospitalDB",
                "root",
                "Suji3101_@.ravi"
            );

        java.sql.PreparedStatement pst =
            con.prepareStatement(sql)
    ) {
        pst.setInt(1, appointmentId);

        int rowsUpdated = pst.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Appointment cancelled successfully!");
        } else {
            System.out.println("Appointment ID not found!");
        }

    } catch (java.sql.SQLException e) {
        System.out.println("Database Error: " + e.getMessage());
    }
}
                    case 6 -> {
                        System.out.println("\n--- Health Information ---");
                        System.out.println("1. Healthy Diet");
                        System.out.println("2. Exercise");
                        System.out.println("3. Sleep");
                        System.out.println("4. Hydration");
                        System.out.println("5. General Health Tips");

                        System.out.print("Choose a category: ");
                        int healthChoice = sc.nextInt();
                        sc.nextLine();

                        switch (healthChoice) {
                            case 1 -> {
                                System.out.println("\n--- Healthy Diet ---");
                                System.out.println("Eat a balanced diet with fruits, vegetables,");
                                System.out.println("whole grains and protein-rich foods.");
                            }
                            case 2 -> {
                                System.out.println("\n--- Exercise ---");
                                System.out.println("Regular physical activity helps maintain");
                                System.out.println("overall fitness and well-being.");
                            }
                            case 3 -> {
                                System.out.println("\n--- Sleep ---");
                                System.out.println("Maintain a regular sleep schedule and");
                                System.out.println("get enough rest for your age.");
                            }
                            case 4 -> {
                                System.out.println("\n--- Hydration ---");
                                System.out.println("Drink water regularly throughout the day.");
                                System.out.println("Stay hydrated, especially during physical activity.");
                            }
                            case 5 -> {
                                System.out.println("\n--- General Health Tips ---");
                                System.out.println("1. Maintain good personal hygiene.");
                                System.out.println("2. Eat nutritious food.");
                                System.out.println("3. Stay physically active.");
                                System.out.println("4. Get adequate sleep.");
                                System.out.println("5. Consult a healthcare professional when needed.");
                            }
                            default -> System.out.println("Invalid health category!");
                        }
                    }
                   case 7 -> {
    System.out.println("\n--- Registered Patients ---");

    String sql = "SELECT * FROM Patients";

    try (
        java.sql.Connection con =
            java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/HospitalDB",
                "root",
                "Suji3101_@.ravi"
            );

        java.sql.PreparedStatement pst =
            con.prepareStatement(sql);

        java.sql.ResultSet rs = pst.executeQuery()
    ) {
        boolean found = false;

        while (rs.next()) {
            found = true;

            System.out.println("-------------------------");
            System.out.println("Patient ID: "
                + rs.getInt("patient_id"));
            System.out.println("Name: "
                + rs.getString("name"));
            System.out.println("Age: "
                + rs.getInt("age"));
            System.out.println("Gender: "
                + rs.getString("gender"));
            System.out.println("Phone: "
                + rs.getString("phone"));
        }

        if (!found) {
            System.out.println("No patients registered.");
        }

    } catch (java.sql.SQLException e) {
        System.out.println("Database Error: " + e.getMessage());
    }
}
                    case 8 -> System.out.println("\nThank you!");
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } while (choice != 8);
        }
    }
}


