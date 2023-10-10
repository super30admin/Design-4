class Twitter {
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int tweetId, int createdAt){
            this.id = tweetId;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>>tweets;
    int time;

    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
        
    }
    
   
    public void postTweet(int userId, int tweetId) {
    follow(userId, userId);
    if (!tweets.containsKey(userId)) {
        tweets.put(userId, new ArrayList<>());
    }
    Tweet newtweet = new Tweet(tweetId, time++);
    tweets.get(userId).add(newtweet);
     }

    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet>pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followeds = followed.get(userId);
        List<Integer> result = new ArrayList<>();
        if(followeds == null) return result;

        for(int fId: followeds){
            List<Tweet> ftweets = tweets.get(fId);
            if(ftweets != null){
                int size = ftweets.size();
                int min = Math.min(size, 10);
                for(int i=size-1; i>=size-min;i--){
                    pq.add(ftweets.get(i));
                    if(pq.size()>10){
                        pq.poll();
                    }
                }
            }
        }
        //List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().id);

        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId){
            followed.get(followerId).remove(followeeId);
        }
        
    }
}



 /*
 
 
postTweet
getNewsfeed
Follow
Unfollow
These are the 4 basic requirements 

Clarifying Questions : 
 
What are the minimum tweets in the newsFeed → May be 5 or 10 

How do you navigate your clarifications ?

What if he say go and implement your objects → User, Tweets 


User Objects Data : 

News
UserId
Followers List
List of Tweets 
Likes
About
ImageURL
Following

But these all are not required, these all are out of scope. We just have to concentrate on the requirements given by the Interviewer.


Tweets Object Data : 

Text
Id
userID
Created At





All the requirements will be in Twitter Object 
postTweet
getNewsfeed
Follow
Unfollow
 

Everytime we need to think about TopDown. First we should start with thinking how twitter looks from outside. It looks like graph connecting user from one country to another country 

So, we use adjacency list for users 

Here the question is in the Adjacency list, what are the users that I should maintain. 1) Users that I follow 2) Users who follows me 

We need to maintain 1) Users that I follow, because our requirement is to get new Feed. 

And also we have Users and his tweets in another adjacency list.

We include ourselves inside the following Adjacency list. And we bring all the tweets of our followers, so my tweets also include these. There will be so many tweets. Now we need to show only a few tweets based on the limit. 

For this we use Heap with k elements and instead of storing all million tweets we can store the last 10 tweets for each user.

 
 
  */