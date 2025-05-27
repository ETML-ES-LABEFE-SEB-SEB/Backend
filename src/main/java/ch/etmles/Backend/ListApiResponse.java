package ch.etmles.Backend;

import java.util.List;

public class ListApiResponse<T> {

    private List<T> content;

    public ListApiResponse(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

}
