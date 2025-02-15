public class Problem1 {
    class Twitter {
        class tweet{
            int tweetId, createdAt;
            tweet(int tweetId, int createdAt){
                this.tweetId = tweetId;
                this.createdAt = createdAt;
            }
        }

        int time;
        HashMap<Integer,HashSet<Integer>> userMap;
        HashMap<Integer,List<tweet>> tweetMap;

        public Twitter() {
            userMap = new HashMap<>();
            tweetMap = new HashMap<>();
            time = 0;
        }

        public void postTweet(int userId, int tweetId) {
            if(!tweetMap.containsKey(userId)){
                tweetMap.put(userId, new ArrayList<>());
            }
            tweetMap.get(userId).add(new tweet(tweetId, time++));
        }

        public List<Integer> getNewsFeed(int userId) {
            PriorityQueue<tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
            if(tweetMap.containsKey(userId)){
                for(tweet tw : tweetMap.get(userId)){
                    pq.add(tw);
                    if(pq.size()>10){
                        pq.poll();
                    }
                }
            }
            HashSet<Integer> followers = userMap.get(userId);
            if(followers != null){
                for(int follower : followers){
                    List<tweet> allTweets = tweetMap.get(follower);
                    if(allTweets != null){
                        for(tweet tw : allTweets){
                            pq.add(tw);
                            if(pq.size()>10){
                                pq.poll();
                            }
                        }
                    }
                }
            }

            List<Integer> result = new ArrayList<>();
            while(!pq.isEmpty()){
                result.add(0,pq.poll().tweetId);
            }
            return result;

        }

        public void follow(int followerId, int followeeId) {
            if(!userMap.containsKey(followerId)){
                userMap.put(followerId, new HashSet<>());
            }
            userMap.get(followerId).add(followeeId);
        }

        public void unfollow(int followerId, int followeeId) {
            if(!userMap.containsKey(followerId)){
                return;
            }
            if(userMap.get(followerId).contains(followeeId)){
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
}
