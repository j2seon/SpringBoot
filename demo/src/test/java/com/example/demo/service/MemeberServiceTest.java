package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemeberServiceTest {

    MemeberService memeberService ;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    public void beforeEach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memeberService = new MemeberService(memoryMemberRepository);
    }


    @Test
    void join() {
        //given 기반!
        Member member =new Member();
        member.setName("jj");
        //when
        Long saveId = memeberService.join(member);

        //then 검증!
        Member findMember = memeberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복회원예외(){
        //given
        Member member1 =new Member();
        member1.setName("ssss");

        Member member2 =new Member();
        member2.setName("ssss");
        //when
        memeberService.join(member1);
//        try { //이것 대신 사용할 문법이 있다.
//            memeberService.join(member2); //예외가 발생해야된다.
//            fail();
//        }catch (IllegalStateException e){
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
//        }
        //then // IllegalStateException이 발생해야되는 곳이 어디다~ 라고 지정!
        //assertThrows(IllegalStateException.class, () -> memeberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memeberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}