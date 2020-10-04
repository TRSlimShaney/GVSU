test: test1 test2 test3 test4

test1:
	bison -d zoomjoystrong.y

test2:
	flex zoomjoystrong.lex

test3:
	clang -o zjs zoomjoystrong.c lex.yy.c zoomjoystrong.tab.c -lSDL2 -lm

test4:
	./zjs < sample.zjs
