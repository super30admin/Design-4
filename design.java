class Twitter {
    int stamptime;
    HashMap<Integer,user> usermap;
    class user{
        int userid;
        HashSet<Integer> followers;
        List<Tweet> tweets;
        public user(int userid){
            this.userid=userid;
            this.followers=new HashSet<>();
            this.tweets=new ArrayList<>();
        }
    }
        class Tweet{
            int tweetid;
            int time;
            public Tweet(int tweetid,int time){
                this.tweetid=tweetid;
                this.time=time;
            }
    }
    public Twitter() {
        this.usermap=new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        Tweet twee=new Tweet(tweetId,stamptime++);
        if(usermap.containsKey(userId)){
            usermap.get(userId).tweets.add(twee);
        }else{
            user newuser=new user(userId);
            newuser.followers.add(userId);
            newuser.tweets.add(twee);
            usermap.put(userId,newuser);
        }
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> ans=new ArrayList<>();
        PriorityQueue<Tweet> q=new PriorityQueue<>((a,b)->a.time-b.time);
        if(!usermap.containsKey(userId)) return ans;
        user us=usermap.get(userId);
        HashSet<Integer> follow=us.followers;
        if(follow!=null){
            for(Integer followw:follow){
                List<Tweet> tw=usermap.get(followw).tweets;
                for(int i=tw.size()-1;i>=tw.size()-10 && i>=0 ;i--){
                    Tweet tws=tw.get(i);
                    q.add(tws);
                    if(q.size()>10){
                        q.poll();
                    }
                }
            }
        }
        while(!q.isEmpty()){
            ans.add(0,q.poll().tweetid);
        }
        return ans;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!usermap.containsKey(followerId)){
            user newuser=new user(followerId);
            newuser.followers.add(followeeId);
            usermap.put(followerId,newuser);
        }else{
        usermap.get(followerId).followers.add(followerId);
        usermap.get(followerId).followers.add(followeeId);
        }
        if (!usermap.containsKey(followeeId)) {
            user newUser = new user(followeeId);
            usermap.put(followeeId, newUser);
        }
    }
    public void unfollow(int followerId, int followeeId) {
        if(usermap.containsKey(followerId)){
            usermap.get(followerId).followers.remove(followeeId);
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

//problem 2
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skipMap;
    Integer ntEl;
    Iterator<Integer> nit;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    public void advance() {
        this.ntEl = null;
        while (nit.hasNext()) {
            Integer el = nit.next();
            if (!skipMap.containsKey(el)) {
                ntEl = el;
                break;
            } else {
                skipMap.put(el, skipMap.get(el) - 1);
                if (skipMap.get(el) == 0) {
                    skipMap.remove(el);
                }
            }
        }
    }

    public boolean hasNext() {
        return ntEl != null;
    }

    public Integer next() {
        Integer el = ntel;
        advance();
        return el;
    }

    public void skip(int val) {
        if (val == ntEl) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());
        System.out.println(itr.hasNext()); // true
        itr.skip(5);
        System.out.println(itr.next()); // 6
        itr.skip(5);
        System.out.println(itr.next()); // 7
        System.out.println(itr.next()); // 6
        itr.skip(8);
        itr.skip(9);
        System.out.println(itr.next()); // 5
        System.out.println(itr.next()); // 5
        System.out.println(itr.next()); // 6
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // 8
        System.out.println(itr.hasNext()); // false
    }
}
