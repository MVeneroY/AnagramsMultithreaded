import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WordList {
    
    WordNode head = null;
    WordNode virtualHead = null;
    WordNode tail = null;

    Lock lock;

    WordList() {
        this.lock = new ReentrantLock();
    }

    WordList(WordNode head) {
        this.head = head;
        this.lock = new ReentrantLock();
    }

    public Boolean isEmpty() { return this.head == null && this.virtualHead == null; }

    public void display() {
        if (head == null && this.virtualHead == null) return;

        WordNode curr;
        if (virtualHead != null) curr = virtualHead;
        else curr = head;

        do {
            System.out.println(curr.word);
            curr = curr.next;
        } while (curr != null);
    }

    public void add(WordNode node) {
        if (node == null) return;

        if (this.isEmpty()) {
            head = node;
            head.next = tail;
            virtualHead = head;

            return;
        }

        if (tail == null) {
            head.next = node;
            tail = head.next;

            return;
        }

        tail.next = node;
        tail = tail.next;
    }

    public void pop(WordNode node, int group) {
        lock.lock();
        try {
            if (node.group != -1) return;

            node.group = group;
            if (node == head) virtualHead = head.next;
            else if (node == virtualHead) virtualHead = virtualHead.next;
        } finally { lock.unlock(); }
    }
}
