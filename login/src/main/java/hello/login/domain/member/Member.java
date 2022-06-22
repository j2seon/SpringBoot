package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    @NotEmpty //값이 없으면 안됌
    private String loginId; //사용자가 입력한 id
    @NotEmpty
    private String name; //이름
    @NotEmpty
    private String password; //비번

}
