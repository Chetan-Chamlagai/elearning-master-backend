package com.e_learning.payloads;

import java.util.List;

import lombok.Data;

@Data
public class UserFacultyDto {


	private int id;
	private String name;
    private List<String> facult;
    private List<String> roles;
    private String imageName;
    public UserFacultyDto(int id, String name, List<String> facult, List<String> roles,String imageName) {
        this.id = id;
        this.facult = facult;
        this.roles = roles;
        this.name=name;
        this.imageName=imageName;
    }
}
