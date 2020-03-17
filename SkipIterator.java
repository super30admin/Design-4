// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode :YES
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach

class SkipIterator implements Iterator<Integer> {
   
  final Iterator<Integer> iterator;
  final HashMap<Integer,Integer> skipcount;
  private  Integer cursor;
public SkipIterator(Iterator<Integer> iterator) {
      this.iterator=iterator;
      this.skipcount=new HashMap<>();
      seekcursor();
}

public boolean hasNext() {
   return cursor!=null;   
}

public Integer next() {
      Integer element=cursor;
      seekcursor();
      return element;
}
  
  public void seekcursor(){
      cursor=null;
      while(iterator.hasNext()){
          Integer element=iterator.next();
          if(!skipcount.containsKey(element)){
             cursor=element; 
              break;
          }else{
              skipcount.put(element,skipcount.get(element)-1);
              skipcount.remove(element,0);
          }
      }
  }

/**
* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
*/ 
public  void skip(int val) {
      if(cursor==val){
         seekcursor();
      }else{
          if(!skipcount.containsKey(val)){
               skipcount.put(val,1);
          }else{ skipcount.put(val,skipcount.get(val)+1);}
    
      }
    }
  
  public static void main(String[] args){
      List<Integer> lst= Arrays.asList(2,3,4,5,-1,0,4,5);
      Iterator iterator=lst.iterator();
      SkipIterator skipIterator=new SkipIterator(iterator);
      System.out.println(skipIterator.hasNext());
      System.out.println(skipIterator.next());
      skipIterator.skip(5);
  }
}