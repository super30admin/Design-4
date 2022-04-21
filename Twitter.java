import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/*
Time Complexity: O()
Space Complexity: O()
Run on leetcode: yes
Any difficulties: no

Approach: **Attempted after discussed in the class**
 */
public class Twitter {
     int postId = 1;
     HashMap<Integer, User> map;
     public Twitter(){
         map = new HashMap<>();
     }

     public void postTweet(int userId, int tweetId){
         User user = map.get(userId);

         if(user!= null){
             user.post.put(postId, tweetId);
         }else{
             User newUser = new User();
             newUser.post.put(postId, tweetId);
             map.put(userId, newUser);
         }

         postId++;
     }

    public List < Integer > getNewsFeed(int userId) {
        ArrayList<HashMap.Entry<Integer,Integer>> result = new ArrayList();
        User user = map.get(userId);
        if (user != null) {
            result.addAll(user.post.entrySet());
            for (int i: user.following) {
                User user2 = map.get(i);
                if (user2 != null) {
                    result.addAll(user2.post.entrySet());
                }
            }
            Collections.sort(result, (HashMap.Entry<Integer,Integer> a, HashMap.Entry<Integer,Integer> b ) ->  b.getKey()-a.getKey());
        }
        List<Integer> list = new ArrayList();
        for(HashMap.Entry<Integer,Integer> entry : result){
            list.add(entry.getValue());
            if(list.size()==10)
                return list;

        }
        return list;
    }

    public void follow(int followerId, int followeeId) {
        User user = map.get(followerId);
        if (user != null) {
            user.following.add(followeeId);
        } else {
            User newUser = new User();
            newUser.following.add(followeeId);
            map.put(followerId, newUser);
        }
    }

    public void unfollow(int followerId, int followeeId) {
        User user = map.get(followerId);
        if (user != null) {
            user.following.remove(followeeId);
        }
    }
}
