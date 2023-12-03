package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient {

    private final Connection connection;

    public Patient(Connection connection1){
        connection = connection1;
    }

    public int addPatient(String name , int age , String gender){

        try {

            String insertQuery = "insert into tbl_patients_info set patient_name = ? , age = ? , gender = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectRows = preparedStatement.executeUpdate();

            if (affectRows > 0) return 1;

        }catch (SQLException e){

            System.out.println("Error Occurred: "+e.getMessage());
        }

        return 0;
    }

    public void viewPatients(int... id){

        int patientId = (id.length > 0) ? id[0] : 0;

        try {

            String condition = "";

            if (patientId > 0) condition = "where pid = "+patientId;

            String selectQry = "select * from tbl_patients_info "+condition;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQry);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){

                System.out.println("-----------------------------------------------------------------------------");
                System.out.println("ID: "+resultSet.getInt("pid"));
                System.out.println("NAME: "+resultSet.getString("patient_name"));
                System.out.println("AGE: "+resultSet.getInt("age"));
                System.out.println("GENDER: "+resultSet.getString("gender"));
                System.out.println("-----------------------------------------------------------------------------");
            }

            if (!resultSet.next()) System.out.println("No Record Found");

        }catch (SQLException e){

            System.out.println("Error Occurred: "+e.getMessage());

        }
    }

    public boolean checkPatient(int id){
        boolean isPresent = false;
        try {

            String selectQry = "select * from tbl_patients_info where pid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQry);
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
            isPresent = resultSet.next();

        }catch (SQLException e){

            System.out.println("Error Occurred: "+e.getMessage());
        }

        return isPresent;
    }
}
