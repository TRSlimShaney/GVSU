//
// Created by Hans Dulimarta.
//

#ifndef BINARYTREES_BINARYSEARCHTREE_H
#define BINARYTREES_BINARYSEARCHTREE_H
#include <memory>
#include <iostream>
#include <stdexcept>
#include <queue>
#include <stack>

using namespace std;

namespace gv {
    template<typename E>     // textbook code E is Comparable
    class BinaryTree {

    public:
        /** WARNING: You will find a few overloaded functions declared in
         * both the public and private sections. This design is intentional
         * because many tree algorithms are recursive and the private counterpart
         * are the ones implementing the recursive work and they usually
         * require additional input argument(s).
         *
         * The non-recursive public functions simply call their recursive
         * counterpart to initiate the recursive work.
         */

        /**
         * Make the tree into an empty tree
         */
        void makeEmpty() noexcept {
            // TODO: complete this function
            root = nullptr;
        }

        /**
         * Insert a new data into the BST while maintaining no duplicate entries
         * @param item
         * @return true if the item can be inserted (no duplicate), false if the tree
         * already contains the same data item.
         */
        bool insert(const E &item) noexcept {
            // TODO: complete this function by invoking a private recursive function
                return insert(item, root);
        }

        /**
         * Remove an item from the tree
         * @param item data to remove
         * @return true is the data is removed, false if the data is not in the tree
         * @throw length_error on an attempt to remove from an empty tree
         */
        bool remove(const E &item) {
            // TODO: complete this function by invoking a private recursive function
            if (isEmpty()) {
                throw length_error("Tree is empty");
            }
            return remove(item, root);
        }

        /**
         * Print the tree using in-order traversal. Separate data item by a single space
         * @param out destination of the print out
         */
        void printTree(ostream &targetStream = cout) const noexcept {
            // TODO: complete this function by invoking a private recursive function
            // Be sure to use "targetStream" (and NOT cout) to print your data
            // For instance the following snippet would print "Hello"
            //   targetStream << "Hello";
            printTree(root, targetStream);
        }

        /**
         * Find the smallest value in the tree
         * @return the smallest value
         * @throw length_error if the tree is empty
         */
        const E findMin() const {
            // TODO: complete this function
            if (isEmpty()) {
                throw length_error("Tree is empty");
            }
            else {
                return findMin(root);
            }
        };

        /**
         * Find the largest value in the tree
         * @return the largest value
         * @throw length_error if the tree is empty
         */
        const E findMax() const {
            // TODO: complete this function
            if (isEmpty()) {
                throw length_error("Tree is empty");
            }
            else {
                return findMax(root);
            }
        }

        /**
         * Check if the given item is stored in the tree
         * @param val
         * @return true if the item is stored in the tree, false otherwise
         */
        bool contains(const E &val) const noexcept {
            // TODO: complete this function by invoking a private recursive function
            return contains(val, root);
        }

        /**
         * Is the tree empty?
         * @return
         */
        bool isEmpty() const noexcept {
            // TODO: complete this function
            if (root == nullptr) {
                return true;
            }
            return false;
        }

        /**
         * Return the number of nodes in the tree (Problem 4.31a)
         * @return
         */
        int numberOfNodes() const noexcept {
            // TODO: complete this function by invoking a private recursive function
            return numberOfNodes(root);
        }

        /**
         * Return the number of leaves in the tree (Problem 4.31b)
         * @return
         */
        int numberOfLeaves() const noexcept {
            // TODO: complete this function by invoking a private recursive function
            return numberOfLeaves(root);
        }

        /**
         * Return the number of full nodes (Problem 4.31c). A full node is a node with exactly two children
         * @return
         */
        int numberOfFullNodes() const noexcept {
            // TODO: complete this function by invoking a private recursive function
            return numberOfFullNodes(root);
        }

        /**
         * Remove all the leaves from the tree and keep the data in these leaves into a vector
         * @return a vector of removed items
         */
        vector<E> remove_leaves() noexcept {
            vector<E> prunedLeaves;
            // TODO: complete this function by invoking a private recursive function
            remove_leaves(root, prunedLeaves);
            // include the vector (prunedLeaves) about in your function invocation
            return prunedLeaves;
        }

        /**
         * Visit the node in level order (Problem 4.40)
         * @return
         */
        vector<E> levelOrder() const {
            vector<E> out;
            // TODO: complete this function
            if (!isEmpty()) {
                levelOrder(root, out);
            }
            return out;
        }

        static bool hasLeak() {
            return nodeCount != 0;
        }

        static int allocatedNodes() {
            return nodeCount;
        }

    private:
        struct Node;

