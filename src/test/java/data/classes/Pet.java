package data.classes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pet {
    private int id = 10;
    private String name = "test pet";
    private String status = "available";
}
