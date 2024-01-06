package com.template.sprbootapi.data.constants;

public enum RoleTypes {
	
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_ANONYMOUS("ROLE_ANONYMOUS");

    private String stringValue;

    RoleTypes(String stringValue) {
        this.stringValue = stringValue;
    }
    
    public String getStringValue(){
    	return this.stringValue;
    }

}