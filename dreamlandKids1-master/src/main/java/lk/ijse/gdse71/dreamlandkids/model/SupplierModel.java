package lk.ijse.gdse71.dreamlandkids.model;

import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.SupplierDTO;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Supplier");

        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    public String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from Supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Supplier values (?,?,?,?,?)",
                supplierDTO.getSupplierId(),
                supplierDTO.getName(),
                supplierDTO.getNic(),
                supplierDTO.getEmail(),
                supplierDTO.getPhone()
        );
    }

    public boolean deleteSupplier(String supplierId) throws SQLException {
        return CrudUtil.execute("delete from Supplier where suplier_id=?", supplierId);
    }

    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "update Supplier set name=?, nic=?, email=?, phone=? where supplier_id=?",
                supplierDTO.getName(),
                supplierDTO.getNic(),
                supplierDTO.getEmail(),
                supplierDTO.getPhone(),
                supplierDTO.getSupplierId()
        );
    }
}
