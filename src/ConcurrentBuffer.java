import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ConcurrentBuffer<T> {

    // A buffer that supports multiple objects accessing it at once. When the
    // contents of the buffer want to be modified they need permission from the
    // Semaphore "bufferKey".

    private Queue<T> buffer = new LinkedList<T>();
    private Semaphore bufferKey = new Semaphore(1);

    private void acquireBufferKey() {
        try {
            bufferKey.acquire();
        } catch (InterruptedException e) {
            System.out.println(
                    "Error: code's critical section has been accessed simultaneously from two threads.");
        }
    }

    private void releaseBufferKey() {
        bufferKey.release();
    }

    public void addToBuffer(T object) {
        acquireBufferKey();
        if (object != null) {
            buffer.add(object);
        }
        releaseBufferKey();
    }

    public T getHead() {
        acquireBufferKey();
        T temp = buffer.poll();
        releaseBufferKey();
        return temp;
    }

}
