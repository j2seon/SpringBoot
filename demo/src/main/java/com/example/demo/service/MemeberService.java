package com.example.demo.service;

import ch.qos.logback.classic.net.server.HardenedLoggingEventInputStream;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemeberService {

    //회원서비스를 만들려면 repository 가 있어야된다/
    private final MemberRepository memberRepository ;

    public MemeberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    //회원가입 >> 가입하면 id를 반환하겠다~
    public Long join(Member member){
        //같은 이름이 있는 중복회원이 안되도록 !
        //1. 이름을 찾고
        //Optional<Member> result = repository.findByName(member.getName());
        validateDuplicate(member);
        // 2. 이름있다면
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        //꺼내고 싶으면 result.get(); >> 바로꺼내는 건 권장하지 않는다.
        //+ 추가로result.orElseGet(); 으로 값이 있으면 꺼내! 라고도 할 수 있다.
        //++ 추가 Optional을 바로반환하는 것보다 바로 반환히는 것으로  바꿔주자!!

        memberRepository.save(member);
        return member.getId();
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberid){
        return memberRepository.findById(memberid);
    }



    //중복회원
    private void validateDuplicate(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                    throw new  IllegalStateException("이미 존재하는 회원입니다.");
                }); // 길어지는 로직나오면 메서드로 빼주는 것이 좋다.
    }



}
