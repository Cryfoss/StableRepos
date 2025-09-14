package swagger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@lombok.extern.jackson.Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
    public class Category{
        private Integer id;
        private String name;
    }

