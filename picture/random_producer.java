package  picture;

import java.util.Random;

public class random_producer {
    private int max;
    private int min;
    public int producer() {
           long randomNum = System.currentTimeMillis();
         //  long ran = (long) (randomNum % (max - min) + min); //循环同一时间会产生相同的数
           Random rand = new Random(randomNum);
           return rand.nextInt(max-min+1)+min;
    }

    /**
 * @param min  这个min是生成的随机数就包含min
 * @param max  这个max是生成的随机数就包含max
 */
    public random_producer(int min,int max) {
        this.max=max;
        this.min=min;
    }
    
}
