package ch.etmles.Backend.Data.ResponseAPI;

public class SinglePageApiResponse<T> {

    private T content;
    private PaginationInfo pagination;

    public SinglePageApiResponse(T content, PaginationInfo pagination) {
        this.content = content;
        this.pagination = pagination;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public PaginationInfo getPagination() {
        return pagination;
    }

    public void setPagination(PaginationInfo pagination) {
        this.pagination = pagination;
    }
}
