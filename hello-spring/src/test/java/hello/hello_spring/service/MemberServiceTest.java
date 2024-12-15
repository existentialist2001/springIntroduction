package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    //MemberService memberService = new MemberService();
    /*memberService로는 저장소에 직접 접근해서 clear할 수 없으니 따로 불러온다..? 근데 저장소가 다른데..?
    MemoryMemberRepository 객체는 다르지만, 그 안에 참조하고 있는 static 변수는 동일하므로, 데이터 공유가 가능한 것*/
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {

        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        /*이렇게 함으로써 기존에는 손가락이 두 개 였는데, 하나로 연결되었다.*/
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    //검증할 함수를 안에 넣는다.
    @Test
    void 회원가입() {

        //given
        Member member = new Member();
        member.setName("hello");
        
        //when
        //그러니까 이게 검증할 함수인 거임
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        //내가 생성하고 넣은 멤버랑, 저장소에서 가져온 멤버랑 같냐
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

/*        try {
            memberService.join(member2);
            //fail(); 이거 왜 안되냐
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}