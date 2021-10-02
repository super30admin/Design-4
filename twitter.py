from heapq import heappush as insert
from heapq import heappop as remove
class Tweet:
    def __init__(self,id_val,created_at):
        self.tid=id_val
        self.created_at=created_at
class CustomListNode:
    def __init__(self,node):
        self.node=node
    def __lt__(self,other):
        return self.node.created_at < other.node.created_at
        
class Twitter:

    def __init__(self):
        self.followed={}
        self.tweet={}
        self.time=0
        
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        
        self.follow(userId,userId)
        if userId not in self.tweet.keys():
            self.tweet[userId]=[]
        self.time=self.time+1
        obj=Tweet(tweetId,(self.time))
    
        self.tweet[userId].append(obj)
            
        

    def getNewsFeed(self, userId: int) -> List[int]:
        newq=[]
        pq=[]
        print(self.followed)
        if len(self.followed)==0 or len(self.tweet)==0:
            return []
        
        if self.followed[userId] is not None:    
            temp=self.followed[userId]
            print(temp)
            

            for i in temp:
                print(i)
                print(self.tweet)
                
                if self.tweet[i] is not None:
                    for j in self.tweet[i]:
                        print(j.tid)
                        print("j")
                        print(j.created_at)
                        insert(pq,(j.created_at,CustomListNode(j)))
                        print(pq)
                        if len(pq)>10:
                            remove(pq)
                        print(pq)
                else:
                    return []
    
        print(pq)
        while pq:
            a=remove(pq)
            newq.append(a[1].node.tid)
        return newq[::-1]
            
            
                
    def follow(self, followerId: int, followeeId: int) -> None:
        print(self.followed)
        print(self.followed.keys())
        if  followerId not in self.followed.keys():
            self.followed[followerId]=[]
        if followeeId not in self.followed[followerId]:
            self.followed[followerId].append(followeeId)
            
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followed.keys() and followerId is not followeeId:
            temp=self.followed[followerId]
            if followeeId in temp:
                self.followed[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)