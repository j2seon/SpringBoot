package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException { //바디로 온거 읽을때
        InputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        response.getWriter().write("Ok");

    }
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer writer) throws IOException { //직접 inputStream,Writer를 받을 수 있다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        writer.write("ok");

    }
//    @PostMapping("/request-body-string-v3")
//    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException { //직접 inputStream,Writer를 받을 수 있다.
//
//        String body = httpEntity.getBody();//HttpEntit가 오면 문자변환 ~ 아런거 없이 얘가 출력해줌
//        log.info("messageBody={}",body); //그럼 출력은???? 똑같이 HttpEntit로 꺼내주면됌.
//
//        return new HttpEntity<>("ok"); //바디메세지를 넣을 수 있다.
//    }
    @PostMapping("/request-body-string-v3") //상속받은 것들로도 할 수 있다.
    public HttpEntity<String> requestBodyStringV3(RequestEntity<String> httpEntity) throws IOException { //직접 inputStream,Writer를 받을 수 있다.

        String body = httpEntity.getBody();//HttpEntit가 오면 문자변환 ~ 아런거 없이 얘가 출력해줌
        log.info("messageBody={}",body); //그럼 출력은???? 똑같이 HttpEntit로 꺼내주면됌.

        return new ResponseEntity<>("ok",HttpStatus.CREATED); //상태코드를 넣을 수 있다.
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messagebody) {

        log.info("messageBody={}",messagebody); // 위에 것들도 적을게 많네? 더 편한방법! post로 바디를 읽어서 @RequestBody으로 적혀있는 곳에 넣어버림

        return "ok"; //RequestBody 가 있으면 응답해주는 것도 있겠지? 앞에 ResponseBody어서 String 으로 던져~~
    }


}
