package ch.etmles.Backend;

public class SingleApiResponse<T> {

    private T content;

    public SingleApiResponse(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
