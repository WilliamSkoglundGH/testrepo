package com.skoglund.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.skoglund.entity.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MemberRegistry {
    private ObservableList<Member> memberList;

    public MemberRegistry() throws IOException {
        convertListToObservable();
    }

    public void addMember(Member member){
        memberList.add(member);
    }

    public ObservableList<Member> getMemberList(){
        return memberList;
    }

    public void saveMemberListToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(new File("members.json"), memberList);
    }

    public List<Member> loadMembersFromFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("members.json");
        if(!jsonFile.exists() || jsonFile.length() == 0){
            mapper.writeValue(jsonFile, new ArrayList<>());
        }
        List<Member> fromFile = Arrays.asList(mapper.readValue(new File("members.json"),
                Member[].class));
        return fromFile;
    }

    public void convertListToObservable() throws IOException {
        this.memberList = FXCollections.observableArrayList(loadMembersFromFile());
    }

    public Member searchAndReturnMember(String id){
            return memberList.stream().filter(member-> member.getId().equals(id))
                    .findAny().orElse(null);
    }
}
