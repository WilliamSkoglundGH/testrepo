package com.skoglund.repository;

import com.skoglund.entity.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberRegistry {
    private final List<Member> memberList;

    public MemberRegistry(){
        memberList = new ArrayList<>();
    }

    public void addMember(Member member){
        memberList.add(member);
    }
}
