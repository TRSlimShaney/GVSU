//
// Created by Hans Dulimarta
//

#ifndef LINKEDLISTWITHTEMPLATE_LINKED_LIST_H
#define LINKEDLISTWITHTEMPLATE_LINKED_LIST_H
#include <memory>
#include <stdexcept>
#include <set>
#include <iostream>

using namespace std;

namespace gv {
    template <typename T> class linked_list {
    private:
        struct Node;
    public:
        /**
         * Default constructor
         */
        linked_list() {
        }

        /**
         * Check if the list contains data items
         * @return true if it is empty, false otherwise
         */
        bool is_empty() const {
            if (head == nullptr) {
                return true;
            }
            return false;
        }

        /**
         * Count the number of  items in the list
         * @return the number of items
         */
        int size() const {
            shared_ptr<Node> pointer = make_shared<Node>();
            pointer = head;
            int counter = 0;

            while (pointer != nullptr) {
                pointer = pointer->next;
                counter++;
            }
            return counter;
        }

        /**
         * Make the list empty
         */
        void clear() {
            head.reset();
            tail.reset();
        }

        /**
         * Return the first data in the list, or throw an length_error exception when the list is empty
         * @return the data stored in the first node
         * @throw length_error when the list is empty
         */
        const T& front() const {
            if (this->is_empty()) {
                throw std::length_error("This linked list is empty!");
            }
            else {
                return head->data;
            }
        };

        /**
         * Return the last data in the list, or throw an length_error exception when the list is empty
         * @return the data stored in the first node
         * @throw length_error when the list is empty
         */
        const T& back() const {
            if (this->is_empty()) {
                throw std::length_error("This linked list is empty!");
            }
            else {
                return tail.lock()->data;
            }
        };

        /**
         * Store x as the first data item in the list
         * @param x the value to store
         */
        void push_front(const T& x) {
            shared_ptr<Node> push = make_shared<Node>();
            shared_ptr<Node> swap = make_shared<Node>();
            push->data = x;


            if (this->is_empty()) {
                head = push;
                tail = push;
            }
            else {
                swap = head;
                swap->prev = push;
                head = push;
                push->next = swap;
            }
        }

        /**
         * Store x as the last data item in the list
         * @param x the value to store
         */
        void push_back(const T& x) {
            shared_ptr<Node> push = make_shared<Node>();
            shared_ptr<Node> swap = make_shared<Node>();
            push->data = x;


            if (this->is_empty()) {
                head = push;
                tail = push;
            }
            else {
                swap = tail.lock();
                swap->next = push;
                push->prev = swap;
                tail = push;

            }

        }

        /**
         * Insert x as a new data item at the specified position in the list.
         * As an example: if the list holds string data {"Carbon", "Oxygen"}
         * insert_into ("Neon", 0) => {"Neon", "Carbon", "Oxygen"}
         * insert_into ("Neon", 1) => {"Carbon", "Neon", "Oxygen"}
         * insert_into ("Neon", 2) => {"Carbon", "Oxygen", "Neon"}
         * insert_into ("Neon", N) => throw out_of_range when N < 0 or N > 2
         *
         * @param x the value to store
         * @param pos the position at which x is to be inserted (0 = first)
         * @throw out_of_range exception when pos is invalid (negative or larger than list size)
         */
        void insert_into (const T&x, int pos) {

            if (pos == 0) {
                push_front(x);
            }
            else if (pos == this->size()) {
                push_back(x);
            }
            else if (pos < 0 || pos > this->size()) {
                throw std::out_of_range("The selected position does not exist.");
            }
            else {
                shared_ptr<Node> pointer = make_shared<Node>();
                shared_ptr<Node> firstElement = make_shared<Node>();
                shared_ptr<Node> secondElement = make_shared<Node>();
                shared_ptr<Node> push = make_shared<Node>();
                pointer = head;
                push->data = x;
                for (int c = 0; c < pos - 1; c++) {
                    pointer = pointer->next;
                }
                firstElement = pointer;
                pointer = pointer->next;
                pointer = pointer->next;
                secondElement = pointer;

                firstElement->next = push;
                secondElement->prev = push;
                push->prev = firstElement;
                push->next = secondElement;
            }
        }

        /**
         * Remove the data at a given position
         * As an example: if the list holds string data {"Carbon", "Oxygen", "Fluor"}
         * remove_from(0) => {"Oxygen", "Fluor"}
         * remove_from(1) => {"Carbon", "Fluor"}
         * remove_from(2) => {"Carbon", "Oxygen"}
         * remove_from(3) => throw out_of_range exception
         * remove_from(-1) => throw out_of_range exception
         *
         * @param pos the position at which the data is to be removed (0 = first)
         * @throw out_of_range exception when pos is invalid (negative or >= list size)
         */
        void remove_from (int pos) {

            if (pos < 0 || pos >= this->size() || this->is_empty()) {
                throw std::out_of_range("The selected position does not exist.");
            }
            else if (pos == 0) {
                pop_front();
            }
            else if (pos == this->size() - 1) {
                pop_back();
            }
            else {
                shared_ptr<Node> pointer = make_shared<Node>();
                shared_ptr<Node> firstElement = make_shared<Node>();
                shared_ptr<Node> secondElement = make_shared<Node>();
                shared_ptr<Node> middleElement = make_shared<Node>();
                pointer = head;
                for (int c = 0; c < pos - 1; c++) {
                    pointer = pointer->next;
                }
                firstElement = pointer;
                pointer = pointer->next;
                middleElement = pointer;
                pointer = pointer->next;
                secondElement = pointer;

                firstElement->next = secondElement;
                secondElement->prev = firstElement;
                middleElement->next.reset();
                middleElement->prev.reset();
            }



        }

        /**
         * Return the data stored at a given position
         * @param pos the position at which the data is to be retrieved (0 = first)
         * @return the data at the requested position
         * @throw out_of_range exception when pos is invalid (negative or >= list size)
         */
        const T& at (int pos) const {
            if (pos < 0 || pos >= this->size() || this->is_empty()) {
                throw std::out_of_range("The selected position does not exist.");
            }

            shared_ptr<Node> pointer = make_shared<Node>();
            pointer = head;
            for (int c = 0; c < pos; c++) {
                pointer = pointer->next;
            }
            return pointer->data;
        };

        /**
         * Remove the first data item
         * @throw length_error when the list is empty
         */
        void pop_front() {
            if (this->is_empty()) {
                throw std::length_error("This linked list is empty!");
            }
            else if (this->size() == 1) {
                head.reset();
                tail.reset();
            }
            else {
                shared_ptr<Node> pointer = make_shared<Node>();
                shared_ptr<Node> otherElement = make_shared<Node>();
                pointer = head->next;
                pointer->prev.reset();
                otherElement = head;
                otherElement->next.reset();
                head = pointer;
            }
        }

        /**
         * Remove the last data item
         * @throw length_error when the list is empty
         */
        void pop_back() {
            if (this->is_empty()) {
                throw std::length_error("This linked list is empty!");
            }
            else if (this->size() == 1) {
                head.reset();
                tail.reset();
            }
            else {
                shared_ptr<Node> pointer = make_shared<Node>();
                shared_ptr<Node> otherElement = make_shared<Node>();
                pointer = tail.lock();
                pointer = pointer->prev.lock();
                pointer->next.reset();
                otherElement = tail.lock();
                otherElement->prev.reset();
                tail = pointer;
            }


        }
        /**
         * Implement self-adjusting list (Problem 3.30b). Search for a given item
         * and move the item to the front of the list (if found)
         * @param val data item to search for in the list
         * @return true if the item is found, false otherwise
         */
        bool find(const T& val) {
            shared_ptr<Node> pointer = make_shared<Node>();
            pointer = head;
            if (this->is_empty()) {
                return false;
            }
            for (int c = 0; c < this->size(); c++) {
                if (pointer->data != val) {
                    pointer = pointer->next;
                }
                else {
                    remove_from(c);
                    push_front(val);
                    return true;
                }
            }
            return false;
        }

    private:
        // You may add private data/function here


        // But, DO NOT CHANGE ANYTHING BELOW THIS LINE
        shared_ptr<Node> head;
        weak_ptr<Node> tail;
        struct Node {
            T data;
            shared_ptr<Node> next;
            weak_ptr<Node> prev;
        };
    };
};
#endif //LINKEDLISTWITHTEMPLATE_LINKED_LIST_H
