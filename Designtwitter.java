class Twitter {
    int time=0;   
    class tweet{
        int tweetId;
        int userId;
        int time;
        tweet(int tweetId, int userId, int time){
            this.tweetId= tweetId;
            this.userId=userId;
            this.time=time;
        }        
    }

    HashMap<Integer,person> users;
    
       List<tweet>alltweets;         

    class person{
        
        int userId;
        HashSet<person> following;
        
        person(int userId){
            this.following= new HashSet<>();
            this.userId=userId;    
        }
        
    }
    
    public Twitter() {
       users = new HashMap<>();
        alltweets= new ArrayList();
        
    }

    public void postTweet(int userId, int tweetId) {
        if(!this.users.containsKey(userId)){
             person p= new person(userId);
            p.following.add(p);
             users.put(userId,p);
        } 
        this.alltweets.add(new tweet(tweetId,userId,this.time) );
        this.time++;

    }

    public List<Integer> getNewsFeed(int userId) {
      List<Integer> ans= new ArrayList<>();
        
        int p=this.alltweets.size()-1;
        
        while(ans.size()<10 && p>=0){
           
        person current_user=users.get(userId); 
        HashSet<person> he_follows = current_user.following;
       person current_tweets_owner = users.get((alltweets.get(p).userId));
          if(he_follows.contains(current_tweets_owner) ){
              
                ans.add(this.alltweets.get(p).tweetId);
              
          } p--;

            
            }
           return ans;
        }

    public void follow(int followerId, int followeeId) {
        
        if(!this.users.containsKey(followerId)){
            
            person p= new person(followerId);
             p.following.add(p);
            users.put(followerId,p);
        }
        
        if(!this.users.containsKey(followeeId)){
            
            person p= new person(followeeId);
             p.following.add(p);
            users.put(followeeId,p);
        } 
        this.users.get(followerId).following.add(this.users.get(followeeId));
    }
    
    public void unfollow(int followerId, int followeeId) {
        this.users.get(followerId).following.remove(this.users.get(followeeId));
    }
}
