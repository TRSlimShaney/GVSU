test: test1 test2 test3 test4 test5

test1:
	gcc proj1.c -o proj1

test2:
	./proj1 1 textbook encrypt.dat encryptOutput.out

test3:
	diff -s encryptOutput.out decrypt.dat

test4:
	./proj1 0 textbook decrypt.dat decryptOutput.out

test5:
	diff -s decryptOutput.out encrypt.dat