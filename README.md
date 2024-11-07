# Design-4

## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)


Approached Solution: In this solution we are designing a basic Twitter system which enables users to post tweets, follow/unfollow other users, and retrieve a news feed. The Twitter class maintains two main data structures - followedMap which tracks followers and followees, and tweetsMap which stores each user's tweets as a list. When a user posts a tweet, the system records it with a timestamp for ordering. The getNewsFeed method retrieves the most recent 10 tweets from the user's feed, including tweets from followed users, using a min-heap to efficiently manage and return the latest tweets in decending order.

Time Complexity: For postTweet it's O(1) for adding a tweet, for getNewsFeed it's O(nlog10) where n is total number of tweets across followed users, and for follow/unfollow it's O(1) for updating the follow relationship.

Space Complexity: O(U + T) where U is the number of users stored in followedMap and T is the total number of tweets stored in tweetsMap.

Approached Code:

class Twitter {

    class Tweet {
        private int id;
        private int createAt;

        public Tweet(int tweetId, int time) {
            this.id = tweetId;
            this.createAt = time;
        }
    }

    private HashMap<Integer, HashSet<Integer>> followedMap;
    private HashMap<Integer, List<Tweet>> tweetsMap;
    private int time;

    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        
        follow(userId, userId);

        if(!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }

        Tweet newTweet = new Tweet(tweetId, time);
        time++;

        tweetsMap.get(userId).add(newTweet);

    }
    
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((Tweet a, Tweet b) -> {
            return a.createAt - b.createAt;
        });
        HashSet<Integer> followed = followedMap.get(userId);

        if(followed != null) {
            for(Integer fid: followed) {
                List<Tweet> fTweets = tweetsMap.get(fid);
                
                if(fTweets != null) {
                    int size = fTweets.size();

                    for(int i = size-1; i >= 0 && i >= size-11; i--) {
                        Tweet currTweet = fTweets.get(i);
                        pq.add(currTweet);
                        
                        if(pq.size() > 10) {
                            pq.poll();
                        }
                    }
                    // for(Tweet fTweet: fTweets) {
                    //     pq.add(fTweet);
                    //     if(pq.size() > 10) {
                    //         pq.poll();
                    //     }
                    // }
                }
            }
        }

        List<Integer> result = new ArrayList<>();

        while(!pq.isEmpty()) {
            result.add(0, pq.poll().id);
        }

        return result;

    }
    
    public void follow(int followerId, int followeeId) {

        if(!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<Integer>());
        }

        followedMap.get(followerId).add(followeeId);

    }
    
    public void unfollow(int followerId, int followeeId) {
        
        if(followedMap.containsKey(followerId) && followerId != followeeId) {
            followedMap.get(followerId).remove(followeeId);
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


Approached Solution: The SkipIterator class extends the functionality of a standard iterator to allow skipping specific elements. It maintains a map to keep track of elements that should be skipped and their counts. The advance() method is responsible for finding the next valid element by checking if the current element is in the skip map. If an element is to be skipped its count in map is decreased and it’s removed if the count reaches zero. The skip() method either advances to the next element if it matches the skip value or increments the skip count in map for future occurrences of that value.

Time Complexity: For next method it's O(n) if multiple elements need to be skipped continuously. For hasNext and skip methods it's O(1).

Space Complexity: O(k) where k is the number of unique elements being skipped, as these are stored in the map.

Approached Code:

Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.

// "static void main" must be defined in a public class.

class SkipIterator implements Iterator<Integer> {
    
    private Iterator<Integer> nit;
    private HashMap<Integer, Integer> map;
    private Integer nextEl;

	public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.map = new HashMap<>();
        advance(); 
	}
    
    private void advance() {
        nextEl = null;
        
        while(nextEl == null && nit.hasNext()) {
            Integer el = nit.next();
            
            if(map.containsKey(el)) {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }

	public boolean hasNext() {
        
        return nextEl != null;
        
	}

	public Integer next() {
        
        Integer result = nextEl;
        advance();
        return result;
        
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
            
        if(nextEl == val) {
            advance();
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
        
	}
}

public class Main {
    public static void main(String[] args) {
//         SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());

//          System.out.println(it.hasNext());

//          it.skip(2);

//          it.skip(1);

//          it.skip(3);

//          System.out.println(it.hasNext());
        
        
        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());

         System.out.println(it.hasNext());
         it.skip(5);
        System.out.println(it.next());
         it.skip(5);
        System.out.println(it.next());
        System.out.println(it.next());
         it.skip(8);
        it.skip(9);
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
         System.out.println(it.hasNext());
        it.skip(8);
        System.out.println(it.hasNext());
		System.out.println(it.next());
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

