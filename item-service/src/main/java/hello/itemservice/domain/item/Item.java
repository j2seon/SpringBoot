package hello.itemservice.domain.item;

import lombok.Data;

//@Getter @Setter 실무에서는 Data 보단 이것만 쓰는 것이 좋다.
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(){}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }


}
