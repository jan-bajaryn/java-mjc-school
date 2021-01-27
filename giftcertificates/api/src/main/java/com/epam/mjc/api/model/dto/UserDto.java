package com.epam.mjc.api.model.dto;

import org.springframework.hateoas.RepresentationModel;

public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    private String username;
    private String name;

    public UserDto() {
    }

    public UserDto(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDto userDto = (UserDto) o;

        if (getId() != null ? !getId().equals(userDto.getId()) : userDto.getId() != null) return false;
        if (getUsername() != null ? !getUsername().equals(userDto.getUsername()) : userDto.getUsername() != null)
            return false;
        return getName() != null ? getName().equals(userDto.getName()) : userDto.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
