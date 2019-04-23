package pl.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "USER")
@SecondaryTable(name = "COLOR", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class User implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    @Column(unique = true, nullable = false, length = 50)
    private String login;
    @Column(nullable = false)
    private String password;

    @OneToOne
    private Avatar avatar;

    @OneToMany
    private List<Content> contents;

    @Column(table = "COLOR", nullable = false)
    private String headerColor;
    @Column(table = "COLOR", nullable = false)
    private String contentColor;
    @Column(table = "COLOR", nullable = false)
    private String linkColor;
    @Column(table = "COLOR", nullable = false)
    private String asideColor;
    @Column(table = "COLOR", nullable = false)
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
        this.headerColor = "#ccc";
        this.contentColor = "#ccc";
        this.linkColor = "#ccc";
        this.asideColor = "#ccc";
        this.footerColor = "#ccc";
    }

    public long getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContentColor() {
        return contentColor;
    }

    public void addContent(Content content){
        contents.add(content);
    }

    public List<Content> getContents() {
        return contents;
    }

    public String getContent(String tile){
        return contents.stream().filter((content) -> content.getTitle().equals(tile))
                .map(Content::getContent).findFirst().orElse("");
    }
}
