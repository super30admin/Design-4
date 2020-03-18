class Logger {
  private Map<String, Integer> msgDict;

  /** Initialize your data structure here. */
  public Logger() {
      //I would be maininging a hashmap to save message and timestamp at which message has arrived.
      msgDict = new HashMap<String, Integer>();
  }

  /**
   * Returns true if the message should be printed in the given timestamp, otherwise returns false.
   */
  public boolean shouldPrintMessage(int timestamp, String message) {
        //if message comes again in the future, then I would be checking its previous timestamp,
        int oldTimeStamp = msgDict.getOrDefault(message, -10);
        //if the previous timestamp of the message is within last 10 seconds then dont print that message. 
        if( timestamp-oldTimeStamp < 10)
            return false;
        else
        {   //else print and save current timestamp for the future reference.
            msgDict.put(message, timestamp);
            return true;
        }
  }
}

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */
