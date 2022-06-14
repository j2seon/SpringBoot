package com.example.demo.repository;


import com.example.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 저장된 회원
    Optional<Member> findById(Long id); // 아이디로 회원 찾음.
    Optional<Member> findByName(String name);// 이름
    // Optional > 만약 아이디가 이름이 없다면 null 로 반환되게 되는데 이때 Optional 로 감싸서 반환하겠다>> null을 그대로 반환하는 것보다 선호됨.
    List<Member> findAll(); // 저장된 반환체 전부를 반환한다.


}
