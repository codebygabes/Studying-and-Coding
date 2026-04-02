#include <stdio.h>
#include <locale.h>
#include <math.h>

int main(){

	setlocale(LC_ALL, "Portuguese");
	
   float preco;
   int desconto;

   printf("Digite o preco: ");
   scanf("%f" , &preco);

   if(preco > 500){
    desconto = 0.20;
   }
   else if(preco > 200){
    desconto = 0.15;
   }

   else if(preco > 100){
    desconto = 0.10;
   }
   else{
    desconto = 0;
    printf("Nao possui desconto");
   }
   float valorfinal = preco - (preco *  desconto / 100);

   printf("O valor final da compra e: R$%.2f\n" , valorfinal);
}