        void printTree(const unique_ptr<Node> &c, ostream &targetStream = cout) const noexcept {

            if (c == nullptr) {
                return;
            }
            else {
                printTree(c->left, targetStream);
                targetStream << c->data + " ";
                printTree(c->right, targetStream);
            }
        }

        void levelOrder(const unique_ptr<Node> &c, vector<E> &x) const {

            if (c == nullptr) {
                return;
            }
            queue<Node*> Working{};
            Working.push(c.get());
            while (!Working.empty()) {
                auto temp = Working.front();
                x.push_back(temp->data);
                Working.pop();;
                if (temp->left != nullptr) {
                    Working.push((temp->left).get());
                }
                if (temp->right != nullptr) {
                    Working.push((temp->right).get());
                }
            }
            return;
        }

        int numberOfNodes(const unique_ptr<Node> &c) const noexcept {

            if (c == nullptr) {
                return 0;
            }
            else {
                return 1 + numberOfNodes(c->left) + numberOfNodes(c->right);
            }
        }

        int numberOfLeaves(const unique_ptr<Node> &c) const noexcept {

            if (c == nullptr) {
                return 0;
            }
            else {
                if (c->left == nullptr && c->right == nullptr) {
                    return 1 + numberOfLeaves(c->left) + numberOfLeaves(c->right);
                }
                return 0 + numberOfLeaves(c->left) + numberOfLeaves(c->right);
            }
        }

        int numberOfFullNodes(const unique_ptr<Node> &c) const noexcept {

            if (c == nullptr) {
                return 0;
            }
            else {
                if (c->left != nullptr && c->right != nullptr) {
                    return 1 + numberOfFullNodes(c->left) + numberOfFullNodes(c->right);
                }
                return 0 + numberOfFullNodes(c->left) + numberOfFullNodes(c->right);
            }
        }

        void remove_leaves(const unique_ptr<Node> &c, vector<E> &x) noexcept{

            if (c == nullptr) {
                return;
            }
            else {
                if (c->left == nullptr && c->right == nullptr) {
                    x.push_back(c->data);
                    remove(c->data);
                }
                else {
                    remove_leaves(c->left, x);
                    remove_leaves(c->right, x);
                }
            }
        }

        bool contains(const E &val, const unique_ptr<Node> &c) const noexcept {

            if (c == nullptr) {
                return false;
            }
            else if (val < c->data) {
                return contains(val, c->left);
            }
            else if (c->data < val) {
                return contains(val, c->right);
            }
            else {
                return true;
            }
        }

        bool insert(const E &x, unique_ptr<Node> &c) noexcept {

            if (isEmpty()) {
                root = make_unique<Node>();
                root->data = x;
                return true;
            }

            if (x < c->data) {
                if (c->left == nullptr) {
                    c->left = make_unique<Node>();
                    c->left->data = x;
                    c->left->left = nullptr;
                    c->left->right = nullptr;
                    return true;
                }
                return insert(x, c->left);
            }
            else if (c->data < x) {
                if (c->right == nullptr) {
                    c->right = make_unique<Node>();
                    c->right->data = x;
                    c->right->left = nullptr;
                    c->right->right = nullptr;
                    return true;
                }
                return insert(x, c->right);
            }
            else {
                return false;
            }
        }

        bool remove(const E &x, unique_ptr<Node> &c) noexcept {

            if (c == nullptr) {
                return false;
            }
            if (x < c->data) {
                return remove(x, c->left);
            }
            if (c->data < x) {
                return remove(x, c->right);
            }
            if (c->left != nullptr && c->right != nullptr) {
                c->data = findMin(c->right);
                return remove(c->data, c->right);
            }
            else {
                if (c->left != nullptr) {
                    c.reset((c->left).release());
                    return true;
                }
                c.reset((c->right).release());
                return true;
            }

        }

        const E findMin(const unique_ptr<Node> &c) const noexcept {

            if (c->left == nullptr) {
                return c->data;
            }
            return findMin(c->left);
        }

        const E findMax(const unique_ptr<Node> &c) const noexcept {

                if (c->right != nullptr) {
                    return findMax(c->right);
                }
                return c->data;
        }

        static int nodeCount;
        // TODO: Make necessary modification to the Node
        // structure if you decide to implement a threaded tree
        struct Node {
            Node() {
                nodeCount++;
            }

            ~Node() {
                nodeCount--;
            }
            E data;
            unique_ptr<Node> left, right;
        };
        unique_ptr<Node> root;

    };

    template<typename E>
    int BinaryTree<E>::nodeCount = 0;
}
#endif //BINARYTREES_BINARYSEARCHTREE_H
