//Timecomplexity:- O(1) for posting,follows,unfollows and O(log10) for min heap;
//Space complexity:- 0(n) for all calls of suerID.
//Did it run on leetcode :- Yes.
//What problem did you face?:- faced lot of problem in getNewsFeed.
//Your code with explanantion:- for posting.following andall used hashmap. In posting in hashamp key is user Id,value
//is list of tweetno,tweetid whenever tweet is posted tweetno is incremented.With this tweetno i added to min heap and 
//removed root then we geet tweetno ,then tweetid from tweetno are added to list. this time you get all tweets in ascending
//order of time but we should get as latest one first, so for that we return reverse of List.

class Twitter {

    Map<Integer,Set<Integer>> map;
    Map<Integer,List<int[]>> map2;
    public Twitter() {
        map=new HashMap<>();
        map2=new HashMap<>();
    }
    int tweetno=1;
    public void postTweet(int userId, int tweetId) {
        if(!map.containsKey(userId)) map.put(userId,new HashSet<>());
        map.get(userId).add(userId);
        if(!map2.containsKey(userId)){
            map2.put(userId,new ArrayList<>());
        }
        map2.get(userId).add(new int[]{tweetId,tweetno});
        tweetno++;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->a[1]-b[1]);
         List<Integer> res=new ArrayList<>();
        if(!map.containsKey(userId)) return res;
        Set<Integer>set=map.get(userId);
        for(int e:set){
            if(!map2.containsKey(e)) continue;
            int count=0;
            List<int[]>temp=map2.get(e);
            for(int i=temp.size()-1;i>=0;i--){
                pq.add(temp.get(i));
                if(pq.size()>10) pq.poll();
                count++;
                if(count==10) break;
            }
        }
        while(!pq.isEmpty()){
            int i[]=pq.poll();
            res.add(i[0]);
        }
        Collections.reverse(res);
        return res;
        
    }
    
    public void follow(int followerId, int followeeId) {
        if(!map.containsKey(followerId)){
            map.put(followerId,new HashSet<>());
        }
        map.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!map.containsKey(followerId) || followerId==followeeId) return;
        map.get(followerId).remove(followeeId);
    }
}
