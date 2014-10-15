package info.acidflow.waveplay.exceptions.server;

/**
 * Created by paul on 16/10/14.
 */
public class ResponseBuilderException extends  AbstractWavePlayServerException{

    public ResponseBuilderException() {
        super();
    }

    public ResponseBuilderException(String detailMessage) {
        super(detailMessage);
    }

    public ResponseBuilderException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ResponseBuilderException(Throwable throwable) {
        super(throwable);
    }
}
