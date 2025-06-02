package ch.etmles.Backend.ResponseAPI;

import org.springframework.data.domain.Page;

import java.util.List;

public class ListPageApiResponse<T> {

    private List<T> content;
    private PaginationInfo pagination;

    public ListPageApiResponse(List<T> content) {
        this.content = content;
    }

    public ListPageApiResponse(Page<T> page) {
        this.content = page.getContent();
        this.pagination = new PaginationInfo(page);
    }

    public ListPageApiResponse(List<T> content, PaginationInfo pagination) {
        this.content = content;
        this.pagination = pagination;
    }

    public List<T> getContent() {
        return content;
    }

    public PaginationInfo getPagination() {
        return pagination;
    }
}
