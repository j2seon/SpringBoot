package hello.itemservice.validation;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.FieldError;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject(){
       String[] messageCodes = codesResolver.resolveMessageCodes("required","item");
        for(String messageCode : messageCodes){
            System.out.println("messageCode = "+messageCode);
            /* messageCode = required.item , messageCode = required */
        }
        Assertions.assertThat(messageCodes).containsExactly("required.item","required");
    }

    @Test
    void messageCodesResolverField(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required","item","itemName",String.class);
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = "+ messageCode);
        }
        //bindingResult.rejectValue("itemName","required"); 내부적으로 MessageCodesResolver처리를 한다.
        new FieldError("item","itemName",null,false,messageCodes,null,null);
        Assertions.assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
