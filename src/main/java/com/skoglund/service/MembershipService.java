package com.skoglund.service;

import com.skoglund.entity.Member;
import com.skoglund.repository.MemberRegistry;

import java.io.IOException;
import java.util.Optional;

public class MembershipService {
    private MemberRegistry memberRegistry;

    public MembershipService(){

    }

    public MembershipService(MemberRegistry memberRegistry){
       this.memberRegistry = memberRegistry;
    }

    public void addNewMember(Member member) {
        memberRegistry.addMember(member);
    }

    public void deleteMemberFromFile(){

    }

    public void saveChangesToFile() throws IOException {
        memberRegistry.saveMemberListToFile();
    }

    public Member searchAndGetMember(String id){
        return memberRegistry.searchAndReturnMember(id);
    }

    public void changeMemberInfo(){

    }
}
