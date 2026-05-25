// Time Complexity : HasNext and Nexr - O(1) ammortized
// Space Complexity : O(k) where k is the count of values to be skipped
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


public class SkipIterator
{
    private IEnumerator<int> it;
    private Dictionary<int, int> skipLookup;

    private bool hasNext;

    private int currentElement;

    public SkipIterator(IEnumerator<int> iterator)
    {
        it = iterator;
        skipLookup = new();
        hasNext = false;

        Helper();
    }

    public bool HasNext()
    {
        return hasNext;
    }

    public int Next()
    {
        if (!hasNext)
        {
            throw new Exception("End of list");
        }

        int result = currentElement;

        Helper();

        return result;
    }

    /*
     * The input parameter is an int, indicating that the next element
     * equal to 'val' needs to be skipped.
     *
     * This method can be called multiple times in a row.
     * Skip(5), Skip(5) means that the next two 5s should be skipped.
     */
    public void Skip(int val)
    {
        if (hasNext && currentElement == val)
        {
            Helper();
        }

        else
        {
            skipLookup[val] = skipLookup.GetValueOrDefault(val, 0) + 1;
        }
    }

    public void Helper()
    {
        hasNext = false;

        while (it.MoveNext())
        {
            hasNext = true;
            int element = it.Current;

            if (skipLookup.ContainsKey(element))
            {
                skipLookup[element] -= 1;

                if (skipLookup[element] == 0)
                {
                    skipLookup.Remove(element);
                }
            }

            else
            {
                currentElement = element;
                return;
            }
        }

    }
}