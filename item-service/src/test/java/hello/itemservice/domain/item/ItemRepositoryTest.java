package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA",1000,10);
        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem=itemRepository.findById(saveItem.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("item1",1000,10);
        Item item2 = new Item("item2",2000,20);

        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> list = itemRepository.findAll();
        //then
        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(item1,item2);

    }

    @Test
    void update() {
        //given
        Item item1 = new Item("item1",1000,10);

        Item saveItem = itemRepository.save(item1);
        Long id =saveItem.getId();
        //when
        Item itemparam =new Item("item2",2000,200);
        itemRepository.update(id,itemparam);
        //then
        Item findItem = itemRepository.findById(id);
        assertThat(findItem.getItemName()).isEqualTo(itemparam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(itemparam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(itemparam.getQuantity());
    }

}