package lk.ijse.gdse71.dreamlandkids.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}
