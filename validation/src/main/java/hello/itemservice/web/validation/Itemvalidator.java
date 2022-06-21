package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class Itemvalidator implements Validator {

    @Override //검증기
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        //item == clazz 아이템이랑 클래스에 넘어오는타입이 같은가?
        //item == subItem(자식부분도 포함!)
    }

    @Override //검증부분
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;
        //검증로직
        if(!StringUtils.hasText(item.getItemName())){ //글자가 없으면
            errors.rejectValue("itemName","required");//required만 넣어주면된다/
        }
        if(item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.rejectValue("price","range", new Object[]{1000 , 1000000},null);
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            errors.rejectValue("quantity","max", new Object[]{9999},null);
        }
        //특정피드가 아닌 복잡 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){//필드에러가 아니면 ObjectError를 사용하자
                errors.reject("totalPriceMin",new Object[]{10000,resultPrice}, null);
            }
        }

    }
}
