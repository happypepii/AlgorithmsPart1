import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int count = 0;

        while(!StdIn.isEmpty()){
            String item = StdIn.readString();
            count++;

            if(queue.size() < k){
                queue.enqueue(item);
            }else{
                int randomIndex = StdRandom.uniformInt(0, count);
                if (randomIndex < k) {
                    queue.dequeue();
                    queue.enqueue(item);
                }
            }
        }

        for(String item : queue){
            System.out.println(item);
        }
        
    }
 }
