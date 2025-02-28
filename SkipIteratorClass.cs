// TC -> Iterator Constructor O(n) otherwise O(1)
// SC -> O(n)

public class Node
{
    public int val;
    public Node next;
    public Node(int val)
    {
        this.val = val;
    }
}
public class SkipIterator : IEnumerator<int>
{
    private IList<int> _collection;
    private Dictionary<int, Node> lookup;
    private int curIndex;
    private int curInt;
    public int Current => curInt;

    object IEnumerator.Current => Current;
    HashSet<int> skipInts;
    public SkipIterator(IList<int> collection)
    {
        _collection = collection;
        curIndex = -1;
        curInt = default;
        lookup = new Dictionary<int, Node>();
        skipInts = new HashSet<int>();
        for (int i = 0; i < collection.Count; i++)
        {
            if (lookup.ContainsKey(collection[i]))
            {
                var current = lookup[collection[i]];
                while (current.next != null)
                {
                    current = current.next;
                }
                current.next = new Node(i);
            }
            else
            {
                lookup.Add(collection[i], new Node(i));
            }
        }
    }

    public int Next()
    {
        if (MoveNext())
        {
            return Current;
        }
        return default;
    }

    public bool HasNext()
    {
        return curIndex < _collection.Count - 1;
    }
    /**
    * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
    * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    */
    public void Skip(int val)
    {
        if (lookup.ContainsKey(val))
        {
            var dummyNode = new Node(-1);

            var current = lookup[val];
            dummyNode.next = current;
            var prev = dummyNode;
            while (current != null && current.val <= curIndex)
            {
                prev = current;
                current = current.next;

            }
            skipInts.Add(current.val);
            prev.next = current.next;
            lookup[val] = dummyNode.next;
        }
    }

    public bool MoveNext()
    {
        //Avoids going beyond the end of the collection.
        if (++curIndex >= _collection.Count)
        {
            return false;
        }
        else
        {
            // Set current int to next item in collection. but if its index present in the skipInt, skip it;
            if (skipInts.Contains(curIndex))
            {
                return MoveNext();
            }
            curInt = _collection[curIndex];
        }
        return true;
    }

    public void Reset()
    {
        curIndex = -1;
    }

    public void Dispose()
    {
    }
}
public class Program
{
    static void Main(string[] args)
    {
        SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
        Console.WriteLine(itr.HasNext()); // true
        Console.WriteLine(itr.Next()); // returns 2
        itr.Skip(5);
        Console.WriteLine(itr.Next()); // returns 3
        Console.WriteLine(itr.Next()); // returns 6 because 5 should be skipped
        Console.WriteLine(itr.Next()); // returns 5
        itr.Skip(5);
        itr.Skip(5);
        Console.WriteLine(itr.Next()); // returns 7
        Console.WriteLine(itr.Next()); // returns -1
        Console.WriteLine(itr.Next()); // returns 10
        Console.WriteLine(itr.HasNext()); // false
        Console.WriteLine(itr.Next()); // default value of int
    }
}

