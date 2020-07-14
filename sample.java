// Time Complexity : O(n) as for Heap the complexity becomes nklogk but k=10 always i.e feedsize
// Space Complexity : O(n*max(f,t)) where n is no of users having f followers and t tweets on average
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : None

class Twitter {
    class Tweet { // tweet class
        int id; // tweetid
        int timestamp; // when tweet was created

        public Tweet(int id, int timestamp) {
            this.id = id;
            this.timestamp = timestamp;
        }
    }

    /** Initialize your data structure here. */
    Map<Integer, HashSet<Integer>> followed; // to map the userid to set of people he follows(unique)
    Map<Integer, List<Tweet>> tweets; // to map the userid to list of tweets made by him
    int timestamp; // to keep track of the last tweet timestamp
    int feedSize = 10; // recent no of tweets to be returned

    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); // user has to follow itself
        if (!tweets.containsKey(userId)) { // check if the userid is not there
            tweets.put(userId, new ArrayList<>()); // add the userid
        }

        tweets.get(userId).add(new Tweet(tweetId, timestamp++)); // get the userid and add to it a new tweet with
                                                                 // tweetid and updated timestamp
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
     * the news feed must be posted by users who the user followed or by the user
     * herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> (t1.timestamp - t2.timestamp)); // heap with sorted
                                                                                                  // tweets based on
                                                                                                  // timestamp
        HashSet<Integer> fIds = followed.get(userId); // get the followed people of user

        if (fIds != null) {
            for (Integer fId : fIds) { // iterate on followedids
                List<Tweet> fTweets = tweets.get(fId); // get the tweets for each followedid
                if (fTweets != null) {
                    for (Tweet fTweet : fTweets) { // iterate through the tweets
                        pq.add(fTweet); // add to priority queue

                        if (pq.size() > feedSize) // if feedsize crossed
                            pq.poll(); // remove and the heapify of heap will put the least recent at top to be removed
                                       // next
                    }
                }

            }
        }

        List<Integer> list = new ArrayList<>(); // result
        while (!pq.isEmpty()) { // till heap becomes empty
            Tweet tId = pq.poll(); // get the tweet out (ascending order)
            list.add(0, tId.id); // we have to give most recent first so always put to the start of list
        }

        return list;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void follow(int followerId, int followeeId) {

        if (!followed.containsKey(followerId)) // if followed map does not contains the user
            followed.put(followerId, new HashSet<>()); // add the user

        followed.get(followerId).add(followeeId); // add the followed person to list of followed
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void unfollow(int followerId, int followeeId) {

        if (followerId != followeeId) { // the user cannot unfollow himself
            if (followed.containsKey(followerId))
                followed.get(followerId).remove(followeeId); // remove the follweeid
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such: Twitter obj =
 * new Twitter(); obj.postTweet(userId,tweetId); List<Integer> param_2 =
 * obj.getNewsFeed(userId); obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */

// Time Complexity : O(n) as we are iterating over the list once
// Space Complexity : O(n) // storing n number of elements to be skipped in
// hashmap
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : None

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map; // to store the element to be skipped and its count
    Iterator<Integer> it; // native iterator
    Integer nextEl; // stores the next element to be returned

    public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        advance(); // call advance and find the next element
    }

    public boolean hasNext() {
        return nextEl != null; // if nextel is not null, then value is present
    }

    public Integer next() {
        int temp = nextEl; // store the value
        advance(); // find the next element
        return temp; // return the stored value
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped. This method can be called multiple times in a row.
     * skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (val == nextEl) // if our value to be skipped is equal to the next value
            advance(); // call advance and move to next
        else
            map.put(val, map.getOrDefault(val, 0) + 1); // else put the value to be skipped in map and increase the
                                                        // count if already there
    }

    private void advance() {
        nextEl = null; // initially null

        while (nextEl == null && it.hasNext()) { // we will iterate the list using the iterator til there is a next
                                                 // element and we havent found a next el
            int el = it.next(); // get the element
            if (map.containsKey(el)) { // check if map contains the element, skip it right here
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            } else
                nextEl = el; // if not we found the next element
        }
    }

}

public class Main {

    public static void main(String[] args) {

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

     }

}