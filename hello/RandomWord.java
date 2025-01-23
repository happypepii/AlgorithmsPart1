package hello;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null; // 儲存當前的候選單字
        int count = 0;          // 已讀取的單字數目

        // 當標準輸入不為空時，不斷讀取單字
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            count++;

            // 使用機率 1/count 更新候選單字
            if (StdRandom.bernoulli(1.0 / count)) {
                champion = word;
            }
        }

        // 輸出最終選中的單字
        if (champion != null) {
            StdOut.println(champion);
        }
    }
}
