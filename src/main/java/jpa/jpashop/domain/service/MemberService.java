package jpa.jpashop.domain.service;

import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional(readOnly = false) // readonly = false 가 디폴트라서 생략가능
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findmember = memberRepository.findByName(member.getName());

        if (!findmember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        //EXCEPTION
    }

    // 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
