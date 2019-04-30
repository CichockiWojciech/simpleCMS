package pl.model.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Avatar {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Min(50)
    @Max(50)
    private int width;

    @NotNull
    @Min(75)
    @Max(75)
    private int height;

    @Lob
    @NotNull
    private byte[] image;
}
