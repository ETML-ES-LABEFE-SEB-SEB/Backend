package ch.etmles.Backend.ResponseAPI;

public class SingleApiResponse<T> {

    private T content;

    public SingleApiResponse(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
