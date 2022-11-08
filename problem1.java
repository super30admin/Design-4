package s30.Design-4;

public class problem1 {
//TC:- O(1)
//SC:- O(1)

    class Twitter {
    
        class Tweets{
            int id;
            int time;
            public Tweets(int id, int time){
                this.id = id;
                this.time = time;
            }
        }
        
        int time;
        
        HashMap<Integer, List<Tweets>> userTweets;
        HashMap<Integer, HashSet<Integer>> userFollow;
    
        public Twitter() {
            userTweets = new HashMap<>();
            userFollow  = new HashMap<>();
        }
        
        public void postTweet(int userId, int tweetId) {
            //TC:- O(1)
            if(!userTweets.containsKey(userId)){
                userTweets.put(userId, new ArrayList<>());
            }
            userTweets.get(userId).add(new Tweets(tweetId, time++));
        }
        
        public List<Integer> getNewsFeed(int userId) {
            //TC:- O(n log(10)) n is users and theirs tweets
            follow(userId, userId);
            PriorityQueue<Tweets> pq = new PriorityQueue<>((a,b) -> a.time - b.time);
            
            HashSet<Integer> users = userFollow.get(userId);
            
            for(int user : users){
                List<Tweets> allTweets = userTweets.get(user);
                if(allTweets != null){
                    for(Tweets tw : allTweets){
                        pq.add(tw);
                        if(pq.size() > 10){
                            pq.poll();
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
        
        public void follow(int followerId, int followeeId) {
            //TC:- O(1)
            if(!userFollow.containsKey(followerId)){
                userFollow.put(followerId, new HashSet<>());
            }
            userFollow.get(followerId).add(followeeId);
        }
        
        public void unfollow(int followerId, int followeeId) {
            //TC:- O(1)
            if(userFollow.containsKey(followerId) && followerId != followeeId){
                userFollow.get(followerId).remove(followeeId);
            }    
        }
    }
    
}
