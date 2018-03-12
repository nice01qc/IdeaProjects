function [result]=variance(x,y)     % x£¬y¶¼ÊÇĞĞ
[r,c]=size(x);
result=0;
n=0;
for i=2:c
    nice = ((x(1,i)-x(1,i-1))^2 + (y(1,i)-y(1,i-1))^2)^0.5;
   if nice > 0
       n=n+1;
   end
   result= result + nice;
end
result = result / n;
end