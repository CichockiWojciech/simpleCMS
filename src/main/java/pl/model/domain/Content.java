package pl.model.domain;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
public class Content implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotNull
    @Size(min = 5, max = 120)
    private String title;

    @Column(nullable = false)
    @Type(type = "text")
    @NotNull
    @Size(min = 5, max = 50000)
    private String text;

    public Content() {
    }

    public Content(@NotNull @Size(min = 5, max = 120) String title, @NotNull @Size(min = 5, max = 50000) String text) {
        this.title = title;
        this.text = text;
    }
}
