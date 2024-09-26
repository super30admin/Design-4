import java.util.*;

public class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private final Map<Integer,Integer> map;
    private  Integer cursor;


    SkipIterator(Iterator<Integer> iterator){
    this.iterator = iterator;
    map = new HashMap<>();
    seekCursor();

    }

    public boolean hasNext(){
    return cursor!=null;
    }

    public Integer next(){
    Integer value = cursor;
    seekCursor();
    return value;
    }

    public void skip(int val){
    if(val==cursor){
      seekCursor();
    }
    else{
        if(!map.containsKey(val)){
            map.put(val,0);
        }
        else{
            map.put(val,map.get(val)+1);
        }
    }
    }

    public void seekCursor(){
    cursor=null;

    while(iterator.hasNext()){
        Integer tmp = iterator.next();
        if(!map.containsKey(tmp)){
            cursor=tmp;
            break;
        }
        else{
            if(map.get(tmp)>0){
                map.put(tmp,map.get(tmp)-1);
            }
            else{
                map.remove(tmp);
            }
        }
    }
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(2,3,5,6,5,7,5,-1,5,10);
        Iterator iterator = list.iterator();
        SkipIterator skipIterator = new SkipIterator(iterator);
        System.out.println(skipIterator.hasNext());
        System.out.println(skipIterator.next());
        skipIterator.skip(5);
        System.out.println(skipIterator.next());
        System.out.println(skipIterator.next());
        System.out.println(skipIterator.next());
        skipIterator.skip(5);
        skipIterator.skip(5);
        System.out.println(skipIterator.next());
        System.out.println(skipIterator.next());
        System.out.println(skipIterator.next());
        System.out.println(skipIterator.hasNext());





    }
}
