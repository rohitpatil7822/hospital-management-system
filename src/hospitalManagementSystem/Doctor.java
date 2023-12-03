package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {

    private final Connection connection;

    public Doctor(Connection connection1){
        connection = connection1;
    }

    public void viewDoctors(int... id){

        int doctorId = (id.length > 0) ? id[0] : 0;

        try {

            String condition = "";

            if (doctorId > 0) condition = "where doctor_id = "+doctorId;

            String selectQry = "select * from tbl_doctors_info "+condition;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQry);

            ResultSet resultSet = preparedStatement.executeQuery();
            

            while (resultSet.next()){

                System.out.println("-----------------------------------------------------------------------------");
                System.out.println("ID: "+resultSet.getInt("doctor_id"));
                System.out.println("NAME: "+resultSet.getString("doctor_name"));
                System.out.println("SPECIALIZATION: "+resultSet.getString("specialization"));
                System.out.println("-----------------------------------------------------------------------------");
            }

            if (!resultSet.next()) System.out.println("No Record Found");

        }catch (SQLException e){

            System.out.println("Error Occurred: "+e.getMessage());

        }
    }

    public boolean checkDoctor(int id){
        boolean isPresent = false;
        try {

            String selectQry = "select * from tbl_doctors_info where doctor_id = ?";
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
