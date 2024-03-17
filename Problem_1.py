'''
Time Complexity - O(1) for follow, unfollow and tweet. O(nlogk) for getting feed
Space Complexity - O(n) for storing the userId-> followees, userId->tweets, O(log k) for getting tweets  

Works on LeetCode.
'''


import heapq
class Tweet:
    def __init__(self, tweetId, createdAt):
        self.tweetId = tweetId #store the TweetId
        self.createdAt = createdAt #store the timestamp of the tweet

    def __lt__(self, tweet):
        return self.createdAt < tweet.createdAt

class Twitter:

    def __init__(self):
        self.followedMap = {} #Follower(UserId)->[Followees]
        self.tweetsMap = {} #UserID->[TweetIds]
        self.createdAt = 0 #maintain Time

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId) #make a user follow itself so that it can its own tweets
        if userId not in self.tweetsMap: #if user has not tweeted before
            self.tweetsMap[userId] = [] #create an entry
        tweets = self.tweetsMap[userId] #get the list of tweets for the user
        tweets.append(Tweet(tweetId, self.createdAt)) #create a new tweet, enter time of creation, add to list of tweets of userId
        self.createdAt+=1


    def getNewsFeed(self, userId: int) -> List[int]:
        pq = [] #initiate the heap
        if userId not in self.followedMap:
            return [] #return empty list if the user does not follow anyone
        followed = self.followedMap[userId] #get the list of users the user follows
        result = []
        count = 0
        if len(followed)>0:
            for fid in followed:
                

                ftweets = self.tweetsMap.get(fid) if fid in self.tweetsMap else [] #get the tweets for every user the user follows
                if len(ftweets)>0:
                    for ftweet in ftweets: 
                        heapq.heappush(pq,ftweet)#push each tweet in the heap
                        count+=1 #increase the size of heap
                        if count > 10: #we only need the 10 latest tweets so we pop all the tweets with earlier times
                            heapq.heappop(pq) 
                            count-=1# while popping we also reduce the size by 1
        while count > 0 :
            tw = heapq.heappop(pq)
            result.append(tw.tweetId) #in ther end we append the latest 10 tweets to a list
            count-=1
        return result[::-1]       #we reverse and return the list           
                  
        

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followedMap: #if the user does not follow anyone or is new, 
            self.followedMap[followerId] = set() #the user is created, and a set of followees for the user is created
        followersSet = self.followedMap[followerId] 
        followersSet.add(followeeId) #add the followee to the set of followees

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.followedMap[followerId] and followerId != followeeId: #if the followee is present in the list of followees and the followee id is not the same as that of the user
            followersSet = self.followedMap[followerId] #get the list of followees
            followersSet.remove(followeeId) #remove the followee from the list



# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)