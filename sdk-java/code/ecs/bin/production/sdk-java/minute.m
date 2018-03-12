clear all
close all
clc
name ='mydata.txt';
data = importdata(name);


times =[];
nums =[0,0];

j=1;
tmp = 0;
[r,c]=size(data);
for i = 1 : r
    if data(i,1)<100
        tmp = data(i,1);
        j=1;
    else 
        if data(i,2) > 3    %% 一次不能超过多少个
            j=j+1;
            continue;
        end
        times(tmp,j)=data(i,1)-23668500;
        nums(tmp,j)=data(i,2);
        j = j+1;
    end    
end




[nr,nc]=size(nums);
for i = 1:nr
   for j = 1:nc
       if times(i,j) == 0
           if j-1 < 1
               continue;
           end
           times(i,j) = times(i,j-1);   
       end
   end
   
   for j = 2:nc
      nums(i,j)=nums(i,j) + nums(i,j-1);
   end
end

% 计算所有方程1-all
[nr,nc]=size(nums);
fangcha=[];
for i=1:nr
    fangcha(i) = variance(times(i,:),nums(i,:));
end





% 绘制数据图，分段绘制
part = [];
[nr,nc]=size(nums);

figure(1)
j=1;
for i=1:nr
    if nums(i,nc) >= 10 || nums(i,nc) == 0
        continue;
    end
    if nums(i,1) == 0
        continue;
    end
   plot(times(i,:),nums(i,:),'LineWidth',1.5); 
   part(1,j) = i;
   j=j+1;
   grid on;
   hold on;
end

figure(2)
j=1;
for i=1:nr
    if nums(i,nc) >= 20 || nums(i,nc) < 10
        continue;
    end
    if nums(i,1) == 0
        continue;
    end
   plot(times(i,:),nums(i,:),'LineWidth',1.5); 
   part(2,j) = i;
   j=j+1;
   grid on;
   hold on;
end

figure(3)
j=1;
for i=1:nr
    if nums(i,nc) >= 30 || nums(i,nc) < 20
        continue;
    end
    if nums(i,1) == 0
        continue;
    end
   plot(times(i,:),nums(i,:),'LineWidth',1.5); 
   part(3,j) = i;
   j=j+1;
   grid on;
   hold on;
end

figure(4)
j=1;
for i=1:nr
    if nums(i,nc) < 30 || nums(i,nc) >= 50
        continue;
    end
   plot(times(i,:),nums(i,:),'LineWidth',1.5); 
   part(4,j) = i;
   j=j+1;
   grid on;
   hold on;
end

figure(5)
j=1;
for i=1:nr
    if nums(i,nc) < 50
        continue;
    end
   plot(times(i,:),nums(i,:),'LineWidth',1.5); 
   part(5,j) = i;
   j=j+1;
   grid on;
   hold on;
end

% 以方差绘制图形
figure(6)
for i=1:nr
    if fangcha(i) > 0 && fangcha(i) < 2400
        plot(times(i,:),nums(i,:),'LineWidth',1.5);
    end
    
   grid on;
   hold on;
end


% 查看7-11
figure(7)
for i=7:11
   plot(times(i,:),nums(i,:),'LineWidth',1.5); 
   grid on;
   hold on;
end








