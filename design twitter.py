'''
Solution : HashMaps, heaps for extracting top-k newsFeed
    - We create Tweet object for every new tweet. 
    - Maintain 
        - userTweets : Map storing list of Tweets made by each user
        - userLog : Map storing set of all userIds followed by each user
        - time : global time clock 
    - postTweet(), follow(), unfollow(): Update respective HashMaps
    - getNewsFeed(): Using 'k' sized min-heap, get the k most recent tweets 
                     to be broadcasted in the newsfeed of current user. 
                     In the Tweet object, we have a custom comparator grounded on 
                     'createdAt', time at which this tweet was created
Time Complexity: 
    - postTweet(): O(1)
    - follow(): O(1)
    - unfollow(): O(1)
    - getNewsFeed(): O(MlogK), M = Average number of tweets for a user which includes
                                   tweets by himself + tweets by users he/she follows
Space Complexity: 
    - postTweet(): O(1)
    - follow(): O(1)
    - unfollow(): O(1)
    - getNewsFeed(): O(K), K = size of heap maintaining most recent 'k' tweets.
    - userTweets : O(N+N*M), N = Users, M = Average number of tweets for a user which includes
                                            tweets by himself + tweets by users he/she follows                                             
    - userLog : O(N + E), E = average numbers of users followed by each user.
'''
class Tweet:
    def __init__(self,id,timestamp):
        self.id = id #tweet id
        self.createdAt = timestamp
    def __lt__(self,other):
        return self.createdAt<=other.createdAt

class Twitter:

    def __init__(self):
        self.userTweets = {} # {userId - [List of TweetObject]}
        self.userLog = {} # {userId - (set of followed users-id)} 
        self.time = 0 # time clock for Twitter at global level

    def postTweet(self, userId: int, tweetId: int) -> None:
        newTweet = Tweet(tweetId,self.time) # create new tweet object
        self.time+=1

        self.userTweets[userId] = self.userTweets.get(userId,[])
        self.userTweets[userId].append(newTweet) # update tweets made by this user

    def getNewsFeed(self, userId: int) -> List[int]:
        k = 10 # 'k' most recent tweets
        heap = [] # min-heap of type priorityQueue<Tweet>, comparator on timestamp

        # check if user has himself Tweeted  
        if userId in self.userTweets:
            for tweet in self.userTweets[userId]:
                heapq.heappush(heap,tweet)
                if len(heap)>k: # maintain top-k recent tweets
                    heapq.heappop(heap) 

        # Update heap with tweets made by users that are being followed 
        if userId in self.userLog and self.userLog[userId]:
            for followed_id in self.userLog[userId]:
                if followed_id in self.userTweets:
                    for tweet in self.userTweets[followed_id]:
                        heapq.heappush(heap,tweet)
                        if len(heap)>k: # maintain top-k recent tweets
                            heapq.heappop(heap) 

        # prepare the newsFeed ordered from most recent to least recent
        if len(heap)==0:
            return [] # empty 
        
        newsFeed = [None]*len(heap)
        for i in range(len(heap)-1,-1,-1):
            newsFeed[i] = heapq.heappop(heap).id # add least recent at last of newsFeed

        return newsFeed

    def follow(self, followerId: int, followeeId: int) -> None:
        self.userLog[followerId]  = self.userLog.get(followerId,set())
        self.userLog[followerId].add(followeeId) # update 'followed list' of the user

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.userLog and followeeId in self.userLog[followerId]: 
            self.userLog[followerId].remove(followeeId) # update 'followed list' of the user


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)