package optionDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/** 
 * @version 1.0 2025-03-1
 * @author pile
 */

//如果要实现不同的扫雷模式就必须得把各种参数提前设置好，封装一下，然后根据用户的选项提供预参数传值，如size.mini size.middle size.max
//action

public class myframe extends JFrame{
  private static final int bili_h_little = 9;//扫雷游戏主区的长宽比例
  private static final int bili_w_little = 9;
  private static final int sweeper_num_l=10;
  private static final int bili_h_middle = 16;//扫雷游戏主区的长宽比例
  private static final int bili_w_middle = 16;
  private static final int sweeper_num_m=40;
  private static final int bili_h_high = 16;//扫雷游戏主区的长宽比例
  private static final int bili_w_high = 30;
  private static final int sweeper_num_h=99;

  private buttonarray abuttonpanel;
  private int count_of_the_game;
  
  private  int fi ;
  private  int fj ;
  private final int fnum ;
  private JPanel gamePanel;
  private JPanel messagepanel;
  private JPanel controlpanel;
  private JButton controlbutton;

    public static JLabel message;
  
    private Image scaledImage ;
    private ImageIcon scaledIcon ;   //scaled意为缩放的       icon是一个接口，imageicon实现了它
    static BufferedImage image = null;//获取图片路径

    private int landmine_number=10;

           private  change_panel little_moddle=new change_panel("little");
           private  change_panel middle_moddle=new change_panel("middle");
           private  change_panel high_moddle=new change_panel("high");

   JButton button;

