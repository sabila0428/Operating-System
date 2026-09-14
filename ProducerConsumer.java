import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class ProducerConsumer{
    private static final int buffer_size = 5;
    private static final int [] buffer = new int[buffer_size];
    private static int in = 0, out = 0, freq = 0;
    private static final Lock lock = new ReentrantLock();
    private static final Condition notFull = lock.newCondition();
    private static final Condition notEmpty = lock.newCondition();

    public static void main(String[] args){
        Thread producer = new Thread(ProducerConsumer::producer);
        Thread consumer = new Thread(ProducerConsumer::consumer);

        producer.start();
        consumer.start();
    }

    private static void producer(){
        while(true){
            int item = (int) Math.random() * 100; //item produced;

            lock.lock();
            try{
                while(freq == buffer_size)
                    notFull.await();
                
                buffer[in] = item;
                System.out.println("Produced: " + item + " at " + in);

                in = (in +1) % buffer_size;
                freq++;

                notEmpty.signal(); // buffer has item
            }

            catch(InterruptedException e){
                e.printStackTrace();
            }
            finally{
                lock.unlock();
            }

            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private static void consumer(){
        while(true){
            lock.lock();
            try{
                while(freq == 0) // when buffer is empty
                    notEmpty.await();
                
                int item = buffer[out];
                System.out.println("Consumed: " + item + " at " + out);

                out = (out+1) % 10;
                freq--;

                notFull.signal();   // buffer now has space
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            finally{
                lock.unlock();
            }

            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}