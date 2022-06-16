package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

    //이거 계속 적기 귀찮잖아? 지원하는 라이브러리가 있다.
   // private final Logger log = LoggerFactory.getLogger(getClass()); // slf4j로 해야된다! >>

    @RequestMapping("/log-test") //get 매핑으로 해도됨.
    public String logTest(){

        String name = "spring";
        System.out.println(name); // 어떤 것이던 그냥 계속나옴 그래서 log를 이용해야된다.
        log.info("info{}", name);

        //밑의 내용 으로 log 범위를 설정할 수 있다. applicationproperties에서 !
        log.trace("trace log={}"+ name); //이것도 가능하지만 + 연산이라 리소스 낭비가 일어나용! 심지어 디버그로 정해놔서 출력도 안되는데 연산은 일어나서낭비!!!
        log.trace("trace log={}",name); //메세지 전부볼거야
        log.debug("debug log={}",name); //디버그
        log.info("info log={}",name); //정보 보통 운영서버에서 적용하며 기본이 info이다.
        log.warn("warn log={}",name); //경고
        log.error("error log={}",name); //에러

        return "OK"; //RestController의 경우 string이 그대로 반환이 된다.
    }




}
