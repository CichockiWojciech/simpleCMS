package pl.model.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Data
@Entity
public class Avatar implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Range(min = 130, max = 130, message = "szerokosc 130")
    private int width;

    @NotNull
    @Range(min = 130, max = 130, message = "szerokosc 130")
    private int height;

    @Lob
    @NotNull
    private byte[] image;

    public Avatar() {
    }

    public Avatar(@NotNull @Min(130) @Max(130) int width, @NotNull @Min(130) @Max(130) int height, @NotNull byte[] image) {
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public String toString(){
        byte[] encodeBase64 = Base64.getEncoder().encode(image);
        try {
            return new String(encodeBase64, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
