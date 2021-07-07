# Design-4

## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

//      Time complexity-
//      follow, unfollow, postTweet-O(1)
//      getnewsfeed-O(nlog(k)) as using min where k = 10 
//      Space complexity-O(num users)

class Twitter {
    class Tweet{
        int tid;
        int createdAt; 
        
        public Tweet(int tid, int createdAt){
            this.tid = tid; 
            this.createdAt = createdAt;
        }
    }
    
    HashMap<Integer, Set<Integer>> followed; 
    HashMap<Integer, List<Tweet>> tweets; 
    int time; 
    
    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap<>(); 
        tweets = new HashMap<>(); 
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        Set<Integer> fids = followed.get(userId); 
        if(fids != null){
            for(int fid: fids){
                List<Tweet> ftweets = tweets.get(fid); 
                if(ftweets != null){
                    for(Tweet ftweet: ftweets){
                        pq.add(ftweet);
                        if(pq.size() > 10){
                            pq.poll(); 
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>(); 
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tid);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId); 
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId){
            Set<Integer> set = followed.get(followerId);
            if(set.contains(followeeId)){
                set.remove(followeeId);
            }
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */


## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

## Time Complexity = O(n)
## Space Complexity = O(n)

Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.

class SkipIterator implements Iterator<Integer> {

	private Iterator<Integer> it; 
	private Integer nextEl; 
	private HashMap<Integer, Integer> map; 

	public SkipIterator(Iterator<Integer> it) {
		this.map = map; 
		this.it = it; 
		advance(); 
	}

	public boolean hasNext() {
		return nextEl != null; 
	}

	public Integer next() {
		Integer temp = nextEl; 
		advance(); 
		return temp; 
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		if(val == nextEl){
			advance();
		}else{
			map.put(val, map.getOrDefault(val,0) + 1);
		}
	}

	private void advance(){
		nextEl = null; 
		if(nextEl == null && it.hasNext){
			Integer el = it.next(); 
			if(!map.containsKey(el)){
				nextEl = el; 
			}else{
				map.put(el, map.get(el) - 1);
				map.remove(el, 0); 
			}
		}
	}
}
Example:

SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
itr.hasNext(); // true
itr.next(); // returns 2
itr.skip(5);
itr.next(); // returns 3
itr.next(); // returns 6 because 5 should be skipped
itr.next(); // returns 5
itr.skip(5);
itr.skip(5);
itr.next(); // returns 7
itr.next(); // returns -1
itr.next(); // returns 10
itr.hasNext(); // false
itr.next(); // error

