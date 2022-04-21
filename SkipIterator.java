import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
/*
Time Complexity: O(n) for hasNext() method, O(1) for both next(), and skip()
Space Complexity: O(n) using HashSet and List

Run on leetcode: yes
Any difficulties: no

Approach: **Attempted after discussed in the class**
 */
public class SkipIterator implements Iterator {

    List<Integer> list;
    int cursor = 0;
    int max;
    HashSet<Integer> skipIndices = new HashSet<>();

    SkipIterator(List<Integer> list) {
        this.list = list;
        max = list.size();
    }

    @Override
    public boolean hasNext() {
        if (cursor >= max) return false;
        for (int i = cursor; i < max; i++) {
            if (!skipIndices.contains(i)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if (hasNext()) {
            while (skipIndices.contains(cursor)) {
                cursor++;
            }
            return list.get(cursor++);
        } else {
            throw new RuntimeException("Empty");
        }

    }

    public void skip(int val) {
        for (int i = cursor; i < max; i++) {
            if (list.get(i) == val) {
                skipIndices.add(i);
                break;
            }
        }
    }
}
