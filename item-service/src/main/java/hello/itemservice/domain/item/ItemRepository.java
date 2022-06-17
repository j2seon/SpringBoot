package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //실제는 hashmapxxxxx ConcurrentHashMap
    private static Long sequence = 0L; //static

    //등록
    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    //조회
    public Item findById(Long id){
        return store.get(id);
    }

    //전체 조회
    public List<Item> findAll(){
        return new ArrayList<>(store.values()); //스토어의 값들
    }

    public void update(Long id, Item updateParam){
        //아이템찾기
        Item findItem = findById(id);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }

}
