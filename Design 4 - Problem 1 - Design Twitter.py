"""
FAANMG Problem #86 {Medium} 

355. Design Twitter


# TC:
# postTweet: O(1)
# getNewsFeed: O(n)
# follow: O(1)
# unfollow: O(1)

Did this code successfully run on Leetcode : Yes


@name: Rahul Govindkumar_RN27JUL2022
"""
# define Tweet class with attributes tid and createAt (timestamp)
class Tweet:
    def __init__(self, tid, time):
        self.tid =tid
        self.createdAt = time

# Distance class is used to implement max heap, to calculate the difference in objects with the help of a comparator
class Heap:
    # using the object of Tweet as we will insert the tweets into the heap
    def __init__(self, tweet):
        self.tweet = tweet

    # overloading the less than operator to compare the createdAt attribute of tweet objects to build max heap
    def __lt__(self, other):
        return self.tweet.createdAt < other.tweet.createdAt

class Twitter:

    def __init__(self):
        
        self.tweetmap=collections.defaultdict(list)
        self.followersmap=collections.defaultdict(set)
        self.time=0
        self.num_tweets=10
        
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        
        self.follow(userId, userId)
        
        # increase the timestamp
        self.time += 1
        
        # add the tweet object (tweetId and time) to the tweet hashmap and store it with userId as the key
        self.tweetmap[userId].append(Tweet(tweetId, self.time))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        
        res = collections.deque()
        heap=[]
        
        for followers_ID in self.followersmap[userId]:
            
            if followers_ID in self.tweetmap:
                
                # extract the tweets of each user in a list
                tweetsList = self.tweetmap[followers_ID]
                
                 # if tweets present then go over each tweet and add it to the max-heap
                if tweetsList is not None:
                    for each_tweet in tweetsList:
                        # In the heap, we store the tweet object, by comparing the createdAt attribute using less than to build min heap
                        heapq.heappush(heap, Heap(each_tweet))
                        
                        # we have a capacity of 10 tweets
                        if len(heap) > self.num_tweets:
                            # if heap size is greater than 10, pop the min element
                            heapq.heappop(heap)
                
          # removing the elements of the heap to fetch the results
        while heap:
            # store the popped element of heap in a temp
            temp = heapq.heappop(heap)
            # store the tweet id from the tweet object into res
            res.appendleft(temp.tweet.tid)
        return res       
        

    def follow(self, followerId: int, followeeId: int) -> None:
        
        self.followersmap[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followersmap and followeeId != followerId:
            # if the followeeId is present in the Hashmap, then remove it from the Hashmap, for the followerId
            if followeeId in self.followersmap[followerId]:
                self.followersmap[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)