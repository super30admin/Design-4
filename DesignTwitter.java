// Time Complexity : O()
// Space Complexity : O()
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : None

public class DesignTwitter {
    class Twitter {

        class Tweet{
            int timeStamp;
            int tweetId;

            public Tweet(int id, int time){
                this.tweetId = id;
                this.timeStamp = time;
            }
        }

        Map<Integer, Set<Integer>> userMap;
        Map<Integer, List<Tweet>> tweetMap;
        int time;

        public Twitter() {
            this.userMap = new HashMap<>();
            this.tweetMap = new HashMap<>();
            this.time = 0;
        }

        public void postTweet(int userId, int tweetId) {// O(1)

            if(!tweetMap.containsKey(userId)){
                follow(userId, userId);
                tweetMap.put(userId, new ArrayList<>());
            }
            tweetMap.get(userId).add(new Tweet(tweetId, time++));
        }

        public List<Integer> getNewsFeed(int userId) { //O(1)
            List<Integer> result = new ArrayList<>();
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);
            if(userMap.containsKey(userId)){
                for(int uId : userMap.get(userId)){
                    //size of tweet list of a user
                    if(tweetMap.get(uId) != null){
                        int size = tweetMap.get(uId).size();
                        for(int j = size - 1; j >= 0 && j > size - 12; j--){
                            pq.add(tweetMap.get(uId).get(j));
                            if(pq.size() > 10){
                                pq.poll();
                            }
                        }
                    }
                }
            }

            while(!pq.isEmpty()){
                result.add(0, pq.poll().tweetId);
            }
            return result;
        }

        public void follow(int followerId, int followeeId) {// O(1)
            if(!userMap.containsKey(followerId)){
                userMap.put(followerId, new HashSet<>());
            }
            userMap.get(followerId).add(followeeId);
        }

        public void unfollow(int followerId, int followeeId) {// O(1)
            if(followerId != followeeId && userMap.containsKey(followerId))
                userMap.get(followerId).remove(followeeId);
        }
    }
}
