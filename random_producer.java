package optionDialog;

import java.util.Random;

/** 
 * @version 1.0 2025-03-1
 * @author pile
 */
////这是一个产生随机数的类，两个构造方法，产生范围[1,max-1||fi*fj-1],用producer得到int型随机数
public class random_producer {   //似乎是不需要产生实例，所以用类变量就行了？感觉还是用实例变量好了，因为每次要用都需要设计一个max数，
//正好创建的时候就直接觉得好了
    private int max=9;
    private int min=1;//和数组一样以0开始，减少歧义概念//这个随机数方法会产生min 到max-1,so min should be 1
    /**@param fi  和fj一起组成max生成的min为①   max为fi*fj-1 */
    public random_producer(int fi,int fj) {
       this.max=fi*fj-1;
    }
      private void modifiedmax(int max){
         this.max=max-1;
      }
       public int producer() {
          long randomNum = System.currentTimeMillis();
         //  long ran = (long) (randomNum % (max - min) + min); //循环同一时间会产生相同的数
          Random rand = new Random(randomNum);
          return rand.nextInt(max-min+1)+min;
}
}

