# Design-4

## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

// Time Complexity - O(1)
// Space Complexity - O(N) number of tweets and users

class Twitter {
class Tweet {
int tweetId;
int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }

    }

    /** Initialize your data structure here. */
    Map<Integer, List<Tweet>> tweetMap;
    Map<Integer, Set<Integer>> followeeMap;
    int timestamp;
    public Twitter() {
        tweetMap = new HashMap<>();
        followeeMap = new HashMap<>();
        timestamp = 0;
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        Tweet tweet = new Tweet(tweetId, timestamp++);
        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId , new ArrayList<>());
        }
        tweetMap.get(userId).add(tweet);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> queue = new PriorityQueue<>((a,b) -> (a.createdAt - b.createdAt));
        Set<Integer> followed = followeeMap.get(userId);

        if(followed != null) {
            for(Integer fId : followed) {
                List<Tweet> tweets = tweetMap.get(fId);
                if(tweets != null) {
                    for(Tweet tweet : tweets) {
                        queue.add(tweet);

                        if(queue.size() > 10) {
                            queue.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!queue.isEmpty()) {
            result.add(0, queue.poll().tweetId);
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        //new follower
        if(!followeeMap.containsKey(followerId)) {
            followeeMap.put(followerId, new HashSet<>());
        }
        //add to followerId
        followeeMap.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followeeMap.containsKey(followerId) && followerId != followeeId) {
            followeeMap.get(followerId).remove(followeeId);
        }
    }

}

/\*\*

- Your Twitter object will be instantiated and called as such:
- Twitter obj = new Twitter();
- obj.postTweet(userId,tweetId);
- List<Integer> param_2 = obj.getNewsFeed(userId);
- obj.follow(followerId,followeeId);
- obj.unfollow(followerId,followeeId);
  \*/

## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

// Time Complexity - O(1)
// Space Complexity - O(N) where n is number of elements

class SkipIterator implements Iterator<Integer> {
HashMap<Integer, Integer> skipMap;
Iterator<Integer> it;
Integer nextElement;
public SkipIterator(Iterator<Integer> it) {
skipMap = new HashMap();
this.it = it;
nextElement = null;
advance();
}

    public boolean hasNext() {
        return nextElement != null;
    }

    public Integer next() {
        Integer temp = nextElement;
        advance();
        return temp;
    }

    /**
    * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
    * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    */
    public void skip(int val) {
        if(val == nextElement) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val,0)+1);
        }
    }
    private void advance() {
        nextElement = null;
        while(nextElement == null && it.hasNext()) {
            Integer element = it.next();
            if(!skipMap.containsKey(element)) {
                nextElement = element;
            } else {
                skipMap.put(element,skipMap.get(element)-1);
                if(skipMap.get(element) == 0) {
                    skipMap.remove(element);
                }
            }
        }
    }

}

public class Main {
public static void main(String[] args) {
List<Integer> list = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
Iterator it = list.iterator();
SkipIterator itr = new SkipIterator(it);
System.out.println(itr.hasNext()); // true
System.out.println(itr.next()); // returns 2
itr.skip(5);
System.out.println(itr.next()); // returns 3
System.out.println(itr.next()); // returns 6 because 5 should be skipped
System.out.println(itr.next()); // returns 5
itr.skip(5);
itr.skip(5);
System.out.println(itr.next()); // returns 7
System.out.println(itr.next()); // returns -1
System.out.println(itr.next()); // returns 10
System.out.println(itr.hasNext()); // false

    }

}
