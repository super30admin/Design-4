#// Time Complexity : O(n log n) n is number of tweets 
# // Space Complexity : O(n) 
# // Did this code successfully run on Leetcode : yes
# // Any problem you faced while coding this : No.

class Twitter:

    def __init__(self):
        self.user=defaultdict(set)
        self.tweet=defaultdict(list)
        self.timestamp=1
        
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.user[userId].add(userId) 
        self.tweet[userId].append([self.timestamp,tweetId])
        self.timestamp+=1

        

    def getNewsFeed(self, userId: int) -> List[int]:
        minHeap=[]
        
        for users in self.user[userId]:
            if users in self.tweet:
                for tweets in self.tweet[users]:
                    heapq.heappush(minHeap,(tweets[0],tweets[1]))
                    if len(minHeap)>10:
                        heapq.heappop(minHeap)
        answer=[]

        while minHeap:
            answer.append(heapq.heappop(minHeap)[1])
        return answer[::-1]


        

    def follow(self, followerId: int, followeeId: int) -> None:
        self.user[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.user and followeeId in self.user[followerId] and followerId!=followeeId :
            self.user[followerId].remove(followeeId)

# Approach:
# The problem can be solved by using a priority queue to store the tweets of the users that the current
# user is following. The priority queue will store the tweets in the order of their timestamps. The
# tweets will be added to the priority queue when the user posts a new tweet or when the user follows
# another user. The priority queue will be popped when the user wants to get the news feed.'

