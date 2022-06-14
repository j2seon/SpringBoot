package com.example.demo.repository;

import com.example.demo.domain.Member;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();
    // 전체 테스트를 할 경우 오류가 날 수 있다 . 따라서 메모리를 지워주는 것이 필ㅇ하다.
    @AfterEach
    public void afterEach(){
    repository.clearStore();
    }


    @Test
    public void save(){
        Member member =new Member();
        member.setName("String");
        repository.save(member);//저장하고 꺼내는데 반환타입을 아까 Optional 로 지정했기때문에! Optional은 get()으로 꺼낼수있지만 좋은 건 아님!!
        Member member1=repository.findById(member.getId()).get();//내가 지정한거랑 db에 저장된거랑 같은지?
        //Assertions.assertEquals(member,member1);
        assertThat(member).isEqualTo(member1);//static 지정
    }

    @Test
    public void findbyName(){
        Member member1 = new Member();
        member1.setName("s1");
        repository.save(member1); // 저장

        Member member2 = new Member();
        member2.setName("s2");
        repository.save(member2); // 저장

        Member result = repository.findByName("s1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findbyAll(){
        Member member = new Member();
        member.setName("spring1");
        repository.save(member);
        Member member1 = new Member();
        member1.setName("spring2");
        repository.save(member1);

        List<Member> list = repository.findAll();
        assertThat(list.size()).isEqualTo(2);
    }

}
