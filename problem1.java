
// Time Complexity : O(nlogk) for gets news feed
// Space Complexity :O(nk) n - user - number of tweets
// Did this code successfully run on Leetcode :yes
// Any problem you faced while coding this :i a still fuzzzy about the OOP design

/*
 * We are using a tweet class to store tweets
 * a followed hashmap to keep track of who is following whoem
 * and a tweetsmap to store userid and its tweets
 * 
 * Fistly for follow first need to check if the user and followed someone of not before
 * if not create a new entery
 * add the followeeID to followed
 * 
 * For unfollow
 * we check if the followed map has the id or not and followerID != followeeId(not to unfollow self);
 * 
 * For postTweet
 * first make the user follow self
 * the check if tweetmap contain the userid(check is the userhas tweeted before)
 * add the new tweet obj to the tweet map and incease the time
 * 
 * for getNewsFeed
 * we make a min heap to the get the latest tweets
 * we use the uerID to get the list of id that user is following
 * then check tweet of each one get the lastest 10 tweets the user has done and push them to queue
 * if the queue size is bigger then 10 remove the peek
 * 
 * eventually by doing this we will have this we will have the 10 lastest tweets and we return them   
 */

class Twitter {
    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweetsMap;
    int time;

    public Twitter() {
        this.followed = new HashMap<>();
        this.tweetsMap = new HashMap<>();

    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, time));
        time++;
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followeds = followed.get(userId);
        if (followeds != null) {
            for (int fid : followeds) {
                List<Tweet> fTweets = tweetsMap.get(fid);
                if (fTweets != null) {
                    int le = fTweets.size();
                    for (int i = le - 1; i >= 0 && i >= le - 11; i--) {
                        Tweet fTweet = fTweets.get(i);
                        pq.add(fTweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);

    }

    public void unfollow(int followerId, int followeeId) {
        if (followed.containsKey(followerId) && followerId != followeeId) {
            followed.get(followerId).remove(followeeId);
        }
    }
}