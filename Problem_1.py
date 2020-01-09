# Pretty perfect code, but doesnt run on Leetcode for one test case

# Please review

class Twitter:
    import heapq
    def __init__(self):
        self.FollowTable = {}
        self.Tweets_list_table = {}
        self.TimeStamp = 1

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.FollowTable:
            self.FollowTable[userId] = {userId}
            
        if userId not in self.Tweets_list_table:
            self.Tweets_list_table[userId] = [(self.TimeStamp, tweetId)]
        else:    
            self.Tweets_list_table[userId].append((self.TimeStamp, tweetId))
        self.TimeStamp += 1
        
    def getNewsFeed(self, userId: int) -> List[int]:
        if userId not in self.FollowTable:
            return []
        
        following_list = list(self.FollowTable[userId])
        if not following_list:
            return []
        
        result = []
        for member in following_list:
            if member not in self.Tweets_list_table:
                continue
            else:
                for tweet in self.Tweets_list_table[member]:
                    if len(result) < 10:
                        result.append(tweet)
                    else:
                        heapq.heappushpop(result, tweet)
        answer = []
        for i in heapq.nlargest(10, result):
            answer.append(i[1])
        return answer
        
    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.FollowTable:
            self.FollowTable[followerId] = {followerId}
        self.FollowTable[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId == followeeId or followerId not in self.FollowTable:
            return
        else:
            self.FollowTable[followerId] -= {followeeId}
