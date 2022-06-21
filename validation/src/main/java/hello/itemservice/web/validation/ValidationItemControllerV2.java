package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final Itemvalidator itemvalidator;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemvalidator);
    }

//    @Autowired 이게되는거라고 생각하자 위에RequiredArgsConstructor가 있어서 생략가능
//    public ValidationItemControllerV2(ItemRepository itemRepository, Itemvalidator itemvalidator) {
//        this.itemRepository = itemRepository;
//        this.itemvalidator = itemvalidator;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item,BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {

        //BindingResult가 v1에서 했던 map 의 역할을 하게된다.

        //검증로직
        if(!StringUtils.hasText(item.getItemName())){ //글자가 없으면
            bindingResult.addError(new FieldError("item","itemName","상품이름은 필수입니다.")); //모델에 담기는 이름/필드명/메시지
        }
        if(item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item","price","가격은 1,000~ 1,000,000까지 허용합니다.")); //모델에 담기는 이름/필드명/메시지
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9999까지 허용합니다.")); //모델에 담기는 이름/필드명/메시지
        }
        //특정피드가 아닌 복잡 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){//필드에러가 아니면 ObjectError를 사용하자
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10.000원 이상이여야합니다, 현재값 : "+resultPrice)); 
            }
        }

        //검증에 실패하면? 다시 폼으로 돌아오게 하기
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
//            model.addAttribute("errors",errors); 모델에 넣을 필요없음. bindingResult는 자동으로 넘어간다.
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item,BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {

        //BindingResult가 v1에서 했던 map 의 역할을 하게된다.

        //검증로직
        if(!StringUtils.hasText(item.getItemName())){ //글자가 없으면
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,null,null,"상품이름은 필수입니다."));
        }
        if(item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,null,null,"가격은 1,000~ 1,000,000까지 허용합니다."));
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,null,null,"수량은 최대 9999까지 허용합니다."));
        }
        //특정피드가 아닌 복잡 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){//필드에러가 아니면 ObjectError를 사용하자
                bindingResult.addError(new ObjectError("item",null,null,"가격 * 수량의 합은 10.000원 이상이여야합니다, 현재값 : "+resultPrice));
            }
        }

        //검증에 실패하면? 다시 폼으로 돌아오게 하기
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
//            model.addAttribute("errors",errors); 모델에 넣을 필요없음. bindingResult는 자동으로 넘어간다.
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item,BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {

        //BindingResult가 v1에서 했던 map 의 역할을 하게된다.

        //검증로직
        if(!StringUtils.hasText(item.getItemName())){ //글자가 없으면
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName"},null,null));
        }
        if(item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{9999},null));
        }
        //특정피드가 아닌 복잡 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){//필드에러가 아니면 ObjectError를 사용하자
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,resultPrice},null));
            }
        }

        //검증에 실패하면? 다시 폼으로 돌아오게 하기
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
//            model.addAttribute("errors",errors); 모델에 넣을 필요없음. bindingResult는 자동으로 넘어간다.
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
   // @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item,BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {

        //BindingResult가 v1에서 했던 map 의 역할을 하게된다.

//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"itemName","required"); >> 밑의 내용을 한즐로 단순공백만

        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        //검증로직
        if(!StringUtils.hasText(item.getItemName())){ //글자가 없으면
            bindingResult.rejectValue("itemName","required");//required만 넣어주면된다/
        }


        if(item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000){
        bindingResult.rejectValue("price","range", new Object[]{1000 , 1000000},null);
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            bindingResult.rejectValue("quantity","max", new Object[]{9999},null);
        }
        //특정피드가 아닌 복잡 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){//필드에러가 아니면 ObjectError를 사용하자
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice}, null);
            }
        }

        //검증에 실패하면? 다시 폼으로 돌아오게 하기
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
//            model.addAttribute("errors",errors); 모델에 넣을 필요없음. bindingResult는 자동으로 넘어간다.
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item,BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {

        //검증기
        itemvalidator.validate(item,bindingResult);

        //검증에 실패하면? 다시 폼으로 돌아오게 하기
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
//            model.addAttribute("errors",errors); 모델에 넣을 필요없음. bindingResult는 자동으로 넘어간다.
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    @PostMapping("/add") //검증할 대상 앞에 @Validated
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증에 실패하면? 다시 폼으로 돌아오게 하기
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
//            model.addAttribute("errors",errors); 모델에 넣을 필요없음. bindingResult는 자동으로 넘어간다.
            return "validation/v2/addForm";
        }

        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

