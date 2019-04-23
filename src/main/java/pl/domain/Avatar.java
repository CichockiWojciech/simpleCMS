package pl.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Avatar {

    @Id
    @GeneratedValue
    private long id;

    private int width;
    private int height;

    @Lob
    private byte[] image;
}
