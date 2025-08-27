package Test.Tools;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Post {
    private int UserId;
    private int id;
    private String title;
    private String body;
}
