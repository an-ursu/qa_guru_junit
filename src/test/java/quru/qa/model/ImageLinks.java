package quru.qa.model;

import java.util.List;

public class ImageLinks {

    public List<String> getSmall() {
        return small;
    }

    public void setSmall(List<String> small) {
        this.small = small;
    }

    public List<String> getNormal() {
        return normal;
    }

    public void setNormal(List<String> normal) {
        this.normal = normal;
    }

    private List<String> small;
    private List<String> normal;
}
