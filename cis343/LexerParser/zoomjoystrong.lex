%{
    #include "zoomjoystrong.tab.h"
	#include <stdlib.h>
    void print(char* str, char* yytext);
%}

%option noyywrap

%%

<<EOF>>									{return END;}
end                                   	{return END;}
[;]                                     {return END_STATEMENT;}
point                                 	{return POINT;}
line                                  	{return LINE;}
circle                                	{return CIRCLE;}
rectangle                             	{return RECTANGLE;}
set_color                             	{return SET_COLOR;}
(\-){0,1}[0-9]*\.[0-9]+                 {yylval.f = atof(yytext); return FLOAT;}
(\-){0,1}[0-9]+							{yylval.i = atoi(yytext); return INT;}
[^\n\r\t ]								{return UNKNOWN;}
[ \n\r\t]                            	{;}


%%

void print(char* str, char* yytext) {
        printf("5%s\t\t(%s)\n", str, yytext);
}
