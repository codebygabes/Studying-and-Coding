#include <stdio.h>
#include <stdlib.h>

int main() {

  int n = 0;
  char letra;
  printf("DIGITE LETRAS: ");
  while (letra != 'X')
  {
    n = n + 1;
    scanf("%c", &letra);
  }
  
  printf("%d", n);

   return 0;
   
}