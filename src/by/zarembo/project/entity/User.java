package by.zarembo.project.entity;

import java.util.Optional;

public class User extends Entity {
    private long userId;
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String password;
    private RoleType role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRole() {
        return role;
    }

    public int getRoleId() {
        return role.ordinal();
    }

    public void setRole(int roleId) {
        Optional<RoleType> roleTypeOptional = RoleType.valueOf(roleId);
        this.role = roleTypeOptional.orElse(RoleType.USER);
    }

    public void setRoleEnum(RoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
