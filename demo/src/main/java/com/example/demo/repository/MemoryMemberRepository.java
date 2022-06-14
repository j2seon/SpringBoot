package com.example.demo.repository;

import com.example.demo.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    // 저장을할때 묶어서 저장해야되겠지? 아이디/회원 이렇게 저장하자 >> 그럼 아이다 찾으면 회원나오겠네.
    private static Map<Long,Member> store = new HashMap<>(); //실무에서는 동시성문제가 있을 수 있어서 hashMap을 사용하지 않음.
    // 시퀀스는 0,1,2, 이렇게 키값을 생성하는것이다.
    private static long sequence=0L;


    @Override
    public Member save(Member member) { // 멤버 저장
        member.setId(++sequence); //맴버를 저장할때 시퀀스값을 하나씩 올리겠다. // 하나씩올라간 값이 id로 지정된다.
        store.put(member.getId(),member); //스토어에 키값 과 맴버 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //return store.get(id); // 이렇게하면 null이 반환될 수 있다. 따라서 Optional을 사용.
        return Optional.ofNullable(store.get(id)); // 애가 null 이여도 감쌀수 있게된다. 이렇게하면 클라이어늩에서 해당내용으로 조치할 수 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // store에 있는 value 값을 돌려서 
                .filter(member -> member.getName().equals(name)) //파라미터로 온값과 같은지 확인한다.
                .findAny(); // 있으면 반환한다 없으면 ? Optional로 감싸서 반환하겟징
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
