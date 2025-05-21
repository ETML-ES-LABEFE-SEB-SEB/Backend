package ch.etmles.Backend;

import org.springframework.data.domain.Page;

import java.util.List;

public class ListApiResponse<T> {

    private List<T> content;
    private PaginationInfo pagination;

    public ListApiResponse(List<T> content) {
        this.content = content;
    }

    public ListApiResponse(Page<T> page) {
        this.content = page.getContent();
        this.pagination = new PaginationInfo(page);
    }

    public List<T> getContent() {
        return content;
    }

    public PaginationInfo getPagination() {
        return pagination;
    }

    public static class PaginationInfo {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
        private boolean last;

        public PaginationInfo(Page<?> page) {
            this.page = page.getNumber() + 1;
            this.size = page.getSize();
            this.totalElements = page.getTotalElements();
            this.totalPages = page.getTotalPages();
            this.last = page.isLast();
        }

        public int getPage() { return page; }
        public int getSize() { return size; }
        public long getTotalElements() { return totalElements; }
        public int getTotalPages() { return totalPages; }
        public boolean isLast() { return last; }
    }
}
