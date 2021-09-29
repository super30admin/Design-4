// Time Complexity: O(1) for both follow and unfollow users, O(1) for creating tweet, O(nlog10) -> O(n) for get top 10 posts
// Space Complexity: O(number of users*number of tweets)+O(number of users* number of average followees per user)
// Write your approach here
// Idea here is to use Map of key as followers to Set of users who that user follows
// on follow we take the follower key's value and add new user in followee list
// on unfollow take the follower key's value and remove provided userid from followee list
// on post get list of tweets done by user and add new tweet (id and time) to list
// to get most recent 10 feeds
// get all the people follower follows including themselves
// make a max heap according to time and once we reach size of 10 we keep removing old once and adding new tweets
// once all tweets from all followees are finished, we return the list of tweets (id)
class Twitter {
    
    class Tweets {
        int tweetId;
        int createdAt;
        
        Tweets(int tweetId, int time) {
            this.tweetId = tweetId;
            this.createdAt = time;
        }
    }
    
    int time;
    Map<Integer, HashSet<Integer>> following;
    Map<Integer, List<Tweets>> posts;
    public Twitter() {
        following = new HashMap<>();
        posts = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!posts.containsKey(userId)) {
            posts.put(userId, new ArrayList<>());
        }
        posts.get(userId).add(new Tweets(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweets> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> fids = following.get(userId) ;
        if(fids!=null) {
            for(Integer fid: fids) {
                List<Tweets> feedTweets = posts.get(fid);
                if(feedTweets!=null) {
                    for(Tweets currTweet: feedTweets) {
                        pq.add(currTweet);
                        if(pq.size()>10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)) following.put(followerId, new HashSet<>());
        following.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(following.containsKey(followerId) && followerId!=followeeId) {
            following.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<String> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */