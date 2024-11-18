package lk.ijse.gdse71.dreamlandkids.model;

import lk.ijse.gdse71.dreamlandkids.dto.EmployeeDTO;
import lk.ijse.gdse71.dreamlandkids.dto.SupplierDTO;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public boolean deleteEmployee(String employeeId) throws SQLException {
        return CrudUtil.execute("delete from Employee where employee_id=?",employeeId);
    }

    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Employee");

        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();

        while (rst.next()) {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    public String getNextEmployeeId() throws SQLException {
        // Correct the SQL query
        ResultSet rst = CrudUtil.execute("select employee_id from Employee order by employee_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);  // Get the last employee_id from the database
            String substring = lastId.substring(1);  // Remove the 'E' prefix
            int i = Integer.parseInt(substring);  // Convert the remaining number part to integer
            int newIdIndex = i + 1;  // Increment the last ID
            return String.format("E%03d", newIdIndex);  // Format the new ID (e.g., E002, E003, etc.)
        }
        return "E001";  // If no records are found, return the first ID
    }


    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Employee values (?,?,?,?,?)",
                employeeDTO.getEmployeeId(),
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getEmail(),
                employeeDTO.getPhone()
        );
    }

    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "update Employee set name=?, nic=?, email=?, phone=? where employee_id=?",
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getEmail(),
                employeeDTO.getPhone(),
                employeeDTO.getEmployeeId()
        );
    }
}
