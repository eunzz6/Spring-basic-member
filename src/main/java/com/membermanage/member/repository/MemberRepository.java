package com.membermanage.member.repository;

import com.membermanage.member.dto.MemberDTO;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    public int save(MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        return 0;
    }
}
