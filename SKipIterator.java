import java.util.*;
class SkipIterator implements Iterator<Integer>{
    Iterator<Integer> it;
    HashMap<Integer,Integer> map;
    Integer nextEle;
    public SkipIterator(Iterator<Integer> it){
        this.it = it;
        map = new HashMap<>();
        advance();
        
    }
    public boolean hasNext(){
        return nextEle != null;
    }
    public Integer next(){
        Integer result = nextEle;
        advance();
        return result;
    }
    public void skip(Integer val){
        if(map.containsKey(val)){
            map.put(val, map.get(val)+1);
        }else{
            map.put(val,1);
        }
    }
    private void advance(){
        while (this.nextEle == null && it.hasNext()){
            Integer ele = it.next();
            if(map.containsKey(ele)){
                map.put( ele, map.get(ele)-1);
                map.remove(ele,0);
            }else{
                nextEle = ele;
            }
        }
    }
    
}
public class HelloWorld{

     public static void main(String []args){
        SkipIterator itr = new SkipIterator(Arrays.asList(2,3,3,4,5,6,7,8).iterator());
        System.out.println(itr.hasNext());
        itr.skip(2);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        itr.skip(5);
        System.out.println(itr.next());
        
        
     }
}