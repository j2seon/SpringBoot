package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //롬복에 있는 RequiredArgsConstructor 써주면 밑의 생성자를 생성해줌.
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    //목록
    @GetMapping
    public String items(Model model){
        List<Item> items =itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    //상세목록
    @GetMapping("{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "/basic/addForm";
    }
  //  @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam int quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item",item);

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2 (@ModelAttribute("item") Item item){

        itemRepository.save(item);
        //model.addAttribute("item",item); 자동추가된다.

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3 (@ModelAttribute Item item){

        itemRepository.save(item);
        //model.addAttribute("item",item); 자동추가된다.

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4 (Item item){

        itemRepository.save(item);
        //model.addAttribute("item",item); 자동추가된다.

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV5 (Item item){
        itemRepository.save(item);
        //model.addAttribute("item",item); 자동추가된다.

        return "redirect:/basic/items/"+item.getId();
    }
    @PostMapping("/add")
    public String addItemV6 (Item item, RedirectAttributes rattr){
        itemRepository.save(item);
        rattr.addAttribute("itemId",item.getId());
        rattr.addAttribute("status",true);
        //model.addAttribute("item",item); 자동추가된다.

        return "redirect:/basic/items/{itemId}";
    }


    //수정 보여주는거
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId,item);

        return "redirect:/basic/items/{itemId}";
    }




    //테스트용 데이터
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",1000,100));
        itemRepository.save(new Item("itemB",2000,200));
    }







}
