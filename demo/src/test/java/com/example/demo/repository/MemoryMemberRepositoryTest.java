package com.example.demo.repository;

import com.example.demo.domain.Member;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
    // 전체 테스트를 할 경우 오류가 날 수 있다 . 따라서 메모리를 지워주는 것이 필ㅇ하다.
    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }


    @Test
    public void save(){
        Member member =new Member();
        member.setName("String");
        memoryMemberRepository.save(member);//저장하고 꺼내는데 반환타입을 아까 Optional 로 지정했기때문에! Optional은 get()으로 꺼낼수있지만 좋은 건 아님!!
        Member member1=memoryMemberRepository.findById(member.getId()).get();//내가 지정한거랑 db에 저장된거랑 같은지?
        //Assertions.assertEquals(member,member1);
        assertThat(member).isEqualTo(member1);//static 지정
    }

    @Test
    public void findbyName(){
        Member member1 = new Member();
        member1.setName("s1");
        memoryMemberRepository.save(member1); // 저장

        Member member2 = new Member();
        member2.setName("s2");
        memoryMemberRepository.save(member2); // 저장

        Member result = memoryMemberRepository.findByName("s1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findbyAll(){
        Member member = new Member();
        member.setName("spring1");
        memoryMemberRepository.save(member);
        Member member1 = new Member();
        member1.setName("spring2");
        memoryMemberRepository.save(member1);

        List<Member> list = memoryMemberRepository.findAll();
        assertThat(list.size()).isEqualTo(2);
    }

}
