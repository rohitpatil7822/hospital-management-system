package hospitalManagementSystem;

import java.sql.*;

public class Appointment {

    private final Connection connection;

    public Appointment(Connection connection1){

        connection = connection1;
    }

    public int bookAppoint(int doctorId , int patientId , String appointmentDate , Doctor doctor , Patient patient){

        if (patient.checkPatient(patientId) && doctor.checkDoctor(doctorId)){

            if (checkDoctorAvalablity(doctorId,appointmentDate)){

                String insertQry = "insert into tbl_appointment set patient_id = ? , doctor_id = ? , appoitment_date = ?";

                try {

                    PreparedStatement preparedStatement = connection.prepareStatement(insertQry);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    java.sql.Date date = java.sql.Date.valueOf(appointmentDate);
                    preparedStatement.setDate(3,date);

                    int affectedRow = preparedStatement.executeUpdate();

                    if (affectedRow > 0) return 1;

                }catch (SQLException e){

                    System.out.println("Error Occurred: "+e.getMessage());
                }

            }
        }

        return  0;
    }

    private boolean checkDoctorAvalablity(int doctorId , String appointmentDate){

        String selectQuery = "select count(*) as cnt from tbl_appointment where doctor_id = ? and appoitment_date = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1,doctorId);
//            java.sql.Date date = java.sql.Date.valueOf(appointmentDate);
            preparedStatement.setString(2, appointmentDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                int count = resultSet.getInt("cnt");

                if (count == 0) return true;
            }

        }catch (SQLException e){

            System.out.println("Error Occurred: "+e.getMessage());
        }
        return false;
    }
}
