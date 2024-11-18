package lk.ijse.gdse71.dreamlandkids.model;

import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    private final ItemModel itemModel = new ItemModel();

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                return false;
            }

            boolean isItemUpdated = itemModel.reduceQty(orderDetailsDTO);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into OrderDetails (order_id, item_id, quantity, price) values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getItemId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }
}
