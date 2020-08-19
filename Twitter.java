/* Time complexity: O(m+n)
space complexity: o(n)
*/

class Twitter{
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
        HashMap<Integer, Set<Integer>> followed;
        HashMap<Integer, List<Tweet>> tweets;
        int feedSize; int timeStamp;
        public Twitter(){
            followed = new HashMap<>();
            tweets = new HashMap<>();
            feedSize = 10;
        }

        public void postTweet(int userID, int tweetID){
            follow(userID, userID);
            if(!tweet.containsKey(userID)){
                tweets.put(userID, new ArrayList<>());
            }
            tweets.get(userID).add(new Tweet(tweetID, timeStamp++));
        }
        public List<Integer> getNewsFeed(int userID){
            PriorityQueue<Tweets> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
            Set<Integer> fIds = followed.get(userID);
            if(fID != null){
                for(int fId: fIds){
                    List<Tweet> fTweets = tweets.get(fId);
                    if(fTweets != null){
                        for(Tweet fTweet: fTweets){
                            pq.add(fTweet);
                            if(pq.size()>feedSize){
                                pq.poll();
                            }
                        }
                    }
                }
            }
            List<Integer> result = new ArrayList<>();
            while(!pq.isEmpty()){
                result.add(0, pq.poll().id);
            }
            return result;
        }

        public void unfollow(int followerId, int followeeId){
            if(followerId != followeeId && followed.containsKey(followerId)){
                followed.get(followerId).remove(followeeId);
            }
        }
    }
}