package data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PetData {
    private int id;
    private String name;
    private Category category;
    private List<String> photoUrls;
    private List<Tags> tags;
    private String status;


    @Builder
    @Data
    public static class Category {
        private int id;
        private String name;
    }


    @Builder
    @Data
    public static class Tags {
        private int id;
        private String name;
    }

}
