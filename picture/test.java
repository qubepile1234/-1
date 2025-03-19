package picture;

public class test{
    random_producer producer =new random_producer(1,30);
    private int ran =producer.producer();
    public static void main(String[] args) {
        
           //int ran =producer.producer();
           test tmd;
           //tmd=new test();
           for (int i = 0; i <100; i++) {
               tmd=new test();//有最大，有最小，想要有随机就需要创建新的对象，所以这句话不能删除
               //新建了test就有了新的producer    ran
                tmd.non();
           }
           System.out.println("");
         
    }
    public void non(){
       System.out.print(this.ran+"  "); 
    }
}