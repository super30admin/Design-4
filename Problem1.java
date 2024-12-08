public class Problem1 {
    class Twitter {
        Map<Integer,Set<Integer>> followerMap;
        Map<Integer,List<Tweet>> tweetsMap;
        int time =1;

        class Tweet{
            int tweetId;
            int userId;
            int postedTime;

        }
        public Twitter() {
            this.followerMap = new HashMap<>();
            this.tweetsMap = new HashMap<>();
        }

        public void postTweet(int userId, int tweetId) {
            Tweet tw = new Tweet();
            tw.tweetId = tweetId;
            tw.userId = userId;

            tw.postedTime = this.time++;
            if(!tweetsMap.containsKey(userId)){
                tweetsMap.put(userId,new ArrayList<>());
            }
            tweetsMap.get(userId).add(tw);
        }

        public List<Integer> getNewsFeed(int userId) {
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.postedTime-b.postedTime); //min heap
            List<Integer> newsFeed = new ArrayList<>();
            Set<Integer> followers = this.followerMap.get(userId);
            if(followers == null){
                followers = new HashSet<>();

            }
            followers.add(userId);// we should be able to see our own tweets as well
            if(followers !=null){
                for(Integer follower : followers){
                    List<Tweet> twOfUser =  tweetsMap.get(follower);
                    if(twOfUser!=null){
                        for(int j=twOfUser.size()-1;j>=0 && j>=twOfUser.size()-10;j--){
                            pq.add(twOfUser.get(j));
                            if(pq.size()>10){
                                pq.poll();
                            }
                        }
                    }

                }
            }

            while(!pq.isEmpty()){
                newsFeed.add(pq.poll().tweetId);
            }
            Collections.reverse(newsFeed);
            return newsFeed;
        }

        public void follow(int followerId, int followeeId) {
            if(!followerMap.containsKey(followerId)){
                followerMap.put(followerId,new HashSet<>());
                //followerMap.get(followerId).add(followerId); //adding himself as follower
            }
            followerMap.get(followerId).add(followeeId);
        }

        public void unfollow(int followerId, int followeeId) {
            if(followerMap.containsKey(followerId) && followerMap.get(followerId).contains(followeeId)){
                followerMap.get(followerId).remove(followeeId);
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
