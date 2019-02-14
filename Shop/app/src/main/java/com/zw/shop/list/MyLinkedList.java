package com.zw.shop.list;


/**
 * 单链表
 * Created by ZouWei on 2018/8/17.
 */
public class MyLinkedList {

    public static void main(String[] args) {
        MyLinkedList obj = new MyLinkedList();
        obj.addAtTail(1);
        obj.addAtTail(2);
//        obj.addAtTail(3);
//        obj.addAtTail(4);
//        obj.addAtTail(5);
//        obj.addAtTail(6);
//        obj.addAtTail(7);
//        obj.addAtTail(8);


        obj.removeNthFromEnd(obj.mHead, 2);
//        obj2.getNode(2).next = obj.getNode(5);
//        obj.getCycleHead(obj.mHead);

//        System.out.println(obj.getCycleHead(obj.mHead).val + "");
    }


    public Node removeNthFromEnd(Node head, int n) {
        if (head == null || head.next == null) return null;
        Node node = head;
        Node node1 = node;
        while (true) {
            if (node1.next == null) {
                head.next = null;
                return node;
            }
            for (int i = 0; i <= n + 1; i++) {
                if (node1 == null) {
                    if (i == n + 1) {
                        head.next = head.next.next;
                        return node;
                    }if(i == n){
                        node = node.next;
                        return node;
                    } else {
                        break;
                    }
                }
                node1 = node1.next;
            }
            head = head.next;
            node1 = head;
        }
    }

    /**
     * @param head
     * @return 是否有环
     * <p>
     * 判断单链表是否有环
     */
    public boolean hasCycle(Node head) {
        Node fastNode = head;
        Node slowNode = head;
        while (fastNode != null && fastNode.next != null) {
            System.out.println("fastNode:" + fastNode.val + "slowNode:" + slowNode.val);
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
            if (fastNode == slowNode) {
                System.out.println("相遇点：" + fastNode.val);
                return true;
            }
        }
        return false;
    }

    /**
     * 相交链表  判断两条链表是否相交
     * 假定不是环形链表
     *
     * @param headA 链表A
     * @param headB 链表B
     * @return
     */
    public Node getIntersectionNode(Node headA, Node headB) {
        if (headA == null || headB == null) return null;
        Node nodeA = headA;
        Node nodeB = headB;
        int numA = 0;
        int numB = 0;
        while (nodeA != null || nodeB != null) {
            if (nodeA == nodeB) return nodeA;
            if (nodeA == null) {
                numB++;
            } else {
                nodeA = nodeA.next;
            }
            if (nodeB == null) {
                numA++;
            } else {
                nodeB = nodeB.next;
            }
        }
        nodeA = headA;
        nodeB = headB;
        while (nodeA != null || nodeB != null) {
            if (nodeA == nodeB) return nodeA;
            if (numB > 0) {
                numB--;
            } else {
                nodeA = nodeA.next;
            }
            if (numA > 0) {
                numA--;
            } else {
                nodeB = nodeB.next;
            }
        }
        return null;
    }

    /**
     * @param head
     * @return 是否有环
     * <p>
     * 判断单链表是否有环  有环返回进环点，无环返回null
     */
    public Node getCycleHead(Node head) {
        Node fastNode = head;
        Node slowNode = head;
        while (fastNode != null && fastNode.next != null) {
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
            if (fastNode == slowNode) break;
        }
        if (fastNode == null || fastNode.next == null) return null;
        slowNode = head;
        while (slowNode != fastNode) {
            fastNode = fastNode.next;
            slowNode = slowNode.next;
        }
        return slowNode;
    }


    private int size = 0;
    private Node mHead;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {

    }

    /**
     * 添加环到指定位置
     *
     * @param position
     */
    public void setCycle(int position) {
        Node node = mHead;
        Node cycleNode = null;
        for (int i = 0; i < size; i++) {
            if (i == position) {
                cycleNode = node;
            }
            if (i == size - 1) {
                node.next = cycleNode;
            }
            node = node.next;
        }
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index < 0 || index >= size || mHead == null) {
            return -1;
        }
        Node node = mHead;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return node.val;
            }
            node = node.next;
        }
        return -1;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public Node getNode(int index) {
        if (index < 0 || index >= size || mHead == null) {
            return null;
        }
        Node node = mHead;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node node = new Node(val);
        if (mHead == null) {
            this.mHead = node;
        } else {
            node.next = mHead;
            mHead = node;
        }
        size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        if (mHead == null) {
            addAtHead(val);
            return;
        }
        Node last = new Node(val);
        Node node = mHead;
        for (int i = 0; i < size; i++) {
            if (node.next == null) {
                node.next = last;
            } else {
                node = node.next;
            }

        }
        size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index > size) {
//            new Exception("index outsize or  index < 1");
            return;
        }
        if (mHead == null) {
            addAtHead(val);
            return;
        }
        if (index == size) {
            addAtTail(val);
            return;
        }
        if (index == 0) {
            addAtHead(val);
            return;
        }
        Node addNode = new Node(val);
        Node node = mHead;
        for (int i = 0; i < size; i++) {
            if (i == index - 1) {
                addNode.next = node.next;
                node.next = addNode;
                size++;
                return;
            }
            node = node.next;
        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index >= size) {
            return;
        }
        if (index == 0) {
            mHead = mHead.next;
            size--;
            return;
        }
        Node node = mHead;
        for (int i = 0; i < size; i++) {
            if (index - 1 == i) {
                node.next = node.next.next;
                size--;
                return;
            }
            node = node.next;
        }
    }

    private static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }
    }
}