  public myframe(){  
//---------------------------------------------------------------------------
//设置菜单条
    count_of_the_game=1;//这个是打印的数字messagepanel
    
    var menubar=new JMenuBar();
    this.setJMenuBar(menubar);
    var editmenu=new JMenu("choose moddle");
    menubar.add(editmenu);
    JMenuItem little=editmenu.add("little:09*09");
    JMenuItem middle=editmenu.add("middle:16*16");
    JMenuItem high  =editmenu.add("high  :30*16");
//---------------------------------------------------------------------
//这一段设置窗口
    Image window_icon=string_to_image("people.jpg", 20, 20);
    this.setIconImage(window_icon);
//这一段代码似乎还真不是想删就删的，因为窗口一开始就应该设置大小的
    int window_width = 640, window_height = 640;//初始化游戏窗口大小
    this.setSize(window_width,window_height);
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;//获取屏幕宽度
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;//获取屏幕高度
    this.setLocation((width - window_width) / 2, (height - window_height) / 2);//把窗口放中间
    this.setResizable(false); //窗口不可更改 ，因为一旦改变按钮比例就不好了，我们先预订几个模式好了
    this.setVisible(true); //窗口是否显示=是
    this.setIconImage(image);
//---------------------------------------------------------------------------------
//设置最上方north的信息panel
          messagepanel=new JPanel();
          messagepanel.setBackground(Color.GREEN);
          message = new JLabel("attack left button make game begenning");
           //message紧随时事
          messagepanel.add(message);
          add(messagepanel,BorderLayout.NORTH);
//========================================================================================================================下面是设置按纽阵列
    fi=bili_h_little;
    fj=bili_w_little;//用i 和 j 形成最终数字   ,final i  final j         fi   ji是按钮的数量final  
    fnum=sweeper_num_l;
    scaledIcon= string_to_imageicon("people.jpg",40,30);
    
    //这里是第一次有abuttonpanel
    abuttonpanel = new buttonarray(fi,fj,fnum,scaledIcon);
    abuttonpanel.setrnumber(10);//这个看起来可以用一个变量名代替，但是实际上如果这样做是不明智的
    gamePanel=abuttonpanel.butary();
    abuttonpanel.exchangeicon(scaledIcon);
    gamePanel.setLayout(new GridLayout(fi,fj));//设置中央panel
//==============================================================================据说有一个双缓冲技术防止屏幕闪烁，但我们目前或者目前加上以后估计暂时用不到
           //这个是people.jpg 是左边那个按钮

//---------------------------------------------------------------------------
scaledIcon= string_to_imageicon("people.jpg",40,40);

controlbutton = new JButton(scaledIcon);
controlbutton.setBounds(880,20,2900,100);//??????????????????????????????????????????????为什么好像不起作用的样子
//这。。。。。。。。。。。。。。。。。。。
controlbutton.setEnabled(true);

//-------------------------------------------------------------------------
//设置西边主要部分panel
            controlpanel =new JPanel();
            controlpanel.add(controlbutton);
            add(controlpanel,BorderLayout.WEST);
           /* button_west_listener listen_west=new button_west_listener();
            controlbutton.addActionListener(listen_west);  */
            //这一段是不得不删的，因为会引起那个controlbutton改变模式时改变错误，似乎是个奇怪的bug，大概是引起了
            //什么冲突，似乎是只能变成第一次启动应用是按下controlbutton时的模式，应该是变量无法更改，所以只能用个计时器了
//------------------------------------------------------------------------
//设置最下方的按钮图片  这个在之后的东西里有设置图标
            button=new JButton("hello");
            add(button,BorderLayout.SOUTH);
//------------------------------------------------------------------------
             //change_panel little_moddle=new change_panel("little");
            // change_panel middle_moddle=new change_panel("middle");
            // change_panel high_moddle=new change_panel("high");
             little.addActionListener(little_moddle);
             middle.addActionListener(middle_moddle);
             high.addActionListener(high_moddle);
             
     Timer timer=new Timer(1000,new reset_panel_listener());
     timer.start();
     Timer timer2=new Timer(1000,new button_west_listener());
     timer2.start();
  }
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//这个得配合那个内部类才行，改变窗口大小，虽然我们没有自定义模式，但是有三个模式，所以是有必要保留这个的
    private void change_window_size(int window_width,int window_height){//游戏窗口大小
        this.setSize(window_width,window_height);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;//获取屏幕宽度
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;//获取屏幕高度
        this.setLocation((width - window_width) / 2, (height - window_height) / 2);//把窗口放中间
    }
//--------------------------------------------------------------------------------------------------------
//根据字符串建立一个被设置好大小的image或imageicon
        private ImageIcon string_to_imageicon(String picname,int imagew,int imageh){
               try {
                        image = ImageIO.read(new File("picture\\"+picname+""));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
          scaledImage = image.getScaledInstance(imagew,imageh, Image.SCALE_SMOOTH);//SCALE_SMOOTH应该是一个常量
          scaledIcon = new ImageIcon(scaledImage);    //scaled意为缩放的
        //建立一个类，输入原来的image，和横纵比例参数，返回一个新的image
                return scaledIcon;
          }
//。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
        private Image string_to_image(String picname,int imageh,int imagew){
               try {
                        image = ImageIO.read(new File("picture\\"+picname+""));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
        return image;
          }
          //改变最下面图片的宽度，然后每次改变//？？？？？真能改变吗
           // void set_deep_pic_size(int wide,int high){ this.panelTop.setpic_size(wide,high);}
//----------------------------------------------------------------------------------------------------------
   String moddle_now;//揭示现在的moddle,主要服务于下面的下面的类，但是需要change_moddle的协助
   String pic="flower1.jpg";//勉强用用1 来初始化吧。。。
//。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
//改变模式的一个内部类监听者
     private class change_panel implements ActionListener{
        private String moddle;     
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;//获取屏幕宽度
        //int height = Toolkit.getDefaultToolkit().getScreenSize().height;//获取屏幕高度
        public change_panel(String moddle){
            this.moddle=moddle; 
        }
        private GridLayout patch_grid_lay_out(String moddle){
           // moddle_now=moddle;
            set_the_moddle_now(moddle);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!reset_panel_listener好像要用
            //System.err.println(""+get_the_moddle_now()+moddle);
            if(moddle=="high"){
                change_window_size(1280, 640);
              //  set_deep_pic_size(1280+200,640);
                //button.setIcon(string_to_imageicon("flower3.jpg", width,120));
                pic="flower3.jpg";
                button.setIcon(string_to_imageicon("flower3.jpg", button.getWidth(),100));
               // button.setIcon(string_to_imageicon(pic, button.getWidth(),100));
                return new GridLayout(16,30);}
            else if(moddle=="middle"){
                change_window_size(800,800);
                pic="flower2.jpg";
              //  button.setIcon(string_to_imageicon("flower2.jpg", button.getWidth(),100));
                return new GridLayout(16,16);}
            else {
                change_window_size(480,480);
                pic="flower1.jpg";
               // button.setIcon(string_to_imageicon("flower1.jpg", button.getWidth(),100));
                return new GridLayout(9,9);}
        }
        public void actionPerformed(ActionEvent event){
            remove(gamePanel);
            abuttonpanel.change_moddle(moddle);
            gamePanel=abuttonpanel.butary();
            gamePanel.setLayout(patch_grid_lay_out(moddle));//?这个有必要吗？？？？？？？？？？？？？？？？？？？？？
            //真有必要
            add(gamePanel,BorderLayout.CENTER);
           // System.err.println(""+gamePanel.getWidth()+"+"+gamePanel.getHeight());全是0

            messagepanel.remove(message);
            message = new JLabel("game begenning"+count_of_the_game);//重置了这个
            count_of_the_game++;
            messagepanel.add(message);
            flag=0;//这个flag是为了反应message的
        }
    }   
//----------------------------------------------------------------------------------------------------
int flag;
//这个和一个timer一起用，不听监听是不是盈利，如果赢了，就需要重置，用这个类，顺便加上一个设置最下面的那个无模式状态的图片，因为可以
//不停监视游戏状态
    private class reset_panel_listener implements ActionListener{

        change_panel reset=new change_panel(moddle_now);
           public void actionPerformed(ActionEvent event){
            //这个if赢了才会动
               if(abuttonpanel.reset_the_panel()){
                reset.actionPerformed(event);     
               }//可以直接在外面设置一个东西一直不停检查是不是这个，如果是就调用
            //change_panel，反正moddle也没有改变，直接重置好了

            if(abuttonpanel.no_landmine_click()==false&&flag==0){//有雷被点击了
            messagepanel.remove(message);
            message = new JLabel("T -  T");//重置了这个
            messagepanel.add(message);
            
            remove(messagepanel);
            add(messagepanel,BorderLayout.NORTH);
            flag++;
            }
            repaint();//都是不可缺的-------------------------
            revalidate();//都是不可缺的----------------------
            button.setIcon(string_to_imageicon(pic, button.getWidth(),100));
            set_the_moddle_now(moddle_now);//之前我们的moddle_now获取不及时，现在让我们时时刻刻监听这个东西
           }
    }   
    ////服务于moddle_now
        public String get_the_moddle_now(){
            return moddle_now;
        }
    ////服务于moddle_now
        private void set_the_moddle_now(String moddle_now){
            this.moddle_now=moddle_now;
        }
////就是设置西边那个按钮的监听器
private class button_west_listener implements ActionListener{
    change_panel the_moddle=little_moddle;
    void reset_the_west_moddle(){
        if(get_the_moddle_now()=="little"){
           the_moddle=little_moddle;
          // System.err.println("little");
        }
        else if(get_the_moddle_now()=="middle"){
            the_moddle=middle_moddle;
           // System.err.println("middle");
        }
        else if(get_the_moddle_now()=="high"){the_moddle=high_moddle;//System.err.println("high");
        }
        else {the_moddle=little_moddle;//System.err.println("error");
        }
    }
    public void actionPerformed(ActionEvent event){  
        controlbutton.removeActionListener(the_moddle);//不知道为什么一开始controlbutton没有监听者用这个却不会报错，大概是因为这是一个友好的设计吧
        reset_the_west_moddle();
        controlbutton.addActionListener(the_moddle);
      //  System.err.println(""+get_the_moddle_now()+" "+moddle_now);
    }
}
}
       