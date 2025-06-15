package ch.etmles.Backend.Data.ResponseAPI;

public class SingleApiResponse<T> {

    private T content;

    public SingleApiResponse(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
