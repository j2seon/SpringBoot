package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class helloController {
    @GetMapping("hello")
    public String hello(Model m){
        m.addAttribute("data","hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc") // 외부에서 파라미터를 받아보자
    public String helloMvc(@RequestParam("name") String name, Model m){
        m.addAttribute("name",name); //파라미터로 넘어온 이름을 넘겨보자
        return "hello-templates";
    }
    @GetMapping("hello-string")
    @ResponseBody // http에서 응답 바디에 이걸 넣어주겠다. 데이터를 그대로 내려준다.
    public String hellostring(@RequestParam("name") String name){
        return "hello"+ name;
    }
    @GetMapping("hello-api")
    @ResponseBody //이게 있으면 응답 바디에 넘겨야지한다. 근데 객체네? 그럼 스프링이 json 방식으로 데이터를 만들어서 반환한다.
    public Hello HelloApi(@RequestParam("name")String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    } //json 방식!!!

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

}
