package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class requsetParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username =request.getParameter("username");
        int age=Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}",username,age);

        response.getWriter().write("ok");

    }
    @ResponseBody //RestController와 같은 기능이다.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username")String memberName,
            @RequestParam("age") int memberage){

        log.info("username={}, age={}",memberName,memberage);
        return  "OK"; //ResponseBody가 없이 return 하면 해당 view를 찾는다

    }

    @ResponseBody //RestController와 같은 기능이다.
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username, //파라미터와 이름이 같으면 생략가능
            @RequestParam int age){

        log.info("username={}, age={}",username,age);
        return  "OK"; //ResponseBody가 없이 return 하면 해당 view를 찾는다

    }

    @ResponseBody //RestController와 같은 기능이다.
    @RequestMapping("/request-param-v4")
    public String requestParamV4(
                String username, //파라미터와 이름이 같으면 생략가능 근데 이러면 못알아볼수도...
                int age){

        log.info("username={}, age={}",username,age);
        return  "OK"; //ResponseBody가 없이 return 하면 해당 view를 찾는다

    }

    @ResponseBody //RestController와 같은 기능이다.
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = false) String username, //파라미터와 이름이 같으면 생략가능
            @RequestParam(required = true) Integer age){//required = false 없어도 오류 안남 근데 ture면 없으면 오류난다.
        //null  이랑 "" 이랑은 다른거임.
        log.info("username={}, age={}",username,age);
        return  "OK"; //ResponseBody가 없이 return 하면 해당 view를 찾는다

    }

    @ResponseBody //RestController와 같은 기능이다.
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue="quest") String username, //파라미터와 이름이 같으면 생략가능
            @RequestParam(required = false, defaultValue = "-1") int age){//required = false 없어도 오류 안남 근데 ture면 없으면 오류난다.
        //null  이랑 "" 이랑은 다른거임.
        log.info("username={}, age={}",username,age);
        return  "OK"; //ResponseBody가 없이 return 하면 해당 view를 찾는다

    }
    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...]) ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"),
                paramMap.get("age"));
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
    }

}
