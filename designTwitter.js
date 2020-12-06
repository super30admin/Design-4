// Time Complexity : O(nlok10)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No



var Twitter = function() {
    this.folow = new Map();//uid:{uid:true,uid:true,...} uid--> Follows these...
    this.tweets=new Map();//tid:uid
};

Twitter.prototype.postTweet = function(userId, tweetId) {
   let tweets=this.tweets;    
   tweets.set(tweetId,userId);
};

Twitter.prototype.getNewsFeed = function(userId) {
   let tweets=this.tweets;
   let folow=this.folow;
   let ids={};
   if(folow.has(userId))
       ids=folow.get(userId);
   
   let res=[];
   let ar= Array.from(tweets);

   while(res.length<10&&ar.length>0){
       let item=(ar.pop());
       if(item[1]===userId||ids[item[1]]){
               res.push(item[0]);   
       }                        
   }
   return res;    
};

Twitter.prototype.follow = function(followerId, followeeId) {
   let folow=this.folow;
   let ar={};
   if(folow.has(followerId))
       ar=folow.get(followerId);
   
   ar[followeeId]=true;
   folow.set(followerId,ar);
};

Twitter.prototype.unfollow = function(followerId, followeeId) {
   let folow=this.folow;
   let ar={};
   if(folow.has(followerId))
       ar=folow.get(followerId);
   
   if(ar.hasOwnProperty(followeeId))
       delete ar[followeeId];
   folow.set(followerId,ar);
};