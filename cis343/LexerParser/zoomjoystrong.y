 
%{
	#include <stdio.h>
    #include "zoomjoystrong.h"
	void yyerror(const char* msg);
	int yylex();
	int num_expressions = 0;
%}

%error-verbose
%start statement_list

%union { int i; char* str; float f;}

%token END
%token END_STATEMENT
%token POINT
%token LINE
%token CIRCLE
%token RECTANGLE
%token SET_COLOR
%token FLOAT
%token INT
%token SPACE
%token UNKNOWN


%type<str> END
%type<str> END_STATEMENT
%type<str> POINT
%type<str> LINE
%type<str> CIRCLE
%type<str> RECTANGLE
%type<str> SET_COLOR
%type<f>   FLOAT
%type<i>   INT
%type<str> SPACE
%type<str> UNKNOWN


%%
statement_list:	statement end
	        |	statement statement_list end
;

statement:     	PLOT_POINT
            |  	DRAW_LINE
            |  	DRAW_CIRCLE
            |  	DRAW_RECTANGLE
            |  	CHANGE_COLOR
            |  	FLOATER
;

end:            END
		{printf("End command or end of file reached.\n");}
		{printf("Closing render window...\n");}
        {finish();}
		{return 0;}

;

PLOT_POINT:	        POINT INT INT END_STATEMENT
{
	if ($2 < 0 || $3 < 0) {
		printf("PLOT_POINT ERROR: One or more position values out of range.\n");
	}
	else {
		{point($2, $3);}
	}
}
;

DRAW_LINE:		    LINE INT INT INT INT END_STATEMENT
{
	if ($2 < 0 || $3 < 0 || $4 < 0 || $5 < 0) {
		printf("DRAW_LINE ERROR: One or more position values out of range.\n");
	}
	else {
		{line($2, $3, $4, $5);}
	}
}
;

DRAW_CIRCLE:        CIRCLE INT INT INT END_STATEMENT
{
	if ($2 < 0 || $3 < 0 || $4 < 0) {
		printf("DRAW_CIRCLE ERROR: One or more position values out of range.\n");
	}
	else {
		{circle($2, $3, $4);}
	}
}
;

DRAW_RECTANGLE:     RECTANGLE INT INT INT INT END_STATEMENT
{
	if ($2 < 0 || $3 < 0 || $4 < 0 || $5 < 0) {
		printf("DRAW_RECTANGLE ERROR: One or more position values out of range.\n");
	}
	else {
		{rectangle($2, $3, $4, $5);}
	}
}
;

CHANGE_COLOR:       SET_COLOR INT INT INT END_STATEMENT
{
	if ($2 > 255 || $3 > 255 || $4 > 255 || $2 < 0 || $3 < 0 || $4 < 0) {
		printf("CHANGE_COLOR ERROR: One or more RGB values out of range.\n");
	}
	else {
		set_color($2, $3, $4);
	}
}
;

FLOATER:            FLOAT END_STATEMENT
		{printf("FLOAT\n");}
;

%%

int main(int argc, char** argv){
	printf("Setting up render window...\n");
	poll_events();
	setup();
	printf("Beginning Parse...\n");
	yyparse();
	printf("Parsing complete.\n");
	return 0;
}

void yyerror(const char* msg){
	fprintf(stderr, "ERROR! %s\n", msg);
}
