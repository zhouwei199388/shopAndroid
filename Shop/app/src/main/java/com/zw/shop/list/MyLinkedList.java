package com.zw.shop.list;

/**
 * Created by ZouWei on 2018/8/17.
 */
public class MyLinkedList {

    private int size = 1;
    private Node mHead;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {

    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index >= size) {
            return -1;
        } else {
            Node node = mHead;
            for (int i = 0; i < size; i++) {
                node = node.next;
                if (size - 1 == index) {
                    return node.val;
                }
            }
        }
        return -1;
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
        Node last = new Node(val);
        Node node = mHead;
        for (int i = 0; i < size; i++) {
            node = node.next;
            if (i == size - 1) {
                node.next = last;
            }
        }
        size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index >= size) {
            return;
        }
        if (index == size - 1) {
            addAtTail(val);
            return;
        }
        Node addNode = new Node(val);
        Node node = mHead;
        for (int i = 0; i < size; i++) {
            node = node.next;
            if (i == index) {
                addNode.next = node.next;
                node.next = addNode;
            }
        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        Node node = mHead;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                Node node1 = node.next;
                node.next = node1;
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
