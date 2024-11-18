package lk.ijse.gdse71.dreamlandkids.model;



import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.ItemDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public ItemDTO findById(String selectedItemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Item where item_id=?", selectedItemId);

        if (rst.next()) {
            return new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }

    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "update Item set quantity = quantity - ? where item_id = ?",
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getItemId()
        );
    }


    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Item");

        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        while (rst.next()) {
            ItemDTO itemDTO = new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    public String getNextItemId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select item_id from Item order by item_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    public boolean saveItem(ItemDTO itemDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Item values (?,?,?,?)",
                itemDTO.getItemId(),
                itemDTO.getItemName(),
                itemDTO.getQuantity(),
                itemDTO.getPrice()
        );
    }

    public boolean deleteItem(String itemId) throws SQLException {
        return CrudUtil.execute("delete from Item where item_id=?", itemId);
    }

    public boolean updateCustomer(ItemDTO itemDTO) throws SQLException {
        return CrudUtil.execute(
                "update Item set name=?, quantity=?, price=? where item_id=?",
                itemDTO.getItemName(),
                itemDTO.getQuantity(),
                itemDTO.getPrice(),
                itemDTO.getItemId()
        );
    }

    public ArrayList<String> getAllItemIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select item_id from Item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }

}

