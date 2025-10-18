import java.util.*;
class CDLLNode {
    int key, val;
    CDLLNode prev, next;

    CDLLNode(int k, int v) {
        key = k;
        val = v;
    }
}

class CDLL {
    CDLLNode head;
    CDLLNode insertAtBegin(int key, int val) {
        CDLLNode nn = new CDLLNode(key, val);
        nn.next = nn;
        nn.prev = nn;

        if (head == null) {
            head = nn;
            return nn;
        }

        CDLLNode last = head.prev;
        nn.next = head;
        head.prev = nn;
        nn.prev = last;
        last.next = nn;
        head = nn;
        return head;
    }

    int deleteAtLast() {
        if (head == null) return -1;

        CDLLNode last = head.prev;

        if (head == last) {
            head = null;
            return last.key;
        }

        last.prev.next = head;
        head.prev = last.prev;
        return last.key;
    }

    
    void moveAtFront(CDLLNode n) {
        if (head == n) return;

        // Detach node
        n.prev.next = n.next;
        n.next.prev = n.prev;

        // Insert at front
        CDLLNode last = head.prev;
        n.next = head;
        head.prev = n;
        n.prev = last;
        last.next = n;
        head = n;
    }

    void print() {
        if (head == null) {
            System.out.println("Cache is empty.");
            return;
        }

        System.out.println("\nCurrent LRU Cache State (MRU -> LRU):");
        CDLLNode temp = head;
        System.out.print("MRU=> { " + temp.key + " : " + temp.val + " }");
        while (temp.next != head) {
            temp = temp.next;
            System.out.print("  ->  { " + temp.key + " : " + temp.val + " }");
        }
        System.out.println("  <=LRU\n");
    }
}

// LRU Cache Implementation
class LRUCache {
    int size, capacity;
    Map<Integer, CDLLNode> map;
    CDLL list = new CDLL();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.map = new HashMap<>();
    }

    
    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        CDLLNode node = map.get(key);
        list.moveAtFront(node);
        return node.val;
    }

    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            CDLLNode node = map.get(key);
            node.val = value;
            list.moveAtFront(node);
        } else {
            if (size == capacity) {
                int deletedKey = list.deleteAtLast();
                map.remove(deletedKey);
                size--;
            }
            CDLLNode newNode = list.insertAtBegin(key, value);
            map.put(key, newNode);
            size++;
        }
    }

    public void print() {
        list.print();
    }
}


public class LRU_CACHE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter capacity of cache: ");
        int capacity = sc.nextInt();
        LRUCache cache = new LRUCache(capacity);

        while (true) {
            System.out.println("----------- MENU -----------");
            System.out.println("1. Get");
            System.out.println("2. Put");
            System.out.println("3. Print Cache");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter key to search: ");
                    int key = sc.nextInt();
                    int val = cache.get(key);
                    if (val == -1)
                        System.out.println("Key not found.");
                    else
                        System.out.println("Found. Value = " + val + " (Moved to front)");
                    break;

                case 2:
                    System.out.print("Enter key-value pair: ");
                    int k = sc.nextInt();
                    int v = sc.nextInt();
                    cache.put(k, v);
                    System.out.println("Inserted/Updated successfully.");
                    break;

                case 3:
                    cache.print();
                    break;

                case 4:
                    System.out.println("Exiting program.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
