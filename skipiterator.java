/*
Time Complexity: O(1)
Space Complexity: O(N)
*/
public class SkipIterator {
Map<Integer, Integer> store;
Long Nextval;
Iterator it;

SkipIterator(Iterator<Integer> it){
    store = new Hasstoreap<>();
    Nextval = Long.MAX_VALUE;
    this.it = it;
}

public boolean hasNext(){
    if(Nextval != Long.MAX_VALUE){
        return true;
    }
    else {
        try{
        Nextval = next();
        hasNext();
        }
        catch(Exception e){
            return false;
        }
    }
    return false;
}

public int next(){
    if(Nextval != Long.MAX_VALUE){
        int temp = Nextval;
        Nextval = Long.MAX_VALUE;
        return temp;
    }
    else if(Nextval == Long.MAX_VALUE){
        if(it.hasNext()){
            int current = it.next();
            if(store.containsKey(current) && store.get(current) != 0){
                store.put(current, store.get(current)-1);
                next();
            }
            return current;
        }
    }
    else {
        return new IndexOutOfBound();
    }
}

public void skipValue(int val){
    store.put(val, store.getOrDefault(val, 0)+1);
}
}