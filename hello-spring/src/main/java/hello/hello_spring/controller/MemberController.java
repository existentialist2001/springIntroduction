package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//스프링에게 이 클래스는 컨트롤러다! 라고 알려준다고 보면 될 듯
@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {

        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);

        return "redirect:/";
    }

    //이렇게 URL 요청이 들어왔을 때
    @GetMapping("/members")
    public String list(Model model) {

        List<Member> members = memberService.findMembers();
        //model은 일단은 중간자 정도로만 파악하고 있자.
        model.addAttribute("members",members);
        //위의 작업을 하고, 아래 템플릿에 보내준다, 이렇게 보면 될 듯
        return "members/memberList";
    }
}
