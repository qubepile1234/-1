package optionDialog;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 使用此类时建议自己确定按钮数量，以及特殊按钮数量
 * 创建此类对象时必须指定按钮图标
 * @version 1.0 2025-03-1
 * @author pile
 */

public class buttonarray{//虽说我们起的名字是按钮阵列，不过估计还是做成个jpanel比较好
    private int fi=10;
    private int fj=10;
    //fi fj 是按钮阵列的横纵行
    private ImageIcon scicon;
    private int tempnumber;
    private int sweeper_num;//这个是用来处理sweeper的数量
   // public int button_pic_size;

  private static final int bili_h_little = 9;//扫雷游戏主区的长宽比例
  private static final int bili_w_little = 9;
  private static final int sweeper_num_l=10;
  private static final int bili_h_middle = 16;//扫雷游戏主区的长宽比例
  private static final int bili_w_middle = 16;
  private static final int sweeper_num_m=40;
  private static final int bili_h_high = 16;//扫雷游戏主区的长宽比例
  private static final int bili_w_high = 30;
  private static final int sweeper_num_h=99;

  private boolean reset_panel_flag;

  private int [][] array;//这个array是记录按钮数组背后的信息的
  private boolean no_landmine_click=true;//用这个变量确定没有地雷被用户点击，配合其他数据组成我们判断胜负的标准
    //这个的初始化不好，待会在那个双重循环里初始化一下，有必要查一查为什么

   // private int buttonsize=20;//既然是扫雷游戏按钮就直接正方形就行了//说实话我怀疑这个到底有没有用//一点用都没有
    private int rnumber=10;//地雷number
   //用来产生随机数
   private random_producer producer;//我们还得防止这个随机数字会重复，大概可以先排序一下，插入随机数，顺便防止重复

    //、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
    /**@param sweeper_num 雷的数量 */
    private int [][] array (int sweeper_num){
        Integer temp;
        LinkedList<Integer> sweeper_List = new LinkedList<Integer>();//地雷点//地雷点//地雷点//地雷点
//泛型好像得用类  对象   object   不能用int 这种基本类型    LinkedList<String> sites = new LinkedList<String>();
        for (int i = 0; i < rnumber; i++) {
            producer=new random_producer(fi,fj);//这句话不能放外面
            temp=producer.producer();//自动装箱
            if(sweeper_List.indexOf(temp)==-1)
            {sweeper_List.add(temp);}//如果没有找到，就加上去，如果找到了，就必须得重新来一个随机数
            else {i--;}//重来       
        }
//把一维链表的雷映射为二维数组的数字
         for (int i = 0; i < fi; i++) {
             for (int j = 0; j < fj; j++) {
                if(sweeper_List.indexOf(fj*i+j)==-1)//注意是fj
                { this.array[i][j]=0;}//找不得到就是没有雷     
                else {this.array[i][j]=-1;}//找得到就是有雷，置为-1；
             }
         }
         return array; 
       }
//------------------------------------------------------------------------------------
//设置每个按钮后面的图片，另外如果猜中地雷会弹出失败窗口，如果是0就自动消除周围方块
  private class picture_after_button implements ActionListener{
        private int number; //-1，0，1，2，3，4，5，6，7，8
        String the_pic=".png";    
        JButton theButton;
        JButton[][] buttonindex;
        int i;
        int j;
        //int flag=0;
        //此number为图片名称
        public picture_after_button(int number,int i,int j,JButton[][] buttonindex){
            this.number=number;
            the_pic=number+the_pic;
            this.theButton=buttonindex[i][j];
            this.i=i;
            this.j=j;
            this.buttonindex=buttonindex;
        }
 
