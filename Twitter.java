
// Time Complexity : O(N*log k), N = total number of tweets from user's followers. k = 10
// Space Complexity :O(n)
// Did this code successfully run on Leetcode : yes


class Twitter {

    private Map<Integer, Set<Integer>> followedMap;
    private Map<Integer, List<Tweet>> tweetsMap;
    private int time;

    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); //this is important because when posting a tweet, we need to follow ourself to see our tweet in the feed
        if(!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, time));
        time++;
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        Queue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
        Set<Integer> followees = followedMap.get(userId);
        if(followees != null) {
            for(Integer followee: followees) {
                List<Tweet> fTweets= tweetsMap.get(followee);
                if(fTweets != null) {
                    for(Tweet fTweet: fTweets) {
                        pq.add(fTweet);
                        if(pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        while(!pq.isEmpty()) {
            res.add(0, pq.poll().tweetId);
        }

        return res;
    }

    public void follow(int followerId, int followeeId) {
        if(!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followedMap.containsKey(followerId) && followerId != followeeId) {
            followedMap.get(followerId).remove(followeeId);
        }
    }

    private static class Tweet{
        int tweetId;
        int time;
        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
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