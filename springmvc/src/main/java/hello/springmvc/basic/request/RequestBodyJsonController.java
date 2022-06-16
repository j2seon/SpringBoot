package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/*
     * {"username":"hello", "age":20}
     * content-type: application/json
     */

@Slf4j
@Controller
public class RequestBodyJsonController {

    //json이기때문에 ObjectMapper 필요하다
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = request.getInputStream();
        String messageBody =StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // inputStream으로 읽어서 변환해주고
        log.info("messageBody={}",messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); //objectMapper로 객체로 만들어주고 해당되는 객체로 받으면됨.
        log.info("username={},age={}",helloData.getUsername(),helloData.getAge());

        response.getWriter().write("ok");
    }
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messagebody) throws IOException {
        log.info("messageBody={}", messagebody);

        HelloData helloData = objectMapper.readValue(messagebody, HelloData.class); //objectMapper로 객체로 만들어주고 해당되는 객체로 받으면됨.
        log.info("username={},age={}", helloData.getUsername(), helloData.getAge());

        return "OK";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {

        log.info("username={},age={}", helloData.getUsername(), helloData.getAge());

        return "OK";
    }
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody(); //바디에서 꺼내와야한다.
        log.info("username={},age={}", data.getUsername(), data.getAge());

        return "OK";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data; //json으로 넘겨받은 값을 객체로 받고 그 객체로 받은 애를 다시 json으로 변환해서 응답(ResponseBody가 있다면!!)
    }


}
