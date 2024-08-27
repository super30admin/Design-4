/*

    L.C: 355. Design Twitter

    Approach: Using HashMap - for constan time look up and MinHeap

    Time Complexity: O(1) - postTweet(), follow(), unfollow()
                    O(nk) - getnewsFeed()
    Space Complexity: O(1) - for all methods (getnewsFeed() has a constant space of 10 all time, so not considered )

 */

class Twitter {


    class Tweet {
        int tweetId;
        int createdAt;

        Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }


    HashMap<Integer, HashSet<Integer>> userMap;  //for adding { key -> userId, value -> followers}
    HashMap<Integer, List<Tweet>> tweetMap; //for adding {key -> userId, value -> listofTweets}
    int time; //for adding creation time in each tweet

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
        time = 0;
    }

    public void postTweet(int userId, int tweetId) {

        Tweet newTweet = new Tweet(tweetId, time++);

        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        //adding tweet for a specific user
        tweetMap.get(userId).add(newTweet);

    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId); //for getting the tweets posted by themself i.e the current user for
        //which the tweets are retrived

        //min heap with custom  comparatator based on creation time
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);

        if(userMap.containsKey(userId)) {
            for(int followeeId : userMap.get(userId)) { //O(n) //get all the folllowers for the user

                if(tweetMap.containsKey(followeeId)) {

                    for(Tweet tweet: tweetMap.get(followeeId)) {  //O(k) //get the tweets for the followee

                        pq.add(tweet); //TC O(log 10) -> O(1)

                        if(pq.size() > 10) { //for getting 10 recent tweet i.e getting last 10 large gest value based on created time
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>(); //constant -> any time the size would not go over 10

        while(!pq.isEmpty()) {

            Tweet tweet =  pq.poll();
            /* adding as the first element in the list
               because the most recent would be the leaf node of the min heap
            */
            result.add(0, tweet.tweetId); //ignoring the TC since only 10 elments would be inserted
        }

        return result;


    }

    public void follow(int followerId, int followeeId) {

        if(!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {

        if(!userMap.containsKey(followerId) || followerId == followeeId) {
            return;
        }

        if(userMap.get(followerId).contains(followeeId)) {
            userMap.get(followerId).remove(followeeId);
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