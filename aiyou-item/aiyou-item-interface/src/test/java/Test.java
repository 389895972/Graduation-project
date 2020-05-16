import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.ArrayList;
import java.util.Iterator;
public class Test {
     public static void main(String[] args) {
          System.out.println(f(3));
     }
     public static int f(int n){
          if(n==0){
               return 0;
          }
          if(n==1||n==2){
               return n;
          }
          int f1=1;
          int f2=1;
          int count=3;
          while (count++<=n){
               int t=f1;
               f1=f2;
               f2=t+f2;
          }
          ArrayList<Integer> list=new ArrayList<>();
          Iterator<Integer> iterator = list.iterator();
          while (iterator.hasNext()){
               if (iterator.next()==3){
                    iterator.remove();
               }
          }
          return f2;
     }

}
