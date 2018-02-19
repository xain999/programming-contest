import java.io.*;
import java.util.*;

public class Solution{
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        int tests = sc.nextInt();
        for(int i=0; i<tests; i++){
            int limit = sc.nextInt();
            int num_topics = sc.nextInt();
            
            boolean check = true;
            int [][] topics = new int [num_topics][3];
            for(int j=0;j<num_topics;j++){
                topics[j][0] = sc.nextInt(); // occurance
                topics[j][1] = sc.nextInt(); // num chars
                if(topics[j][1]<=limit){
                    check = true;
                }
                topics[j][2] = sc.nextInt(); // value
            }
            
            if(!check){
                System.out.println("Case #"+(i+1)+":");
                continue;
            }
            
            //fill table
            int [][] table = new int[limit+1][num_topics+1];
            for(int j=1;j<limit+1;j++){
                //System.out.println("VAL "+j);
                int score = Integer.MIN_VALUE;
                int used_topic=-1;
                for(int k=0;k<num_topics;k++){
                    int index = j-topics[k][1];
                    if(index>=0){
                        if(table[index][num_topics]+topics[k][2]>score &&
                           table[index][k]+1<=topics[k][0] &&
                           table[index][num_topics]+topics[k][2] > table[j-1][num_topics]){
                            score = table[index][num_topics]+topics[k][2];
                            used_topic = k;
                        }
                    }
                }
                
                //System.out.println("used topic "+used_topic+" inc "+inc);
                //update cum sum
                table[j][num_topics] = score;
                //update topic vals
                if(used_topic>=0){
                    int used_chars = topics[used_topic][1];
                    for(int l=0;l<num_topics;l++){
                        table[j][l]=table[j-used_chars][l];
                        if(used_topic==l){
                            table[j][l]++;
                        }
                    }
                }
                else if(used_topic == -1){
                    for(int l=0;l<num_topics+1;l++){
                        table[j][l]=table[j-1][l];
                    }
                }
                else{
                }
            }
            /*
             for(int j=0;j<limit+1;j++){
             for(int k=0;k<num_topics+1;k++){
             System.out.print(table[j][k]+" ");
             }
             System.out.println();
             }
             */
            
            System.out.print("Case #"+(i+1)+": ");
            for(int n=0;n<num_topics;n++){
                if(table[limit][n]>0){
                    for(int p=0;p<table[limit][n];p++){
                        System.out.print((n+1)+" ");
                    }
                }
            }
            System.out.println();    
            
        } 
    }
}
