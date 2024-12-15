package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//스프링부트 컨테이너와 함께 테스트를 실행함
@SpringBootTest

//이걸 명시해줌으로써 한번 테스트 후 db를 롤백해줌, 그래서 지우는 코드를 넣지 않고 다음 테스트를 실행할 수 있음
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    //검증할 함수를 안에 넣는다.
    @Test
    void 회원가입() {

        //given
        Member member = new Member();
        member.setName("hello");
        
        //when
        //그러니까 이게 검증할 함수인 거임, 여기서 중복검사
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        //내가 생성하고 넣은 멤버랑, 저장소에서 가져온 멤버랑 같냐, 여기서는 진짜 db랑 연결되어 같은지 판단
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    //회원 가입 시 중복을 걸러내는 로직이 잘 작동하는 지 검증하기 위해, 의도적으로 중복 데이터 주입
    @Test
    public void 중복_회원_예외() {

        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}