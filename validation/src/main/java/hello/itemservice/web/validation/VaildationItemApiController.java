package hello.itemservice.web.validation;


import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("validation/api/items")
public class VaildationItemApiController {


    @PostMapping("/add")//검증+ api로 사용하기 위해서 requestBody 사용
    public Object addItme(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult){
        log.info("api 컨트롤러 호출 ");

        if(bindingResult.hasErrors()){ //!!! json을 객체로 못바꾸면 컨트롤러에 접근도 못함.
            log.info("검증오류발생 errors={}",bindingResult);
            return bindingResult.getAllErrors();//모든 오류를 리턴한다.
            // 그리고 RestController가 있어서 responsebody가 붙는다>> json으로 값이 나간다.
        }

        log.info("성공");
        return form;
    }

}
