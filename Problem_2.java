// Time Complexity :O(n)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach
  //store the inorder traversal of and then find out the two nodes and swap them.

package Practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SkipIterator implements Iterator<Integer>{
	 HashMap<Integer,Integer> hash;
	 Integer nextElement;
	 Iterator<Integer> it;
	public SkipIterator(Iterator<Integer> it) {
		this.hash=new HashMap<>();
		this.nextElement=0;
		this.it=it;
		advance();
		
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return nextElement!=null;
	}

	@Override
	public Integer next() {
		// TODO Auto-generated method stub
		Integer result=nextElement;
		advance();
		return result;
	}
	
	public void skip(Integer val) {
		if(nextElement!=val) {
			if(hash.containsKey(val)) {
				hash.put(val,hash.get(val)+1);
			}else {
				hash.put(val,1);
			}
		}else {
			advance();
		}
	}
	
	public void advance() {
		nextElement=null;
		while(nextElement==null && it.hasNext()) {
			int element=it.next();
			if(hash.containsKey(element)) {
				hash.put(element, hash.get(element)-1);
				hash.put(element,0);
			}else {
				nextElement=element;
			}
		}
	}
	
	public static void main(String[] args) {
		List<Integer> list=new ArrayList<>();
		list.add(2);
		list.add(2);
		list.add(3);
		list.add(5);
		list.add(6);
		list.add(5);
		list.add(8);
		list.add(5);
		Iterator<Integer> it=list.iterator();
		SkipIterator sk=new SkipIterator(it);
		
		System.out.println(sk.hasNext());
		sk.skip(2);
		sk.skip(2);
		sk.skip(3);
		System.out.println(sk.next());
		sk.skip(5);
		System.out.println(sk.next());
		System.out.println(sk.next());
		sk.skip(5);
		System.out.println(sk.next());
	}

}