        public void auto_delet_zero(){//只有0方块的监听器有必要调用此方法
           // if()
           //我还得判断是不是边界才行
                     if(i!=0&&i!=fi-1)
{
    if(j!=0&&j!=fj-1)  //主体   因为是主体的概率大所以放在第一位
{
    //如果想让button自动消除就得获得它的引用
    buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
    buttonindex[i-1][j].doClick();
    buttonindex[i-1][j+1].doClick();
    buttonindex[i][j-1].doClick();
    buttonindex[i][j+1].doClick();
    buttonindex[i+1][j-1].doClick();
    buttonindex[i+1][j].doClick();
    buttonindex[i+1][j+1].doClick();
    array[i][j]=-1;
    
}
else if(j==0){
   // buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
    buttonindex[i-1][j].doClick();
    buttonindex[i-1][j+1].doClick();
   // buttonindex[i][j-1].doClick();
    buttonindex[i][j+1].doClick();
   // buttonindex[i+1][j-1].doClick();
    buttonindex[i+1][j].doClick();
    buttonindex[i+1][j+1].doClick();
    array[i][j]=-1;
}
else {
    buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
    buttonindex[i-1][j].doClick();
   // buttonindex[i-1][j+1].doClick();
    buttonindex[i][j-1].doClick();
   // buttonindex[i][j+1].doClick();
    buttonindex[i+1][j-1].doClick();
    buttonindex[i+1][j].doClick();
   // buttonindex[i+1][j+1].doClick();
    array[i][j]=-1;
    }
}//左右边
             //下面就是i==0||i==fi-1了
                     else {
                               if(j!=0&&j!=fj-1)//左右边 
{
    if(i==0){
   // buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
   // buttonindex[i-1][j].doClick();
   // buttonindex[i-1][j+1].doClick();
    buttonindex[i][j-1].doClick();
    buttonindex[i][j+1].doClick();
    buttonindex[i+1][j-1].doClick();
    buttonindex[i+1][j].doClick();
    buttonindex[i+1][j+1].doClick();
    array[i][j]=-1;
}
else {
    buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
    buttonindex[i-1][j].doClick();
    buttonindex[i-1][j+1].doClick();
    buttonindex[i][j-1].doClick();
    buttonindex[i][j+1].doClick();
   // buttonindex[i+1][j-1].doClick();
   // buttonindex[i+1][j].doClick();
   // buttonindex[i+1][j+1].doClick();
    array[i][j]=-1;
    }

}
                               else //角
     {
        if(i==0&&j==0){
            //buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
            //buttonindex[i-1][j].doClick();
            //buttonindex[i-1][j+1].doClick();
            //buttonindex[i][j-1].doClick();
            buttonindex[i][j+1].doClick();
            //buttonindex[i+1][j-1].doClick();
            buttonindex[i+1][j].doClick();
            buttonindex[i+1][j+1].doClick();
            array[i][j]=-1;
        }
        else if(i==fi-1&&j==fj-1){
            buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
            buttonindex[i-1][j].doClick();
            //buttonindex[i-1][j+1].doClick();
            buttonindex[i][j-1].doClick();
            //buttonindex[i][j+1].doClick();
            //buttonindex[i+1][j-1].doClick();
            //buttonindex[i+1][j].doClick();
            //buttonindex[i+1][j+1].doClick();
            array[i][j]=-1;
        }
        else if(i==0&&j==fj-1){
            //buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
            //buttonindex[i-1][j].doClick();
            //buttonindex[i-1][j+1].doClick();
            buttonindex[i][j-1].doClick();
            //buttonindex[i][j+1].doClick();
            buttonindex[i+1][j-1].doClick();
            buttonindex[i+1][j].doClick();
            //buttonindex[i+1][j+1].doClick();
            array[i][j]=-1;
        }
        else if(i==fi-1&&j==0){
          //  buttonindex[i-1][j-1].doClick();//只要让0方块自动click并把自己对应的数组改成-1减小不必要的消耗就行了
            buttonindex[i-1][j].doClick();
            buttonindex[i-1][j+1].doClick();
          //  buttonindex[i][j-1].doClick();
            buttonindex[i][j+1].doClick();
          //  buttonindex[i+1][j-1].doClick();
          //  buttonindex[i+1][j].doClick();
          //  buttonindex[i+1][j+1].doClick();
            array[i][j]=-1;
        }
        else System.err.println("出错了呜呜呜  T —— T  ");
     }
}
        }
        public void actionPerformed(ActionEvent event){
        //搞不懂，那按钮什么状态下是select呢，键盘操作吗
        //就奇怪，明明不应该是图片适应按钮吗?为什么不一样，我还得多加个10
        //我有点明白了，因为之前传入的people图标可能影响了button的图片摆放位置，我们在这里重新设置，不还是在myframe里重新设置一下？？？
        theButton.setDisabledIcon(string_to_imageicon(the_pic,this.theButton.getWidth(),this.theButton.getHeight()));
            // icon是接口 imageicon实现了它
            theButton.setEnabled(false);
            if(this.number==-1){
                JOptionPane.showMessageDialog(null, "the game is over", "overgame",JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(null, "you still could control the game ", "overgame",JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(null, "or play a new moddle,or replay the current moddle", "overgame",JOptionPane.ERROR_MESSAGE);    
            //需要能把整个游戏状态重新设置一遍的监听器动作
            //让用户自己设置好了
            no_landmine_click=false;

            }
           else if(this.number==0){
                        auto_delet_zero();//////////////////////
                   }
               }
        //需要实现一个监听者
    }
//------------------------------------------------------------------------------------
//如果赢了这个会弹出胜利窗口
     private class overgame implements ActionListener{
       JButton[][] thebutton;
       int count;//用来计数剩余的方块数，配合no_landmine_click判断用户是否胜利
       public overgame(JButton[][] thebutton){
            this.thebutton=thebutton;
       }
        public void actionPerformed(ActionEvent event){
              this.count=0;
         for(int i=0;i<this.thebutton.length;i++){
            for(int j=0;j<this.thebutton[i].length;j++){
             //////////////////////////////////////  System.err.print("  "+thebutton[i][j].isEnabled());
               if(thebutton[i][j].isEnabled()){this.count++;}
           }
           ////////////////////////////////////////System.err.println("");
         }
         if(this.count==sweeper_num&&no_landmine_click==true){
            JOptionPane.showMessageDialog(null, "you are winnnnnnnnn", "overgame",JOptionPane.ERROR_MESSAGE);
         reset_panel_flag=true;
            }
        }
    }
//---------------------------------------------------------------------------------
/**@return  如果赢了，就需要重置，这个就会return true*/
public boolean  reset_the_panel(){
    boolean reset_panel;//如果赢了，就需要重置，这个就会return true
    reset_panel=reset_panel_flag;
    return reset_panel;
}
//---------------------------------------------------------------------------------
////没有地雷被踩到，利用了之前判断胜利的一个标志的变量,没有地雷被踩到就是true
public boolean no_landmine_click(){
    return no_landmine_click;
}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//------------------------------------------------------------------------------------------
//确定每个按钮背后数组的数字
public void Confirm_other_number(int [][] array){
    for (int i = 1; i < fi-1; i++) {//边角问题好烦  //这个没必要搞多态
        for (int j = 1; j < fj-1; j++) {//边角问题上或许可以试试搞多态，增加一下代码可读性//需要搞两个，不要在
        //一个是正常的，就是center，center就传数据就行了。一个是corner，corner直接处理....不对，还是得多态，不然每个循环还得加上判断语句
        //一个是line，可以巧妙利用fi,fj，这样line里再加一个判断语句就好，line传一个is_five的bool
            if(array[i][j]==-1){
                    continue;//如果是-1就直接跳出好了
                }
               else array[i][j]=help_confirm_num(i, j);
        }
    }
    for(int i=0;i<fi;i=fi-1+i){//天才的想法
           for(int j=0;j<fj;j=fj-1+j){
             if(array[i][j]==-1){
                    continue;//如果是-1就直接跳出好了
                }
          else      array[i][j]=help_confirm_num("is corner",i, j);
           } 
        }
    for(int i=0;i<fi;i=fi-1+i){
        for(int j=1;j<fj-1;j++){
            if(array[i][j]==-1){
                    continue;//如果是-1就直接跳出好了
                }
           else array[i][j]=help_confirm_num(i, j, "is_line");
        }
    }
        for(int j=0;j<fj;j=fj-1+j){
        for(int i=1;i<fi-1;i++){
            if(array[i][j]==-1){
                    continue;//如果是-1就直接跳出好了
                }
           else array[i][j]=help_confirm_num(i, j, "is_line");
        }
    }
         this.array=array;//明天再改吧
}
//--------------------------------------------------------------------------------------------
//下面三个帮忙处理上面那个
//还好array数组是一个可以在这里用的变量/；成员变量的实例变量而不是局部变量
public int help_confirm_num(int i,int j){
                if(array[i-1][j-1]==-1){//没必要再写一个方法减少代码长度，比较调用方法还得用到栈，只会浪费时间
                    array[i][j]++;
                }
                if(array[i-1][j]==-1){//似乎可以在上一段代码的循环时就用indexof确定，但是不知道实际开销怎么样
                    array[i][j]++;//indexof不见得比再加一个二重循环要好
                }
                if(array[i-1][j+1]==-1){
                    array[i][j]++;
                }
                if(array[i][j-1]==-1){
                    array[i][j]++;
                }
                if(array[i][j+1]==-1){
                    array[i][j]++;
                }
                if(array[i+1][j-1]==-1){
                    array[i][j]++;
                }
                if(array[i+1][j]==-1){
                    array[i][j]++;
                }
                if(array[i+1][j+1]==-1){
                    array[i][j]++;
                }
    return array[i][j];
}
//多态自己倒是搞出来了，但是好像并不应该这样利用多态，算了，不管了
//似乎真的不应该用多态
public int help_confirm_num(int i,int j,String is_line){
    if(i==0){
        if(array[i][j+1]==-1)array[i][j]++;
        if(array[i][j-1]==-1)array[i][j]++;
        if(array[i+1][j+1]==-1)array[i][j]++;
        if(array[i+1][j]==-1)array[i][j]++;
        if(array[i+1][j-1]==-1)array[i][j]++;
        return array[i][j];
    }
    if(j==0){
        if(array[i-1][j]==-1)array[i][j]++;
        if(array[i+1][j]==-1)array[i][j]++;
        if(array[i-1][j+1]==-1)array[i][j]++;
        if(array[i][j+1]==-1)array[i][j]++;
        if(array[i+1][j+1]==-1)array[i][j]++;
        return array[i][j];
    }
    if(i==fi-1){
        if(array[i][j+1]==-1)array[i][j]++;
        if(array[i][j-1]==-1)array[i][j]++;
        if(array[i-1][j+1]==-1)array[i][j]++;
        if(array[i-1][j]==-1)array[i][j]++;
        if(array[i-1][j-1]==-1)array[i][j]++;
        return array[i][j];
    }
    if(j==fj-1){
        if(array[i-1][j]==-1)array[i][j]++;
        if(array[i+1][j]==-1)array[i][j]++;
        if(array[i-1][j-1]==-1)array[i][j]++;
        if(array[i][j-1]==-1)array[i][j]++;
        if(array[i+1][j-1]==-1)array[i][j]++;
        return array[i][j];
    }
    System.out.println("zhejuhuabeishiyonglema");
    return 7;
}
//边角处理
public int help_confirm_num(String is_corner,int i,int j){
    if(i==0&&j==0){
        if(array[i][j+1]==-1){array[i][j]++;}
        if(array[i+1][j]==-1){array[i][j]++;}
        if(array[i+1][j+1]==-1){array[i][j]++;}
        return array[i][j];
    }
    if(i==0&&j!=0){
        if(array[i][j-1]==-1){array[i][j]++;}
        if(array[i+1][j]==-1){array[i][j]++;}
        if(array[i+1][j-1]==-1){array[i][j]++;}
        return array[i][j];
    }
    if(i!=0&&j==0){
        if(array[i][j+1]==-1){array[i][j]++;}
        if(array[i-1][j]==-1){array[i][j]++;}
        if(array[i-1][j+1]==-1){array[i][j]++;}
        return array[i][j];
    }
    if(i!=0&&j!=0){
        if(array[i][j-1]==-1){array[i][j]++;}
        if(array[i-1][j]==-1){array[i][j]++;}
        if(array[i-1][j-1]==-1){array[i][j]++;}
        return array[i][j];
    }
    return 8;//防止出现奇怪的错误备用，其实这个句子是不会被用到的
}
//-------------------------------------------------------------------------------------------------
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//--------------------------------------------------------------------------------------
//很怀疑这个到底有没有用
//安置方块图像
    public buttonarray(ImageIcon scicon) {
        this.scicon=scicon;
    }
//----------------------------------------------------------------------------------
//构造方法    
    public buttonarray(int fi,int fj,int sweeper_num,ImageIcon scicon){
      this.fi=fi;
      this.fj=fj;
      this.scicon=scicon;
      this.sweeper_num=sweeper_num;
      this.array=new int [fi][fj];/////////////////////
    }       
//---------------------------------------------------------------------------------------------------------
////改变模式
     public void change_moddle(String moddle){
          if(moddle=="high"){fi=bili_h_high;fj=bili_w_high;sweeper_num=sweeper_num_h;
          //this.setbuttonsize(10); 
          this.setrnumber(sweeper_num_h);  this.array=new int [fi][fj];}
          else if(moddle=="middle"){fi=bili_h_middle;fj=bili_w_middle;sweeper_num=sweeper_num_m;
          //this.setbuttonsize(15);
           this.setrnumber(sweeper_num_m);  this.array=new int [fi][fj];}
          else {fi=bili_h_little;fj=bili_w_little;sweeper_num=sweeper_num_l;
          //this.setbuttonsize(20);
           this.setrnumber(sweeper_num_l);  this.array=new int [fi][fj];}
          //这个设置buttonsize一点用都没有，button的size好像是根据窗口设置的//真一点用都没有
     }
//---------------------------------------------------------------------------------------------------
/////这个有用到，是我们设置模式时设置相应的地雷数量时用到的
    void setrnumber(int num){
        this.rnumber=num;
    }
 //-----------------------------------------------
 ////本想用来设置改变游戏图片topic的，但是还是不做了，时间也不够
   public void exchangeicon(ImageIcon a_icon){
    this.scicon=a_icon;
   }
//----------------------------------------------------------------------------------------------
private picture_after_button listen;
//......................................
////这个文件中的主要的东西
    public JPanel butary(){//真是有点搞不懂java的参数传递机制，我们现在做的大概是返回一个jpanel的引用？
     JButton[][]buttonindex=new JButton [fi][fj];
     overgame over=new overgame(buttonindex);
     reset_panel_flag=false;

        producer=new random_producer(fi,fj);
        JPanel buttonpanel=new JPanel(new GridLayout(fi,fj));//这一步不能忽略，我因为忘记创造实例导致程序无法运行
        //现在array已经变成我们想要的array了
      this.array=array(sweeper_num);
     Confirm_other_number(this.array);//现在array已经变成我们想要的array了//现在array已经变成我们想要的array了//现在array已经变
     no_landmine_click=true;//[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
        for (int i = 0; i< fi; i++) {        //创建一个专门生产阵列按钮的类
                /////////////                                  System.err.println("");
            for (int j = 0; j < fj; j++) {      
                ////////////                                   System.err.print("这是"+array[i][j]);
      buttonindex[i][j]=new JButton(scicon);//////////这个先搁置一下
     // buttonindex[i][j]= new JButton(string_to_imageicon("people.jpg",buttonindex[i][j].getWidth(),buttonindex[i][j].getHeight()));
     //System.err.print(""+buttonindex[i][j].getWidth()+"+"+buttonindex[i][j].getHeight());//这个打印出来全是0，看来按钮的尺寸是
     //设置完按钮后确定的
       //buttonindex[i][j].setSize(buttonsize,buttonsize);
       
       this.listen=new picture_after_button(this.array[i][j],i,j,buttonindex);
       buttonindex[i][j].addActionListener(over);
       buttonindex[i][j].addActionListener(this.listen);
       buttonpanel.add(buttonindex[i][j]);
            }
        }
        return buttonpanel;
    }
//---------------------------------------------------------------------------------------------------------
//基础的设置图片，用来辅助那个picture after button
/**  @param picname  文件名字，大概是数字，我们贴心的加了.png后缀
     @param  imageh 图片高度
     @return 返回一个缩放的图片
 */
private ImageIcon string_to_imageicon(String picname,int imagew,int imageh){
    BufferedImage image = null;//获取图片路径
               try {  //image = ImageIO.read(new File("picture\\"+"people"+".jpg"));
                       image = ImageIO.read(new File("picture\\"+picname));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
         Image scaledImage = image.getScaledInstance(imagew,imageh, Image.SCALE_FAST);//SCALE_SMOOTH应该是一个常量
         //Image scaledImage = image.getScaledInstance(imagew,imageh, Image.SCALE_FAST);//SCALE_SMOOTH应该是一个常量
       ImageIcon   scaledIcon = new ImageIcon(scaledImage);    //scaled意为缩放的
        //建立一个类，输入原来的image，和横纵比例参数，返回一个新的image
                return scaledIcon;
          }
}
//----------------------------------------------------------------------------------------------------------------------