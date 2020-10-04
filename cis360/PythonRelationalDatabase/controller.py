#/////////////////////////////////
# Author:   Shane Stacy
# Desc:     Small relational database using SQLite backend, interfaced with Python3
# Date:     2/16/2020
#/////////////////////////////////
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, ForeignKey, Integer, String, Float, Boolean
from sqlalchemy import Index
from sqlalchemy.orm import relationship, backref, sessionmaker
from sqlalchemy import create_engine

Base = declarative_base()



#  the student table
class Student_Table(Base):
    __tablename__ = 'student'
    id = Column(Integer, index=True, primary_key=True)
    sex = Column(String(6))
    status = Column(String(9))
    credits = Column(Integer)
    age = Column(Integer)
    advisor = Column(String(20))


#  the student object
class Student(object):
    def __init__(self, id, sex, status, credits, age, advisor):
        # student id number
        self.id = id
        # student is male or female
        self.sex = sex
        # freshman, sophomore, junior, or senior
        self.status = status
        # number of credits
        self.credits = credits
        # how old they are
        self.age = age
        # advisor
        self.advisor = advisor


def main():

    #  create db engine
    engine = create_engine('sqlite:///:memory:', echo = True)
    #  create the session with the engine
    Session = sessionmaker(bind = engine)
    session = Session()
    #  create the table
    Base.metadata.create_all(engine)

    #  make a list of Students objects
    #  student parameters are id, sex, status, credits, age, advisor.  Primary key is id.
    students = []
    students.append(Student(123456789, 'M', 'Senior', 105, 21, 'Smith'))
    students.append(Student(987654321, 'F', 'Senior', 112, 23, 'Thompson'))
    students.append(Student(135797531, 'M', 'Senior', 140, 34, 'Thompson'))
    students.append(Student(123123123, 'F', 'Junior', 90, 21, 'Smith'))
    students.append(Student(321321321, 'M', 'Junior', 93, 20, 'Smith'))
    students.append(Student(456456456, 'F', 'Sophomore', 30, 19, 'Thompson'))
    students.append(Student(654654654, 'M', 'Sophomore', 33, 19, 'Smith'))
    students.append(Student(789789789, 'F', 'Freshman', 10, 18, 'Thompson'))
    students.append(Student(987987987, 'M', 'Freshman', 15, 19, 'Thompson'))
    students.append(Student(101010101, 'F', 'Freshman', 21, 19, 'Smith'))

    #  insert the Student objects into the Student_Table
    for student in students:
        add = Student_Table(id = student.id, sex = student.sex, status = student.status, credits = student.credits, age = student.age, advisor = student.advisor)
        session.add(add)

    session.commit()


    while (1):
        #  prompt user
        print('Enter your choice:')
        print('1<--Insert a row')
        print('2<--See the table contents')
        print('3<--Quit')
        x = int(input())
        if (x == 3):
            exit(0)
        elif (x == 1):
            print('Please enter id, sex, status, credits, age, and advisor delimited by comma.')
            x = input()
            list = x.split(',')
            userStudent = Student(list[0], list[1], list[2], list[3], list[4], list[5])
            add = Student_Table(id = userStudent.id, sex = userStudent.sex, status = userStudent.status, credits = userStudent.credits, age = userStudent.age, advisor = userStudent.advisor)
            session.add(add)
            session.commit()
        elif (x == 2):
            #  get the query (not the results of the query!)
            query1 = session.query(Student_Table)
            #  get the results of the query
            results = query1.all()
            #  for each student in the table, print their id
            for each in results:
                print(each.id, '\t', each.sex, '\t', each.status, '\t', each.credits, '\t', each.age, '\t', each.advisor)
        else:
            print()


#  if script is run directly, execute main
if __name__=='__main__':
    main()

