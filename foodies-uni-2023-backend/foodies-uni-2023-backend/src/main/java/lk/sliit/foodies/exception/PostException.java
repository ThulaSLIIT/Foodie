package lk.sliit.foodies.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostException extends RuntimeException {
    private int status;
    private String msg;

    public PostException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public PostException(int status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }
}
