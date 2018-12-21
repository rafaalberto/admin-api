package br.com.api.admin.entity;

import br.com.api.admin.enumeration.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = User.TABLE_NAME)
public class User implements Serializable {

    private static final long serialVersionUID = -4803349741695893029L;

    static final String TABLE_NAME = "users";
    private static final String SEQUENCE_NAME = "seq_users";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @NotBlank(message = "error-user-1")
    @Size(min = 5, max = 20, message = "error-user-2")
    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @NotBlank(message = "error-user-3")
    @Size(min = 6, max = 100, message = "error-user-4")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotBlank(message = "error-user-5")
    @Size(min = 5, max = 50, message = "error-user-6")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull(message = "error-user-7")
    @Column(name = "profile", nullable = false, length = 2)
    private ProfileEnum profile;

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

    @JsonIgnore
    @JsonProperty(value = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileEnum getProfile() {
        return profile;
    }

    public void setProfile(ProfileEnum profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                profile == user.profile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, name, profile);
    }
}
