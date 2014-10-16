package info.acidflow.waveplay.exceptions.file;

/**
 * Created by paul on 16/10/14.
 */
public class FileNotOnExternalStorageException extends AbstractWavePlayFileException {

    public FileNotOnExternalStorageException() {
        super();
    }

    public FileNotOnExternalStorageException(String detailMessage) {
        super(detailMessage);
    }

    public FileNotOnExternalStorageException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FileNotOnExternalStorageException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public void handleException() {
        super.handleException();
    }
}
