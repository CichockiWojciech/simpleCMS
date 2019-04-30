package pl.model.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "USER")
@SecondaryTable(name = "COLOR", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class User implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Size(min = 5, max = 80)
    private String firstName;

    @NotNull
    @Size(min = 5, max = 80, message = "nieprawidlowa dlugosc nazwiska")
    private String lastName;

    @NotNull
    @Size(min = 5, max = 80)
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 5, max = 80)
    @Column(unique = true)
    private String login;

    @NotNull
    @Size(min = 5, max = 80)
    private String password;

    @OneToOne
    private Avatar avatar;

    @OneToMany
    private List<Content> contents;

    @Column(table = "COLOR", nullable = true)
    @Size(min = 7, max = 7)
    private String headerColor;

    @Column(table = "COLOR", nullable = true)
    @Size(min = 7, max = 7)
    private String contentColor;

    @Column(table = "COLOR", nullable = true)
    @Size(min = 7, max = 7)
    private String linkColor;

    @Column(table = "COLOR", nullable = true)
    @Size(min = 7, max = 7)
    private String asideColor;

    @Column(table = "COLOR", nullable = true)
    @Size(min = 7, max = 7)
    private String footerColor;

    public User() {
    }

    public User(String firstName, String lastName, String email, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = password;

        this.contents = new ArrayList<>();
        this.headerColor = "#cccccc";
        this.contentColor = "#cccccc";
        this.linkColor = "#cccccc";
        this.asideColor = "#cccccc";
        this.footerColor = "#cccccc";
    }

    public void addContent(Content content){
        contents.add(content);
    }

    public String getContentByTitle(String tile){
        return contents.stream().filter((content) -> content.getTitle().equals(tile))
                .map(Content::getContent).findFirst().orElse("");
    }
}