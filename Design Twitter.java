// Time Complexity : O(nlogk)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
// Your code here along with comments explaining your approach
// We will create a twitter class, which will have all the functions. We will maintain a User Map which contains the userId and the users whom they follow. 
// We will also maintain a TweetMap where we will have the userId and their tweetids. We will maintaintain timestamps for all the tweets done.
// Follow: we will add the user id of who is supposed to be followed in the UserMap, to the respective user.
// Unfollow: We will remove the followee from the hashmap for the respective user
// postTweet: We will add the tweet and the timestamp to the tweetsmap
// getNewsFeed: We need to get the top 10 latest tweets of the followees. So we will get the followees, then refer the tweetsMap 
// and use min heap to find the latest 10 tweets using their timeStamps.
class Twitter 
{
    class Tweet
    {
        int tid;    //twitter id
        int time;      //time stamp

        public Tweet(int tid,int time)
        {
            this.tid=tid;
            this.time=time;
        }
    }

//UserMap ----- user ids and the hashset of the ids of the people whom they follow
private HashMap<Integer,HashSet<Integer>> followedMap;
//tweets Map----- userid and their tweets
private HashMap<Integer,List<Tweet>> tweetsMap;
//time stamp
int time;

    public Twitter() 
    {
        this.followedMap=new HashMap<>();
        this.tweetsMap=new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) 
    {
       //a user should be able to see their own tweets , so they need to follow themselves too
       follow(userId,userId);
       //if the user is not present in the tweets map, then we add them first and then get their tweet
       if(!tweetsMap.containsKey(userId))
       {
           tweetsMap.put(userId,new ArrayList<>());
       }
       //add the tweet for the respective user
       tweetsMap.get(userId).add(new Tweet(tweetId,time++));     
    }
    
    public List<Integer> getNewsFeed(int userId) 
    {
        //we need to get the news feed i.e the latest 10 tweets of the followees

        //we will create a min heap priority queue by using custom comparator on each timestamp
        PriorityQueue <Tweet> pq=new PriorityQueue<>((a,b)->a.time-b.time);
        //We will create a HashSet of all the users who have been followed by the input userId
        HashSet<Integer> followeds=followedMap.get(userId);

        if(followeds!=null)
        {
            //we will go through each followee and get their tweets
            for(int fid:followeds)
            {
                //We will create a list to store the tweets of all the followees
                List<Tweet> fTweets=tweetsMap.get(fid);
                if(fTweets!=null)
                {
                    //iterating through each tweet to create a min heap 
                    for(Tweet ftweet:fTweets)
                    {
                        pq.add(ftweet);
                        if(pq.size()>10)
                        {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result=new ArrayList<>();
        while(!pq.isEmpty())
        {
            result.add(0,pq.poll().tid);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) 
    {
        //if the user is not present in the followed map,then we first add them and their add their followee
        if(!followedMap.containsKey(followerId))
        {
            followedMap.put(followerId,new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);
        
    }
    
    public void unfollow(int followerId, int followeeId) 
    {
        //if the follower is present in the followed Map and they are not unfollowing themselves, then we remove the followee from the hashmap
        if(followedMap.containsKey(followerId) && followerId!=followeeId)
        {
            followedMap.get(followerId).remove(followeeId);
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