package swagger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
    public class Pet{
        private Integer id;
        private Category category;
        private String name;
        private List<String> photoUrls;
        private List<Tag> tags;
        private String status;
    }
