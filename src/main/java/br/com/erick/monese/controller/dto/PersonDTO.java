package br.com.erick.monese.controller.dto;

public class PersonDTO {

    private String suid;
    private String name;

    public PersonDTO() {

    }

    public PersonDTO(String suid, String name) {
        this.suid = suid;
        this.name = name;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
