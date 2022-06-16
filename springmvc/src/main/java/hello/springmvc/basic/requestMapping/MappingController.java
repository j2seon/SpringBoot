package hello.springmvc.basic.requestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET) //url로 매핑을 했다~  배열로 만들수도 있음.
    public String helloBasic(){
        log.info("hello-Basic");
        return "OK";
    }
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션 (코드보기)
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable userId
     * /mapping/{userId}
     */

    @GetMapping("/mapping/{userId}") // 내가 /mapping/userA 라고 보내면 userA의 값을 받을 수 있다.
    public String mappingPath(@PathVariable String userId) {
        log.info("mappingPath userId={}", userId); //변수명에 맞추면 PathVariable("") < 안의 내용 생략 가능
        return "Ok";
    }

    /**
     * PathVariable 사용 다중!!
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}") // 어떤 유저가 주문한 주문번호!
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode" 없어야된다
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    
    @GetMapping(value = "/mapping-param", params = "mode=debug")//params = "mode=debug"가 있어야 호출 된다. (and조건)
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug") //헤더에 반영 url 말구!
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }
    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }
    @PostMapping(value = "/mapping-consumes", consumes = MediaType.APPLICATION_JSON_VALUE) //이렇게 적는게 더 좋겟지?
    public String mappingConsumes2() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE) //나는 text를 받을 수 있어! 그러니가 다른거 적으면 406 에러나요!
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }




}
