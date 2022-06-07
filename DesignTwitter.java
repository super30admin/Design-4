// Time and space complexities - mentioned below on each funcs

class Twitter {
    // tweet obj
    class Tweet {
        int id;
        int createdAt;
        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    // map on user to tweets relationship
    Map<Integer, List<Tweet>> tweets;
    // map on user to followees relationship
    Map<Integer, Set<Integer>> follows;
    // dummy time
    int time;
    public Twitter() {
        tweets = new HashMap<>();
        follows = new HashMap<>();
        time = 0;
    }

    public void postTweet(int userId, int tweetId) { // Time: O(1)
        // when follower posts, he should be able to see his own posts on his feed as well,
        follows.putIfAbsent(userId, new HashSet<>());
        // so self-subscription happens here
        follows.get(userId).add(userId);
        // if user has not posted anything before,
        // we create an entry into tweets dict
        tweets.putIfAbsent(userId, new ArrayList<>());
        // then add the tweet along with the dummy timestamp
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) { // Time: O(No of subscribers * No of tweets with each subscribers * log 10) + O(All the collected tweets) | Space: O(10) - as we are maintaining only top 10
        List<Integer> result = new ArrayList<>();
        // if user does not have any followees list yet, there's nothing to retrieve on the wall
        if(!follows.containsKey(userId)) return result;
        // if user has followees list, then we are going through tweets dict
        // gather all the tweets
        // maintaining a minHeap to handle the top 10 recent tweets
        Set<Integer> followings = follows.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt-b.createdAt);
        for(Integer following: followings) {
            List<Tweet> totalTweets = tweets.get(following);
            if(totalTweets != null) {
                for(Tweet curr: totalTweets) {
                    pq.add(curr);
                    if(pq.size() > 10)
                        pq.poll();
                }
            }
        }
        // once we have the top 10 recent tweets,
        // we are supposed to display them in descending order, as it was minHeap
        // we keep adding the tweets to the result, placing every entry on the first postition
        while(!pq.isEmpty()) {
            result.add(0, pq.poll().id);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) { //Time: O(1)
        // if user does not have any entries on followees list yet,
        // then we ought to create one and make him subscribe himself to see his future posts on wall
        if(!follows.containsKey(followerId)) {
            follows.put(followerId, new HashSet<>());
            follows.get(followerId).add(followerId);
        }
        // add new followees
        follows.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) { // Time: O(1)
        // an user can not unsubscribe his own id
        // he can unsubscrible only others
        if(followerId != followeeId && follows.containsKey(followerId)) {
            Set<Integer> followings = follows.get(followerId);
            if(followings != null)
                followings.remove(followeeId);
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
